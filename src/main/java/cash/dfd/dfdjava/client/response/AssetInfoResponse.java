package cash.dfd.dfdjava.client.response;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

public class AssetInfoResponse {
    private String id;
    private String symbol;
    private Integer precision;
    private String issuer;
    private JSONObject options;
    @JSONField(name = "dynamic_asset_data_id")
    private String dynamicAssetDataId;
    private JSONArray feeds;
    private List<String> publishers;
    @JSONField(name = "current_feed")
    private JSONObject currentFeed;
    @JSONField(name = "current_feed_publication_time")
    private Date currentFeedPublicationTime;
    @JSONField(name = "allow_withdraw_deposit")
    private Boolean allowWithdrawDeposit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public JSONObject getOptions() {
        return options;
    }

    public void setOptions(JSONObject options) {
        this.options = options;
    }

    public String getDynamicAssetDataId() {
        return dynamicAssetDataId;
    }

    public void setDynamicAssetDataId(String dynamicAssetDataId) {
        this.dynamicAssetDataId = dynamicAssetDataId;
    }

    public JSONArray getFeeds() {
        return feeds;
    }

    public void setFeeds(JSONArray feeds) {
        this.feeds = feeds;
    }

    public List<String> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<String> publishers) {
        this.publishers = publishers;
    }

    public JSONObject getCurrentFeed() {
        return currentFeed;
    }

    public void setCurrentFeed(JSONObject currentFeed) {
        this.currentFeed = currentFeed;
    }

    public Date getCurrentFeedPublicationTime() {
        return currentFeedPublicationTime;
    }

    public void setCurrentFeedPublicationTime(Date currentFeedPublicationTime) {
        this.currentFeedPublicationTime = currentFeedPublicationTime;
    }

    public Boolean getAllowWithdrawDeposit() {
        return allowWithdrawDeposit;
    }

    public void setAllowWithdrawDeposit(Boolean allowWithdrawDeposit) {
        this.allowWithdrawDeposit = allowWithdrawDeposit;
    }
}
