package cash.dfd.dfdjava.operation;

import cash.dfd.dfdjava.asset.Asset;
import com.alibaba.fastjson.annotation.JSONField;

public class ContractInvokeOperation implements IOperation {
    private Asset fee;
    @JSONField(name = "invoke_cost")
    private long invokeCost;
    @JSONField(name = "gas_price")
    private long gasPrice;
    @JSONField(name = "caller_addr")
    private String callerAddr;
    @JSONField(name = "caller_pubkey")
    private String callerPubkey; // 这里似乎用的是公钥的hex格式
    @JSONField(name = "contract_id")
    private String contractId;
    @JSONField(name = "contract_api")
    private String contractApi;
    @JSONField(name = "contract_arg")
    private String contractArg;
    @JSONField(name = "guarantee_id")
    private String guaranteeId;

    public Asset getFee() {
        return fee;
    }

    public void setFee(Asset fee) {
        this.fee = fee;
    }

    public long getInvokeCost() {
        return invokeCost;
    }

    public void setInvokeCost(long invokeCost) {
        this.invokeCost = invokeCost;
    }

    public long getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(long gasPrice) {
        this.gasPrice = gasPrice;
    }

    public String getCallerAddr() {
        return callerAddr;
    }

    public void setCallerAddr(String callerAddr) {
        this.callerAddr = callerAddr;
    }

    public String getCallerPubkey() {
        return callerPubkey;
    }

    public void setCallerPubkey(String callerPubkey) {
        this.callerPubkey = callerPubkey;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractApi() {
        return contractApi;
    }

    public void setContractApi(String contractApi) {
        this.contractApi = contractApi;
    }

    public String getContractArg() {
        return contractArg;
    }

    public void setContractArg(String contractArg) {
        this.contractArg = contractArg;
    }

    public String getGuaranteeId() {
        return guaranteeId;
    }

    public void setGuaranteeId(String guaranteeId) {
        this.guaranteeId = guaranteeId;
    }

    @Override
    public int getOperationType() {
        return OperationTypes.CONTRACT_INVOKE_OPERATION;
    }
}
