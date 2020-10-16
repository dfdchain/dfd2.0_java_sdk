package cash.dfd.dfdjava.utils;

public class IdUtil {
    public static int getId(String idStr) {
        String[] idSlice = idStr.split("[.]");
        if(idSlice.length != 3) {
            throw new RuntimeException("get id from x.y.z id string error");
        }
        return Integer.parseInt(idSlice[2]);
    }
}
