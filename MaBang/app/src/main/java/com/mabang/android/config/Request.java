package com.mabang.android.config;

/**
 * Created by Xiongrui on 2016/12/14.
 */
public class Request {
    /**
     * 接口url地址 IP  //  局域网内的测试地址：http://192.168.1.223:47080/
     */
//    public final static String URL_DOMAIN = "192.168.1.94:8081";//志辉电脑通过热点
//    public final static String URL_DOMAIN = "192.168.2.105:8081";//志辉电脑通过家WiFi
    public final static String URL_DOMAIN = "119.23.230.234:80";//外网测试地址
    /**
     * http请求路径
     */
    public final static String URL_BASE = "http://" + URL_DOMAIN;

    /**
     * 接口请求地址_http
     */
    public final static String API_URL_USER = URL_BASE + "/api/member.php";

    /**
     * 接口请求地址_http
     */
    public final static String API_URL_WORKER = URL_BASE + "/api/worker.php";

    /**
     * 关于我们链接url地址
     */
    public final static String ABOUT_LC_URL = URL_BASE + "/mobile/about.html";
    //http://luckycoin.gdcaihui.com/mobile/about_yangjiang.html
    public final static String ABOUT_LC_URL_YANG_JIANG = URL_BASE + "/mobile/about_yangjiang.html";


}
