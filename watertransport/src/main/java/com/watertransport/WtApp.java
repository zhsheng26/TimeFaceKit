package com.watertransport;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import java.util.List;

import cn.timeface.timekit.TimeKit;
import timber.log.Timber;

/**
 * Created by zhangsheng on 2017/8/2.
 */

public class WtApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (shouldInit()) {
            TimeKit.init(this);
        }
        if (BuildConfig.DEBUG || BuildConfig.LOG_DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
