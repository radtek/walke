package com.aipiao.bkpkold.acitivty.active;

import android.content.Intent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.base.BaseActivity;
import com.aipiao.bkpkold.bean.bmob.News;
import com.aipiao.bkpkold.views.TopView;

/**
 * Created by caihui on 2018/3/21.
 */

public class BigGrandDetailActivity extends BaseActivity {
    private TopView bigDetailTopView;
    private BridgeWebView webview;
    private News news;
    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            news = (News) intent.getExtras().get("news");
            bigDetailTopView.getTopTitleTextView().setText(news.getQihao() + "");
            webview.loadUrl(news.getUrl()+"");

        }
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                webview.loadUrl("file:///android_asset/error_404.html");
                super.onReceivedError(view, request, error);
            }
        });
        bigDetailTopView.getLeftImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        bigDetailTopView = (TopView) findViewById(R.id.bigDetailTopView);
        webview = (BridgeWebView) findViewById(R.id.webview);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_big_grand_detail;
    }
}
