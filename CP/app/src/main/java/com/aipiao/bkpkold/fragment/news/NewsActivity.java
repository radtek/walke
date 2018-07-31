package com.aipiao.bkpkold.fragment.news;

import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.base.BaseActivity;
import com.aipiao.bkpkold.bean.wubai.AnalysisAd;
import com.aipiao.bkpkold.views.TopView;

/**
 * Created by chennaikang on 2018/3/25.
 */

public class NewsActivity extends BaseActivity {
    private TopView webTopView;
    private PullToRefreshWebView mPullToRefreshWebView;
    private WebView webview;
    private Button returnButton;




    private AnalysisAd analysisAd;


    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent!=null){
            analysisAd= (AnalysisAd) intent.getExtras().get("new");
            if (analysisAd!=null){
              showLoading();
                webTopView.getTopTitleTextView().setTextSize(15);
                webTopView.getTopTitleTextView().setText(analysisAd.getNtitle()+"");
                webview.loadUrl("http://5.9188.com"+analysisAd.getArcurl());
            }

        }
        webview.setWebChromeClient(new WebChromeClient() {//监听网页加载
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成

                    hideLoading();
                }
                super.onProgressChanged(view, newProgress);
            }
        });


        webTopView.getLeftImageView().setVisibility(View.GONE);
    }

    @Override
    protected void initView() {
        returnButton= (Button) findViewById(R.id.returnButton);
        webTopView = (TopView) findViewById(R.id.webTopView);
         webview = (WebView) findViewById(R.id.webview);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_news_detail_webview;
    }
}
