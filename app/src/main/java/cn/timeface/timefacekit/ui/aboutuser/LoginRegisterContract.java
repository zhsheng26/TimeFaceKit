package cn.timeface.timefacekit.ui.aboutuser;

import cn.timeface.timefacekit.api.entity.UserObj;
import cn.timeface.timekit.support.tfmvp.TfMvpPresenter;
import cn.timeface.timekit.support.tfmvp.TfMvpView;

/**
 * Created by zhangsheng on 2017/8/1.
 */

public interface LoginRegisterContract {
    interface ILoginView extends TfMvpView {
        void loginSuccess(UserObj userObj);
    }

    interface ILoginPresenter extends TfMvpPresenter<ILoginView> {
        void login(String account, String pw, int role);
    }
}
