package cash.dfd.dfdjava.test.client;

import cash.dfd.dfdjava.address.Address;
import cash.dfd.dfdjava.builder.TransactionBuilder;
import cash.dfd.dfdjava.client.NodeClient;
import cash.dfd.dfdjava.client.response.AmountInfoResponse;
import cash.dfd.dfdjava.client.response.AssetInfoResponse;
import cash.dfd.dfdjava.client.response.TransactionResponse;
import cash.dfd.dfdjava.client.response.TxOperationReceiptResponse;
import cash.dfd.dfdjava.config.Constants;
import cash.dfd.dfdjava.exceptions.TransactionException;
import cash.dfd.dfdjava.operation.IOperation;
import cash.dfd.dfdjava.operation.NodeException;
import cash.dfd.dfdjava.operation.TransferOperation;
import cash.dfd.dfdjava.transaction.Transaction;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NodeClientTests {
    private static final Logger log = LoggerFactory.getLogger(NodeClientTests.class);

    @Test
    public void testNodeClientGetInfo() throws NodeException {
        String nodeRpcEndpoint = "ws://localhost:8090";
        NodeClient nodeClient = new NodeClient(nodeRpcEndpoint);

        nodeClient.open();
        nodeClient.sendLogin();

        String addr = "DAQ2LP5DzXr1JMVWUEqQYfgniFPqjwMeNj";
        String pubKeyStr = "";
        String contractId = "cYmS6hW3BSez8q1TB4NsNA2KHsV77rf1De";
        String contractApi = "tokenName";
        String contractArg = "";
        String contractTxId = "";
        List<AmountInfoResponse> balances = nodeClient.getAddrBalances(addr);
        List<AssetInfoResponse> assets = nodeClient.listAssets();
//        JSONObject testingResult = nodeClient.invokeContractTesting(pubKeyStr, contractId, contractApi, contractArg);
//        String invokeResult = nodeClient.invokeContractOffline(pubKeyStr, contractId, contractApi, contractArg);
        log.info("balances: {}", JSON.toJSONString(balances));
        log.info("assets: {}", JSON.toJSONString(assets));
//        log.info("testing result: {}", testingResult.toJSONString());
//        log.info("invoke result: {}", invokeResult);
//        String refInfo = nodeClient.constructRefInfo(6648413, "0065725d686b75c3d6576ecc17d5161c260d6dcd");
//        log.info("example refInfo: {}", refInfo);
        String refInfoFromNode = nodeClient.getRefInfo();
        log.info("refInfoFromNode: {}", refInfoFromNode);
        JSONObject blockInfo = nodeClient.getBlock(100);
        log.info("blockInfo: {}", JSON.toJSONString(blockInfo));
//        List<TxOperationReceiptResponse> txReceipts = nodeClient.getContractTxReceipts(contractTxId);
//        log.info("txReceipts: {}", JSON.toJSONString(txReceipts));
    }

    @Test
    public void testTransfer() throws NodeException,TransactionException {
        String nodeRpcEndpoint = "ws://localhost:8090";
        NodeClient nodeClient = new NodeClient(nodeRpcEndpoint);

        nodeClient.open();
        nodeClient.sendLogin();

        String refInfo = nodeClient.getRefInfo();
        String chainId = Constants.mainnetChainId;
        String wifStr = "";
        String fromAddr = "DAQ2LP5DzXr1JMVWUEqQYfgniFPqjwMeNj";
        String toAddr = "DAQ2LP5DzXr1JMVWUEqQYfgniFPqjwMeNj";
        BigDecimal amount = new BigDecimal("0.001");
        BigDecimal fee = new BigDecimal("0.0011");
        String memo = "test";
        Transaction tx = TransactionBuilder.createTransferTransaction(refInfo, fromAddr, toAddr, amount, "1.3.0", 5, fee, memo, null);
        log.info("unsigned tx: {}", JSON.toJSONString(tx));
        String txJson = TransactionBuilder.signTransaction(tx, wifStr, chainId, Address.ADDRESS_PREFIX);
        log.info("signed tx: {}", txJson);
        String txId = nodeClient.broadcastTransactionASync(JSON.parseObject(txJson));
        log.info("txId: {}", txId);
    }

    @Test
    public void testTokenTransfer() throws NodeException,TransactionException {
        String nodeRpcEndpoint = "ws://localhost:8090";
        NodeClient nodeClient = new NodeClient(nodeRpcEndpoint);

        nodeClient.open();
        nodeClient.sendLogin();

        String refInfo = nodeClient.getRefInfo();
        String chainId = Constants.mainnetChainId;
        String wifStr = "";
        String callerAddr = "DAQ2LP5DzXr1JMVWUEqQYfgniFPqjwMeNj";
        String callerPubKey = "D8Vhuqn2JeBu78ZgxwRMQkVMnweTL67QaBxYGuhrsytdWyfA7F3";
        String contractId = "cZgcKr944yhacPKXgWvDvjkjDfNpGBoN9J";
        String contractApi = "transfer";
        String contractArg = "DB7m9ifiwvfrdnAoZGHChtpRazLDtKoADm,1";

        long gasLimit = 10000;
        long gasPrice = 1;

        BigDecimal fee = new BigDecimal("0.003");

        Transaction tx = TransactionBuilder.createContractInvokeTransaction(refInfo, callerAddr, callerPubKey,
                contractId, contractApi, contractArg, fee, gasLimit, gasPrice, null);
        log.info("unsigned tx: {}", JSON.toJSONString(tx));
        String txJson = TransactionBuilder.signTransaction(tx, wifStr, chainId, Address.ADDRESS_PREFIX);
        log.info("signed tx: {}", txJson);
        String txId = nodeClient.broadcastTransactionASync(JSON.parseObject(txJson));
        log.info("txId: {}", txId);
    }


    @Test
    public void testTokenBalance() throws NodeException {
        String nodeRpcEndpoint = "ws://localhost:8090";
        NodeClient nodeClient = new NodeClient(nodeRpcEndpoint);

        nodeClient.open();
        nodeClient.sendLogin();

        String pubKeyStr = "D8Vhuqn2JeBu78ZgxwRMQkVMnweTL67QaBxYGuhrsytdWyfA7F3";
        String contractId = "cZgcKr944yhacPKXgWvDvjkjDfNpGBoN9J";
        String contractApi = "balanceOf";
        String contractArg = "DB7m9ifiwvfrdnAoZGHChtpRazLDtKoADm";
        String invokeResult = nodeClient.invokeContractOffline(pubKeyStr, contractId, contractApi, contractArg);

        log.info("invokeResult: {}", invokeResult);
    }

    @Test
    public void getDynamicGlobalProperties() throws NodeException {
        String nodeRpcEndpoint = "ws://localhost:8090";
        NodeClient nodeClient = new NodeClient(nodeRpcEndpoint);

        nodeClient.open();
        nodeClient.sendLogin();

        JSONObject obj = nodeClient.getDynamicGlobalProperties();
        log.info("DynamicGlobalProperties: {}", obj);
    }

    @Test
    public void testGetHeadBlockNumber() throws NodeException {
        String nodeRpcEndpoint = "ws://localhost:8090";
        NodeClient nodeClient = new NodeClient(nodeRpcEndpoint);

        nodeClient.open();
        nodeClient.sendLogin();

        String headBlockNumber = nodeClient.getHeadBlockNumber();
        log.info("headBlockNumber: {}", headBlockNumber);
    }

}
