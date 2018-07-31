package com.grandsea.ticketvendingapplication.dao;

import com.grandsea.ticketvendingapplication.model.bean.District;
import com.grandsea.ticketvendingapplication.model.bean.Station;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */

public class DistrictDao {

    //模拟数据所用
    public static List<District> getDistrict(){
        List<District> districts = new ArrayList<>();
        District d1 = new District();
        List<Station> stations1 = new ArrayList<>();
        Station station1 = new Station();
        Station station2 = new Station();
        Station station3 = new Station();
        station1.setName("1号站点");
        station1.setId(1);
        station2.setName("2号站点");
        station2.setId(2);
        station3.setName("3号站点");
        station3.setId(3);
        stations1.add(station1);
        stations1.add(station2);
        stations1.add(station3);
        d1.setStations(stations1);
        d1.setName("广州");

        District d2 = new District();
        List<Station> stations2 = new ArrayList<>();
        Station station11 = new Station();
        Station station22 = new Station();
        Station station33 = new Station();
        Station station44 = new Station();
        station11.setName("11号站点");
        station22.setName("22号站点");
        station33.setName("33号站点");
        station44.setName("44号站点");
        station11.setId(11);
        station22.setId(22);
        station33.setId(33);
        station44.setId(44);
        stations2.add(station11);
        stations2.add(station22);
        stations2.add(station33);
        stations2.add(station44);
        d2.setStations(stations2);
        d2.setName("珠海");


        districts.add(d1);
        districts.add(d2);

        return districts;

    }
}
