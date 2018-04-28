package com.game4u.arwinebottle.bean;

/**
 * Created by walke.Z on 2017/11/13.
 */

public class ResultLogin extends ResultBean {

    private UserInfo userInfo;//用户信息

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "ResultLogin{" +
                "userInfo=" + userInfo +
                '}';
    }
}
