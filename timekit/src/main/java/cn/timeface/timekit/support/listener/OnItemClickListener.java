package cn.timeface.timekit.support.listener;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by zhangsheng on 2017/5/26.
 */

public interface OnItemClickListener<View, Value> {
    void onItemClick(View view, Value value, int position, @Nullable Bundle bundle);
}
