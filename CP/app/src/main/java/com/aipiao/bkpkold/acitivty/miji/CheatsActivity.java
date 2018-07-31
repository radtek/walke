package com.aipiao.bkpkold.acitivty.miji;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;

import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.acitivty.active.BigGrandDetailActivity;
import com.aipiao.bkpkold.adapter.mijj.CheatsAdapter;
import com.aipiao.bkpkold.base.BaseActivity;
import com.aipiao.bkpkold.bean.bmob.News;
import com.aipiao.bkpkold.views.ListViewForScrollView;
import com.aipiao.bkpkold.views.TopView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 秘籍
 * Created by caihui on 2018/3/21.
 */

public class CheatsActivity extends BaseActivity {

    private TopView cheastTopView;
    private SwipeRefreshLayout cheastSwipeRefreshLayout;
    private ListViewForScrollView cheastListViewForScrollView;
    private List<News> newses;
    private CheatsAdapter cheatsAdapter;

    @Override
    protected void initData() {
        cheastTopView.getLeftImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cheastListViewForScrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = newses.get(position);
                Intent intent=new Intent(CheatsActivity.this,BigGrandDetailActivity.class);
                intent.putExtra("news",news);
                startActivity(intent);
            }
        });
        cheastTopView.getTopTitleTextView().setText("竞彩秘籍");
        getBigGrandData();
        cheastSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBigGrandData();
            }
        });
    }
    private void getBigGrandData() {
        BmobQuery<News> query = new BmobQuery<News>();
        query.addWhereEqualTo("type", "2");
        query.setLimit(50);
        query.findObjects(new FindListener<News>() {
            @Override
            public void done(List<News> object, BmobException e) {
                if (e == null) {
                    if (object != null) {
                        newses = object;
                    }
                    cheatsAdapter = new CheatsAdapter(object);
                    cheastListViewForScrollView.setAdapter(cheatsAdapter);
                }
                cheastSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initView() {
        cheastTopView = (TopView) findViewById(R.id.cheastTopView);
        cheastSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.cheastSwipeRefreshLayout);
        cheastListViewForScrollView = (ListViewForScrollView) findViewById(R.id.cheastListViewForScrollView);

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_cheast;
    }
}
