package com.aipiao.bkpkold.bean;

/**
 * Created by Administrator on 2018/1/31.
 */
public class Root {
    private boolean success;

    private AppConfig AppConfig;

    public void setSuccess(boolean success){
        this.success = success;
    }
    public boolean getSuccess(){
        return this.success;
    }
    public void setAppConfig(AppConfig AppConfig){
        this.AppConfig = AppConfig;
    }
    public AppConfig getAppConfig(){
        return this.AppConfig;
    }
}
