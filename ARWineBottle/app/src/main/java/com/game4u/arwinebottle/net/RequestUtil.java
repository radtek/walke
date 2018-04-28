package com.game4u.arwinebottle.net;

import android.util.Log;
import android.widget.Toast;

import com.game4u.arwinebottle.bean.ResultLogin;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.MediaType;
import walke.base.BaseActivity;

/**
 * Created by walke.Z on 2017/11/7.
 */

public class RequestUtil {

    private static final String TAG = "RequestUtil";
    private static RequestUtil instance;
    private static BaseActivity mBaseActivity;

    public synchronized static RequestUtil getInstance(BaseActivity baseActivity) {
        if (instance == null) {
            instance = new RequestUtil();
            mBaseActivity = baseActivity;
        }
        return instance;
    }




    public interface MyCallBack<T> {
        void onError(Exception exc);

        void onSuccess(T reponse);

        void onSuccess(String str);
    }


    public void register(String api, Object param, String orther, final MyCallBack myCallback) {
//        String header = Header.getHeader(mBaseActivity);
        String contentStr = ContentData.registerContent(mBaseActivity,param,orther);//formatContentOld
        String url = Api.BASE_URL + api;
        Log.i(TAG, "register: ----------------------------- start");
        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
//                .addHeader("phead", header)
                .content(contentStr)//new Gson().toJson(param)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        String name = Thread.currentThread().getName();
                        Toast.makeText(mBaseActivity,"网络异常，请重新刷新", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                        myCallback.onError(e);
                    }

                    @Override//{"result":{"msg":"Service Exception","errorcode":0,"status":-1}}
                    public void onResponse(String response, int id) {
                        //{"result":{"func":"register","servertime":"2017-11-11 17:13:48","status":1},"userInfo":{},"code":1,"desc":"注册成功！"}
                        String name = Thread.currentThread().getName();
                        myCallback.onSuccess(response);
                        try {
                            ResultLogin resultLogin = new Gson().fromJson(response, ResultLogin.class);

                            myCallback.onSuccess(resultLogin);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            myCallback.onSuccess(null);
                        }
                    }
                });

    }

    public void login(String api, String phone, String password,String code, final MyCallBack myCallback) {

        String contentStr = ContentData.loginContent(mBaseActivity,phone,password,code);//formatContentOld
        String url = Api.BASE_URL + api;
        Log.i(TAG, "login: ----------------------------- start");
        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(contentStr)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        String name = Thread.currentThread().getName();//main
                        Toast.makeText(mBaseActivity,"网络异常，请重新刷新", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                        myCallback.onError(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //{"result":{"func":"login","servertime":"2017-11-13 11:24:48","status":1},"code":-1,"desc":"账号或密码错误！"}
                        //{"result":{"func":"login","servertime":"2017-11-13 11:34:07","status":1},"userInfo":{},"code":1,"desc":"登录成功"}
                        String name = Thread.currentThread().getName();//main
                        myCallback.onSuccess(response);

                        Object o = new Gson().fromJson(response, Object.class);

                        try {
//                            ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                            ResultLogin resultLogin = new Gson().fromJson(response, ResultLogin.class);

//                            Object o = new Gson().fromJson(response, Object.class);

                            myCallback.onSuccess(resultLogin);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            myCallback.onSuccess(null);
                        }
                    }
                });

    }

    public void resetPassword(String phone, String password,String code, final MyCallBack myCallback) {

        String contentStr = ContentData.resetPasswordContent(mBaseActivity,phone,password,code);//formatContentOld
        String url = Api.BASE_URL + Api.RESET_PASSWORD;
        Log.i(TAG, "login: ----------------------------- start");
        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(contentStr)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mBaseActivity,"网络异常，请重新刷新", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                        myCallback.onError(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //{"result":{"func":"updatePwd","servertime":"2017-11-13 14:45","status":1},"code":1,"desc":"密码修改成功"}
                        myCallback.onSuccess(response);
                        try {
                            ResultLogin resultLogin = new Gson().fromJson(response, ResultLogin.class);
                            myCallback.onSuccess(resultLogin);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            myCallback.onSuccess(null);
                        }
                    }
                });

    }

    public void phoneAuthCode(String phone, final MyCallBack<ResultLogin> myCallBack) {
        String contentStr = ContentData.phoneAuthCodeContent(mBaseActivity,phone);//formatContentOld
        String url = Api.BASE_URL + Api.SMS;
        Log.i(TAG, "login: ----------------------------- start");
        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(contentStr)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mBaseActivity,"网络异常，请重新刷新", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                        myCallBack.onError(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        myCallBack.onSuccess(response);
                        try {
                            ResultLogin resultLogin = new Gson().fromJson(response, ResultLogin.class);
                            myCallBack.onSuccess(resultLogin);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            //myCallBack.onSuccess(null);
                        }
                    }
                });
    }


}
