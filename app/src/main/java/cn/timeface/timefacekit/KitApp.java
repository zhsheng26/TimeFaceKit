package cn.timeface.timefacekit;

import android.app.Application;

import cn.timeface.timekit.*;
import timber.log.Timber;

/**
 * Created by zhangsheng on 2017/6/16.
 */

public class KitApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TimeKit.init(this);
        if (BuildConfig.DEBUG || BuildConfig.LOG_DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
