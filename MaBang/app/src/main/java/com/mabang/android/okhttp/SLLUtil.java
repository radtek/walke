package com.mabang.android.okhttp;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by Walke.Z
 * on 2017/12/8. 26.
 * email：1126648815@qq.com
 */
public class SLLUtil {

    public static SSLSocketFactory getSSLSocketFactory(Context context) {
        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(getHttpsKeyStore(context));
            sslContext.init(null, trustManagerFactory.getTrustManagers(),
                    new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (KeyStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }



    public static KeyStore getHttpsKeyStore(Context context) {
        InputStream ins = null;
        InputStream ins02 = null;
        try {
            //KuaiYing/assets/_.51kuaiying.com.cer
//            ins =  BaseApplacation.getInstance().getAssets().open("_.51kuaiying.com.cer");// 下载的证书放到项目中的assets目录中;
            ins = context.getAssets().open("_.51kuaiying.com.cer");// 下载的证书放到项目中的assets目录中;
//            ins02 = BaseApplacation.getInstance().getAssets().open("51kuaiyingpc.cer");// 下载的证书放到项目中的assets目录中;
            ins02 = context.getAssets().open("51kuaiyingpc.cer");// 下载的证书放到项目中的assets目录中;

            // 读取证书
            CertificateFactory cerFactory = CertificateFactory
                    .getInstance("X.509"); // 问1
            Certificate cer = cerFactory.generateCertificate(ins);
            Certificate cer02 = cerFactory.generateCertificate(ins02);
            // 创建一个证书库，并将证书导入证书库
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType()); // 问2
            keyStore.load(null, null);
            keyStore.setCertificateEntry("trust[0]", cer);
            keyStore.setCertificateEntry("trust[1]", cer02);
            return keyStore;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ins != null&&ins02 != null) {
                try {
                    ins.close();
                    ins02.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }




}
