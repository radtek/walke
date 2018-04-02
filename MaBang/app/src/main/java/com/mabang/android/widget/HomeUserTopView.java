package com.mabang.android.widget;

import android.content.Context;
import android.os.Build;
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

public class HomeUserTopView extends LinearLayout implements View.OnClickListener {
    private  MyEditText etSearch;
//    private  SearchView etSearch;
    private LinearLayout leftLayout;
    private View stutasBar;
    private ImageView ivLeft,ivSearch;
    private TextView tvCity;
    public HomeUserTopView(Context context) {
        this(context,null);
    }

    public HomeUserTopView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HomeUserTopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_home_user_top, this);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundResource(R.color.title_bg);
        stutasBar = findViewById(R.id.vhut_status);
        leftLayout = ((LinearLayout) findViewById(R.id.vhut_leftLayout));
        ivLeft = ((ImageView) findViewById(R.id.vhut_ivCityIcon));
        tvCity = ((TextView) findViewById(R.id.vhut_tvCity));
        ivSearch = ((ImageView) findViewById(R.id.vhut_ivSearch));
        etSearch = ((MyEditText) findViewById(R.id.vhut_etSearch));
//        etSearch = ((SearchView) findViewById(R.id.vht_etSearch));
        leftLayout.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        adaptiveNdk(context);

    }
    private void adaptiveNdk(Context context) {
        int statusBarHeight = WindowUtil.getStatusBarHeight(context);
        LayoutParams params = (LayoutParams) stutasBar.getLayoutParams();
        params.height=statusBarHeight;
        stutasBar.setLayoutParams(params);
//        stutasBar.setBackgroundResource(R.drawable.bg_stutas_bar);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            stutasBar.setVisibility(GONE);
        }else {
            stutasBar.setVisibility(VISIBLE);
        }

    }

    public TextView getTvCity() {
        return tvCity;
    }

    public MyEditText getEtSearch() {
        return etSearch;
    }

    @Override
    public void onClick(View v) {
        if (v == leftLayout) {
            if (mHomeTopViewClickListener != null)
                mHomeTopViewClickListener.onCityClick();

        } else if (v == ivSearch) {
            if (mHomeTopViewClickListener != null)
                mHomeTopViewClickListener.onSearchClick();

        }
    }
    public interface HomeTopViewClickListener {
        void onCityClick();

        void onSearchClick();
    }

    private HomeTopViewClickListener mHomeTopViewClickListener;

    public void setHomeTopViewClickListener(HomeTopViewClickListener homeTopViewClickListener) {
        mHomeTopViewClickListener = homeTopViewClickListener;
    }
}
