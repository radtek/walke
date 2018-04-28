package com.game4u.arwinebottle.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.game4u.arwinebottle.R;
import com.game4u.arwinebottle.activity.user.LoginActivity;
import com.game4u.arwinebottle.config.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;
import walke.base.widget.TitleBar;

/**
 * Created by walke.Z on 2017/11/7.
 */

public class LaunchActivity extends TitleActivity {
    @BindView(R.id.launch_img)
    ImageView ivLaunch;
    private boolean isLogin = true;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_launch;
    }


    @Override
    public void initContentView(TitleBar titleBar) {
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        titleBar.setVisibility(View.GONE);
    }


    @Override
    protected void initData() {
        requestLogin();
        initPermission(Permission.PERMISSIONS, Permission.REQUEST_CODE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLogin) {
                    startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
                    overridePendingTransition(R.anim.keep,R.anim.keep);
                    finish();
                }else {
                    startActivity(new Intent(LaunchActivity.this, HomeActivity.class));
                    overridePendingTransition(R.anim.keep,R.anim.keep);
                    finish();
                }
            }
        }, 2000);

        ivLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin=false;//测试让其直接进入homeActivity
            }
        });

    }

    /**
     * 请求登录，即实现免登陆效果
     */
    private void requestLogin() {

    }

}
