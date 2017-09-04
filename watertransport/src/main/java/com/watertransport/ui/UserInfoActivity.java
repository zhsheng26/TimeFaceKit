package com.watertransport.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.watertransport.R;

import cn.timeface.timekit.activity.TfBaseActivity;

public class UserInfoActivity extends TfBaseActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, UserInfoActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
    }
}
