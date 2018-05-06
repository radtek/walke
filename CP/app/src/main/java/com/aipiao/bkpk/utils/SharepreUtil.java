package com.aipiao.bkpk.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharepreUtil {

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("isSave", Context.MODE_PRIVATE);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getSharedPreferences(context).getBoolean(key, defValue);
    }

    public static String getString(Context context, String key) {
        return getSharedPreferences(context).getString(key, null);
    }

    public static void putIntVioce(Context context, String key, int vioce) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,vioce);
        editor.commit();
    }

    public static int getIntVioce(Context context, String key, int defaultVoice) {
        return getSharedPreferences(context).getInt(key,defaultVoice);
    }

}
