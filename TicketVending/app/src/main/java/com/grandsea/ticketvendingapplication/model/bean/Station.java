package com.grandsea.ticketvendingapplication.model.bean;

/**
 * Created by Administrator on 2017/9/6.
 */

public class Station {
    private int id;
    private String name;
    private String district; //区域

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
