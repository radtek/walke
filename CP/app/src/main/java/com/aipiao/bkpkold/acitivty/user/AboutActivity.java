package com.aipiao.bkpkold.acitivty.user;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.base.BaseActivity;

/**
 * Created by caihui on 2018/3/26.
 */

public class AboutActivity extends BaseActivity {

    private View layoutTitleBar;
    private TextView buttonBack;
    private TextView title;
    private Button buttonRight;
    private ImageView imageViewIcon;
    private TextView textViewName;
    private TextView textViewVersion;
    private TextView textViewDesc;
    private ImageButton buttonTel;
    private TextView textViewInfo;




    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        layoutTitleBar = (View) findViewById(R.id.layout_title_bar);
        buttonBack = (TextView) findViewById(R.id.button_back);
        title = (TextView) findViewById(R.id.title);
        buttonRight = (Button) findViewById(R.id.button_right);
        imageViewIcon = (ImageView) findViewById(R.id.imageView_icon);
        textViewName = (TextView) findViewById(R.id.textView_name);
        textViewVersion = (TextView) findViewById(R.id.textView_version);
        textViewDesc = (TextView) findViewById(R.id.textView_desc);
        buttonTel = (ImageButton) findViewById(R.id.button_tel);
        textViewInfo = (TextView) findViewById(R.id.textView_info);
        title.setText("关于我们");
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(AboutActivity.this,KefuViewlActivity.class));
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.about_activity;
    }
}
