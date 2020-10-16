package cash.dfd.dfdjava.client;

public class RpcErrorInfo {
    public static final int INTERNAL_ERROR_CODE = 10001;
    public static final int TIMEOUT_ERROR_CODE = 10002;

    private Integer code;
    private String message;
    private Object data;

    public RpcErrorInfo() {
    }

    public RpcErrorInfo(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
