package com.grandsea.ticketvendingapplication.model.bean;

import java.util.List;

/**
 * Created by Grandsea09 on 2017/10/12.
 */

public class PrintfInfo {

    /**
     * result : {"func":"elec_tickets","status":1}
     * electTickets : [{"shiftDateTicketNumId":123813,"getOnStationId":6,"getOnStation":"珠海珠海机场","takeOffStation":"珠海拱北城轨站","ticketDate":"2017-10-16","getOnTime":"11:10:00","plateNum":"粤C19799","busPartnerId":1,"busPartnerPhone":"8111333","busPartnerName":"珠海机场快线","qrCode":"1001761231","code":"1001761231","name":"杨声劲","phone":"188****5546","idCard":"441501********5051","seat":1,"orderId":"dd5b6400281acef152168e9221e36ce4","isContact":1,"status":0,"type":1},{"shiftDateTicketNumId":123813,"getOnStationId":6,"getOnStation":"珠海珠海机场","takeOffStation":"珠海拱北城轨站","ticketDate":"2017-10-16","getOnTime":"11:10:00","plateNum":"粤C19799","busPartnerId":1,"busPartnerPhone":"8111333","busPartnerName":"珠海机场快线","qrCode":"1001761230","code":"1001761230","name":"杨声劲","phone":"188****5546","idCard":"441501********5051","seat":2,"orderId":"dd5b6400281acef152168e9221e36ce4","isContact":0,"status":0,"type":3}]
     */

    private ResultBean result;
    private List<ElectTicketsBean> electTickets;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public List<ElectTicketsBean> getElectTickets() {
        return electTickets;
    }

    public void setElectTickets(List<ElectTicketsBean> electTickets) {
        this.electTickets = electTickets;
    }

    public static class ResultBean {
        /**
         * func : elec_tickets
         * status : 1
         */

        private String func;
        private int status;

        public String getFunc() {
            return func;
        }

        public void setFunc(String func) {
            this.func = func;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class ElectTicketsBean {
        /**
         * shiftDateTicketNumId : 123813
         * getOnStationId : 6
         * getOnStation : 珠海珠海机场
         * takeOffStation : 珠海拱北城轨站
         * ticketDate : 2017-10-16
         * getOnTime : 11:10:00
         * plateNum : 粤C19799
         * busPartnerId : 1
         * busPartnerPhone : 8111333
         * busPartnerName : 珠海机场快线
         * qrCode : 1001761231
         * code : 1001761231
         * name : 杨声劲
         * phone : 188****5546
         * idCard : 441501********5051
         * seat : 1
         * orderId : dd5b6400281acef152168e9221e36ce4
         * isContact : 1
         * status : 0
         * type : 1
         */

        private int shiftDateTicketNumId;
        private int getOnStationId;
        private String getOnStation;
        private String takeOffStation;
        private String ticketDate;
        private String getOnTime;
        private String plateNum;
        private int busPartnerId;
        private String busPartnerPhone;
        private String busPartnerName;
        private String qrCode;
        private String code;
        private String name;
        private String phone;
        private String idCard;
        private int seat;
        private String orderId;
        private int isContact;
        private int status;
        private int type;
        private double price;

        public int getShiftDateTicketNumId() {
            return shiftDateTicketNumId;
        }

        public void setShiftDateTicketNumId(int shiftDateTicketNumId) {
            this.shiftDateTicketNumId = shiftDateTicketNumId;
        }

        public int getGetOnStationId() {
            return getOnStationId;
        }

        public void setGetOnStationId(int getOnStationId) {
            this.getOnStationId = getOnStationId;
        }

        public String getGetOnStation() {
            return getOnStation;
        }

        public void setGetOnStation(String getOnStation) {
            this.getOnStation = getOnStation;
        }

        public String getTakeOffStation() {
            return takeOffStation;
        }

        public void setTakeOffStation(String takeOffStation) {
            this.takeOffStation = takeOffStation;
        }

        public String getTicketDate() {
            return ticketDate;
        }

        public void setTicketDate(String ticketDate) {
            this.ticketDate = ticketDate;
        }

        public String getGetOnTime() {
            return getOnTime;
        }

        public void setGetOnTime(String getOnTime) {
            this.getOnTime = getOnTime;
        }

        public String getPlateNum() {
            return plateNum;
        }

        public void setPlateNum(String plateNum) {
            this.plateNum = plateNum;
        }

        public int getBusPartnerId() {
            return busPartnerId;
        }

        public void setBusPartnerId(int busPartnerId) {
            this.busPartnerId = busPartnerId;
        }

        public String getBusPartnerPhone() {
            return busPartnerPhone;
        }

        public void setBusPartnerPhone(String busPartnerPhone) {
            this.busPartnerPhone = busPartnerPhone;
        }

        public String getBusPartnerName() {
            return busPartnerName;
        }

        public void setBusPartnerName(String busPartnerName) {
            this.busPartnerName = busPartnerName;
        }

        public String getQrCode() {
            return qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public int getSeat() {
            return seat;
        }

        public void setSeat(int seat) {
            this.seat = seat;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public int getIsContact() {
            return isContact;
        }

        public void setIsContact(int isContact) {
            this.isContact = isContact;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
