package com.grandsea.ticketvendingapplication;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.grandsea.ticketvendingapplication.constant.Constant;
import com.grandsea.ticketvendingapplication.constant.DefaultConfig;
import com.grandsea.ticketvendingapplication.model.bean.LocationInfo;
import com.grandsea.ticketvendingapplication.util.SharepreUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;


public class TicketVendingApplication extends Application
{
    public static TicketVendingApplication instance;

    public static TicketVendingApplication getInstance() {
        if (instance==null){
            instance=new TicketVendingApplication();
        }
        return instance;
    }
    private String CER_12306 = "-----BEGIN CERTIFICATE-----\n" +
            "MIICmjCCAgOgAwIBAgIIbyZr5/jKH6QwDQYJKoZIhvcNAQEFBQAwRzELMAkGA1UEBhMCQ04xKTAn\n" +
            "BgNVBAoTIFNpbm9yYWlsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MQ0wCwYDVQQDEwRTUkNBMB4X\n" +
            "DTA5MDUyNTA2NTYwMFoXDTI5MDUyMDA2NTYwMFowRzELMAkGA1UEBhMCQ04xKTAnBgNVBAoTIFNp\n" +
            "bm9yYWlsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MQ0wCwYDVQQDEwRTUkNBMIGfMA0GCSqGSIb3\n" +
            "DQEBAQUAA4GNADCBiQKBgQDMpbNeb34p0GvLkZ6t72/OOba4mX2K/eZRWFfnuk8e5jKDH+9BgCb2\n" +
            "9bSotqPqTbxXWPxIOz8EjyUO3bfR5pQ8ovNTOlks2rS5BdMhoi4sUjCKi5ELiqtyww/XgY5iFqv6\n" +
            "D4Pw9QvOUcdRVSbPWo1DwMmH75It6pk/rARIFHEjWwIDAQABo4GOMIGLMB8GA1UdIwQYMBaAFHle\n" +
            "tne34lKDQ+3HUYhMY4UsAENYMAwGA1UdEwQFMAMBAf8wLgYDVR0fBCcwJTAjoCGgH4YdaHR0cDov\n" +
            "LzE5Mi4xNjguOS4xNDkvY3JsMS5jcmwwCwYDVR0PBAQDAgH+MB0GA1UdDgQWBBR5XrZ3t+JSg0Pt\n" +
            "x1GITGOFLABDWDANBgkqhkiG9w0BAQUFAAOBgQDGrAm2U/of1LbOnG2bnnQtgcVaBXiVJF8LKPaV\n" +
            "23XQ96HU8xfgSZMJS6U00WHAI7zp0q208RSUft9wDq9ee///VOhzR6Tebg9QfyPSohkBrhXQenvQ\n" +
            "og555S+C3eJAAVeNCTeMS3N/M5hzBRJAoffn3qoYdAO1Q8bTguOi+2849A==\n" +
            "-----END CERTIFICATE-----";
    private static final int GET_SN_CODE=21;
    private String snCode;

    public String getSnCode() {
        return snCode;
    }

    private String defaultUid;

    public String getDefaultUid() {
        if (TextUtils.isEmpty(defaultUid)) {
            defaultUid = SharepreUtil.getString(this, Constant.DEFAULT_UID,DefaultConfig.DEFAULT_UID);
        }
        return defaultUid;
    }

    public void setDefaultUid(String defaultUid) {
        this.defaultUid = defaultUid;
        SharepreUtil.putString(this,Constant.DEFAULT_UID,defaultUid);
    }

    private LocationInfo locationInfo;
    public LocationInfo getLocationInfo() {
        if (locationInfo==null){
            int  depart_city_id= SharepreUtil.getInt(this,Constant.DEPART_CITY_ID, DefaultConfig.DEPART_CITY_ID);
            String  depart_city_name= SharepreUtil.getString(this,Constant.DEPART_CITY_NAME,DefaultConfig.DEPART_CITY_NAME);
            int  depart_station_id= SharepreUtil.getInt(this,Constant.DEPART_STATION_ID,DefaultConfig.GET_ON_ID);
            String  depart_station_name= SharepreUtil.getString(this,Constant.DEPART_STATION_NAME,DefaultConfig.GET_ON_STATION_NAME);
            locationInfo = new LocationInfo();
            locationInfo.setDepart_city_id(depart_city_id);
            locationInfo.setDepart_city_name(depart_city_name);
            locationInfo.setGet_on_id(depart_station_id);
            locationInfo.setDepart_station_name(depart_station_name);

        }
        return locationInfo;
    }
    public void setLocationInfo(LocationInfo locationInfo) {
        if (locationInfo==null) return;
        this.locationInfo = locationInfo;
        SharepreUtil.putInt(this,Constant.DEPART_CITY_ID,locationInfo.getDepart_city_id());
        SharepreUtil.putString(this,Constant.DEPART_CITY_NAME,locationInfo.getDepart_city_name());
        SharepreUtil.putInt(this,Constant.DEPART_STATION_ID,locationInfo.getGet_on_id());
        SharepreUtil.putString(this,Constant.DEPART_STATION_NAME,locationInfo.getDepart_station_name());
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case GET_SN_CODE:
                    Log.d("GetSnNumbers", "SN: " + msg.obj.toString());
                    snCode=msg.obj.toString().trim();
                    if (getSNCodeListener!=null){
                        getSNCodeListener.getSNCodeSuccess(snCode);
                    }
                    break;
                default:
                    break;

            }

        }
    };

    @Override
    public void onCreate()
    {
        super.onCreate();

//        ClearableCookieJar cookieJar1 = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

//        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60*1000L, TimeUnit.MILLISECONDS)
                .readTimeout(60*1000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .hostnameVerifier(new HostnameVerifier()
                {
                    @Override
                    public boolean verify(String hostname, SSLSession session)
                    {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);


        Thread snThread = new Thread(new getSerialnoNumbers(mHandler));
        snThread.start();

    }
    private static class getSerialnoNumbers implements Runnable {
        Handler mHandler;
        final String serialnoStr = "[ro.boot.serialno]";

        getSerialnoNumbers(Handler handler) {
            this.mHandler = handler;
        }

        public void run() {
            try {
                Process p = Runtime.getRuntime().exec("getprop");
                p.waitFor();
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(
                        p.getInputStream()));
                String temp = "";
                while ((temp = stdInput.readLine()) != null) {
                    if (temp.contains(serialnoStr)) {
                        temp.replaceAll(" ", "");
                        int index = temp.indexOf(serialnoStr);
                        temp = temp.substring(index + 20);
                        temp = temp.substring(1, temp.length() - 1);
                        Log.d("getSerialnoNumbers", temp);
                        Message msg = new Message();
                        msg.what=GET_SN_CODE;
                        msg.obj = temp;
                        mHandler.sendMessage(msg);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface GetSNCodeListener{
        void getSNCodeSuccess(String snCode);
    }
    private GetSNCodeListener getSNCodeListener;

    public GetSNCodeListener getGetSNCodeListener() {
        return getSNCodeListener;
    }

    public void setGetSNCodeListener(GetSNCodeListener getSNCodeListener) {
        this.getSNCodeListener = getSNCodeListener;
    }
}
