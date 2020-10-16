package cash.dfd.dfdjava.utils;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;

public class PrivateKeyUtil {
    public static String privateKeyToWif(ECKey ecKey) {
        return ecKey.getPrivateKeyAsWiF(NetworkParameters.fromID(NetworkParameters.ID_MAINNET));
    }
}
