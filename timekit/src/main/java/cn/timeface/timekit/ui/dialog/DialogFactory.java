package cn.timeface.timekit.ui.dialog;

/**
 * Created by zhangsheng on 2017/6/16.
 */

public class DialogFactory {
    public static BaseDialog createLoadingDialog() {
        return DialogLoading.newInstance();
    }
}
