package cash.dfd.dfdjava.utils;

import cash.dfd.dfdjava.crypto.CryptoUtil;
import cash.dfd.dfdjava.exceptions.CryptoException;
import org.bitcoinj.core.*;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.math.ec.ECPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.SecureRandom;

public class SignatureUtil {
    private static final Logger log = LoggerFactory.getLogger(SignatureUtil.class);

    //    public static ECKey getPrivateKey(String privateKeyHex) throws CryptoException {
//        byte[] privateKeyBytes = Numeric.hexStringToByteArray(privateKeyHex);
//        log.info("bytes size: {}", privateKeyBytes.length);
//        ECKey key = ECKey.fromPrivate(privateKeyBytes);
//        return key;
//    }
    public static ECKey getPrivateKeyfromWif(String wifStr) {
        DumpedPrivateKey dumpedPrivateKey = DumpedPrivateKey.fromBase58(NetworkParameters.fromID(NetworkParameters.ID_MAINNET), wifStr);
        return dumpedPrivateKey.getKey();
    }

    private static ECPoint recoverPubKey(Sha256Hash e, ECKey.ECDSASignature signature, int i) {
        ECKey recovered = ECKey.recoverFromSignature(i, signature, e, true);
        if(recovered == null) {
            return null;
        }
        return recovered.getPubKeyPoint();
    }

    private static int calcPubKeyRecoveryParam(Sha256Hash e, ECKey.ECDSASignature signature, ECPoint q) throws CryptoException {
        for(int i=0;i<4;i++) {
            ECPoint qPrime = recoverPubKey(e, signature, i);
            if(qPrime == null) {
                continue;
            }
            if(qPrime.equals(q)) {
                return i;
            }
        }
        throw new CryptoException("Unable to find valid recovery factor");
    }

    private static ECKey.ECDSASignature signWithNonce(ECKey privateKey, byte[] hash, int nonce) {
        Sha256Hash e = Sha256Hash.wrap(hash);
//        ECDSASigner signer = new ECDSASigner(new HMacDSAKCalculator(new SHA256Digest()));
        ECDSASigner signer = new ECDSASigner(); // 默认是随机k值发生器，可以不需要nonce产生不同的k值
        ECPrivateKeyParameters privKey = new ECPrivateKeyParameters(privateKey.getPrivKey(), ECKey.CURVE);

        signer.init(true, privKey);

        BigInteger[] components = signer.generateSignature(hash);
        ECKey.ECDSASignature signature = (new ECKey.ECDSASignature(components[0], components[1])).toCanonicalised();
        return signature;
    }

    public static byte[] getSignature(String wifStr, byte[] toSignBytes) throws CryptoException {
        ECKey privateKey = getPrivateKeyfromWif(wifStr);
        try {
            byte[] txDigest = CryptoUtil.sha256(toSignBytes);
            log.debug("tx digest: {}", Numeric.toHexStringNoPrefix(txDigest));
            log.info("tx digest: {}", Numeric.toHexStringNoPrefix(txDigest));
            byte[] hash = txDigest;
            Sha256Hash e = Sha256Hash.wrap(hash);
            int nonce = 0;
            ECKey.ECDSASignature ecSignature;
            int recId;
            while(true) {
                ecSignature = signWithNonce(privateKey, hash, nonce);
                nonce++;
                byte[] der = ecSignature.encodeToDER();

                byte lenR = der[3];
                byte lenS = der[5+lenR];
                if(lenR == 32 && lenS == 32) {
                    recId = privateKey.findRecoveryId(e, ecSignature);
                    int headerByte = recId + 27 + 4;
                    byte[] sigData = new byte[65];
                    sigData[0] = (byte)headerByte;
                    System.arraycopy(Utils.bigIntegerToBytes(ecSignature.r, 32), 0, sigData, 1, 32);
                    System.arraycopy(Utils.bigIntegerToBytes(ecSignature.s, 32), 0, sigData, 33, 32);
                    return sigData;
                }
                if(nonce % 10 == 0) {
                    log.warn("WARN: {} attempts to find canonical signature", nonce);
                }
                if(nonce > 10000) {
                    throw new CryptoException(nonce + " attempts to find canonical signature");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CryptoException(e);
        }
    }
}
