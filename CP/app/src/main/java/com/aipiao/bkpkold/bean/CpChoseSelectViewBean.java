package com.aipiao.bkpkold.bean;

import com.aipiao.bkpkold.views.choseview.SelectBallsView;

/**
 * Created by caihui on 2018/3/23.
 */

public class CpChoseSelectViewBean {

    private String numberType;
    private SelectBallsView selectBallsView;
    private String type;

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }

    public SelectBallsView getSelectBallsView() {
        return selectBallsView;
    }

    public void setSelectBallsView(SelectBallsView selectBallsView) {
        this.selectBallsView = selectBallsView;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
