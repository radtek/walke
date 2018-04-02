package com.mabang.android.okhttp.loadimg;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by walke.Z on 2018/3/12.
 */

public class BitmapCache {

    private LruCache<String, Bitmap> bitmapLruCache;

    private static BitmapCache instance;

    private BitmapCache() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 6;
        bitmapLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes()* bitmap.getHeight();//单位 B
            }
        };
    }

    public static BitmapCache getInstance() {
        if (instance==null)
            instance=new BitmapCache();
        return instance;
    }

    public void putBitmap(String key, Bitmap bitmap) {
        bitmapLruCache.put(key, bitmap);
    }

    public Bitmap getBitmap(String key) {
        return bitmapLruCache.get(key);
    }
}
