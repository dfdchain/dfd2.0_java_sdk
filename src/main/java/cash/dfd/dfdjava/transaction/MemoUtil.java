package cash.dfd.dfdjava.transaction;

import cash.dfd.dfdjava.address.Address;

public class MemoUtil {
    public static Memo defaultMemo() {
        Memo memo = new Memo();
        memo.setFrom(Address.ADDRESS_PREFIX + "1111111111111111111111111111111114T1Anm");
        memo.setTo(Address.ADDRESS_PREFIX + "1111111111111111111111111111111114T1Anm");
        memo.setNonce(0L);
        memo.setMessage("");
        memo.setEmpty(true);
        memo.setTransientMessage("");
        return memo;
    }
}
