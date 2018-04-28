package com.game4u.arwinebottle.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.game4u.arwinebottle.R;
import com.game4u.arwinebottle.ar.UnityPlayerNativeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import walke.base.widget.TitleBar;

/**
 * Created by walke.Z on 2017/11/2.
 */

public class WinePageActivity extends TitleActivity {
    @BindView(R.id.awp_viewPager)
    ViewPager mViewPager;
    @BindView(R.id.awp_toScan)
    LinearLayout mToScan;
    private int[] imgs=new int[]{R.mipmap.wine1,R.mipmap.wine2,R.mipmap.wine3,
            R.mipmap.wine5,R.mipmap.wine6,R.mipmap.wine7,R.mipmap.wine8};

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_wine_page;
    }


    @Override
    public void initContentView(TitleBar titleBar) {
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        mRootView.setBackgroundResource(R.color.bg_000000);
        titleBar.setTitleText("MEJON ESTATE");
        titleBar.getIvLeft().setVisibility(View.VISIBLE);
        titleBar.getIvLeft().setImageResource(R.mipmap.icon_back_3x);
    }

    @Override
    protected void initData() {
//        initList();

        mViewPager.setAdapter(new WinePageAdapter());

    }

    private void initList() {

    }


    public void toScan(View v) {
        startActivity(new Intent(this, UnityPlayerNativeActivity.class));
    }



    private class WinePageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            return super.instantiateItem(container, position);
            ImageView iv = new ImageView(WinePageActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(lp);
            iv.setImageResource(imgs[position]);
            iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            container.addView(iv);
            return iv;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }

}
