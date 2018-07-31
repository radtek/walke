package com.grandsea.ticketvendingapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grandsea.ticketvendingapplication.R;


/**
 * Created by Grandsea09 on 2017/10/7.
 */

public class TitleBar extends LinearLayout {
    private RelativeLayout rightLayout;
    private ImageView ivLeft;
    private  TextView tvCenter ,tvRight;

    public TitleBar(Context context) {
        this(context,null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.title_bar, this);
        ivLeft = ((ImageView) findViewById(R.id.tb_ivLeft));
        tvCenter = ((TextView) findViewById(R.id.tb_textCenter));
        tvRight = ((TextView) findViewById(R.id.tb_textRight));
        rightLayout = ((RelativeLayout) findViewById(R.id.tb_rightLayout));

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.TitleBar_rightHide:
                    boolean rightHide = a.getBoolean(attr, false);
                    if (rightHide)
                        rightLayout.setVisibility(GONE);
                    break;
            }
        }
        a.recycle();
    }

    public ImageView getIvLeft() {
        return ivLeft;
    }

    public TextView getTvCenter() {
        return tvCenter;
    }

    public TextView getTvRight() {
        return tvRight;
    }
}
