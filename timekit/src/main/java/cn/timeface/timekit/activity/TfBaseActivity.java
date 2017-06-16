package cn.timeface.timekit.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import cn.timeface.timekit.R;
import cn.timeface.timekit.ui.dialog.DialogFactory;
import cn.timeface.timekit.ui.dialog.DialogImpl;
import cn.timeface.timekit.util.sys.NetworkUtil;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhangsheng on 2017/5/15.
 */

public class TfBaseActivity extends AppCompatActivity {
    protected AppCompatActivity activity;
    private Toast toast;
    private CompositeDisposable mCompositeDisposable;
    private DialogImpl progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setupToolbar();
    }

    private void setupToolbar() {
        View toolbarView = findViewById(R.id.toolbar);
        if (toolbarView != null && toolbarView instanceof Toolbar) {
            Toolbar toolbar = (Toolbar) toolbarView;
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public CompositeDisposable getCompositeDisposable() {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        return mCompositeDisposable;
    }

    public void addSubscription(Disposable disposable) {
        getCompositeDisposable().add(disposable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) mCompositeDisposable.dispose();
    }

    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = DialogFactory.createLoadingDialog(activity);
        }
        progressDialog.show();
    }

    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void showToast(@StringRes int resId) {
        showToast(getResources().getString(resId));
    }

    public void showToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }


    public boolean isNetworkConnected() {
        return NetworkUtil.isNetAvailable(this);
    }
}
