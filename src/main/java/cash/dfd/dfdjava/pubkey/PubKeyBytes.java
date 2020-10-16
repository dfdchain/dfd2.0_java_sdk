package cash.dfd.dfdjava.pubkey;

public class PubKeyBytes {
    private byte[] data; // length 33

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public PubKeyBytes() {
    }

    public PubKeyBytes(byte[] data) {
        this.data = data;
    }
}
