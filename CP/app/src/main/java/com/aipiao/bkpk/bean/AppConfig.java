package com.aipiao.bkpk.bean;

/**
 * Created by Administrator on 2018/1/31.
 */
public class AppConfig {

    private String PushKey;

    private int AcceptCount;

    private String AppId;

    private String ShowWeb;

    private String Del;

    private String Url;

    private String Remark;

    public void setPushKey(String PushKey){
        this.PushKey = PushKey;
    }
    public String getPushKey(){
        return this.PushKey;
    }
    public void setAcceptCount(int AcceptCount){
        this.AcceptCount = AcceptCount;
    }
    public int getAcceptCount(){
        return this.AcceptCount;
    }
    public void setAppId(String AppId){
        this.AppId = AppId;
    }
    public String getAppId(){
        return this.AppId;
    }
    public void setShowWeb(String ShowWeb){
        this.ShowWeb = ShowWeb;
    }
    public String getShowWeb(){
        return this.ShowWeb;
    }
    public void setDel(String Del){
        this.Del = Del;
    }
    public String getDel(){
        return this.Del;
    }
    public void setUrl(String Url){
        this.Url = Url;
    }
    public String getUrl(){
        return this.Url;
    }
    public void setRemark(String Remark){
        this.Remark = Remark;
    }
    public String getRemark(){
        return this.Remark;
    }

}

