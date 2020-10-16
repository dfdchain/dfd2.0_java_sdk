package cash.dfd.dfdjava.operation;

import cash.dfd.dfdjava.asset.Asset;
import cash.dfd.dfdjava.transaction.Memo;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class TransferOperation implements IOperation {
    private Asset fee;
    @JSONField(name = "guarantee_id")
    private String guaranteeId;
    private String from;
    private String to;
    @JSONField(name = "from_addr")
    private String fromAddr;
    @JSONField(name = "to_addr")
    private String toAddr;
    private Asset amount;
    private Memo memo;
    private List<Object> extensions;

    public Asset getFee() {
        return fee;
    }

    public void setFee(Asset fee) {
        this.fee = fee;
    }

    public String getGuaranteeId() {
        return guaranteeId;
    }

    public void setGuaranteeId(String guaranteeId) {
        this.guaranteeId = guaranteeId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFromAddr() {
        return fromAddr;
    }

    public void setFromAddr(String fromAddr) {
        this.fromAddr = fromAddr;
    }

    public String getToAddr() {
        return toAddr;
    }

    public void setToAddr(String toAddr) {
        this.toAddr = toAddr;
    }

    public Asset getAmount() {
        return amount;
    }

    public void setAmount(Asset amount) {
        this.amount = amount;
    }

    public Memo getMemo() {
        return memo;
    }

    public void setMemo(Memo memo) {
        this.memo = memo;
    }

    public List<Object> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<Object> extensions) {
        this.extensions = extensions;
    }

    @Override
    public int getOperationType() {
        return OperationTypes.TRANSFER_OPERATION;
    }
}
