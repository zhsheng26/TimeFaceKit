package cn.timeface.timekit.support.listener;

import android.view.View;

/**
 * Created by zhangsheng on 2017/5/25.
 */

public interface RVItemClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
