package cn.timeface.timefacekit.support;

import cn.timeface.timefacekit.api.entity.UserObj;
import cn.timeface.timekit.util.storage.Remember;

/**
 * Created by zhangsheng on 2017/6/19.
 */

public class FastData extends Remember {
    public static void saveUserInfo(UserObj userObj) {
        putString("userId", userObj.getUserId());
        putString("nickName", userObj.getNickName());
        putString("avatar", userObj.getAvatar());
    }
}
