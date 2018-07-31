package com.grandsea.ticketvendingapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.base.BaseActivity;
import com.grandsea.ticketvendingapplication.constant.IntentKey;
import com.grandsea.ticketvendingapplication.constant.UrlConstant;
import com.grandsea.ticketvendingapplication.model.bean.LockTicketStatus;
import com.grandsea.ticketvendingapplication.model.bean.OrderInfo;
import com.grandsea.ticketvendingapplication.model.bean.Passenger;
import com.grandsea.ticketvendingapplication.model.bean.PersonInfo;
import com.grandsea.ticketvendingapplication.model.common.GsonObject;
import com.grandsea.ticketvendingapplication.net.RequestUtil;
import com.grandsea.ticketvendingapplication.util.BasicUtil;
import com.grandsea.ticketvendingapplication.util.RandomValue;
import com.grandsea.ticketvendingapplication.view.CustomButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

import static com.grandsea.ticketvendingapplication.adapter.GsonAdapter.gson;

/**
 * Created by Grandsea09 on 2017/10/9.
 */

public class InputIdActivity extends BaseActivity {
    private static final String TAG = "InputIdActivity";
    @BindView(R.id.aii_etName)
    EditText etName;
    @BindView(R.id.aii_etIdCode)
    EditText etId;
    @BindView(R.id.aii_btSure)
    CustomButton cbSure;
    @BindView(R.id.aii_btBack)
    CustomButton cbBack;
    private OrderInfo mOrderInfo;
    private int totalTickets;
    private String mOrderId;
    private String mTicketDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_id);
        ButterKnife.bind(this);
        initData();
        etId.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
    }

    private void initData() {

        //获取数据
        Intent intent = getIntent();
        if (null == intent)
            return;
        mTicketDate = intent.getStringExtra(IntentKey.TICKET_DATE);
        mOrderInfo = (OrderInfo) intent.getSerializableExtra(IntentKey.ORDER_INFO);
        totalTickets = intent.getIntExtra(IntentKey.TOTAL_TICKETS,0);
        if (totalTickets==0){
            toast("请重新购票");
            finish();
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mOrderId)){
            RequestUtil.cancelOrderById(this,mOrderId);
        }
    }

    @OnClick({R.id.aii_btSure, R.id.aii_btBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.aii_btSure:
                String id = etId.getText().toString().trim();
                String name = etName.getText().toString().trim();
                if (TextUtils.isEmpty(id)){
                    toast("身份证号码不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(name)){
                    toast("姓名不能为空！");
                    return;
                }
                PersonInfo personInfo = new PersonInfo(name, id);
                commitOrder(personInfo);
                break;
            case R.id.aii_btBack:
                finish();
                break;
        }
    }
    /**
     *  提交订单
     * @param idInfo
     * @return
     */
    private void commitOrder( PersonInfo idInfo) {
        String no = idInfo.getIdNo();
        String name = idInfo.getName();
        mOrderInfo.setName(name);
        mOrderInfo.setPhone(RandomValue.getTel());//随机生成手机号
        mOrderInfo.setIdCard(no);
        List<Passenger> passengers = initPassengers(no, name);
        mOrderInfo.setPassengers(gson.toJson(passengers));
        final Map<String, Object> paramMap = new HashMap();
        paramMap.put("orderInfo", mOrderInfo);
        String requestContent = new Gson().toJson(BasicUtil.buildPostData(this,paramMap));//58ff4eddda0e83185baa7eb7cc4fc5d1
        Log.d(TAG, requestContent);
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
                        toast("网络异常，请重新刷新");
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
                            if (null!=payParams){
                                mOrderId = payParams.getString("orderId");
                                if (!TextUtils.isEmpty(mOrderId))
                                    intentToPay(mOrderId);
                                else
                                    toast("异常");
                            }

                        } else{
                            String payParams = dataGson.getString("payParams");
                            if (!TextUtils.isEmpty(payParams))
                                toast(payParams);
                            else
                                toast("异常");
                        }


                    }
                });
    }

    /**
     * 跳转支付页面：支付
     * @param orderId
     */
    private void intentToPay(String orderId) {

        //跳转支付页面：支付
        Intent intent = new Intent(this, PayActivity.class);
        //int status = commitOrder(mOrderInfo,passengers);
        intent.putExtra(IntentKey.ORDER_INFO, mOrderInfo);
        intent.putExtra(IntentKey.ORDER_ID, orderId);
        intent.putExtra(IntentKey.TICKET_DATE,mTicketDate);//++
        startActivity(intent);
    }


    private  List<Passenger> initPassengers(String no, String name) {
        List<Passenger> passengers = new ArrayList<>();
        int numberMan = mOrderInfo.getTicketNum();
        int numberChild = mOrderInfo.getcTicketNum();
        int numberStudent = mOrderInfo.getsTicketNum();
        int numberKid = mOrderInfo.getkTicketNum();
        if (numberMan>0){
            for (int i = 0; i < numberMan; i++) {
                Passenger p = new Passenger();
                p.setIdCard(no);
                p.setName(name);
                p.setType(1);
                passengers.add(p);
            }
        }
        if (numberChild>0){
            for (int i = 0; i < numberChild; i++) {
                Passenger p = new Passenger();
                p.setIdCard(no);
                p.setName(name);
                p.setType(2);
                passengers.add(p);
            }
        }
        if (numberStudent>0){
            for (int i = 0; i < numberStudent; i++) {
                Passenger p = new Passenger();
                p.setIdCard(no);
                p.setName(name);
                p.setType(3);
                passengers.add(p);
            }
        }
        if (numberKid>0){
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


}