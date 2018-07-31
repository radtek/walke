package com.grandsea.ticketvendingapplication.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */

public class City implements Serializable{

    private int id;

    private int provinceId;

    private String name;

    private List<District> districts;
//    ......
    public City(){

    }

    public City(int id, int provinceId, String name) {
        this.id = id;
        this.provinceId = provinceId;
        this.name = name;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public int getId() {
        return id;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
