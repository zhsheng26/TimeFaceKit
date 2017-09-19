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
import com.watertransport.entity.VerifyNumObj;
import com.watertransport.support.WtConstant;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timekit.activity.TfBaseActivity;
import cn.timeface.timekit.support.SchedulersCompat;
import cn.timeface.timekit.ui.TfWebViewActivity;
import cn.timeface.timekit.ui.edittext.XEditText;
import cn.timeface.timekit.util.UiUtil;
import cn.timeface.timekit.util.string.StringUtil;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;


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
    @BindView(R.id.btn_get_verify)
    Button btnGetVerify;
    @BindView(R.id.ed_verify_num)
    XEditText edVerifyNum;
    private int user_role;
    private ApiStores apiStores;
    private String verifyNumObjYzm = "";

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
        setTitle(isBoat ? "船主注册" : "货主注册");
        UiUtil.showView(layoutBoatRegister, isBoat);
        UiUtil.showView(layoutCargoRegister, !isBoat);
        apiStores = ApiService.getInstance().getApi();
        btnSubmit.setOnClickListener(this);
        tvAgreementDetail.setOnClickListener(v -> TfWebViewActivity.start(activity, "服务条款", BuildConfig.BASE_URL + "userManualAgreement.html"));
        btnGetVerify.setOnClickListener(v -> getVerifyNum());
    }

    private void getVerifyNum() {
        //1先验证手机号是否已经注册
        //2获取验证码
        String phone = edUserPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入手机号");
            return;
        }
        if (!StringUtil.isMobileNum(phone)) {
            showToast("请输入正确的手机号");
            return;
        }
        btnGetVerify.setEnabled(false);
        Disposable disposable = apiStores.checkMobile(phone)
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(netResponse -> {
                    if (netResponse.isResult()) {
                        reqVerifyNum(phone);
                    } else {
                        btnGetVerify.setEnabled(true);
                        showToast(netResponse.getMessage());
                    }
                }, throwable -> {
                    btnGetVerify.setEnabled(true);
                    Timber.d(throwable);
                });
        addSubscription(disposable);
    }

    private void reqVerifyNum(String phone) {
        int count_time = 120; //总时间
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .compose(SchedulersCompat.applyIoSchedulers())
                .take(count_time + 1)
                .map(aLong -> count_time - aLong)
                .doOnSubscribe(disposable -> {
                    btnGetVerify.setEnabled(false);
                    sendVerifyNum(phone);
                })
                .subscribe(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long value) {
                        btnGetVerify.setEnabled(false);
                        btnGetVerify.setText(String.format("重新发送(%s)", value));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        btnGetVerify.setEnabled(true);
                        btnGetVerify.setText("获取验证码");

                    }
                });
    }

    private void sendVerifyNum(String phone) {
        Disposable disposable = apiStores.sendDxyzm(phone)
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(pageInfoNetResponse -> {
                    if (pageInfoNetResponse.isResult()) {
                        List<VerifyNumObj> list = pageInfoNetResponse.getData().getList();
                        if (list == null || list.size() == 0) return;
                        VerifyNumObj verifyNumObj = list.get(0);
                        verifyNumObjYzm = verifyNumObj.getYzm();
                    }
                }, Timber::d);
        addSubscription(disposable);
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
        String phone = edUserPhone.getText().toString();
        String pw = edUserPw.getText().toString();
        String realName = edUserRealName.getText().toString();
        String boatAddress = edBoatAddress.getText().toString();
        String boatCompany = edBoatCompany.getText().toString();
        String boatNum = edBoatNum.getText().toString();
        String boatVerify = edBoatVerify.getText().toString();
        String boatCapacity = edBoatCapacity.getText().toString();
        String verifyNum = edVerifyNum.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入手机号");
            return;
        }
        if (!StringUtil.isMobileNum(phone)) {
            showToast("手机号不正确");
            return;
        }
        if (TextUtils.isEmpty(verifyNum)) {
            showToast("请输入验证码");
            return;
        }
        if (!TextUtils.isDigitsOnly(verifyNumObjYzm) || !verifyNum.equals(verifyNumObjYzm)) {
            showToast("验证码不正确");
            return;
        }
        if (TextUtils.isEmpty(pw)) {
            showToast("请输入密码");
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

        if (pw.length() < 6) {
            showToast("密码太短，不安全");
            return;
        }
        if (!cbReadContact.isChecked()) {
            showToast("请阅读并同意服务条款");
            return;
        }
        Disposable subscribe = apiStores.registerBoat("",
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
        String phone = edUserPhone.getText().toString();
        String pw = edUserPw.getText().toString();
        String realName = edUserRealName.getText().toString();
        String company = edCompanyName.getText().toString();
        String address = edCompanyAddress.getText().toString();
        String verifyNum = edVerifyNum.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入手机号");
            return;
        }
        if (!StringUtil.isMobileNum(phone.trim())) {
            showToast("手机号不正确");
            return;
        }
        if (TextUtils.isEmpty(verifyNum)) {
            showToast("请输入验证码");
            return;
        }
        if (!TextUtils.isDigitsOnly(verifyNumObjYzm) || !verifyNum.equals(verifyNumObjYzm)) {
            showToast("验证码不正确");
            return;
        }
        if (TextUtils.isEmpty(pw)) {
            showToast("请输入密码");
            return;
        }

        if (TextUtils.isEmpty(realName)) {
            showToast("请输入真实姓名");
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
        Disposable subscribe = apiStores.registerCargo("",
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
