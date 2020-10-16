package cash.dfd.dfdjava.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecureRandomUtil {
    public static SecureRandom getSha1Random() throws NoSuchAlgorithmException {
        return SecureRandom.getInstance("SHA1PRNG");
    }
}
