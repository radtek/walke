package com.aipiao.bkpkold.acitivty.cp;

import android.content.Intent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.base.BaseActivity;
import com.aipiao.bkpkold.views.TopView;

/**
 * Created by caihui on 2018/3/21.
 */

public class CpWebActivity extends BaseActivity {
    private BridgeWebView cpwebview;
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private TopView cpTopView;
    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent!=null){
            String url = (String) intent.getExtras().get("url");
        }

        initWebView();

        cpwebview.setWebViewClient(new WebViewClient() {
            @Override
            // 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                showLoading();
                if (url.contains("https://m.ydniu.com")) {
                    return true;
                }
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                cpwebview.loadUrl("file:///android_asset/error_404.html");
                super.onReceivedError(view, request, error);
            }
        });

    }



    private void initWebView() {

        cpwebview.getSettings().setJavaScriptEnabled(true);
        cpwebview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        cpwebview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        // 开启 DOM storage API 功能
        cpwebview.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        cpwebview.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        //      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        //设置数据库缓存路径
        cpwebview.getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        cpwebview.getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        cpwebview.getSettings().setAppCacheEnabled(true);
    }

    @Override
    protected void initView() {
        cpwebview = (BridgeWebView) findViewById(R.id.cpwebview);
        cpTopView = (TopView) findViewById(R.id.cpTopView);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_cp_webview;
    }
}
