package com.game4u.arwinebottle.activity;

import android.graphics.Color;
import android.view.View;

import com.game4u.arwinebottle.R;
import com.game4u.arwinebottle.WineBottleApplication;
import com.game4u.arwinebottle.config.GsonAdapter;

import walke.base.activity.ActionBarActivity;
import walke.base.widget.TitleBar;

/**
 * Created by walke.Z on 2017/11/1.
 */

public abstract class TitleActivity extends ActionBarActivity implements GsonAdapter {

    public WineBottleApplication getWineBottleApp() {
        return (WineBottleApplication) getApplication();
    }

    @Override
    protected void initView(TitleBar titleBar) {
        titleBar.setThisBackgroundColor(Color.TRANSPARENT);
        titleBar.getIvLeft().setVisibility(View.VISIBLE);
        titleBar.getTvCenter().setTextColor(Color.WHITE);
        mRootView.setBackgroundResource(R.drawable.meizi2);
        initContentView(titleBar);
    }
    public abstract void initContentView(TitleBar titleBar);

    @Override
    public void leftClick() {
        super.leftClick();
    }
}
