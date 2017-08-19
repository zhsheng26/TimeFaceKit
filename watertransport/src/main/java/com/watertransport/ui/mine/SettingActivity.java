package com.watertransport.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.watertransport.R;
import com.watertransport.support.FastData;
import com.watertransport.ui.loginregister.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timekit.activity.TfBaseActivity;

public class SettingActivity extends TfBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_about)
    RelativeLayout layoutAbout;
    @BindView(R.id.layout_service)
    RelativeLayout layoutService;
    @BindView(R.id.layout_call_we)
    RelativeLayout layoutCallWe;
    @BindView(R.id.layout_alter_pw)
    RelativeLayout layoutAlterPw;
    @BindView(R.id.layout_question)
    RelativeLayout layoutQuestion;
    @BindView(R.id.layout_suggest)
    RelativeLayout layoutSuggest;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("设置");
        btnSubmit.setOnClickListener(v -> {
            FastData.saveUserInfo(null);
            Intent intent = new Intent();
            intent.setClass(activity, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
