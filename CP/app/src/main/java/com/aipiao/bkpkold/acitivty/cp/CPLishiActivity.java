package com.aipiao.bkpkold.acitivty.cp;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.adapter.ListOpenAdapter;
import com.aipiao.bkpkold.base.BaseActivity;
import com.aipiao.bkpkold.bean.bmob.Lottery;
import com.aipiao.bkpkold.views.TopView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by caihui on 2018/3/22.
 */

public class CPLishiActivity extends BaseActivity {

    private TopView currentTopView;
    private PullToRefreshListView lishiPullToRefreshListView;
    private String type;
    private ListOpenAdapter listOpenAdapter;
    private int allSize;
    private int pageSize = 10;
    private List<Lottery> lcList;
    @Override
    protected void initData() {
        lishiPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        lishiPullToRefreshListView.setRefreshing(true);
        Intent intent = getIntent();
        if (intent != null) {
            type = (String) intent.getExtras().get("type");
            if (type != null) {
                if (type.equals("11")) {
                    currentTopView.getTopTitleTextView().setText("双色球开奖历史");
                } else if (type.equals("1007")) {
                    currentTopView.getTopTitleTextView().setText("大乐透开奖历史");
                } else if (type.equals("1")) {
                    currentTopView.getTopTitleTextView().setText("福彩3d开奖历史");
                } else if (type.equals("1005")) {
                    currentTopView.getTopTitleTextView().setText("排列3开奖历史");
                } else if (type.equals("1006")) {
                    currentTopView.getTopTitleTextView().setText("排列5开奖历史");
                } else if (type.equals("13")) {
                    currentTopView.getTopTitleTextView().setText("七乐彩开奖历史");
                } else if (type.equals("1008")) {
                    currentTopView.getTopTitleTextView().setText("七星彩开奖历史");
                }
            }
        }
        currentTopView.getLeftImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getCpDataByType(type);
        lishiPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getCpDataByType(type);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                final BmobQuery<Lottery> query = new BmobQuery<>();
                query.order("-time");
                query.addWhereEqualTo("type", type);
                query.findObjects(new FindListener<Lottery>() {
                    @Override
                    public void done(List<Lottery> list, BmobException e) {
                        allSize =list.size();
                        query.order("-time");
                        query.addWhereEqualTo("type", type);
                        pageSize += 10;
                        if (pageSize>allSize){
                            query.setLimit(allSize);
                        }else {
                            query.setLimit(pageSize += 10);
                        }
                        query.findObjects(new FindListener<Lottery>() {
                            @Override
                            public void done(List<Lottery> list, BmobException e) {
                                if (list != null) {
                                    lcList = list;
                                    if (listOpenAdapter != null) {
                                        listOpenAdapter = null;
                                    }
                                    listOpenAdapter = new ListOpenAdapter(list);
                                    lishiPullToRefreshListView.setAdapter(listOpenAdapter);
                                    // 加载完数据设置为不刷新状态，将下拉进度收起来
                                    lishiPullToRefreshListView.onRefreshComplete();
                                }

                            }
                        });
                    }
                });

            }
        });


        lishiPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CPLishiActivity.this, CpCurrentActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("lottery",lcList.get(position));
                startActivity(intent);
                finish();
            }
        });
    }


    private void getCpDataByType(final String type) {
        lishiPullToRefreshListView.setRefreshing(true);
        BmobQuery<Lottery> query = new BmobQuery<>();
        query.addWhereEqualTo("type", type);
        query.order("-time");
        query.setLimit(20);
        query.findObjects(new FindListener<Lottery>() {
            @Override
            public void done(List<Lottery> list, BmobException e) {
                lishiPullToRefreshListView.onRefreshComplete();
                if (list != null && list.size() >= 0) {
                    lcList=list;
                    if (listOpenAdapter != null) {
                        listOpenAdapter = null;
                    }
                    listOpenAdapter = new ListOpenAdapter(list);
                    lishiPullToRefreshListView.setAdapter(listOpenAdapter);
                    lishiPullToRefreshListView.onRefreshComplete();
                }
            }
        });
    }


    @Override
    protected void initView() {

        currentTopView = (TopView) findViewById(R.id.lishiTopView);
        lishiPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lishiPullToRefreshListView);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_lishi;
    }
}
