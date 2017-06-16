package cn.timeface.timekit.support.listener;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by zhangsheng on 2017/5/26.
 */

public interface OnItemClickListener<T> {
    void onItemClick(T t, int position, @Nullable Bundle bundle);
}
