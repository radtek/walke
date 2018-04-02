package com.mabang.android.okhttp;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;
import com.mabang.android.config.Api;
import com.mabang.android.config.Constants;
import com.mabang.android.config.Request;
import com.mabang.android.entity.vo.Message;
import com.mabang.android.entity.vo.VoBase;
import com.mabang.android.okhttp.callback.Callback;
import com.mabang.android.utils.AppUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import walke.base.tool.AESUtil;
import walke.base.tool.CompressUtil;
import walke.base.tool.LoadingDialog;
import walke.base.tool.MD5Util;
import walke.base.tool.NetWorkUtil;
import walke.base.tool.SharepreUtil;
import walke.base.tool.ToastUtil;

/**
 * @author View
 * @date 2016/11/27 15:41
 */
public class HttpReuqest {

    public interface CallBack<T> {

        void onSuccess(Message message, T result);

        void onError(Exception exc);

        void onFinish();

    }

    private AppCompatActivity mActivity;

    public HttpReuqest(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }

    @NonNull
    private String getPostHeader() {
        String s = getPhoneSystem() + " " + getPacakename() + "/" + AppUtils.getVersionName(mActivity);
        return s;
    }

    public String getPacakename() {
        String pacakeName = mActivity.getPackageName().replaceAll("\\.", "_");
        return pacakeName;
    }

    public String getPhoneSystem() {
        String userAgentString = new WebView(mActivity).getSettings().getUserAgentString();
        return userAgentString;
        /*String release = Build.VERSION.RELEASE;
        icon_return "Android/"+ release;*/
    }

    public synchronized void sendMessage(final String type, Object param, boolean showDialog, final CallBack myCallBack) {
        if (mActivity!=null&&!mActivity.isFinishing()) {
            Dialog loginLoadDialog = LoadingDialog.createDialog(mActivity, "请稍等");//
            if (showDialog) {
                loginLoadDialog.show();//这个应该避免error: View not attached to window manager，建议在onCreate稍后一点执行
            } else {
                loginLoadDialog.dismiss();
            }
        }
        Message message = new Message();
        message.setType(type);
        message.setVersion(Api.VERSION);
        if (param != null) {
            if (param instanceof String)
                message.setMessage(param.toString());
            else {
                String voMessage = new GsonBuilder().create().toJson(param);
                message.setMessage(voMessage);
            }
        }
        Log.i("walke", "sendMessage:---------------->>> 请求类型 type=" + type + " 请求体：------>>> message = " + message.getMessage());
        String sign = MD5Util.MD5Encode(message.getId() + message.getVersion() + message.getType() + message.getMessage() + Api.CLIENT_SALT);
        message.setSign(sign);
        String messageJson = new GsonBuilder().create().toJson(message);
        String baseUrl = null;
        if (type.startsWith("20")) {
            baseUrl = Request.API_URL_WORKER;
        } else {
            baseUrl = Request.API_URL_USER;
        }
        try {
            byte[] data = CompressUtil.compress(messageJson);
            data = AESUtil.encrypt(data, Api.SECRET_KEY);
            OkHttpUtils
                    .post()
                    .url(baseUrl)
                    .addHeader("User-Agent", getPostHeader())
                    .addParams(Api.PARAMS_KEY, MD5Util.MD5Encode(Api.KEY))
                    .addParams(Api.PARAM_MESSAGE, Base64.encodeToString(data, Base64.DEFAULT))
                    .build()
                    .execute(new Callback<String>() {
                        @Override
                        public String parseNetworkResponse(Response response, int id) throws Exception {
                            return response.body().string();
                        }

                        @Override
                        public void onError(Call call, Exception exc, int id) {
                            if (mActivity!=null&&!mActivity.isFinishing())
                                LoadingDialog.dialogDismiss(mActivity);
                            if (myCallBack != null) {
                                myCallBack.onError(exc);
                                if (!NetWorkUtil.isNetworkConnected(mActivity))
                                    ToastUtil.showToast(mActivity, Constants.ERROR_NO_NET);
                                else if (exc instanceof SocketTimeoutException)
                                    ToastUtil.showToast(mActivity, "请求超时，请重试");//e.getMessage() + ""
                                else if (exc instanceof IOException)
                                    ToastUtil.showToast(mActivity, "连接服务器异常");//e.getMessage() + ""
                                else
                                    ToastUtil.showToast(mActivity, Constants.ERROR_SERVICE_BACK);//e.getMessage() + ""
                                myCallBack.onFinish();
                            }
                            return;
                        }

                        @Override
                        public void onResponse(String result, int id) {
                            try {
                                if (mActivity!=null&&!mActivity.isFinishing())
                                    LoadingDialog.dialogDismiss(mActivity);

                                if (result == null || TextUtils.isEmpty(result)) {
                                    if (myCallBack != null) {
                                        myCallBack.onError(new Exception("服务器返回空字符"));
                                        ToastUtil.showToast(mActivity, Constants.ERROR_SERVICE);
                                        myCallBack.onFinish();
                                    }
                                    return;
                                }
                                byte[] data = Base64.decode(result, Base64.DEFAULT);
                                data = AESUtil.decrypt(data, Api.SECRET_KEY);
                                String messageJson = CompressUtil.decompress(data);
                                Message message = new Gson().fromJson(messageJson, Message.class);

                                if (myCallBack != null) {
                                    if (message == null) {
                                        throw new Exception(Constants.ERROR_SERVICE_BACK_DATA);
                                    } else {
                                        String sign = MD5Util.MD5Encode(message.getId() + message.getVersion() + message.getType() + message.getMessage() + Api.CLIENT_SALT);
                                        if (sign == null || message.getSign() == null || !sign.equalsIgnoreCase(message.getSign()))
                                            throw new Exception("数据验签失败");

                                        Object paramBack = null;
                                        if (message.getMessage() != null) {
                                            Type[] types = myCallBack.getClass().getGenericInterfaces();
                                            if (types.length > 0) {
                                                Type type = ((ParameterizedType) types[0]).getActualTypeArguments()[0];
                                                try {
                                                    paramBack = new Gson().fromJson(message.getMessage(), type);
                                                } catch (JsonSyntaxException ex) {
                                                    throw new Exception(message.getMessage());
                                                }
                                                if (paramBack != null && (paramBack instanceof LinkedTreeMap || (paramBack instanceof ArrayList && ((ArrayList) paramBack).size() > 0) && ((ArrayList) paramBack).get(0) instanceof LinkedTreeMap))
                                                    throw new Exception(Constants.ERROR_SERVICE_BACK_DATA_CAST);
                                            }
                                        }
                                        if (paramBack instanceof VoBase) {
                                            VoBase resultVoBase = (VoBase) paramBack;
                                            if (Api.VERSION_ERROR == resultVoBase.getCode()) {
                                                // TODO: 2017/3/1 //版本异常
                                            } else if (Api.NO_LOGIN == resultVoBase.getCode()) {
                                                // TODO: 2017/3/1  未登录
                                            }
                                            {
                                            }
                                        }
                                        if (paramBack == null)
                                            myCallBack.onError(new Exception("请求返回：result==null"));
                                        else
                                            myCallBack.onSuccess(message, paramBack);
                                    }
                                }
                            } catch (Exception e) {
                                Log.e("请求服务器回调处理异常", "" + e);
                                if (myCallBack != null) {
                                    myCallBack.onError(e);
                                    myCallBack.onFinish();
                                }
                            }
                            if (myCallBack != null) {
                                myCallBack.onFinish();
                            }
                            return;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            if (mActivity!=null&&!mActivity.isFinishing())
                LoadingDialog.dialogDismiss(mActivity);
        }
        return;
    }

//    public synchronized void sendMessageWorker(final String type, Object param, final CallBack myCallBack) {
//        if (mActivity!=null&&!mActivity.isFinishing()) {
//            Dialog loginLoadDialog = LoadingDialog.createDialog(mActivity, "请稍等");
//            loginLoadDialog.show();
//        }
//        Message message = new Message();
//        message.setType(type);
//        message.setVersion(Api.VERSION);
//        if (param != null) {
//            if (param instanceof String)
//                message.setMessage(param.toString());
//            else {
//                String voMessage = new GsonBuilder().create().toJson(param);
//                message.setMessage(voMessage);
//            }
//        }
//        String sign = MD5Util.MD5Encode(message.getId() + message.getVersion() + message.getType() + message.getMessage() + Api.CLIENT_SALT);
//        message.setSign(sign);
//        String messageJson = new GsonBuilder().create().toJson(message);
//        try {
//            byte[] data = CompressUtil.compress(messageJson);
//            data = AESUtil.encrypt(data, Api.SECRET_KEY);
//            OkHttpUtils
//                    .post()
//                    .url(Request.API_URL_WORKER)
//                    .addHeader("User-Agent", getPostHeader())
//                    .addParams(Api.PARAMS_KEY, MD5Util.MD5Encode(Api.KEY))
//                    .addParams(Api.PARAM_MESSAGE, Base64.encodeToString(data, Base64.DEFAULT))
//                    .build()
//                    .execute(new Callback<String>() {
//                        @Override
//                        public String parseNetworkResponse(Response response, int id) throws Exception {
//                            return response.body().string();
//                        }
//
//                        @Override
//                        public void onError(Call call, Exception exc, int id) {
//                            if (mActivity!=null&&!mActivity.isFinishing())
//                                LoadingDialog.dialogDismiss(mActivity);
//                            if (myCallBack != null) {
//                                myCallBack.onError(exc);
//                                if (!NetWorkUtil.isNetworkConnected(mActivity))
//                                    ToastUtil.showToast(mActivity, Constants.ERROR_NO_NET);
//                                else if (exc instanceof SocketTimeoutException)
//                                    ToastUtil.showToast(mActivity, "请求超时，请重试");//e.getMessage() + ""
//                                else
//                                    ToastUtil.showToast(mActivity, Constants.ERROR_SERVICE_BACK);//e.getMessage() + ""
//                                myCallBack.onFinish();
//                            }
//                            return;
//                        }
//
//                        @Override
//                        public void onResponse(String result, int id) {
//                            try {
//                                if (mActivity!=null&&!mActivity.isFinishing())
//                                    LoadingDialog.dialogDismiss(mActivity);
//
//                                if (result == null || TextUtils.isEmpty(result)) {
//                                    if (myCallBack != null) {
//                                        myCallBack.onError(new Exception(Constants.ERROR_SERVICE));
//                                        ToastUtil.showToast(mActivity, Constants.ERROR_SERVICE);
//                                        myCallBack.onFinish();
//                                    }
//                                    return;
//                                }
//                                byte[] data = Base64.decode(result, Base64.DEFAULT);
//                                data = AESUtil.decrypt(data, Api.SECRET_KEY);
//                                String messageJson = CompressUtil.decompress(data);
//                                Message message = new Gson().fromJson(messageJson, Message.class);
//
//                                if (myCallBack != null) {
//                                    if (message == null) {
//                                        throw new Exception(Constants.ERROR_SERVICE_BACK_DATA);
//                                    } else {
//                                        String sign = MD5Util.MD5Encode(message.getId() + message.getVersion() + message.getType() + message.getMessage() + Api.CLIENT_SALT);
//                                        if (sign == null || message.getSign() == null || !sign.equalsIgnoreCase(message.getSign()))
//                                            throw new Exception("数据验签失败");
//
//                                        Object paramBack = null;
//                                        if (message.getMessage() != null) {
//                                            Type[] types = myCallBack.getClass().getGenericInterfaces();
//                                            if (types.length > 0) {
//                                                Type type = ((ParameterizedType) types[0]).getActualTypeArguments()[0];
//                                                try {
//                                                    paramBack = new Gson().fromJson(message.getMessage(), type);
//                                                } catch (JsonSyntaxException ex) {
//                                                    throw new Exception(message.getMessage());
//                                                }
//                                                if (paramBack != null && (paramBack instanceof LinkedTreeMap || (paramBack instanceof ArrayList && ((ArrayList) paramBack).size() > 0) && ((ArrayList) paramBack).get(0) instanceof LinkedTreeMap))
//                                                    throw new Exception(Constants.ERROR_SERVICE_BACK_DATA_CAST);
//                                            }
//                                        }
//                                        if (paramBack instanceof VoBase) {
//                                            VoBase resultVoBase = (VoBase) paramBack;
//                                            if (Api.VERSION_ERROR == resultVoBase.getCode()) {
//                                                // TODO: 2017/3/1 //版本异常
//                                            } else if (Api.NO_LOGIN == resultVoBase.getCode()) {
//                                                // TODO: 2017/3/1  未登录
//                                            }
//                                            {
//                                            }
//                                        }
//                                        if (paramBack == null)
//                                            myCallBack.onError(new Exception("请求返回：result==null"));
//                                        else
//                                            myCallBack.onSuccess(message, paramBack);
//                                    }
//                                }
//                            } catch (Exception e) {
//                                Log.e("请求服务器回调处理异常", "" + e);
//                                if (myCallBack != null) {
//                                    myCallBack.onError(e);
//                                    myCallBack.onFinish();
//                                }
//                            }
//                            if (myCallBack != null) {
//                                myCallBack.onFinish();
//                            }
//                            return;
//                        }
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (mActivity!=null&&!mActivity.isFinishing())
//                LoadingDialog.dialogDismiss(mActivity);
//        }
//        return;
//    }

    /**
     * 获取标准 Cookie ，并存储
     * 用：saveCookie(OkHttpUtils.getInstance().getOkHttpClient(), HttpUrl.parse(mUrl));
     *
     * @param httpClient
     * @param httpUrl
     */
    public void saveCookie(OkHttpClient httpClient, HttpUrl httpUrl) {
        List<Cookie> cookies = httpClient.cookieJar().loadForRequest(httpUrl);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = cookies.get(i);
            String cookieStr = cookie.toString();
            if (!TextUtils.isEmpty(cookieStr)) {
                sb.append(cookieStr + "=");//最后的“=”不能少
            }
        }
        SharepreUtil.putString(mActivity, Constants.WEBVIEW_COOKIE, sb.toString());
    }

}
