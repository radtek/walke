package com.mabang.android.okhttp.loadapk;

/**
 * Created by caihui on 2016/9/26.
 */
public class ExceptionBean {

    private Exception mException;//下载异常
    private boolean isnetworking;//网络是否连接
    private int rspCode;//网络响应码

    public ExceptionBean() {
    }

    public ExceptionBean(Exception exception, boolean isnetworking) {
        mException = exception;
        this.isnetworking = isnetworking;

    }

    public ExceptionBean(Exception exception, boolean isnetworking, int rspCode) {
        mException = exception;
        this.isnetworking = isnetworking;
        this.rspCode = rspCode;
    }

    public Exception getException() {
        return mException;
    }

    public void setException(Exception exception) {
        mException = exception;
    }

    public boolean isnetworking() {
        return isnetworking;
    }

    public void setIsnetworking(boolean isnetworking) {
        this.isnetworking = isnetworking;
    }


    public int getRspCode() {
        return rspCode;
    }

    public void setRspCode(int rspCode) {
        this.rspCode = rspCode;
    }

    @Override
    public String toString() {
        return "ExceptionBean{" +
                "mException=" + mException +
                ", isnetworking=" + isnetworking +
                ", rspCode=" + rspCode +
                '}';
    }
}
