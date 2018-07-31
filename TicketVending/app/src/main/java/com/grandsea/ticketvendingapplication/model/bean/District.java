package com.grandsea.ticketvendingapplication.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 * 区
 */

public class District {
    private int id;
    private String name;
    private int cityId;
    private int provinceId;

    private List<Station> stations;

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public District(){}

    public District(int id, String name, int cityId, int provinceId) {
        this.id = id;
        this.name = name;
        this.cityId = cityId;
        this.provinceId = provinceId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCityId() {
        return cityId;
    }

    public int getProvinceId() {
        return provinceId;
    }
}
