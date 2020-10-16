package cash.dfd.dfdjava.transaction;

import cash.dfd.dfdjava.operation.IOperation;
import com.alibaba.fastjson.annotation.JSONField;

import java.beans.Transient;
import java.util.List;

public class Transaction {
    @JSONField(name = "ref_block_num")
    private int refBlockNum;
    @JSONField(name = "ref_block_prefix")
    private long refBlockPrefix;
    private String expiration;
    private List<List<Object>> operations;
    private List<Object> extensions;
    private List<String> signatures;
    private long transientExpiration;
    private List<IOperation> transientOperations;

    public int getRefBlockNum() {
        return refBlockNum;
    }

    public void setRefBlockNum(int refBlockNum) {
        this.refBlockNum = refBlockNum;
    }

    public long getRefBlockPrefix() {
        return refBlockPrefix;
    }

    public void setRefBlockPrefix(long refBlockPrefix) {
        this.refBlockPrefix = refBlockPrefix;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public List<List<Object>> getOperations() {
        return operations;
    }

    public void setOperations(List<List<Object>> operations) {
        this.operations = operations;
    }

    public List<Object> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<Object> extensions) {
        this.extensions = extensions;
    }

    public List<String> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<String> signatures) {
        this.signatures = signatures;
    }

    @Transient
    public long getTransientExpiration() {
        return transientExpiration;
    }

    public void setTransientExpiration(long transientExpiration) {
        this.transientExpiration = transientExpiration;
    }

    @Transient
    public List<IOperation> getTransientOperations() {
        return transientOperations;
    }

    public void setTransientOperations(List<IOperation> transientOperations) {
        this.transientOperations = transientOperations;
    }
}
