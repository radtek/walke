package com.game4u.arwinebottle.bean;

import java.io.Serializable;

/**
 * Created by walke.Z on 2017/11/11.
 */

public class ResultBean implements Serializable {

    /**
     * result : {"func":"login","servertime":"2017-11-11 19:07:29","status":1}
     * code : -2
     * desc : 验证码不正确
     */

    private Result result;
    private int code;
    private String desc;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
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

    public class Result {
        /**
         * func : login
         * servertime : 2017-11-11 19:07:29
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
}
