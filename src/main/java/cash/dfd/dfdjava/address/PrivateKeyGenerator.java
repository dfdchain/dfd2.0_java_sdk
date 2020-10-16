package cash.dfd.dfdjava.address;

import cash.dfd.dfdjava.exceptions.CryptoException;
import cash.dfd.dfdjava.utils.SecureRandomUtil;
import org.bitcoinj.core.ECKey;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.util.PrivateKeyFactory;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PrivateKeyGenerator {
    public static ECKey generate() throws CryptoException {
        try {
            ECKeyGenerationParameters keyGenParams = new ECKeyGenerationParameters(ECKey.CURVE, SecureRandomUtil.getSha1Random());
            ECKeyPairGenerator generator = new ECKeyPairGenerator();
            generator.init(keyGenParams);
            AsymmetricCipherKeyPair keyPair = generator.generateKeyPair();
            ECPrivateKeyParameters ecPrivateKeyParameters = (ECPrivateKeyParameters) keyPair.getPrivate();
            return ECKey.fromPrivate(ecPrivateKeyParameters.getD());
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e);
        }
    }
}
