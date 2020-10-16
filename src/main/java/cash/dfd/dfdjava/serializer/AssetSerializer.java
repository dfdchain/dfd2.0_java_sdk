package cash.dfd.dfdjava.serializer;

import cash.dfd.dfdjava.asset.Asset;
import cash.dfd.dfdjava.crypto.CryptoUtil;
import cash.dfd.dfdjava.exceptions.DeserializeException;
import cash.dfd.dfdjava.exceptions.SerializeException;
import cash.dfd.dfdjava.utils.IdUtil;

public class AssetSerializer implements ISerializer<Asset> {
    @Override
    public byte[] serialize(Asset instance) throws SerializeException {
        Int64Serializer int64Serializer = Int64Serializer.defaultInstance();
        byte[] amountBytes = int64Serializer.serialize(instance.getAmount());
        int assetIdValue = IdUtil.getId(instance.getAssetId());
        UnsignedVarIntSerializer unsignedVarIntSerializer = new UnsignedVarIntSerializer();
        byte[] assetIdBytes = unsignedVarIntSerializer.serialize(assetIdValue);
        return CryptoUtil.bytesMerge(amountBytes, assetIdBytes);
    }

    @Override
    public Asset deserialize(byte[] bytes) throws DeserializeException {
        return null;
    }
}
