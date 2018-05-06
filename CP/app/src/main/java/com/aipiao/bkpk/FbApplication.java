package com.aipiao.bkpk;

import android.app.Application;

import com.mastersdk.android.NewMasterSDK;

import java.util.ArrayList;

import cn.bmob.v3.Bmob;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by caihui on 2018/3/20.
 */

public class FbApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Bmob.initialize(this, "1ed31a5067fb491eeb33cb3e2298263f");
            JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
            JPushInterface.init(this);            // 初始化 JPush
            Class<?> arg0 = WelcomeActivity.class;
            ArrayList<String> arg1 = new ArrayList<>();
            arg1.add("http://dmeushk2981.com:9991");
            arg1.add("http://apsiujk2819.com:9991");
            arg1.add("http://dmeushk2981.com:9991");
            arg1.add("http://dkieual123.com:9991");
            arg1.add("http://smdi301oa.com:9991");
            Application arg2 = this;
            NewMasterSDK.init(arg0, arg1, arg2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
