package com.watertransport.support;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
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
    public static final String shipCode = "shipCode";
    public static final String shipLicense = "shipLicense";
    public static final String tonnage = "tonnage";
    public static final String belongs = "belongs";
    public static final String belongsCompany = "belongsCompany";
    public static final String addrees = "addrees";
    public static final String companyName = "companyName";
    public static final String registeAddress = "registeAddress";
    /*
    * 船主：
船号(shipCode)、船主证(shipLicense)、船载吨位(tonnage)、所在地(belongs)、所属公司(belongsCompany);
货运公司/货主：
 地址(addrees)、公司名称(companyName)、注册地址(registeAddress)
    * */
    private static int phone;

    public static void putUserJson(UserObj userObj) {
        String toJson = new Gson().toJson(userObj);
        putString("userJson", toJson);
    }

    public static
    @Nullable
    UserObj getUserInfo() {
        String userJson = getString("userJson", "");
        if (TextUtils.isEmpty(userJson)) return null;
        return new Gson().fromJson(userJson, UserObj.class);
    }

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
