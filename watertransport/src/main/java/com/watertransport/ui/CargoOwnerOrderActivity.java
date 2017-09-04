package com.watertransport.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.watertransport.R;

import cn.timeface.timekit.activity.TfBaseActivity;

public class CargoOwnerOrderActivity extends TfBaseActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, CargoOwnerOrderActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargo_owner_order);
        getSupportActionBar().setTitle("我的订单");
        findViewById(R.id.app_bar).setVisibility(View.GONE);
    }
}
