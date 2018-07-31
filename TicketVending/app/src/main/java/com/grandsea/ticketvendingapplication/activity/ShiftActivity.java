package com.grandsea.ticketvendingapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.adapter.ShiftAdapter;
import com.grandsea.ticketvendingapplication.base.BaseActivity;
import com.grandsea.ticketvendingapplication.constant.IntentKey;
import com.grandsea.ticketvendingapplication.constant.UrlConstant;
import com.grandsea.ticketvendingapplication.model.bean.OrderInfo;
import com.grandsea.ticketvendingapplication.model.bean.PostShiftParams;
import com.grandsea.ticketvendingapplication.model.bean.Shift;
import com.grandsea.ticketvendingapplication.model.common.GsonObject;
import com.grandsea.ticketvendingapplication.util.BasicUtil;
import com.grandsea.ticketvendingapplication.util.DateUtil;
import com.grandsea.ticketvendingapplication.util.DialogManager;
import com.grandsea.ticketvendingapplication.view.CustomButton;
import com.grandsea.ticketvendingapplication.view.DateView;
import com.grandsea.ticketvendingapplication.view.ImageTextView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;

import static com.grandsea.ticketvendingapplication.adapter.GsonAdapter.gson;

/**
 * 班次
 */
public class ShiftActivity extends BaseActivity implements View.OnClickListener {

    private static final int DEFAULT_PAGE_SIZE = 15;
    private static final int PAGE_MIN = 1;

    private PostShiftParams mPostShiftParams;
    private RecyclerView recyclerView;
    private int pageid =PAGE_MIN;
    private List<Shift> shiftDataList = new ArrayList<>();
    private CustomButton btBack,btLast,btNext;
    private int currentPage =1;
    private ImageTextView itvStationStart,itvStationEnd;
    private DateView dateView;
    private String ticketDate;
    private TextView tvNoShift;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);

        itvStationStart =(ImageTextView) findViewById(R.id.ash_stationStart);
        itvStationEnd =(ImageTextView) findViewById(R.id.ash_stationEnd);
        dateView =(DateView) findViewById(R.id.ash_dateView);
        dateView.setDateChangeListener(new DateView.DateChangeListener() {
            @Override
            public void add(int day) {
                long timeSpan = day*DateView.DAY_SECEND;
                currentPage=PAGE_MIN;//重置为第一页
                getShiftList(currentPage, timeSpan);
            }

            @Override
            public void minus(int day) {
                long timeSpan = day*DateView.DAY_SECEND;
                currentPage=PAGE_MIN;//重置为第一页
                getShiftList(currentPage, timeSpan);
            }
        });

        //处理上下按钮功能
        btBack = (CustomButton) findViewById(R.id.ash_btBack);
        btNext =(CustomButton) findViewById(R.id.ash_btNext);
        btLast =(CustomButton) findViewById(R.id.ash_btLast);

        btBack.setOnClickListener(this);
        btNext.setOnClickListener(this);
        btLast.setOnClickListener(this);

        recyclerView =(RecyclerView) findViewById(R.id.ash_RecyclerView);
        tvNoShift =(TextView) findViewById(R.id.ash_tipsNoShift);
        tvNoShift.setVisibility(View.GONE);
        tvSpannableImg(tvNoShift,"很抱歉，你查询的站点今天没有合适的班次\n你可以点击日期右边的@查询明天的班次，\n或者点击右下角的“返回按钮选择其他站点”","@");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initData();

    }

    private void initData() {
        //处理前面传递过来的对象 -->
        Intent intent = getIntent();
        mPostShiftParams = (PostShiftParams)intent.getSerializableExtra(IntentKey.SHIFT_OBJ);
        String getOnStation = mPostShiftParams.getGetOnStation();
        String takeOffStation = mPostShiftParams.getTakeOffStation();
        itvStationStart.getTvLeft().setText(getOnStation+"");
        itvStationEnd.getTvLeft().setText(takeOffStation+"");
        getShiftList(currentPage,0);
    }

    @Override
    public void onClick(View v) {
        if (v == btBack) {
            finish();
        } else if (v == btLast) {
//            toast("btLast");
            if (null == recyclerView)
                return;
            if (currentPage > PAGE_MIN) {
                currentPage--;
                getShiftList(currentPage,dateView.getTimeSpan());
            } else {
                DialogManager.dialogPageTurn(ShiftActivity.this, "已经是第一页",null);
            }
        } else if (v == btNext) {
            if (null == recyclerView)
                return;
            if(shiftDataList.size() < DEFAULT_PAGE_SIZE){
                DialogManager.dialogPageTurn(ShiftActivity.this, "已经是最后一页",null);
            }else{
                currentPage ++;
                getShiftList(currentPage,dateView.getTimeSpan());
            }
        }
    }

    private void getShiftList(int pageid,long timeSpan) {
        Date date = new Date(System.currentTimeMillis() + timeSpan);
        ticketDate = DateUtil.formatYYYYMMDD(date);
        mPostShiftParams.setDepartDate(ticketDate);
        Map<String,Object> paramMap = new HashMap();
        paramMap.put("type",1);
        paramMap.put("pageid",pageid);
        paramMap.put("params", mPostShiftParams);
        paramMap.put("orderType",1); //1：按时间升序排序，2：按时间降序排序
        String requestContent =new Gson().toJson(BasicUtil.buildPostData(this,paramMap)) ;
        logI(requestContent);
        OkHttpUtils
                .postString()
                .url(UrlConstant.SHIFT_LIST)
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
                    public void onResponse(String s, int i) {
                        GsonObject dataGson = new GsonObject(gson.fromJson(s, JsonObject.class)); //对数据转换成json后进行对象封装
                        final List<Shift> shiftList = dataGson.getAsList(gson,"shifts",Shift.class);
                        shiftDataList = shiftList;
                        if(shiftList ==null || shiftList.size()==0){
                            //toast("没有班次了");
                            tvNoShift.setVisibility(View.VISIBLE);
                            //return;
                        }else {
                            tvNoShift.setVisibility(View.GONE);
                        }
                        ShiftAdapter adapter = new ShiftAdapter(shiftList);
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(new ShiftAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                if (shiftList.get(position).getTicketNum()<1) {
                                    toast("请选择有余票的车次");
                                    return;
                                }
                                Intent intent = new Intent(ShiftActivity.this,SelectTicketsActivity.class);//PassengerActivity
                                Shift shift = shiftList.get(position);
                                String shiftStr = gson.toJson(shift);
                                OrderInfo orderInfo = gson.fromJson(shiftStr,OrderInfo.class);
                                orderInfo.setShiftId(shift.getId());
                                orderInfo.setTicketDate(ticketDate); // 选中班次的日期   --DateUtil.formatYYYYMMDD(new Date())

                                int departCityId = mPostShiftParams.getDepartCityId();
                                String departCity = mPostShiftParams.getDepartCity();
                                int destCityId = mPostShiftParams.getDestCityId();
                                String destCity = mPostShiftParams.getDestCity();
                                //TODO 要修改。。以下 4 项
                                orderInfo.setDepartCityId(departCityId);//出发城市id  --DefaultConfig.DEPART_CITY_ID
                                orderInfo.setDepartCity(departCity);//出发城市 --"珠海"
                                orderInfo.setDestCityId(destCityId);//终点城市id
                                orderInfo.setDestCity(destCity);//终点城市
                                intent.putExtra(IntentKey.EXTRA_SHIFT_TO_PASSENGER_OBJ,orderInfo);
                                intent.putExtra(IntentKey.TICKET_DATE,dateView.getTvDate().getText().toString().trim());
                                startActivity(intent);
                            }
                        });
                    }
                });
    }


}
