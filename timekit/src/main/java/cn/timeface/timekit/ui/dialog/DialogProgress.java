package cn.timeface.timekit.ui.dialog;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by zhangsheng on 2017/6/16.
 */

public class DialogProgress extends ProgressDialog implements DialogImpl {

    public DialogProgress(Context context) {
        super(context);
    }

    public DialogProgress(Context context, int theme) {
        super(context, theme);
    }
}
