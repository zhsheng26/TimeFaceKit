package cn.timeface.timekit;

import android.annotation.SuppressLint;
import android.content.Context;

import cn.timeface.timekit.support.net.DeviceUuidFactory;
import cn.timeface.timekit.util.storage.Remember;
import cn.timeface.timekit.util.storage.StorageUtil;
import cn.timeface.timekit.util.sys.ScreenUtil;

/**
 * Created by zhangsheng on 2017/5/15.
 */

public final class TimeKit {
    // context
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static void init(Context context) {
        TimeKit.context = context.getApplicationContext();
        Remember.init(context, BuildConfig.APPLICATION_ID + "_preferences");
        StorageUtil.init(context, null);
        ScreenUtil.init(context);
        DeviceUuidFactory.init(context);
    }

    public static Context getContext() {
        return context;
    }
}
