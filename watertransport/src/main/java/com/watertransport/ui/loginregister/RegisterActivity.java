package com.watertransport.ui.loginregister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.watertransport.R;
import com.watertransport.support.WtConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timekit.activity.TfBaseActivity;
import cn.timeface.timekit.ui.edittext.XEditText;
import cn.timeface.timekit.util.UiUtil;


public class RegisterActivity extends TfBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_user_name)
    XEditText edUserName;
    @BindView(R.id.ed_user_pw)
    XEditText edUserPw;
    @BindView(R.id.ed_user_phone)
    XEditText edUserPhone;
    @BindView(R.id.ed_user_real_name)
    XEditText edUserRealName;
    @BindView(R.id.ed_boat_address)
    XEditText edBoatAddress;
    @BindView(R.id.ed_boat_company)
    XEditText edBoatCompany;
    @BindView(R.id.ed_boat_num)
    XEditText edBoatNum;
    @BindView(R.id.ed_boat_verify)
    XEditText edBoatVerify;
    @BindView(R.id.ed_boat_capacity)
    XEditText edBoatCapacity;
    @BindView(R.id.ed_company_name)
    XEditText edCompanyName;
    @BindView(R.id.ed_company_address)
    XEditText edCompanyAddress;
    @BindView(R.id.ed_detail_address)
    XEditText edDetailAddress;
    @BindView(R.id.cb_read_contact)
    CheckBox cbReadContact;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.layout_boat_register)
    LinearLayout layoutBoatRegister;
    @BindView(R.id.layout_cargo_register)
    LinearLayout layoutCargoRegister;

    public static void start(Context context, int userRole) {
        Intent starter = new Intent(context, RegisterActivity.class);
        starter.putExtra("user_role", userRole);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        int user_role = getIntent().getIntExtra("user_role", WtConstant.USER_ROLE_BOAT);
        boolean isBoat = user_role == WtConstant.USER_ROLE_BOAT;
        getSupportActionBar().setTitle(isBoat ? "船主注册" : "货主注册");
        UiUtil.showView(layoutBoatRegister, isBoat);
        UiUtil.showView(layoutCargoRegister, !isBoat);
    }
}
