package com.game4u.arwinebottle.bean;

/**
 * Created by walke.Z on 2017/11/7.
 */

public class UserInfo extends VOBase {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

   /* @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                '}';
    }*/
     private String phone;
    private String password;
    private String salesCode;

    public UserInfo() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalesCode() {
        return salesCode;
    }

    public void setSalesCode(String salesCode) {
        this.salesCode = salesCode;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", salesCode='" + salesCode + '\'' +
                '}';
    }
}
