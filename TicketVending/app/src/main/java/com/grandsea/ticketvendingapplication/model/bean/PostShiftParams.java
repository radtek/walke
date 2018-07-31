package com.grandsea.ticketvendingapplication.model.bean;


import java.io.Serializable;

//java.lang.RuntimeException: Parcelable encountered IOException writing serializable object  报这个错误是，类对象里面的类对象也要实现 Serializable接口，不能JsonArray，这个类没有实现Serializable接口
public class PostShiftParams implements Serializable {

	
	private int departCityId = 0;
	private int destCityId = 0;
	private String departDate = "";
	private int getOnId = 0;
	private String getOnStation = "";//上车点名称
	private String departCity = "";//上车点城市
	private int takeOffId = 0;
	private String takeOffStation = "";//下车点名称
    private String destCity ="";//下车点城市
    private String [] timeRanges= new String[]{};

    public String getDepartCity() {
        return departCity;
    }

    public void setDepartCity(String departCity) {
        this.departCity = departCity;
    }

    public String getDestCity() {
        return destCity;
    }

    public void setDestCity(String destCity) {
        this.destCity = destCity;
    }

    public void setTimeRanges(String[] timeRanges) {
        this.timeRanges = timeRanges;
    }

    public String[] getTimeRanges() {
        return timeRanges;
    }

    public void setDepartCityId(int departCityId) {
        this.departCityId = departCityId;
    }

    public void setDestCityId(int destCityId) {
        this.destCityId = destCityId;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public void setGetOnId(int getOnId) {
        this.getOnId = getOnId;
    }

    public void setGetOnStation(String getOnStation) {
        this.getOnStation = getOnStation;
    }

    public void setTakeOffId(int takeOffId) {
        this.takeOffId = takeOffId;
    }

    public void setTakeOffStation(String takeOffStation) {
        this.takeOffStation = takeOffStation;
    }

    public int getDepartCityId() {
        return departCityId;
    }

    public int getDestCityId() {
        return destCityId;
    }

    public String getDepartDate() {
        return departDate;
    }

    public int getGetOnId() {
        return getOnId;
    }

    public String getGetOnStation() {
        return getOnStation;
    }

    public int getTakeOffId() {
        return takeOffId;
    }

    public String getTakeOffStation() {
        return takeOffStation;
    }

    @Override
    public String toString() {
        return "PostShiftParams{" +
                "departCityId=" + departCityId +
                ", destCityId=" + destCityId +
                ", departDate='" + departDate + '\'' +
                ", getOnId=" + getOnId +
                ", getOnStation='" + getOnStation + '\'' +
                ", takeOffId=" + takeOffId +
                ", takeOffStation='" + takeOffStation + '\'' +
                ", timeRanges=" + timeRanges +
                '}';
    }
}
