package com.mabang.android.okhttp.callback;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

import okhttp3.Response;

/**
 * @author View
 * @date 2016/11/27 16:34
 */
public abstract class JsonCallback<T> extends Callback<T> {
    protected Class<T> clazz;

    public JsonCallback() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        if (pt != null && pt.getActualTypeArguments().length > 0) {
            this.clazz = (Class) pt.getActualTypeArguments()[0];
        }
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        T t = new Gson().fromJson(string, clazz);
        return t;
    }
}
