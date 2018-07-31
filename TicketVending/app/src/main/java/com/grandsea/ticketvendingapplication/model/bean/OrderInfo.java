package com.grandsea.ticketvendingapplication.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/9.
 */

public class OrderInfo implements Serializable{
    //订单信息 -->提交订单时所用
    private int shiftId;
    private String  serialNum;
    private int routeId;
    private String shortName; //路线简称
    private int departCityId;
    private String departCity;
    private int getOnStationId;
    private String getOnStation; //（城市+上车站点）
    private int destCityId;
    private String destCity;
    private int takeOffStationId;
    private String takeOffStation; //（城市+下车站点）
    private int busPartnerId;
    private String busPartnerName;
    private String plateNum;//车牌号
    private String busPartnerPhone;  //司机电话号码
    private String ticketDate;//车票日期
    private String departTime;//发车时间
    private String getOnTime;//上车时间
    private int ticketNum;      //成人票数量	必填
    private int cTicketNum;      //儿童票数量	必填
    private int sTicketNum;      //学生票数量	新增 by walke
    private int kTicketNum;      //儿童免费票数量	新增 by walke
    private int hasInsurance;   //是否买保险	0：否，1：是
    private String name;        //联系人姓名
    private String phone;       //联系电话
    private String idCard;
    private String passengers; //乘车人列表 【数组】
    private int couponId;       //优惠券id
    private String couponTitle; //优惠券名称
    private int busInfoId;
    private int isPartnerSysData;
    private String departStationNode;
    private String destStationNode;
    private int price;//成人票价
    private int halfPrice;//半价
    private int studentPrice;//学生价
    private int totalPrice;

    @Override
    public String toString() {
        return "OrderInfo{" +
                "shiftId=" + shiftId +
                ", serialNum='" + serialNum + '\'' +
                ", routeId=" + routeId +
                ", shortName='" + shortName + '\'' +
                ", departCityId=" + departCityId +
                ", getOnStationId=" + getOnStationId +
                ", getOnStation='" + getOnStation + '\'' +
                ", destCityId=" + destCityId +
                ", takeOffStationId=" + takeOffStationId +
                ", takeOffStation='" + takeOffStation + '\'' +
                ", busPartnerId=" + busPartnerId +
                ", busPartnerName='" + busPartnerName + '\'' +
                ", plateNum='" + plateNum + '\'' +
                ", busPartnerPhone='" + busPartnerPhone + '\'' +
                ", ticketDate='" + ticketDate + '\'' +
                ", departTime='" + departTime + '\'' +
                ", getOnTime='" + getOnTime + '\'' +
                ", ticketNum=" + ticketNum +
                ", hasInsurance=" + hasInsurance +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", passengers=" + passengers +
                ", couponId=" + couponId +
                ", couponTitle='" + couponTitle + '\'' +
                '}';
    }

    public int getsTicketNum() {
        return sTicketNum;
    }

    public void setsTicketNum(int sTicketNum) {
        this.sTicketNum = sTicketNum;
    }

    public int getkTicketNum() {
        return kTicketNum;
    }

    public void setkTicketNum(int kTicketNum) {
        this.kTicketNum = kTicketNum;
    }

    public void setDepartCity(String departCity) {
        this.departCity = departCity;
    }

    public void setDestCity(String destCity) {
        this.destCity = destCity;
    }

    public String getDepartCity() {
        return departCity;
    }

    public String getDestCity() {
        return destCity;
    }

    public void setcTicketNum(int cTicketNum) {
        this.cTicketNum = cTicketNum;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public void setBusInfoId(int busInfoId) {
        this.busInfoId = busInfoId;
    }

    public void setIsPartnerSysData(int isPartnerSysData) {
        this.isPartnerSysData = isPartnerSysData;
    }

    public void setDepartStationNode(String departStationNode) {
        this.departStationNode = departStationNode;
    }

    public void setDestStationNode(String destStationNode) {
        this.destStationNode = destStationNode;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setHalfPrice(int halfPrice) {
        this.halfPrice = halfPrice;
    }

    public void setStudentPrice(int studentPrice) {
        this.studentPrice = studentPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }


    public int getcTicketNum() {
        return cTicketNum;
    }

    public String getIdCard() {
        return idCard;
    }

    public int getBusInfoId() {
        return busInfoId;
    }

    public int getIsPartnerSysData() {
        return isPartnerSysData;
    }

    public String getDepartStationNode() {
        return departStationNode;
    }

    public String getDestStationNode() {
        return destStationNode;
    }

    public int getPrice() {
        return price;
    }

    public int getHalfPrice() {
        return halfPrice;
    }

    public int getStudentPrice() {
        return studentPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setPassengers(String passengers) {
        this.passengers = passengers;
    }

    public String getPassengers() {
        return passengers;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setDepartCityId(int departCityId) {
        this.departCityId = departCityId;
    }

    public void setGetOnStationId(int getOnStationId) {
        this.getOnStationId = getOnStationId;
    }

    public void setGetOnStation(String getOnStation) {
        this.getOnStation = getOnStation;
    }

    public void setDestCityId(int destCityId) {
        this.destCityId = destCityId;
    }

    public void setTakeOffStationId(int takeOffStationId) {
        this.takeOffStationId = takeOffStationId;
    }

    public void setTakeOffStation(String takeOffStation) {
        this.takeOffStation = takeOffStation;
    }

    public void setBusPartnerId(int busPartnerId) {
        this.busPartnerId = busPartnerId;
    }

    public void setBusPartnerName(String busPartnerName) {
        this.busPartnerName = busPartnerName;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public void setBusPartnerPhone(String busPartnerPhone) {
        this.busPartnerPhone = busPartnerPhone;
    }

    public void setTicketDate(String ticketDate) {
        this.ticketDate = ticketDate;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public void setGetOnTime(String getOnTime) {
        this.getOnTime = getOnTime;
    }

    public void setTicketNum(int ticketNum) {
        this.ticketNum = ticketNum;
    }

    public void setHasInsurance(int hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public void setCouponTitle(String couponTitle) {
        this.couponTitle = couponTitle;
    }

    public int getShiftId() {
        return shiftId;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public int getRouteId() {
        return routeId;
    }

    public String getShortName() {
        return shortName;
    }

    public int getDepartCityId() {
        return departCityId;
    }

    public int getGetOnStationId() {
        return getOnStationId;
    }

    public String getGetOnStation() {
        return getOnStation;
    }

    public int getDestCityId() {
        return destCityId;
    }

    public int getTakeOffStationId() {
        return takeOffStationId;
    }

    public String getTakeOffStation() {
        return takeOffStation;
    }

    public int getBusPartnerId() {
        return busPartnerId;
    }

    public String getBusPartnerName() {
        return busPartnerName;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public String getBusPartnerPhone() {
        return busPartnerPhone;
    }

    public String getTicketDate() {
        return ticketDate;
    }

    public String getDepartTime() {
        return departTime;
    }

    public String getGetOnTime() {
        return getOnTime;
    }

    public int getTicketNum() {
        return ticketNum;
    }

    public int getHasInsurance() {
        return hasInsurance;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getCouponId() {
        return couponId;
    }

    public String getCouponTitle() {
        return couponTitle;
    }
}
