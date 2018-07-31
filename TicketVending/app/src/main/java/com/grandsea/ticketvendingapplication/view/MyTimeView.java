package com.grandsea.ticketvendingapplication.view;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grandsea.ticketvendingapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Grandsea09 on 2017/10/7.
 */

public class MyTimeView extends LinearLayout {
    private TextView tvDate, tvMin;
    private Handler mHandler=new Handler();
    private Runnable mRunnable ;

    public MyTimeView(Context context) {
        this(context, null);
    }

    public MyTimeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_my_time, this);
        tvDate = ((TextView) findViewById(R.id.vmt_date));
        tvMin = ((TextView) findViewById(R.id.vmt_min));
        setTime();
        refreshTime(1*2000);
    }

    private void setTime() {
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd    HH:mm    ");
        SimpleDateFormat formatter3 = new SimpleDateFormat("HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String dateStr = formatter1.format(curDate);
        String dateStr2 = formatter2.format(curDate);
        String dateStr3 = formatter3.format(curDate);
        tvDate.setText(dateStr+"");
        tvMin.setText(dateStr3+"");
    }

    private void refreshTime(final int time) {
        //保证只有一个mRunnable,并且使用该mRunnable调用自己，不会因为线程乱开而影响程序(导致ANR异常)
        mRunnable =new Runnable() {
            @Override
            public void run() {
                setTime();
                mHandler.postDelayed(this, time);
            }
        };
        mHandler.postDelayed(mRunnable,time);
    }
    public void stopRefresh(){
        mHandler.removeCallbacks(mRunnable);
    }

}
