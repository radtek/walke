//package com.grandsea.ticketvendingapplication.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.grandsea.ticketvendingapplication.R;
//import com.grandsea.ticketvendingapplication.adapter.DsitrictAdapter;
//import com.grandsea.ticketvendingapplication.constant.DefaultConfig;
//import com.grandsea.ticketvendingapplication.constant.IntentKey;
//import com.grandsea.ticketvendingapplication.constant.UrlConstant;
//import com.grandsea.ticketvendingapplication.model.bean.City;
//import com.grandsea.ticketvendingapplication.model.bean.District;
//import com.grandsea.ticketvendingapplication.model.bean.PostShiftParams;
//import com.grandsea.ticketvendingapplication.model.bean.Station;
//import com.grandsea.ticketvendingapplication.model.common.GsonObject;
//import com.grandsea.ticketvendingapplication.util.BasicUtil;
//import com.grandsea.ticketvendingapplication.util.DateUtil;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.callback.StringCallback;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import okhttp3.Call;
//import okhttp3.MediaType;
//
//import static com.grandsea.ticketvendingapplication.adapter.GsonAdapter.gson;
//
///**
// * Created by Administrator on 2017/8/15.
// * 目标站点
// */
//
//public class DestStationActivity extends AppCompatActivity {
//
//    private static final String TAG = "DestStationActivity";
//
//    private static final String HOT_CITY_TAG = "热门城市";
//    private static final int getOnId = DefaultConfig.GET_ON_ID;  //出发站点id
//
//    private TextView destCityBameTextView;
//    private RecyclerView districtStationRecyclerView;
//    private DsitrictAdapter districtAdapter;
//    private int departCityId = 0;//出发城市id
//    private int destCityId = 0;
//    private String destCityName;
//    private Button btStationBack;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dest_station);
//
//        Intent intent =getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
//
//        departCityId = intent.getIntExtra(IntentKey.INTENT_DEPART_CITY_ID,0);
//        City city = (City)intent.getSerializableExtra(IntentKey.INTENT_DEST_CITY);
//        destCityId = city.getId();
//        destCityName = city.getName();
//
//        destCityBameTextView = (TextView) findViewById(R.id.dest_city_name_txt);
//        destCityBameTextView.setText("目的地城市："+destCityName +"   ");
//
//        districtStationRecyclerView = (RecyclerView) findViewById(R.id.district_station_recycler_view_id);
//        districtStationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        //返回键
//        btStationBack = (Button) findViewById(R.id.bt_station_back);
//        btStationBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        getStationList();
//
//    }
//    private void getStationList(){
//        if(districtAdapter == null){
//
//            Map<String,Object> paramMap = new HashMap();
//            paramMap.put("type",1);
//            paramMap.put("departCityId",departCityId);
//            paramMap.put("destCityId",destCityId);
//            paramMap.put("date", DateUtil.formatYYYYMMDD(new Date())); //默认今天
//            paramMap.put("getOnId", getOnId);  //值=-1时，表示获取上车点   值=0时，表示获取所有下车点   值=1时，表示获取对应下车点
//
//            String requestContent =new Gson().toJson(BasicUtil.buildPostData(paramMap)) ;
//            Log.d(TAG,requestContent);
//            OkHttpUtils
//                    .postString()
//                    .url(UrlConstant.STATION_LIST)
//                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
//                    .content(requestContent)
//                    .build()
//                    .execute(new StringCallback(){
//
//                        @Override
//                        public void onError(Call call, Exception e, int i) {
//                            Toast.makeText(DestStationActivity.this,"网络异常，请重新刷新", Toast.LENGTH_LONG).show();
//                            e.printStackTrace();
//                        }
//
//                        @Override
//                        public void onResponse(String s, final int i) {
//                            Log.e(TAG, "onResponse：complete");
//                            Log.d(TAG,s);
//                            //s = {"result":{"func":"station_list","status":1},"stations":[{"cityId":2,"id":9,"district":"三乡","name":"三乡"},{"cityId":2,"id":10,"district":"坦洲","name":"天逸酒店"},{"cityId":2,"id":75,"district":"西区","name":"中山富业广场"}]}
//                            GsonObject dataGson = new GsonObject(gson.fromJson(s, JsonObject.class)); //对数据转换成json后进行对象封装
//                            List<Station> stationList = dataGson.getAsList(gson,"stations",Station.class);
//                            final List<District> list =  handleStationDate(stationList);
//
//                            if(list ==null || list.size()== 0){
//                                Toast.makeText(DestStationActivity.this,"当前城市已没有下车站点",Toast.LENGTH_SHORT).show();
//                            }
//
//                            districtAdapter= new DsitrictAdapter(list);
//                            districtStationRecyclerView.setAdapter(districtAdapter);
//                            districtAdapter.setOnItemClickListener(new DsitrictAdapter.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(int districtPosition, int position) { //区域的位置+站点的位置
////                                    Toast.makeText(DestStationActivity.this,list.get(districtPosition).getName()+"==>"+list.get(districtPosition).getStations().get(position).getName(),Toast.LENGTH_LONG).show();
//                                    //数据传输给下一个页面
//                                    District district = list.get(districtPosition);
//                                    Station station = district.getStations().get(position);
//
//                                    Intent intent = new Intent(DestStationActivity.this,ShiftActivity.class);
//                                    Log.d(TAG,"**************START*****点击了站点：***************"+station.getId()+" "+station.getName());
//                                    PostShiftParams shiftParams = new PostShiftParams();
//                                    shiftParams.setDepartCityId(departCityId);
//                                    shiftParams.setDestCityId(destCityId);
//                                    shiftParams.setDepartDate(DateUtil.formatYYYYMMDD(new Date())); //TODO 默认今天，待修改
//                                    shiftParams.setGetOnId(getOnId);
//                                    shiftParams.setGetOnStation("珠海机场");                           //TODO 待修改位置
//                                    shiftParams.setTakeOffId(station.getId());
//                                    shiftParams.setTakeOffStation(station.getName());
//                                    intent.putExtra(IntentKey.SHIFT_OBJ,shiftParams);
//                                    startActivity(intent);
//
//                                }
//                            });
//                        }
//
//                    });
//        }else{
//            districtAdapter.notifyDataSetChanged();
//        }
//
//    }
//
//    //处理数据：分区域的站点
//    private List<District> handleStationDate(List<Station> stationList) {
//        List<District> dataList = new ArrayList<District>();
//
//        Map<String,District> districtMap = new HashMap<>();         //头部信息
//        Map<String,List<Station>> stationMap = new HashMap<>();     //中间展示数据
//        if(stationList!=null && stationList.size()>0){
//            for(Station station:stationList){
//                List<Station> stationListIntoMap = null;
//                String key = station.getDistrict();
//                if(key == null){
//                    key = HOT_CITY_TAG ;
//                }
//                if(stationMap.containsKey(key)){
//                    stationListIntoMap = stationMap.get(key);
//                    stationListIntoMap.add(station);
//                }else{
//                    stationListIntoMap = new ArrayList<Station>();
//                    stationListIntoMap.add(station);
//                    stationMap.put(key,stationListIntoMap);
//                }
//                District district = districtMap.get(key);
//                if(district == null){
//                    district = new District();
//                    district.setName(key);
//                    districtMap.put(key,district);
//                }
//            }
//        }
//
//        //组装新的list集合 //热门城市
//        Log.d(TAG,"**************START********************");
//        if(districtMap.get(HOT_CITY_TAG) != null){
//            District district = districtMap.get(HOT_CITY_TAG);
//            district.setStations(stationMap.get(HOT_CITY_TAG));
//            dataList.add(district);
//        }
//        Set<String> keySet = stationMap.keySet();
//        for(String key:keySet){
//            if(!HOT_CITY_TAG.equals(key)){
//                District district = districtMap.get(key);
//                district.setStations(stationMap.get(key));
//                dataList.add(district);
//            }
//        }
//        Log.d(TAG,"**************END********************");
//        return dataList;
//    }
//}
