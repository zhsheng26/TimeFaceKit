package cn.timeface.timekit.ui.dialog;

import android.content.Context;

/**
 * Created by zhangsheng on 2017/6/16.
 */

public class DialogFactory {
    public static DialogImpl createLoadingDialog(Context context) {
        return new DialogProgress(context);
    }
}
