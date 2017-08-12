package com.watertransport.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.watertransport.R;
import com.watertransport.support.WtConstant;
import com.watertransport.ui.loginregister.ForgetPwActivity;
import com.watertransport.ui.loginregister.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.timeface.timekit.activity.TfBaseActivity;

public class SelectRoleActivity extends TfBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rl_boat_owner)
    RelativeLayout rlBoatOwner;
    @BindView(R.id.rl_cargo_owner)
    RelativeLayout rlCargoOwner;
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
        getSupportActionBar().setTitle("您是？");
        forRegister = getIntent().getBooleanExtra("forRegister", true);
    }

    @OnClick({R.id.rl_boat_owner, R.id.rl_cargo_owner})
    public void clickSelect(View v) {
        int role = WtConstant.USER_ROLE_BOAT;
        switch (v.getId()) {
            case R.id.rl_cargo_owner:
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
