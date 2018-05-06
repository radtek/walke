package com.aipiao.bkpk.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aipiao.bkpk.R;


/**
 * @author View
 * @date 2018/2/6 9:01
 */
public class TopView extends LinearLayout {
    private ImageView leftImageView;
    private  ImageView rightImageView;
    private TextView topTitleTextView;

    public TopView(Context context) {
        this(context, null);
    }

    public TopView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.top_view, this);
        leftImageView= (ImageView) findViewById(R.id.leftImageView);
        rightImageView= (ImageView) findViewById(R.id.rightImageView);
        topTitleTextView= (TextView) findViewById(R.id.topTitleTextView);
    }


    public ImageView getLeftImageView() {
        return leftImageView;
    }

    public void setLeftImageView(ImageView leftImageView) {
        this.leftImageView = leftImageView;
    }

    public ImageView getRightImageView() {
        return rightImageView;
    }

    public void setRightImageView(ImageView rightImageView) {
        this.rightImageView = rightImageView;
    }

    public TextView getTopTitleTextView() {
        return topTitleTextView;
    }

    public void setTopTitleTextView(TextView topTitleTextView) {
        this.topTitleTextView = topTitleTextView;
    }
}
