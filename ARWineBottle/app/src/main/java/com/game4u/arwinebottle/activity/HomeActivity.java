package com.game4u.arwinebottle.activity;

import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.game4u.arwinebottle.R;
import com.game4u.arwinebottle.config.Permission;
import walke.base.widget.TextImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import walke.base.widget.TitleBar;

public class HomeActivity extends TitleActivity {

    @BindView(R.id.home_ARScan)
    TextImageView mARScan;
    @BindView(R.id.home_item2)
    TextImageView mItem2;
    @BindView(R.id.home_item3)
    TextImageView mItem3;
    private GestureDetector mGestureDetector;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_home;
    }
    @Override
    public void initContentView(TitleBar titleBar) {
        ButterKnife.bind(this);
        titleBar.setVisibility(View.GONE);
    }


    @Override
    protected void initData() {
        initPermission(Permission.PERMISSIONS, Permission.REQUEST_CODE);

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            /**
             * 发生确定的单击时执行
             * @param e
             * @return
             */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {//单击事件
                //toast("这是单击事件");
                return super.onSingleTapConfirmed(e);
            }

            /**
             * 双击发生时的通知
             * @param e
             * @return
             */
            @Override
            public boolean onDoubleTap(MotionEvent e) {//双击事件
//                toast("这是双击事件");
//                startActivity(new Intent(HomeActivity.this, TestActivity.class));
                return super.onDoubleTap(e);
            }

            /**
             * 双击手势过程中发生的事件，包括按下、移动和抬起事件
             * @param e
             * @return
             */
            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return super.onDoubleTapEvent(e);
            }
        });
        mItem2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });

    }


    @OnClick({R.id.home_ARScan, R.id.home_item2, R.id.home_item3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_ARScan:
                //startActivity(new Intent(this, UnityPlayerNativeActivity1.class));
                startActivity(new Intent(this, WineActivity.class));
                break;
            case R.id.home_item2:
//                startActivity(new Intent(this, UnityPlayerActivity3.class));
                break;
            case R.id.home_item3:
                //startActivity(new Intent(this, UnityPlayerActivity2.class));
                break;
        }
    }

    private long mPreTime;

    @Override
    public void onBackPressed() {
        if (this instanceof HomeActivity) {
            if (System.currentTimeMillis() - mPreTime > 2000) {
                toast("再按一次,退出应用");
                mPreTime = System.currentTimeMillis();
                //getGuaGuayiApplication().exit();
                return;
            }
        }
//        super.onBackPressed();
        exit();
    }


}
