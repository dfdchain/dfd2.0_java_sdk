package cash.dfd.dfdjava.client.response;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

public class TransactionResponse {
    private String id;
    @JSONField(name = "block_num")
    private Long blockNum;
    @JSONField(name = "trx_num")
    private Integer trxNum;
    private JSONObject trx;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(Long blockNum) {
        this.blockNum = blockNum;
    }

    public Integer getTrxNum() {
        return trxNum;
    }

    public void setTrxNum(Integer trxNum) {
        this.trxNum = trxNum;
    }

    public JSONObject getTrx() {
        return trx;
    }

    public void setTrx(JSONObject trx) {
        this.trx = trx;
    }
}
