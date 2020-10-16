package cash.dfd.dfdjava.serializer;

import cash.dfd.dfdjava.crypto.CryptoUtil;
import cash.dfd.dfdjava.exceptions.DeserializeException;
import cash.dfd.dfdjava.exceptions.PubKeyInvalidException;
import cash.dfd.dfdjava.exceptions.SerializeException;
import cash.dfd.dfdjava.pubkey.PubKeyUtil;
import cash.dfd.dfdjava.transaction.AccountOptions;

public class AccountOptionsSerializer implements ISerializer<AccountOptions> {
    @Override
    public byte[] serialize(AccountOptions instance) throws SerializeException {
        try {
            Uint16Serializer uint16Serializer = Uint16Serializer.defaultInstance();
            byte[] memoKeyBytes = PubKeyUtil.getPubKeyBytes(instance.getMemoKey());
            byte[] five = CryptoUtil.singleBytes((byte) 5);
            byte[] zero1 = uint16Serializer.serialize(0);
            byte[] zero2 = uint16Serializer.serialize(0);
            byte[] zero3 = CryptoUtil.singleBytes((byte) 0);
            byte[] ten = CryptoUtil.singleBytes((byte) 10);
            byte[] zero4 = CryptoUtil.singleBytes((byte) 0);
            return CryptoUtil.bytesMerge(memoKeyBytes, five, zero1, zero2, zero3, ten, zero4);
        } catch (PubKeyInvalidException e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public AccountOptions deserialize(byte[] bytes) throws DeserializeException {
        return null;
    }
}
