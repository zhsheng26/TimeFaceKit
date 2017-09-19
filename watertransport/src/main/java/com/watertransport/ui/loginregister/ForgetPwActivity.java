package com.watertransport.ui.loginregister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.watertransport.R;
import com.watertransport.api.ApiService;
import com.watertransport.entity.VerifyNumObj;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timekit.activity.TfBaseActivity;
import cn.timeface.timekit.support.SchedulersCompat;
import cn.timeface.timekit.ui.edittext.XEditText;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class ForgetPwActivity extends TfBaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_boat_phone)
    XEditText edBoatPhone;
    @BindView(R.id.ed_verify_num)
    XEditText edVerifyNum;
    @BindView(R.id.btn_get_verify)
    Button btnGetVerify;
    @BindView(R.id.layout_boat_forget)
    LinearLayout layoutBoatForget;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private String verifyNumObjYzm;
    private String phoneNum;

    public static void start(Context context, int userRole) {
        Intent starter = new Intent(context, ForgetPwActivity.class);
        starter.putExtra("user_role", userRole);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pw1);
        ButterKnife.bind(this);
        setTitle("忘记密码");
        btnGetVerify.setOnClickListener(v -> {
            phoneNum = edBoatPhone.getText().toString();
            if (TextUtils.isEmpty(phoneNum)) {
                showToast("请输入手机号");
                return;
            }
            btnGetVerify.setEnabled(false);
            getVerifyNum();
        });

        btnSubmit.setOnClickListener(v -> {
            String inputVerifyNum = edVerifyNum.getText().toString();
            if (TextUtils.isEmpty(inputVerifyNum)) {
                showToast("请输入验证码");
                return;
            }
            if (!TextUtils.isDigitsOnly(inputVerifyNum)) {
                showToast("验证码不正确");
                return;
            }
            if (!inputVerifyNum.equals(verifyNumObjYzm)) {
                showToast("验证码不正确");
                return;
            }
            SetPwActivity.start(activity, phoneNum);
        });
    }

    public void getVerifyNum() {
        int count_time = 120; //总时间
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .compose(SchedulersCompat.applyIoSchedulers())
                .take(count_time + 1)
                .map(aLong -> count_time - aLong)
                .doOnSubscribe(disposable -> {
                    btnGetVerify.setEnabled(false);
                    reqVerifyNum();
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

    private void reqVerifyNum() {
        Disposable disposable = ApiService.getInstance().getApi().sendDxyzm(edBoatPhone.getText().toString())
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(pageInfoNetResponse -> {
                    List<VerifyNumObj> list = pageInfoNetResponse.getData().getList();
                    if (list == null || list.size() == 0) return;
                    VerifyNumObj verifyNumObj = list.get(0);
                    verifyNumObjYzm = verifyNumObj.getYzm();
                }, Timber::d);
        addSubscription(disposable);
    }
}
