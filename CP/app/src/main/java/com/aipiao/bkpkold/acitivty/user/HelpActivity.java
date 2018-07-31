package com.aipiao.bkpkold.acitivty.user;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.base.BaseActivity;

/**
 * Created by caihui on 2018/3/26.
 */

public class HelpActivity extends BaseActivity {


    private View layoutTitleBar;
    private TextView buttonBack;
    private TextView title;
    private Button buttonRight;
    private BridgeWebView helpWebView;


    @Override
    protected void initData() {
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("帮助中心");
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, KefuViewlActivity.class));
            }
        });
        helpWebView.loadUrl("file:///android_asset/help/play.html");
    }

    @Override
    protected void initView() {
        layoutTitleBar = (View) findViewById(R.id.layout_title_bar);
        buttonBack = (TextView) findViewById(R.id.button_back);
        title = (TextView) findViewById(R.id.title);
        buttonRight = (Button) findViewById(R.id.button_right);
        helpWebView = (BridgeWebView) findViewById(R.id.helpWebView);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_help;
    }
}
