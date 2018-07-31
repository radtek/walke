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
import com.grandsea.ticketvendingapplication.util.ViewUtil;

/**
 * Created by Grandsea09 on 2017/10/8.
 */

public class ImageTextView extends LinearLayout {
    private ImageView ivRight;
    private TextView tvLeft;

    public ImageTextView(Context context) {
        this(context,null);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_img_text, this);
        ivRight = ((ImageView) findViewById(R.id.vit_icon));
        tvLeft = ((TextView) findViewById(R.id.vit_text));

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ImageTextView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.ImageTextView_img:
                    int resourceId = a.getResourceId(attr, -1);
                    if (-1!=resourceId)
                        ivRight.setImageResource(resourceId);
                    break;
                case R.styleable.ImageTextView_textContent:
                    String text = a.getString(attr);
                    if (!TextUtils.isEmpty(text))
                        tvLeft.setText(text);
                    break;
                case R.styleable.ImageTextView_interval:
                    int interval = a.getInt(attr, ViewUtil.dipTopx(getContext(),5));
                    LinearLayout.LayoutParams lp = (LayoutParams) tvLeft.getLayoutParams();
                    lp.leftMargin=interval;
                    tvLeft.setLayoutParams(lp);
                    break;
            }
        }
        a.recycle();
    }

    public ImageView getIvRight() {
        return ivRight;
    }

    public TextView getTvLeft() {
        return tvLeft;
    }
}
