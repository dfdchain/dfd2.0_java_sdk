package cash.dfd.dfdjava.client;

public class NodeRpcResponse {
    private Object result;
    private String jsonrpc;
    private Integer id;
    private RpcErrorInfo error;

    public static NodeRpcResponse ofError(int id, int errorCode, String message, Object data) {
        NodeRpcResponse res = new NodeRpcResponse();
        res.setId(id);
        res.setJsonrpc("2.0");
        res.setError(new RpcErrorInfo(errorCode, message, data));
        return res;
    }

    public static NodeRpcResponse ofResult(int id, Object result) {
        NodeRpcResponse res = new NodeRpcResponse();
        res.setId(id);
        res.setJsonrpc("2.0");
        res.setResult(result);
        return res;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RpcErrorInfo getError() {
        return error;
    }

    public void setError(RpcErrorInfo error) {
        this.error = error;
    }
}
