package cash.dfd.dfdjava.serializer;

import cash.dfd.dfdjava.config.Constants;
import cash.dfd.dfdjava.exceptions.DeserializeException;
import cash.dfd.dfdjava.exceptions.SerializeException;

import java.io.ByteArrayOutputStream;

public class Uint32Serializer implements ISerializer<Long> {
    private final boolean littleEndian;

    public Uint32Serializer(boolean littleEndian) {
        this.littleEndian = littleEndian;
    }

    @Override
    public byte[] serialize(Long instance) throws SerializeException {
        long intValue = instance;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        if(littleEndian) {
            bos.write((byte)intValue);
            bos.write((byte) (intValue >> 8));
            bos.write((byte) (intValue >> 16));
            bos.write((byte) (intValue >> 24));
        } else {
            bos.write((byte) (intValue >> 24));
            bos.write((byte) (intValue >> 16));
            bos.write((byte) (intValue >> 8));
            bos.write((byte) intValue);
        }
        return bos.toByteArray();
    }

    @Override
    public Long deserialize(byte[] bytes) throws DeserializeException {
        long value;
        if(littleEndian) {
            value = ((long) (bytes[0]&0xffL)) | (((long)(bytes[1]&0xffL)) << 8) | (((long)(bytes[2]&0xffL)) << 16) | (((long) (bytes[3]&0xffL)) << 24);
        } else {
            value = ((long) (bytes[3]&0xffL)) | (((long) (bytes[2]&0xffL)) << 8) | (((long)(bytes[1]&0xffL)) << 16) | (((long) (bytes[0]&0xffL)) << 24);
        }
        return value;
    }

    public static Uint32Serializer defaultInstance() {
        return new Uint32Serializer(Constants.littleEndian);
    }
}
