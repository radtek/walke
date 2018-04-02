package com.mabang.android.okhttp.builder;


import com.mabang.android.okhttp.OkHttpUtils;
import com.mabang.android.okhttp.request.OtherRequest;
import com.mabang.android.okhttp.request.RequestCall;

/**
 * Created by View on 2016 11/14
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
