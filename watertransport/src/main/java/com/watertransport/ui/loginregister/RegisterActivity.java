package com.watertransport.ui.loginregister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.watertransport.R;

import cn.timeface.timekit.activity.TfBaseActivity;


public class RegisterActivity extends TfBaseActivity {
    public static void start(Context context, int userRole) {
        Intent starter = new Intent(context, RegisterActivity.class);
        starter.putExtra("user_role", userRole);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}
