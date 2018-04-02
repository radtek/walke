package com.mabang.android;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.mabang.android.config.Constants;
import com.mabang.android.entity.vo.AliyunInfo;
import com.mabang.android.entity.vo.MemberInfo;
import com.mabang.android.okhttp.OkHttpUtils;
import com.mabang.android.okhttp.cookie.CookieJarImpl;
import com.mabang.android.okhttp.cookie.store.MemoryCookieStore;
import com.mabang.android.okhttp.log.LoggerInterceptor;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import walke.base.BaseApp;
import walke.base.tool.SharepreUtil;

/**
 * Created by walke on 2018/1/31.
 * email:1032458982@qq.com
 */

public class MaBangApplication extends BaseApp {


    private MemberInfo memberInfo;

    public MemberInfo getMemberInfo() {
        if (this.memberInfo == null) {
            memberInfo = new MemberInfo();
            memberInfo.setAccount(SharepreUtil.getString(this, Constants.MEMBER_ACCOUNT));
            memberInfo.setToken(SharepreUtil.getString(this, Constants.MEMBER_TOKEN));
//            String apiType = SharepreUtil.getString(this, Constants.MEMBER_API_TYPE);
//
//            if (ApiType.MEMBER.getSessionKey().equals(apiType)){
//                memberInfo.setType(ApiType.MEMBER);
//            }else if (ApiType.WORKER.getSessionKey().equals(apiType)){
//                memberInfo.setType(ApiType.WORKER);
//            }
        }
        return memberInfo;
    }

    public void setMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
        if (memberInfo == null) {
            SharepreUtil.putString(this, Constants.MEMBER_ACCOUNT, null);
            SharepreUtil.putString(this, Constants.MEMBER_TOKEN, null);
//            SharepreUtil.putString(this, Constants.MEMBER_API_TYPE, null);
        } else {
            SharepreUtil.putString(this, Constants.MEMBER_ACCOUNT, memberInfo.getAccount());
            SharepreUtil.putString(this, Constants.MEMBER_TOKEN, memberInfo.getToken());
//            if (memberInfo.getType()!=null) {
//                SharepreUtil.putString(this, Constants.MEMBER_API_TYPE, memberInfo.getType().getSessionKey());
//            }else {
//                SharepreUtil.putString(this, Constants.MEMBER_API_TYPE, null);
//            }
        }
    }

//    public MemberInfo getWorkerMemberInfo() {
//        if (this.memberInfo == null) {
//            memberInfo = new MemberInfo();
//            memberInfo.setAccount(SharepreUtil.getString(this, Constants.WORKER_ACCOUNT));
//            memberInfo.setToken(SharepreUtil.getString(this, Constants.WORKER_TOKEN));
//        }
//        return memberInfo;
//    }
//
//    public void setWorkerMemberInfo(MemberInfo memberInfo) {
//        this.memberInfo = memberInfo;
//        if (memberInfo == null) {
//            SharepreUtil.putString(this, Constants.WORKER_ACCOUNT, null);
//            SharepreUtil.putString(this, Constants.WORKER_TOKEN, null);
//        } else {
//            SharepreUtil.putString(this, Constants.WORKER_ACCOUNT, memberInfo.getAccount());
//            SharepreUtil.putString(this, Constants.WORKER_TOKEN, memberInfo.getToken());
//        }
//    }

    private AliyunInfo mAliyunInfo;

    public AliyunInfo getAliyunInfo() {
        return mAliyunInfo;
    }

    public void setAliyunInfo(AliyunInfo aliyunInfo) {
        mAliyunInfo = aliyunInfo;
    }

    private MemoryCookieStore mMemoryCookieStore;

    public MemoryCookieStore getMemoryCookieStore() {
        return mMemoryCookieStore;
    }

    public void setMemoryCookieStore(MemoryCookieStore memoryCookieStore) {
        this.mMemoryCookieStore = memoryCookieStore;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        initHttpRequest2();
        initCrashSetting();

    }

    private void initCrashSetting() {// 初始化Bugly
        Context context = getApplicationContext();
        String packageName = context.getPackageName();
        String processName = getProcessName(android.os.Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(context, "bf15cec024", true, strategy);
    }

    /**
     * 获取进程号对应的进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * OKHttp信任所有证书
     */
    private void initHttpRequest2() {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }
                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
        // Install the all-trusting trust manager
        SSLContext sslContext=null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        // Create an ssl socket factory with our all-trusting manager
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        mMemoryCookieStore = new MemoryCookieStore();
        CookieJarImpl cookieJar1 = new CookieJarImpl(mMemoryCookieStore);
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30000L, TimeUnit.MILLISECONDS)
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .cookieJar(cookieJar1)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslSocketFactory)
                .build();
        OkHttpUtils.initClient(mOkHttpClient);
    }

    /**
     * 清除应用的task栈，如果程序正常运行这会导致应用退回到桌面
     */
    public final void exit() {


        /** 进程会被kill掉 http://blog.csdn.net/chonbj/article/details/10182369 */
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            System.exit(0);
        } else {// android2.1 
            ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            am.restartPackage(getPackageName());
        }
        //进程没被kill掉 使用finlish activity不一定会被gc回收掉
        /*for (Iterator<Map.Entry<String, SoftReference<Activity>>> iterator = this.taskMap.entrySet().iterator(); iterator.hasNext(); ) {
            SoftReference<Activity> activityReference = iterator.next().getValue();
            Activity activity = activityReference.get();
            if (activity != null) {
                activity.finish();
            }
        }
        this.taskMap.clear();*/
        return;
    }

}
