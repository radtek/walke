package com.mabang.android.utils;

import android.location.Address;
import android.location.Geocoder;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.mabang.android.activity.base.AppActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by walke on 2018/3/10.
 * email:1032458982@qq.com
 */

public class BDMapUtils {

    public static String getCityName(AppActivity appActivity, LatLng latLng) {
        List<Address> addList = null;
        Geocoder ge = new Geocoder(appActivity);
        try {
            addList = ge.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (addList != null && addList.size() > 0) {
            for (int i = 0; i < addList.size(); i++) {
                Address ad = addList.get(i);
                String locality = ad.getLocality();
                Log.i("walke", "--------------------------------getCityName: latLongString = " + ad.getCountryName() + ";" + locality);
                String featureName = ad.getFeatureName();
                String subAdminArea = ad.getSubAdminArea();
                if (TextUtils.isEmpty(subAdminArea))
                    return locality;
                else
                    return subAdminArea;
            }
        }
        return null;

    }


    public static String getCityNameBD(AppActivity appActivity, LatLng latLng) {

        GeoCoder ge = GeoCoder.newInstance();

        ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption();
        ReverseGeoCodeOption location = reverseGeoCodeOption.location(latLng);
        ge.reverseGeoCode(location);


        GeoCodeOption geoCodeOption = new GeoCodeOption();
//        ge
        ge.geocode(geoCodeOption);


        return null;

    }


    private String baiduUrl = "http://api.map.baidu.com/geocoder?output=json&location=23.131427,113.379763&ak=ba0N1XM0RSeosXBwtXwMv0ilVW3TkaD0";


    public static void getCityName(final LatLng latLng, final BDGetCityListener bdGetCityListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuilder resultData = new StringBuilder();
                    //秘钥换成你的秘钥，申请地址在下边
//                    String url = "http://api.map.baidu.com/geocoder/v2/?ak=" + "ba0N1XM0RSeosXBwtXwMv0ilVW3TkaD0" + "&location=" + latLng.latitude + "," + latLng.longitude + "&output=json&pois=1";
                    String url = "http://api.map.baidu.com/geocoder?output=json" + "&location=" + latLng.latitude + "," + latLng.longitude + "&ak=" + "ba0N1XM0RSeosXBwtXwMv0ilVW3TkaD0";
                    URL myURL = null;
                    URLConnection httpsConn = null;
                    myURL = new URL(url);
                    InputStreamReader insr = null;
                    BufferedReader br = null;
                    httpsConn = (URLConnection) myURL.openConnection();// 不使用代理
                    if (httpsConn != null) {
                        insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
                        br = new BufferedReader(insr);
                        String data = null;
                        while ((data = br.readLine()) != null) {
                            resultData.append(data);
                        }
                    }
                    if (insr != null)
                        insr.close();
                    if (br != null)
                        br.close();
                    String s = resultData.toString();
                    JSONObject jo = new JSONObject(s);
                    JSONObject result = jo.getJSONObject("result");
                    JSONObject addressComponent = result.getJSONObject("addressComponent");
                    String city = addressComponent.getString("city");
                    if (bdGetCityListener != null)
                        bdGetCityListener.success(city);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (bdGetCityListener != null)
                        bdGetCityListener.fail(e);
                }
            }
        }).start();
    }


    public interface BDGetCityListener {
        void success(String cityName);

        void fail(Exception exc);
    }

//
//    public static void main(String[] args) throws IOException {
////        String o = BDMapUtils.getCityName("117.2317","39.5427");
//        BDMapUtils.getCityName(new LatLng(39.5427, 117.2317), new BDGetCityListener() {
//            @Override
//            public void success(String cityName) {
//                Log.i("walke", "success: city" + cityName);
//            }
//
//            @Override
//            public void fail(Exception exc) {
//                Log.i("walke", "fail: exc" + exc.getMessage());
//            }
//        });
//    }


}
