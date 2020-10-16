package cash.dfd.dfdjava.exceptions;

public class DeserializeException extends Exception {
    public DeserializeException() {
    }

    public DeserializeException(String message) {
        super(message);
    }

    public DeserializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeserializeException(Throwable cause) {
        super(cause);
    }

    public DeserializeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
