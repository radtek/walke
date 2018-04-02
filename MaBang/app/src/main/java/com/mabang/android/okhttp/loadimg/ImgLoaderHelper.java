package com.mabang.android.okhttp.loadimg;

import android.content.Context;
import android.util.Log;


/**
 * Created by caihui on 2016/9/23.
 */
public class ImgLoaderHelper {

    private ImgLoading mImgLoading;


    private static ImgLoaderHelper mInstance;

    private String loadUrl;

    private ImgLoaderHelper() {

    }

    public static ImgLoaderHelper getInstance(Context context) {

        return new ImgLoaderHelper();
    }

    public static ImgLoaderHelper getInstance() {
        if (mInstance == null) {
            mInstance = new ImgLoaderHelper();
        }
        return mInstance;
    }


    /**
     * @param imgUrl   要加载的目标文件地址
     * @param listener 构造方法传入接口实例，监听下载情况
     */
    public void loadImg(String imgUrl, String cacheKey, ImgLoadListener listener) {
        loadUrl = imgUrl;
        mImgLoading = new ImgLoading(imgUrl, cacheKey, listener);
        Log.i("walke", "loadImg: --------------------------- new ImgLoading()  imgUrl = " + imgUrl);
        mImgLoading.load();
    }
    /**
     * @param imgUrl   要加载的目标文件地址
     * @param listener 构造方法传入接口实例，监听下载情况
     */
    public void loadImg(String imgUrl, ImgLoadListener listener) {
        loadUrl = imgUrl;
        mImgLoading = new ImgLoading(imgUrl, imgUrl, listener);//已imgUrl做缓存key
        Log.i("walke", "loadImg: --------------------------- new ImgLoading()  imgUrl = " + imgUrl);
        mImgLoading.load();
    }

}
