package com.aipiao.bkpkold.acitivty.fb;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.base.BaseActivity;

/**
 * Created by caihui on 2018/3/21.
 */

public class FootBallActivity extends BaseActivity {

    private BridgeWebView trendwebview;
    private Button returnButton;
    private static final String APP_CACAHE_DIRNAME = "/webcache";

    @Override
    protected void initData() {
        showLoading();
    }

    private void initWebView() {

        trendwebview.getSettings().setJavaScriptEnabled(true);
        trendwebview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        trendwebview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        // 开启 DOM storage API 功能
        trendwebview.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        trendwebview.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        //      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        //设置数据库缓存路径
        trendwebview.getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        trendwebview.getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        trendwebview.getSettings().setAppCacheEnabled(true);
    }

    @Override
    protected void initView() {
        trendwebview = (BridgeWebView) findViewById(R.id.trendwebview);
        returnButton = (Button) findViewById(R.id.returnButton);
        trendwebview.loadUrl("http://live.m.500.com/home/zq/jczq/cur?render=local");
        trendwebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    hideLoading();
                }
            }

        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trendwebview.canGoBack()) {
                    trendwebview.goBack();
                } else {
                    finish();
                }
            }
        });
        initWebView();
        trendwebview.setWebViewClient(new WebViewClient() {
            @Override
            // 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                showLoading();
                if (url.contains("http://m.500.com/")) {
                    return true;
                }
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                trendwebview.loadUrl("file:///android_asset/error_404.html");
                super.onReceivedError(view, request, error);
            }
        });

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_fb_webview;
    }
}
