package cash.dfd.dfdjava.transaction;

import com.alibaba.fastjson.annotation.JSONField;

import java.beans.Transient;
import java.util.List;

public class Authority {
    @JSONField(name = "weight_threshold")
    private long weightThreshold;
    @JSONField(name = "account_auths")
    private List<Object> accountAuths;
    @JSONField(name = "key_auths")
    private List<List<Object>> keyAuths;
    private String transientKeyAuths;

    public long getWeightThreshold() {
        return weightThreshold;
    }

    public void setWeightThreshold(long weightThreshold) {
        this.weightThreshold = weightThreshold;
    }

    public List<Object> getAccountAuths() {
        return accountAuths;
    }

    public void setAccountAuths(List<Object> accountAuths) {
        this.accountAuths = accountAuths;
    }

    public List<List<Object>> getKeyAuths() {
        return keyAuths;
    }

    public void setKeyAuths(List<List<Object>> keyAuths) {
        this.keyAuths = keyAuths;
    }

    @Transient
    public String getTransientKeyAuths() {
        return transientKeyAuths;
    }

    public void setTransientKeyAuths(String transientKeyAuths) {
        this.transientKeyAuths = transientKeyAuths;
    }
}
