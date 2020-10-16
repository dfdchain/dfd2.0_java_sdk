package cash.dfd.dfdjava.exceptions;

public class PubKeyInvalidException extends Exception {
    public PubKeyInvalidException() {
    }

    public PubKeyInvalidException(String message) {
        super(message);
    }

    public PubKeyInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public PubKeyInvalidException(Throwable cause) {
        super(cause);
    }

    public PubKeyInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
