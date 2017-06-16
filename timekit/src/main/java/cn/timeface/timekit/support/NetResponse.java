package cn.timeface.timekit.support;

/**
 * Created by zhangsheng on 2016/12/28.
 */

public class NetResponse<T>{
    private String status;
    private int errorCode;
    private String info;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean success(){
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
