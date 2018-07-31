package com.grandsea.ticketvendingapplication.activity.launch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.activity.DestCityActivity;
import com.grandsea.ticketvendingapplication.base.BaseActivity;
import com.grandsea.ticketvendingapplication.util.ViewUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvSellTicket;
    private TextView tvAirport;
    private ImageView ivSetting;
    private ViewPager viewPager;
    private int[] imgs=new int[]{R.mipmap.page1,R.mipmap.page2,R.mipmap.page1,R.mipmap.page2};
    private Runnable mRunnable;
    private Handler mHandler;
    private TextView tvTerminalNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler=new Handler();

        int windowHeight = ViewUtil.getWindowHeight(this);
        int windowWidth = ViewUtil.getWindowWidth(this);

        tvSellTicket =(TextView) findViewById(R.id.am_btSellTicket);
        ivSetting =(ImageView) findViewById(R.id.am_ivSetting);
        tvAirport = (TextView) findViewById(R.id.am_tvAirport);
        viewPager = (ViewPager) findViewById(R.id.am_viewPager);
        tvTerminalNum = (TextView) findViewById(R.id.am_tvTerminalNum);
        tvSellTicket.setOnClickListener(this);
        tvAirport.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
        ivSetting.setVisibility(View.VISIBLE);//

        viewPager.setAdapter(new PageAdapter());
        viewPager.setCurrentItem(imgs.length * 200);
        autoScroll(10*1000);
        getApp().setGetSNCodeListener(this);


    }


    @Override
    public void getSNCodeSuccess(String snCode) {
        super.getSNCodeSuccess(snCode);
        tvTerminalNum.setText("终端号："+snCode+"");//test
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitleBarTRight(tvAirport);
    }

    @Override
    public void onClick(View v) {
        if (v==tvAirport){
            //startActivity(new Intent(MainActivity.this,TestActivity.class));
//            int viewHeight = ViewUtil.getViewHeight(viewPager);
//            int viewWidth = ViewUtil.getViewWidth(viewPager);
//            Log.i("onClick","viewHeight:"+viewHeight+"--------- viewWidth:"+viewWidth);
//            DialogManager.createDialogTwoButton(this,
//                    "当前出发站点 : 珠海机场",
//                    "如果你需要购买从其他站点出发的车票，请点击确认按钮", new DialogManager.DialogTwoButtonClickListener() {
//                        @Override
//                        public void leftOnClick(WindowManager.LayoutParams lp, Dialog dialog) {
//
//                        }
//
//                        @Override
//                        public void rightOnClick(WindowManager.LayoutParams lp, Dialog dialog) {
//
//                        }
//                    });

        }else if (v==ivSetting){
            startActivity(new Intent(MainActivity.this,SettingActivity.class));
        }else if (v== tvSellTicket){
            //跳转获取城市页面
            Log.d("TEST","页面开始跳转");
            Intent intent = new Intent(MainActivity.this,DestCityActivity.class);
            startActivity(intent);
        }
    }


    private void autoScroll( final int time) {
        //保证只有一个mRunnable,并且使用该mRunnable调用自己，不会因为线程乱开而影响程序(导致ANR异常)
        mRunnable =new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                mHandler.postDelayed(this, time);
            }
        };
        mHandler.postDelayed(mRunnable,time);
    }

    class PageAdapter extends PagerAdapter{
        private int len;

        public PageAdapter() {
            len = imgs.length;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % len;// 设置为可以无限滑动 需要对position取模
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setImageResource(imgs[position]);
            container.addView(imageView);
            int viewHeight = ViewUtil.getViewHeight(imageView);
            int viewWidth = ViewUtil.getViewWidth(imageView);
            Log.i("instantiateItem","viewHeight:"+viewHeight+"--------- viewWidth:"+viewWidth);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TEST","页面开始跳转");
                    Intent intent = new Intent(MainActivity.this,DestCityActivity.class);
                    startActivity(intent);
                }
            });
            //return super.instantiateItem(container, position);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}
