package cash.dfd.dfdjava.serializer;

import cash.dfd.dfdjava.exceptions.DeserializeException;
import cash.dfd.dfdjava.exceptions.SerializeException;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.io.IOException;

public class SignedVarIntSerializer implements ISerializer<Integer> {
    @Override
    public byte[] serialize(Integer instance) throws SerializeException {
        ByteArrayDataOutput bao = ByteStreams.newDataOutput();
        try {
            Varint.writeSignedVarInt(instance, bao);
            return bao.toByteArray();
        } catch (IOException e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public Integer deserialize(byte[] bytes) throws DeserializeException {
        return Varint.readSignedVarInt(bytes);
    }

    public static SignedVarIntSerializer defaultInstance() {
        return new SignedVarIntSerializer();
    }
}
