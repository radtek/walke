package com.grandsea.ticketvendingapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grandsea.ticketvendingapplication.R;

/**
 * Created by Grandsea09 on 2017/10/7.
 */

public class CustomButton extends LinearLayout {
    private ImageView ivRight;
    private  TextView tvLeft;

    public CustomButton(Context context) {
        this(context,null);
    }

    public CustomButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.custom_button, this);
        ivRight = ((ImageView) findViewById(R.id.cb_img));
        tvLeft = ((TextView) findViewById(R.id.cb_text));

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomButton, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomButton_img:
                    int resourceId = a.getResourceId(attr, -1);
                    if (-1!=resourceId)
                        ivRight.setImageResource(resourceId);
                    break;
                case R.styleable.CustomButton_textContent:
                    String text = a.getString(attr);
                    if (!TextUtils.isEmpty(text))
                        tvLeft.setText(text);
                    break;
            }
        }
        a.recycle();
    }

    public ImageView getIvRight() {
        return ivRight;
    }

    public void setIvRight(ImageView ivRight) {
        this.ivRight = ivRight;
    }

    public TextView getTvLeft() {
        return tvLeft;
    }

    public void setTvLeft(TextView tvLeft) {
        this.tvLeft = tvLeft;
    }
}
