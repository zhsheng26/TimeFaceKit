package cn.timeface.picker.internal.entity;

import android.net.Uri;

/**
 * Created by zhangsheng on 2017/5/16.
 */

public interface IMediaItem {
    long getId();

    Uri getContentUri();

    String getMimeType();

    long getDate();

    long getDuration();

    long getSize();
}
