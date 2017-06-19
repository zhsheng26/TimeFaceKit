package cn.timeface.timefacekit.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangsheng on 2017/6/19.
 */

public class GroupAlbumImageItem implements Parcelable {
    private long date;
    private List<ImgObj> images;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public List<ImgObj> getImages() {
        return images;
    }

    public void setImages(List<ImgObj> images) {
        this.images = images;
    }

    public GroupAlbumImageItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.date);
        dest.writeTypedList(this.images);
    }

    protected GroupAlbumImageItem(Parcel in) {
        this.date = in.readLong();
        this.images = in.createTypedArrayList(ImgObj.CREATOR);
    }

    public static final Creator<GroupAlbumImageItem> CREATOR = new Creator<GroupAlbumImageItem>() {
        @Override
        public GroupAlbumImageItem createFromParcel(Parcel source) {
            return new GroupAlbumImageItem(source);
        }

        @Override
        public GroupAlbumImageItem[] newArray(int size) {
            return new GroupAlbumImageItem[size];
        }
    };
}
