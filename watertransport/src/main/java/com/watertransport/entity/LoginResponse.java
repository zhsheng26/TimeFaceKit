package com.watertransport.entity;

/**
 * Created by zhangsheng on 2017/8/19.
 */

public class LoginResponse {

    /**
     * result : true
     * message : 登录成功!
     * token : 48-ad9c2152b6e842459cc1b47b4a284ddf
     * userId : 48
     * page :
     */

    private boolean result;
    private String message;
    private String token;
    private String userId;
    private String page;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
