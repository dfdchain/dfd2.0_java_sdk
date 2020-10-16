package cash.dfd.dfdjava.transaction;

import java.util.ArrayList;

public class AccountOptionsUtil {
    public AccountOptions defaultAccountOptions() {
        AccountOptions accountOptions = new AccountOptions();
        accountOptions.setMemoKey("");
        accountOptions.setVotingAccount("1.2.5");
        accountOptions.setNumWitness(0);
        accountOptions.setNumCommittee(0);
        accountOptions.setVotes(new ArrayList<>());
        accountOptions.setMinerPledgePayBack((byte) 10);
        accountOptions.setExtensions(new ArrayList<>());
        return accountOptions;
    }
}
