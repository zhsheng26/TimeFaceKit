package cn.timeface.timekit.ui.dialog;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by zhangsheng on 2017/6/16.
 */

public abstract class BaseDialog extends DialogFragment {

    private boolean onTouchOutside = false;

    private String tipMsg = "";
    protected OnDialogListener onDialogListener;
    protected CharSequence negativeBtnTxt = "取消";
    protected CharSequence positiveBtnTxt = "确定";
    private int dialogWidth = WindowManager.LayoutParams.WRAP_CONTENT;
    private int dialogHeight = WindowManager.LayoutParams.WRAP_CONTENT;
    private int gravity = Gravity.CENTER;
    private int animation;


    public BaseDialog setOnTouchOutside(boolean onTouchOutside) {
        this.onTouchOutside = onTouchOutside;
        return this;
    }

    public BaseDialog setTipMsg(String tipMsg) {
        if (!tipMsg.isEmpty()) {
            this.tipMsg = tipMsg;
        }
        return this;
    }

    public BaseDialog setNegativeBtnTxt(CharSequence text) {
        this.negativeBtnTxt = text;
        return this;
    }

    public BaseDialog setPostiveBtnTxt(CharSequence text) {
        this.positiveBtnTxt = text;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public void onStart() {
        super.onStart();
        setCancelable(onTouchOutside);
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setGravity(gravity);
            window.setWindowAnimations(animation);
            window.setLayout(dialogWidth, dialogHeight);
            window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }
    }

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, "");
    }

    public BaseDialog setDialogWidth(int pixels) {
        this.dialogWidth = pixels;
        return this;
    }

    public BaseDialog setOnDialogListener(OnDialogListener onDialogListener) {
        this.onDialogListener = onDialogListener;
        return this;
    }

    public BaseDialog setDialogHeight(@DimenRes int height) {
        this.dialogHeight = height;
        return this;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDialogListener != null) onDialogListener.onDismiss();
    }

    public BaseDialog setAnimation(@StyleRes int animationStyleResId) {
        this.animation = animationStyleResId;
        return this;
    }

    public BaseDialog setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public String getTipMsg() {
        return tipMsg;
    }
}
