package com.aipiao.bkpkold.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.acitivty.cp.CpCurrentActivity;
import com.aipiao.bkpkold.adapter.OpenAdapter;
import com.aipiao.bkpkold.base.BaseFragment;
import com.aipiao.bkpkold.bean.aicai.OpenCode;
import com.aipiao.bkpkold.bean.bmob.Lottery;
import com.aipiao.bkpkold.views.TopView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by caihui on 2018/3/21.
 */

public class OpenFragment extends BaseFragment {
    private TopView openTopView;
    private PullToRefreshListView openPullToRefreshListView;
    private OpenCode openCode;
    private String url = "https://m.xiaobaicp.com/hemacp-inf-war/inf/lotterynum/latest?catId=11%2C1007%2C1%2C1005%2C1006%2C13%2C1008%2C1201%2C1207%2C1206%2C2001%2C2002%2C2003%2C1203%2C1204%2C1205%2C1209%2C1210%2C1202%2C1211";
    private OpenAdapter openAdapter;

    @Override
    protected void initView() {
        openTopView = findView(R.id.openTopView);
        openPullToRefreshListView = findView(R.id.openPullToRefreshListView);
        openTopView.getLeftImageView().setVisibility(View.GONE);
        openTopView.getTopTitleTextView().setText("开奖大厅");
    }

    @Override
    protected void initData() {
        openPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        getOpenCode();


//
//   11 双色球
// * 1007  大乐透
// * 1 福彩3d
// * 1005 排列3
// * 1006 排列5
// * 13 七乐彩
// * 1008 七星彩

        openPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), CpCurrentActivity.class);
                Lottery lottery=new Lottery();
                switch (position) {
                    case 1:
                        intent.putExtra("type", "11");
                        break;
                    case 2:
                        intent.putExtra("type", "1007");
                        break;
                    case 3:
                        intent.putExtra("type", "1");
                        break;
                    case 4:
                        intent.putExtra("type", "1005");
                        break;
                    case 5:
                        intent.putExtra("type", "1006");
                        break;
                    case 6:
                        intent.putExtra("type", "13");
                        break;
                    case 7:
                        intent.putExtra("type", "1008");
                        break;
                }
                intent.putExtra("lottery", lottery);
                startActivity(intent);
            }
        });
    }

    private void getOpenCode() {
        openPullToRefreshListView.setRefreshing(true);
        OkHttpUtils
                .get()
                .url(url)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                openPullToRefreshListView.onRefreshComplete();

            }

            @Override
            public void onResponse(final String response, int id) {
                if (!TextUtils.isEmpty(response)) {
                    openCode = gson.fromJson(response, OpenCode.class);
                    if (openCode != null) {
                        if (openAdapter != null) {
                            openAdapter = null;
                        }
                        openAdapter = new OpenAdapter(openCode);
                        openPullToRefreshListView.setAdapter(openAdapter);
                    }
                }
                openPullToRefreshListView.onRefreshComplete();
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_open;
    }

    public static OpenFragment newInstance() {
        OpenFragment openFragment = new OpenFragment();
        return openFragment;
    }
}
