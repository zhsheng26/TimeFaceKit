package com.watertransport.ui.loginregister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.watertransport.BuildConfig;
import com.watertransport.R;
import com.watertransport.api.ApiService;
import com.watertransport.api.ApiStores;
import com.watertransport.support.WtConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timekit.activity.TfBaseActivity;
import cn.timeface.timekit.support.SchedulersCompat;
import cn.timeface.timekit.ui.TfWebViewActivity;
import cn.timeface.timekit.ui.edittext.XEditText;
import cn.timeface.timekit.util.UiUtil;
import cn.timeface.timekit.util.string.StringUtil;
import io.reactivex.disposables.Disposable;


public class RegisterActivity extends TfBaseActivity implements View.OnClickListener {
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
    @BindView(R.id.tv_confirm_agreement)
    TextView tvConfirmAgreement;
    @BindView(R.id.tv_agreement_detail)
    TextView tvAgreementDetail;
    private int user_role;
    private ApiStores apiStores;

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
        user_role = getIntent().getIntExtra("user_role", WtConstant.USER_ROLE_BOAT);
        boolean isBoat = user_role == WtConstant.USER_ROLE_BOAT;
        getSupportActionBar().setTitle(isBoat ? "船主注册" : "货主注册");
        UiUtil.showView(layoutBoatRegister, isBoat);
        UiUtil.showView(layoutCargoRegister, !isBoat);
        apiStores = ApiService.getInstance().getApi();
        btnSubmit.setOnClickListener(this);
        tvAgreementDetail.setOnClickListener(v -> TfWebViewActivity.start(activity, "服务条款", BuildConfig.BASE_URL + "userManualAgreement.html"));
    }

    @Override
    public void onClick(View v) {
        if (user_role == WtConstant.USER_ROLE_BOAT) {
            registerBoat();
        } else {
            registerCargo();
        }
    }

    private void registerBoat() {
        String userName = edUserName.getText().toString();
        String pw = edUserPw.getText().toString();
        String phone = edUserPhone.getText().toString();
        String realName = edUserRealName.getText().toString();
        String boatAddress = edBoatAddress.getText().toString();
        String boatCompany = edBoatCompany.getText().toString();
        String boatNum = edBoatNum.getText().toString();
        String boatVerify = edBoatVerify.getText().toString();
        String boatCapacity = edBoatCapacity.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            showToast("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(pw)) {
            showToast("请输入密码");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(realName)) {
            showToast("请输入真实姓名");
            return;
        }
        if (TextUtils.isEmpty(boatNum)) {
            showToast("请输入船号");
            return;
        }
        if (TextUtils.isEmpty(boatCapacity)) {
            showToast("请输入船载吨位");
            return;
        }
        if (!TextUtils.isDigitsOnly(boatCapacity) || Long.parseLong(boatCapacity) < 0) {
            showToast("请输入正确的船载吨位");
        }
        if (!StringUtil.isMobileNum(phone)) {
            showToast("手机号不正确");
            return;
        }
        if (pw.length() < 6) {
            showToast("密码太短，不安全");
            return;
        }
        if (!cbReadContact.isChecked()) {
            showToast("请阅读并同意服务条款");
            return;
        }
        Disposable subscribe = apiStores.registerBoat(userName.trim(),
                pw.trim(),
                user_role,
                phone.trim(),
                boatNum.trim(),
                realName.trim(),
                boatAddress.trim(),
                boatCompany.trim(),
                boatVerify.trim(),
                boatCapacity.trim())
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(netResponse -> {
                    showToast(netResponse.getMessage());
                    if (netResponse.isResult()) {
                        Intent intent = new Intent();
                        intent.setClass(activity, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }, throwable -> showToast("抱歉，出错了"));
        addSubscription(subscribe);


    }

    private void registerCargo() {
        String userName = edUserName.getText().toString();
        String pw = edUserPw.getText().toString();
        String phone = edUserPhone.getText().toString();
        String realName = edUserRealName.getText().toString();
        String company = edCompanyName.getText().toString();
        String address = edCompanyAddress.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            showToast("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(pw)) {
            showToast("请输入密码");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(realName)) {
            showToast("请输入真实姓名");
            return;
        }
        if (!StringUtil.isMobileNum(phone.trim())) {
            showToast("手机号不正确");
            return;
        }
        if (pw.length() < 6) {
            showToast("密码太短，不安全");
            return;
        }
        Disposable subscribe = apiStores.registerCargo(userName.trim(),
                pw.trim(),
                user_role,
                phone.trim(),
                realName.trim(),
                company.trim(),
                address.trim())
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(netResponse -> {
                            showToast(netResponse.getMessage());
                            if (netResponse.isResult()) {
                                Intent intent = new Intent();
                                intent.setClass(activity, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                        , throwable -> showToast("抱歉，出错了"));
        addSubscription(subscribe);

    }

}
