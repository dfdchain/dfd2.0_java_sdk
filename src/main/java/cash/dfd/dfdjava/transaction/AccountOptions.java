package cash.dfd.dfdjava.transaction;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class AccountOptions {
    @JSONField(name = "memo_key")
    private String memoKey;
    @JSONField(name = "voting_account")
    private String votingAccount;
    @JSONField(name = "num_witness")
    private long numWitness;
    @JSONField(name = "num_committee")
    private long numCommittee;
    @JSONField(name = "votes")
    private List<Object> votes;
    @JSONField(name = "miner_pledge_pay_back")
    private byte minerPledgePayBack;
    @JSONField(name = "extensions")
    private List<Object> extensions;

    public String getMemoKey() {
        return memoKey;
    }

    public void setMemoKey(String memoKey) {
        this.memoKey = memoKey;
    }

    public String getVotingAccount() {
        return votingAccount;
    }

    public void setVotingAccount(String votingAccount) {
        this.votingAccount = votingAccount;
    }

    public long getNumWitness() {
        return numWitness;
    }

    public void setNumWitness(long numWitness) {
        this.numWitness = numWitness;
    }

    public long getNumCommittee() {
        return numCommittee;
    }

    public void setNumCommittee(long numCommittee) {
        this.numCommittee = numCommittee;
    }

    public List<Object> getVotes() {
        return votes;
    }

    public void setVotes(List<Object> votes) {
        this.votes = votes;
    }

    public byte getMinerPledgePayBack() {
        return minerPledgePayBack;
    }

    public void setMinerPledgePayBack(byte minerPledgePayBack) {
        this.minerPledgePayBack = minerPledgePayBack;
    }

    public List<Object> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<Object> extensions) {
        this.extensions = extensions;
    }
}
