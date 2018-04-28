package com.game4u.arwinebottle.config;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * 吾日三省吾身：看脸，看秤，看余额。
 * Created by lanso on 2017/1/20.
 * Glide图片框架 把 Bitmap 的格式转换到 ARGB_8888：
 * 然后在AndroidManifest.xml中将GlideModule定义为meta-data
     <application>节点下
      ……
      ……
      <meta-data android:name="com.guaguayi.android.util.GlideConfigUtil"
          android:value="GlideModule"/>
      </application>
 *
 *
 */
public class GlideConfigUtil implements GlideModule {
    /** 设置 配置信息、图片色彩格式、缓存大小位置等
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // Apply options to the builder here.
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

        int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
        int memoryCacheSize = maxMemory / 8;//设置图片内存缓存占用八分之一
        //设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));

    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }

}
