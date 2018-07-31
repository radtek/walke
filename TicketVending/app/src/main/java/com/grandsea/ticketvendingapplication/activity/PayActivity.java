package com.grandsea.ticketvendingapplication.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.other.BeepManager;
import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.base.BaseActivity;
import com.grandsea.ticketvendingapplication.constant.IntentKey;
import com.grandsea.ticketvendingapplication.constant.UrlConstant;
import com.grandsea.ticketvendingapplication.model.bean.LockTicketStatus;
import com.grandsea.ticketvendingapplication.model.bean.OrderInfo;
import com.grandsea.ticketvendingapplication.model.common.GsonObject;
import com.grandsea.ticketvendingapplication.net.RequestUtil;
import com.grandsea.ticketvendingapplication.util.BasicUtil;
import com.grandsea.ticketvendingapplication.util.DialogManager;
import com.grandsea.ticketvendingapplication.view.CustomButton;
import com.grandsea.ticketvendingapplication.view.DateView;
import com.grandsea.ticketvendingapplication.view.ImageTextView;
import com.grandsea.ticketvendingapplication.view.OrderTicketView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;

import static com.grandsea.ticketvendingapplication.adapter.GsonAdapter.gson;

//支付
public class PayActivity extends BaseActivity implements View.OnClickListener {

    private OrderInfo orderInfo = new OrderInfo();

    private ImageTextView itvStationStart, itvStationEnd, itvStationWay, itvStationTime;
    private DateView dateView;
    private CustomButton btBack;//btSure
    private OrderTicketView otvMan, otvChild, otvStudent, otvKid;
    private TextView tvTotalTicket, tvTotalPrice;
    private ImageView ivAliPay;
    private ImageView ivWechatPay;
    private String mOrderId;
    private String mTicketDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initView();
        initData();
        mBeepManager = new BeepManager(this, R.raw.beep);

    }

    private void initView() {

        itvStationStart = (ImageTextView) findViewById(R.id.ap_stationStart);
        itvStationEnd = (ImageTextView) findViewById(R.id.ap_stationEnd);
        itvStationWay = (ImageTextView) findViewById(R.id.ap_stationWay);
        itvStationTime = (ImageTextView) findViewById(R.id.ap_stationTime);
        dateView = (DateView) findViewById(R.id.ap_dateView);
        dateView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        otvMan = (OrderTicketView) findViewById(R.id.ap_otvMan);
        otvChild = (OrderTicketView) findViewById(R.id.ap_otvChild);
        otvStudent = (OrderTicketView) findViewById(R.id.ap_otvStudent);
        otvKid = (OrderTicketView) findViewById(R.id.ap_otvKid);


        btBack = (CustomButton) findViewById(R.id.ap_btBack);
        btBack.setOnClickListener(this);
//        btSure.setOnClickListener(this);

        tvTotalTicket = (TextView) findViewById(R.id.ap_tvTotalTicket);
        tvTotalPrice = (TextView) findViewById(R.id.ap_tvTotalPrice);

        ivWechatPay = (ImageView) findViewById(R.id.ap_wechatPay);
        ivAliPay = (ImageView) findViewById(R.id.ap_aliPay);
        ivAliPay.setVisibility(View.GONE);
        ivAliPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //RequestUtil.cancelOrderById(PayActivity.this,mOrderId);

               /* DialogManager.dialogPayTips(PayActivity.this,
                        "支付宝支付",
                        "请把支付宝授权码放置于\n二维码读取窗口",
                        "如何获得支付宝授权码？\n手机支付宝App首页->付钱",
                        R.mipmap.icon_pay_ali,
                        new DialogManager.OneButtonClickListener() {
                            @Override
                            public void click(WindowManager.LayoutParams lp, Dialog dialog) {
                                if (checkPackage("com.telpo.tps550.api")) {
                                    Intent intent = new Intent();
                                    intent.setClassName("com.telpo.tps550.api", "com.telpo.tps550.api.barcode.Capture");
                                    try {
                                        startActivityForResult(intent, REQUEST_CODE);
                                    } catch (ActivityNotFoundException e) {
                                        toast("未安装API模块，无法进行二维码/身份证识别");
                                    }
                                } else {
                                    toast("未安装API模块，无法进行二维码/身份证识别");
                                }
                            }
                        });*/
            }
        });
        ivWechatPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogManager.dialogPayTips(PayActivity.this,
                        "微信面对面支付",
                        "请把微信授权码放置于\n二维码读取窗口",
                        "如何获得微信授权码？\n手机微信App->我的->钱包->收付款",
                        R.mipmap.icon_pay_wechat,
                        new DialogManager.OneButtonClickListener() {
                            @Override
                            public void click(WindowManager.LayoutParams lp, Dialog dialog) {
                                if (checkPackage("com.telpo.tps550.api")) {
                                    Intent intent = new Intent();
                                    intent.setClassName("com.telpo.tps550.api", "com.telpo.tps550.api.barcode.Capture");
                                    try {
                                        startActivityForResult(intent, REQUEST_CODE);
                                    } catch (ActivityNotFoundException e) {
                                        toast("未安装API模块，无法进行二维码/身份证识别");
                                    }
                                } else {
                                    toast("未安装API模块，无法进行二维码/身份证识别");
                                }
                            }
                        });
               /* if (checkPackage("com.telpo.tps550.api")) {
                    Intent intent = new Intent();
                    intent.setClassName("com.telpo.tps550.api", "com.telpo.tps550.api.barcode.Capture");
                    try {
                        startActivityForResult(intent, REQUEST_CODE);
                    } catch (ActivityNotFoundException e) {
                        toast("未安装API模块，无法进行二维码/身份证识别");
                    }
                } else {
                    toast("未安装API模块，无法进行二维码/身份证识别");
                }*/
            }
        });



    }

    private void initData() {
        //获取数据
        Intent intent = getIntent();
        if (null == intent)
            return;
        mTicketDate = intent.getStringExtra(IntentKey.TICKET_DATE);
        dateView.setDateText(mTicketDate + "");
        mOrderId = intent.getStringExtra(IntentKey.ORDER_ID);
        orderInfo = (OrderInfo) intent.getSerializableExtra(IntentKey.ORDER_INFO);
        setTicketView(orderInfo);

    }

    private void setTicketView(OrderInfo orderInfo) {
        if (null == orderInfo) {
            toast("请重新下单");
            finish();
            return;
        } else {
            itvStationStart.getTvLeft().setText("" + orderInfo.getTakeOffStation());
            itvStationEnd.getTvLeft().setText("" + orderInfo.getGetOnStation());
            itvStationWay.getTvLeft().setText("线路：" + orderInfo.getShortName());
            itvStationTime.getTvLeft().setText("乘车时间：" + orderInfo.getGetOnTime());//上车时间
            int priceMan = orderInfo.getPrice();
            otvMan.getTvText().setText("成人票(" + priceMan + ") X "+orderInfo.getTicketNum()+"(张)");
            int priceHalf = orderInfo.getHalfPrice();
            otvChild.getTvText().setText("儿童票(" + priceHalf + ") X "+orderInfo.getcTicketNum()+"(张)");
            int priceStudent = orderInfo.getStudentPrice();
            otvStudent.getTvText().setText("学生票(" + priceStudent + ") X "+orderInfo.getsTicketNum()+"(张)");
            otvKid.getTvText().setText("免票儿童(" + 0 + ") X "+orderInfo.getkTicketNum()+"(张)");
            int totalTickets = orderInfo.getTicketNum() + orderInfo.getcTicketNum() + orderInfo.getsTicketNum() + orderInfo.getkTicketNum();
            tvSpannable(tvTotalTicket, "当前共购票(", ""+totalTickets, ")张", Color.parseColor("#E55C32"));
            tvSpannable(tvTotalPrice, "总价格为(", ""+orderInfo.getTotalPrice(), ")", Color.parseColor("#B4501E"));

        }
    }

    private boolean checkPackage(String packageName) {
        PackageManager manager = this.getPackageManager();
        Intent intent = new Intent().setPackage(packageName);
        List<ResolveInfo> infos = manager.queryIntentActivities(intent, PackageManager.GET_INTENT_FILTERS);
        if (infos == null || infos.size() < 1) {
            return false;
        }
        return true;
    }

    public static final int REQUEST_CODE=0x124;
    private BeepManager mBeepManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x124) {
            if (resultCode == 0) {
                if (data != null) {
                    mBeepManager.playBeepSoundAndVibrate();
                    String qrcode = data.getStringExtra("qrCode");
                    //toast("Scan result:" + qrcode);// TODO: 2017/10/14 隐藏吐司
                    pay(qrcode);
                    return;
                }
            } else {
                toast("Scan Failed 扫描失败");
            }
        }

    }

    private void pay(String qrcode) {

        Map<String,Object> paramMap = new HashMap();
        paramMap.put("orderId",mOrderId);//
        int totalPrice = orderInfo.getTotalPrice();
//        paramMap.put("totalFee", totalPrice);//orderInfo.getTotalPrice()
        paramMap.put("totalFee", 0.01);//orderInfo.getTotalPrice() // TODO: 2017/10/23 改回上一行
        paramMap.put("code",qrcode);//
        String requestContent =new Gson().toJson(BasicUtil.buildPostData(this,paramMap)) ;
        int timeOut = OkHttpUtils.getInstance().getOkHttpClient().connectTimeoutMillis();
        logI(requestContent);
        OkHttpUtils
                .postString()
                .url(UrlConstant.PAY_WECHAT)
//                .url("http://192.168.2.23:9093/sfc-order/scan_code")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(requestContent)
                .build()
                .execute(new StringCallback(){
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        toast("网络异常，请重新刷新");
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(String str, int i) {
                        // {"result":{"func":"scan_code","status":1},"code":-2,"desc":"201 商户订单号重复"}
                        //{"result":{"func":"scan_code","status":1},"code":-2,"desc":"101 付款码已过期，请刷新再试"}
                        //{"result":{"func":"scan_code","status":1},"status":1,"desc":"支付成功"}
                        GsonObject dataGson = new GsonObject(gson.fromJson(str, JsonObject.class)); //对数据转换成json后进行对象封装
                        int status = dataGson.getInt("status", -1);//1：为成功
                        if (status == LockTicketStatus.SUCCESS) {
                            Intent intent = new Intent(PayActivity.this, PrintfActivity.class);
                            //intent.putExtra(IntentKey.ORDER_INFO, mOrderInfo);
                            intent.putExtra(IntentKey.ORDER_ID, mOrderId);
                            startActivity(intent);
                        } else if(status == LockTicketStatus.FAIL) {

                        }
                        String desc = dataGson.getString("desc");
                        toast(""+desc);
                    }
                });
    }

    /**
     * SpannableStringBuilder的使用,TextView部分字体变颜色
     */
    public void tvSpannable(TextView tv, String front, String tag, String after, int color) {
        String str = front + tag + after;
        int winStart = str.indexOf(tag);
        int winEnd = winStart + tag.length();
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new ForegroundColorSpan(color), winStart, winEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(style);
    }

    @Override
    public void onClick(View v) {
       if (v == btBack) {
          back();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }
    private void back() {
        RequestUtil.cancelOrderById(this,mOrderId);
        finish();
    }
}
