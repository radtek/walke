package com.game4u.arwinebottle.net;

import android.util.Base64;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import walke.base.BaseActivity;

import static android.R.attr.data;

/**
 * Created by walke.Z on 2017/11/11.
 */

public class ContentData {




    /** 格式化注册请求内容
     * @param param
     * @param other
     * @return String 文本
     */
    public static String registerContent(BaseActivity activity, Object param, String other) {
        String str = "";
        Map<String,Object> dataMap=new HashMap<>();
        Map<String,Object> headerMap = Header.getHeaderMap(activity);
        dataMap.put("code", other);
        dataMap.put("phead", headerMap);
        dataMap.put("userInfo",param);
        //PRIVATE_KEY：-->yyyyyyyy
        String data = new Gson().toJson(dataMap) ;//  dataMap.toString()
        String md5Encode = MD5Utils.MD5Encode(Api.PRIVATE_KEY + data + Api.PRIVATE_KEY,"utf-8");
        Map<String, Object> map = new HashMap<>();
        // 对提交的数据进行处理
        // 默认0：无处理；1：采用gzip压缩（请求上传的数据量比较大（大于10k）时使用）；
        // 2：BASE64加密（涉及到采集用户本地数据的时候使用）；
        map.put("handle", 0);
        map.put("data", dataMap);//提交的json格式请求数据
        map.put("pkey", Api.KEY);// 通信key APP端必须，由接口提供方提供 xxxxxxxx
        map.put("sign", md5Encode);//数据校验码 APP端必须，String sign = md5(私钥+data数据+私钥);私钥由接口提供方提供
        map.put("shandle", 0); //server是否对返回的数据进行处理 默认1：采用gzip压缩；0：无处理；
        str=new Gson().toJson(map);
        return str;
    }


    /** 格式化login请求内容
     *
     * @param phone
     * @param password
     * @param code
     * @return String 文本
     */
    public static String loginContent(BaseActivity activity, String phone, String password, String code) {
        String str = "";
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("phone", phone);
        dataMap.put("password", password);
        dataMap.put("code",code);
        Map<String,Object> headerMap = Header.getHeaderMap(activity);
        dataMap.put("phead", headerMap);
        //PRIVATE_KEY：-->yyyyyyyy
        String data = new Gson().toJson(dataMap) ;//  dataMap.toString()
        String md5Encode = MD5Utils.MD5Encode(Api.PRIVATE_KEY + data + Api.PRIVATE_KEY,"utf-8");
        Map<String, Object> map = new HashMap<>();
        // 对提交的数据进行处理
        // 默认0：无处理；1：采用gzip压缩（请求上传的数据量比较大（大于10k）时使用）；
        // 2：BASE64加密（涉及到采集用户本地数据的时候使用）；
        map.put("handle", 0);
        map.put("data", dataMap);//提交的json格式请求数据
        map.put("pkey", Api.KEY);// 通信key APP端必须，由接口提供方提供 xxxxxxxx
        map.put("sign", md5Encode);//数据校验码 APP端必须，String sign = md5(私钥+data数据+私钥);私钥由接口提供方提供
        map.put("shandle", 0); //server是否对返回的数据进行处理 默认1：采用gzip压缩；0：无处理；
        str=new Gson().toJson(map);
        return str;
    }


    /** 格式化注册请求内容
     *
     * @param phone
     * @param password
     * @param code
     * @return String 文本
     */
    public static String resetPasswordContent(BaseActivity activity, String phone, String password, String code) {
        String str = "";
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("phone", phone);
        dataMap.put("password", password);
        dataMap.put("code",code);
        Map<String,Object> headerMap = Header.getHeaderMap(activity);
        dataMap.put("phead", headerMap);
        //PRIVATE_KEY：-->yyyyyyyy
        String data = new Gson().toJson(dataMap) ;//  dataMap.toString()
        String md5Encode = MD5Utils.MD5Encode(Api.PRIVATE_KEY + data + Api.PRIVATE_KEY,"utf-8");
        Map<String, Object> map = new HashMap<>();
        // 对提交的数据进行处理
        // 默认0：无处理；1：采用gzip压缩（请求上传的数据量比较大（大于10k）时使用）；
        // 2：BASE64加密（涉及到采集用户本地数据的时候使用）；
        map.put("handle", 0);
        map.put("data", dataMap);//提交的json格式请求数据
        map.put("pkey", Api.KEY);// 通信key APP端必须，由接口提供方提供 xxxxxxxx
        map.put("sign", md5Encode);//数据校验码 APP端必须，String sign = md5(私钥+data数据+私钥);私钥由接口提供方提供
        map.put("shandle", 0); //server是否对返回的数据进行处理 默认1：采用gzip压缩；0：无处理；
        str=new Gson().toJson(map);
        return str;
    }


    public static String phoneAuthCodeContent(BaseActivity activity, String phone) {
        String str = "";
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("phone", phone);
        Map<String,Object> headerMap = Header.getHeaderMap(activity);
        dataMap.put("phead", headerMap);
        //PRIVATE_KEY：-->yyyyyyyy
        String data = new Gson().toJson(dataMap) ;//  dataMap.toString()
        String md5Encode = MD5Utils.MD5Encode(Api.PRIVATE_KEY + data + Api.PRIVATE_KEY,"utf-8");
        Map<String, Object> map = new HashMap<>();
        // 对提交的数据进行处理
        // 默认0：无处理；1：采用gzip压缩（请求上传的数据量比较大（大于10k）时使用）；
        // 2：BASE64加密（涉及到采集用户本地数据的时候使用）；
        map.put("handle", 0);
        map.put("data", dataMap);//提交的json格式请求数据
        map.put("pkey", Api.KEY);// 通信key APP端必须，由接口提供方提供 xxxxxxxx
        map.put("sign", md5Encode);//数据校验码 APP端必须，String sign = md5(私钥+data数据+私钥);私钥由接口提供方提供
        map.put("shandle", 0); //server是否对返回的数据进行处理 默认1：采用gzip压缩；0：无处理；
        str=new Gson().toJson(map);
        return str;
    }




    /******************---------------------------------------------------------------------------------------*****************/

    /** 格式化请求内容
     * @param param
     * @return String 文本
     */
    private static String registerContentOld(BaseActivity activity, Object param) {
        String str = "";
        String infoData = new Gson().toJson(param);
        Map<String,Object> dataMap=new HashMap<>();
        String header = Header.getHeader(activity);
        Map<String,Object> headerMap = Header.getHeaderMap(activity);
        dataMap.put("phead", headerMap);
        dataMap.put("userInfo",param);
//        String data = new Gson().toJson(dataMap);

        //PRIVATE_KEY：-->yyyyyyyy
        String md5Encode = MD5.encodeStr(Api.PRIVATE_KEY +data + Api.PRIVATE_KEY);
        Map<String, Object> map = new HashMap<>();
        // 对提交的数据进行处理
        // 默认0：无处理；1：采用gzip压缩（请求上传的数据量比较大（大于10k）时使用）；
        // 2：BASE64加密（涉及到采集用户本地数据的时候使用）；
        map.put("handle", 0);
        map.put("data", dataMap);//提交的json格式请求数据
        map.put("pkey", Api.KEY);// 通信key APP端必须，由接口提供方提供 xxxxxxxx
        map.put("sign", md5Encode);//数据校验码 APP端必须，String sign = md5(私钥+data数据+私钥);私钥由接口提供方提供
        map.put("shandle", 0); //server是否对返回的数据进行处理 默认1：采用gzip压缩；0：无处理；
//        str = map.toString();
        str=new Gson().toJson(map);
        return str;
    }

    private static String formatContentBase64(Object param) {
        String str = null;
        try {

            String json = new Gson().toJson(param);
            byte[] bs = json.toString().getBytes("UTF-8");
            String encodeStr = Base64.encodeToString(bs, Base64.DEFAULT);
            //对加密结果bytes采用ISO-8859-1编码成字符串
            String data = new String(encodeStr.getBytes(), "ISO-8859-1");
//            byte[] bs = encodeStr.getBytes("iso8859-1");
            //PRIVATE_KEY：-->yyyyyyyy
            String md5Encode = MD5Utils.MD5Encode(Api.PRIVATE_KEY + data + Api.PRIVATE_KEY,"utf-8");
            Map<String, Object> map = new HashMap<>();
            // 对提交的数据进行处理
            // 默认0：无处理；1：采用gzip压缩（请求上传的数据量比较大（大于10k）时使用）；
            // 2：BASE64加密（涉及到采集用户本地数据的时候使用）；
            map.put("handle", 2);
            map.put("data", data);//提交的json格式请求数据
            map.put("pkey", Api.KEY);// 通信key APP端必须，由接口提供方提供 xxxxxxxx
            map.put("sign", md5Encode);//数据校验码 APP端必须，String sign = md5(私钥+data数据+私钥);私钥由接口提供方提供: yyyyyyyy
            map.put("shandle", 0); //server是否对返回的数据进行处理 默认1：采用gzip压缩；0：无处理；
            str = map.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return str;
    }


}
