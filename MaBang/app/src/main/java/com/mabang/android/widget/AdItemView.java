package com.mabang.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mabang.android.R;

/**
 * Created by walke.Z on 2018/4/10.
 */

public class AdItemView extends RelativeLayout {
    private TextView tvName;
    private EditText etDesc;
    private ImageView ivArrow;

    public AdItemView(Context context) {
        this(context,null);
    }

    public AdItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AdItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.view_ad_item, this);
        ivArrow = ((ImageView) findViewById(R.id.vai_ivArrow));//头部地址
        etDesc = ((EditText) findViewById(R.id.vai_etDesc));//头部地址
        tvName = ((TextView) findViewById(R.id.vai_tvName));//头部地址

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AdItemView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.AdItemView_leftText) {
                String str = a.getString(attr);
                if (!TextUtils.isEmpty(str))
                    tvName.setText(str);
            } else if (attr == R.styleable.AdItemView_rightArrowShow) {
                boolean isShowBottom = a.getBoolean(attr, true);
                if (isShowBottom){
                    ivArrow.setVisibility(VISIBLE);
                }else {
                    ivArrow.setVisibility(INVISIBLE);
                }
            }else if (attr == R.styleable.AdItemView_rightEtEnable) {
                boolean istEnable = a.getBoolean(attr, true);
                etDesc.setEnabled(istEnable);
            }
        }
        a.recycle();

    }

    public TextView getTvName() {
        return tvName;
    }

    public EditText getEtDesc() {
        return etDesc;
    }

    public ImageView getIvArrow() {
        return ivArrow;
    }
}
