package com.game4u.arwinebottle;

import com.game4u.arwinebottle.bean.UserInfo;
import com.game4u.arwinebottle.config.Contants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import walke.base.tool.SharepreUtil;
import walke.base.BaseApp;

/**
 * Created by hui on 2017/10/28.
 * okHttp参考：https://github.com/hongyangAndroid/okhttputils
 */

public class WineBottleApplication extends BaseApp {

   private UserInfo mUserInfo;

   public UserInfo getUserInfo() {
      if (this.mUserInfo == null) {
         mUserInfo = new UserInfo();
         mUserInfo.setId(SharepreUtil.getString(this, Contants.USER_INFO_ID));

      }
      return mUserInfo;
   }

   public void setUserInfo(UserInfo userInfo) {
      mUserInfo = userInfo;
      if (userInfo == null) {
         SharepreUtil.putString(this, Contants.USER_INFO_ID, null);

      } else {
         SharepreUtil.putString(this, Contants.USER_INFO_ID, userInfo.getId());
      }
   }



   @Override
   public void onCreate() {
      super.onCreate();
      //初始化全局变量

      super.onCreate();

      // PersistentCookieStore //持久化cookie
      // SerializableHttpCookie //持久化cookie
      // MemoryCookieStore //cookie信息存在内存中
      CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));//持久化cookie
      /*OkHttpClient okHttpClient = new OkHttpClient.Builder()
              .cookieJar(cookieJar)
              //其他配置
              .build();*/
      //对于Https 设置可访问所有的https网站
      HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
      OkHttpClient okHttpClient = new OkHttpClient.Builder()
              .cookieJar(cookieJar)//对于Cookie(包含Session)
              .addInterceptor(new LoggerInterceptor("TAG"))//log日志配置
              .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
              .connectTimeout(15000L, TimeUnit.MILLISECONDS)//15s超时
              .readTimeout(15000L, TimeUnit.MILLISECONDS)
              //其他配置
              .build();

      OkHttpUtils.initClient(okHttpClient);


   }
}
