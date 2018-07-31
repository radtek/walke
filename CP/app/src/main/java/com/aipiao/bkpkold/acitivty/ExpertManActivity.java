package com.aipiao.bkpkold.acitivty;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.adapter.ExperManAdapter;
import com.aipiao.bkpkold.base.BaseActivity;
import com.aipiao.bkpkold.bean.aicai.ExpertMan;
import com.aipiao.bkpkold.views.ListViewForScrollView;
import com.aipiao.bkpkold.views.TopView;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/3/20.
 */

public class ExpertManActivity extends BaseActivity {
    private TopRightMenu mTopRightMenu;
    private SwipeRefreshLayout expermanSwipeRefreshLayout;
    private ImageView leftImageView;
    private TopView expermanTop;
    private ExpertMan expertMan;
    private String currentTyoe = "11111";
    private ExperManAdapter experManAdapter;
    private ListViewForScrollView expertListView;

    @Override
    protected void initData() {
        leftImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getExperListData(currentTyoe);

        expermanSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getExperListData(currentTyoe);
            }
        });

        expertListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExpertMan.DatasBean datasBean = expertMan.getDatas().get(position);
                Intent intent=new Intent(ExpertManActivity.this, ExpertManDetailActivity.class);
                intent.putExtra("currentTyoe",currentTyoe);
                intent.putExtra("datasBean",datasBean);
                startActivity(intent);
            }
        });
    }

    private void getExperListData(String currentTyoe) {
        showLoading();
        OkHttpUtils
                .get()
                .url("https://m.xiaobaicp.com/hemacp-inf-war/inf/expert/getFangans?catId=" + currentTyoe + "&pageNo=1&pageSize=50")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                hideLoading();
                toast("请检测你的网络");
                if (expermanSwipeRefreshLayout != null) {
                    expermanSwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onResponse(final String response, int id) {
                hideLoading();
                if (expermanSwipeRefreshLayout != null) {
                    expermanSwipeRefreshLayout.setRefreshing(false);
                }
                if (response != null && !TextUtils.isEmpty(response)) {
                    expertMan = gson.fromJson(response, ExpertMan.class);
                    if (expertMan != null) {
                        if (expertMan.getDatas() != null &&expertMan.getDatas().size()>0) {
                            if (experManAdapter != null) {
                                experManAdapter = null;
                            }
                            experManAdapter = new ExperManAdapter(expertMan.getDatas());
                            expertListView.setAdapter(experManAdapter);
                        }else {
                            if (experManAdapter != null) {
                                experManAdapter = null;
                            }
                            experManAdapter = new ExperManAdapter(expertMan.getDatas());
                            expertListView.setAdapter(experManAdapter);
                            toast("暂时还没有人登榜.....");
                        }
                    }
                }

            }
        });

    }

    @Override
    protected void initView() {
        expertListView = (ListViewForScrollView) findViewById(R.id.expertListView);
        expermanSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.expermanSwipeRefreshLayout);
        leftImageView = (ImageView) findViewById(R.id.leftImageView);
        expermanTop = (TopView) findViewById(R.id.expermanTop);
        expermanTop.getLeftImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        expermanTop.getTopTitleTextView().setText("大神榜");
        expermanTop.getRightImageView().setVisibility(View.VISIBLE);
        expermanTop.getRightImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopRightMenu = new TopRightMenu(ExpertManActivity.this);
                List<MenuItem> menuItems = new ArrayList<>();
                menuItems.add(new MenuItem("赢11选5"));
                menuItems.add(new MenuItem("青11选5"));
                menuItems.add(new MenuItem("快乐11选5"));
                menuItems.add(new MenuItem("粤11选5"));
                menuItems.add(new MenuItem("新11选5"));
                menuItems.add(new MenuItem("甘肃11选5"));
                menuItems.add(new MenuItem("快乐10分"));
                mTopRightMenu
                        .setHeight(480)     //默认高度480
                        .setWidth(420)      //默认宽度wrap_content
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                        .addMenuList(menuItems)
                        .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position) {
                                switch (position) {
                                    case 0:
                                        expermanTop.getTopTitleTextView().setText("大神榜__赢11选5");
                                        currentTyoe = "11111";
                                        break;
                                    case 1:   expermanTop.getTopTitleTextView().setText("大神榜__青11选5");
                                        currentTyoe = "11108";
                                        break;
                                    case 2:expermanTop.getTopTitleTextView().setText("大神榜__快乐11选5");
                                        currentTyoe = "11077";
                                        break;
                                    case 3: expermanTop.getTopTitleTextView().setText("大神榜__粤11选5");
                                        currentTyoe = "11070";
                                        break;
                                    case 4:expermanTop.getTopTitleTextView().setText("大神榜__新11选5");
                                        currentTyoe = "11103";
                                        break;
                                    case 5: expermanTop.getTopTitleTextView().setText("大神榜__甘肃11选5");
                                        currentTyoe = "11109";
                                        break;
                                    case 6:  expermanTop.getTopTitleTextView().setText("大神榜__快乐10分");
                                        currentTyoe = "30";
                                        break;
                                }
                                getExperListData(currentTyoe);

                            }
                        })
                        .showAsDropDown(expermanTop.getRightImageView(), -225, 0);
            }
        });

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_experman;
    }
}
