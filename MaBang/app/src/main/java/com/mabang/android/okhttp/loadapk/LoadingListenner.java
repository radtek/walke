package com.mabang.android.okhttp.loadapk;

/**
 * Created by caihui on 2016/10/8.
 */
public interface LoadingListenner {

    void loadStart(int startSize, int fileSize);

    void loading(int current, int total);

    void loadStop(ExceptionBean exceptionBean, int current, int total);

    void loadFinish(String url, String localPath);

}
