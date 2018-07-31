package com.grandsea.ticketvendingapplication.model.bean;

import java.io.Serializable;

/**
 * Created by Grandsea09 on 2017/10/13.
 */

public class PersonInfo implements Serializable {
    private String name;//姓名
    private String idNo;//身份证号

    public PersonInfo() {
    }

    public PersonInfo(String name, String idNo) {
        this.name = name;
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
}
