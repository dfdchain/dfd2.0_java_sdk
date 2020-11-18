package cash.dfd.dfdjava.builder;

import cash.dfd.dfdjava.asset.Asset;
import cash.dfd.dfdjava.asset.AssetUtil;
import cash.dfd.dfdjava.config.Constants;
import cash.dfd.dfdjava.crypto.CryptoUtil;
import cash.dfd.dfdjava.exceptions.CryptoException;
import cash.dfd.dfdjava.exceptions.PubKeyInvalidException;
import cash.dfd.dfdjava.exceptions.SerializeException;
import cash.dfd.dfdjava.exceptions.TransactionException;
import cash.dfd.dfdjava.operation.ContractInvokeOperation;
import cash.dfd.dfdjava.operation.ContractTransferOperation;
import cash.dfd.dfdjava.operation.OperationsUtil;
import cash.dfd.dfdjava.operation.TransferOperation;
import cash.dfd.dfdjava.pubkey.PubKeyUtil;
import cash.dfd.dfdjava.serializer.TransactionSerializer;
import cash.dfd.dfdjava.transaction.Memo;
import cash.dfd.dfdjava.transaction.MemoUtil;
import cash.dfd.dfdjava.transaction.RefBlockInfo;
import cash.dfd.dfdjava.transaction.Transaction;
import cash.dfd.dfdjava.utils.Numeric;
import cash.dfd.dfdjava.utils.SignatureUtil;
import cash.dfd.dfdjava.utils.StringUtil;
import com.alibaba.fastjson.JSON;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

public class TransactionBuilder {
    private static final Logger log = LoggerFactory.getLogger(TransactionBuilder.class);

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    static {
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static Transaction createContractTransferTransaction(String refInfo, String callerAddr, String callerPubKey, String contractId,
                                                                BigDecimal transferAmount,
                                                                String assetId, int assetPrecision, String transferMemo, BigDecimal fee, Long gasLimit, Long gasPrice, String guaranteeId) throws TransactionException {
        long transferAmountFull = transferAmount.multiply(new BigDecimal(10).pow(assetPrecision)).longValue();

        Asset amountAsset = AssetUtil.defaultAsset();
        amountAsset.setAssetId(assetId);
        amountAsset.setAmount(transferAmountFull);

        long feeFull = fee.multiply(new BigDecimal(10).pow(Constants.dfdPrecision)).longValue();

        Asset feeAsset = AssetUtil.defaultAsset();
        feeAsset.setAmount(feeFull);

        ContractTransferOperation operation = OperationsUtil.defaultContractTransferOperation();
        operation.setFee(feeAsset);
        operation.setCallerAddr(callerAddr);
        if(callerPubKey!=null && callerPubKey.startsWith(PubKeyUtil.PUBKEY_STRING_PREFIX)) {
            // 把公钥从base58格式转换成hex格式
            try {
                callerPubKey = PubKeyUtil.base58PubKeyToHex(callerPubKey);
            } catch (PubKeyInvalidException e) {
                throw new TransactionException(e);
            }
        }
        operation.setCallerPubkey(callerPubKey);
        operation.setContractId(contractId);
        operation.setAmount(amountAsset);
        operation.setParam(transferMemo!=null?transferMemo:"");
        if(gasLimit!=null) {
            operation.setInvokeCost(gasLimit);
        }
        if(gasPrice!=null) {
            operation.setGasPrice(gasPrice);
        }

        if(!StringUtil.isEmpty(guaranteeId)) {
            operation.setGuaranteeId(guaranteeId);
        }

        long expireSec = (System.currentTimeMillis() / 1000) + Constants.expireTimeout;
        String expireStr = sdf.format(new Date(expireSec * 1000)); // expire_str := "2018-09-26T09:14:40"

        RefBlockInfo refBlockInfo = RefBlockInfo.decodeFromInfoString(refInfo);
        int refBlockNum = refBlockInfo.getRefBlockNum();
        long refBlockPrefix = refBlockInfo.getRefBlockPrefix();
        Transaction tx = new Transaction();
        tx.setRefBlockNum(refBlockNum);
        tx.setRefBlockPrefix(refBlockPrefix);
        tx.setExpiration(expireStr);
        tx.setTransientExpiration(expireSec);
        tx.setOperations(Collections.singletonList(Arrays.asList(operation.getOperationType(), operation)));
        tx.setExtensions(new ArrayList<>());
        tx.setSignatures(new ArrayList<>());
        tx.setTransientOperations(Collections.singletonList(operation));
        return tx;
    }

    public static Transaction createContractInvokeTransaction(String refInfo, String callerAddr, String callerPubKey, String contractId,
                                                              String contractApi, String contractArg, BigDecimal fee, Long gasLimit, Long gasPrice, String guaranteeId) throws TransactionException {
        long feeFull = fee.multiply(new BigDecimal(10).pow(Constants.dfdPrecision)).longValue();

        Asset feeAsset = AssetUtil.defaultAsset();
        feeAsset.setAmount(feeFull);

        ContractInvokeOperation operation = OperationsUtil.defaultContractInvokeOperation();
        operation.setFee(feeAsset);
        operation.setCallerAddr(callerAddr);
        if(callerPubKey!=null && callerPubKey.startsWith(PubKeyUtil.PUBKEY_STRING_PREFIX)) {
            // 把公钥从base58格式转换成hex格式
            try {
                callerPubKey = PubKeyUtil.base58PubKeyToHex(callerPubKey);
            } catch (PubKeyInvalidException e) {
                throw new TransactionException(e);
            }
        }
        operation.setCallerPubkey(callerPubKey);
        operation.setContractId(contractId);
        operation.setContractApi(contractApi);
        operation.setContractArg(contractArg);
        if(gasLimit!=null) {
            operation.setInvokeCost(gasLimit);
        }
        if(gasPrice!=null) {
            operation.setGasPrice(gasPrice);
        }

        if(!StringUtil.isEmpty(guaranteeId)) {
            operation.setGuaranteeId(guaranteeId);
        }

        long expireSec = (System.currentTimeMillis() / 1000) + Constants.expireTimeout;
        String expireStr = sdf.format(new Date(expireSec * 1000)); // expire_str := "2018-09-26T09:14:40"

        RefBlockInfo refBlockInfo = RefBlockInfo.decodeFromInfoString(refInfo);
        int refBlockNum = refBlockInfo.getRefBlockNum();
        long refBlockPrefix = refBlockInfo.getRefBlockPrefix();
        Transaction tx = new Transaction();
        tx.setRefBlockNum(refBlockNum);
        tx.setRefBlockPrefix(refBlockPrefix);
        tx.setExpiration(expireStr);
        tx.setTransientExpiration(expireSec);
        tx.setOperations(Collections.singletonList(Arrays.asList(operation.getOperationType(), operation)));
        tx.setExtensions(new ArrayList<>());
        tx.setSignatures(new ArrayList<>());
        tx.setTransientOperations(Collections.singletonList(operation));
        return tx;
    }

    public static Transaction createTransferTransaction(String refInfo, String fromAddr, String toAddr, BigDecimal transferAmount,
                                                        String assetId, int assetPrecision, BigDecimal fee, String memo,
                                                        String guaranteeId) throws TransactionException {
        long transferAmountFull = transferAmount.multiply(new BigDecimal(10).pow(assetPrecision)).longValue();
        long feeFull = fee.multiply(new BigDecimal(10).pow(Constants.dfdPrecision)).longValue();

        Asset amountAsset = AssetUtil.defaultAsset();
        amountAsset.setAssetId(assetId);
        amountAsset.setAmount(transferAmountFull);

        Asset feeAsset = AssetUtil.defaultAsset();
        feeAsset.setAmount(feeFull);

        TransferOperation transferOperation = OperationsUtil.defaultTransferOperation();
        transferOperation.setFee(feeAsset);
        transferOperation.setAmount(amountAsset);
        transferOperation.setFromAddr(fromAddr);
        transferOperation.setToAddr(toAddr);

        if(StringUtil.isEmpty(memo)) {
            transferOperation.setMemo(null);
        } else {
            Memo memoObj = MemoUtil.defaultMemo();
            memoObj.setTransientMessage(memo);
            memoObj.setEmpty(false);
            byte[] zero4 = new byte[4];
            CryptoUtil.setBytesZero(zero4);
            byte[] memoBytes = CryptoUtil.bytesMerge(zero4, memo.getBytes());
            memoObj.setMessage(Numeric.toHexStringNoPrefix(memoBytes));
            transferOperation.setMemo(memoObj);
        }

        if(!StringUtil.isEmpty(guaranteeId)) {
            transferOperation.setGuaranteeId(guaranteeId);
        }

        long expireSec = (System.currentTimeMillis() / 1000) + Constants.expireTimeout;
        String expireStr = sdf.format(new Date(expireSec * 1000)); // expire_str := "2018-09-26T09:14:40"

        RefBlockInfo refBlockInfo = RefBlockInfo.decodeFromInfoString(refInfo);
        int refBlockNum = refBlockInfo.getRefBlockNum();
        long refBlockPrefix = refBlockInfo.getRefBlockPrefix();
        Transaction tx = new Transaction();
        tx.setRefBlockNum(refBlockNum);
        tx.setRefBlockPrefix(refBlockPrefix);
        tx.setExpiration(expireStr);
        tx.setTransientExpiration(expireSec);
        tx.setOperations(Collections.singletonList(Arrays.asList(transferOperation.getOperationType(), transferOperation)));
        tx.setExtensions(new ArrayList<>());
        tx.setSignatures(new ArrayList<>());
        tx.setTransientOperations(Collections.singletonList(transferOperation));
        return tx;
    }

    public static String signTransaction(Transaction transaction, String wifStr, String chainId, String addressPrefix)
            throws TransactionException {
        TransactionSerializer txSerializer = new TransactionSerializer(addressPrefix);
        try {
            byte[] txBytes = txSerializer.serialize(transaction);
            log.debug("tx hex: {}", Numeric.toHexStringNoPrefix(txBytes));
            byte[] chainIdBytes = Numeric.hexStringToByteArray(chainId);
            byte[] toSignBytes = CryptoUtil.bytesMerge(chainIdBytes, txBytes);
            log.info("chain id hex: {}", Hex.toHexString(chainIdBytes));
            log.info("tx hex: {}", Hex.toHexString(txBytes));
            byte[] sig = SignatureUtil.getSignature(wifStr, toSignBytes);
            List<String> signatures = transaction.getSignatures();
            if(signatures==null) {
                signatures = new ArrayList<>();
            }
            signatures.add(Numeric.toHexStringNoPrefix(sig));
            transaction.setSignatures(signatures);
            return JSON.toJSONString(transaction);
        } catch (Exception e) {
            e.printStackTrace();
            throw new TransactionException(e);
        }
    }
}
