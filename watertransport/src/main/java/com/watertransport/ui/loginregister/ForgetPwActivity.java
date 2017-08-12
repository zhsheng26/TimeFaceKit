package com.watertransport.ui.loginregister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.watertransport.R;
import com.watertransport.support.WtConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timekit.activity.TfBaseActivity;
import cn.timeface.timekit.ui.edittext.XEditText;
import cn.timeface.timekit.util.UiUtil;

public class ForgetPwActivity extends TfBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_boat_phone)
    XEditText edBoatPhone;
    @BindView(R.id.ed_boat_num)
    XEditText edBoatNum;
    @BindView(R.id.ed_boat_verify)
    XEditText edBoatVerify;
    @BindView(R.id.layout_boat_forget)
    LinearLayout layoutBoatForget;
    @BindView(R.id.ed_cargo_phone)
    XEditText edCargoPhone;
    @BindView(R.id.ed_cargo_user_name)
    XEditText edCargoUserName;
    @BindView(R.id.layout_cargo_forget)
    LinearLayout layoutCargoForget;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    public static void start(Context context, int userRole) {
        Intent starter = new Intent(context, ForgetPwActivity.class);
        starter.putExtra("user_role", userRole);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pw);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("忘记密码");
        int user_role = getIntent().getIntExtra("user_role", WtConstant.USER_ROLE_BOAT);
        boolean isBoat = user_role == WtConstant.USER_ROLE_BOAT;
        UiUtil.showView(layoutBoatForget, isBoat);
        UiUtil.showView(layoutCargoForget, !isBoat);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetPwActivity.start(activity);
            }
        });
    }
}
