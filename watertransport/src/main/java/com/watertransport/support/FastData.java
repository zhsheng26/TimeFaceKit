package com.watertransport.support;

import com.watertransport.entity.UserObj;

import cn.timeface.timekit.util.storage.Remember;

/**
 * Created by zhangsheng on 2017/6/19.
 */

public class FastData extends Remember {
    public static void saveUserInfo(UserObj userObj) {
        putString("userId", userObj.getUserId());
        putString("nickName", userObj.getUserName());
        putString("avatar", userObj.getAvatar());
    }

    public static String getUserId() {
        return getString("userId", "");
    }

    public static int getUserRole() {
        return getInt("userRole", 1);
    }
}
