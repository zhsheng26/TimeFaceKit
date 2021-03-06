package cn.timeface.timekit.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import cn.timeface.timekit.support.IEventBus;
import cn.timeface.timekit.util.sys.NetworkUtil;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhangsheng on 2017/5/15.
 */

public class TfBaseFragment extends Fragment {
    private Toast toast;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this instanceof IEventBus) {
            EventBus.getDefault().register(this);
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
    public void onDestroy() {
        if (this instanceof IEventBus) {
            EventBus.getDefault().unregister(this);
        }
        if (mCompositeDisposable != null) mCompositeDisposable.dispose();
        super.onDestroy();
    }

    public void showLoading() {

    }

    public void hideLoading() {

    }

    public void showToast(@StringRes int resId) {
        showToast(getResources().getString(resId));
    }

    public void showToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }


    public boolean isNetworkConnected() {
        return NetworkUtil.isNetAvailable(getActivity());
    }
}
