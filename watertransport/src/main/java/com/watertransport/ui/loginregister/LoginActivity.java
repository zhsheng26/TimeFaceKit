package com.watertransport.ui.loginregister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.watertransport.ui.SelectRoleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timekit.activity.TfBaseActivity;


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

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        tvPassword.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.tv_password || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        btnSubmit.setOnClickListener(view -> attemptLogin());
        tvGoForget.setOnClickListener(view -> {

        });
        tvRegister.setOnClickListener(view -> SelectRoleActivity.start(activity));

    }

    private void attemptLogin() {
        String password = tvPassword.getText().toString();

        MainActivity.start(activity);
    }

}

