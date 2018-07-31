package com.grandsea.ticketvendingapplication.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.TicketVendingApplication;
import com.grandsea.ticketvendingapplication.util.LogUtil;
import com.grandsea.ticketvendingapplication.util.ToastUtil;

/**
 * Created by Grandsea09 on 2017/9/30.
 */

public class BaseActivity extends AppCompatActivity implements TicketVendingApplication.GetSNCodeListener {

    public TicketVendingApplication getApp() {
        return (TicketVendingApplication) getApplication();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getApp().setGetSNCodeListener(this);

    }

    @Override
    public void getSNCodeSuccess(String snCode) {
        getSNSuccess(snCode);
    }

    private void getSNSuccess(String snCode) {

    }


    /**
     * SpannableStringBuilder的使用,TextView部分字体变颜色
     */
    public void setTitleBarTRight(TextView tv) {
        String depart_station_name = getApp().getLocationInfo().getDepart_station_name();
        if (!TextUtils.isEmpty(depart_station_name)) {
            if (depart_station_name.length() >4)
                depart_station_name=depart_station_name.substring(0,4);
            tv.setText("当前:" + depart_station_name);
        }
    }
    /**
     * SpannableStringBuilder的使用,TextView部分字体变颜色
     */
    public void tvSpannable(TextView tv, String front, String tag, String after, int color) {
        String str = front + tag + after;
        int winStart = str.indexOf(tag);
        int winEnd = winStart + tag.length();
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new ForegroundColorSpan(color), winStart, winEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(style);
    }


    /**
     * 使用SpannableStringBuilder设置样式——图片
     */
    public void tvSpannableImg(TextView textView,String strAll,String tag) {
        int i = strAll.indexOf(tag);
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(strAll);
        ImageSpan imageSpan = new ImageSpan(this, R.mipmap.date_right_60x60);
        //将index为6、7的字符用图片替代
        spannableString.setSpan(imageSpan, i, i+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(spannableString);
    }


    protected void toast(String message){
        if (!TextUtils.isEmpty(message))
            ToastUtil.showToast(this,message);
    }
    protected void middleToast(String message){
        if (!TextUtils.isEmpty(message))
            ToastUtil.showMidlleToast(this,message);
    }

    protected void logI(String message){
        if (!TextUtils.isEmpty(message))
            LogUtil.i(this.getClass().getSimpleName(),"-------------------> "+message);
    }
    protected void logD(String message){
        if (!TextUtils.isEmpty(message))
            LogUtil.d(this.getClass().getSimpleName(),"--------> "+message);
    }
    protected void logE(String message){
        if (!TextUtils.isEmpty(message))
            LogUtil.e(this.getClass().getSimpleName(),"---------      -----------> "+message);
    }


}
