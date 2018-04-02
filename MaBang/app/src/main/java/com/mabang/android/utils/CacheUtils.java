package com.mabang.android.utils;

import android.content.Context;

import com.mabang.android.config.Constants;
import com.mabang.android.entity.vo.AddressInfo;

import walke.base.tool.SharepreUtil;

/**
 * Created by walke on 2018/1/31.
 * email:1032458982@qq.com
 */

public class CacheUtils {

    /**
     * @param context
     * @param addressStr
     */
    public static void saveAddressInfoStr(Context context, String addressStr) {
        SharepreUtil.putString(context, Constants.ADDRESS_INFO,addressStr);
    }
    /**
     * @param context
     */
    public static String getAddressInfoStr(Context context) {
        return SharepreUtil.getString(context, Constants.ADDRESS_INFO);
    }
    /**
     * @param context
     * @param addressInfo
     */
    public static void setCurrentAddress(Context context, AddressInfo addressInfo) {
        SharepreUtil.putInt(context,"ProvinceId",addressInfo.getProvinceId());
        SharepreUtil.putInt(context,"CityId",addressInfo.getCityId());
        SharepreUtil.putInt(context,"ZoneId",addressInfo.getZoneId());
        SharepreUtil.putInt(context,"StreetId",addressInfo.getStreetId());
    }
    /**
     * @param context
     */
    public static AddressInfo getCurrentAddress(Context context) {
        AddressInfo addressInfo=new AddressInfo();
        addressInfo.setProvinceId(SharepreUtil.getInt(context,"ProvinceId",440000));
        addressInfo.setCityId(SharepreUtil.getInt(context,"CityId",440100));
        addressInfo.setZoneId(SharepreUtil.getInt(context,"ZoneId",440103));
        addressInfo.setStreetId(SharepreUtil.getInt(context,"StreetId",440103011));
        return addressInfo;
    }


}
