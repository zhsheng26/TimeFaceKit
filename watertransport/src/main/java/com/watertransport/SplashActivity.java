package com.watertransport;

import android.os.Bundle;
import android.text.TextUtils;

import com.watertransport.support.FastData;
import com.watertransport.ui.loginregister.LoginActivity;

import java.util.concurrent.TimeUnit;

import cn.timeface.timekit.activity.TfBaseActivity;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class SplashActivity extends TfBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Disposable disposable = Observable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> {
                    String userId = FastData.getUserId();
                    if (TextUtils.isEmpty(userId)) {
                        LoginActivity.start(activity);
                    } else {
                        MainActivity.start(activity);
                    }
                    finish();
                });
        addSubscription(disposable);
    }
}
