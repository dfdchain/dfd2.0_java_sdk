package cash.dfd.dfdjava.serializer;

import cash.dfd.dfdjava.config.Constants;
import cash.dfd.dfdjava.exceptions.DeserializeException;
import cash.dfd.dfdjava.exceptions.SerializeException;

import java.io.ByteArrayOutputStream;

public class Uint64Serializer implements ISerializer<Long> {
    private final boolean littleEndian;

    public Uint64Serializer(boolean littleEndian) {
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
            bos.write((byte) (intValue >> 32));
            bos.write((byte) (intValue >> 40));
            bos.write((byte) (intValue >> 48));
            bos.write((byte) (intValue >> 56));
        } else {
            bos.write((byte) (intValue >> 56));
            bos.write((byte) (intValue >> 48));
            bos.write((byte) (intValue >> 40));
            bos.write((byte) (intValue >> 32));
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
            value = ((long) (bytes[0]&0xffL)) | (((long)(bytes[1]&0xffL)) << 8) | (((long)(bytes[2]&0xffL)) << 16) | (((long) (bytes[3]&0xffL)) << 24)
                    | (((long) (bytes[4]&0xffL)) << 32) | (((long) (bytes[5]&0xffL)) << 40) | (((long) (bytes[6]&0xffL)) << 48) | (((long) (bytes[7]&0xffL)) << 56);
        } else {
            value =((long) (bytes[7])&0xffL) | (((long)(bytes[6]&0xffL)) << 8) | (((long)(bytes[5]&0xffL)) << 16) | (((long) (bytes[4]&0xffL)) << 24)
                    | (((long) (bytes[3]&0xffL)) << 32) | (((long) (bytes[2]&0xffL)) << 40) | (((long) (bytes[1]&0xffL)) << 48) | (((long) (bytes[0]&0xffL)) << 56);
        }
        return value;
    }

    public static Uint64Serializer defaultInstance() {
        return new Uint64Serializer(Constants.littleEndian);
    }
}
