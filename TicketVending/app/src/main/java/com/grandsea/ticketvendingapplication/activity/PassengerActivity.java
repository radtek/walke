//package com.grandsea.ticketvendingapplication.activity;
//
//import android.app.Instrumentation;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.hardware.usb.UsbDevice;
//import android.hardware.usb.UsbManager;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.grandsea.ticketvendingapplication.R;
//import com.grandsea.ticketvendingapplication.adapter.GsonAdapter;
//import com.grandsea.ticketvendingapplication.adapter.PassengerAdapter;
//import com.grandsea.ticketvendingapplication.constant.IntentKey;
//import com.grandsea.ticketvendingapplication.constant.UrlConstant;
//import com.grandsea.ticketvendingapplication.constant.DefaultConfig;
//import com.grandsea.ticketvendingapplication.dao.PassengerDao;
//import com.grandsea.ticketvendingapplication.model.bean.OrderInfo;
//import com.grandsea.ticketvendingapplication.model.bean.Passenger;
//import com.grandsea.ticketvendingapplication.activity.third_party.HIDTestNoUse.ShowIDCardPicDialog;
//import com.grandsea.ticketvendingapplication.util.BasicUtil;
//import com.grandsea.ticketvendingapplication.util.DealStrSubUtil;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.callback.StringCallback;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import okhttp3.Call;
//import okhttp3.MediaType;
//
///**
// * 乘客
// * */
//public class PassengerActivity extends AppCompatActivity implements GsonAdapter,View.OnClickListener {
//
//    private static final String TAG = "PassengerActivity";
//    private OrderInfo orderInfo /*= new OrderInfo()*/;
//    private RecyclerView passengerRecyclerView;
//    private TextView shiftNameTextView;
//    private Button btPassengerBack;
//    private Button btAddPassenger;
//    private TextView tvTotalPrice;
//    private Button btPay;
//    private PassengerAdapter passengerAdapter;
//    private List<Passenger> passengers = new ArrayList<>();
////    private Shift shift = new Shift();
//
//    //读取身份证所需字段
//    private TextView mTextView; //提示字段
//    private boolean mSecondCard;
//    private UsbDevice mDevice;
//    private iDRHIDDev mHIDDev;
//    private UsbManager mUsbManager;
//    private ShowIDCardPicDialog mShowIDCardPicDialog;
//    private iDRHIDDev.SecondIDInfo sIDInfo;
//    private static final String ACTION_USB_PERMISSION = "com.Routon.HIDTest.USB_PERMISSION";
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_passenger);
//
//        //获取数据
//        Intent i = getIntent();
//        orderInfo = (OrderInfo)i.getSerializableExtra(IntentKey.EXTRA_SHIFT_TO_PASSENGER_OBJ);
//
//        if(orderInfo == null){
//            orderInfo = new OrderInfo();
//        }
//        shiftNameTextView = (TextView)findViewById(R.id.shift_name_txt);
//        passengerRecyclerView = (RecyclerView)findViewById(R.id.passenger_recycler_view_id);
//        btPassengerBack = (Button) findViewById(R.id.bt_passenger_back);
//        btAddPassenger = (Button) findViewById(R.id.bt_add_passenger);
//        tvTotalPrice =(TextView) findViewById(R.id.tv_total_price);
//        btPay =(Button) findViewById(R.id.bt_pay);
//        mTextView = (TextView) findViewById(R.id.textView1);
//        mTextView.setText("身份证阅读程序：");
//
//        btAddPassenger.setOnClickListener(this); //添加乘车人
//
//        shiftNameTextView.setText(orderInfo.getGetOnStation() +" --> " + orderInfo.getTakeOffStation() +"   ");
//
//        passengerRecyclerView.setLayoutManager(new LinearLayoutManager(PassengerActivity.this));
//
//        btPassengerBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread() {
//                    public void run() {
//                        try {
//                            Instrumentation inst = new Instrumentation();
//                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
//                        } catch (Exception e) {
//                            Log.e("Exception when sendKeyDownUpSync", e.toString());
//                        }
//                    }
//                }.start();
//            }
//        });
//
//
//
//        passengers =  PassengerDao.getPassenger();
////      passengers = new ArrayList<>();
//        passengerAdapter = new PassengerAdapter(passengers,orderInfo);
//        passengerRecyclerView.setAdapter(passengerAdapter);
//        //初始化价格
//        if(passengers != null || passengers.size() >0){
//            updateTotalPrice(passengers.size());
//
//        }
//
//
//        passengerAdapter.setOnItemClickListener(new PassengerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//
//                Toast.makeText(PassengerActivity.this,"移除用户："+passengers.get(position).getName(),Toast.LENGTH_LONG).show();
//                passengers.remove(position);
//
//                passengerAdapter.notifyDataSetChanged();
//            }
//        });
//
//
//        //跳转支付页面：下单
//        btPay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(PassengerActivity.this,"点击支付。。",Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(PassengerActivity.this,PayActivity.class);
//                int status = submitOrder(orderInfo,passengers);
//                intent.putExtra(IntentKey.ORDER_INFO,orderInfo);
////                if(status >0 ){
////                    startActivity(intent);
////                }
//                startActivity(intent);
//            }
//        });
//
//
//        //*******************身份证读取功能********************************************************************
////        PosUtil.setRfidPower(PosUtil.RFID_POWER_ON);
//        // initialize card idInfo
//        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
//        mDevice = null;
//        mHIDDev = new iDRHIDDev();
//        // initialize card idInfo
//        mSecondCard = true;
////        clearSecondCardInfo();
////        clearTypeACardInfo();
//
//        // listen for new devices
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(ACTION_USB_PERMISSION);
//        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
//        registerReceiver(mUsbReceiver, filter);
//
//        // 检查USB设备是否插入
//        for (UsbDevice device : mUsbManager.getDeviceList().values()) {
//
//            Log.e(TAG, "vid: " + device.getVendorId() + " pid:" + device.getProductId());
//            if (device.getVendorId() == 1061 && device.getProductId() == 33113) {
//                Intent intent = new Intent(ACTION_USB_PERMISSION);
//                PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//                mUsbManager.requestPermission(device, mPermissionIntent);
//                Log.d(TAG, "usb device: checked");
//                appendLog("发现读卡设备");
//            }
//        }
//
//        mShowIDCardPicDialog = new ShowIDCardPicDialog(this);
//    }
//
//    //更新总价
//    private void updateTotalPrice(int size) {
//        tvTotalPrice.setText(orderInfo.getPrice()*size + "");
//    }
//
//    private BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//
//            if (ACTION_USB_PERMISSION.equals(action)) {
//                synchronized (this) {
//                    UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
//
//                    if (mDevice != null) {
//                        mHIDDev.closeDevice();
//                        mDevice = null;
//                    }
//
//                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
//                        if (device != null) {
//                            // call method to set up device communication
//
//                            final int ret = mHIDDev.openDevice(mUsbManager, device);
//                            Log.i(TAG, "open device:" + ret);
//                            if (ret == 0) {
//                                mDevice = device;
//                                appendLog("usb device: 已授权");
//                            } else {
//                                mDevice = null;
//                                appendLog("usb device: 授权失败");
//                            }
//                        }
//                    } else {
//                        Log.d(TAG, "permission denied for device " + device);
//                        appendLog("usb device: 未授权");
//                        finish();
//                    }
//                }
//            }
//        }
//    };
//
//    private void appendLog(String log) {
//        log = mTextView.getText() + "\n" + log;
//
////        Toast.makeText(this,"log:"+log,Toast.LENGTH_LONG).show();
//        mTextView.setText(log);
//    }
//    private int submitOrder(OrderInfo orderInfo, List<Passenger> passengers) {
//        int status = 0;
//        Passenger passenger = passengers.get(0);
//        orderInfo.setName(passenger.getName());
//        orderInfo.setPhone(passenger.getPhone());
//        orderInfo.setIdCard(passenger.getIdCard());
//        orderInfo.setTicketNum(passengers.size());
//        orderInfo.setTotalPrice(orderInfo.getPrice()*passengers.size());
//        orderInfo.setPassengers(gson.toJson(passengers));
////        Log.d(TAG,gson.toJson(orderInfo));
//
//        Map<String,Object> paramMap = new HashMap();
//        paramMap.put("orderInfo",orderInfo);
//        String requestContent =new Gson().toJson(BasicUtil.buildPostData("58ff4eddda0e83185baa7eb7cc4fc5d1","13588888888",paramMap)) ;
//        Log.d(TAG,requestContent);
//        OkHttpUtils
//                .postString()
//                .url(UrlConstant.ORDER_TICKET)
//                .mediaType(MediaType.parse("application/json; charset=utf-8"))
//                .content(requestContent)
//                .build()
//                .execute(new StringCallback(){
//
//                    @Override
//                    public void onError(Call call, Exception e, int i) {
//                        Toast.makeText(PassengerActivity.this,"网络异常，请重新刷新", Toast.LENGTH_LONG).show();
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onResponse(String s, final int i) {
//                        Log.d(TAG,s);
//                        Toast.makeText(PassengerActivity.this,"s:"+s,Toast.LENGTH_LONG).show();
//
////                        GsonObject dataGson = new GsonObject(gson.fromJson(s, JsonObject.class)); //对数据转换成json后进行对象封装
//
//
//                    }
//
//                });
////        status=1;
//
//        return status;
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        if(v == btAddPassenger){ //添加乘车人功能
//           /* new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    getIDInfo();
//                }
//            },3000);*/
//            getIDInfo();
//        }
//    }
//
//    public void getIDInfo() {
//        if(passengers.size() >= DefaultConfig.MAX_PASSENGER_COUNT){
//            Toast.makeText(PassengerActivity.this,
//                    "Sorry，最多能添加"+ DefaultConfig.MAX_PASSENGER_COUNT +"位乘客",Toast.LENGTH_LONG).show();
//            return;
//        }
////            String iDInfo = "姓    名：杨声劲\n" +
////             性    别：男\n" +
////             名    族：汉\n" +
////             生    日：1994****\n" +
////             住    址：广东省汕尾市" +
////             身份证号：441501********5051\n" +
////             发证机关：汕尾市公安局********\n" +
////             有效日期：20121203-20221203\n";
//
////
//        int ret = findCard();
//        if(ret < 0){
//            int i=0;
//            while(i<=8){
//                try {
//                    Thread.sleep(500);
//                    i++;
//                    ret = findCard();
//                    if(ret > 0){
//                        break;
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        Passenger passenger = null;
//        if (ret >= 0) // 找到卡
//        {
//            // 读卡
//            sIDInfo = mHIDDev.new SecondIDInfo();
//            byte[] fingerPrint = new byte[1024];
//
//            // ret = mHIDDev.ReadBaseMsg(sIDInfo);
//            ret = mHIDDev.ReadBaseFPMsg(sIDInfo, fingerPrint);
//
//            if (ret < 0) {
//                appendLog("读卡失败：");
//                return;
//            }
//            appendLog(sIDInfo.fingerPrint == 1024 ? "有指纹" : "无指纹");
//            // 设置蜂鸣器和LED灯
//            ret = mHIDDev.BeepLed(true, true, 500);
//
//            passenger = new Passenger();
//            String iDInfo = sIDInfo.getFormatIDCardText();
//            String name = DealStrSubUtil.getSubUtilSimple(iDInfo,"姓    名：(.*?)\n"); //返回第一个符合的数据
//            String idCard = DealStrSubUtil.getSubUtilSimple(iDInfo,"身份证号：(.*?)\n"); //返回第一个符合的数据
//            passenger.setName(name);
//            passenger.setIdCard(idCard);
//            // 刷新显示
////          invalidate();
////          Toast.makeText(this,"身份证信息"+sIDInfo.getFormatIDCardText(),Toast.LENGTH_LONG).show();
//        } else // 未找到卡
//        {
//            iDRHIDDev.MoreAddrInfo mAddr = mHIDDev.new MoreAddrInfo();
//            // 通过读追加地址来判断卡是否在机具上。
//            ret = mHIDDev.GetNewAppMsg(mAddr);
//            if (ret < 0) // 机具上没有放卡
//                appendLog("请放卡：");
//            else // 机具上的卡已读过一次
//                appendLog("请重新放卡：");
//            return ;
//        }
//
//        // 读卡号， 注意不要放在读身份证信息前面，否则会读身份证信息失败
//        byte data[] = new byte[32];
//        int www = mHIDDev.getIDCardCID(data);
//        if (www < 0) {
//            appendLog("读卡号失败：" + www);
//        } else {
//            String cardID = String.format("%s %02x%02x%02x%02x%02x%02x%02x%02x", "卡体号：", data[0], data[1], data[2],
//                    data[3], data[4], data[5], data[6], data[7]);
//            appendLog(cardID);
//        }
//        if(passenger !=null ){
//            if(!passengers.contains(passenger)){
//                passengers.add(passenger);
//                updateTotalPrice(passengers.size());
//            }
//        }
//        passengerAdapter.notifyDataSetChanged();
////        return IDInfo;
//    }
//
//    private int findCard() {
//        //TODO 下面是实现读取身份证外设接口
//        Passenger passenger = null;
////                passenger.setIdCard("44888**************8889");
////                passenger.setName("从康007");
//
//        mTextView.setText("读二代证：");
//        mSecondCard = true;
////        clearSecondCardInfo();
//
//        if (mDevice == null) {
//            appendLog("请插入读卡器！");
//            return 0;
//        }
//
//        int ret;
//
//        // 读安全模块的状态
//        ret = mHIDDev.GetSamStaus();
//        if (ret < 0) {
//            appendLog("读卡器未准备好！");
//            return ret;
//        }
//
//        iDRHIDDev.SamIDInfo samIDInfo = mHIDDev.new SamIDInfo();
//
//        // 读安全模块号
//        ret = mHIDDev.GetSamId(samIDInfo);
//        appendLog("samid: " + samIDInfo.samID);
//
//        // 找卡
//        ret = mHIDDev.Authenticate();
//        return ret;
//    }
//}
