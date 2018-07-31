package com.aipiao.bkpkold.fragment;

import android.support.v4.app.Fragment;

import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.base.BaseFragment;

/**
 * Created by chennaikang on 2018/3/24.
 */

public class HotnewsFragment extends BaseFragment {
    @Override
    protected void initView() {


    }

    @Override
    protected void initData() {


    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_hotnews;
    }

    public static Fragment newInstance() {

        HotnewsFragment hotnewsFragment=new HotnewsFragment();
        return  hotnewsFragment;
    }
}
