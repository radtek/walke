package com.mabang.android.okhttp.builder;

import java.util.Map;

/**
 * Created by View on 2016 11/14
 */
public interface HasParamsable
{
    OkHttpRequestBuilder params(Map<String, String> params);
    OkHttpRequestBuilder addParams(String key, String val);
}
