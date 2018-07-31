package com.grandsea.ticketvendingapplication.util;

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
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getSharedPreferences(context).getBoolean(key, defValue);
    }
    public static void putString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key,String strDefault) {
        return getSharedPreferences(context).getString(key, strDefault);
    }

    public static void putInt(Context context, String key, int id) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,id);
        editor.commit();
    }

    public static int getInt(Context context, String key,int intDefault) {
        return getSharedPreferences(context).getInt(key,intDefault);
    }

}
