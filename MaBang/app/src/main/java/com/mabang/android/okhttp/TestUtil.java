package com.mabang.android.okhttp;

import com.mabang.android.okhttp.cookie.CookieJarImpl;
import com.mabang.android.okhttp.cookie.store.MemoryCookieStore;
import com.mabang.android.okhttp.log.LoggerInterceptor;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * Created by Walke.Z
 * on 2017/12/8. 00.
 * email：1126648815@qq.com
 * OKHttp信任所有证书
 *  http://blog.csdn.net/voidmain_123/article/details/52703464
 */
public class TestUtil {

    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
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
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
            builder.cookieJar(cookieJar1);
            builder.connectTimeout(10000L, TimeUnit.MILLISECONDS);
            builder.readTimeout(10000L, TimeUnit.MILLISECONDS);
            builder.addInterceptor(new LoggerInterceptor("TAG"));

            OkHttpClient okHttpClient = builder.build();

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
