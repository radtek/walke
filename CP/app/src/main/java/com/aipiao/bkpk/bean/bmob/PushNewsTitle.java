package com.aipiao.bkpk.bean.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by caihui on 2018/3/20.
 */

public class PushNewsTitle extends BmobObject {

    private  String title;
    private String Type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
