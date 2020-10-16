package cash.dfd.dfdjava.crypto.exceptions;

public class Base58DecodeException extends Exception {
    public Base58DecodeException() {
    }

    public Base58DecodeException(String message) {
        super(message);
    }

    public Base58DecodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public Base58DecodeException(Throwable cause) {
        super(cause);
    }

    public Base58DecodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
