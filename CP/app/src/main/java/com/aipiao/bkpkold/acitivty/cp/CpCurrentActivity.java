package com.aipiao.bkpkold.acitivty.cp;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.caiyi.lottery.LottoTrendActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.aipiao.bkpkold.R;
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

public class CpCurrentActivity extends BaseActivity {


    private TopView currentTopView;
    private TextView cpName;
    private TextView cpQihao;
    private TextView cpTrent;
    private TextView cpLishi;
    private LinearLayout ballLinearLayout;
    private TextView historyn1;
    private TextView historyn2;
    private TextView historyn3;
    private TextView historyn4;
    private TextView historyn5;
    private TextView historyn6;
    private TextView historyn7;
    private TextView historyn8;
    private TextView cpPoll;
    private TextView cpTime;
    private TextView cpSale;
    private String type;
    private LinearLayout rootLinearLayout;
    private PullToRefreshScrollView mLcPullToRefreshScrollView;


    private LinearLayout ssqView;
    private LinearLayout fucaiView;
    /**
     * ssq
     */
    private TextView oneZ;
    private TextView oneP;
    private TextView onesZ;
    private TextView onesP;
    private TextView onesssZ;
    private TextView onesssP;
    private TextView onessssZ;
    private TextView onessssP;
    private TextView onesssssZ;
    private TextView onesssssP;
    private TextView onessssssZ;
    private TextView onessssssP;
    private TextView onesssssssZ;
    private TextView onesssssssP;


    private LinearLayout l1;

    private LinearLayout l2;
    private LinearLayout l3;
    private LinearLayout l4;
    private LinearLayout l5;
    private LinearLayout l6;


//
//   11 双色球
// * 1007  大乐透
// * 1 福彩3d
// * 1005 排列3
// * 1006 排列5
// * 13 七乐彩
// * 1008 七星彩

    private Lottery lottery;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            type = (String) intent.getExtras().get("type");
            lottery = (Lottery) intent.getExtras().get("lottery");
            if (type != null) {
                if (type.equals("11")) {
                    ssqView.setVisibility(View.VISIBLE);
                    currentTopView.getTopTitleTextView().setText("双色球开奖详情");
                    cpName.setText("双色球");

                } else if (type.equals("1007")) {
                    currentTopView.getTopTitleTextView().setText("大乐透开奖详情");
                    cpName.setText("大乐透");

                } else if (type.equals("1")) {

                    ssqView.setVisibility(View.VISIBLE);
                    currentTopView.getTopTitleTextView().setText("福彩3d开奖详情");

                    cpName.setText("福彩3d");
                } else if (type.equals("1005")) {
                    ssqView.setVisibility(View.VISIBLE);
                    currentTopView.getTopTitleTextView().setText("排列3开奖详情");
                    cpName.setText("排列3");

                } else if (type.equals("1006")) {
                    ssqView.setVisibility(View.VISIBLE);
                    currentTopView.getTopTitleTextView().setText("排列5开奖详情");
                    cpName.setText("排列5");

                } else if (type.equals("13")) {
                    ssqView.setVisibility(View.VISIBLE);
                    currentTopView.getTopTitleTextView().setText("七乐彩开奖详情");
                    cpName.setText("七乐彩");

                } else if (type.equals("1008")) {
                    ssqView.setVisibility(View.VISIBLE);
                    currentTopView.getTopTitleTextView().setText("七星彩开奖详情");
                    cpName.setText("七星彩");
                }
            }
            if (TextUtils.isEmpty(lottery.getNo())) {
                getCpDataByType(type);
            } else {
                getCpDataByType(lottery);
            }


        }

        currentTopView.getLeftImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLcPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getCpDataByType(type);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }
        });
    }

    private void getCpDataByType(Lottery lottery) {

        cpQihao.setText("第 " + lottery.getNo() + " 期 ");
        cpPoll.setText("奖池:" + lottery.getPool() + " 元 ");
        cpSale.setText("销售:" + lottery.getSale() + " 元");
        cpTime.setText(" " + lottery.getTime() + " ");
        if (type.equals("11")) { //双色球
            String[] numbers = lottery.getNumbers().split(",");
            if (numbers.length >= 6) {
                historyn1.setText(numbers[0]);
                historyn2.setText(numbers[1]);
                historyn3.setText(numbers[2]);
                historyn4.setText(numbers[3]);
                historyn5.setText(numbers[4]);
                historyn6.setText(numbers[5]);
                historyn6.setBackgroundResource(R.drawable.blue_radius_style);
                historyn7.setBackgroundResource(R.drawable.blue_radius_style);
                historyn7.setText(numbers[6]);
                historyn8.setVisibility(View.GONE);
            }
        } else if (type.equals("1007")) { //大乐透
            String[] numbers = lottery.getNumbers().split(",");
            historyn1.setText(numbers[0]);
            historyn2.setText(numbers[1]);
            historyn3.setText(numbers[2]);
            historyn4.setText(numbers[3]);
            historyn5.setText(numbers[4]);
            historyn6.setText(numbers[5]);
            historyn6.setBackgroundResource(R.drawable.blue_radius_style);
            historyn7.setBackgroundResource(R.drawable.blue_radius_style);
            historyn7.setText(numbers[6]);
            historyn8.setVisibility(View.GONE);
        } else if (type.equals("1")) { //福彩3d
            String[] numbers = lottery.getNumbers().split(",");
            if (numbers.length >= 2) {
                historyn1.setText(numbers[0]);
                historyn2.setText(numbers[1]);
                historyn3.setText(numbers[2]);
                historyn4.setVisibility(View.GONE);
                historyn5.setVisibility(View.GONE);
                historyn6.setVisibility(View.GONE);
                historyn7.setVisibility(View.GONE);
                historyn8.setVisibility(View.GONE);
            }
        } else if (type.equals("1005")) {
            //排列3
            String[] numbers = lottery.getNumbers().split(",");
            historyn1.setText(numbers[0]);
            historyn2.setText(numbers[1]);
            historyn3.setText(numbers[2]);
            historyn4.setVisibility(View.GONE);
            historyn5.setVisibility(View.GONE);
            historyn6.setVisibility(View.GONE);
            historyn7.setVisibility(View.GONE);
            historyn8.setVisibility(View.GONE);

        } else if (type.equals("1006")) {
            //排列5
            String[] numbers = lottery.getNumbers().split(",");
            historyn1.setText(numbers[0]);
            historyn2.setText(numbers[1]);
            historyn3.setText(numbers[2]);
            historyn4.setText(numbers[3]);
            historyn5.setText(numbers[4]);
            historyn6.setVisibility(View.GONE);
            historyn7.setVisibility(View.GONE);
            historyn8.setVisibility(View.GONE);
        } else if (type.equals("13")) {//七乐彩
            String[] numbers = lottery.getNumbers().split(",");
            if (numbers.length >= 7) {
                historyn1.setText(numbers[0]);
                historyn2.setText(numbers[1]);
                historyn3.setText(numbers[2]);
                historyn4.setText(numbers[3]);
                historyn5.setText(numbers[4]);
                historyn6.setText(numbers[5]);
                historyn7.setText(numbers[6]);
                historyn7.setBackgroundResource(R.drawable.red_radius_style);
                historyn8.setText(numbers[7]);
            }

        } else if (type.equals("1008")) { //七星彩
            String[] numbers = lottery.getNumbers().split(",");
            if (numbers.length >= 6) {
                historyn1.setText(numbers[0]);
                historyn2.setText(numbers[1]);
                historyn3.setText(numbers[2]);
                historyn4.setText(numbers[3]);
                historyn5.setText(numbers[4]);
                historyn6.setText(numbers[5]);
                historyn6.setBackgroundResource(R.drawable.red_radius_style);
                historyn7.setBackgroundResource(R.drawable.red_radius_style);
                historyn7.setText(numbers[6]);
                historyn8.setVisibility(View.GONE);
            }
        }
        initSSQData(lottery);

    }

    private void getCpDataByType(final String type) {
        mLcPullToRefreshScrollView.setRefreshing(true);
        BmobQuery<Lottery> query = new BmobQuery<>();
        query.addWhereEqualTo("type", type);
        query.order("-time");
        query.setLimit(1);
        query.findObjects(new FindListener<Lottery>() {
            @Override
            public void done(List<Lottery> list, BmobException e) {
                mLcPullToRefreshScrollView.onRefreshComplete();
                if (list != null && list.size() >= 0) {
                    Lottery lottery = list.get(0);
                    cpQihao.setText("第 " + lottery.getNo() + " 期 ");
                    cpPoll.setText("奖池:" + lottery.getPool() + " 元 ");
                    cpSale.setText("销售:" + lottery.getSale() + " 元");
                    cpTime.setText(" " + lottery.getTime() + " ");
                    if (type.equals("11")) { //双色球
                        String[] numbers = lottery.getNumbers().split(",");
                        if (numbers.length >= 6) {
                            historyn1.setText(numbers[0]);
                            historyn2.setText(numbers[1]);
                            historyn3.setText(numbers[2]);
                            historyn4.setText(numbers[3]);
                            historyn5.setText(numbers[4]);
                            historyn6.setText(numbers[5]);
                            historyn6.setBackgroundResource(R.drawable.blue_radius_style);
                            historyn7.setBackgroundResource(R.drawable.blue_radius_style);
                            historyn7.setText(numbers[6]);
                            historyn8.setVisibility(View.GONE);
                        }
                    } else if (type.equals("1007")) { //大乐透
                        String[] numbers = lottery.getNumbers().split(",");
                        historyn1.setText(numbers[0]);
                        historyn2.setText(numbers[1]);
                        historyn3.setText(numbers[2]);
                        historyn4.setText(numbers[3]);
                        historyn5.setText(numbers[4]);
                        historyn6.setText(numbers[5]);
                        historyn6.setBackgroundResource(R.drawable.blue_radius_style);
                        historyn7.setBackgroundResource(R.drawable.blue_radius_style);
                        historyn7.setText(numbers[6]);
                        historyn8.setVisibility(View.GONE);
                    } else if (type.equals("1")) { //福彩3d
                        String[] numbers = lottery.getNumbers().split(",");
                        if (numbers.length >= 2) {
                            historyn1.setText(numbers[0]);
                            historyn2.setText(numbers[1]);
                            historyn3.setText(numbers[2]);
                            historyn4.setVisibility(View.GONE);
                            historyn5.setVisibility(View.GONE);
                            historyn6.setVisibility(View.GONE);
                            historyn7.setVisibility(View.GONE);
                            historyn8.setVisibility(View.GONE);
                        }
                    } else if (type.equals("1005")) {
                        //排列3
                        String[] numbers = lottery.getNumbers().split(",");
                        historyn1.setText(numbers[0]);
                        historyn2.setText(numbers[1]);
                        historyn3.setText(numbers[2]);
                        historyn4.setVisibility(View.GONE);
                        historyn5.setVisibility(View.GONE);
                        historyn6.setVisibility(View.GONE);
                        historyn7.setVisibility(View.GONE);
                        historyn8.setVisibility(View.GONE);

                    } else if (type.equals("1006")) {
                        //排列5
                        String[] numbers = lottery.getNumbers().split(",");
                        historyn1.setText(numbers[0]);
                        historyn2.setText(numbers[1]);
                        historyn3.setText(numbers[2]);
                        historyn4.setText(numbers[3]);
                        historyn5.setText(numbers[4]);
                        historyn6.setVisibility(View.GONE);
                        historyn7.setVisibility(View.GONE);
                        historyn8.setVisibility(View.GONE);
                    } else if (type.equals("13")) {//七乐彩
                        String[] numbers = lottery.getNumbers().split(",");
                        if (numbers.length >= 7) {
                            historyn1.setText(numbers[0]);
                            historyn2.setText(numbers[1]);
                            historyn3.setText(numbers[2]);
                            historyn4.setText(numbers[3]);
                            historyn5.setText(numbers[4]);
                            historyn6.setText(numbers[5]);
                            historyn7.setText(numbers[6]);
                            historyn7.setBackgroundResource(R.drawable.red_radius_style);
                            historyn8.setText(numbers[7]);
                        }

                    } else if (type.equals("1008")) { //七星彩
                        String[] numbers = lottery.getNumbers().split(",");
                        if (numbers.length >= 6) {
                            historyn1.setText(numbers[0]);
                            historyn2.setText(numbers[1]);
                            historyn3.setText(numbers[2]);
                            historyn4.setText(numbers[3]);
                            historyn5.setText(numbers[4]);
                            historyn6.setText(numbers[5]);
                            historyn6.setBackgroundResource(R.drawable.red_radius_style);
                            historyn7.setBackgroundResource(R.drawable.red_radius_style);
                            historyn7.setText(numbers[6]);
                            historyn8.setVisibility(View.GONE);
                        }
                    }
                    initSSQData(lottery);
                }
            }
        });

    }


    /**
     * ssq
     * <p>
     * //
     * //   11 双色球
     * // * 1007  大乐透
     * // * 1 福彩3d
     * // * 1005 排列3
     * // * 1006 排列5
     * // * 13 七乐彩
     * // * 1008 七星彩
     *
     * @param lottery
     */
    private void initSSQData(Lottery lottery) {
        String[] winners = lottery.getWinners().split(",");
        String[] amounts = lottery.getAmount().split(",");
        try {
            if (lottery.getType().equals("11")) {
                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.VISIBLE);
                l3.setVisibility(View.VISIBLE);
                l4.setVisibility(View.VISIBLE);
                l5.setVisibility(View.VISIBLE);
                l6.setVisibility(View.VISIBLE);
                // 双色球
                if (winners.length >= 5) {
                    oneZ.setText(winners[0] + " 注 ");
                    onesZ.setText(winners[1] + " 注 ");
                    onesssZ.setText(winners[2] + " 注 ");
                    onessssZ.setText(winners[3] + " 注 ");
                    onesssssZ.setText(winners[4] + " 注 ");
                    onessssssZ.setText(winners[5] + " 注 ");
                }
                if (winners.length >= 5) {
                    oneP.setText(amounts[0] + " 元 ");
                    onesP.setText(amounts[1] + " 元 ");
                    onesssP.setText(amounts[2] + " 元 ");
                    onessssP.setText(amounts[3] + " 元 ");
                    onesssssP.setText(amounts[4] + " 元 ");
                    onessssssP.setText(amounts[5] + " 元 ");
                }
            } else if (lottery.getType().equals("13") || lottery.getType().equals("1008")) {
                //七乐彩   七星彩
                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.VISIBLE);
                if (winners.length >= 2) {
                    oneZ.setText(winners[0] + " 注 ");
                    onesZ.setText(winners[1] + " 注 ");
                }
                if (winners.length >= 2) {
                    oneP.setText(amounts[0] + " 元 ");
                    onesP.setText(amounts[1] + " 元 ");
                }
            } else if (lottery.getType().equals("1") || lottery.getType().equals("1005")) {
                //3d   排列3
                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.VISIBLE);
                l3.setVisibility(View.VISIBLE);
                if (winners.length >= 3) {
                    oneZ.setText(winners[0] + " 注 ");
                    onesZ.setText(winners[1] + " 注 ");
                    onesssZ.setText(winners[2] + " 注 ");
                }
                if (winners.length >= 3) {
                    oneP.setText(amounts[0] + " 元 ");
                    onesP.setText(amounts[1] + " 元 ");
                    onesssP.setText(amounts[2] + " 元 ");

                }
            } else if (lottery.getType().equals("1006")) {
                //  排列5
                l1.setVisibility(View.VISIBLE);

                if (winners.length >= 1) {
                    oneZ.setText(winners[0] + " 注 ");
                }
                if (winners.length >= 1) {
                    oneP.setText(amounts[0] + " 元 ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void initView() {
        ssqView = (LinearLayout) findViewById(R.id.ssqView);
        initSSqView();
        rootLinearLayout = (LinearLayout) findViewById(R.id.rootLinearLayout);
        currentTopView = (TopView) findViewById(R.id.currentTopView);
        cpName = (TextView) findViewById(R.id.cpName);
        cpQihao = (TextView) findViewById(R.id.cpQihao);
        cpTrent = (TextView) findViewById(R.id.cpTrent);
        cpLishi = (TextView) findViewById(R.id.cpLishi);
        ballLinearLayout = (LinearLayout) findViewById(R.id.ballLinearLayout);
        historyn1 = (TextView) findViewById(R.id.historyn1);
        historyn2 = (TextView) findViewById(R.id.historyn2);
        historyn3 = (TextView) findViewById(R.id.historyn3);
        historyn4 = (TextView) findViewById(R.id.historyn4);
        historyn5 = (TextView) findViewById(R.id.historyn5);
        historyn6 = (TextView) findViewById(R.id.historyn6);
        historyn7 = (TextView) findViewById(R.id.historyn7);
        historyn8 = (TextView) findViewById(R.id.historyn8);
        cpPoll = (TextView) findViewById(R.id.cpPoll);
        cpTime = (TextView) findViewById(R.id.cpTime);
        cpSale = (TextView) findViewById(R.id.cpSale);
        l1 = (LinearLayout) findViewById(R.id.l1);
        l2 = (LinearLayout) findViewById(R.id.l2);
        l3 = (LinearLayout) findViewById(R.id.l3);
        l4 = (LinearLayout) findViewById(R.id.l4);
        l5 = (LinearLayout) findViewById(R.id.l5);
        l6 = (LinearLayout) findViewById(R.id.l6);
        cpTrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CpCurrentActivity.this, LottoTrendActivity.class);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });

        mLcPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.mLcPullToRefreshScrollView);

        cpLishi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CpCurrentActivity.this, CPLishiActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });
    }

    private void initSSqView() {
        oneZ = (TextView) findViewById(R.id.oneZ);
        oneP = (TextView) findViewById(R.id.oneP);
        onesZ = (TextView) findViewById(R.id.onesZ);
        onesP = (TextView) findViewById(R.id.onesP);
        onesssZ = (TextView) findViewById(R.id.onesssZ);
        onesssP = (TextView) findViewById(R.id.onesssP);
        onessssZ = (TextView) findViewById(R.id.onessssZ);
        onessssP = (TextView) findViewById(R.id.onessssP);
        onesssssZ = (TextView) findViewById(R.id.onesssssZ);
        onesssssP = (TextView) findViewById(R.id.onesssssP);
        onessssssZ = (TextView) findViewById(R.id.onessssssZ);
        onessssssP = (TextView) findViewById(R.id.onessssssP);
        onesssssssZ = (TextView) findViewById(R.id.onesssssssZ);
        onesssssssP = (TextView) findViewById(R.id.onesssssssP);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_cp_current;
    }
}
