package com.mabang.android.okhttp.utils;

/**
 * Created by View on 2016 11/14
 */
public class Exceptions
{
    public static void illegalArgument(String msg, Object... params)
    {
        throw new IllegalArgumentException(String.format(msg, params));
    }


}
