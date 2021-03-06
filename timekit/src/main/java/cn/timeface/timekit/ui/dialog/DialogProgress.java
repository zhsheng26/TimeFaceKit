package cn.timeface.timekit.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.timeface.timekit.R;

/**
 * Created by zhangsheng on 2017/6/16.
 * 显示进度的dialog
 */

public class DialogProgress extends BaseDialog {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTipMsg("正在加载，请稍后...");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View loadingView = inflater.inflate(R.layout.layout_dialog_loading, container);
        TextView tvTip = (TextView) loadingView.findViewById(R.id.tv_loading_tip);
        tvTip.setText(TextUtils.isEmpty(getTipMsg()) ? "正在加载，请稍后..." : getTipMsg());
        return loadingView;
    }
}
