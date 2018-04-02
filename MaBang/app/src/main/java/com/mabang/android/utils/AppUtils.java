package com.mabang.android.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.mabang.android.R;
import com.mabang.android.activity.base.AppActivity;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import walke.base.tool.FileUtil;
import walke.base.tool.WindowUtil;

import static walke.base.tool.FileUtil.app_file_path;

/**
 * Created by walke on 2018/1/31.
 * email:1032458982@qq.com
 */

public class AppUtils {

    /**
     * 注意 stutasBar 的上一层控件要求是 LinearLayout。否则会报错
     *
     * @param context
     * @param stutasBar
     */
    public static void adaptiveNdk_StatusBar(Context context, View stutasBar) {
        int statusBarHeight = WindowUtil.getStatusBarHeight(context);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) stutasBar.getLayoutParams();
        params.height = statusBarHeight;
        stutasBar.setLayoutParams(params);
        stutasBar.setBackgroundResource(R.drawable.bg_stutas_bar);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            //LogUtil.i("11111","SDK_INT = "+Build.VERSION.SDK_INT);
            stutasBar.setVisibility(View.GONE);
        } else {
            //LogUtil.i("1111122222","SDK_INT = "+Build.VERSION.SDK_INT);
            stutasBar.setVisibility(View.VISIBLE);
        }
    }
    public static void adaptiveNdk_StatusBar(AppActivity appActivity, int resId) {
        View stutasBar = appActivity.findViewById(R.id.details_statusBar);
        int statusBarHeight = WindowUtil.getStatusBarHeight(appActivity);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) stutasBar.getLayoutParams();
        params.height = statusBarHeight;
        stutasBar.setLayoutParams(params);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            //LogUtil.i("11111","SDK_INT = "+Build.VERSION.SDK_INT);
            stutasBar.setVisibility(View.GONE);
        } else {
            //LogUtil.i("1111122222","SDK_INT = "+Build.VERSION.SDK_INT);
            stutasBar.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 创建App目录根目录
     */
    public static void makeAppRootDir() {
        // 判断存储卡是否可用，创建文件夹
        if (FileUtil.isSDcardAvailable()) {
            File file = new File(app_file_path);
            if (!file.exists()){
                file.mkdir();
            }
        }
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


    /*
    cmd获取SHA1的方法 【C:\Users\Administrator>】 keytool -list -v -keystore D:\ASproject\projectJks\mabang.jks
    然后输入jks的密码[控制台可能会不显示你有没有输入，只管输入正确密码 然后回车即可]
           MD5: DF:6B:B9:C9:AE:CA:FF:58:15:95:15:7B:B6:07:8E:B9
           SHA1: 0A:B1:9A:C3:30:35:26:F4:C3:C2:3A:28:30:76:C7:5B:F1:6D:A8:D1
           SHA256: B3:DE:8F:C2:B0:B5:2F:F6:98:A9:20:49:57:BA:7D:4D:15:07:81:2A:09:9D:3B:7A:6B:F4:37:68:F0:67:F6:09
           签名算法名称: SHA256withRSA
           版本: 3
    */

    //这个是获取SHA1的方法一：
    // 77:55:31:06:A6:71:C3:63:05:3D:DB:B1:41:B9:FC:BD:C3:97:8C:FE    没使用签名文件jks
    // 0A:B1:9A:C3:30:35:26:F4:C3:C2:3A:28:30:76:C7:5B:F1:6D:A8:D1    在app.gradle引用了jks
    //百度地图key ： ba0N1XM0RSeosXBwtXwMv0ilVW3TkaD0
    public static String getCertificateSHA1Fingerprint(Context context) {
        //获取包管理器
        PackageManager pm = context.getPackageManager();
        //获取当前要获取SHA1值的包名，也可以用其他的包名，但需要注意，
        //在用其他包名的前提是，此方法传递的参数Context应该是对应包的上下文。
        String packageName = context.getPackageName();
        //返回包括在包中的签名信息
        int flags = PackageManager.GET_SIGNATURES;
        PackageInfo packageInfo = null;
        try {
            //获得包的所有内容信息类
            packageInfo = pm.getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //签名信息
        Signature[] signatures = packageInfo.signatures;
        byte[] cert = signatures[0].toByteArray();
        //将签名转换为字节数组流
        InputStream input = new ByteArrayInputStream(cert);
        //证书工厂类，这个类实现了出厂合格证算法的功能
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        //X509证书，X.509是一种非常通用的证书格式
        X509Certificate c = null;
        try {
            c = (X509Certificate) cf.generateCertificate(input);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        String hexString = null;
        try {
            //加密算法的类，这里的参数可以使MD4,MD5等加密算法
            MessageDigest md = MessageDigest.getInstance("SHA1");
            //获得公钥
            byte[] publicKey = md.digest(c.getEncoded());
            //字节到十六进制的格式转换
            hexString = byte2HexFormatted(publicKey);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return hexString;
    }

    //这里是将获取到得编码进行16进制转换
    private static String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);
        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1)
                h = "0" + h;
            if (l > 2)
                h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (arr.length - 1))
                str.append(':');
        }
        return str.toString();
    }

    public static String textReplace(String old, String tag) {
        if (TextUtils.isEmpty(old)||"null".equals(old)||"NULL".equals(old)||"Null".equals(old))
            return tag;
        else
            return old;
    }
}
