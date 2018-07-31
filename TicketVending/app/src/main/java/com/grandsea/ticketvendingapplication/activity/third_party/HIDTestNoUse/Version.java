//package com.grandsea.ticketvendingapplication.activity.third_party.HIDTestNoUse;
//
//import android.content.Context;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager.NameNotFoundException;
//
//import com.Routon.iDRHIDLib.iDRHIDDev;
//
//public class Version {
//
//    public static String getDEMOVersion(Context context){
//        String ver = "";
//        try {
//            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
//            ver = info.versionName;
//        } catch (NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return ver;
//    }
//
//    public static String getSDKVersion(){
//        return iDRHIDDev.version;
//    }
//}
