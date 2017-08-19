package com.watertransport.ui.loginregister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.watertransport.MainActivity;
import com.watertransport.R;
import com.watertransport.api.ApiService;
import com.watertransport.api.ApiStores;
import com.watertransport.support.FastData;
import com.watertransport.ui.SelectRoleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timekit.activity.TfBaseActivity;
import cn.timeface.timekit.support.SchedulersCompat;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class LoginActivity extends TfBaseActivity {


    @BindView(R.id.login_progress)
    ProgressBar loginProgress;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_app_name)
    TextView tvAppName;
    @BindView(R.id.tv_slogan)
    TextView tvSlogan;
    @BindView(R.id.tv_account)
    EditText tvAccount;
    @BindView(R.id.tv_password)
    EditText tvPassword;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_go_forget)
    TextView tvGoForget;
    @BindView(R.id.ll_login_view)
    LinearLayout llLoginView;
    @BindView(R.id.login_form)
    ScrollView loginForm;
    private ApiStores apiStores;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        apiStores = ApiService.getInstance().getApi();
        tvPassword.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.tv_password || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        btnSubmit.setOnClickListener(view -> attemptLogin());
        tvGoForget.setOnClickListener(view -> SelectRoleActivity.start(activity, false));
        tvRegister.setOnClickListener(view -> SelectRoleActivity.start(activity, true));

    }

    private void attemptLogin() {
        String accountName = tvAccount.getText().toString();
        String password = tvPassword.getText().toString();
        if (TextUtils.isEmpty(accountName)) {
            showToast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showToast("请输入密码");
        }

        Disposable disposable = apiStores.login(accountName.trim(), password.trim())
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(netResponse -> {
                    if (netResponse.isResult()) {
                        FastData.saveUserInfo(netResponse.getPage().getList().get(0));
                        FastData.saveToken(netResponse.getToken());
                        MainActivity.start(activity);
                    } else {
                        showToast(netResponse.getMessage());
                    }
                }, throwable -> showToast("抱歉，出错了"));
        addSubscription(disposable);
    }

}

