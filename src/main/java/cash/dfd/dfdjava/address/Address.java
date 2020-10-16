package cash.dfd.dfdjava.address;

import cash.dfd.dfdjava.crypto.Base58;
import cash.dfd.dfdjava.crypto.CryptoUtil;
import cash.dfd.dfdjava.exceptions.AddressException;
import cash.dfd.dfdjava.utils.Numeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Address {
    private static final Logger log = LoggerFactory.getLogger(Address.class);

    public static final String ADDRESS_PREFIX = AddressVersion.VersionPrefix;

    private byte[] addy;
    private byte version;

    public byte[] getAddy() {
        return addy;
    }

    public byte[] getAddyWithVersion() {
        return CryptoUtil.bytesMerge(CryptoUtil.singleBytes(version), addy);
    }

    public void setAddy(byte[] addy) {
        this.addy = addy;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public Address(byte[] addy, byte version) {
        this.addy = addy;
        this.version = version;
    }

    public Address(byte[] addy) {
        this(addy, AddressVersion.NORMAL);
    }

    public static Address fromPubKey(byte[] pubKeyBytes, byte version) {
        byte[] pubKeySha256 = CryptoUtil.sha256(pubKeyBytes);
        byte[] addrBytes = CryptoUtil.ripemd160(pubKeySha256);
        return new Address(addrBytes, version);
    }

    public static Address fromBuffer(byte[] buffer) {
        return new Address(buffer);
    }

    public static Address fromString(String addrString, String addrPrefix) throws AddressException {
        if(addrString==null) {
            throw new AddressException("invalid DFD address length");
        }

        String addyStr = addrString;
        try {
            byte[] addyBytes = Base58.decode(addyStr);
            if(addyBytes.length<=4) {
                throw new AddressException("invalid address checksum");
            }
            byte[] checksumBytes = CryptoUtil.bytesSlice(addyBytes, addyBytes.length-4, addyBytes.length);
            byte[] newAddyBytes = CryptoUtil.bytesSlice(addyBytes, 0, addyBytes.length-4);

            byte[] bytes = CryptoUtil.sha256(newAddyBytes);
            byte[] newChecksumBytes = CryptoUtil.sha256(bytes);

            byte[] newChecksumBytesFirst4Bytes = CryptoUtil.bytesSlice(newChecksumBytes, 0, 4);
            boolean isEqualChecksum = CryptoUtil.deepEqualsOfBytes(checksumBytes, newChecksumBytesFirst4Bytes);
            if(!isEqualChecksum) {
                log.debug("expect checksum {} got {}", Numeric.toHexStringNoPrefix(checksumBytes), Numeric.toHexStringNoPrefix(newChecksumBytesFirst4Bytes));
                throw new AddressException("invalid address checksum");
            }
            byte addrVersion = newAddyBytes[0];
            byte[] addrBytesWithoutVersion = CryptoUtil.bytesSlice(newAddyBytes, 1, newAddyBytes.length);
            return new Address(addrBytesWithoutVersion, addrVersion);
        } catch (Exception e) {
            throw new AddressException(e);
        }
    }

    public byte[] toBuffer() {
        byte[] allBuffer = new byte[1+addy.length];
        allBuffer[0] = version;
        for(int i=0;i<addy.length;i++) {
            allBuffer[i+1] = addy[i];
        }
        return allBuffer;
    }

    public String getValue(String prefix) {
        try {
            byte[] allBuffer = this.toBuffer();
            byte[] bytes = CryptoUtil.sha256(allBuffer);
            byte[] checksum = CryptoUtil.sha256(bytes);
            byte[] addyWithChecksum = new byte[allBuffer.length+4]; // addyWithChecksum = allBuffer = checksum[0:4]
            for(int i=0;i<allBuffer.length;i++) {
                addyWithChecksum[i] = allBuffer[i];
            }
            for(int i=0;i<4;i++) {
                addyWithChecksum[allBuffer.length+i] = checksum[i];
            }
            return Base58.encode(addyWithChecksum);
        } catch (Exception e) {
            log.debug("Address.getValue error: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return getValue(ADDRESS_PREFIX);
    }
}
