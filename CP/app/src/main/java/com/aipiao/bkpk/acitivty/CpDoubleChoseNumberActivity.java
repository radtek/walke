package com.aipiao.bkpk.acitivty;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caiyi.lottery.LottoTrendActivity;
import com.aipiao.bkpk.R;
import com.aipiao.bkpk.acitivty.cp.CPLishiActivity;
import com.aipiao.bkpk.base.BaseActivity;
import com.aipiao.bkpk.views.TopView;
import com.aipiao.bkpk.views.choseredblue.ShakeListener;
import com.aipiao.bkpk.views.choseview.SelectBallsView;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caihui on 2018/3/23.
 */

public class CpDoubleChoseNumberActivity extends BaseActivity implements View.OnClickListener {
    private TopView choseTopView;

    private ShakeListener mShakeListener = null;
    private Vibrator mVibrator;

    private TopRightMenu mTopRightMenu;
    private TextView redTextView, blueTextView;
    private int selectHongNumber = 0, selectLanNumber = 0;
    private LinearLayout redAndblueLinearLayout;
    private LinearLayout otherCohseLinearLayout;
    private LinearLayout wanweiLinearLayout;
    private LinearLayout qianweiLinearLayout;
    private LinearLayout baiweiLinearLayout;
    private LinearLayout shiweiLinearLayout;
    private LinearLayout geweiLinearLayout;


    private SelectBallsView redSelectBallsView;
    private SelectBallsView blueSelectBallsView;
    private SelectBallsView wanweiSelectBallsView;//万位
    private SelectBallsView qianweiSelectBallsView;//千位
    private SelectBallsView baiweiSelectBallsView;//百位
    private SelectBallsView shiweiSelectBallsView;//十位
    private SelectBallsView geweiSelectBallsView;//个位
    private List<SelectBallsView> SelectBallsViewList = new ArrayList<>();
    private StringBuffer choseNumber = new StringBuffer();
    private ClipboardManager cm;
    private String cid;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initData() {
        cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        Intent intent = getIntent();
        if (intent != null) {
            /**
             * caid
             * 11 双色球
             * 1007  大乐透
             * 1 福彩3d
             * 1005 排列3
             * 1006 排列5
             * 13 七乐彩
             * 1008 七星彩
             */
            cid = (String) intent.getExtras().get("cid");
            if (cid.equals("11")) {
                choseTopView.getTopTitleTextView().setText("双色球模拟选号");
                redAndblueLinearLayout.setVisibility(View.VISIBLE);
                redSelectBallsView.setNumCount(9);
                redSelectBallsView.setStartNum(1);
                redSelectBallsView.setEndNum(35);
                blueSelectBallsView.setNumCount(8);
                blueSelectBallsView.setStartNum(1);
                blueSelectBallsView.setEndNum(16);
                redSelectBallsView.initBalls();
                blueSelectBallsView.initBalls();
            } else if (cid.equals("1007")) {
                choseTopView.getTopTitleTextView().setText("大乐透模拟选号");

                redAndblueLinearLayout.setVisibility(View.VISIBLE);
                redSelectBallsView.setNumCount(9);
                redSelectBallsView.setStartNum(1);
                redSelectBallsView.setEndNum(33);
                blueSelectBallsView.setNumCount(7);
                blueSelectBallsView.setStartNum(1);
                blueSelectBallsView.setEndNum(12);
                redSelectBallsView.initBalls();
                blueSelectBallsView.initBalls();
            } else if (cid.equals("1")) {
                choseTopView.getTopTitleTextView().setText("福彩3d模拟选号");
                otherCohseLinearLayout.setVisibility(View.VISIBLE);
                baiweiLinearLayout.setVisibility(View.VISIBLE);
                shiweiLinearLayout.setVisibility(View.VISIBLE);
                geweiLinearLayout.setVisibility(View.VISIBLE);

                baiweiSelectBallsView.setNumCount(6);
                baiweiSelectBallsView.setStartNum(0);
                baiweiSelectBallsView.setEndNum(10);


                shiweiSelectBallsView.setNumCount(6);
                shiweiSelectBallsView.setStartNum(0);
                shiweiSelectBallsView.setEndNum(10);


                geweiSelectBallsView.setNumCount(6);
                geweiSelectBallsView.setStartNum(0);
                geweiSelectBallsView.setEndNum(10);
                baiweiSelectBallsView.initBalls();
                shiweiSelectBallsView.initBalls();
                geweiSelectBallsView.initBalls();

            } else if (cid.equals("1005")) {
                choseTopView.getTopTitleTextView().setText("排列3模拟选号");
                otherCohseLinearLayout.setVisibility(View.VISIBLE);
                baiweiLinearLayout.setVisibility(View.VISIBLE);
                shiweiLinearLayout.setVisibility(View.VISIBLE);
                geweiLinearLayout.setVisibility(View.VISIBLE);

                baiweiSelectBallsView.setNumCount(6);
                baiweiSelectBallsView.setStartNum(0);
                baiweiSelectBallsView.setEndNum(10);


                shiweiSelectBallsView.setNumCount(6);
                shiweiSelectBallsView.setStartNum(0);
                shiweiSelectBallsView.setEndNum(10);


                geweiSelectBallsView.setNumCount(6);
                geweiSelectBallsView.setStartNum(0);
                geweiSelectBallsView.setEndNum(10);
                baiweiSelectBallsView.initBalls();
                shiweiSelectBallsView.initBalls();
                geweiSelectBallsView.initBalls();
            } else if (cid.equals("1006")) {
                choseTopView.getTopTitleTextView().setText("排列5模拟选号");

                otherCohseLinearLayout.setVisibility(View.VISIBLE);
                wanweiLinearLayout.setVisibility(View.VISIBLE);
                qianweiLinearLayout.setVisibility(View.VISIBLE);
                baiweiLinearLayout.setVisibility(View.VISIBLE);
                shiweiLinearLayout.setVisibility(View.VISIBLE);
                geweiLinearLayout.setVisibility(View.VISIBLE);

                wanweiSelectBallsView.setNumCount(5);
                wanweiSelectBallsView.setStartNum(0);
                wanweiSelectBallsView.setEndNum(10);

                qianweiSelectBallsView.setNumCount(5);
                qianweiSelectBallsView.setStartNum(0);
                qianweiSelectBallsView.setEndNum(10);

                baiweiSelectBallsView.setNumCount(5);
                baiweiSelectBallsView.setStartNum(0);
                baiweiSelectBallsView.setEndNum(10);


                shiweiSelectBallsView.setNumCount(5);
                shiweiSelectBallsView.setStartNum(0);
                shiweiSelectBallsView.setEndNum(10);

                geweiSelectBallsView.setNumCount(5);
                geweiSelectBallsView.setStartNum(0);
                geweiSelectBallsView.setEndNum(10);
                wanweiSelectBallsView.initBalls();
                qianweiSelectBallsView.initBalls();
                baiweiSelectBallsView.initBalls();
                shiweiSelectBallsView.initBalls();
                geweiSelectBallsView.initBalls();

            } else if (cid.equals("13")) {
                choseTopView.getTopTitleTextView().setText("七乐彩模拟选号");
                redAndblueLinearLayout.setVisibility(View.VISIBLE);
                blueSelectBallsView.setVisibility(View.GONE);
                redSelectBallsView.setNumCount(5);
                redSelectBallsView.setStartNum(1);
                redSelectBallsView.setEndNum(35);
                redSelectBallsView.initBalls();

            } else if (cid.equals("1008")) {
                choseTopView.getTopTitleTextView().setText("七星彩模拟选号");
                otherCohseLinearLayout.setVisibility(View.VISIBLE);
                wanweiLinearLayout.setVisibility(View.VISIBLE);
                qianweiLinearLayout.setVisibility(View.VISIBLE);
                baiweiLinearLayout.setVisibility(View.VISIBLE);
                shiweiLinearLayout.setVisibility(View.VISIBLE);
                geweiLinearLayout.setVisibility(View.VISIBLE);

                wanweiSelectBallsView.setNumCount(5);
                wanweiSelectBallsView.setStartNum(0);
                wanweiSelectBallsView.setEndNum(10);

                qianweiSelectBallsView.setNumCount(5);
                qianweiSelectBallsView.setStartNum(0);
                qianweiSelectBallsView.setEndNum(10);

                baiweiSelectBallsView.setNumCount(5);
                baiweiSelectBallsView.setStartNum(0);
                baiweiSelectBallsView.setEndNum(10);


                shiweiSelectBallsView.setNumCount(5);
                shiweiSelectBallsView.setStartNum(0);
                shiweiSelectBallsView.setEndNum(10);

                geweiSelectBallsView.setNumCount(5);
                geweiSelectBallsView.setStartNum(0);
                geweiSelectBallsView.setEndNum(10);
                wanweiSelectBallsView.initBalls();
                qianweiSelectBallsView.initBalls();
                baiweiSelectBallsView.initBalls();
                shiweiSelectBallsView.initBalls();
                geweiSelectBallsView.initBalls();
            }
        }

        mVibrator = (Vibrator) getApplication().getSystemService(VIBRATOR_SERVICE);
        mShakeListener = new ShakeListener(this);
        // 当手机摇晃时
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            public void onShake() {
                mShakeListener.stop();
                startVibrato(); // 开始 震动
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        randomSelect();
                        mVibrator.cancel();
                        mShakeListener.start();
                    }
                }, 1000);
            }
        });
    }

    private void randomSelect() {
        for (final SelectBallsView selectBallsView : SelectBallsViewList) {
            selectBallsView.randomSelectInitBalls();
        }
        showMiss();
        for (final SelectBallsView selectBallsView : SelectBallsViewList) {
            selectBallsView.setSelectBallPickListener(new SelectBallsView.SelectBallPickListener() {
                @Override
                public void pickOnClickListener() {
                    choseNumber.append(selectBallsView.getSelectBallsString());
                }
            });
        }
//        redSelectBallsView.randomSelectInitBalls();
//        blueSelectBallsView.randomSelectInitBalls();
//        showMiss();
//
//        String str1 = redSelectBallsView.getSelectBallsString();
//        selectHongNumber = redSelectBallsView.getSelectBallsString().length();
//        redTextView.setText(str1 + "");
//
//        String str2 = blueSelectBallsView.getSelectBallsString();
//        selectLanNumber = blueSelectBallsView.getSelectBallsString().length();
//        blueTextView.setText(str2 + "");
    }

    // 定义震动
    public void startVibrato() {

        mVibrator.vibrate(new long[]{200, 400, 0, 400}, -1);
        // 第一个｛｝里面是节奏数组，第一个参数为等待指定时间后开始震动，震动时间为第二个参数。后边的参数依次为等待震动和震动的时间
        // 第二个参数是重复次数，-1为不重复，0为一直震动，非-1则从pattern的指定下标开始重复
    }

    @Override
    protected void initView() {

        redAndblueLinearLayout = (LinearLayout) findViewById(R.id.redAndblueLinearLayout);
        otherCohseLinearLayout = (LinearLayout) findViewById(R.id.otherCohseLinearLayout);
        blueTextView = (TextView) findViewById(R.id.blueTextView);
        redTextView = (TextView) findViewById(R.id.redTextView);
        choseTopView = (TopView) findViewById(R.id.choseTopView);
        redSelectBallsView = (SelectBallsView) findViewById(R.id.redSelectBallsView);
        blueSelectBallsView = (SelectBallsView) findViewById(R.id.blueSelectBallsView);
        choseTopView.getRightImageView().setVisibility(View.VISIBLE);

        wanweiLinearLayout = (LinearLayout) findViewById(R.id.wanweiLinearLayout);
        wanweiSelectBallsView = (SelectBallsView) findViewById(R.id.wanweiSelectBallsView);
        qianweiLinearLayout = (LinearLayout) findViewById(R.id.qianweiLinearLayout);
        qianweiSelectBallsView = (SelectBallsView) findViewById(R.id.qianweiSelectBallsView);
        baiweiLinearLayout = (LinearLayout) findViewById(R.id.baiweiLinearLayout);
        baiweiSelectBallsView = (SelectBallsView) findViewById(R.id.baiweiSelectBallsView);
        shiweiLinearLayout = (LinearLayout) findViewById(R.id.shiweiLinearLayout);
        shiweiSelectBallsView = (SelectBallsView) findViewById(R.id.shiweiSelectBallsView);
        geweiLinearLayout = (LinearLayout) findViewById(R.id.geweiLinearLayout);
        geweiSelectBallsView = (SelectBallsView) findViewById(R.id.geweiSelectBallsView);

        SelectBallsViewList.add(redSelectBallsView);
        SelectBallsViewList.add(blueSelectBallsView);
        SelectBallsViewList.add(wanweiSelectBallsView);
        SelectBallsViewList.add(qianweiSelectBallsView);
        SelectBallsViewList.add(baiweiSelectBallsView);
        SelectBallsViewList.add(shiweiSelectBallsView);
        SelectBallsViewList.add(geweiSelectBallsView);


        choseTopView.getLeftImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVibrator != null) {
                    mVibrator.cancel();
                }
                finish();
            }
        });

        for (final SelectBallsView selectBallsView : SelectBallsViewList) {
            selectBallsView.setSelectBallPickListener(new SelectBallsView.SelectBallPickListener() {
                @Override
                public void pickOnClickListener() {
                    choseNumber.append(selectBallsView.getSelectBallsString());
                }
            });
        }
        choseTopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopRightMenu = new TopRightMenu(CpDoubleChoseNumberActivity.this);
                List<MenuItem> menuItems = new ArrayList<>();
                menuItems.add(new MenuItem("显示遗漏"));
                menuItems.add(new MenuItem("隐藏遗漏"));
                menuItems.add(new MenuItem("历史开奖"));
                menuItems.add(new MenuItem("走势图"));
//                menuItems.add(new MenuItem("玩法"));
                mTopRightMenu
                        .setHeight(600)     //默认高度480
                        .setWidth(420)      //默认宽度wrap_content
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                        .addMenuList(menuItems)
                        .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position) {
                                switch (position) {
                                    case 0:
                                        for (final SelectBallsView selectBallsView : SelectBallsViewList) {
                                            selectBallsView.setShowMissValue(true);
                                        }
                                        break;
                                    case 1:
                                        for (final SelectBallsView selectBallsView : SelectBallsViewList) {
                                            selectBallsView.setShowMissValue(false);
                                        }
                                        break;
                                    case 2:
                                        Intent intent1 = new Intent(CpDoubleChoseNumberActivity.this, CPLishiActivity.class);
                                        intent1.putExtra("type", cid);
                                        startActivity(intent1);
                                        break;
                                    case 3:
                                        Intent intent = new Intent(CpDoubleChoseNumberActivity.this, LottoTrendActivity.class);
                                        intent.putExtra("type", cid);
                                        startActivity(intent);
                                        break;
//                                    case 4:
//                                        Intent helpintent = new Intent(CpDoubleChoseNumberActivity.this, LottoTrendActivity.class);
//                                        startActivity(helpintent);
//                                        break;
                                }

                            }
                        })
                        .showAsDropDown(choseTopView.getRightImageView(), -225, 0);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_cpdoublechosenumber;
    }


    /**
     * 显示/隐藏遗漏
     *
     * @param
     */
    public void showMiss() {

        for (final SelectBallsView selectBallsView : SelectBallsViewList) {
            selectBallsView.setShowMissValue(true);
        }
//
//        redSelectBallsView.setShowMissValue(!redSelectBallsView.getShowMissValue());
//        blueSelectBallsView.setShowMissValue(!blueSelectBallsView.getShowMissValue());
    }

    public void confirm(View view) {
//        if (choseNumber.length() == 0) {
//            toast("请先选择");
//            return;
//        }
        cm.setText(choseNumber.toString() + "");
        Toast.makeText(this, "模拟选择的号码已经复制到剪切板了", Toast.LENGTH_SHORT).show();
    }

    public void clenNumber(View view) {
        for (final SelectBallsView selectBallsView : SelectBallsViewList) {
            selectBallsView.initCleanBalls();
            selectBallsView.setShowMissValue(true);
        }
//        redSelectBallsView.initCleanBalls();
//        blueSelectBallsView.initCleanBalls();
//        redSelectBallsView.setShowMissValue(true);
//        blueSelectBallsView.setShowMissValue(true);
//        redTextView.setText("红球：暂没有选择");
//        blueTextView.setText("蓝球：暂没有选择");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVibrator != null) {
            mVibrator.cancel();
        }
    }
}
