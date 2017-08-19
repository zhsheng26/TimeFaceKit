package com.watertransport.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.watertransport.R;

import cn.timeface.timekit.activity.TfBaseActivity;

public class AddNewOrderActivity extends TfBaseActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, AddNewOrderActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_order);
        getSupportActionBar().setTitle("新增运单信息");

    }
}
