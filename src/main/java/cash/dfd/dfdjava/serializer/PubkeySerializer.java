package cash.dfd.dfdjava.serializer;

import cash.dfd.dfdjava.crypto.CryptoUtil;
import cash.dfd.dfdjava.exceptions.DeserializeException;
import cash.dfd.dfdjava.exceptions.SerializeException;
import cash.dfd.dfdjava.pubkey.PubKeyBytes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PubkeySerializer implements ISerializer<PubKeyBytes> {
    @Override
    public byte[] serialize(PubKeyBytes instance) throws SerializeException {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bos.write(instance.getData());
            return bos.toByteArray();
        } catch (IOException e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public PubKeyBytes deserialize(byte[] bytes) throws DeserializeException {
        return null;
    }
}
