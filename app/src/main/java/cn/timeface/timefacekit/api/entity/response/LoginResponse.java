package cn.timeface.timefacekit.api.entity.response;

import cn.timeface.timefacekit.api.entity.UserObj;
import cn.timeface.timekit.support.NetResponse;

/**
 * Created by zhangsheng on 2017/6/19.
 */

public class LoginResponse extends NetResponse{
    private UserObj userInfo;
    private int isFirst;
    private int fresh_sch;
    private int limitType;
    private String token;
    private int from;//-1时表示没有设置过密码

    public UserObj getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserObj userInfo) {
        this.userInfo = userInfo;
    }

    public int getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(int isFirst) {
        this.isFirst = isFirst;
    }

    public int getFresh_sch() {
        return fresh_sch;
    }

    public void setFresh_sch(int fresh_sch) {
        this.fresh_sch = fresh_sch;
    }

    public int getLimitType() {
        return limitType;
    }

    public void setLimitType(int limitType) {
        this.limitType = limitType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }
}
