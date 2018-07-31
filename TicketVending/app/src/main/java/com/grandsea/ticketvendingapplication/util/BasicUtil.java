package com.grandsea.ticketvendingapplication.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.grandsea.ticketvendingapplication.base.BaseActivity;
import com.grandsea.ticketvendingapplication.model.common.Phead;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/25.
 */
//基础工具：用来组装phead头部
public class BasicUtil {
    //本地测试用   58ff4eddda0e83185baa7eb7cc4fc5d1（从康）   587f33c9e4b06012ca6752cce8fd00e0 ()
    //private static final String UID= DefaultConfig.DEFAULT_UID;//TODO 自助售票机的id

    public static JsonObject buildCommonData(){
        JsonObject reqDataJson = new JsonObject();
        reqDataJson.addProperty("handle", 0);
        reqDataJson.addProperty("shandle", 0);
//        reqDataJson.addProperty("sign", 0);

        return reqDataJson;
    }


    /** 请求头
     * @param activity
     * @return
     */
//    public static JsonElement buildPhead(){
    public static JsonElement buildPhead(BaseActivity activity){
//        String uid = UID;
        String defaultUid = activity.getApp().getDefaultUid();
        String uid = defaultUid;
        Phead phead = new Phead();
        phead.setUid(uid);
        phead.setPhone("13588888888");
        phead.setPversion(1);
        phead.setCid(3);//
        phead.setCversion(1);
        phead.setCversionname("5.3");
        phead.setChannel(1);
        phead.setPositions("");
        phead.setEuid("0f3326eadcc11eff76b1e7f369f103b9");
        phead.setCoordinates("");
        phead.setRequesttime("1505815258528");

        return new Gson().toJsonTree(phead);
    }

    /**
     * @param activity
     * @param paramsBesidesPhead
     * @return
     */
    public static JsonObject buildPostData(BaseActivity activity,Map<String, Object> paramsBesidesPhead){
        JsonObject reqDataJson = buildCommonData();

        JsonObject dataJson = new JsonObject();
        dataJson.add("phead", buildPhead(activity));
        if(paramsBesidesPhead != null){
            for (String key : paramsBesidesPhead.keySet()) {
                Object param = paramsBesidesPhead.get(key);
                if(param instanceof Number){
                    dataJson.addProperty(key, (Number)param);
                }else if(param instanceof String){
                    dataJson.addProperty(key, (String)param);
                }else if(param instanceof Character){
                    dataJson.addProperty(key, (Character)param);
                }else if(param instanceof Boolean){
                    dataJson.addProperty(key, (Boolean)param);
                }else{
                    dataJson.add(key, new Gson().toJsonTree(param));
                }
            }
        }
        reqDataJson.add("data", dataJson);

        return reqDataJson;
    }

}
