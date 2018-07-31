package com.aipiao;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.acitivty.user.LoginActivity;
import com.aipiao.bkpkold.base.BaseActivity;
import com.aipiao.bkpkold.bean.Root;
import com.aipiao.bkpkold.bean.bmob.User;
import com.aipiao.bkpkold.fragment.FindFragment;
import com.aipiao.bkpkold.fragment.MeFragment;
import com.aipiao.bkpkold.fragment.OpenFragment;
import com.aipiao.bkpkold.fragment.newshome.MyNewHomeFragment;
import com.aipiao.bkpkold.utils.SharepreUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
 import com.aipiao.bkpkold.views.ListViewForScrollView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by caihui on 2018/4/10.
 */

public class NewHomeActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout bottomLinearLayout;
    private RelativeLayout rlUnLogin;
    private CircleImageView imageViewTouXiang;
    private TextView tvRegistLogin;
    private ImageView ivQiandao;
    private LinearLayout indexLinearLayout;
    private LinearLayout openLinearLayout;
    private LinearLayout findLinearLayout;
    private LinearLayout meLinearLayout;
    private ImageView menuImageView;
    private FrameLayout content;
    private Root appPush;

    private String objectId;

    private TextView moonTextView;
    private TextView currentTextView;

    private ListViewForScrollView newListView;

    private SlidingMenu menu;

    @Override
    protected void onResume() {
        super.onResume();
        String userString = SharepreUtil.getString(NewHomeActivity.this, "user");
        if (!TextUtils.isEmpty(userString)) {
            User user = gson.fromJson(userString, User.class);
            if (!TextUtils.isEmpty(user.getNickname())) {
                tvRegistLogin.setText("昵称 : " + user.getNickname() + "");
            }
            if (!TextUtils.isEmpty(user.getMoon())) {
                moonTextView.setText("心情 : " + user.getMoon() + "");
            }
        }
    }

    @Override
    protected void initData() {


        // configure the SlidingMenu
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        View menuView = View.inflate(NewHomeActivity.this, R.layout.menu_left_drawer, null);
        initmenuView(menuView);
        menu.setMenu(menuView);

        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content, MyNewHomeFragment.newInstance()).commit();
    }

    private void initmenuView(View menuView) {
        newListView= (ListViewForScrollView) menuView.findViewById(R.id.newListView);
        currentTextView = (TextView) menuView.findViewById(R.id.currentTextView);
        moonTextView = (TextView) menuView.findViewById(R.id.moonTextView);
        rlUnLogin = (RelativeLayout) menuView.findViewById(R.id.rl_un_login);
        imageViewTouXiang = (CircleImageView) menuView.findViewById(R.id.imageView_tou_xiang);
        tvRegistLogin = (TextView) menuView.findViewById(R.id.tv_regist_login);
        ivQiandao = (ImageView) menuView.findViewById(R.id.iv_qiandao);
        indexLinearLayout = (LinearLayout) menuView.findViewById(R.id.indexLinearLayout);
        openLinearLayout = (LinearLayout) menuView.findViewById(R.id.openLinearLayout);
        findLinearLayout = (LinearLayout) menuView.findViewById(R.id.findLinearLayout);
        meLinearLayout = (LinearLayout) menuView.findViewById(R.id.meLinearLayout);
        indexLinearLayout.setOnClickListener(this);
        openLinearLayout.setOnClickListener(this);
        findLinearLayout.setOnClickListener(this);
        meLinearLayout.setOnClickListener(this);
        tvRegistLogin.setOnClickListener(this);

        currentTextView.setText("当前版本号:" + getLocalVersion(NewHomeActivity.this)+".0.0");
    }

    public int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }


    @Override
    protected void initView() {
        menuImageView = (ImageView) findViewById(R.id.menuImageView);
        content = (FrameLayout) findViewById(R.id.content);
        bottomLinearLayout = (LinearLayout) findViewById(R.id.bottomLinearLayout);

        menuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle();
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_newhome;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.indexLinearLayout:
                fm.beginTransaction().replace(R.id.content, MyNewHomeFragment.newInstance()).commit();
                menu.toggle();
                break;
            case R.id.openLinearLayout:
                fm.beginTransaction().replace(R.id.content, OpenFragment.newInstance()).commit();
                menu.toggle();
                break;
            case R.id.findLinearLayout:
                fm.beginTransaction().replace(R.id.content, FindFragment.newInstance()).commit();
                menu.toggle();
                break;
            case R.id.meLinearLayout:
                fm.beginTransaction().replace(R.id.content, MeFragment.newInstance()).commit();
                menu.toggle();
                break;
            case R.id.tv_regist_login:
              String  objectId = SharepreUtil.getString(NewHomeActivity.this, "objectId");
                if (TextUtils.isEmpty(objectId)) {
                    startActivity(new Intent(NewHomeActivity.this, LoginActivity.class));
                    toast("请先注册或登陆");return;
                }else {
                    startActivity(new Intent(NewHomeActivity.this, LoginActivity.class));
                }
                break;
        }
    }
}
