package com.aipiao.bkpk.bean.bmob;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by caihui on 2018/3/26.
 */

public class User extends BmobObject {

    private  String phone;
    private BmobFile headIcon;
    private String moon;
    private String nickname;
    private String pwd;
    private String sex;
    private String signcount;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BmobFile getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(BmobFile headIcon) {
        this.headIcon = headIcon;
    }

    public String getMoon() {
        return moon;
    }

    public void setMoon(String moon) {
        this.moon = moon;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSigncount() {
        return signcount;
    }

    public void setSigncount(String signcount) {
        this.signcount = signcount;
    }
}
