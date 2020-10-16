package cash.dfd.dfdjava.client.response;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class TxOperationReceiptResponse {
    public static class ReceiptEventInfo {
        @JSONField(name = "block_num")
        private Integer blockNum;
        @JSONField(name = "event_arg")
        private String eventArg;
        @JSONField(name = "event_name")
        private String eventName;
        @JSONField(name = "op_num")
        private Integer opNum;
        @JSONField(name = "contract_address")
        private String contractAddress;
        @JSONField(name = "caller_addr")
        private String callerAddr;

        public Integer getBlockNum() {
            return blockNum;
        }

        public void setBlockNum(Integer blockNum) {
            this.blockNum = blockNum;
        }

        public String getEventArg() {
            return eventArg;
        }

        public void setEventArg(String eventArg) {
            this.eventArg = eventArg;
        }

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public Integer getOpNum() {
            return opNum;
        }

        public void setOpNum(Integer opNum) {
            this.opNum = opNum;
        }

        public String getContractAddress() {
            return contractAddress;
        }

        public void setContractAddress(String contractAddress) {
            this.contractAddress = contractAddress;
        }

        public String getCallerAddr() {
            return callerAddr;
        }

        public void setCallerAddr(String callerAddr) {
            this.callerAddr = callerAddr;
        }
    }
    @JSONField(name = "exec_succeed")
    private Boolean execSucceed;
    private String invoker;
    @JSONField(name = "op_num")
    private Integer opNum;
    @JSONField(name = "trx_id")
    private String trxId;
    @JSONField(name = "block_num")
    private Integer blockNum;
    private Long gas;
    private String id;
    @JSONField(name = "api_result")
    private String apiResult;
    @JSONField(name = "acctual_fee") // api property not wrong!
    private Long acctualFee;
    private List<ReceiptEventInfo> events;
    @JSONField(name = "contract_withdraw")
    private JSONArray contractWithdraw;
    @JSONField(name = "contract_balances")
    private JSONArray contractBalances;
    @JSONField(name = "deposit_contract")
    private JSONArray depositContract;
    @JSONField(name = "deposit_to_address")
    private JSONArray depositToAddress;
    @JSONField(name = "transfer_fees")
    private JSONArray transferFees;
    @JSONField(name = "storage_changes")
    private JSONArray storageChanges;

    public Boolean getExecSucceed() {
        return execSucceed;
    }

    public void setExecSucceed(Boolean execSucceed) {
        this.execSucceed = execSucceed;
    }

    public String getInvoker() {
        return invoker;
    }

    public void setInvoker(String invoker) {
        this.invoker = invoker;
    }

    public Integer getOpNum() {
        return opNum;
    }

    public void setOpNum(Integer opNum) {
        this.opNum = opNum;
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public Integer getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(Integer blockNum) {
        this.blockNum = blockNum;
    }

    public Long getGas() {
        return gas;
    }

    public void setGas(Long gas) {
        this.gas = gas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiResult() {
        return apiResult;
    }

    public void setApiResult(String apiResult) {
        this.apiResult = apiResult;
    }

    public Long getAcctualFee() {
        return acctualFee;
    }

    public void setAcctualFee(Long acctualFee) {
        this.acctualFee = acctualFee;
    }

    public List<ReceiptEventInfo> getEvents() {
        return events;
    }

    public void setEvents(List<ReceiptEventInfo> events) {
        this.events = events;
    }

    public JSONArray getContractWithdraw() {
        return contractWithdraw;
    }

    public void setContractWithdraw(JSONArray contractWithdraw) {
        this.contractWithdraw = contractWithdraw;
    }

    public JSONArray getContractBalances() {
        return contractBalances;
    }

    public void setContractBalances(JSONArray contractBalances) {
        this.contractBalances = contractBalances;
    }

    public JSONArray getDepositContract() {
        return depositContract;
    }

    public void setDepositContract(JSONArray depositContract) {
        this.depositContract = depositContract;
    }

    public JSONArray getDepositToAddress() {
        return depositToAddress;
    }

    public void setDepositToAddress(JSONArray depositToAddress) {
        this.depositToAddress = depositToAddress;
    }

    public JSONArray getTransferFees() {
        return transferFees;
    }

    public void setTransferFees(JSONArray transferFees) {
        this.transferFees = transferFees;
    }

    public JSONArray getStorageChanges() {
        return storageChanges;
    }

    public void setStorageChanges(JSONArray storageChanges) {
        this.storageChanges = storageChanges;
    }
}
