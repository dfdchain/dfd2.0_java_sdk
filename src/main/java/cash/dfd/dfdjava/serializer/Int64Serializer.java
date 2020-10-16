package cash.dfd.dfdjava.serializer;

import cash.dfd.dfdjava.config.Constants;
import cash.dfd.dfdjava.exceptions.DeserializeException;
import cash.dfd.dfdjava.exceptions.SerializeException;

import java.io.ByteArrayOutputStream;

public class Int64Serializer implements ISerializer<Long> {
    private final boolean littleEndian;

    public Int64Serializer(boolean littleEndian) {
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
            value = ((long) (bytes[0] & 0xffl)) | (((long)(bytes[1]& 0xffl)) << 8) | (((long)(bytes[2]& 0xffl)) << 16) | (((long) (bytes[3]& 0xffl)) << 24)
            | (((long) (bytes[4]& 0xffl)) << 32) | (((long) (bytes[5]& 0xffl)) << 40) | (((long) (bytes[6]& 0xffl)) << 48) | (((long) (bytes[7]& 0xffl)) << 56);
        } else {
            value =((long) (bytes[7]& 0xffl)) | (((long)(bytes[6]& 0xffl)) << 8) | (((long)(bytes[5]& 0xffl)) << 16) | (((long) (bytes[4]& 0xffl)) << 24)
                    | (((long) (bytes[3]& 0xffl)) << 32) | (((long) (bytes[2]& 0xffl)) << 40) | (((long) (bytes[1]& 0xffl)) << 48) | (((long) (bytes[0]& 0xffl)) << 56);
        }
        return value;
    }

    public static Int64Serializer defaultInstance() {
        return new Int64Serializer(Constants.littleEndian);
    }
}
