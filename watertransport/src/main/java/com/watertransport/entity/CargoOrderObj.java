package com.watertransport.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangsheng on 2017/8/27.
 */

public class CargoOrderObj extends BaseObj implements Parcelable {

    /**
     * page :
     * createBy : 25
     * createDate : 2017-08-16 17:07:32
     * updateBy : 25
     * updateDate :
     * delFlag : 0
     * id : 2
     * cargoId : 20
     * cargoName : 煤炭
     * transporterId :
     * transporterName : 合肥长江海运科技有限公司
     * loadTerminal : 马鞍山
     * unloadTerminal : 巢湖
     * tonnage : 1000000
     * tonnageCost : 20
     * statue : 0
     * remarks : 黄花树下不见不散
     * userId : 25
     * loginName : 十三居士
     * realName : 吕鹏
     * companyName : 安徽合肥航运科技有限公司
     * mobile : 15000000000
     * phone : 0551-5555000
     * addrees : 安徽省合肥市巢湖路巢湖大厦1001
     */

    private String page;
    private String createBy;
    private String createDate;
    private String updateBy;
    private String updateDate;
    private String delFlag;
    private String id;
    private String cargoId;
    private String cargoName;
    private String transporterId;
    private String transporterName;
    private String loadTerminal;
    private String unloadTerminal;
    private String tonnage;
    private String tonnageCost;
    private String statue;
    private String remarks;
    private String userId;
    private String loginName;
    private String realName;
    private String companyName;
    private String mobile;
    private String phone;
    private String addrees;

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

    public String getCargoId() {
        return cargoId;
    }

    public void setCargoId(String cargoId) {
        this.cargoId = cargoId;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public String getTransporterId() {
        return transporterId;
    }

    public void setTransporterId(String transporterId) {
        this.transporterId = transporterId;
    }

    public String getTransporterName() {
        return transporterName;
    }

    public void setTransporterName(String transporterName) {
        this.transporterName = transporterName;
    }

    public String getLoadTerminal() {
        return loadTerminal;
    }

    public void setLoadTerminal(String loadTerminal) {
        this.loadTerminal = loadTerminal;
    }

    public String getUnloadTerminal() {
        return unloadTerminal;
    }

    public void setUnloadTerminal(String unloadTerminal) {
        this.unloadTerminal = unloadTerminal;
    }

    public String getTonnage() {
        return tonnage;
    }

    public void setTonnage(String tonnage) {
        this.tonnage = tonnage;
    }

    public String getTonnageCost() {
        return tonnageCost;
    }

    public void setTonnageCost(String tonnageCost) {
        this.tonnageCost = tonnageCost;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddrees() {
        return addrees;
    }

    public void setAddrees(String addrees) {
        this.addrees = addrees;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.page);
        dest.writeString(this.createBy);
        dest.writeString(this.createDate);
        dest.writeString(this.updateBy);
        dest.writeString(this.updateDate);
        dest.writeString(this.delFlag);
        dest.writeString(this.id);
        dest.writeString(this.cargoId);
        dest.writeString(this.cargoName);
        dest.writeString(this.transporterId);
        dest.writeString(this.transporterName);
        dest.writeString(this.loadTerminal);
        dest.writeString(this.unloadTerminal);
        dest.writeString(this.tonnage);
        dest.writeString(this.tonnageCost);
        dest.writeString(this.statue);
        dest.writeString(this.remarks);
        dest.writeString(this.userId);
        dest.writeString(this.loginName);
        dest.writeString(this.realName);
        dest.writeString(this.companyName);
        dest.writeString(this.mobile);
        dest.writeString(this.phone);
        dest.writeString(this.addrees);
    }

    public CargoOrderObj() {
    }

    protected CargoOrderObj(Parcel in) {
        this.page = in.readString();
        this.createBy = in.readString();
        this.createDate = in.readString();
        this.updateBy = in.readString();
        this.updateDate = in.readString();
        this.delFlag = in.readString();
        this.id = in.readString();
        this.cargoId = in.readString();
        this.cargoName = in.readString();
        this.transporterId = in.readString();
        this.transporterName = in.readString();
        this.loadTerminal = in.readString();
        this.unloadTerminal = in.readString();
        this.tonnage = in.readString();
        this.tonnageCost = in.readString();
        this.statue = in.readString();
        this.remarks = in.readString();
        this.userId = in.readString();
        this.loginName = in.readString();
        this.realName = in.readString();
        this.companyName = in.readString();
        this.mobile = in.readString();
        this.phone = in.readString();
        this.addrees = in.readString();
    }

    public static final Parcelable.Creator<CargoOrderObj> CREATOR = new Parcelable.Creator<CargoOrderObj>() {
        @Override
        public CargoOrderObj createFromParcel(Parcel source) {
            return new CargoOrderObj(source);
        }

        @Override
        public CargoOrderObj[] newArray(int size) {
            return new CargoOrderObj[size];
        }
    };
}
