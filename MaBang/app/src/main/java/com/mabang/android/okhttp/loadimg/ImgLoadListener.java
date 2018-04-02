package com.mabang.android.okhttp.loadimg;

import android.graphics.Bitmap;

/**
 * Created by caihui on 2016/10/8.
 */
public interface ImgLoadListener {

    /**
     * @param startSize
     * @param fileSize  完善后应当不要这个
     */
    void loadStart(int startSize, int fileSize);

    /**
     * @param current
     * @param total 当total=-1，从网络连接中获取内容长度失败，个别文件例如
     *              http://pic1.win4000.com/wallpaper/8/51bb08e7a700a.jpg
     */
    void loading(int current, int total);

    void loadStop(Exception exc);

    void loadFinish(Bitmap bitmap);

}
