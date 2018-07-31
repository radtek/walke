package com.grandsea.ticketvendingapplication.activity.launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.activity.DestCityActivity;
import com.grandsea.ticketvendingapplication.adapter.CityAdapter;
import com.grandsea.ticketvendingapplication.adapter.CityStationAdapter;
import com.grandsea.ticketvendingapplication.base.BaseActivity;
import com.grandsea.ticketvendingapplication.constant.UrlConstant;
import com.grandsea.ticketvendingapplication.model.bean.City;
import com.grandsea.ticketvendingapplication.model.bean.District;
import com.grandsea.ticketvendingapplication.model.bean.LocationInfo;
import com.grandsea.ticketvendingapplication.model.bean.Station;
import com.grandsea.ticketvendingapplication.model.common.GsonObject;
import com.grandsea.ticketvendingapplication.util.BasicUtil;
import com.grandsea.ticketvendingapplication.util.DateUtil;
import com.grandsea.ticketvendingapplication.util.DialogManager;
import com.grandsea.ticketvendingapplication.view.CustomButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.MediaType;

import static com.grandsea.ticketvendingapplication.adapter.GsonAdapter.gson;

/**
 * Created by Grandsea09 on 2017/10/17.
 */

public class ChangeDepartStationActivity extends BaseActivity implements View.OnClickListener {
    private static final String HOT_CITY_TAG = "热门城市";
    private CityAdapter cityAdapter;
    private CityStationAdapter stationAdapter;
    private List<City> cities = new ArrayList<>();
    private ListView listView;
    private CustomButton btBack, btLast, btNext;
    private GridView gridView;
    //mGetOnId  值=-1时，表示获取上车点   值=0时，表示获取所有下车点   值=1时，表示获取对应下车点

    private int currentTop = 0;
    private List<District> listDistrict;
    private Object mDepartCityId;
    private int mGetOnId;
    private String mDepartCityName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_depart_station);

        listView = (ListView) findViewById(R.id.acds_ListView);
        gridView = (GridView) findViewById(R.id.acds_gridView);
        btBack = (CustomButton) findViewById(R.id.acds_btBack);
        btLast = (CustomButton) findViewById(R.id.acds_btLast);
        btNext = (CustomButton) findViewById(R.id.acds_btNext);

        mGetOnId = getApp().getLocationInfo().getGet_on_id();
        mDepartCityName = getApp().getLocationInfo().getDepart_city_name();
        mDepartCityId = getApp().getLocationInfo().getDepart_city_id();

        getCityList();
        btBack.setOnClickListener(this);
        btLast.setOnClickListener(this);
        btNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == btBack) {
            finish();
        } else if (v == btLast) {
            if (null == listView)
                return;
            if (null == listDistrict || listDistrict.size() < 1) {
                toast("没有上一个了");
                return;
            }
            if (currentTop > 0) {
                currentTop--;
                listView.setSelection(currentTop);
            } else {
                toast("当前是第一个");
            }

        } else if (v == btNext) {

            if (null == listView)
                return;
            if (null == listDistrict || listDistrict.size() < 1) {
                toast("没有下一个了");
                return;
            }
            if (currentTop < listDistrict.size() - 1) {
                currentTop++;
                listView.setSelection(currentTop);
            } else {
                toast("当前是最后一个");
            }
        }
    }

    private void getCityList() {
        if (cityAdapter == null) {//cityAdapterOld
            OkHttpUtils
                    .postString()
//                    .url(UrlConstant.AIRPORT_LIST)
                    .url(UrlConstant.AIRPORT_CITIES)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(new Gson().toJson(BasicUtil.buildPostData(this,new HashMap())))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int i) {
                            toast("网络异常，请重新刷新");
                            e.printStackTrace();
                        }
                        @Override
                        public void onResponse(String str, final int i) {
                            //{"result":{"func":"airport_list","status":1},"airports":[{"cityName":"珠海","cityId":1,"id":73,"name":"珠海机场"}]}
                            GsonObject dataGson = new GsonObject(gson.fromJson(str, JsonObject.class)); //对数据转换成json后进行对象封装
//                            cities = dataGson.getAsList(gson, "airports", City.class);
                            cities = dataGson.getAsList(gson, "cities", City.class);
                            if (cities==null||cities.size()<1){
                                toast("暂无城市列表");
                                return;
                            }
                            cityAdapter = new CityAdapter(cities);
                            gridView.setAdapter(cityAdapter);
                            /*getDefaultStationList(DefaultConfig.DEPART_CITY_ID, cities.get(3).getId(), 0);*/
                            getStationList(cities.get(0));
                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    /*Intent intent = new Intent(DestCityActivity.this,DestStationActivity.class);
                                    intent.putExtra(IntentKey.INTENT_DEPART_CITY_ID, DefaultConfig.DEPART_CITY_ID);
                                    intent.putExtra(IntentKey.INTENT_DEST_CITY,cities.get(position));
                                    startActivity(intent);*/
                                    cityAdapter.itemPosition(position);
                                    getStationList(cities.get(position));
                                }
                            });
                        }
                    });
        } else {
            cityAdapter.notifyDataSetChanged();
        }
    }

    /**
     * @param city   站点信息
     */
    private void getStationList(final City city) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("type", 1);
        paramMap.put("departCityId", mDepartCityId);//
        paramMap.put("destCityId", city.getId());
        paramMap.put("date", DateUtil.formatYYYYMMDD(new Date())); //默认今天
        paramMap.put("getOnId", 0);// mGetOnId //值=-1时，表示获取上车点   值=0时，表示获取所有下车点   值=1/具体指时，表示获取对应下车点
        String requestContent = new Gson().toJson(BasicUtil.buildPostData(this,paramMap));
        OkHttpUtils
                .postString()
//                    .url("http://192.168.2.23:9092/sfc-search/station_list")
                .url(UrlConstant.STATION_LIST)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(requestContent)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        logI("onError"+e.toString());
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(String s, final int i) {
                        GsonObject dataGson = new GsonObject(gson.fromJson(s, JsonObject.class)); //对数据转换成json后进行对象封装
                        List<Station> stationList = dataGson.getAsList(gson, "stations", Station.class);
                        listDistrict = handleStationDate(stationList);
                        if (listDistrict == null || listDistrict.size() == 0) {
                            toast("当前城市已没有出发站点");
                            DialogManager.dialogNoStation(ChangeDepartStationActivity.this,"当前城市已没有出发站点\n你可以选择其他城市",null);
                            //return;
                        }
                        stationAdapter = new CityStationAdapter(listDistrict);
                        listView.setAdapter(stationAdapter);
                        stationAdapter.setOnItemClickListener(new CityStationAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int districtPosition, int position) { //区域的位置+站点的位置
                                //数据传输给下一个页面
                                District district = listDistrict.get(districtPosition);
                                Station station = district.getStations().get(position);
                                Intent intent = new Intent(ChangeDepartStationActivity.this, DestCityActivity.class);
                                logI("**************START*****点击了站点：***************" + station.getId() + " " + station.getName());
                                LocationInfo locationInfo = new LocationInfo();
                                locationInfo.setGet_on_id(station.getId());
                                locationInfo.setDepart_station_name(station.getName());
                                locationInfo.setDepart_city_id(city.getId());
                                locationInfo.setDepart_city_name(city.getName());
                                getApp().setLocationInfo(locationInfo);
//                                PostShiftParams shiftParams = new PostShiftParams();
//                                shiftParams.setDepartCityId(g);//设置出发点城市id
//                                shiftParams.setDepartCity(mDepartCityName);
//                                shiftParams.setDestCityId(destCity.getId());//设置终点车市id
//                                shiftParams.setDestCity(destCity.getName());
//                                shiftParams.setDepartDate(DateUtil.formatYYYYMMDD(new Date())); //TODO 默认今天，待修改
//                                shiftParams.setGetOnId(mGetOnId);//设置出发站点id (暂时为默认)
//                                //值=-1时，表示获取上车点   值=0时，表示获取所有车次   具体值时，表示获取对应具体出发站点的下车点
//                                shiftParams.setGetOnStation("珠海机场");//设置出发站点 (暂时为默认)      //TODO 待修改位置
//
//                                shiftParams.setTakeOffId(station.getId());//设置下车点id
//                                shiftParams.setTakeOffStation(station.getName());////设置下车点
//                                intent.putExtra(IntentKey.SHIFT_OBJ, shiftParams);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                });

    }


    //处理数据：分区域的站点
    private List<District> handleStationDate(List<Station> stationList) {
        List<District> dataList = new ArrayList<>();
        Map<String, District> districtMap = new HashMap<>();         //头部信息
        Map<String, List<Station>> stationMap = new HashMap<>();     //中间展示数据
        if (stationList != null && stationList.size() > 0) {
            for (Station station : stationList) {
                List<Station> stationListIntoMap = null;
                String key = station.getDistrict();
                if (key == null) {
                    key = HOT_CITY_TAG;
                }
                if (stationMap.containsKey(key)) {
                    stationListIntoMap = stationMap.get(key);
                    stationListIntoMap.add(station);
                } else {
                    stationListIntoMap = new ArrayList<Station>();
                    stationListIntoMap.add(station);
                    stationMap.put(key, stationListIntoMap);
                }
                District district = districtMap.get(key);
                if (district == null) {
                    district = new District();
                    district.setName(key);
                    districtMap.put(key, district);
                }
            }
        }

        //组装新的list集合 //热门城市
        logI("**************START********************");
        if (districtMap.get(HOT_CITY_TAG) != null) {
            District district = districtMap.get(HOT_CITY_TAG);
            district.setStations(stationMap.get(HOT_CITY_TAG));
            dataList.add(district);
        }
        Set<String> keySet = stationMap.keySet();
        for (String key : keySet) {
            if (!HOT_CITY_TAG.equals(key)) {
                District district = districtMap.get(key);
                district.setStations(stationMap.get(key));
                dataList.add(district);
            }
        }
        logI("**************END********************");
        return dataList;
    }
}
