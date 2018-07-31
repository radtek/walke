package com.grandsea.ticketvendingapplication.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.other.BeepManager;
import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.adapter.GsonAdapter;
import com.grandsea.ticketvendingapplication.base.BaseActivity;
import com.grandsea.ticketvendingapplication.constant.IntentKey;
import com.grandsea.ticketvendingapplication.constant.UrlConstant;
import com.grandsea.ticketvendingapplication.model.bean.LockTicketStatus;
import com.grandsea.ticketvendingapplication.model.bean.OrderInfo;
import com.grandsea.ticketvendingapplication.model.bean.Passenger;
import com.grandsea.ticketvendingapplication.model.common.GsonObject;
import com.grandsea.ticketvendingapplication.net.RequestUtil;
import com.grandsea.ticketvendingapplication.util.BasicUtil;
import com.grandsea.ticketvendingapplication.util.DateUtil;
import com.grandsea.ticketvendingapplication.util.DialogManager;
import com.grandsea.ticketvendingapplication.util.LogUtil;
import com.grandsea.ticketvendingapplication.util.RandomValue;
import com.grandsea.ticketvendingapplication.view.CustomButton;
import com.grandsea.ticketvendingapplication.view.DateView;
import com.grandsea.ticketvendingapplication.view.ImageTextView;
import com.grandsea.ticketvendingapplication.view.SelectTicketViewOld;
import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.idcard.IdCard;
import com.telpo.tps550.api.idcard.IdentityInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * 乘客联系人
 */
public class SelectTicketsActivity extends BaseActivity implements GsonAdapter, View.OnClickListener, SelectTicketViewOld.SelectTicketListenerOld {

    private static final int TICKET_BUY_MAX = 6;
    private OrderInfo mOrderInfo;

    //读取身份证所需字段

    private ImageTextView itvStationStart, itvStationEnd, itvStationWay, itvStationTime;
    private DateView dateView;
    private CustomButton btBack, btSure;
    private SelectTicketViewOld stvMan, stvChild, stvStudent, stvKid;
    private TextView tvTotalTicket, tvTotalPrice;

    /**
     * 能购买的最大票数
     */
    private int buyTicketMax = TICKET_BUY_MAX;
    private int priceMan;
    private int priceHalf;
    private int priceStudent;
    private int totalPrice;
    private int totalTickets;
    private int numberStudent;
    private int numberKid;
    private int numberMan;
    private int numberChild;
    private Dialog mDialogGetIdInfo;
    private String mOrderId;
    private String mTicketDate;

    /**
     * 剩余票数(最大票数)
     */
    //private int ticketSurplus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tickets);
        mOrderId = "";
        initView();
        initData();
    }

    private void initView() {

        itvStationStart = (ImageTextView) findViewById(R.id.ast_stationStart);
        itvStationEnd = (ImageTextView) findViewById(R.id.ast_stationEnd);
        itvStationWay = (ImageTextView) findViewById(R.id.ast_stationWay);
        itvStationTime = (ImageTextView) findViewById(R.id.ast_stationTime);
        dateView = (DateView) findViewById(R.id.ast_dateView);
        dateView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        stvMan = (SelectTicketViewOld) findViewById(R.id.ast_stvMan);
        stvChild = (SelectTicketViewOld) findViewById(R.id.ast_stvChild);
        stvStudent = (SelectTicketViewOld) findViewById(R.id.ast_stvStudent);
        stvKid = (SelectTicketViewOld) findViewById(R.id.ast_stvKid);
        stvMan.setSelectTicketListenerOld(this);
        stvChild.setSelectTicketListenerOld(this);
        stvStudent.setSelectTicketListenerOld(this);
        stvKid.setSelectTicketListenerOld(this);

        btBack = (CustomButton) findViewById(R.id.ast_btBack);
        btSure = (CustomButton) findViewById(R.id.ast_btSure);
        btBack.setOnClickListener(this);
        btSure.setOnClickListener(this);

        tvTotalTicket = (TextView) findViewById(R.id.ast_tvTotalTicket);
        tvTotalPrice = (TextView) findViewById(R.id.ast_tvTotalPrice);

        tvSpannable(tvTotalTicket, "当前共购票(", "0", ")张", Color.parseColor("#E55C32"));
        tvSpannable(tvTotalPrice, "总价格为(", "0", ")", Color.parseColor("#B4501E"));
    }

    private void initData() {
        //获取数据
        Intent intent = getIntent();
        if (null == intent)
            return;
        mTicketDate = DateUtil.formatYYYYMMDay(new Date());
        logI("1111");
        mTicketDate = intent.getStringExtra(IntentKey.TICKET_DATE);
        dateView.setDateText(mTicketDate + "");
        mOrderInfo = (OrderInfo) intent.getSerializableExtra(IntentKey.EXTRA_SHIFT_TO_PASSENGER_OBJ);
        setTicketView(mOrderInfo);
       /* if (mOrderInfo == null) {
            mOrderInfo = new OrderInfo();
        }*/
    }

    private void setTicketView(OrderInfo orderInfo) {
        if (null == orderInfo) {
            stvMan.setTicketMax(0);
            stvChild.setTicketMax(0);
            stvStudent.setTicketMax(0);
            stvKid.setTicketMax(0);
        } else {
            int ticketSurplus = orderInfo.getTicketNum();
            if (ticketSurplus > 0 && ticketSurplus < TICKET_BUY_MAX)
                buyTicketMax = ticketSurplus;
            String takeOffStation = orderInfo.getTakeOffStation();
            String getOnStation = orderInfo.getGetOnStation();
            itvStationStart.getTvLeft().setText("" + getOnStation);
            itvStationEnd.getTvLeft().setText("" + takeOffStation);
            itvStationWay.getTvLeft().setText("线路：" + orderInfo.getShortName());
            itvStationTime.getTvLeft().setText("乘车时间：" + orderInfo.getGetOnTime());//上车时间
            priceMan = orderInfo.getPrice();
            stvMan.getTvTicketsInfo().setText("成人票(" + priceMan + "元/张)");
            priceHalf = orderInfo.getHalfPrice();
            stvChild.getTvTicketsInfo().setText("儿童票(" + priceHalf + "元/张)");
            priceStudent = orderInfo.getStudentPrice();
            stvStudent.getTvTicketsInfo().setText("学生票(" + priceStudent + "元/张)");
            stvMan.setTicketMax(buyTicketMax);
            stvChild.setTicketMax(buyTicketMax);
            stvStudent.setTicketMax(buyTicketMax);
            stvKid.setTicketMax(stvMan.getNumber());
            if (orderInfo.getHalfPrice() < 1) {//学生票为0时不可买
                //stvChild.setEnabled(false);
                stvChild.setSelectEnable(false);
                //stvChild.setBackgroundResource(R.color._grey_605E5C);
            }

            //dateView.setDateText(mOrderInfo.getTicketDate()+"");
        }
    }

    /**
     * @param day 票数加减点击事件
     */
    @Override
    public void changed(int day) {
        numberMan = stvMan.getNumber();
        numberChild = stvChild.getNumber();
        numberStudent = stvStudent.getNumber();
        numberKid = stvKid.getNumber();

        stvMan.setTicketMax(buyTicketMax - numberChild - numberStudent);
        stvChild.setTicketMax(buyTicketMax - numberMan - numberStudent);
        stvStudent.setTicketMax(buyTicketMax - numberChild - numberMan);
        stvKid.setTicketMax(stvMan.getNumber());

        totalTickets = numberMan + numberChild + numberStudent + numberKid;
        tvSpannable(tvTotalTicket, "当前共购票(", totalTickets + "", ")张", Color.parseColor("#E55C32"));
        totalPrice = numberMan * priceMan + numberChild * priceHalf + numberStudent * priceStudent + numberKid * 0;
        tvSpannable(tvTotalPrice, "总价格为(",
                totalPrice + "",
                ")", Color.parseColor("#B4501E"));
    }

    @Override
    public void onClick(View v) {
        if (v == btSure) {
            if (totalTickets < 1) {
                toast("请选购车票");
                return;
            }
            boolean flag = false;
            if (flag) {//不需要身份证

            } else {//需要身份证
                readIdCard();

            }
        } else if (v == btBack) {
            finish();
        }
    }

    private void readIdCard() {
        acount = 0;//重置扫描身份证次数
        mDialogCancel = false;
        getIDInfo();
        mDialogGetIdInfo = DialogManager.dialogGetIdInfo(this, new DialogManager.DialogTwoButtonClickListener() {
            @Override
            public void leftOnClick(WindowManager.LayoutParams lp, Dialog dialog) {//cancel
                mDialogCancel = true;
//                toast("loopHandler.removeCallbacks(loopRunnable)");
            }

            @Override
            public void rightOnClick(WindowManager.LayoutParams lp, Dialog dialog) {
//                        startActivity(new Intent(SelectTicketsActivity.this, InputIdActivity.class));
                //跳转手动输入界面
                Intent intent = new Intent(SelectTicketsActivity.this, InputIdActivity.class);
                mOrderInfo.setTicketNum(numberMan);
                mOrderInfo.setcTicketNum(numberChild);
                mOrderInfo.setsTicketNum(numberStudent);
                mOrderInfo.setkTicketNum(numberKid);
                //mOrderInfo.setTicketNum(totalTickets);
                mOrderInfo.setTotalPrice(totalPrice);
                intent.putExtra(IntentKey.ORDER_INFO, mOrderInfo);
                intent.putExtra(IntentKey.TOTAL_TICKETS, totalTickets);
                intent.putExtra(IntentKey.TICKET_DATE, mTicketDate);//++
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * //提交订单
     *
     * @param idInfo
     * @return
     */
    private void commitOrder(IdentityInfo idInfo) {
        String no = idInfo.getNo().trim();
        String name = idInfo.getName().trim();
        mOrderInfo.setName(name);
        mOrderInfo.setPhone(RandomValue.getTel());//随机生成手机号
        mOrderInfo.setIdCard(no);
        mOrderInfo.setTicketNum(totalTickets);
        mOrderInfo.setTotalPrice(totalPrice);

        List<Passenger> passengers = initPassengers(no, name);

        mOrderInfo.setPassengers(gson.toJson(passengers));
        final Map<String, Object> paramMap = new HashMap();
        paramMap.put("orderInfo", mOrderInfo);
        String requestContent = new Gson().toJson(BasicUtil.buildPostData(this,paramMap));//58ff4eddda0e83185baa7eb7cc4fc5d1
        logI(requestContent);
        //intentToPay(mOrderId);
        OkHttpUtils
                .postString()
                .url(UrlConstant.ORDER_TICKET)
//                .url("http://192.168.2.23:9093/sfc-order/order_ticket")//从康
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(requestContent)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        Toast.makeText(SelectTicketsActivity.this, "网络异常，请重新刷新", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String s, final int i) {//status 0：失败，1：成功
                        //{"result":{"func":"order_ticket","status":1},"status":-1}
                        //{"result":{"func":"order_ticket","status":1},"payParams":{"appId":"wx2f6261204158f860","nonceStr":"2119b8d43eafcf353e07d7cb5554170b","package":"prepay_id\u003dwx2017101120274517434d56f00642106335","prepay_id":"wx2017101120274517434d56f00642106335","sign":"7D40C3E24C38FB0535E4629456DD91F9","signType":"MD5","timeStamp":"1507724824","orderId":"090dd4011caec9d3e79ad8ba572d725c"},"status":1}
                        GsonObject dataGson = new GsonObject(gson.fromJson(s, JsonObject.class)); //对数据转换成json后进行对象封装
                        int status = dataGson.getInt("status", -1);
                        if (status == LockTicketStatus.SUCCESS) {
                            GsonObject payParams = dataGson.getGsonObject("payParams");
                            if (null != payParams) {
                                mOrderId = payParams.getString("orderId");
                                if (!TextUtils.isEmpty(mOrderId))
                                    intentToPay(mOrderId);
                                else
                                    toast("异常");
                            }
                        } else {
                            String payParams = dataGson.getString("payParams");
                            if (!TextUtils.isEmpty(payParams))
                                toast(payParams);
                            else
                                toast("异常");
                        }

                    }
                });
    }

    private List<Passenger> initPassengers(String no, String name) {
        List<Passenger> passengers = new ArrayList<>();
        if (numberMan > 0) {
            for (int i = 0; i < numberMan; i++) {
                Passenger p = new Passenger();
                p.setIdCard(no);
                p.setName(name);
                p.setType(1);
                passengers.add(p);
            }
        }
        if (numberChild > 0) {
            for (int i = 0; i < numberChild; i++) {
                Passenger p = new Passenger();
                p.setIdCard(no);
                p.setName(name);
                p.setType(2);
                passengers.add(p);
            }
        }
        if (numberStudent > 0) {
            for (int i = 0; i < numberStudent; i++) {
                Passenger p = new Passenger();
                p.setIdCard(no);
                p.setName(name);
                p.setType(3);
                passengers.add(p);
            }
        }
        if (numberKid > 0) {
            for (int i = 0; i < numberKid; i++) {
                Passenger p = new Passenger();
                p.setIdCard(no);
                p.setName(name);
                p.setType(4);
                passengers.add(p);
            }
        }
        return passengers;
    }

    /**
     * 跳转支付页面：支付
     *
     * @param orderId
     */
    private void intentToPay(String orderId) {
        if (mDialogGetIdInfo != null && mDialogGetIdInfo.isShowing()) {
            mDialogGetIdInfo.dismiss();
            // 设置背景颜色变回全亮
            setWindowAlphaBackToOne();
        }

        //跳转支付页面：支付
        Intent intent = new Intent(SelectTicketsActivity.this, PayActivity.class);
        //int status = commitOrder(mOrderInfo,passengers);
        mOrderInfo.setTicketNum(numberMan);
        mOrderInfo.setcTicketNum(numberChild);
        mOrderInfo.setsTicketNum(numberStudent);
        mOrderInfo.setkTicketNum(numberKid);
        intent.putExtra(IntentKey.ORDER_INFO, mOrderInfo);
        intent.putExtra(IntentKey.ORDER_ID, orderId);
        intent.putExtra(IntentKey.TICKET_DATE, dateView.getTvDate().getText().toString().trim());
        startActivity(intent);
    }

    IdentityInfo idInfo;
    BeepManager beepManager;
    Bitmap bitmap;
    byte[] image;
    byte[] fringerprint;
    String fringerprintData;

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mOrderId)) {
            RequestUtil.cancelOrderById(this, mOrderId);
        }
        // 设置背景颜色变回全亮
        setWindowAlphaBackToOne();

        beepManager = new BeepManager(this, R.raw.beep);
        initReadDevice();
    }

    private void initReadDevice() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    IdCard.open(SelectTicketsActivity.this);
                } catch (TelpoException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //无法连接读卡器
                            toast("无法连接读卡器");
                            DialogManager.dialogTwoButton(
                                    SelectTicketsActivity.this,
                                    "无法连接读卡器，退出或重试",
                                    "退出", "重试",
                                    new DialogManager.DialogTwoButtonClickListener() {
                                        @Override
                                        public void leftOnClick(WindowManager.LayoutParams lp, Dialog dialog) {
                                            finish();
                                        }

                                        @Override
                                        public void rightOnClick(WindowManager.LayoutParams lp, Dialog dialog) {
                                            initReadDevice();
                                        }
                                    });

                        }
                    });
                }
            }
        }).start();
    }

    private void setWindowAlphaBackToOne() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
    }

    @Override
    protected void onPause() {
        super.onPause();
        beepManager.close();
        beepManager = null;
        IdCard.close();
        if (mDialogGetIdInfo != null && mDialogGetIdInfo.isShowing()) {
            mDialogGetIdInfo.dismiss();
            // 设置背景颜色变回全亮
            setWindowAlphaBackToOne();
        }
    }

    /**
     * 获取id信息
     */
    public void getIDInfo() {
        //new GetIDInfoTask().execute();
        idInfo = null;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    idInfo = IdCard.checkIdCard(1600);//luyq modify
                    if (idInfo != null) {
                        image = IdCard.getIdCardImage();
                        bitmap = IdCard.decodeIdCardImage(image);
                        // luyq add 增加指纹信息
                        fringerprint = IdCard.getFringerPrint();
                        fringerprintData = getFingerInfo(fringerprint);
                    }
                } catch (TelpoException e) {
                    e.printStackTrace();
                }
                if (mHandler != null) {
                    Message message = mHandler.obtainMessage();
                    message.obj = idInfo;
                    mHandler.sendMessage(message);
                }
            }
        }).start();


    }

   /* private Handler loopHandler=new Handler();
    private Runnable loopRunnable=new Runnable() {
        @Override
        public void run() {
            getIDInfo();
        }
    };*/

    private int acount;
    private boolean mDialogCancel;//弹窗点击了取消
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            if (null == beepManager) {
                return;//
            }
            if (mDialogCancel) {
                return;
            }
            beepManager.playBeepSoundAndVibrate();
            IdentityInfo idInfo = (IdentityInfo) msg.obj;
            if (null != idInfo) {
                commitOrder(idInfo);
                LogUtil.d("111", "idInfo: " + idInfo.toString());
            } else {
                acount++;
                if (acount >= 10) {
                    //toast("获取失败，请尝试手工输入");
                    if (mDialogGetIdInfo !=null&& mDialogGetIdInfo.isShowing()){
                        mDialogGetIdInfo.dismiss();
                    }
                    DialogManager.createDialogTwoButton(SelectTicketsActivity.this,
                            "读取提示",
                            "身份证读取超时",
                            "确认继续读取身份证？",
                            new DialogManager.DialogTwoButtonClickListener() {
                                @Override
                                public void leftOnClick(WindowManager.LayoutParams lp, Dialog dialog) {

                                }

                                @Override
                                public void rightOnClick(WindowManager.LayoutParams lp, Dialog dialog) {
                                    dialog.dismiss();
                                    readIdCard();
                                }
                            });
                    return;
                }
//                loopHandler.postDelayed(loopRunnable,1000);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getIDInfo();
                    }
                }, 1000);

            }
        }
    };


    /***********************************************************************************************************/

    private class GetIDInfoTask extends AsyncTask<Void, Integer, TelpoException> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {//子线程之前
            super.onPreExecute();
            //getData.setEnabled(false);
            btSure.setEnabled(false);
            dialog = new ProgressDialog(SelectTicketsActivity.this);
            dialog.setTitle(getString(R.string.idcard_czz));
            dialog.setMessage(getString(R.string.idcard_ljdkq));
            dialog.setCancelable(false);
            dialog.show();
            idInfo = null;
            bitmap = null;
        }

        @Override
        protected TelpoException doInBackground(Void... arg0) {//子线程工作
            TelpoException result = null;
            try {
                publishProgress(1);
//				idInfo = IdCard.checkIdCard(4000);
                idInfo = IdCard.checkIdCard(1600);//luyq modify
                if (idInfo != null) {
                    image = IdCard.getIdCardImage();
                    bitmap = IdCard.decodeIdCardImage(image);
                    // luyq add 增加指纹信息
                    fringerprint = IdCard.getFringerPrint();
                    fringerprintData = getFingerInfo(fringerprint);
                }
            } catch (TelpoException e) {
                e.printStackTrace();
                result = e;
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values[0] == 1) {
                dialog.setMessage(getString(R.string.idcard_hqsfzxx));
            }
        }

        @Override
        protected void onPostExecute(TelpoException result) { //子线程之后
            super.onPostExecute(result);
            dialog.dismiss();
            //getData.setEnabled(true);
            btSure.setEnabled(true);
            if (result == null) {
                beepManager.playBeepSoundAndVibrate();
                String idInfoStr = getString(R.string.idcard_xm)
                        + idInfo.getName() + "\n\n"
                        + getString(R.string.idcard_xb) + idInfo.getSex()
                        + "\n\n" + getString(R.string.idcard_mz)
                        + idInfo.getNation() + "\n\n"
                        + getString(R.string.idcard_csrq) + idInfo.getBorn()
                        + "\n\n" + getString(R.string.idcard_dz)
                        + idInfo.getAddress() + "\n\n"
                        + getString(R.string.idcard_sfhm) + idInfo.getNo()
                        + "\n\n" + getString(R.string.idcard_qzjg)
                        + idInfo.getApartment() + "\n\n"
                        + getString(R.string.idcard_yxqx) + idInfo.getPeriod()
                        + "\n\n" + getString(R.string.idcard_zwxx)
                        + fringerprintData;
//                toast( "idInfoStr: " + idInfoStr);
            } else {
                toast("读取失败或超时");
            }
        }
    }

    private String getFingerInfo(byte[] fpData) {
        // 解释第1枚指纹，总长度512字节，部分数据格式：
        // 第1字节为特征标识'C'
        // 第5字节为注册结果代码，0x01-注册成功，0x02--注册失败, 0x03--未注册, 0x09--未知
        // 第6字节为指位代码
        // 第7字节为指纹质量值，0x00表示未知，1~100表示质量值
        // 第512字节 crc8值
        String fingerInfo = "";
        if (fpData != null && fpData.length == 1024 && fpData[0] == 'C') {
            fingerInfo = fingerInfo + GetFingerName(fpData[5]);
            if (fpData[4] == 0x01)
                fingerInfo = fingerInfo + " " + getString(R.string.idcard_zwzl) + String.valueOf(fpData[6]);
            else
                fingerInfo = fingerInfo + GetFingerStatus(fpData[4]);
            fingerInfo = fingerInfo + "  ";
            if (fpData[512] == 'C') {
                fingerInfo = fingerInfo + GetFingerName(fpData[512 + 5]);
                if (fpData[512 + 4] == 0x01)
                    fingerInfo = fingerInfo + " " + getString(R.string.idcard_zwzl) + String.valueOf(fpData[512 + 6]);
                else
                    fingerInfo = fingerInfo + GetFingerStatus(fpData[512 + 4]);
            }
        } else {
            fingerInfo = getString(R.string.idcard_wdqhbhzw);
        }
        return fingerInfo;
    }

    private String GetFingerName(int fingerPos) {
        String fingerName = "";
        switch (fingerPos) {
            case 11://	右手拇指
                fingerName = getString(R.string.idcard_ysmz);
                break;
            case 12://	右手食指
                fingerName = getString(R.string.idcard_yssz);
                break;
            case 13://	右手中指
                fingerName = getString(R.string.idcard_yszz);
                break;
            case 14://	右手环指
                fingerName = getString(R.string.idcard_yshz);
                break;
            case 15://	右手小指
                fingerName = getString(R.string.idcard_ysxz);
                break;
            case 16://	左手拇指
                fingerName = getString(R.string.idcard_zsmz);
                break;
            case 17://	左手食指
                fingerName = getString(R.string.idcard_zssz);
                break;
            case 18://	左手中指
                fingerName = getString(R.string.idcard_zszz);
                break;
            case 19://	左手环指
                fingerName = getString(R.string.idcard_zshz);
                break;
            case 20://			左手小指
                fingerName = getString(R.string.idcard_zsxz);
                break;
            case 97:
//			右手不确定指位
                fingerName = getString(R.string.idcard_ysbqdzw);
                break;
            case 98:
//			左手不确定指位
                fingerName = getString(R.string.idcard_zsbqdzw);
                break;
            case 99:
//			其他不确定指位
                fingerName = getString(R.string.idcard_qtbqdzw);
                break;
            default:
//			指位未知
                fingerName = getString(R.string.idcard_zwwz);
                break;
        }
        return fingerName;
    }

    // 第5字节为注册结果代码，0x01-注册成功，0x02--注册失败, 0x03--未注册, 0x09--未知
    private String GetFingerStatus(int fingerStatus) {
        String fingerStatusName = "";
        switch (fingerStatus) {
            case 0x01:
//			注册成功
                fingerStatusName = getString(R.string.idcard_zccg);
                break;
            case 0x02:
//			注册失败
                fingerStatusName = getString(R.string.idcard_zcsb);
                break;
            case 0x03:
//			未注册
                fingerStatusName = getString(R.string.idcard_wzc);
                break;
            case 0x09:
//			注册状态未知
                fingerStatusName = getString(R.string.idcard_zcztwz);
                break;
            default:
//			注册状态未知
                fingerStatusName = getString(R.string.idcard_zcztwz);
                break;
        }
        return fingerStatusName;
    }

}
