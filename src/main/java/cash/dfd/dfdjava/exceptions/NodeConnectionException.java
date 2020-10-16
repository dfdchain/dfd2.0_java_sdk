package cash.dfd.dfdjava.exceptions;

public class NodeConnectionException extends Exception {
    public NodeConnectionException() {
    }

    public NodeConnectionException(String message) {
        super(message);
    }

    public NodeConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NodeConnectionException(Throwable cause) {
        super(cause);
    }

    public NodeConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
