package com.grandsea.ticketvendingapplication.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/9.
 */

public class Passenger {
    //乘客信息
    private int id;
    private String name;
    private String phone;
    private String idCard;
    private int isContact; //是否为联系人 1：是，0：否
    private int type ;     //乘车人类型	1：成人（全票），3：学生（学生票），4：免票儿童
    private int status;     //票状态	-1：已退票，0：未验票，1：已验票
    private String code;    //验票码	申请退票时上传

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public void setIsContact(int isContact) {
        this.isContact = isContact;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public int getIsContact() {
        return isContact;
    }

    public int getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Passenger passenger = (Passenger) o;

        if (id != passenger.id) return false;
        if (isContact != passenger.isContact) return false;
        if (type != passenger.type) return false;
        if (status != passenger.status) return false;
        if (!name.equals(passenger.name)) return false;
        if (phone != null ? !phone.equals(passenger.phone) : passenger.phone != null) return false;
        if (!idCard.equals(passenger.idCard)) return false;
        return code != null ? code.equals(passenger.code) : passenger.code == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + idCard.hashCode();
        result = 31 * result + isContact;
        result = 31 * result + type;
        result = 31 * result + status;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }


    public static void main(String ...args){
        Passenger p1 = new Passenger();
        Passenger p2 = new Passenger();
        p1.setName("xiao");
        p2.setName("xiao");
        p1.setIdCard("123");
        p2.setIdCard("123");

        List<Passenger> list = new ArrayList<>();
        list.add(p1);
        System.out.print("ttttt:"+list.contains(p2));

    }
}
