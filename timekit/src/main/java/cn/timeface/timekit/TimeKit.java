package cn.timeface.timekit;

import android.content.Context;
import android.util.Log;

import cn.timeface.timekit.util.log.LogUtil;
import cn.timeface.timekit.util.storage.StorageType;
import cn.timeface.timekit.util.storage.StorageUtil;
import cn.timeface.timekit.util.sys.ScreenUtil;

/**
 * Created by zhangsheng on 2017/5/15.
 */

public final class TimeKit {
    // context
    private static Context context;

    public static void init(Context context) {
        TimeKit.context = context.getApplicationContext();
        // init tools
        StorageUtil.init(context, null);
        ScreenUtil.init(context);
        // init log
        String path = StorageUtil.getDirectoryByDirType(StorageType.TYPE_LOG);
        LogUtil.init(path, Log.DEBUG);
    }

    public static Context getContext() {
        return context;
    }
}
