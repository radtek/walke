package com.aipiao.bkpk.acitivty;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.aipiao.bkpk.R;
import com.aipiao.bkpk.adapter.ForeCastDetailAdapter;
import com.aipiao.bkpk.base.BaseActivity;
import com.aipiao.bkpk.bean.aicai.ForeCastMan;
import com.aipiao.bkpk.bean.aicai.ForeCastManDetail;
import com.aipiao.bkpk.views.ListViewForScrollView;
import com.aipiao.bkpk.views.TopView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by caihui on 2018/3/20.
 */

public class ForecastDetailActivity extends BaseActivity {

    private SwipeRefreshLayout mDetailSwipeRefreshLayout;
    private TopView forecastDetailTopView;
    private ImageView forecastDetailIcon;
    private TextView forecastDetailName;
    private TextView forecastDetailCount;
    private ListViewForScrollView forecastDetailListViewForScrollView;

    private ForeCastManDetail foreCastManDetail;
    private ForeCastDetailAdapter foreCastDetailAdapter;
    private ForeCastMan.DatasBean foreCastMan;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            foreCastMan = (ForeCastMan.DatasBean) intent.getExtras().get("foreCastMan");
            String currentType = (String) intent.getExtras().get("currentType");
            getForeCastManDetail(foreCastMan.getCatId() + "", foreCastMan.getDashiId(), currentType);

        }
        forecastDetailTopView.getTopTitleTextView().setText("预测号码");
        forecastDetailTopView.getLeftImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getForeCastManDetail(String catid, String dashiId, String currentType) {
        showLoading();
        if (currentType.equals("71110")){
                catid="11";

        }
        if (currentType.equals("82209")){
            catid="1007";
        }
        if (currentType.equals("93105")){
            catid="1";
        }
        if (currentType.equals("10105")){
            catid="1005";
        }
        OkHttpUtils
                .get()
                .url("https://m.xiaobaicp.com/hemacp-inf-war/inf/ycds/lastrecoder?catId=" + catid + "&dashiId=" + dashiId + "&yuceType=" + currentType)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                hideLoading();
                toast("请检测你的网络");

            }

            @Override
            public void onResponse(final String response, int id) {
                hideLoading();
                if (response != null && !TextUtils.isEmpty(response)) {
                    foreCastManDetail = gson.fromJson(response, ForeCastManDetail.class);
                    if (foreCastDetailAdapter != null) {
                        foreCastDetailAdapter = null;
                    }
                    Glide.with(ForecastDetailActivity.this)
                            .load(foreCastMan.getPhotoUrl())
                            .into(forecastDetailIcon);
                    forecastDetailName.setText(foreCastMan.getName() + "");
                    forecastDetailCount.setText(foreCastMan.getViewCount() + "查看");
                    foreCastDetailAdapter = new ForeCastDetailAdapter(foreCastManDetail.getLastRecoder());
                    forecastDetailListViewForScrollView.setAdapter(foreCastDetailAdapter);
                }

            }
        });

    }

    @Override
    protected void initView() {
        mDetailSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.mDetailSwipeRefreshLayout);
        forecastDetailTopView = (TopView) findViewById(R.id.forecastDetailTopView);
        forecastDetailIcon = (ImageView) findViewById(R.id.forecastDetailIcon);
        forecastDetailName = (TextView) findViewById(R.id.forecastDetailName);
        forecastDetailCount = (TextView) findViewById(R.id.forecastDetailCount);
        forecastDetailListViewForScrollView = (ListViewForScrollView) findViewById(R.id.forecastDetailListViewForScrollView);


        mDetailSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                toast("最新数据了");
                mDetailSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_forecastdetail;
    }


}
