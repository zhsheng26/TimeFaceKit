package cn.timeface.timefacekit.api.entity;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import cn.timeface.picker.internal.entity.IMediaItem;
import cn.timeface.picker.internal.entity.MediaItem;

/**
 * Created by zhangsheng on 2017/6/19.
 */

public class ImgObj implements IMediaItem, Parcelable {
    /*
    *   url	string	图片url(本地图片的时候为图片localIdentifier)
        id	number	图片id
        height	number	图片高度
        width	number	图片宽度
        remark	string	图注
        isLocal	boolean	是否是本地图片，发布时用到
        imageOrientation	number	图片旋转属性
        nameLabels	array	图片姓名标签数组NameLabelModel(New)
        time	object	该图片所属于的时光，[TimeLineModel]{timeTitle,content,author,date}(New)
    *
    * */
    private long id;
    private Uri uri;
    private String url;
    private int width;
    private int height;
    private int imageOrientation;
    private String mimeType;
    private long date;
    private long size;


    public ImgObj() {
    }

    public ImgObj(MediaItem item) {
        this.mimeType = item.getMimeType();
        this.size = item.getSize();
        this.date = item.getDate();
        this.uri = item.getContentUri();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Uri getContentUri() {
        if (uri != null) {
            return uri;
        }
        return Uri.parse(url);
    }

    @Override
    public String getMimeType() {
        return null;
    }

    @Override
    public long getDate() {
        return date;
    }

    @Override
    public long getDuration() {
        return 0;
    }

    @Override
    public long getSize() {
        return 0;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getImageOrientation() {
        return imageOrientation;
    }

    public void setImageOrientation(int imageOrientation) {
        this.imageOrientation = imageOrientation;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeParcelable(this.uri, flags);
        dest.writeString(this.url);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeInt(this.imageOrientation);
        dest.writeString(this.mimeType);
        dest.writeLong(this.date);
        dest.writeLong(this.size);
    }

    protected ImgObj(Parcel in) {
        this.id = in.readLong();
        this.uri = in.readParcelable(Uri.class.getClassLoader());
        this.url = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.imageOrientation = in.readInt();
        this.mimeType = in.readString();
        this.date = in.readLong();
        this.size = in.readLong();
    }

    public static final Parcelable.Creator<ImgObj> CREATOR = new Parcelable.Creator<ImgObj>() {
        @Override
        public ImgObj createFromParcel(Parcel source) {
            return new ImgObj(source);
        }

        @Override
        public ImgObj[] newArray(int size) {
            return new ImgObj[size];
        }
    };
}
