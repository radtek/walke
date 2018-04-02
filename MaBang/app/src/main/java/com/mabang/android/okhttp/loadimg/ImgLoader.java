package com.mabang.android.okhttp.loadimg;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.mabang.android.R;

import java.io.File;


/**
 * Created by walke on 2018/3/11.
 * email:1032458982@qq.com
 */

public class ImgLoader {

    /**
     * 文件保存路径
     */
    private String mSaveDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AHui/img";
    private String mTempDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AHui/temp";

    private ImgLoader() {

    }

    private static ImgLoader instance;

    public static ImgLoader getInstance() {

        if (instance == null)
            instance = new ImgLoader();
        return instance;
    }

    public ImgLoader setSaveDir(String saveDir) {
        mSaveDir = saveDir+"/img";
        return instance;
    }

    public String getSaveDir() {
        return mSaveDir;
    }

    public String getTempDir() {
        return mTempDir;
    }

    public void setTempDir(String tempDir) {
        mTempDir = tempDir+"/temp";
    }

    public void loadImage(Context context, String imgUrl, String cacheKey, final ImageView iv) {
        if (TextUtils.isEmpty(imgUrl)) {
            iv.setImageResource(R.mipmap.error);
            return;
        }
        if (BitmapCache.getInstance().getBitmap(cacheKey) != null) {//1.先从缓存获取bitmap
            iv.setImageBitmap(BitmapCache.getInstance().getBitmap(cacheKey));//如果缓存有直接 设置给控件
        } else if (getFileBitmap(imgUrl) != null) {//2.从文件系统中获取bitmap
            BitmapCache.getInstance().putBitmap(cacheKey, getFileBitmap(imgUrl));//如果文件系统中存在放入缓存
            iv.setImageBitmap(getFileBitmap(imgUrl));//然后直接 设置给控件
        } else {//3.网络加载
            Log.i("walke", "loadImage: -----------------------------imgUrl = " + imgUrl);
            //③网络加载
            ImgLoaderHelper.getInstance(context).loadImg(imgUrl, cacheKey, new ImgLoadListener() {
                @Override
                public void loadStart(int startSize, int fileSize) {
                    Log.i("walke", "loadStart: ----------------------------startSize : " + startSize + "    fileSize = " + fileSize);
                }

                @Override
                public void loading(int current, int total) {
                    Log.i("walke", "loading: ----------------------------current : " + current + "    total = " + total);
                }

                @Override
                public void loadStop(Exception exc) {
                    Log.i("walke", "loadStop: ---------------------------- 下载失败exc : " + exc.getMessage());
                }

                /** 下载完成，在下载线程中做放入缓存、保存本地文件
                 * @param bitmap
                 */
                @Override
                public void loadFinish(Bitmap bitmap) {
                    Log.i("walke", "loadFinish: ---------------------------- 下载完成  ");
                    // 设置给控件
                    iv.setImageBitmap(bitmap);
                }
            });
        }
    }

    public Bitmap getFileBitmap(String key) {
        String md5EncodeImgName = ImgMD5Util.MD5Encode(key) + ".png";//954E4307684F4BEEDF34BBA21DE5A23E.png
        File imgFile = new File(mSaveDir, md5EncodeImgName);
        boolean exists = imgFile.exists();
        boolean b = ImgFileSizeUtil.getFileOrFilesSize(imgFile, ImgFileSizeUtil.SIZETYPE_B) > 0;
        if (exists && b) {
            Bitmap bitmap = ImgBitmapUtil.getBitmap(imgFile.getAbsolutePath());
            return bitmap;
        }
        return null;
    }

    public interface LoadingListener {
        /**
         * @param current
         * @param total 当total=-1，从网络连接中获取内容长度失败，个别文件例如 http://pic1.win4000.com/wallpaper/8/51bb08e7a700a.jpg
         */
        void loading(int current, int total);

        void loadError(Exception exc);

        void loadFinish();
    }


    public void loadImage(Context context, String imgUrl, String cacheKey, final ImageView iv, final LoadingListener loadingListener) {
        if (TextUtils.isEmpty(imgUrl)) {
            iv.setImageResource(R.mipmap.error);
            if (loadingListener!=null)
                loadingListener.loadError(new Exception("图片地址为空"));
            return;
        }
        if (BitmapCache.getInstance().getBitmap(cacheKey) != null) {//1.先从缓存获取bitmap
            if (loadingListener!=null)
                loadingListener.loadFinish();
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setImageBitmap(BitmapCache.getInstance().getBitmap(cacheKey));//如果缓存有直接 设置给控件

        } else if (getFileBitmap(imgUrl) != null) {//2.从文件系统中获取bitmap
            if (loadingListener!=null)
                loadingListener.loadFinish();
            BitmapCache.getInstance().putBitmap(cacheKey, getFileBitmap(imgUrl));//如果文件系统中存在放入缓存
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setImageBitmap(getFileBitmap(imgUrl));//然后直接 设置给控件
        } else {//3.网络加载
            Log.i("walke", "loadImage: -----------------------------imgUrl = " + imgUrl);
            //③网络加载
            ImgLoaderHelper.getInstance(context).loadImg(imgUrl, cacheKey, new ImgLoadListener() {
                @Override
                public void loadStart(int startSize, int fileSize) {
                }

                @Override
                public void loading(int current, int total) {
                    if (loadingListener!=null)
                        loadingListener.loading(current,total);
                }

                @Override
                public void loadStop(Exception exc) {
                    if (loadingListener!=null)
                        loadingListener.loadError(exc);
                }

                /** 下载完成，在下载线程中做放入缓存、保存本地文件,避免ARN和OOM异常
                 * @param bitmap
                 */
                @Override
                public void loadFinish(Bitmap bitmap) {
                    if (loadingListener!=null)
                        loadingListener.loadFinish();
                    // 设置给控件
                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    iv.setImageBitmap(bitmap);


                }
            });
        }
    }

}
