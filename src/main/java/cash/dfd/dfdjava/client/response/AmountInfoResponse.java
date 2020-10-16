package cash.dfd.dfdjava.client.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class AmountInfoResponse {
    private BigInteger amount;
    @JSONField(name = "asset_id")
    private String assetId;

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }
}
