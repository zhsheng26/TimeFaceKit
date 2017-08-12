package com.watertransport.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.Guideline;
import android.view.View;
import android.widget.Button;

import com.watertransport.R;
import com.watertransport.support.WtConstant;
import com.watertransport.ui.loginregister.ForgetPwActivity;
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
    private boolean forRegister;

    /**
     * 选择用户身份，然后进入注册或忘记密码
     *
     * @param context
     * @param forRegister
     */
    public static void start(Context context, boolean forRegister) {
        Intent starter = new Intent(context, SelectRoleActivity.class);
        starter.putExtra("forRegister", forRegister);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        ButterKnife.bind(this);
        forRegister = getIntent().getBooleanExtra("forRegister", true);
    }

    @OnClick({R.id.btn_boat_owner, R.id.btn_cargo_host})
    public void clickSelect(View v) {
        int role = WtConstant.USER_ROLE_BOAT;
        switch (v.getId()) {
            case R.id.btn_cargo_host:
                role = WtConstant.USER_ROLE_CARGO;
                break;
        }
        if (forRegister) {
            RegisterActivity.start(activity, role);
        } else {
            ForgetPwActivity.start(activity, role);
        }
    }
}
