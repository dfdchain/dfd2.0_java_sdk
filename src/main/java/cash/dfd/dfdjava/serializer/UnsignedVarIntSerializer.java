package cash.dfd.dfdjava.serializer;

import cash.dfd.dfdjava.exceptions.DeserializeException;
import cash.dfd.dfdjava.exceptions.SerializeException;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.io.IOException;

public class UnsignedVarIntSerializer implements ISerializer<Integer> {
    @Override
    public byte[] serialize(Integer instance) throws SerializeException {
        ByteArrayDataOutput bao = ByteStreams.newDataOutput();
        try {
            Varint.writeUnsignedVarInt(instance, bao);
            return bao.toByteArray();
        } catch (IOException e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public Integer deserialize(byte[] bytes) throws DeserializeException {
        return Varint.readUnsignedVarInt(bytes);
    }

    public static UnsignedVarIntSerializer defaultInstance() {
        return new UnsignedVarIntSerializer();
    }
}
