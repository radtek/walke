package com.aipiao.bkpk.acitivty;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.aipiao.bkpk.R;
import com.aipiao.bkpk.base.BaseActivity;
import com.aipiao.bkpk.bean.match.MatchInfo;
import com.aipiao.bkpk.bean.match.MatchIntegral;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by caihui on 2018/4/10.
 */

public class GameFootballDetailActivity extends BaseActivity {
    private String dataid;

    private TextView backTextView;
    private ImageView homeImageView;
    private TextView homeTextView;
    private TextView timeTextView;
    private TextView scoreTextView;
    private TextView tabTextView;
    private ImageView keduiImageView;
    private TextView keduiTextView;
    private LinearLayout mainandguestteamLinearLayout;
    private LinearLayout mainandguestteamDataLinearLayout1;
    private TextView teamdataTextView;
    private TextView matchTextView;
    private TextView matchTextView1;
    private TextView shengpingFuTextView;
    private TextView lostinTextView;
    private LinearLayout mainandguestteamDataLinearLayout2;
    private TextView teamdataTextView2;
    private TextView matchTextView2;
    private TextView matchTextView21;
    private TextView shengpingFuTextView2;
    private TextView lostinTextView2;

 private  String type="";
    private MatchInfo matchInfo;
    private MatchIntegral matchIntegral;


    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            dataid = (String) intent.getExtras().get("dataid");
        }
        getDataMatchInfo(dataid);
        getDataMatchInfoHistory(dataid);
    }

    private void getDataMatchInfoHistory(String dataid) {

        String url = "http://t.zhuoyicp.com/data_zhuoyicp/zq/zqForeCast/jfDw/" + dataid + "/0";
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                if (!TextUtils.isEmpty(response)) {
                    matchIntegral = gson.fromJson(response, MatchIntegral.class);
                    MatchIntegral.JfHomeBean jf_home = matchIntegral.getJf_home();
                    teamdataTextView.setText(jf_home.getSai()+" "+jf_home.getName());
                    matchTextView.setTag(type);
                    matchTextView1.setText(jf_home.getSai()+"");
                    shengpingFuTextView.setText(jf_home.getWin()+"/"+jf_home.getPing()+"/"+jf_home.getLose());
                    lostinTextView.setText(jf_home.getJin()+"/"+jf_home.getShi());

                    MatchIntegral.JfAwayBean jf_away = matchIntegral.getJf_away();
                    teamdataTextView2.setText(jf_away.getSai()+" "+jf_away.getName());
                    matchTextView2.setTag(type);
                    matchTextView21.setText(jf_away.getSai()+"");
                    shengpingFuTextView2.setText(jf_away.getWin()+"/"+jf_away.getPing()+"/"+jf_away.getLose());
                    lostinTextView2.setText(jf_away.getJin()+"/"+jf_away.getShi());

                }
            }
        });
//
    }

    private void getDataMatchInfo(String dataid) {
        String url = "http://t.zhuoyicp.com/data_zhuoyicp/zq/zqForeCast/foreCastDetail/" + dataid;
        OkHttpUtils.get().url(url)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                if (!TextUtils.isEmpty(response)) {
                    matchInfo = gson.fromJson(response, MatchInfo.class);
                    MatchInfo.TitleBean titleBean = matchInfo.getTitle();
                    if (titleBean != null) {
                        Glide.with(GameFootballDetailActivity.this)
                                .load(titleBean.getHomePhoto())
                                .into(homeImageView);
                        Glide.with(GameFootballDetailActivity.this)
                                .load(titleBean.getGuestPhoto())
                                .into(keduiImageView);
                        homeTextView.setText(titleBean.getHome() + "");
                        keduiTextView.setText(titleBean.getAwary() + "");
                        tabTextView.setText(titleBean.getUnion() + "");
                        type=titleBean.getUnion();
                        if (titleBean.getHomeScore() != null && titleBean.getAwaryScore() != null) {
                            scoreTextView.setText(titleBean.getHomeScore() + " :  " + titleBean.getAwaryScore() + " ");
                        } else {
                            scoreTextView.setText("0:0");
                        }
                        timeTextView.setText(titleBean.getStartTime() + "");
                    }

                    Log.d("----", matchInfo.toString());
                }
            }
        });
    }

    @Override
    protected void initView() {
        backTextView = (TextView) findViewById(R.id.backTextView);
        homeImageView = (ImageView) findViewById(R.id.homeImageView);
        homeTextView = (TextView) findViewById(R.id.homeTextView);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        tabTextView = (TextView) findViewById(R.id.tabTextView);
        keduiImageView = (ImageView) findViewById(R.id.keduiImageView);
        keduiTextView = (TextView) findViewById(R.id.keduiTextView);
        mainandguestteamLinearLayout = (LinearLayout) findViewById(R.id.mainandguestteamLinearLayout);
        mainandguestteamDataLinearLayout1 = (LinearLayout) findViewById(R.id.mainandguestteamDataLinearLayout1);
        teamdataTextView = (TextView) findViewById(R.id.teamdataTextView);
        matchTextView = (TextView) findViewById(R.id.matchTextView);
        matchTextView1 = (TextView) findViewById(R.id.matchTextView1);
        shengpingFuTextView = (TextView) findViewById(R.id.shengpingFuTextView);
        lostinTextView = (TextView) findViewById(R.id.lostinTextView);
        mainandguestteamDataLinearLayout2 = (LinearLayout) findViewById(R.id.mainandguestteamDataLinearLayout2);
        teamdataTextView2 = (TextView) findViewById(R.id.teamdataTextView2);
        matchTextView2 = (TextView) findViewById(R.id.matchTextView2);
        matchTextView21 = (TextView) findViewById(R.id.matchTextView21);
        shengpingFuTextView2 = (TextView) findViewById(R.id.shengpingFuTextView2);
        lostinTextView2 = (TextView) findViewById(R.id.lostinTextView2);
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_game_detail;
    }
}
