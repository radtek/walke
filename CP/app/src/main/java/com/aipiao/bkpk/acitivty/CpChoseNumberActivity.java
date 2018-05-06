package com.aipiao.bkpk.acitivty;

import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.aipiao.bkpk.R;
import com.aipiao.bkpk.base.BaseActivity;
import com.aipiao.bkpk.views.TopView;
import com.aipiao.bkpk.views.choseredblue.Calculator;
import com.aipiao.bkpk.views.choseredblue.RandomMadeBall;
import com.aipiao.bkpk.views.choseredblue.ShakeListener;
import com.aipiao.bkpk.views.choseredblue.adapter.BlueBallAdapter;
import com.aipiao.bkpk.views.choseredblue.adapter.RedBallAdapter;

import java.util.ArrayList;

/**
 * Created by caihui on 2018/3/23.
 */

public class CpChoseNumberActivity extends BaseActivity implements View.OnClickListener {

    private TopView choseTopView;
    private GridView gridViRedBall, gridViBlueBall;
    private RedBallAdapter adpRedBall;
    private BlueBallAdapter adpBlueBall;
    private ArrayList<String> arrRandomRed, arrRandomBlue;

    private Button btnRandomSelect, btnClearNumber, btnPurchaseBall;
    private TextView txtResult,txtShowRedBall,txtShowBlueBall;

    private int selectHongNumber = 6, selectLanNumber = 1;

    private ShakeListener mShakeListener = null;
    private Vibrator mVibrator;

    @Override
    protected void initData() {
// 对红球GridView进行监听，获得红球选中的数。
        gridViRedBall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在每次获取点击的item时将对应的checkbox状态改变，同时修改map的值。
                RedBallAdapter.redGridViewHolder vHolder = (RedBallAdapter.redGridViewHolder) view.getTag();
                vHolder.chkRed.toggle();
                adpRedBall.getSelected().put(position, vHolder.chkRed.isChecked());
                int tempRed = 0;
                String hq = "";
                for (int i = 0; i < gridViRedBall.getCount(); i++) {
                    RedBallAdapter.redGridViewHolder vHolder_red = (RedBallAdapter.redGridViewHolder) gridViRedBall.getChildAt(i).getTag();
                    if (adpRedBall.hisSelected.get(i)) {
                        ++tempRed;
                        selectHongNumber = tempRed;
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(CpChoseNumberActivity.this,android.R.color.white));
                    } else {
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(CpChoseNumberActivity.this,android.R.color.black));
                    }
                    if (adpRedBall.getSelected().get(i)){
                        hq = hq + (i + 1) + "  ";
                    }
                }
                txtShowRedBall.setText(hq);

                long zhushu = Calculator.calculateBetNum(selectHongNumber, selectLanNumber);
                txtResult.setText("共" + String.valueOf(zhushu) + "注" + (zhushu * 2) + "元");
            }
        });

        // 对篮球GridView进行监听，获取选球状态。
        gridViBlueBall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BlueBallAdapter.LanGridViewHolder vHollder = (BlueBallAdapter.LanGridViewHolder) view.getTag();
                vHollder.chkBlue.toggle();
                adpBlueBall.getSelected().put(position, vHollder.chkBlue.isChecked());
                int tempBlue = 0;
                String lq = "";
                for (int i = 0; i < gridViBlueBall.getCount(); i++) {
                    BlueBallAdapter.LanGridViewHolder vHolder_Blue = (BlueBallAdapter.LanGridViewHolder) gridViBlueBall.getChildAt(i).getTag();
                    if (BlueBallAdapter.lisSelected.get(i)) {
                        ++tempBlue;
                        selectLanNumber = tempBlue;
                        vHolder_Blue.chkBlue.setTextColor(getResources().getColor(android.R.color.white));
                    } else {
                        vHolder_Blue.chkBlue.setTextColor(getResources().getColor(android.R.color.black));
                    }

                    if (adpBlueBall.getSelected().get(i)){
                        lq = lq + (i + 1) + "  ";
                    }
                }
                txtShowBlueBall.setText(lq);
                long zhushu = Calculator.calculateBetNum(selectHongNumber, selectLanNumber);
                txtResult.setText("共" + String.valueOf(zhushu) + "注" + (zhushu * 2) + "元");
            }
        });

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

    // 定义震动
    public void startVibrato() {
        mVibrator.vibrate(new long[] { 200, 400, 0, 400 }, -1);
        // 第一个｛｝里面是节奏数组，第一个参数为等待指定时间后开始震动，震动时间为第二个参数。后边的参数依次为等待震动和震动的时间
        // 第二个参数是重复次数，-1为不重复，0为一直震动，非-1则从pattern的指定下标开始重复
    }

    private void randomSelect() {
        getBallNumber();
        adpRedBall.updateData(arrRandomRed);
        adpBlueBall.updateData(arrRandomBlue);
        selectHongNumber = 6;
        selectLanNumber = 1;
        String hq = "";
        for (int i = 0; i < arrRandomRed.size(); i++) {
            hq = hq + (Integer.parseInt(arrRandomRed.get(i)) + 1) + "  ";
        }
        txtShowRedBall.setText(hq);

        String lq = "";
        for (int i = 0; i < arrRandomBlue.size(); i++) {
            lq = lq + (Integer.parseInt(arrRandomBlue.get(i)) + 1) + "  ";
        }
        txtShowBlueBall.setText(lq);

        txtResult.setText("共1注2元");
    }


    @Override
    protected void initView() {
        // 获得振动器服务
        mVibrator = (Vibrator) getApplication().getSystemService(VIBRATOR_SERVICE);
        // 实例化加速度传感器检测类
        mShakeListener = new ShakeListener(this);

        gridViRedBall = (GridView) findViewById(R.id.gridview_hongqiu);
        gridViBlueBall = (GridView) findViewById(R.id.gridview_lanqiu);

        txtShowRedBall = (TextView) findViewById(R.id.txt_showhongqiu);
        txtShowBlueBall = (TextView) findViewById(R.id.txt_showlanqiu);
        txtResult = (TextView) findViewById(R.id.txt_result);

        btnRandomSelect = (Button) findViewById(R.id.btn_random_select);
        btnClearNumber = (Button) findViewById(R.id.btn_clear_number);
        btnPurchaseBall = (Button) findViewById(R.id.btn_purchase_ball);

        btnRandomSelect.setOnClickListener(this);
        btnClearNumber.setOnClickListener(this);
        btnPurchaseBall.setOnClickListener(this);

        adpRedBall = new RedBallAdapter(this, arrRandomRed);
        adpBlueBall = new BlueBallAdapter(this, arrRandomBlue);

        gridViRedBall.setAdapter(adpRedBall);
        gridViBlueBall.setAdapter(adpBlueBall);
        choseTopView= (TopView) findViewById(R.id.choseTopView);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_cpchosenumber;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 清空选号按钮
            case R.id.btn_clear_number:
                adpRedBall.clearData();
                adpBlueBall.clearData();
                txtShowRedBall.setText("");
                txtShowBlueBall.setText("");
                selectHongNumber = 0;
                selectLanNumber = 0;
                txtResult.setText("共0注0元");
                break;
            // 随机选号按钮
            case R.id.btn_random_select:
                randomSelect();
                txtResult.setText("共1注2元");
                break;
            // 投注确定按钮
            case R.id.btn_purchase_ball:
                break;
            default:
                break;
        }
    }

    private void getBallNumber() {
        arrRandomRed = RandomMadeBall.getRedBall();
        arrRandomBlue = RandomMadeBall.getBlueBall();
    }
}
