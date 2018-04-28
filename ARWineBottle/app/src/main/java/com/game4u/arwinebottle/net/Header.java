package com.game4u.arwinebottle.net;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.WebView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import walke.base.BaseActivity;

import static android.content.ContentValues.TAG;
import static android.content.Context.TELEPHONY_SERVICE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_GPRS;
import static android.telephony.TelephonyManager.NETWORK_TYPE_UNKNOWN;

/**
 * Created by walke.Z on 2017/11/8.
 * 偶然发现：android.os.RecoverySystem.installPackage(Context c,File f);安装API
 */

public class Header {

    public static String getHeader(BaseActivity activity) {
        Map<String, Object> map = new HashMap<>();
        map.put("pversion", 1);                                 //协议版本号 必填，目前为1
        map.put("sid", "" + getAndroidId(activity));             //系统id APP端必填，安卓系统填androidId
        map.put("imei", "" + getIMEI(activity));                 //手机imei 选填
        map.put("uid", activity.getBaseApp().getUid());                                      //用户id 如果项目有特定的用户id则必填，没有则不填   默认 -- 1
        map.put("cid", 1);                                      //产品id 必填，找服务端分配：Android:1，IOS:2，H5:3
        map.put("cversion", 1);                                 //必填，由客户端分配
        map.put("cversionname", "" + getVersionName(activity));  //客户端软件版名称 必填，versionname值例如：2.3
        map.put("channel", 1);                                  //渠道号 APP必填
        map.put("imsi", "" + getIMSI(activity));                 //运营商编码 APP必填，例如：460030912121001
        map.put("sys", ""+ getSYS(activity));                    //系统版本 APP必填：2.3.6
        map.put("model", ""+getPhoneModel(activity));            //机型 APP必填
        map.put("requesttime", "" + System.currentTimeMillis());//请求时间戳 必填，客户端请求服务器的手机时间戳
        map.put("net", "" + getNetType(activity));               //网络类型 APP必填（模块会根据网络类型下发），unknown,wifi,gprs,3g,4g
//        map.put("coordinates", "");                           //经纬度信息 选填，经度#纬度
//        map.put("positions", "");                             //定位到的位置信息 选填，国家#省份#城市
//        map.put("pushcid", "");                               //个推中的cid 选填
        String phoneSystem = getPhoneSystem(activity);
//        String hederStr = map.toString();
        String hederStr = new Gson().toJson(map);

        Log.i(TAG, "getHeader: ");
        return hederStr;
    }

    public static Map<String,Object> getHeaderMap(BaseActivity activity) {
        Map<String, Object> map = new HashMap<>();
        map.put("pversion", 1);                                 //协议版本号 必填，目前为1
        map.put("sid", "" + getAndroidId(activity));             //系统id APP端必填，安卓系统填androidId
        map.put("imei", "" + getIMEI(activity));                 //手机imei 选填
        map.put("uid", 1);                                      //用户id 如果项目有特定的用户id则必填，没有则不填
        map.put("cid", 1);                                      //产品id 必填，找服务端分配：Android:1，IOS:2，H5:3
        map.put("cversion", 1);                                 //必填，由客户端分配
        map.put("cversionname", "" + getVersionName(activity));  //客户端软件版名称 必填，versionname值例如：2.3
        map.put("channel", 1);                                  //渠道号 APP必填
        map.put("imsi", "" + getIMSI(activity));                 //运营商编码 APP必填，例如：460030912121001
        map.put("sys", ""+ getSYS(activity));                    //系统版本 APP必填：2.3.6
        map.put("model", ""+getPhoneModel(activity));            //机型 APP必填
        map.put("requesttime", "" + System.currentTimeMillis());//请求时间戳 必填，客户端请求服务器的手机时间戳
        map.put("net", "" + getNetType(activity));               //网络类型 APP必填（模块会根据网络类型下发），unknown,wifi,gprs,3g,4g
//        map.put("coordinates", "");                           //经纬度信息 选填，经度#纬度
//        map.put("positions", "");                             //定位到的位置信息 选填，国家#省份#城市
//        map.put("pushcid", "");                               //个推中的cid 选填
        String phoneSystem = getPhoneSystem(activity);
//        String hederStr = map.toString();

        Log.i(TAG, "getHeader: ");
        return map;
    }


    /** for example,the IMEI for GSM and the MEID or ESN for CDMA phones.
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {//86185103412003
        String imei = ((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getDeviceId();
        return imei;
    }

    /**
     * 参考 http://www.360doc.com/content/11/0920/13/7322578_149730373.shtml
     *      http://www.cnblogs.com/zyw-205520/p/3829119.html
     *
     * @param context
     * @return
     */
    public static String getIMSI(Context context) {
        //
        String a = ((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getSimOperator();//getSimSerialNumber() 46007
        String b = ((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getSimSerialNumber();//getSimSerialNumber()//898600F1191507053266
        String c = ((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getSimOperatorName();//getSimSerialNumber()//CMCC
        String d = ((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getSimCountryIso();//getSimSerialNumber()//cn
        String imsi = ((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getSubscriberId();//460077663376405

        return imsi;

    }

    /** http://blog.csdn.net/boonya/article/details/39396331
     * 更具体地说，Settings.Secure.ANDROID_ID 是一串64位的编码（十六进制的字符串），
     * 是随机生成的设备的第一个引导，其记录着一个固定值，通过它可以知道设备的寿命（在设备恢复出厂设置后，该值可能会改变）。
     * ANDROID_ID也可视为作为唯一设备标识号的一个好选择。如要检索用于设备ID 的ANDROID_ID，请参阅下面的示例代码
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);//ANDROID_ID
        return androidId;
    }

    public static String getPhoneModel(Context context) {
         /*获取当前系统的android版本号*/
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;//
        String release = Build.VERSION.RELEASE;//获取版本号 6.0 华为5C
        String model = Build.MODEL;// 获取手机型号  NEM-AL10
        return model + "";
    }

    public static String getPhoneSystem(Context context) {
        String userAgentString = new WebView(context).getSettings().getUserAgentString();
        return userAgentString;
    }


    /**
    * 设备的软件版本号：
    * 例如：the IMEI/SV(software version) for GSM phones.
    * Return null if the software version is not available.
    */
    public static String getSYS(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);

        String deviceId = tm.getDeviceId();
        String release = Build.VERSION.RELEASE;//获取版本号 6.0 华为5C
        return release;

    }

    /**
     * @return 当前版本名
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String version = "";
        try {
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (Exception e) {
        }
        return version;
    }

    /**
     * 当前使用的网络类型：
     * 例如： NETWORK_TYPE_UNKNOWN  网络类型未知  0
       NETWORK_TYPE_GPRS     GPRS网络  1
       NETWORK_TYPE_EDGE     EDGE网络  2
       NETWORK_TYPE_UMTS     UMTS网络  3
       NETWORK_TYPE_HSDPA    HSDPA网络  8
       NETWORK_TYPE_HSUPA    HSUPA网络  9
       NETWORK_TYPE_HSPA     HSPA网络  10
       NETWORK_TYPE_CDMA     CDMA网络,IS95A 或 IS95B.  4
       NETWORK_TYPE_EVDO_0   EVDO网络, revision 0.  5
       NETWORK_TYPE_EVDO_A   EVDO网络, revision A.  6
       NETWORK_TYPE_1xRTT    1xRTT网络  7
     */
    public static String getNetworkType(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);

        String type = "unknown";
        int networkType = tm.getNetworkType();//int
        if (networkType == NETWORK_TYPE_GPRS) {
            type = "gprs";
        } else if (networkType == NETWORK_TYPE_UNKNOWN) {
            type = "unknown";
        } else {

        }
        return type;

    }

    /**当前使用的网络类型： http://blog.sina.com.cn/s/blog_4a0238270101iocw.html
     * @param context
     * @return
     */
    public static String getNetType(Context context) {
        String currentNetworkType = NetworkUtil.getCurrentNetworkType(context);
        return currentNetworkType;
       /* String type="unknown";
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info!=null){
            if ( info.getType() ==  ConnectivityManager.TYPE_WIFI){
                type="wifi";
            }else if (info.getType() ==  ConnectivityManager.TYPE_MOBILE){
                int subtype = info.getSubtype();
                String subtypeName = info.getSubtypeName();
                if (subtype==NETWORK_TYPE_CDMA||subtype==NETWORK_TYPE_HSDPA){

                }

            }
        }else {
            return "unknown";
        }
        return type;*/

    }



}
