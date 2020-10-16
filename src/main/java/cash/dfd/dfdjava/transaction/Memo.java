package cash.dfd.dfdjava.transaction;

import java.beans.Transient;

public class Memo {
    private String from; // public_key_type  33
    private String to; // public_key_type  33
    private long nonce;
    private String message;

    private boolean empty;
    private String transientMessage;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Transient
    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    @Transient
    public String getTransientMessage() {
        return transientMessage;
    }

    public void setTransientMessage(String transientMessage) {
        this.transientMessage = transientMessage;
    }
}
