package com.watertransport.support;

import com.watertransport.entity.UserObj;

import cn.timeface.timekit.util.storage.Remember;

/**
 * Created by zhangsheng on 2017/6/19.
 */

public class FastData extends Remember {

    public static final String USER_ID = "userId";
    public static final String USER_ROLE = "userRole";
    public static final String REAL_NAME = "realName";
    public static final String LOGIN_NAME = "loginName";
    public static final String TOKEN = "token";
    public static final String MOBILE = "mobile";
    private static int phone;

    public static void saveUserInfo(UserObj userObj) {
        if (userObj == null) {
            putString(USER_ID, "");
            putInt(USER_ROLE, 0);
            putString(REAL_NAME, "");
            putString(LOGIN_NAME, "");
            putString(TOKEN, "");
            putString(MOBILE, "");
            return;
        }
        putString(USER_ID, userObj.getId());
        putInt(USER_ROLE, Integer.parseInt(userObj.getUserType()));
        putString(REAL_NAME, userObj.getName());
        putString(LOGIN_NAME, userObj.getLoginName());
        putString(MOBILE, userObj.getMobile());
    }

    public static String getUserId() {
        return getString(USER_ID, "");
    }

    public static int getUserRole() {
        return getInt(USER_ROLE, WtConstant.USER_ROLE_BOAT);
    }

    public static String getRealName() {
        return getString(REAL_NAME, "");
    }

    public static String getLoginName() {
        return getString(LOGIN_NAME, "");
    }

    public static String getToken() {
        return getString(TOKEN, "");
    }

    public static void saveUserId(String userId) {
        putString(USER_ID, userId);
    }

    public static void saveToken(String token) {
        putString(TOKEN, token);
    }

    public static String getPhone() {
        return getString(MOBILE, "");
    }
}
