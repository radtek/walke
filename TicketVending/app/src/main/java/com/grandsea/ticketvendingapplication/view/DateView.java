package com.grandsea.ticketvendingapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grandsea.ticketvendingapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Grandsea09 on 2017/10/8.
 */

public class DateView extends LinearLayout implements View.OnClickListener {

    public static final long DAY_SECEND =24*60*60*1000;

    private TextView tvDate;
    private ImageView ivDateLeft,ivDateRight;
    private int day=0;
    private long timeSpan;

    public DateView(Context context) {
        this(context,null);
    }

    public DateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_date, this);
        ivDateLeft = ((ImageView) findViewById(R.id.vd_dateLeft));
        ivDateRight = ((ImageView) findViewById(R.id.vd_dateRight));
        tvDate = ((TextView) findViewById(R.id.vd_date));
        ivDateLeft.setOnClickListener(this);
        ivDateRight.setOnClickListener(this);
        setDate(System.currentTimeMillis());
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DateView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.DateView_changeEnable:
                    boolean changeEnable = a.getBoolean(attr, true);
                    if (!changeEnable){
                        ivDateLeft.setEnabled(false);
                        ivDateLeft.setImageResource(R.mipmap.date_left_gray);
                        ivDateRight.setEnabled(false);
                        ivDateRight.setImageResource(R.mipmap.date_right_gray);
                    }

                    break;
            }
        }
        a.recycle();

    }

    public ImageView getIvDateLeft() {
        return ivDateLeft;
    }

    public ImageView getIvDateRight() {
        return ivDateRight;
    }

    public TextView getTvDate() {
        return tvDate;
    }

    private void setDate(long time) {
        Date curDate = new Date(time);//获取当前时间 //System.currentTimeMillis()
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy年MM月dd日");
        String dateStr = formatter1.format(curDate);
        tvDate.setText(dateStr+"");
    }

    public void setDateText(long time) {
        Date curDate = new Date(time);//获取当前时间 //System.currentTimeMillis()
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy年MM月dd日");
        String dateStr = formatter1.format(curDate);
        tvDate.setText(dateStr+"");
    }
    public void setDateText(String time) {
        tvDate.setText(time+"");
    }

    @Override
    public void onClick(View v) {
        if (v==ivDateLeft){
            day--;
//            timeSpan = day*24*60*60*1000;
//            setDate(System.currentTimeMillis()+ timeSpan);
//            timeSpan = day*24*60*60*1000;
            if (null!=dateChangeListener)
                dateChangeListener.minus(day);
        }else if (v==ivDateRight){
            day++;
            //setDate(System.currentTimeMillis()+ day*24*60*60*1000);
//            timeSpan = day*;
            if (null!=dateChangeListener)
                dateChangeListener.add(day);
        }
        timeSpan = day*DAY_SECEND;
        setDate(System.currentTimeMillis()+ timeSpan);

    }

    public long getTimeSpan() {
        return timeSpan;
    }

    public interface DateChangeListener{
        void add(int day);
        void minus(int day);
    }

    private DateChangeListener dateChangeListener;

    public DateChangeListener getDateChangeListener() {
        return dateChangeListener;
    }

    public void setDateChangeListener(DateChangeListener dateChangeListener) {
        this.dateChangeListener = dateChangeListener;
    }


    public static void main(String ...args){
        testDate(System.currentTimeMillis()-24*60*60*1000);
        testDate(System.currentTimeMillis()+24*60*60*1000);
    }

    private static void testDate(long time) {
        Date curDate = new Date(time);//获取当前时间 //System.currentTimeMillis()
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy年MM月dd日");
        String dateStr = formatter1.format(curDate);
        System.out.println("-------->>> "+dateStr);
    }
}
