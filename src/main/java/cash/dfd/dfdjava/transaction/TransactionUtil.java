package cash.dfd.dfdjava.transaction;

import java.util.ArrayList;

public class TransactionUtil {
    public static Transaction defaultTransaction() {
        Transaction tx = new Transaction();
        tx.setRefBlockNum(0);
        tx.setRefBlockPrefix(0);
        tx.setExpiration("");
        tx.setOperations(new ArrayList<>());
        tx.setExtensions(new ArrayList<>());
        tx.setSignatures(new ArrayList<>());
        tx.setTransientExpiration(0);
        tx.setTransientOperations(new ArrayList<>());
        return tx;
    }
}
