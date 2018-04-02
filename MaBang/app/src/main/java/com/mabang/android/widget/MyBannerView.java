package com.mabang.android.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import walke.base.R;
import walke.base.tool.ViewUtil;
import walke.base.widget.banner.BannerBaseAdapter;
import walke.base.widget.banner.BannerViewPager;

/**
 * Created by walke.Z on 2017/8/8.
 */

public class MyBannerView extends RelativeLayout implements View.OnClickListener{

    private BannerViewPager mBannerViewPager;
    private LinearLayout pointLinearLayout;
    private BannerBaseAdapter mAdapter;
    private View mEmptyView1;

    public MyBannerView(Context context) {
        this(context,null);
    }

    public MyBannerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initChild(context);
    }

    private void initChild(Context context) {
        mBannerViewPager = new BannerViewPager(context);
        LayoutParams pagerParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mBannerViewPager.setLayoutParams(pagerParams);
        addView(mBannerViewPager);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        int margins = ViewUtil.dpToPx(context, 8);
        params.setMargins(margins,margins,margins,margins);

        pointLinearLayout = new LinearLayout(context);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        addView(pointLinearLayout,params);

        initPoint(3);
        mBannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                //***********选定后点的颜色***************
                if (pointLinearLayout.getChildCount() > 1) {//轮播图大于1
                    int len = position % pointLinearLayout.getChildCount();
                    for (int i = 0; i < pointLinearLayout.getChildCount(); i++) {
                        View dot = pointLinearLayout.getChildAt(i);
                        if (len == i) {
                            dot.setBackgroundResource(R.drawable.point_selected);
                        } else {
                            dot.setBackgroundResource(R.drawable.point_unselect);
                        }
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    public void setAdapter(BannerBaseAdapter adapter){
        if (adapter!=null&&adapter.getDataSize()>0&& mEmptyView1 !=null){
            mEmptyView1.setVisibility(GONE);
        }
        mAdapter = adapter;
        mBannerViewPager.setAdapter(mAdapter);
        int count = mAdapter.getDataSize();
        initPoint(count);
        adapter.setOnDataChangeListener(new BannerBaseAdapter.OnDataChangeListener() {
            @Override
            public void dataChange(List list) {
                initPoint(list.size());
            }
        });

    }

    private void initPoint(int num) {
        if (pointLinearLayout==null||num<1)
            return;
        pointLinearLayout.removeAllViews();

        for (int i = 0; i < num; i++) {
            View point = new View(getContext());
            //大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewUtil.dpToPx(getContext(), 6), ViewUtil.dpToPx(getContext(), 6));
            //距离
            params.leftMargin = ViewUtil.dpToPx(getContext(), 3);
            params.rightMargin = ViewUtil.dpToPx(getContext(), 3);
            point.setLayoutParams(params);
            if (i == 0) {
                point.setBackgroundResource(R.drawable.point_selected);
            } else {
                point.setBackgroundResource(R.drawable.point_unselect);
            }
            pointLinearLayout.addView(point);

        }
    }


    public void startRoll(int startPosition, int cTime) {
        mBannerViewPager.setCurrentItem(startPosition);
        mBannerViewPager.setChangeTime(cTime);//设置切换时间
        mBannerViewPager.startRoll();
    }

    public void setSelectionItem(int position) {
        mBannerViewPager.setCurrentItem(position);
    }
    public int getSelectionItem() {
        return mBannerViewPager.getCurrentItem();
    }

    public BannerBaseAdapter getAdapter() {
        return mAdapter;
    }

    public BannerViewPager getBannerViewPager() {
        return mBannerViewPager;
    }

    public void setEmptyView1(View view) {
        if (view==null)return;
        mEmptyView1 = view;
        this.addView(mEmptyView1);
    }
    public void setEmptyView2(String text) {
        View inflate = View.inflate(getContext(), R.layout.base_banner_empty, null);
        inflate.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mEmptyView1 = inflate;//bbe_pbLoading
        final ProgressBar pbLoading = (ProgressBar) inflate.findViewById(R.id.bbe_pbLoading);
        final TextView tvText = (TextView) inflate.findViewById(R.id.bbe_tvText);
        pbLoading.setVisibility(VISIBLE);
        tvText.setVisibility(GONE);
        if (!TextUtils.isEmpty(text))
            tvText.setText(text);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pbLoading.setVisibility(GONE);
                tvText.setVisibility(VISIBLE);
            }
        },2000);
        this.addView(mEmptyView1);
    }

    @Override
    public void onClick(View v) {//点击事件无效，被内部子控件截取了
        Log.i("walke", "BannerView onClick: 1111111111111111111");
    }
}
