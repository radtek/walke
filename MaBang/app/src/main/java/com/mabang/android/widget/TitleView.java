package com.mabang.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mabang.android.R;

import walke.base.tool.WindowUtil;

/**
 * Created by walke on 2018/2/3.
 * email:1032458982@qq.com
 */

public class TitleView extends LinearLayout implements View.OnClickListener {
    private LinearLayout llParent;
    private View stutasBar;
    private TextView tvRight;
    private ImageView ivLeft;
    private TextView tvCenter;

    public TitleView(Context context) {
        this(context,null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_title, this);
        llParent = ((LinearLayout) findViewById(R.id.vt_parent));
        stutasBar = findViewById(R.id.vt_status);
        ivLeft = ((ImageView) findViewById(R.id.vt_ivLeft));
        tvCenter = ((TextView) findViewById(R.id.vt_tvCenter));
        tvRight = ((TextView) findViewById(R.id.vt_tvRight));
        ivLeft.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        adaptiveNdk(context);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TitleView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.TitleView_ivLeft) {
                int resourceId = a.getResourceId(attr, R.mipmap.ic_launcher);
                ivLeft.setImageResource(resourceId);
            }else if (attr == R.styleable.TitleView_centerText) {
                String str = a.getString(attr);
                if (!TextUtils.isEmpty(str))
                    tvCenter.setText(str);
            } else if (attr == R.styleable.TitleView_rightText) {
                String str = a.getString(attr);
                if (!TextUtils.isEmpty(str))
                    tvRight.setText(str);
            }
        }
        a.recycle();

    }
    private void adaptiveNdk(Context context) {
        int statusBarHeight = WindowUtil.getStatusBarHeight(context);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) stutasBar.getLayoutParams();
        params.height=statusBarHeight;
        stutasBar.setLayoutParams(params);
//        stutasBar.setBackgroundResource(R.drawable.bg_stutas_bar);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            stutasBar.setVisibility(GONE);
        }else {
            stutasBar.setVisibility(VISIBLE);
        }

    }

    public LinearLayout getLlParent() {
        return llParent;
    }

    public View getStutasBar() {
        return stutasBar;
    }

    public TextView getTvRight() {
        return tvRight;
    }

    public ImageView getIvLeft() {
        return ivLeft;
    }

    public TextView getTvCenter() {
        return tvCenter;
    }

    public TitleViewClickListener getTitleViewClickListener() {
        return mTitleViewClickListener;
    }

    @Override
    public void onClick(View v) {
        if (v == ivLeft) {
            if (mTitleViewClickListener != null)
                mTitleViewClickListener.titleViewLeftClick();
        } else if (v == tvRight) {
            if (mTitleViewClickListener != null)
                mTitleViewClickListener.titleViewRightClick();

        }
    }
    public interface TitleViewClickListener {
        void titleViewLeftClick();

        void titleViewRightClick();
    }

    private TitleViewClickListener mTitleViewClickListener;

    public void setTitleBarClickListener(TitleViewClickListener titleViewClickListener) {
        mTitleViewClickListener = titleViewClickListener;
    }
}
