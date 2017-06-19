package cn.timeface.timefacekit.api.entity;

/**
 * Created by zhangsheng on 2017/6/19.
 */

public class UserObj {
    private String userId; // 用户Id
    private String nickName; // 用户名称
    private String realName; // 用户真名
    private String avatar; // 用户头像
    private int type = 0;
    private int from;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "UserObj{" +
                "userId='" + userId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", realName='" + realName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", type=" + type +
                ", from=" + from +
                '}';
    }
}
