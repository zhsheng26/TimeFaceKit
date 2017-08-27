package cn.timeface.timekit.support;

/**
 * Created by zhangsheng on 2016/12/28.
 */

public class NetResponse<T> {
    private String status;
    private boolean result;
    private int errorCode;
    private String message;
    private T page;

    public T getData() {
        return page;
    }

    public void setData(T data) {
        this.page = data;
    }

    public boolean success() {
        return "1".equals(status);
    }

    public String getState() {
        return status;
    }

    public void setState(String state) {
        this.status = state;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String info) {
        this.message = info;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
