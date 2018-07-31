package com.grandsea.ticketvendingapplication.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.activity.launch.MainActivity;
import com.grandsea.ticketvendingapplication.base.BaseActivity;
import com.grandsea.ticketvendingapplication.constant.IntentKey;
import com.grandsea.ticketvendingapplication.constant.UrlConstant;
import com.grandsea.ticketvendingapplication.model.bean.PrintfInfo;
import com.grandsea.ticketvendingapplication.util.BasicUtil;
import com.grandsea.ticketvendingapplication.util.BitmapUtil;
import com.grandsea.ticketvendingapplication.util.ToastUtil;
import com.grandsea.ticketvendingapplication.util.ViewUtil;
import com.grandsea.ticketvendingapplication.view.CustomButton;
import com.grandsea.ticketvendingapplication.view.TicketView;
import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.printer.UsbThermalPrinter;
import com.telpo.tps550.api.util.StringUtil;
import com.telpo.tps550.api.util.SystemUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;

import static com.grandsea.ticketvendingapplication.adapter.GsonAdapter.gson;
import static com.grandsea.ticketvendingapplication.util.ToastUtil.context;

public class PrintfActivity extends BaseActivity implements OnClickListener {


    private String printVersion;
    private static final int NOPAPER = 3;
    private static final int LOWBATTERY = 4;
    private static final int PRINTVERSION = 5;
    private static final int PRINTQRCODE = 7;
    private static final int CANCELPROMPT = 10;
    private static final int PRINTERR = 11;
    private static final int OVERHEAT = 12;
    private static final int NOBLACKBLOCK = 15;
    private static final int UPDATE_VIEW = 16;
    private static final int PRINT_FINISH = 17;

    MyHandler handler;
    private String Result;
    private Boolean nopaper = false;
    private boolean mLowBattery = false;
//    private ProgressDialog qrCodeDialog;
    ProgressDialog mDialog;
    UsbThermalPrinter mUsbThermalPrinter = new UsbThermalPrinter(PrintfActivity.this);
    private CustomButton cbBackHome;
    private TextView tvThanks, tvPrintingNum,tvTotalNum;
    private String mOrderId;
    private int currentNum;//当前打印的第几张
    private int totalNum;//总共车票数量
    private List<PrintfInfo.ElectTicketsBean> mElectTickets;
    private LinearLayout testLinearLayout;//测试
    private LinearLayout tipsPrintingLayout;
    private TextView tipsError;
    private boolean isFinished=false;
    private Handler jumpHandler=new Handler();
    private Runnable jumpRunnale=new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(PrintfActivity.this,MainActivity.class));
        }
    };


    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NOPAPER:// error1 纸不足
                    tipsPrintingLayout.setVisibility(View.GONE);
                    tipsError.setVisibility(View.VISIBLE);
                    tipsError.setText("购票成功，由于打印纸已用完，\n请凭身份证到人工售票处领取车票");
                    //noPaperDlg();
                    break;
                case LOWBATTERY://error2电量不足
                    tipsPrintingLayout.setVisibility(View.GONE);
                    tipsError.setVisibility(View.VISIBLE);
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(PrintfActivity.this);
                    alertDialog.setTitle(R.string.operation_result);
                    alertDialog.setMessage(getString(R.string.LowBattery));
                    alertDialog.setPositiveButton(getString(R.string.dialog_comfirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alertDialog.show();
                    break;
                case NOBLACKBLOCK://error3找不到黑标
                    toast("找不到黑标");
                    break;
                case PRINTVERSION://操作失败

                    mDialog.dismiss();
                    if (msg.obj.equals("1")) {
                        toast("打印版本："+printVersion);
                    } else {
//                        toast("操作失败------------");
                    }
//                    tipsPrintingLayout.setVisibility(View.GONE);
//                    tipsError.setVisibility(View.VISIBLE);
                    break;
                case PRINTQRCODE:
                    logI("12333");
                    new qrcodePrintThread().start();
                    break;
                case CANCELPROMPT://取消、完成提示
                    logI("12333CANCELPROMPT");
//                    if (qrCodeDialog != null && !PrintfActivity.this.isFinishing()) {
//                        qrCodeDialog.dismiss();
//                        qrCodeDialog = null;
//                    }
                    break;
                case OVERHEAT://打印过热结果
                    tipsPrintingLayout.setVisibility(View.GONE);
                    tipsError.setVisibility(View.VISIBLE);
                    AlertDialog.Builder overHeatDialog = new AlertDialog.Builder(PrintfActivity.this);
                    overHeatDialog.setTitle(R.string.operation_result);//结果
                    overHeatDialog.setMessage(getString(R.string.overTemp));//打印过热
                    overHeatDialog.setPositiveButton(getString(R.string.dialog_comfirm),//确定
                            new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    overHeatDialog.show();
                    break;
                case UPDATE_VIEW://结果
                    tvSpannable(tvPrintingNum, "正在打印第 ", ""+currentNum, " 张乘车凭证", Color.parseColor("#E55C32"));
                    break;
                case PRINT_FINISH://正常完成
                    jumpHandler.postDelayed(jumpRunnale,6*1000);
                    break;
                default://打印出错
                    ToastUtil.showToast(getApplicationContext(),"Print Error!");
                    tipsPrintingLayout.setVisibility(View.GONE);
                    tipsError.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_printf);
        initView();
        handler = new MyHandler();
        IntentFilter pIntentFilter = new IntentFilter();
        pIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        pIntentFilter.addAction("android.intent.action.BATTERY_CAPACITY_EVENT");
        registerReceiver(printReceive, pIntentFilter);



        mDialog = new ProgressDialog(PrintfActivity.this);
        mDialog.setTitle(R.string.idcard_czz);//操作中
        mDialog.setMessage(getText(R.string.watting));//正在检测驱动版本，请稍候...
        mDialog.setCancelable(false);
        mDialog.show();

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    mUsbThermalPrinter.start(0);
                    mUsbThermalPrinter.reset();
                    printVersion = mUsbThermalPrinter.getVersion();
                } catch (TelpoException e) {
                    e.printStackTrace();
                } finally {
                    if (printVersion != null) {
                        Message message = new Message();
                        message.what = PRINTVERSION;
                        message.obj = "1";
                        handler.sendMessage(message);
                    } else {
                        Message message = new Message();
                        message.what = PRINTVERSION;
                        message.obj = "0";
                        handler.sendMessage(message);
                    }
                }
            }
        }).start();

        initData();

    }
    private void initView() {
        cbBackHome = (CustomButton) findViewById(R.id.apr_cbBackHome);//
        tvThanks = (TextView) findViewById(R.id.apr_tvThanks);//
        tvPrintingNum = (TextView) findViewById(R.id.apr_tvPrintingNu);//
        tvTotalNum = (TextView) findViewById(R.id.apr_tvTotalNum);//

        testLinearLayout = (LinearLayout) findViewById(R.id.apr_testLinearLayout);//
        tipsPrintingLayout = (LinearLayout) findViewById(R.id.apr_tipsPrintingLayout);//
        tipsError = (TextView) findViewById(R.id.apr_tipsError);//

        tipsPrintingLayout.setVisibility(View.VISIBLE);
        tipsError.setVisibility(View.GONE);

        tvThanks.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //print(electTicketsBean);
            }
        });
        cbBackHome.setOnClickListener(this);
    }


    private void initData() {
        //正在打印第0张乘车凭证,   共0张
        tvSpannable(tvPrintingNum, "正在打印第 ", ""+currentNum, " 张乘车凭证", Color.parseColor("#E55C32"));
        tvSpannable(tvTotalNum, "共", ""+totalNum, "张", Color.parseColor("#B4501E"));
        //获取数据
        Intent intent = getIntent();
        if (null == intent)
            return;
        mOrderId = intent.getStringExtra(IntentKey.ORDER_ID);
        logI("------------->mOrderId: "+mOrderId);
        //测试
        //mOrderId="dd5b6400281acef152168e9221e36ce4";
        getTicketInfo(mOrderId);
    }
    @Override
    public void onClick(View v) {
        if (v==cbBackHome){
            back();
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        back();
    }
    private void back() {
        jumpHandler.removeCallbacks(jumpRunnale);
        startActivity(new Intent(PrintfActivity.this, MainActivity.class));
        finish();
    }

    private void getTicketInfo(final String orderId) {

        final Map<String,Object> paramMap = new HashMap();
        paramMap.put("orderId",orderId);//
        String requestContent =new Gson().toJson(BasicUtil.buildPostData(this,paramMap)) ;
        int i = OkHttpUtils.getInstance().getOkHttpClient().connectTimeoutMillis();
        logI(requestContent);
        OkHttpUtils
                .postString()
                .url(UrlConstant.ELECTR_ORDER)
//                .url("http://192.168.2.23:9093/sfc-order/scan_code")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(requestContent)
                .build()
                .execute(new StringCallback(){
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtil.showToast(context,"网络异常，请重新刷新");
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(String str, int i) { // {"result":{"func":"","status":1}}
                       /* GsonObject dataGson = new GsonObject(gson.fromJson(str, p.class)); //对数据转换成json后进行对象封装
                        int status = dataGson.getInt("status", -1);
                        JsonArray electTickets = dataGson.getJsonArray("electTickets");*/
                        PrintfInfo printfInfo = gson.fromJson(str, PrintfInfo.class);
                        mElectTickets = printfInfo.getElectTickets();
                        if (null!= mElectTickets && mElectTickets.size()>0){
                            totalNum=mElectTickets.size();
                            tvSpannable(tvTotalNum, "共", ""+totalNum, "张", Color.parseColor("#B4501E"));
                            print();
//                            for (int position = 0; position < mElectTickets.size(); position++) {
//                                TicketView ticketView = new TicketView(PrintfActivity.this);
//                                ticketView.setViewByData(mElectTickets.get(position));
//                                testLinearLayout.addView(ticketView);
//                            }
                        }
                    }
                });
    }

    private void print() {

        if (mLowBattery == true) { //电量
            handler.sendMessage(handler.obtainMessage(LOWBATTERY, 1, 0, null));
        } else {
            if (!nopaper) {
//                qrCodeDialog = ProgressDialog.show(this, "二维码加载", "二维码生成中，请稍候……");
                handler.sendMessage(handler.obtainMessage(PRINTQRCODE, 1, 0, null));
            } else {
                toast("打印机初始化中，请稍后再试");
            }
        }
    }
    /* Called when the application resumes */
    @Override
    protected void onResume() {
        super.onResume();
    }

    private final BroadcastReceiver printReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_NOT_CHARGING);
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
                //TPS390 can not print,while in low battery,whether is charging or not charging
                if(SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS390.ordinal()){
                    if (level * 5 <= scale) {
                        mLowBattery = true;
                    } else {
                        mLowBattery = false;
                    }
                }else {
                    if (status != BatteryManager.BATTERY_STATUS_CHARGING) {
                        if (level * 5 <= scale) {
                            mLowBattery = true;
                        } else {
                            mLowBattery = false;
                        }
                    } else {
                        mLowBattery = false;
                    }
                }
            }
            //Only use for TPS550MTK devices
            else if (action.equals("android.intent.action.BATTERY_CAPACITY_EVENT")) {
                int status = intent.getIntExtra("action", 0);
                int level = intent.getIntExtra("level", 0);
                if(status == 0){
                    if(level < 1){
                        mLowBattery = true;
                    }else {
                        mLowBattery = false;
                    }
                }else {
                    mLowBattery = false;
                }
            }
        }
    };

    private void noPaperDlg() {
        AlertDialog.Builder dlg = new AlertDialog.Builder(PrintfActivity.this);
        dlg.setTitle(getString(R.string.noPaper));//打印缺纸
        dlg.setMessage(getString(R.string.noPaperNotice));//打印缺纸，请放入纸后重试
        dlg.setCancelable(false);
        dlg.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dlg.show();
    }

    //二维码打印线程
    private class qrcodePrintThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                mUsbThermalPrinter.reset();
                mUsbThermalPrinter.setGray(5);//灰度值 0--7
                if (mElectTickets==null||mElectTickets.size()<1)
                    return;

                for (int i = 0; i < mElectTickets.size(); i++) {
                    TicketView ticketView = new TicketView(PrintfActivity.this);
                    ticketView.setViewByData(mElectTickets.get(i));
                    int viewHeight = ViewUtil.getViewHeight(ticketView);
                    int viewWidth = ViewUtil.getViewWidth(ticketView);
                    Bitmap bitmap = BitmapUtil.getViewBitmap(ticketView, viewWidth, viewHeight);//inflate   printLinearLayout
                    currentNum=i+1;
                    Message msg = handler.obtainMessage();
                    msg.what=UPDATE_VIEW;
                    handler.sendMessage(msg);
                    if(bitmap != null){
                        mUsbThermalPrinter.printLogo(bitmap, true);
                    }
                    mUsbThermalPrinter.printString();
                    mUsbThermalPrinter.walkPaper(5);
                }
                mUsbThermalPrinter.walkPaper(15);
                handler.sendMessage(handler.obtainMessage(PRINT_FINISH, 1, 0, null));
//                LinearLayout linearLayout = new LinearLayout(PrintfActivity.this);
//                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
//                linearLayout.setOrientation(LinearLayout.VERTICAL);
//                for (int i = 0; i < mElectTickets.size(); i++) {
//                    TicketView ticketView = new TicketView(PrintfActivity.this);
//                    ticketView.setViewByData(mElectTicketsBean);
//                    linearLayout.addView(ticketView);
//                }
//                int viewHeight = ViewUtil.getViewHeight(linearLayout);
//                int viewWidth = ViewUtil.getViewWidth(linearLayout);
//                Bitmap bitmap = BitmapUtil.getViewBitmap(linearLayout, viewWidth, viewHeight);//inflate   printLinearLayout
//                if(bitmap != null){
//                    mUsbThermalPrinter.printLogo(bitmap, true);
//                }
//                mUsbThermalPrinter.printString();
//                mUsbThermalPrinter.walkPaper(15);

            } catch (Exception e) {
                e.printStackTrace();
                Result = e.toString();
                if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                    nopaper = true;
                } else if (Result.equals("com.telpo.tps550.api.printer.OverHeatException")) {
                    handler.sendMessage(handler.obtainMessage(OVERHEAT, 1, 0, null));
                } else {
                    handler.sendMessage(handler.obtainMessage(PRINTERR, 1, 0, null));
                }
            } finally {
                handler.sendMessage(handler.obtainMessage(CANCELPROMPT, 1, 0, null));
                if (nopaper){
                    handler.sendMessage(handler.obtainMessage(NOPAPER, 1, 0, null));
                    nopaper = false;
                    return;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
//        if (qrCodeDialog != null && !PrintfActivity.this.isFinishing()) {
//            qrCodeDialog.dismiss();
//            qrCodeDialog = null;
//        }

        unregisterReceiver(printReceive);
        mUsbThermalPrinter.stop();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


}
