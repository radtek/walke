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

public class ReturnButton extends LinearLayout {
    private ImageView ivLeft;
    private  TextView tvRight;

    public ReturnButton(Context context) {
        this(context,null);
    }

    public ReturnButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ReturnButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.return_button, this);
        ivLeft = ((ImageView) findViewById(R.id.rb_img));
        tvRight = ((TextView) findViewById(R.id.rb_text));
        this.setBackgroundResource(R.drawable.bg_return_button);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ReturnButton, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.ReturnButton_img:
                    int resourceId = a.getResourceId(attr, -1);
                    if (-1!=resourceId)
                        ivLeft.setImageResource(resourceId);
                    break;
                case R.styleable.ReturnButton_textContent:
                    String text = a.getString(attr);
                    if (!TextUtils.isEmpty(text))
                        tvRight.setText(text);
                    break;
            }
        }
        a.recycle();
    }

    public ImageView getIvLeft() {
        return ivLeft;
    }

    public void setIvLeft(ImageView ivLeft) {
        this.ivLeft = ivLeft;
    }

    public TextView getTvRight() {
        return tvRight;
    }

    public void setTvRight(TextView tvRight) {
        this.tvRight = tvRight;
    }
}
