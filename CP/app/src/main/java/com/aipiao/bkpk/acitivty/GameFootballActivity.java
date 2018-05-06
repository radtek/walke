package com.aipiao.bkpk.acitivty;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.aipiao.bkpk.R;
import com.aipiao.bkpk.adapter.MatchAdapter;
import com.aipiao.bkpk.adapter.fb.GameFootballAdapter;
import com.aipiao.bkpk.base.BaseActivity;
import com.aipiao.bkpk.bean.GameFootball;
import com.aipiao.bkpk.utils.DateUtil;
import com.aipiao.bkpk.views.TopView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

/**
 * Created by caihui on 2018/4/8.
 */

public class GameFootballActivity extends BaseActivity {
    private TopView gameFootballTopView;
    private TextView totalTextView;
    private ImageView filterImageView;
    private ImageView filterTimeImageView;
    private PullToRefreshListView gameFootballListViewForScrollView;
    private GameFootball gameFootball;
    private GameFootballAdapter gameFootballAdapter;
    private AlertDialog dialog;
    List<GameFootball.DatasBean> mSelectTimeBeans = new ArrayList<>();
    private DrawerLayout drawerlayout;
    private ListView rightListView;
    private List<GameFootball.DatasBean> matchTypeList = new ArrayList<>();
    private MatchAdapter matchAdapter;

    @Override
    protected void initData() {

        gameFootballTopView.getLeftImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gameFootballTopView.getTopTitleTextView().setText("竞彩足球");
        getGameFootballData();
    }

    @Override
    protected void initView() {
        rightListView = (ListView) findViewById(R.id.rightListView);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        gameFootballTopView = (TopView) findViewById(R.id.gameFootballTopView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        filterImageView = (ImageView) findViewById(R.id.filterImageView);
        filterTimeImageView = (ImageView) findViewById(R.id.filterTimeImageView);
        gameFootballListViewForScrollView = (PullToRefreshListView) findViewById(R.id.gameFootballListViewForScrollView);
        gameFootballListViewForScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                getGameFootballData();
            }
        });

        gameFootballListViewForScrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<GameFootball.DatasBean> datas = gameFootball.getDatas();
                GameFootball.DatasBean datasBean = datas.get(position-1);
                Intent intent=new Intent(GameFootballActivity.this,GameFootballDetailActivity.class);
                intent.putExtra("dataid",datasBean.getDataid());
                startActivity(intent);
            }
        });

        rightListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerlayout.closeDrawer(Gravity.RIGHT);
                GameFootball.DatasBean datasBean = matchTypeList.get(position);
                if (datasBean!=null){
                    getGameFootballDataByName(datasBean.getMname());
                }
            }
        });

        filterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlayout.openDrawer(Gravity.RIGHT);
            }
        });

        filterTimeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(GameFootballActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        getGameFootballDataByDate(DateUtil.getStringDate(date));
//                       toast("date "+ DateUtil.getStringDate(date));
                    }
                }).build();
                pvTime.show();
//                toast("a");
//                View popupView = View.inflate(GameFootballActivity.this,R.layout.pop_football_time, null);
//                PopupWindow window = new PopupWindow(popupView,400,300);
//                window.setAnimationStyle(R.style.popup_window_anim);
//                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
//                window.setFocusable(true);
//                window.setOutsideTouchable(true);
//                window.update();
//                window.showAsDropDown(gameFootballListViewForScrollView, 0, 20);
            }
        });
    }

    private void getGameFootballDataByName(String mname) {
        if (gameFootball != null) {
            List<GameFootball.DatasBean> datas = gameFootball.getDatas();
            if (datas != null) {
                for (GameFootball.DatasBean db : datas) {
                    if (db.getMname().contains(mname)) {
                        mSelectTimeBeans.add(db);
                    }
                }
                hideLoading();
                totalTextView.setText("全部赛事[" + mSelectTimeBeans.size() + "]场");
                if (gameFootballAdapter != null) {
                    gameFootballAdapter.notifyDataSetChanged(mSelectTimeBeans);
                } else {
                    gameFootballAdapter = new GameFootballAdapter(mSelectTimeBeans);
                }
                gameFootballListViewForScrollView.setAdapter(gameFootballAdapter);
            }
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_football;
    }

    private void getGameFootballDataByDate(String time) {
        showLoading();
        if (gameFootball != null) {
            List<GameFootball.DatasBean> datas = gameFootball.getDatas();
            if (datas != null) {
                for (GameFootball.DatasBean db : datas) {
                    if (db.getMt().split(" ")[0].contains(time)) {
                        mSelectTimeBeans.add(db);
                    }
                }
                hideLoading();
                totalTextView.setText("全部赛事[" + mSelectTimeBeans.size() + "]场");
                if (gameFootballAdapter != null) {
                    gameFootballAdapter.notifyDataSetChanged(mSelectTimeBeans);
                } else {
                    gameFootballAdapter = new GameFootballAdapter(mSelectTimeBeans);
                }
                gameFootballListViewForScrollView.setAdapter(gameFootballAdapter);
            }
        }
    }

    private void getGameFootballData() {
        showLoading();
        OkHttpUtils.get().url("https://t.zhuoyicp.com/mapi_yjcp/api/gain/dzInfo?lotId=42&playType=hh&issue=no&sid=52001001189").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                hideLoading();
                gameFootballListViewForScrollView.onRefreshComplete();
                toast("请检测您的网络");
            }

            @Override
            public void onResponse(String response, int id) {
                hideLoading();
                gameFootballListViewForScrollView.onRefreshComplete();
                if (!TextUtils.isEmpty(response)) {
                    gameFootball = gson.fromJson(response, GameFootball.class);
                    if (gameFootball != null) {
                        if (gameFootball.getDatas() != null) {
                            filterMatchData(gameFootball.getDatas());
                            totalTextView.setText("全部赛事[" + gameFootball.getDatas().size() + "]场");
                            if (gameFootballAdapter != null) {
                                gameFootballAdapter.notifyDataSetChanged(gameFootball.getDatas());
                            } else {
                                gameFootballAdapter = new GameFootballAdapter(gameFootball.getDatas());
                            }
                            gameFootballListViewForScrollView.setAdapter(gameFootballAdapter);
                        }
                    }
                }

            }
        });
    }

    private void filterMatchData(List<GameFootball.DatasBean> datas) {
        for (int i = 0; i < datas.size() - 1; i++) {
            for (int j = datas.size() - 1; j > i; j--) {
                if (datas.get(j).getMname().equals(datas.get(i).getMname())) {
                    datas.remove(j);
                }
            }
        }
        matchTypeList=datas;
        matchAdapter=new MatchAdapter(datas);
        rightListView.setAdapter(matchAdapter);

        Log.d("--",datas.toString());

    }
}
