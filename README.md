dfd2.0-java
=========

dfd-2.0 java sdk


# Usage

* `mvn package install -Dmaven.test.skip=true` to install lib locally
* add dependency to pom.xml or build.gradle

# Demo

* create and sign transfer transaction

```
String refInfo = "30375,575365464";
String chainId = Constants.mainnetChainId;
String wifStr = "";
String fromAddr = "";
String toAddr = "";
BigDecimal amount = new BigDecimal("0.001");
BigDecimal fee = new BigDecimal("0.0011");
String memo = "test";
Transaction tx = TransactionBuilder.createTransferTransaction(refInfo, fromAddr, toAddr, amount, "1.3.0", 5, fee, memo, null);
String txJson = TransactionBuilder.signTransaction(tx, wifStr, chainId, Address.ADDRESS_PREFIX);
log.info("signed tx: {}", txJson);
// then you can use client rpc's lightwallet_broadcast or node rpc to broadcast this signed transaction json
```

* invoke contract transaction

```
String refInfo = "16573,2299525098";
String chainId = Constants.mainnetChainId;
String wifStr = "";
String callerAddr = "";
String callerPubKey = "";
String contractId = "cYmS6hW3BSez8q1TB4NsNA2KHsV77rf1De";
String contractApi = "balanceOf";
String contractArg = "";

long gasLimit = 10000;
long gasPrice = 1;

BigDecimal fee = new BigDecimal("0.003");

Transaction tx = TransactionBuilder.createContractInvokeTransaction(refInfo, callerAddr, callerPubKey,
        contractId, contractApi, contractArg, fee, gasLimit, gasPrice, null);
String txJson = TransactionBuilder.signTransaction(tx, wifStr, chainId, Address.ADDRESS_PREFIX);
log.info("signed tx: {}", txJson);
```

* transfer asset to contract transaction
```
String refInfo = getRefInfo();
String chainId = Constants.mainnetChainId;
String wifStr = "";
String callerAddr = "";
String callerPubKey = "";
String contractId = "cYmS6hW3BSez8q1TB4NsNA2KHsV77rf1De";
BigDecimal transferAmount = new BigDecimal("0.001");
String transferMemo = "hi";

long gasLimit = 10000;
long gasPrice = 1;

BigDecimal fee = new BigDecimal("0.003");

Transaction tx = TransactionBuilder.createContractTransferTransaction(refInfo, callerAddr, callerPubKey,
        contractId, transferAmount, "1.3.0", Constants.dfdPrecision, transferMemo, fee, gasLimit, gasPrice, null);
String txJson = TransactionBuilder.signTransaction(tx, wifStr, chainId, Address.ADDRESS_PREFIX);
log.info("signed tx: {}", txJson);
```

* generate private key and address

```
ECKey ecKey = PrivateKeyGenerator.generate();
String privateKeyHex = ecKey.getPrivateKeyAsHex();
String wif = PrivateKeyUtil.privateKeyToWif(ecKey);
log.info("privateKeyHex: {}", privateKeyHex);
log.info("privateKey wif: {}", wif);
Address address = Address.fromPubKey(ecKey.getPubKey(), AddressVersion.NORMAL);
log.info("address: {}", address);
```

* get data from network node rpc

```
String nodeRpcEndpoint = "ws://localhost:8090";
NodeClient nodeClient = new NodeClient(nodeRpcEndpoint);
try {
    nodeClient.open();
    nodeClient.sendLogin();
    String addr = "";
    String pubKeyStr = "";
    String contractId = "cYmS6hW3BSez8q1TB4NsNA2KHsV77rf1De";
    String contractApi = "tokenName";
    String contractArg = "";
    String contractTxId = "";
    List<AmountInfoResponse> balances = nodeClient.getAddrBalances(addr);
    List<AssetInfoResponse> assets = nodeClient.listAssets();
    JSONObject testingResult = nodeClient.invokeContractTesting(pubKeyStr, contractId, contractApi, contractArg);
    String invokeResult = nodeClient.invokeContractOffline(pubKeyStr, contractId, contractApi, contractArg);
    log.info("balances: {}", JSON.toJSONString(balances));
    log.info("assets: {}", JSON.toJSONString(assets));
    log.info("testing result: {}", testingResult.toJSONString());
    log.info("invoke result: {}", invokeResult);
    String refInfo = nodeClient.constructRefInfo(6648413, "0065725d686b75c3d6576ecc17d5161c260d6dcd");
    log.info("example refInfo: {}", refInfo);
    JSONObject blockInfo = nodeClient.getBlock(100);
    log.info("blockInfo: {}", JSON.toJSONString(blockInfo));
    List<TxOperationReceiptResponse> txReceipts = nodeClient.getContractTxReceipts(contractTxId);
    log.info("txReceipts: {}", JSON.toJSONString(txReceipts));
} finally {
    nodeClient.close();
}
```