package com.aipiao.bkpk.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.aipiao.bkpk.R;
import com.aipiao.bkpk.acitivty.CpDoubleChoseNumberActivity;
import com.aipiao.bkpk.acitivty.ExpertManActivity;
import com.aipiao.bkpk.acitivty.ForecastActivity;
import com.aipiao.bkpk.acitivty.WebViewlActivity;
import com.aipiao.bkpk.acitivty.active.BigGrandActivity;
import com.aipiao.bkpk.acitivty.miji.CheatsActivity;
import com.aipiao.bkpk.adapter.HomeAdapter;
import com.aipiao.bkpk.adapter.fb.FootBallAdapter;
import com.aipiao.bkpk.base.BaseFragment;
import com.aipiao.bkpk.bean.bmob.PushNewsTitle;
import com.aipiao.bkpk.bean.wubai.PushFb;
import com.aipiao.bkpk.views.MarqueeView;
import com.aipiao.bkpk.views.MyGridView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by caihui on 2018/3/20.
 */

public class HomeFragment extends BaseFragment {
    private SwipeRefreshLayout homeSwipeRefreshLayout;
    private Banner homeBanner;
    private int[] imageViewsUrl = {R.mipmap.lll};
    private List<Integer> imageViews;
    private PushNewsTitle mBmobpushNewsTitle;

    private TextView yuceTextView, winTextView;
    private MarqueeView homeMarqueeView;


    private PushFb pushFb;
    private FootBallAdapter footBallAdapter;


    private MyGridView homegridview;

    private LinearLayout bigAwarLinearLayout, agaginAwarLinearLayout, winAwarLinearLayout;

    @Override
    protected void initView() {
        bigAwarLinearLayout = findView(R.id.bigAwarLinearLayout);
        agaginAwarLinearLayout = findView(R.id.agaginAwarLinearLayout);
        winAwarLinearLayout = findView(R.id.winAwarLinearLayout);
        homegridview = findView(R.id.homegridview);
        homeSwipeRefreshLayout = findView(R.id.homeSwipeRefreshLayout);
        homeBanner = findView(R.id.homeBanner);
        homeMarqueeView = findView(R.id.homeMarqueeView);
        yuceTextView = findView(R.id.yuceTextView);
        winTextView = findView(R.id.winTextView);
        winAwarLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CheatsActivity.class));

            }
        });

/**
 * caid
 * 11 双色球
 * 1007  大乐透
 * 1 福彩3d
 * 1005 排列3
 * 1006 排列5
 * 13 七乐彩
 * 1008 七星彩
 */
        homegridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), CpDoubleChoseNumberActivity.class);
                switch (position) {
                    case 0:
                        intent.putExtra("cid", "11");
                        startActivity(intent);
                        break;
                    case 1:
                        intent.putExtra("cid", "1007");
                        startActivity(intent);
                        break;
                    case 2:
                        intent.putExtra("cid", "1");
                        startActivity(intent);
                        break;
                    case 3:
                        intent.putExtra("cid", "1005");
                        startActivity(intent);
                        break;
                    case 4:
                        intent.putExtra("cid", "1006");
                        startActivity(intent);
                        break;
                    case 5:
                        intent.putExtra("cid", "13");
                        startActivity(intent);
                        break;
                    case 6:
                        intent.putExtra("cid", "1008");
                        startActivity(intent);
                        break;
                    case 7:
                        toast("暂无到时间点");
//                        startActivity(new Intent(getContext(), GameFootballActivity.class));
//                        break;
                    case 8:
                        break;
                }

            }
        });


        agaginAwarLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), WebViewlActivity.class));

            }
        });

        bigAwarLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BigGrandActivity.class));
            }
        });

        yuceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ForecastActivity.class));
            }
        });

        winTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ExpertManActivity.class));
            }
        });

        homeSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPushNews();
//                getPushFootBall();
            }
        });
    }

    @Override
    protected void initData() {
        imageViews = new ArrayList<>();
        for (int i = 0; i < imageViewsUrl.length; i++) {
            imageViews.add(imageViewsUrl[i]);
        }
        //设置图片加载器
        homeBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        homeBanner.setImages(imageViews);
        //banner设置方法全部调用完毕时最后调用
        homeBanner.start();
        homegridview.setAdapter(new HomeAdapter());

//        getPushNews();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    public static Fragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPushNews();
//        getPushFootBall();
    }

    private void getPushFootBall() {
        OkHttpUtils
                .post()
                .url("https://t.zhuoyicp.com/live_zhuoyicp/kaijang/recents")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(final String response, int id) {
                Log.d("---------", response);

            }

        });
    }

    private final OkHttpClient client = new OkHttpClient();

    private void getPushNews() {
        BmobQuery<PushNewsTitle> query = new BmobQuery<PushNewsTitle>();
        query.setLimit(50);
        query.findObjects(new FindListener<PushNewsTitle>() {
            @Override
            public void done(List<PushNewsTitle> object, BmobException e) {
                homeSwipeRefreshLayout.setRefreshing(false);
                if (e == null) {
                    List<String> mTitle = new ArrayList<String>();
                    if (object != null && object.size() > 0) {
                        for (PushNewsTitle pushNewsTitle : object) {
                            mTitle.add(pushNewsTitle.getTitle());
                        }
                        homeMarqueeView.startWithList(mTitle);
                    }
                }
            }
        });

    }


    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext())
                    .load(path)
                    .into(imageView);
        }


    }
}
