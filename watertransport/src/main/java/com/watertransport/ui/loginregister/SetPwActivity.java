package com.watertransport.ui.loginregister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.watertransport.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timekit.activity.TfBaseActivity;
import cn.timeface.timekit.ui.edittext.XEditText;

public class SetPwActivity extends TfBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_new_pw)
    XEditText etNewPw;
    @BindView(R.id.ed_boat_phone)
    XEditText edBoatPhone;
    @BindView(R.id.layout_boat_forget)
    LinearLayout layoutBoatForget;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    public static void start(Context context) {
        Intent starter = new Intent(context, SetPwActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pw);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("设置新密码");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
