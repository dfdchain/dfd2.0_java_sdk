package cash.dfd.dfdjava.asset;

import com.alibaba.fastjson.annotation.JSONField;

public class Asset {
    private long amount;
    @JSONField(name = "asset_id")
    private String assetId;

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }
}
