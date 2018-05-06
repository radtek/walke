package com.aipiao.bkpk.acitivty;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.aipiao.bkpk.R;
import com.aipiao.bkpk.adapter.ForecasAdapter;
import com.aipiao.bkpk.base.BaseActivity;
import com.aipiao.bkpk.bean.aicai.ForeCastMan;
import com.aipiao.bkpk.views.ListViewForScrollView;
import com.aipiao.bkpk.views.TopView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by caihui on 2018/3/20.
 */

public class ForecastActivity extends BaseActivity {
    private RadioGroup radioGroup1;
    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radio3;
    private RadioButton radio4;
    private TopView foceTopView;

    private ForeCastMan foreCastMan;
    private ForecasAdapter forecasAdapter ;
    private ListViewForScrollView forceListViewForScrollView;
  private SwipeRefreshLayout forceSwipeRefreshLayout;
    private String currentType="71110";


    @Override
    protected void initData() {
        foceTopView.getTopTitleTextView().setText("预测大师");
        foceTopView.getLeftImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getForceastManByType("71110");
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radio1:
                        currentType="71110";
                        getForceastManByType("71110");
                        radio1.setTextColor(getResources().getColor(R.color.windowBackground));
                        radio2.setTextColor(getResources().getColor(R.color.red));
                        radio3.setTextColor(getResources().getColor(R.color.red));
                        radio4.setTextColor(getResources().getColor(R.color.red));
                        break;
                    case R.id.radio2:
                        currentType="93105";
                        getForceastManByType("93105");
                        radio2.setTextColor(getResources().getColor(R.color.windowBackground));
                        radio1.setTextColor(getResources().getColor(R.color.red));
                        radio3.setTextColor(getResources().getColor(R.color.red));
                        radio4.setTextColor(getResources().getColor(R.color.red));
                        break;
                    case R.id.radio3:
                        currentType="82209";
                         getForceastManByType("82209");
                        radio3.setTextColor(getResources().getColor(R.color.windowBackground));
                        radio2.setTextColor(getResources().getColor(R.color.red));
                        radio1.setTextColor(getResources().getColor(R.color.red));
                        radio4.setTextColor(getResources().getColor(R.color.red));
                        break;
                    case R.id.radio4:
                        currentType="10105";
                        getForceastManByType("10105");
                        radio4.setTextColor(getResources().getColor(R.color.windowBackground));
                        radio1.setTextColor(getResources().getColor(R.color.red));
                        radio2.setTextColor(getResources().getColor(R.color.red));
                        radio3.setTextColor(getResources().getColor(R.color.red));
                        break;

                }
            }
        });


        forceListViewForScrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (foreCastMan!=null){
                    List<ForeCastMan.DatasBean> datas = foreCastMan.getDatas();
                    if (datas!=null){
                        ForeCastMan.DatasBean datasBean = datas.get(position);
                        Intent intent=new Intent(ForecastActivity.this,ForecastDetailActivity.class);
                        intent.putExtra("foreCastMan",datasBean);
                        intent.putExtra("currentType",currentType);
                        startActivity(intent);
                    }
                }
            }
        });

        forceSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getForceastManByType(currentType);
            }
        });
    }


    private  void getForceastManByType(String type){
        showLoading();
        OkHttpUtils
                .get()
                .url("https://m.xiaobaicp.com/hemacp-inf-war/inf/ycds/list/dashi?catId=1&pageNo=1&pageSize=20&yuceType="+type)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                forceSwipeRefreshLayout.setRefreshing(false);
                hideLoading();
                toast("请检测你的网络");

            }
            @Override
            public void onResponse(final String response, int id) {
                forceSwipeRefreshLayout.setRefreshing(false);
                hideLoading();
               if (response!=null){
                   foreCastMan=gson.fromJson(response,ForeCastMan.class);
                   if (forecasAdapter!=null){
                       forecasAdapter=null;
                   }
                   if (foreCastMan!=null){
                       if (foreCastMan.getDatas()!=null){
                           forecasAdapter=new ForecasAdapter(foreCastMan.getDatas());
                           forceListViewForScrollView.setAdapter(forecasAdapter);
                       }
                   }else {
                       toast("请检测你的网络");

                   }
               }
            }
        });

//        https://m.xiaobaicp.com/hemacp-inf-war/inf/ycds/list/dashi?catId=1&pageNo=1&pageSize=20&yuceType=93105
    }

    @Override
    protected void initView() {
        forceSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.forceSwipeRefreshLayout);
        foceTopView= (TopView) findViewById(R.id.foceTopView);
        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        radio3 = (RadioButton) findViewById(R.id.radio3);
        radio4 = (RadioButton) findViewById(R.id.radio4);
        forceListViewForScrollView= (ListViewForScrollView) findViewById(R.id.forceListViewForScrollView);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_forecas;
    }
}
