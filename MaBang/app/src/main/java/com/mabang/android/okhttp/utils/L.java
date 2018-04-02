package com.mabang.android.okhttp.utils;

import android.util.Log;

/**
 * Created by View on 2016 11/14
 */
public class L
{
    private static boolean debug = false;

    public static void e(String msg)
    {
        if (debug)
        {
            Log.e("OkHttp", msg);
        }
    }

}

