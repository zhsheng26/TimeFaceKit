package cn.timeface.timekit.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import cn.timeface.timekit.R;
import cn.timeface.timekit.util.sys.DeviceUtil;

/**
 * Created by zhangsheng on 2017/9/15.
 * 提示文案，可供选择确定、取消的Dialog
 */

public class DialogTip extends BaseDialog {
    public static DialogTip newInstance() {
        Bundle args = new Bundle();
        DialogTip fragment = new DialogTip();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示：")
                .setMessage(TextUtils.isEmpty(getTipMsg()) ? "请再次确认您的操作？" : getTipMsg())
                .setPositiveButton(positiveBtnTxt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onDialogListener != null) onDialogListener.onPositiveSelect();
                    }
                })
                .setNegativeButton(negativeBtnTxt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onDialogListener != null) onDialogListener.onNegativeSelect();
                    }
                })
                .setCancelable(false);
        setDialogWidth(DeviceUtil.getScreenWidth(getActivity()) - 2 * getResources().getDimensionPixelSize(R.dimen.margin_xlarge));
        return builder.create();
    }
}
