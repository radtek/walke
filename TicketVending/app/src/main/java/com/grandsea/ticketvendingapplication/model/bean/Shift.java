package com.grandsea.ticketvendingapplication.model.bean;

import com.google.gson.JsonArray;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/8.
 */

public class Shift implements Serializable{
    private int id;
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
    private String plateNum;
    private String busPartnerPhone;  //司机电话号码
    private String ticketDate;
    private String departTime;
    private String getOnTime;
    private int busInfoId;
    private int isPartnerSysData;
    private String departStationNode;
    private String destStationNode;
    private int price;
    private int halfPrice;
    private int studentPrice;
    private int ticketNum;      //成人票数量	必填

    public void setBusPartnerPhone(String busPartnerPhone) {
        this.busPartnerPhone = busPartnerPhone;
    }

    public String getBusPartnerPhone() {
        return busPartnerPhone;
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

    public void setTicketNum(int ticketNum) {
        this.ticketNum = ticketNum;
    }

    public int getTicketNum() {
        return ticketNum;
    }

    public void setId(int id) {
        this.id = id;
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


    public void setTicketDate(String ticketDate) {
        this.ticketDate = ticketDate;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public void setGetOnTime(String getOnTime) {
        this.getOnTime = getOnTime;
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

    public int getId() {
        return id;
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

    public String getDestCity() {
        return destCity;
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

    public String getTicketDate() {
        return ticketDate;
    }

    public String getDepartTime() {
        return departTime;
    }

    public String getGetOnTime() {
        return getOnTime;
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
}
