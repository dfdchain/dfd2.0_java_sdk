package cash.dfd.dfdjava.operation;

import cash.dfd.dfdjava.asset.AssetUtil;

import java.util.ArrayList;

public class OperationsUtil {
    public static TransferOperation defaultTransferOperation() {
        TransferOperation op = new TransferOperation();
        op.setFee(AssetUtil.defaultAsset());
        op.setGuaranteeId(null);
        op.setFrom("1.2.0");
        op.setTo("1.2.0");
        op.setFromAddr("");
        op.setToAddr("");
        op.setAmount(AssetUtil.defaultAsset());
        op.setMemo(null);
        op.setExtensions(new ArrayList<>());
        return op;
    }
    public static ContractInvokeOperation defaultContractInvokeOperation() {
        ContractInvokeOperation op = new ContractInvokeOperation();
        op.setFee(AssetUtil.defaultAsset());
        op.setGuaranteeId(null);
        op.setCallerAddr("");
        op.setCallerPubkey("");
        op.setContractId("");
        op.setContractApi("");
        op.setContractArg("");
        op.setInvokeCost(10000);
        op.setGasPrice(1);
        return op;
    }

    public static ContractTransferOperation defaultContractTransferOperation() {
        ContractTransferOperation op = new ContractTransferOperation();
        op.setFee(AssetUtil.defaultAsset());
        op.setGuaranteeId(null);
        op.setCallerAddr("");
        op.setCallerPubkey("");
        op.setContractId("");
        op.setAmount(AssetUtil.defaultAsset());
        op.setParam("");
        op.setInvokeCost(10000);
        op.setGasPrice(1);
        return op;
    }
}
