package com.mabang.android.okhttp;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.mabang.android.okhttp.callback.BitmapCallback;

import okhttp3.Call;

/**
 * @author View
 * @date 2016/12/16 9:54
 */
public class OkImageUtil {

    public static void loadImage(String url, final ImageView imageView) {
        OkHttpUtils
                .get()//
                .url(url)//
                .build()//
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        if (response != null) {
                            imageView.setImageBitmap(response);

                        }
                    }
                });


    }

    public static void loadImage(String url, final ImageView imageView, final CallBack callBack) {
        OkHttpUtils
                .get()//
                .url(url)//
                .build()//
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (callBack != null) {
                            callBack.onError();
                        }
                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        if (response != null) {
                            if (callBack != null) {
                                callBack.onSuccess();
                            }
                            imageView.setImageBitmap(response);

                        }
                    }
                });


    }
    public static void loadImage(String url, final ImgCallBack imgCallBack) {
        OkHttpUtils
                .get()//
                .url(url)//
                .build()//
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (imgCallBack != null) {
                            imgCallBack.onError();
                        }
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id) {
                        if (bitmap != null) {
                            if (imgCallBack != null) {
                                imgCallBack.onSuccess(bitmap);
                            }
                        }
                    }
                });
    }

    public interface CallBack {
        void onSuccess();

        void onError();
    }

    public interface ImgCallBack {
        void onSuccess(Bitmap bitmap);

        void onError();
    }
}
