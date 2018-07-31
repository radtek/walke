package com.aipiao.bkpkold.acitivty;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.adapter.ExperManActivityDetailAdapter;
import com.aipiao.bkpkold.base.BaseActivity;
import com.aipiao.bkpkold.bean.aicai.ExpertMan;
import com.aipiao.bkpkold.bean.aicai.ExpertManDetail;
import com.aipiao.bkpkold.views.TopView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/3/20.
 */

public class ExpertManDetailActivity extends BaseActivity {

    private  ImageView statusImageView;
    private TopView forecastDetailTopView;
    private ImageView forecastDetailIcon;
    private TextView forecastDetailName;
    private TextView forecastDetailCount;
    private TextView typeTextView;
    private TextView lotTextView;
    private TextView statusTextView;
    private TextView textView;
    private ListView ExperManlistView;
    private String currentTyoe;
    private ExpertMan.DatasBean datasBean;
    private ExpertManDetail expertManDetail;
    private ExperManActivityDetailAdapter experManActivityDetailAdapter;
    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            currentTyoe = (String) intent.getExtras().get("currentTyoe");
            ExpertMan.DatasBean datasBean = (ExpertMan.DatasBean) intent.getExtras().get("datasBean");
            if (datasBean != null) {
                getExperManDetail(currentTyoe, datasBean.getId());
            }
        }
        forecastDetailTopView.getTopTitleTextView().setText("大神榜详情");
        forecastDetailTopView.getLeftImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void getExperManDetail(String catId, String fid) {
        showLoading();
        OkHttpUtils
                .get()
                .url("https://m.xiaobaicp.com/hemacp-inf-war/inf/expert/getFanganById?catId=" + catId + "&fanganId=" + fid)
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
                    expertManDetail = gson.fromJson(response, ExpertManDetail.class);
                    if (expertManDetail != null && expertManDetail.getData() != null && expertManDetail.getData().getExpert() != null) {
                        Glide.with(ExpertManDetailActivity.this)
                                .load(expertManDetail.getData().getExpert().getPhotoUrl())
                                .into(forecastDetailIcon);
                        forecastDetailName.setText(expertManDetail.getData().getExpert().getName() + "");
                        forecastDetailCount.setText(expertManDetail.getData().getViewCount() + "查看");
                        if (currentTyoe.equals("11111")) {
                            typeTextView.setText("赢11选5");
                        } else if (currentTyoe.equals("11077")) {
                            typeTextView.setText("快乐11选5");
                        } else if (currentTyoe.equals("11070")) {
                            typeTextView.setText("粤11选5");
                        } else if (currentTyoe.equals("11109")) {
                            typeTextView.setText("甘肃11选5");
                        } else if (currentTyoe.equals("30")) {
                            typeTextView.setText("快乐10分");
                        }
                        lotTextView.setText(expertManDetail.getData().getLot()+"");
                        if (expertManDetail.getData().getStatus()!=1){
                            statusImageView.setBackgroundResource(R.mipmap.icon_zhong_jiang);
                            statusTextView.setText("完成");
                            statusTextView.setTextColor(getResources().getColor(R.color.red));
                        }
                        if (experManActivityDetailAdapter!=null){
                            experManActivityDetailAdapter=null;
                        }
                        experManActivityDetailAdapter=new ExperManActivityDetailAdapter(expertManDetail.getData().getFanganDetails());
                        ExperManlistView.setAdapter(experManActivityDetailAdapter);
                    }


                }

            }
        });

    }

    @Override
    protected void initView() {
        statusImageView= (ImageView) findViewById(R.id.statusImageView);
        forecastDetailTopView = (TopView) findViewById(R.id.forecastDetailTopView);
        forecastDetailIcon = (ImageView) findViewById(R.id.forecastDetailIcon);
        forecastDetailName = (TextView) findViewById(R.id.forecastDetailName);
        forecastDetailCount = (TextView) findViewById(R.id.forecastDetailCount);
        typeTextView = (TextView) findViewById(R.id.typeTextView);
        lotTextView = (TextView) findViewById(R.id.lotTextView);
        statusTextView = (TextView) findViewById(R.id.statusTextView);
        textView = (TextView) findViewById(R.id.textView);
        ExperManlistView = (ListView) findViewById(R.id.ExperManlistView);

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_exper_detail;
    }
}
