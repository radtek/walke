//package com.grandsea.ticketvendingapplication.net;
//
//import android.content.Context;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.grandsea.ticketvendingapplication.constant.UrlConstant;
//import com.grandsea.ticketvendingapplication.model.bean.City;
//import com.grandsea.ticketvendingapplication.model.common.GsonObject;
//import com.grandsea.ticketvendingapplication.util.BasicUtil;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.callback.StringCallback;
//
//import java.util.HashMap;
//import java.util.List;
//
//import okhttp3.Call;
//import okhttp3.MediaType;
//
//import static com.grandsea.ticketvendingapplication.adapter.GsonAdapter.gson;
//
///**
// * Created by hui on 2017/9/25.
// */
//
//public class HttpRequest {
//   private static final String TAG = "HttpRequest";
//    private static HttpRequest instance;
//
//    public static synchronized HttpRequest getInstance() {
//        if (null==instance)
//            instance=new HttpRequest();
//        return instance;
//    }
//
//   interface CallBack<T>{
//      void success(T result);
//      void error(Exception e);
//   }
//
//   public static void post(final Context context, final CallBack<Object> callBack){
//      OkHttpUtils
//              .postString()
//              .url(UrlConstant.AIRPORT_CITIES)
//              .mediaType(MediaType.parse("application/json; charset=utf-8"))
//              .content(new Gson().toJson(BasicUtil.buildPostData(new HashMap())))
//              .build()
//              .execute(new StringCallback(){
//
//                 @Override
//                 public void onError(Call call, Exception e, int i) {
//                    Log.d(TAG,e.toString());
//                    Toast.makeText(context,"网络异常，请重新刷新", Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                    callBack.error(e);
//                 }
//
//                 @Override
//                 public void onResponse(String s, final int i) {
//                    Log.e(TAG, "onResponse：complete");
//                    Log.d(TAG,s);
//                    GsonObject dataGson = new GsonObject(gson.fromJson(s, JsonObject.class)); //对数据转换成json后进行对象封装
//
//                    JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
//                    List<City> cities = dataGson.getAsList(gson, "cities", City.class);
//
//                 }
//              });
//   }
//}
