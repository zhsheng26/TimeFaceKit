package cn.timeface.timekit.support.tfmvp;


import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class TfBasePresenter<V extends TfMvpView> implements TfMvpPresenter<V> {

    private final CompositeDisposable mCompositeDisposable;

    private V mMvpView;

    public TfBasePresenter(@NonNull CompositeDisposable compositeDisposable) {
        this.mCompositeDisposable = compositeDisposable;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mCompositeDisposable.dispose();
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public void addSubscription(Disposable disposable) {
        getCompositeDisposable().add(disposable);
    }

}
