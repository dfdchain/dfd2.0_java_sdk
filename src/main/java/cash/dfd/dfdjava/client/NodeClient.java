package cash.dfd.dfdjava.client;

import cash.dfd.dfdjava.client.response.AmountInfoResponse;
import cash.dfd.dfdjava.client.response.AssetInfoResponse;
import cash.dfd.dfdjava.client.response.TransactionResponse;
import cash.dfd.dfdjava.client.response.TxOperationReceiptResponse;
import cash.dfd.dfdjava.exceptions.DeserializeException;
import cash.dfd.dfdjava.operation.NodeException;
import cash.dfd.dfdjava.serializer.Uint32Serializer;
import cash.dfd.dfdjava.utils.Numeric;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class NodeClient implements Closeable {
    private static final Logger log = LoggerFactory.getLogger(NodeClient.class);

    public static final int RPC_LOGIN_SERVICE_ID = 1;
    public static int RPC_DATABASE_SERVICE_ID = 2;
    public static int RPC_NETWORK_SERVICE_ID = 3;
    public static int RPC_HISTORY_SERVICE_ID = 4;

    private String nodeRpcEndpoint; // node的websocket rpc endpoint
    private WebSocketClient wsClient;
    private ConcurrentMap<Integer, CompletableFuture<NodeRpcResponse>> waitingRpcRequests = new ConcurrentHashMap<>();

    public NodeClient(String nodeRpcEndpoint) {
        this.nodeRpcEndpoint = nodeRpcEndpoint;
        wsClient = new WebSocketClient(this, nodeRpcEndpoint);
    }

    public void onChannelConnected() {
        log.info("WebSocket Client connected!");
    }

    public void onChannelConnectFailed() {
        log.warn("WebSocket Client failed to connect");
    }

    public void onChannelDisconnected() {
        log.warn("WebSocket Client disconnected!");
    }

    public void onWebSocketFrame(WebSocketFrame frame) {
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            onTextWebSocketFrame(textFrame);
        } else if (frame instanceof PongWebSocketFrame) {
            log.info("received pong from ws server");
        } else if (frame instanceof CloseWebSocketFrame) {

        }
    }

    private NodeRpcResponse decodeNodeRpcResponse(String text) {
        try {
            JSONObject json = JSON.parseObject(text);
            return json.toJavaObject(NodeRpcResponse.class);
        } catch (Exception e) {
            log.warn("invalid node rpc response format {}", text);
            return null;
        }
    }

    private void onTextWebSocketFrame(TextWebSocketFrame frame) {
        String text = frame.text();
        log.debug("response {}", text);
        NodeRpcResponse rpcRes = decodeNodeRpcResponse(text);
        if(rpcRes==null) {
            return;
        }
        if(rpcRes.getId()==null) {
            return;
        }
        int requestId = rpcRes.getId();
        CompletableFuture<NodeRpcResponse> resFuture = waitingRpcRequests.getOrDefault(requestId, null);
        if(resFuture==null) {
            return;
        }
        waitingRpcRequests.remove(requestId);
        try {
            resFuture.complete(rpcRes);
        } catch (Exception e) {
            log.warn("complete node request future warn {}", e.getMessage());
        }
    }

    public void onWebSocketClientException(Throwable cause) {
        cause.printStackTrace();
        log.error("node websocket channel exception", cause);
    }

    public void open() throws NodeException {
        try {
            wsClient.open();
        } catch (URISyntaxException e) {
            throw new NodeException(e);
        } catch (IOException e) {
            throw new NodeException(e);
        } catch (InterruptedException e) {
            throw new NodeException(e);
        }
    }

    public void sendText(String msg) {
        wsClient.sendText(msg);
    }

    @Override
    public void close() {
        if(wsClient!=null) {
            try {
                wsClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error("ws client close error", e);
            }
        }
    }

    public JSONObject getTransactionById(String txid) throws NodeException {
        return (JSONObject) callRpc(RPC_DATABASE_SERVICE_ID, "get_transaction_by_id", Collections.singletonList(txid));
    }

    public JSONArray getObjects(List<String> objectIds) throws NodeException {
        return (JSONArray) callRpc(RPC_DATABASE_SERVICE_ID, "get_objects", Collections.singletonList(objectIds));
    }

    public JSONObject getBlock(int blockNum) throws NodeException {
        return (JSONObject) callRpc(RPC_DATABASE_SERVICE_ID, "get_block", Collections.singletonList(blockNum));
    }

    public List<TxOperationReceiptResponse> getContractTxReceipts(String txid) throws NodeException {
        JSONArray resJson = (JSONArray) callRpc(RPC_DATABASE_SERVICE_ID, "get_contract_invoke_object", Collections.singletonList(txid));
        return resJson.toJavaList(TxOperationReceiptResponse.class);
    }

    public String constructRefInfo(int headBlockNumber, String headBlockId) throws NodeException {
        int refBlockNum = headBlockNumber & 0xffff;
        if(headBlockId.length()<8) {
            throw new NodeException("invalid headBlockId");
        }
        headBlockId = headBlockId.substring(8);
        byte[] headBlockIdBytes = Numeric.hexStringToByteArray(headBlockId);
        long refBlockPrefix;
        try {
            refBlockPrefix = new Uint32Serializer(true).deserialize(headBlockIdBytes);
        } catch (DeserializeException e) {
            throw new NodeException(e);
        }
        return String.format("%s,%s", refBlockNum, refBlockPrefix);
    }

    /**
     * get "refBlockNum,refBlockPrefix" from network
     * @return
     */
    public String getRefInfo() throws NodeException {
        JSONArray objects = getObjects(Collections.singletonList("2.1.0"));
        if(objects.isEmpty()) {
            throw new NodeException("can't find object 2.1.0 to get ref info");
        }
        JSONObject firstObject = objects.getJSONObject(0);
        Integer headBlockNumber = firstObject.getInteger("head_block_number");
        String headBlockId = firstObject.getString("head_block_id");
        if(headBlockNumber == null || headBlockId == null) {
            throw new NodeException("empty head_block_number or head_block_id to consturct ref info");
        }
        return constructRefInfo(headBlockNumber, headBlockId);
    }

    public JSONObject getDynamicGlobalProperties() throws NodeException {
        return (JSONObject) callRpc(RPC_DATABASE_SERVICE_ID, "get_dynamic_global_properties", null);
    }

    public String getHeadBlockNumber() throws NodeException {
        JSONObject obj =  (JSONObject) callRpc(RPC_DATABASE_SERVICE_ID, "get_dynamic_global_properties", null);
        return obj.get("head_block_number").toString();
    }

    public String getChainId() throws NodeException {
        return (String) callRpc(RPC_DATABASE_SERVICE_ID, "get_chain_id", null);
    }

    public List<AmountInfoResponse> getAddrBalances(String addr) throws NodeException {
        JSONArray resJson = (JSONArray) callRpc(RPC_DATABASE_SERVICE_ID, "get_addr_balances", Collections.singletonList(addr));
        return resJson.toJavaList(AmountInfoResponse.class);
    }

    public List<AssetInfoResponse> listAssets(String prefix, int limit) throws NodeException {
        JSONArray resJson = (JSONArray) callRpc(RPC_DATABASE_SERVICE_ID, "list_assets", Arrays.asList(prefix, limit));
        return resJson.toJavaList(AssetInfoResponse.class);
    }

    public List<AssetInfoResponse> listAssets() throws NodeException {
        return listAssets("", 100);
    }

    public JSONObject getAccountByName(String name) throws NodeException {
        return (JSONObject) callRpc(RPC_DATABASE_SERVICE_ID, "get_account_by_name", Collections.singletonList(name));
    }

    public JSONObject getMinerById(String minerId) throws NodeException {
        return (JSONObject) callRpc(RPC_DATABASE_SERVICE_ID, "get_miner", Collections.singletonList(minerId));
    }

    public JSONArray getAccountLockBalances(String accountId) throws NodeException {
        return (JSONArray) callRpc(RPC_DATABASE_SERVICE_ID, "get_account_lock_balance", Collections.singletonList(accountId));
    }

    public JSONArray getAddressPayBackBalances(String address, String assetSymbol) throws NodeException {
        return (JSONArray) callRpc(RPC_DATABASE_SERVICE_ID, "get_address_pay_back_balance", Arrays.asList(address, assetSymbol));
    }

    public JSONArray getSyncModeNetworkInfo() throws NodeException {
        return (JSONArray) callRpc(RPC_DATABASE_SERVICE_ID, "get_sync_mode_network_info", null);
    }

    /**
     * 调用network node rpc本地执行合约获取合约API返回结果
     * @param callerPubKeyStr
     * @param contractId
     * @param contractApi
     * @param contractArg
     * @return
     */
    public String invokeContractOffline(String callerPubKeyStr, String contractId, String contractApi, String contractArg) throws NodeException {
        return (String) callRpc(RPC_DATABASE_SERVICE_ID, "invoke_contract_offline",
                Arrays.asList(callerPubKeyStr, contractId, contractApi, contractArg!=null?contractArg:""));
    }

    public JSONObject invokeContractTesting(String callerPubKeyStr, String contractId, String contractApi, String contractArg) throws NodeException {
        return (JSONObject) callRpc(RPC_DATABASE_SERVICE_ID, "invoke_contract_testing",
                Arrays.asList(callerPubKeyStr, contractId, contractApi, contractArg!=null?contractArg:""));
    }

    public TransactionResponse broadcastTransactionSync(JSONObject signedTransactionJson) throws NodeException {
        JSONObject resJson = (JSONObject) callRpc(RPC_NETWORK_SERVICE_ID, "broadcast_transaction_synchronous", Collections.singletonList(signedTransactionJson));
        return resJson.toJavaObject(TransactionResponse.class);
    }

    public void sendLogin() throws NodeException {
        callRpc(RPC_LOGIN_SERVICE_ID, "login", Arrays.asList("", ""));
        RPC_DATABASE_SERVICE_ID = Integer.parseInt(callRpc(RPC_LOGIN_SERVICE_ID, "database", null).toString());
        RPC_NETWORK_SERVICE_ID = Integer.parseInt(callRpc(RPC_LOGIN_SERVICE_ID, "network_broadcast", null).toString());
        RPC_HISTORY_SERVICE_ID = Integer.parseInt(callRpc(RPC_LOGIN_SERVICE_ID, "history", null).toString());
    }

    private AtomicInteger idGenerator = new AtomicInteger(0);
    private int rpcTimeoutMilliSeconds = 10000; // rpc超时的毫秒数

    public Object callRpc(int rpcServiceId, String method, Object args) throws NodeException {
        NodeRpcResponse res = callRpcWithError(rpcServiceId, method, args);
        if(res.getError()!=null) {
            throw new NodeException(JSON.toJSONString(res.getError()));
        }
        return res.getResult();
    }

    /**
     * @return
     */
    public NodeRpcResponse callRpcWithError(int rpcServiceId, String method, Object args) {
        JSONObject msgJson = new JSONObject();
        msgJson.put("method", "call");
        int id = idGenerator.incrementAndGet();
        msgJson.put("id", id);
        JSONArray paramsJson = new JSONArray();
        paramsJson.add(rpcServiceId);
        paramsJson.add(method);
        paramsJson.add(args != null ? args : new ArrayList<>());
        msgJson.put("params", paramsJson);
        String msg = msgJson.toJSONString();
        CompletableFuture<NodeRpcResponse> future = new CompletableFuture<>();
        waitingRpcRequests.put(id, future);
        log.debug("request {}", msg);
        sendText(msg);
        try {
            return future.get(rpcTimeoutMilliSeconds, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return NodeRpcResponse.ofError(id, RpcErrorInfo.INTERNAL_ERROR_CODE, e.getMessage(), e);
        } catch (ExecutionException e) {
            return NodeRpcResponse.ofError(id, RpcErrorInfo.INTERNAL_ERROR_CODE, e.getMessage(), e);
        } catch (TimeoutException e) {
            return NodeRpcResponse.ofError(id, RpcErrorInfo.TIMEOUT_ERROR_CODE, "timeout", null);
        } finally {
            waitingRpcRequests.remove(id);
        }
    }
}
