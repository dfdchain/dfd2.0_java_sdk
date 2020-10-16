package cash.dfd.dfdjava.client;

import cash.dfd.dfdjava.operation.NodeException;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketClient implements Closeable {
    private static final Logger log = LoggerFactory.getLogger(WebSocketClient.class);

    private final NodeClient nodeClient;
    private String endpoint;
    private transient boolean closed = true;
    private Channel clientChannel;
    private EventLoopGroup eventLoopGroup;

    public WebSocketClient(NodeClient nodeClient, String endpoint) {
        this.nodeClient = nodeClient;
        this.endpoint = endpoint;
    }

    public void open() throws URISyntaxException, IOException, InterruptedException, NodeException {
        if(!closed) {
            return;
        }
        closed = false;
        log.info("trying to connect to {}", endpoint);

        URI uri = new URI(endpoint);
        String scheme = uri.getScheme() == null? "ws" : uri.getScheme();
        final String host = uri.getHost() == null? "127.0.0.1" : uri.getHost();
        final int port;
        if (uri.getPort() == -1) {
            if ("ws".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("wss".equalsIgnoreCase(scheme)) {
                port = 443;
            } else {
                port = -1;
            }
        } else {
            port = uri.getPort();
        }

        if (!"ws".equalsIgnoreCase(scheme) && !"wss".equalsIgnoreCase(scheme)) {
            throw new NodeException("Only WS(S) is supported.");
        }

        final boolean ssl = "wss".equalsIgnoreCase(scheme);
        final SslContext sslCtx;
        if (ssl) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }

        eventLoopGroup = new NioEventLoopGroup();
        try {
            // Connect with V13 (RFC 6455 aka HyBi-17). You can change it to V08 or V00.
            // If you change it to V00, ping is not supported and remember to change
            // HttpResponseDecoder to WebSocketHttpResponseDecoder in the pipeline.
            final WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(
                    uri, WebSocketVersion.V13, null, true, new DefaultHttpHeaders(), 65536 * 5); // need use larger payload limit than default
            final WebSocketClientHandler handler =
                    new WebSocketClientHandler(nodeClient,
                            handshaker);

            Bootstrap b = new Bootstrap();
            b.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline p = ch.pipeline();
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc(), host, port));
                            }
                            p.addLast(
                                    new HttpClientCodec(),
                                    new HttpObjectAggregator(8192),
                                    WebSocketClientCompressionHandler.INSTANCE,
                                    handler);
                        }
                    });

            clientChannel = b.connect(uri.getHost(), port).sync().channel();
            handler.handshakeFuture().sync();
            log.info("handshake finished");
        } catch (Exception e) {
            close();
            throw e;
        }
    }

    public void sendPing(){
        WebSocketFrame frame = new PingWebSocketFrame(Unpooled.wrappedBuffer(new byte[] { 8, 1, 8, 1 }));
        sendWebSocketFrame(frame);
    }

    public void sendWebSocketFrame(WebSocketFrame frame) {
        clientChannel.writeAndFlush(frame);
    }

    public void sendText(String msg) {
        WebSocketFrame frame = new TextWebSocketFrame(msg);
        sendWebSocketFrame(frame);
    }

    @Override
    public void close() throws IOException {
        if(clientChannel!=null) {
            clientChannel.writeAndFlush(new CloseWebSocketFrame());
            try {
                clientChannel.closeFuture().sync();
            } catch (InterruptedException e) {
                throw new IOException(e);
            }
        }
        if(eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
        }
        closed = true;
    }
}
