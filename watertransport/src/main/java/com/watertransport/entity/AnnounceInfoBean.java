package com.watertransport.entity;

/**
 * Created by zhangsheng on 2017/8/29.
 */

public class AnnounceInfoBean extends BaseObj {
    /**
     * page :
     * createBy :
     * createDate :
     * updateBy :
     * updateDate :
     * delFlag : 0
     * id :
     * announceContext : 各位船主，大家晚上好！
     * announceType : 3
     */

    private String page;
    private String createBy;
    private String createDate;
    private String updateBy;
    private String updateDate;
    private String delFlag;
    private String id;
    private String announceContext;
    private String announceType;

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

    public String getAnnounceContext() {
        return announceContext;
    }

    public void setAnnounceContext(String announceContext) {
        this.announceContext = announceContext;
    }

    public String getAnnounceType() {
        return announceType;
    }

    public void setAnnounceType(String announceType) {
        this.announceType = announceType;
    }
}
