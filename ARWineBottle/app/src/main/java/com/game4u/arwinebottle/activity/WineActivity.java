package com.game4u.arwinebottle.activity;

import android.content.Intent;
import android.view.View;

import com.game4u.arwinebottle.R;
import com.game4u.arwinebottle.ar.UnityPlayerNativeActivity;

import walke.base.widget.TitleBar;

/**
 * Created by walke.Z on 2017/11/2.
 */

public class WineActivity extends TitleActivity {
    @Override
    protected int rootLayoutId() {
        return R.layout.activity_wine;
    }


    @Override
    public void initContentView(TitleBar titleBar) {
        mRootView.setBackgroundResource(R.color.bg_000000);
        titleBar.setTitleText("");//全家福
        titleBar.getIvLeft().setVisibility(View.VISIBLE);
        titleBar.getIvLeft().setImageResource(R.mipmap.icon_back_3x);
    }

    @Override
    protected void initData() {

    }

    public void clickHand(View v){
        startActivity(new Intent(this,WinePageActivity.class));
    }
    public void toScan(View v) {
        startActivity(new Intent(this, UnityPlayerNativeActivity.class));
    }
}
