package cash.dfd.dfdjava.exceptions;

public class MessageEncodingException extends RuntimeException {
    public MessageEncodingException() {
    }

    public MessageEncodingException(String message) {
        super(message);
    }

    public MessageEncodingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageEncodingException(Throwable cause) {
        super(cause);
    }

    public MessageEncodingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
