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
 * Created by Grandsea09 on 2017/10/9.
 */

public class OrderTicketView extends LinearLayout {
    private ImageView ivIcon;
    private TextView tvText;

    public OrderTicketView(Context context) {
        this(context,null);
    }

    public OrderTicketView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public OrderTicketView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_order_ticket, this);
        ivIcon = ((ImageView) findViewById(R.id.vot_icon));
        tvText = ((TextView) findViewById(R.id.vot_tvText));
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SelectTicketViewOld, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.SelectTicketViewOld_img:
                    int resourceId = a.getResourceId(attr, -1);
                    if (-1!=resourceId)
                        ivIcon.setImageResource(resourceId);
                    break;
                case R.styleable.SelectTicketViewOld_textContent:
                    String text = a.getString(attr);
                    if (!TextUtils.isEmpty(text))
                        tvText.setText(text);
                    break;
            }
        }
        a.recycle();
    }

    public ImageView getIvIcon() {
        return ivIcon;
    }

    public TextView getTvText() {
        return tvText;
    }
}
