package cn.timeface.timekit.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import cn.timeface.timekit.R;

/**
 * Created by zhangsheng on 2017/5/15.
 * 等待加载的Dialog
 */

public class DialogLoading extends BaseDialog {
    public static DialogLoading newInstance() {
        Bundle args = new Bundle();
        DialogLoading fragment = new DialogLoading();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View loadingView = inflater.inflate(R.layout.layout_dialog_loading, container);
        TextView tvTip = (TextView) loadingView.findViewById(R.id.tv_loading_tip);
        tvTip.setText(TextUtils.isEmpty(getTipMsg()) ? "正在加载，请稍后..." : getTipMsg());
        setOnTouchOutside(true);
        return loadingView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
    }
}
