package com.aipiao.bkpkold.bean.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by caihui on 2018/3/28.
 */

public class UpdateApp extends BmobObject {

    private  String desc;
    private  String isupdate;
    private  String url;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIsupdate() {
        return isupdate;
    }

    public void setIsupdate(String isupdate) {
        this.isupdate = isupdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
