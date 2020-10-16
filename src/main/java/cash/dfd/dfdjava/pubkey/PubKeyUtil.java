package cash.dfd.dfdjava.pubkey;

import cash.dfd.dfdjava.address.Address;
import cash.dfd.dfdjava.address.AddressVersion;
import cash.dfd.dfdjava.crypto.Base58;
import cash.dfd.dfdjava.crypto.CryptoUtil;
import cash.dfd.dfdjava.crypto.exceptions.Base58DecodeException;
import cash.dfd.dfdjava.exceptions.PubKeyInvalidException;
import cash.dfd.dfdjava.utils.Numeric;

public class PubKeyUtil {
    public static final String PUBKEY_STRING_PREFIX = AddressVersion.VersionPrefix;

    public static final int PUBKEY_BYTES_SIZE = 33;

    public static byte[] getPubKeyBytes(String pubKeyStr) throws PubKeyInvalidException {
        if (pubKeyStr == null || pubKeyStr.length() <= PUBKEY_STRING_PREFIX.length()) {
            throw new PubKeyInvalidException("invalid pubKeyStr prefix");
        }
        if(!pubKeyStr.startsWith(PUBKEY_STRING_PREFIX)) {
            // 非base58格式的公钥，尝试用hex格式解析
            try {
                return Numeric.hexStringToByteArray(pubKeyStr);
            } catch (Exception e) {
                throw new PubKeyInvalidException(e);
            }
        }
        String base58Addr = pubKeyStr.substring(PUBKEY_STRING_PREFIX.length());
        try {
            byte[] addrBytes = Base58.decode(base58Addr);
            byte[] addrBytesWithoutChecksum = CryptoUtil.bytesSlice(addrBytes, 0, addrBytes.length-4);
            return addrBytesWithoutChecksum;
        } catch (Base58DecodeException e) {
            throw new PubKeyInvalidException(e);
        }
    }

    public static String base58PubKeyToHex(String pubKeyBase58Str) throws PubKeyInvalidException {
        byte[] pubKeyBytes = getPubKeyBytes(pubKeyBase58Str);
        return Numeric.toHexStringNoPrefix(pubKeyBytes);
    }
}
