package com.game4u.arwinebottle.bean;

/**
 * Created by walke.Z on 2017/11/11.
 */

public class RegisterResult {

    /**
     * result : {"func":"register","servertime":"2017-11-11 17:13:48","status":1}
     * userInfo : {}
     * code : 1
     * desc : 注册成功！
     */

    private ResultBean result;
    private UserInfoBean userInfo;
    private int code;
    private String desc;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static class ResultBean {
        /**
         * func : register
         * servertime : 2017-11-11 17:13:48
         * status : 1
         */

        private String func;
        private String servertime;
        private int status;

        public String getFunc() {
            return func;
        }

        public void setFunc(String func) {
            this.func = func;
        }

        public String getServertime() {
            return servertime;
        }

        public void setServertime(String servertime) {
            this.servertime = servertime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class UserInfoBean {
    }
}
