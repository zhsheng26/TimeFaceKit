package cn.timeface.timefacekit.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timefacekit.BuildConfig;
import cn.timeface.timefacekit.R;
import cn.timeface.timefacekit.api.ApiService;
import cn.timeface.timefacekit.api.ApiStores;
import cn.timeface.timefacekit.support.FastData;
import cn.timeface.timefacekit.ui.material.TypesetPhotoActivity;
import cn.timeface.timekit.activity.TfBaseActivity;
import cn.timeface.timekit.util.encode.AES;
import cn.timeface.timekit.util.sys.DeviceUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends TfBaseActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private ApiStores apiStores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        apiStores = ApiService.getInstance().getApi();
        firstRun();
        String userId = FastData.getString("userId", "");
        Timber.v("userId = " + userId);
        btnLogin.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(userId)) {
                startActivity(new Intent(activity, TypesetPhotoActivity.class));
                return;
            }
            String name = etName.getText().toString();
            String pw = etPassword.getText().toString();
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pw)) {
                userLogin(name, pw);
            }

        });

    }

    private void firstRun() {
        Disposable disposable = apiStores.firstRun(Build.MODEL, Build.VERSION.RELEASE, BuildConfig.VERSION_NAME,
                DeviceUtil.getScreenHeight(this) + "X" + DeviceUtil.getScreenWidth(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(netResponse -> Timber.v(netResponse.getInfo() + ""));
        addSubscription(disposable);
    }

    private void userLogin(String name, String pw) {
        Disposable disposable = apiStores
                .login(Uri.encode(name), Uri.encode(new AES().encrypt(pw.getBytes())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    if (loginResponse.success()) {
                        FastData.saveUserInfo(loginResponse.getUserInfo());
                    } else {
                        showToast(loginResponse.getInfo());
                    }
                });
        addSubscription(disposable);
    }
}
