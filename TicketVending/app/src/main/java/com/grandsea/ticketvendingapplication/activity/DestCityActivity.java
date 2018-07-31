package com.grandsea.ticketvendingapplication.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.activity.launch.ChangeDepartStationActivity;
import com.grandsea.ticketvendingapplication.adapter.CityAdapter;
import com.grandsea.ticketvendingapplication.adapter.CityStationAdapter;
import com.grandsea.ticketvendingapplication.base.BaseActivity;
import com.grandsea.ticketvendingapplication.constant.IntentKey;
import com.grandsea.ticketvendingapplication.constant.UrlConstant;
import com.grandsea.ticketvendingapplication.model.bean.City;
import com.grandsea.ticketvendingapplication.model.bean.District;
import com.grandsea.ticketvendingapplication.model.bean.PostShiftParams;
import com.grandsea.ticketvendingapplication.model.bean.Station;
import com.grandsea.ticketvendingapplication.model.common.GsonObject;
import com.grandsea.ticketvendingapplication.util.BasicUtil;
import com.grandsea.ticketvendingapplication.util.DateUtil;
import com.grandsea.ticketvendingapplication.util.DialogManager;
import com.grandsea.ticketvendingapplication.view.CustomButton;
import com.grandsea.ticketvendingapplication.view.TitleBar;
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
 * 目标城市 use
 */
public class DestCityActivity extends BaseActivity implements View.OnClickListener {

    //    private CityAdapterOld cityAdapterOld;

    private static final String HOT_CITY_TAG = "热门城市";
    private CityAdapter cityAdapter;
    private CityStationAdapter stationAdapter;
    private List<City> cities = new ArrayList<>();
    private ListView listView;
    private CustomButton btBack, btLast, btNext;
    private GridView gridView;

    //mGetOnId  值=-1时，表示获取上车点   值=0时，表示获取所有下车点   值=1时，表示获取对应下车点
    private int mGetOnId ;  //出发站点id     DefaultConfig.GET_ON_ID
    private String mDepartCityName ;  //出发城市名     DefaultConfig.DEPART_CITY_NAME
    private int mDepartCityId ;///出发城市id      DefaultConfig.DEPART_CITY_ID

    private int currentTop = 0;
    private List<District> listDistrict;
    private TitleBar mTitleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dest_city);

        mTitleBar = (TitleBar) findViewById(R.id.adc_top);

        listView = (ListView) findViewById(R.id.adc_ListView);
        gridView = (GridView) findViewById(R.id.adc_gridView);
        btBack = (CustomButton) findViewById(R.id.adc_btBack);
        btLast = (CustomButton) findViewById(R.id.adc_btLast);
        btNext = (CustomButton) findViewById(R.id.adc_btNext);
        mGetOnId = getApp().getLocationInfo().getGet_on_id();
        mDepartCityName = getApp().getLocationInfo().getDepart_city_name();
        mDepartCityId = getApp().getLocationInfo().getDepart_city_id();
        getCityList();
        btBack.setOnClickListener(this);
        btLast.setOnClickListener(this);
        btNext.setOnClickListener(this);

        setTitleBarTRight(mTitleBar.getTvRight());

        mTitleBar.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogManager.createDialogTwoButton(DestCityActivity.this,
                        "当前出发站点 : "+getApp().getLocationInfo().getDepart_station_name(),//"当前出发站点 : 珠海机场"
                        "如果你需要购买从其他站点出发的车票，请点击确认按钮",
                        "确认需要更换？",
                        new DialogManager.DialogTwoButtonClickListener() {
                            @Override
                            public void leftOnClick(WindowManager.LayoutParams lp, Dialog dialog) {

                            }

                            @Override
                            public void rightOnClick(WindowManager.LayoutParams lp, Dialog dialog) {
                                //授权成功后跳转到选择出发站点界面
                                Intent intent = new Intent(DestCityActivity.this, ChangeDepartStationActivity.class);
                                intent.putExtra(IntentKey.CHANGE_DEPART_STATION,"更换择出发站点：");
                                startActivity(intent);
                                finish();
                            }
                        });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitleBarTRight(mTitleBar.getTvRight());
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
                        public void onResponse(String s, final int i) {
                            GsonObject dataGson = new GsonObject(gson.fromJson(s, JsonObject.class)); //对数据转换成json后进行对象封装
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
     * @param destCity   目标城市
     */
    private void getStationList(final City destCity) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("type", 1);
        paramMap.put("departCityId", mDepartCityId);//
        paramMap.put("destCityId", destCity.getId());
        paramMap.put("date", DateUtil.formatYYYYMMDD(new Date())); //默认今天
        paramMap.put("getOnId", 0);  //值=-1时，表示获取上车点   值=0时，表示获取所有下车点   值=1时，表示获取对应下车点
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
                        logI("----------STATION_LIST-------onError--------->e:"+e.toString() );
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(String s, final int i) {
                        GsonObject dataGson = new GsonObject(gson.fromJson(s, JsonObject.class)); //对数据转换成json后进行对象封装
                        List<Station> stationList = dataGson.getAsList(gson, "stations", Station.class);
                        listDistrict = handleStationDate(stationList);
                        if (listDistrict == null || listDistrict.size() == 0) {
                            toast("当前城市已没有下车站点");
                            DialogManager.dialogNoStation(DestCityActivity.this,"当前城市已没有下车站点\n你可以选择其他城市",null);
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
                                Intent intent = new Intent(DestCityActivity.this, ShiftActivity.class);
                                logI("**************START*****点击了站点：***************" + station.getId() + " " + station.getName());
                                PostShiftParams shiftParams = new PostShiftParams();
                                shiftParams.setDepartCityId(mDepartCityId);//设置出发点城市id
                                shiftParams.setDepartCity(mDepartCityName);
                                shiftParams.setDestCityId(destCity.getId());//设置终点车市id
                                shiftParams.setDestCity(destCity.getName());
                                shiftParams.setDepartDate(DateUtil.formatYYYYMMDD(new Date())); //TODO 默认今天，待修改
                                shiftParams.setGetOnId(mGetOnId);//设置出发站点id (暂时为默认)
                                //值=-1时，表示获取上车点   值=0时，表示获取所有车次   具体值时，表示获取对应具体出发站点的下车点
                                String depart_station_name = getApp().getLocationInfo().getDepart_station_name();
                                shiftParams.setGetOnStation(depart_station_name);//设置出发站点 (暂时为默认:"珠海机场")      //TODO 待修改位置

                                shiftParams.setTakeOffId(station.getId());//设置下车点id
                                shiftParams.setTakeOffStation(station.getName());////设置下车点
                                intent.putExtra(IntentKey.SHIFT_OBJ, shiftParams);
                                startActivity(intent);
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



//---------------------------------------------------------------------------------------------------

/**
 * @param departCityId 出发城市id(即头部,)
 * @param destCityId   目标城市id
 * @param mGetOnId      值=1(或具体出发站)时，表示获取对应下车点(具体出发站点，具有班次的对应下车点)
 */
    /*private void getDefaultStationList(final int departCityId, final int destCityId, final int mGetOnId) {
        if (stationAdapter == null) {
            Map<String, Object> paramMap = new HashMap();
            paramMap.put("type", 1);
            paramMap.put("departCityId", departCityId);//
            paramMap.put("destCityId", destCityId);
            paramMap.put("date", DateUtil.formatYYYYMMDD(new Date())); //默认今天
            paramMap.put("getOnId", mGetOnId);  //值=-1时，表示获取上车点   值=0时，表示获取所有下车点   值=1时，表示获取对应下车点

            String requestContent = new Gson().toJson(BasicUtil.buildPostData("", "", paramMap));
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
                            Toast.makeText(getApplicationContext(), "网络异常，请重新刷新", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(String s, final int i) {

                            GsonObject dataGson = new GsonObject(gson.fromJson(s, JsonObject.class)); //对数据转换成json后进行对象封装
                            List<Station> stationList = dataGson.getAsList(gson, "stations", Station.class);
                            listDistrict = handleStationDate(stationList);
                            if (listDistrict == null || listDistrict.size() == 0) {
                                Toast.makeText(getApplicationContext(), "当前城市已没有下车站点", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            stationAdapter = new CityStationAdapter(listDistrict);
                            listView.setAdapter(stationAdapter);
                            stationAdapter.setOnItemClickListener(new CityStationAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int districtPosition, int position) { //区域的位置+站点的位置
//                                    Toast.makeText(DestStationActivity.this,list.get(districtPosition).getName()+"==>"+list.get(districtPosition).getStations().get(position).getName(),Toast.LENGTH_LONG).show();
                                    //数据传输给下一个页面
                                    District district = listDistrict.get(districtPosition);
                                    Station station = district.getStations().get(position);
                                    Intent intent = new Intent(DestCityActivity.this, ShiftActivity.class);
                                    logI("**************START*****点击了站点：***************" + station.getId() + " " + station.getName());
                                    PostShiftParams shiftParams = new PostShiftParams();
                                    shiftParams.setDepartCityId(departCityId);
                                    shiftParams.setDestCityId(destCityId);
                                    shiftParams.setDepartDate(DateUtil.formatYYYYMMDD(new Date())); //TODO 默认今天，待修改
                                    shiftParams.setGetOnId(mGetOnId);
                                    shiftParams.setGetOnStation("珠海机场");                           //TODO 待修改位置
                                    shiftParams.setTakeOffId(station.getId());
                                    shiftParams.setTakeOffStation(station.getName());
                                    intent.putExtra(IntentKey.SHIFT_OBJ, shiftParams);
                                    startActivity(intent);

                                }
                            });
                        }

                    });
        } else {
            stationAdapter.notifyDataSetChanged();
        }
    }*/