package com.watertransport.entity;

/**
 * Created by zhangsheng on 2017/8/29.
 */

public class MsgObj extends BaseObj {
    /**
     * page :
     * createBy :
     * createDate : 2017-08-28 02:13:48
     * updateBy :
     * updateDate :
     * delFlag : 0
     * id : 3
     * announceId : 10
     * userId : 37
     * readStatus : 0
     * announceInfo : {"page":"","createBy":"","createDate":"","updateBy":"","updateDate":"","delFlag":"0","id":"","announceContext":"各位船主，大家晚上好！","announceType":"3"}
     */

    private String page;
    private String createBy;
    private String createDate;
    private String updateBy;
    private String updateDate;
    private String delFlag;
    private String id;
    private String announceId;
    private String userId;
    private String readStatus;
    private AnnounceInfoBean announceInfo;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnnounceId() {
        return announceId;
    }

    public void setAnnounceId(String announceId) {
        this.announceId = announceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public AnnounceInfoBean getAnnounceInfo() {
        return announceInfo;
    }

    public void setAnnounceInfo(AnnounceInfoBean announceInfo) {
        this.announceInfo = announceInfo;
    }

}
