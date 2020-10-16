package cash.dfd.dfdjava.serializer;

import cash.dfd.dfdjava.config.Constants;
import cash.dfd.dfdjava.exceptions.DeserializeException;
import cash.dfd.dfdjava.exceptions.SerializeException;

import java.io.ByteArrayOutputStream;

public class Uint16Serializer implements ISerializer<Integer> {
    private final boolean littleEndian;

    public Uint16Serializer(boolean littleEndian) {
        this.littleEndian = littleEndian;
    }

    @Override
    public byte[] serialize(Integer instance) throws SerializeException {
        int intValue = instance;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        if(littleEndian) {
            bos.write((byte) intValue);
            bos.write((byte) (intValue >> 8));
        } else {
            bos.write((byte) (intValue >> 8));
            bos.write((byte) intValue);
        }
        return bos.toByteArray();
    }

    @Override
    public Integer deserialize(byte[] bytes) throws DeserializeException {
        int value;
        if(littleEndian) {
            value = ((int) (bytes[0]&0xffL)) | (((int)(bytes[1]&0xffL)) << 8);
        } else {
            value = ((int) (bytes[1]&0xffL)) | (((int) (bytes[0]&0xffL)) << 8);
        }
        return value;
    }

    public static Uint16Serializer defaultInstance() {
        return new Uint16Serializer(Constants.littleEndian);
    }
}
