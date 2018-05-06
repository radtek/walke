package com.aipiao.bkpk;


import android.content.Intent;
import android.os.Handler;

import com.aipiao.bkpk.base.BaseActivity;


/**
 * @author View
 * @date 2018/2/22 14:02
 */
public class WelcomeActivity extends BaseActivity {


    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(WelcomeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },100);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mwelcome;
    }
}
