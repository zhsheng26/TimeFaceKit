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
import com.watertransport.api.ApiStores;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timekit.activity.TfBaseActivity;
import cn.timeface.timekit.support.SchedulersCompat;
import cn.timeface.timekit.ui.edittext.XEditText;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class SetPwActivity extends TfBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_new_pw)
    XEditText etNewPw;
    @BindView(R.id.ed_again_pw)
    XEditText etAgainPw;
    @BindView(R.id.layout_boat_forget)
    LinearLayout layoutBoatForget;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private String phone;
    private ApiStores apiStores;

    public static void start(Context context, String pone) {
        Intent starter = new Intent(context, SetPwActivity.class);
        starter.putExtra("phone", pone);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pw);
        ButterKnife.bind(this);
        setTitle("设置新密码");
        phone = getIntent().getStringExtra("phone");
        apiStores = ApiService.getInstance().getApi();
        btnSubmit.setOnClickListener(v -> submitPw());
    }

    private void submitPw() {
        String pw = etNewPw.getText().toString();
        if (TextUtils.isEmpty(pw)) {
            showToast("请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(etAgainPw.getText().toString())) {
            showToast("请再次输入新密码");
            return;
        }
        if (!etAgainPw.getText().toString().equals(pw)) {
            showToast("两次密码不一致");
            return;
        }

        Disposable subscribe = apiStores.modifyPassword(phone, pw, phone)
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
                }, Timber::d);
        addSubscription(subscribe);
    }
}
