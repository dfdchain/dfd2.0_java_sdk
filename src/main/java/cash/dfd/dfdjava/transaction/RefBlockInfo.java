package cash.dfd.dfdjava.transaction;

import cash.dfd.dfdjava.exceptions.TransactionException;

public class RefBlockInfo {
    private int refBlockNum;
    private long refBlockPrefix;

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

    public static RefBlockInfo decodeFromInfoString(String info) throws TransactionException {
        String[] refInfoSplitted = info.split(",");
        if(refInfoSplitted.length!=2) {
            throw new TransactionException("in GetRefblockInfo function, get refblockinfo failed");
        }
        try {
            String refBlockNumStr = refInfoSplitted[0];
            String refBlockPrefixStr = refInfoSplitted[1];
            int refBlockNum = Integer.parseInt(refBlockNumStr);
            long refBlockPrefix = Long.parseLong(refBlockPrefixStr);
            RefBlockInfo refBlockInfo = new RefBlockInfo();
            refBlockInfo.setRefBlockNum(refBlockNum);
            refBlockInfo.setRefBlockPrefix(refBlockPrefix);
            return refBlockInfo;
        } catch (Exception e) {
            throw new TransactionException(e);
        }
    }
}
