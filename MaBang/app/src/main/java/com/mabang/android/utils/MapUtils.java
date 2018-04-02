package com.mabang.android.utils;

import android.util.Log;

import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by walke on 2018/3/15.
 * email:1032458982@qq.com
 */

public class MapUtils {

    /**
     * 通过经纬度集合获取适当的地图缩放级别
     */
    public static int getMapLevel(List<LatLng> latLngList) {

        // 比较选出集合中最大经纬度
        List<Double> latitudeList = new ArrayList<Double>();
        List<Double> longitudeList = new ArrayList<Double>();
        for (int i = 0; i < latLngList.size(); i++) {
            double latitude = latLngList.get(i).latitude;
            double longitude = latLngList.get(i).longitude;
            latitudeList.add(latitude);
            longitudeList.add(longitude);
        }

        Double maxLatitude = Collections.max(latitudeList);
        Double maxLongitude = Collections.max(longitudeList);
        Double min_Latitude = Collections.min(latitudeList);
        Double min_Longitude = Collections.min(longitudeList);
        int mapLevel = calculateDistance(maxLatitude, maxLongitude, min_Latitude, min_Longitude);
        return mapLevel;
    }

    /**
     * 计算两个Marker之间的距离
     */
    public static int calculateDistance( Double maxLatitude,Double maxLongitude,Double minLatitude, Double minLongitude ) {
        double distance = GeoHasher.GetDistance(maxLatitude, maxLongitude, minLatitude, minLongitude);
        int mapLevel = getLevel(distance);
        Log.i("info", "maxLatitude==" + maxLatitude + ";minLatitude==" + minLatitude + ";maxLongitude==" + maxLongitude + ";minLongitude==" + minLongitude);
        return mapLevel;
    }

    /**
     * 计算两个Marker之间的距离
     */
    public static double getDistance(List<LatLng> latLngList) {

        // 比较选出集合中最大经纬度
        List<Double> latitudeList = new ArrayList<Double>();
        List<Double> longitudeList = new ArrayList<Double>();
        for (int i = 0; i < latLngList.size(); i++) {
            double latitude = latLngList.get(i).latitude;
            double longitude = latLngList.get(i).longitude;
            latitudeList.add(latitude);
            longitudeList.add(longitude);
        }

        Double maxLatitude = Collections.max(latitudeList);
        Double maxLongitude = Collections.max(longitudeList);
        Double min_Latitude = Collections.min(latitudeList);
        Double min_Longitude = Collections.min(longitudeList);

        double distance = GeoHasher.GetDistance(maxLatitude, maxLongitude, min_Latitude, min_Longitude)+7;//
        int mapLevel = getLevel(distance);//清远 16
        Log.i("info", "-----------------------------------maxLatitude==" + maxLatitude + ";minLatitude==" + min_Latitude + ";maxLongitude==" + maxLongitude + ";minLongitude==" + min_Longitude);
        return distance;
    }

    /**
     * 计算两个Marker之间的距离
     */
    public static LatLng getCenter(List<LatLng> latLngList) {

        // 比较选出集合中最大经纬度
        List<Double> latitudeList = new ArrayList<Double>();
        List<Double> longitudeList = new ArrayList<Double>();
        for (int i = 0; i < latLngList.size(); i++) {
            double latitude = latLngList.get(i).latitude;
            double longitude = latLngList.get(i).longitude;
            latitudeList.add(latitude);
            longitudeList.add(longitude);
        }

        Double maxLatitude = Collections.max(latitudeList);
        Double maxLongitude = Collections.max(longitudeList);
        Double min_Latitude = Collections.min(latitudeList);
        Double min_Longitude = Collections.min(longitudeList);

        LatLng latLng = new LatLng((maxLatitude+min_Latitude)/2,(maxLongitude+min_Longitude)/2);
        Log.i("walke", "getCenter: ---------------------latLng.lat = "+latLng.latitude+" ----latLng.lng = "+latLng.longitude);

        return latLng;
    }


    /**
     *根据距离判断地图级别
     */
    public static int getLevel(double distance) {
        int mapLevel=16;//200米
//        int zoom[] = {200, 500, 1000, 2000, 5000, 1000, 2000, 25000, 50000, 100000};
        int zoom[] = {10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 1000, 2000, 25000, 50000, 100000, 200000, 500000, 1000000, 2000000};
        Log.i("info", "---------------------------------------------- distance = " + distance);
        for (int i = 0; i < zoom.length; i++) {
            int zoomNow = zoom[i];
            if (zoomNow - distance * 1000 > 0) {
                mapLevel = 18 - i + 6;
                //设置地图显示级别为计算所得level
//                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(mapLevel).build()));
                break;
            }
        }
        return mapLevel;
    }

}
