package com.grandsea.ticketvendingapplication.net;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.grandsea.ticketvendingapplication.base.BaseActivity;
import com.grandsea.ticketvendingapplication.constant.UrlConstant;
import com.grandsea.ticketvendingapplication.model.bean.LockTicketStatus;
import com.grandsea.ticketvendingapplication.model.common.GsonObject;
import com.grandsea.ticketvendingapplication.util.BasicUtil;
import com.grandsea.ticketvendingapplication.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;

import static com.grandsea.ticketvendingapplication.adapter.GsonAdapter.gson;

/**
 * Created by Grandsea09 on 2017/10/7.
 */

public class RequestUtil {
    private static final String TAG = "RequestUtil";
    private static RequestUtil instance;//不需要单例 因为OkHttpUtils已经是封装好的单例了

    public static synchronized RequestUtil getInstance() {
        if (null==instance)
            instance=new RequestUtil();
        return instance;
    }

    public interface CallBack{
        void error(Exception e);
        void success(GsonObject gsonObject);
    }

    public static void sendRequest(final Context context,String url ,final CallBack callBack){
        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(new Gson().toJson(BasicUtil.buildPostData((BaseActivity) context,new HashMap())))
                .build()
                .execute(new StringCallback(){

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        Log.d(TAG,e.toString());
                        Toast.makeText(context,"网络异常，请重新刷新", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                        callBack.error(e);
                    }

                    @Override
                    public void onResponse(String s, final int i) {
                        Log.e(TAG, "onResponse：complete");
                        Log.d(TAG,s);
                        GsonObject dataGson = new GsonObject(gson.fromJson(s, JsonObject.class)); //对数据转换成json后进行对象封装
                        callBack.success(dataGson);

                    }
                });
    }

    public static void cancelOrderById(final Context context, String orderId) {

        Map<String,Object> paramMap = new HashMap();
        paramMap.put("orderId",orderId);//
        String requestContent =new Gson().toJson(BasicUtil.buildPostData((BaseActivity) context,paramMap)) ;
        Log.d(TAG,requestContent);
        OkHttpUtils
                .postString()
                .url(UrlConstant.CANCEL_ORDER_TICKET)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(requestContent)
                .build()
                .execute(new StringCallback(){
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtil.showToast(context,"网络异常，请一分钟后重新下单");//刷新
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(String s, int i) {
                        GsonObject dataGson = new GsonObject(gson.fromJson(s, JsonObject.class)); //对数据转换成json后进行对象封装
                        int status = dataGson.getInt("status", -1);
                        if (status == LockTicketStatus.SUCCESS) {

                        } else{
                            //ToastUtil.showToast(context,"网络异常，请一分钟后重新下单");//刷新
                        }
                    }
                });
    }



}
