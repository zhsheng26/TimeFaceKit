package com.watertransport.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangsheng on 2017/9/3.
 */

public class BoatHostOrderObj extends CargoOrderObj implements Parcelable {

    /**
     * page :
     * createBy : 37
     * createDate : 2017-08-18 01:07:31
     * updateBy :
     * updateDate :
     * delFlag : 0
     * id : 1
     * sbipuserId : 1
     * goodsId : 2
     * goodsName : 铁
     * transporter : 闽冉
     * mobile : 15600000000
     * orderId :
     * loadTime : 2017-08-01 00:00:00.0
     * unloadTime :
     * loadCity : 马鞍山
     * loadTerminal : 马鞍山码头
     * unloadCity : 巢湖
     * unloadTerminal : 散兵码头
     * tonnage : 1200
     * settlementTime : 2017-08-02 00:00:00.0
     * transportCost :
     * settlementMoney :
     * orderStatue : 0
     * userId : 37
     * loginName : 三丰
     * realName : 张三丰
     * belongs : 安徽巢湖23
     * shipCode : 华润123
     * belongsCompany :
     * shipLicense : 99990000
     * wheelLicense :
     * sailorLicense :
     */

    private String sbipuserId;
    private String goodsId;
    private String goodsName;
    private String transporter;
    private String orderId;
    private String loadTime;
    private String unloadTime;
    private String loadCity;
    private String unloadCity;
    private String settlementTime;
    private String transportCost;
    private String settlementMoney;
    private int orderStatue;
    private String belongs;
    private String shipCode;
    private String belongsCompany;
    private String shipLicense;
    private String wheelLicense;
    private String sailorLicense;

    public String getSbipuserId() {
        return sbipuserId;
    }

    public void setSbipuserId(String sbipuserId) {
        this.sbipuserId = sbipuserId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    @Override
    public String getCargoName() {
        return goodsName;
    }

    @Override
    public String getTonnageCost() {
        return transportCost;
    }


    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Override
    public String getTransporter() {
        return transporter;
    }

    public void setTransporter(String transporter) {
        this.transporter = transporter;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(String loadTime) {
        this.loadTime = loadTime;
    }

    @Override
    public String getUnloadTime() {
        return unloadTime;
    }

    public void setUnloadTime(String unloadTime) {
        this.unloadTime = unloadTime;
    }

    public String getLoadCity() {
        return loadCity;
    }

    public void setLoadCity(String loadCity) {
        this.loadCity = loadCity;
    }

    public String getUnloadCity() {
        return unloadCity;
    }

    public void setUnloadCity(String unloadCity) {
        this.unloadCity = unloadCity;
    }

    public String getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(String settlementTime) {
        this.settlementTime = settlementTime;
    }

    public String getTransportCost() {
        return transportCost;
    }

    public void setTransportCost(String transportCost) {
        this.transportCost = transportCost;
    }

    public String getSettlementMoney() {
        return settlementMoney;
    }

    public void setSettlementMoney(String settlementMoney) {
        this.settlementMoney = settlementMoney;
    }

    @Override
    public int getOrderStatue() {
        return orderStatue;
    }

    public void setOrderStatue(int orderStatue) {
        this.orderStatue = orderStatue;
    }

    public String getBelongs() {
        return belongs;
    }

    public void setBelongs(String belongs) {
        this.belongs = belongs;
    }

    public String getShipCode() {
        return shipCode;
    }

    public void setShipCode(String shipCode) {
        this.shipCode = shipCode;
    }

    public String getBelongsCompany() {
        return belongsCompany;
    }

    public void setBelongsCompany(String belongsCompany) {
        this.belongsCompany = belongsCompany;
    }

    public String getShipLicense() {
        return shipLicense;
    }

    public void setShipLicense(String shipLicense) {
        this.shipLicense = shipLicense;
    }

    public String getWheelLicense() {
        return wheelLicense;
    }

    public void setWheelLicense(String wheelLicense) {
        this.wheelLicense = wheelLicense;
    }

    public String getSailorLicense() {
        return sailorLicense;
    }

    public void setSailorLicense(String sailorLicense) {
        this.sailorLicense = sailorLicense;
    }

    public BoatHostOrderObj() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.sbipuserId);
        dest.writeString(this.goodsId);
        dest.writeString(this.goodsName);
        dest.writeString(this.transporter);
        dest.writeString(this.orderId);
        dest.writeString(this.loadTime);
        dest.writeString(this.unloadTime);
        dest.writeString(this.loadCity);
        dest.writeString(this.unloadCity);
        dest.writeString(this.settlementTime);
        dest.writeString(this.transportCost);
        dest.writeString(this.settlementMoney);
        dest.writeInt(this.orderStatue);
        dest.writeString(this.belongs);
        dest.writeString(this.shipCode);
        dest.writeString(this.belongsCompany);
        dest.writeString(this.shipLicense);
        dest.writeString(this.wheelLicense);
        dest.writeString(this.sailorLicense);
    }

    protected BoatHostOrderObj(Parcel in) {
        super(in);
        this.sbipuserId = in.readString();
        this.goodsId = in.readString();
        this.goodsName = in.readString();
        this.transporter = in.readString();
        this.orderId = in.readString();
        this.loadTime = in.readString();
        this.unloadTime = in.readString();
        this.loadCity = in.readString();
        this.unloadCity = in.readString();
        this.settlementTime = in.readString();
        this.transportCost = in.readString();
        this.settlementMoney = in.readString();
        this.orderStatue = in.readInt();
        this.belongs = in.readString();
        this.shipCode = in.readString();
        this.belongsCompany = in.readString();
        this.shipLicense = in.readString();
        this.wheelLicense = in.readString();
        this.sailorLicense = in.readString();
    }

    public static final Creator<BoatHostOrderObj> CREATOR = new Creator<BoatHostOrderObj>() {
        @Override
        public BoatHostOrderObj createFromParcel(Parcel source) {
            return new BoatHostOrderObj(source);
        }

        @Override
        public BoatHostOrderObj[] newArray(int size) {
            return new BoatHostOrderObj[size];
        }
    };
}
