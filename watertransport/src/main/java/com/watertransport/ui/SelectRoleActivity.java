package com.watertransport.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.Guideline;
import android.view.View;
import android.widget.Button;

import com.watertransport.R;
import com.watertransport.support.WtConstant;
import com.watertransport.ui.loginregister.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.timeface.timekit.activity.TfBaseActivity;

public class SelectRoleActivity extends TfBaseActivity {
    @BindView(R.id.btn_boat_owner)
    Button btnBoatHost;
    @BindView(R.id.btn_cargo_host)
    Button btnCargoHost;
    @BindView(R.id.guideline)
    Guideline guideline;

    public static void start(Context context) {
        Intent starter = new Intent(context, SelectRoleActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_boat_owner, R.id.btn_cargo_host})
    public void clickSelect(View v) {
        int role = WtConstant.USER_ROLE_BOAT;
        switch (v.getId()) {
            case R.id.btn_cargo_host:
                role = WtConstant.USER_ROLE_CARGO;
                break;
        }
        RegisterActivity.start(activity, role);
    }
}
