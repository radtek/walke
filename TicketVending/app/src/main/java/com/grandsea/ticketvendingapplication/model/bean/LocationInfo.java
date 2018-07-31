package com.grandsea.ticketvendingapplication.model.bean;

import java.io.Serializable;

/**
 * Created by Grandsea09 on 2017/10/17.
 */

public class LocationInfo implements Serializable {
    private int depart_city_id;//出发城市id--默认
    private int get_on_id;//出发站点id--默认
    private String depart_city_name;//出发城市名称--默认
    private String depart_station_name;//出发站点名称--默认

    public int getDepart_city_id() {
        return depart_city_id;
    }

    public void setDepart_city_id(int depart_city_id) {
        this.depart_city_id = depart_city_id;
    }

    public int getGet_on_id() {
        return get_on_id;
    }

    public void setGet_on_id(int get_on_id) {
        this.get_on_id = get_on_id;
    }

    public String getDepart_city_name() {
        return depart_city_name;
    }

    public void setDepart_city_name(String depart_city_name) {
        this.depart_city_name = depart_city_name;
    }

    public String getDepart_station_name() {
        return depart_station_name;
    }

    public void setDepart_station_name(String depart_station_name) {
        this.depart_station_name = depart_station_name;
    }
}
