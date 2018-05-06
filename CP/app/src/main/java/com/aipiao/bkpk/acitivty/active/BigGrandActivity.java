package com.aipiao.bkpk.acitivty.active;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;

import com.aipiao.bkpk.R;
import com.aipiao.bkpk.adapter.big.BigGrandAdapter;
import com.aipiao.bkpk.base.BaseActivity;
import com.aipiao.bkpk.bean.bmob.News;
import com.aipiao.bkpk.views.ListViewForScrollView;
import com.aipiao.bkpk.views.TopView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by caihui on 2018/3/21.
 * 大奖
 */

public class BigGrandActivity extends BaseActivity {
    private TopView bigTopView;
    private ListViewForScrollView bigListViewForScrollView;
    private BigGrandAdapter bigGrandAdapter;
    private SwipeRefreshLayout bigSwipeRefreshLayout;
    private List<News> objects;

    @Override
    protected void initData() {
        bigTopView.getTopTitleTextView().setText("大奖榜");
        getBigGrandData();

        bigListViewForScrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = objects.get(position);
                Intent intent=new Intent(BigGrandActivity.this,BigGrandDetailActivity.class);
                intent.putExtra("news",news);
                startActivity(intent);
            }
        });
    }

    private void getBigGrandData() {
        BmobQuery<News> query = new BmobQuery<News>();
        query.addWhereEqualTo("type","1");
        query.setLimit(50);
        query.findObjects(new FindListener<News>() {
            @Override
            public void done(List<News> object, BmobException e) {
                 if (e == null) {
                     if (object!=null){
                         objects=object;
                     }
                     bigGrandAdapter=new BigGrandAdapter(object);
                     bigListViewForScrollView.setAdapter(bigGrandAdapter);
                }
                bigSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initView() {
        bigSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.bigSwipeRefreshLayout);
        bigTopView = (TopView) findViewById(R.id.bigTopView);
        bigListViewForScrollView = (ListViewForScrollView) findViewById(R.id.bigListViewForScrollView);
        bigTopView.getTopTitleTextView().setText("大奖榜");
        bigTopView.getLeftImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bigSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBigGrandData();
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_big;
    }
}
