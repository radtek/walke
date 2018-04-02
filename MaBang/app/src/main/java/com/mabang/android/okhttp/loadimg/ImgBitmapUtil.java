package com.mabang.android.okhttp.loadimg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Walke.Z
 * on 2017/7/2. 01.
 * email：1126648815@qq.com
 */
public class ImgBitmapUtil {

    public static int default_size=300;

    public static Bitmap getBitmap(String filePath) {
        //第一采样
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int oldWidth = options.outWidth;
        int outHeight = options.outHeight;
        int sampleSize = 1;
        sampleSize=oldWidth/default_size;//原图的款除以，目标宽度
        //第二次采样
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        return bitmap;
    }

    public static Bitmap getBitmap(byte[] bytes, int destWidth) {
        //第一采样
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        //BitmapFactory.decodeFile(filePath, options);
        int oldWidth = options.outWidth;
        int outHeight = options.outHeight;
        int sampleSize = 1;
       /* while ((oldWidth / sampleSize > destWidth)) {
            sampleSize *= 2;
        }*/
        sampleSize=oldWidth/destWidth;
        //第二次采样
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        return bitmap;
    }

}
