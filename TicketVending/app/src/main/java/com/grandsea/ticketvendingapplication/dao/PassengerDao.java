package com.grandsea.ticketvendingapplication.dao;

import com.grandsea.ticketvendingapplication.model.bean.Passenger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/19.
 */

public class PassengerDao {
    public static List<Passenger> getPassenger(){
        List<Passenger> passengers = new ArrayList<>();
        Passenger p1 = new Passenger();
        Passenger p2 = new Passenger();
        Passenger p3 = new Passenger();
        Passenger p4 = new Passenger();
        Passenger p5 = new Passenger();

        p1.setId(1);
        p1.setName("晓红1");
        p1.setIdCard("441888*********8888");

        p2.setId(2);
        p2.setName("晓蓝2");
        p2.setIdCard("441888*********8888");

        p3.setId(3);
        p3.setName("大红3");
        p3.setIdCard("441888*********8888");

        p4.setId(4);
        p4.setName("晓红4");
        p4.setIdCard("441888*********8888");

        p5.setId(48);
        p5.setName("张三");
        p5.setIdCard("41072519810921401X");
        p5.setPhone("13225656561");
        p5.setIsContact(1);
        p5.setType(1);

//        passengers.add(p1);
//        passengers.add(p2);
//        passengers.add(p3);
//        passengers.add(p4);
        passengers.add(p5);
        return passengers;
    }
}
