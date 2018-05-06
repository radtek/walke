package com.aipiao.bkpk.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.aipiao.bkpk.utils.NetUtils;

/**
 * Created by caihui on 2018/3/20.
 */

public abstract class BaseFragment extends Fragment {
    protected View mRootview;
    protected Gson gson;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            mRootview = View.inflate(inflater.getContext(), getLayout(), null);
            gson=new Gson();
            initView();
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!NetUtils.isNetworkAvailable(getContext())) {
            toast("请检测你的网络");
        }
    }

    protected void toast(final String value) {
       getActivity().runOnUiThread(new Runnable() {
           @Override
           public void run() {
               Toast.makeText(getContext(), value, Toast.LENGTH_SHORT).show();
           }
       });

    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract int getLayout();

    protected <V extends View> V findView(int resId) {
        return (V) mRootview.findViewById(resId);
    }

}
