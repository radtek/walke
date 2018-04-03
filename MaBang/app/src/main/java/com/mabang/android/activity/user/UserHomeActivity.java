package com.mabang.android.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.mabang.android.R;
import com.mabang.android.activity.base.MapActivity;
import com.mabang.android.adapter.HomeAdListAdapter;
import com.mabang.android.adapter.HomeAdTypeListAdapter;
import com.mabang.android.config.Api;
import com.mabang.android.config.Constants;
import com.mabang.android.entity.vo.AddressInfo;
import com.mabang.android.entity.vo.AreaInfo;
import com.mabang.android.entity.vo.BillboardInfo;
import com.mabang.android.entity.vo.Message;
import com.mabang.android.entity.vo.SearchInfo;
import com.mabang.android.okhttp.HttpReuqest;
import com.mabang.android.utils.BDMapUtils;
import com.mabang.android.utils.DialogManager;
import com.mabang.android.utils.MapUtils;
import com.mabang.android.utils.PopupWindowManager;
import com.mabang.android.widget.DropDownAlertDialog;
import com.mabang.android.widget.HomeUserTopView;
import com.mabang.android.widget.MyEditText;

import java.util.ArrayList;
import java.util.List;

import walke.base.tool.NetWorkUtil;
import walke.base.widget.ImageTextView;

/**
 * Created by walke on 2018/2/3.
 * email:1032458982@qq.com
 * 查询请求 区号一定要传
 * ①默认进来传经纬度+(地图左上角经纬度、地图右下角经纬度
 * ②地址选择弹窗，传四级联动地址id dialogSearchRequest
 * ③用户输入信息(模糊地址)，传区号id，
 */

public class UserHomeActivity extends MapActivity implements HomeUserTopView.HomeTopViewClickListener, RadioGroup.OnCheckedChangeListener, DropDownAlertDialog.OnDropDownAlertDialogEvent, DropDownAlertDialog.OnAlertDialogEvent {
    private HomeUserTopView mTopView;
    private ImageTextView itvExit, itvPreselect;
    private RadioGroup mRadioGroup;
    private ListView lvAds;

    private SearchInfo mSearchInfo;
    private List<BillboardInfo> mBillboardInfoList;
    private ImageView lvAdMenu, ivLocation;

    private DropDownAlertDialog changeAddressDialog;


    /**
     * ①默认进来传经纬度+(地图左上角经纬度、地图右下角经纬度
     */
    protected static final int SEARCH_WAY_LOCATION = 0x00;
    /**
     * ②地址选择弹窗，传四级联动地址id dialogSearchRequest
     */
    protected static final int SEARCH_WAY_DIALOG = 0x01;
    /**
     * ③用户输入信息(模糊地址)，传区号id，
     */
    protected static final int SEARCH_WAY_INPUT = 0x02;


    protected int last_search_way = SEARCH_WAY_LOCATION;
    private AreaInfo infoProvince, infoCity, infoZone, infoStreet;
    private String mInputAddress;

    private RadioButton rbCanBooking, rbMineBooked, rbMineUsing, rbUsed;
    private TextView tvEmptyView;
    private LinearLayout lvAdLayout;
    private MyEditText etSearch;
    private TextView tvCity;
    /**
     * 点击搜索时能否根据输入文本获取经纬度
     */
    private boolean inputTextGetLatLng;
    private HomeAdListAdapter mAdapter;
    private List<BillboardInfo> mTypeList;
    private SearchInfo mDefaultSearchInfo;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_home_user;
    }

    @Override
    protected void initView() {
        mTopView = ((HomeUserTopView) findViewById(R.id.homeUser_topView));
        mTopView.setHomeTopViewClickListener(this);
        etSearch = mTopView.getEtSearch();
        tvCity = mTopView.getTvCity();
        itvExit = ((ImageTextView) findViewById(R.id.homeUser_exit));
        itvPreselect = ((ImageTextView) findViewById(R.id.homeUser_preselect));
        mRadioGroup = ((RadioGroup) findViewById(R.id.homeUser_RadioGroup));
        rbCanBooking = ((RadioButton) findViewById(R.id.home_rbCanBooking));
        rbMineBooked = ((RadioButton) findViewById(R.id.home_rbMineBooked));
        rbMineUsing = ((RadioButton) findViewById(R.id.home_rbMineUsing));
        rbUsed = ((RadioButton) findViewById(R.id.home_rbUsed));
        lvAds = ((ListView) findViewById(R.id.homeUser_lvAdPosition));
        lvAdMenu = ((ImageView) findViewById(R.id.homeUser_menu));
        ivLocation = ((ImageView) findViewById(R.id.homeUser_ivLocation));
        tvEmptyView = ((TextView) findViewById(R.id.homeUser_lvAdEmptyView));
        lvAdLayout = ((LinearLayout) findViewById(R.id.homeUser_lvAdLayout));

        mRadioGroup.setOnCheckedChangeListener(this);
        itvExit.setOnClickListener(this);
        itvPreselect.setOnClickListener(this);
        lvAdMenu.setOnClickListener(this);
        ivLocation.setOnClickListener(this);

        lvAds.setEmptyView(tvEmptyView);

        lvAds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etSearch.clearFocus();
                if (mBillboardInfoList != null && mBillboardInfoList.size() > 0) {//当mBillboardInfoList有正常的多个数据是
                    if (position <= mBillboardInfoList.size() - 1) {
                        final BillboardInfo info = mBillboardInfoList.get(position);
                        LatLng latLng = getLatLng(info);
                        if (latLng != null) {//当被点击的有坐标
                            moveToNewCenter(latLng);
                            if (mTypeList == null)
                                initOverlay(mSearchInfo, info);
                            else
                                initTypeOverlay(mDefaultSearchInfo, mTypeList, info);
                        }
                    }
                }
            }
        });

        // 地图初始化
        initMapView((MapView) findViewById(R.id.homeUser_mapView));

    }

    @Override
    protected void initData() {

        etSearch.setMyFocusChangeListener(new MyEditText.MyFocusChangeListener() {
            @Override
            public void focusChange(View v, boolean hasFocus) {
                logI("etSearch  焦点动态： hasFocus = " + hasFocus);
                String etText = etSearch.getText().toString().trim();
                if (hasFocus) {//获取焦点
                    // 搜索输入框里的值是定位地址时，获得焦点时直接清空，是其他地址就不清空
                    if (!TextUtils.isEmpty(etText)) {
                        if (etText.equals(locationAddress)) {
                            etSearch.setText("");
                            etSearch.setHint("");
                            etSearch.setHint("请输入地址");
                            etSearch.setHintTextColor(Color.parseColor("#cccccc"));
                        } else {
                            //光标移动到最后
                            etSearch.setSelection(etText.length());
                        }
                    } else {
                        etSearch.setHint("");
                        etSearch.setHint("请输入地址");
                        etSearch.setHintTextColor(Color.parseColor("#cccccc"));
                    }
                } else {//失去焦点
                    logI("etSearch  失去焦点动态： hasFocus = " + hasFocus);
                    if (TextUtils.isEmpty(etText)) {
                        etSearch.setHint("");
                        etSearch.setHint(locationAddress);
                        etSearch.setHintTextColor(Color.parseColor("#000000"));
                    }
                }
            }
        });

    }


    @Override
    protected void enterFirstData(SearchInfo result) {
        super.enterFirstData(result);
        mDefaultSearchInfo = result;
        setSearchInfoData(result);
    }

    @Override
    protected void mapMarkerClick(Marker marker, final BillboardInfo markerInfo) {
        etSearch.clearFocus();
        if (mTypeList == null)
            initOverlay(mSearchInfo, markerInfo);
        else
            initTypeOverlay(mDefaultSearchInfo, mTypeList, markerInfo);
        setMapCenter(marker.getPosition(), new LocateDoneListener() {
            @Override
            public void done() {
                DialogManager.dialogBillboardInfoOneButton(UserHomeActivity.this, markerInfo, "详情", new DialogManager.OneButtonClickListener() {
                    @Override
                    public void onClicked(BillboardInfo billboardInfo) {
                        Intent intent = new Intent(getApplicationContext(), AdDetailsActivity.class);
                        intent.putExtra(Constants.BILLBOARD_INFO, billboardInfo);
                        startActivity(intent);
                    }
                });
            }
        });

    }

    @Override
    public void onCityClick() {
        etSearch.clearFocus();
        if (this.changeAddressDialog != null) {
            String chooseText = this.changeAddressDialog.getChooseText();
            if (!TextUtils.isEmpty(chooseText)) {//没加载到数据
                this.changeAddressDialog.show();
            } else {
                this.changeAddressDialog = new DropDownAlertDialog(this);
                this.changeAddressDialog.setOnItemChangeEvent(this);
                this.changeAddressDialog.setOnAlertDialogEvent(this);
                this.changeAddressDialog.show();
                this.loadAddressInfo(-1);
            }
        } else {
            this.changeAddressDialog = new DropDownAlertDialog(this);
            this.changeAddressDialog.setOnItemChangeEvent(this);
            this.changeAddressDialog.setOnAlertDialogEvent(this);
            this.changeAddressDialog.show();
            this.loadAddressInfo(-1);
        }
        return;
    }

    @Override
    public void onSearchClick() {//搜索按钮点击事件
        etSearch.clearFocus();
        resetRadioButton();
        mInputAddress = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(mInputAddress) || mInputAddress.equals(locationAddress)) {
            last_search_way = SEARCH_WAY_LOCATION;//
            /** 查询 ①默认进来传经纬度+(地图左上角经纬度、地图右下角经纬度 */
            resetLocation();
        } else {/** 查询 ③用户输入信息(模糊地址)，传区号id， */
            //①先通过文本信息获取经纬度然后
            LatLng latLngBystr = getLatLngBystrAndMoveTo(mInputAddress);
            if (latLngBystr == null) {
                inputTextGetLatLng = false;
//                toast("获取对应地理位置失败,请重试");
                //todo 加没取成功就直接用文本请求服务器查？
                inputSearchRequest(mInputAddress, locationZoneCode);//③再请求模糊查询
                return;
            }
            inputTextGetLatLng = true;
            GeoCoder mSearch = GeoCoder.newInstance();//②用GeoCoder获取区号
            mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                @Override
                public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                }

                @Override
                public void onGetReverseGeoCodeResult(ReverseGeoCodeResult geoCodeResult) {
                    locationZoneCode = geoCodeResult.getAdcode();
                    inputSearchRequest(mInputAddress, locationZoneCode);//③再请求模糊查询
                }
            });
            if (!NetWorkUtil.isNetworkConnected(UserHomeActivity.this)) {
                toast("网络连接不可用");
            } else {
                //
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLngBystr));
            }

            BDMapUtils.getCityName(latLngBystr, new BDMapUtils.BDGetCityListener() {
                @Override
                public void success(String cityName) {
                    logI("BDMapUtils api cityName = " + cityName);
                }

                @Override
                public void fail(Exception exc) {
                    logI("BDMapUtils api exc = " + exc);
                }
            });

            String cityName = BDMapUtils.getCityName(this, latLngBystr);
            logI(" BDMapUtils.getCityName city=" + cityName);
            if (tvCity != null && !TextUtils.isEmpty(cityName)) {
                if (cityName.endsWith("市")) {
                    cityName = cityName.substring(0, cityName.length() - 1);
                }
                tvCity.setText(cityName);
            } else {
//                toast("获取对应地理位置失败,请重试");
            }

        }
    }

    /**
     * ③用户输入信息(模糊地址)，传区号id，
     */

    private void inputSearchRequest(final String etAddress, int zoneId) {
        SearchInfo searchInfo = new SearchInfo();
        // TODO: 2018/2/14 设置对应参数
        searchInfo.setAccount(getMemberInfo().getAccount());
        searchInfo.setToken(getMemberInfo().getToken());
        if (zoneId == 0) {
            toast("无法准确获取当前位置，请到网络较好的地方重试");
            return;
        }
        searchInfo.setAddress(etAddress + "");
        searchInfo.setZoneId(zoneId);
        last_search_way = SEARCH_WAY_INPUT;
        logI("inputSearchRequest 用户输入信息(模糊地址) address=" + etAddress);
        httpReuqest.sendMessage(Api.User_search, searchInfo, true, new HttpReuqest.CallBack<SearchInfo>() {
            @Override
            public void onSuccess(Message message, SearchInfo result) {
                setInputOrDialogSearchResult(result);
            }

            @Override
            public void onError(Exception exc) {
                logI(exc + "");
            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        try {
            if (etSearch != null)
                etSearch.removeFocus();
            switch (checkedId) {
                case R.id.home_rbCanBooking://可预订
                    if (rbCanBooking.isChecked())
                        searchRequestType(SearchInfo.SearchType.CAN_BESPEAK);
                    break;
                case R.id.home_rbMineBooked://
                    if (rbMineBooked.isChecked())
                        searchRequestType(SearchInfo.SearchType.MY_BESPEAK);
                    break;
                case R.id.home_rbMineUsing://我占用的
                    boolean checked = rbMineUsing.isChecked();
                    if (checked)
                        searchRequestType(SearchInfo.SearchType.MY_OCCUPY);
                    break;
                case R.id.home_rbUsed://已占用
//                rbUsed.setChecked(true);
//                mRadioGroup.check(checkedId);
                    if (rbUsed.isChecked())
                        searchRequestType(SearchInfo.SearchType.OCCUPY);
                    break;
            }

        } catch (Exception exc) {
            logE(exc.getMessage());
        }


    }

    /**
     * 筛选查询请求 区号一定要传
     * 默认进来传经纬度+(地图左上角经纬度、地图右下角经纬度
     * ①地址选择弹窗，传四级联动地址id
     * ②输入用户信息(模糊地址)，传区号id，
     */
    private void searchRequestType(final SearchInfo.SearchType searchType) {
        SearchInfo searchInfo = new SearchInfo();
        // TODO: 2018/2/14 设置对应参数
        searchInfo.setAccount(getMemberInfo().getAccount());
        searchInfo.setToken(getMemberInfo().getToken());
        searchInfo.setSearchType(searchType);
        if (last_search_way == SEARCH_WAY_DIALOG) {
            logI("searchRequestType 地址栏查询：" + searchType.getText());
            if (infoProvince != null)
                searchInfo.setProvinceId(infoProvince.getAreaId());
            if (infoProvince != null)
                searchInfo.setCityId(infoCity.getAreaId());
            if (infoProvince != null)
                searchInfo.setZoneId(infoZone.getAreaId());
            if (infoProvince != null)
                searchInfo.setStreetId(infoStreet.getAreaId());
        } else if (last_search_way == SEARCH_WAY_INPUT) {
            if (locationZoneCode == 0) {
                toast("无法准确获取当前位置，请到网络较好的地方重试");
                return;
            }
            logI("searchRequestType 用户输入信息(模糊地址)：" + searchType.getText());
            searchInfo.setAddress(mInputAddress + "");
            searchInfo.setZoneId(locationZoneCode);
        } else {
            logI("searchRequestType 初始进入查询mStartPoint+mEndPoint：" + searchType.getText());
            if (mStartPoint != null && mEndPoint != null) {
                searchInfo.setStartPoint(mStartPoint);
                searchInfo.setEndPoint(mEndPoint);
            } else {
                toast("无法准确获取当前位置，请到网络较好的地方重试");
                return;
            }
        }
        httpReuqest.sendMessage(Api.User_search, searchInfo, true, new HttpReuqest.CallBack<SearchInfo>() {
            @Override
            public void onSuccess(Message message, SearchInfo result) {
                if (Api.OK == result.getCode()) {
                    String text = result.getText();
                    logI(text + "");
                    // TODO: 2018/2/16 获取相应数据做对应显示
                    if (searchType.equals(SearchInfo.SearchType.CAN_BESPEAK))
                        setSearchTypeInfoData(result);
                    else
                        setSearchInfoData(result);

                    List<BillboardInfo> biList = result.getBillboardInfoList();
                    if (biList != null && biList.size() > 0) {//当有广告位列表时遍历列表，若其中一个广告位带经纬度就移动到该位置
                        for (BillboardInfo billboardInfo : biList) {
                            LatLng latLng = getLatLng(billboardInfo);
                            if (latLng != null) {
                                moveToNewCenter(latLng);
                                break;
                            }
                        }
                    }

                }

            }

            @Override
            public void onError(Exception exc) {
                logI(exc + "");
            }

            @Override
            public void onFinish() {

            }
        });
    }

    private void setSearchInfoData(SearchInfo result) {
        mSearchInfo = result;
        //广告位列表
        mBillboardInfoList = result.getBillboardInfoList();

        mAdapter = null;//先回收
        mTypeList = null;//重置

        if (mBillboardInfoList == null) {
            mAdapter = new HomeAdListAdapter(new ArrayList<BillboardInfo>());
            lvAds.setAdapter(mAdapter);
        } else {
            mAdapter = new HomeAdListAdapter(mBillboardInfoList);
            lvAds.setAdapter(mAdapter);
            initOverlay(result);
            for (BillboardInfo billboardInfo : mBillboardInfoList) {
                logI("billboardInfo = " + billboardInfo);
            }
        }

    }

    /**
     * 条件搜索返回处理
     *
     * @param typeResult
     */
    private void setSearchTypeInfoData(SearchInfo typeResult) {
        if (mSearchInfo == null)
            return;
        //广告位列表
        mBillboardInfoList = mDefaultSearchInfo.getBillboardInfoList();
        mTypeList = typeResult.getBillboardInfoList();
        if (mTypeList == null || mTypeList.size() < 1) {
            toast("筛选没有结果");
            if (mBillboardInfoList == null)//mBillboardInfoList 直接new一个size=0的
//                mAdapter = new HomeAdTypeListAdapter(mBillboardInfoList, mTypeList);
                mAdapter = new HomeAdTypeListAdapter(new ArrayList<BillboardInfo>(), mTypeList);
            else {
                mAdapter = new HomeAdTypeListAdapter(mBillboardInfoList, mTypeList);
                initTypeOverlay(mDefaultSearchInfo, mTypeList);
            }
            lvAds.setAdapter(mAdapter);
            return;
        }

        mAdapter = null;//先回收

        if (mBillboardInfoList == null) {//mBillboardInfoList 直接new一个size=0的
            mAdapter = new HomeAdTypeListAdapter(new ArrayList<BillboardInfo>(), null);
            lvAds.setAdapter(mAdapter);
        } else {
            mAdapter = new HomeAdTypeListAdapter(mBillboardInfoList, mTypeList);
            lvAds.setAdapter(mAdapter);
            initTypeOverlay(mDefaultSearchInfo, mTypeList);
            for (BillboardInfo billboardInfo : mTypeList) {
                logI("mTypeList: billboardInfo = " + billboardInfo.toString());
            }

        }

    }

    /**
     * @param address  定位成功后回调
     * @param cityName
     */
    @Override
    protected void locationAddress(String address, String cityName) {
        super.locationAddress(address, cityName);
        if (mTopView != null && !TextUtils.isEmpty(address)) {
            etSearch.setText("");
            etSearch.setHint(address);
            etSearch.setHintTextColor(Color.parseColor("#000000"));
//            this.etSearch.setText(address);
//            this.etSearch.setSelection(address.length());
        }
        if (tvCity != null && !TextUtils.isEmpty(cityName)) {
            if (cityName.endsWith("市")) {
                cityName = cityName.substring(0, cityName.length() - 1);
            }
            tvCity.setText(cityName);
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.homeUser_exit://退出
                etSearch.clearFocus();
                PopupWindowManager.showExitTips(this, itvExit, Api.User_Exit);
                break;
            case R.id.homeUser_preselect://预选
                etSearch.clearFocus();
                Intent intent = new Intent(this, AdPreselectActivity.class);
                startActivity(intent);
                break;
            case R.id.homeUser_menu://底部列表上的图片
                etSearch.clearFocus();
                if (lvAdLayout.getVisibility() == View.VISIBLE) {
                    lvAdLayout.setVisibility(View.GONE);
                } else {
                    lvAdLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.homeUser_ivLocation://地位图标
                last_search_way = SEARCH_WAY_LOCATION;//
                etSearch.clearFocus();
                resetRadioButton();
                //重新地位
                /** 查询 ①默认进来传经纬度+(地图左上角经纬度、地图右下角经纬度 */
                resetLocation();
                break;
        }
    }

    /**
     * 原来清除选中状态后最后一个点击
     */
    private void resetRadioButton() {
//        rbCanBooking.setChecked(false);
//        rbMineUsing.setChecked(false);
//        rbMineBooked.setChecked(false);
//        rbUsed.setChecked(false);
//        rbCanBooking.clearFocus();
//        rbMineBooked.clearFocus();
//        rbMineUsing.clearFocus();
//        rbUsed.clearFocus();
        mRadioGroup.clearCheck();//解决、有效
    }

    /**
     * @param panelIndex 加载地址信息
     */
    public void loadAddressInfo(final int panelIndex) {
        this.changeAddressDialog.showLoad();
        AreaInfo provinceInfo = null;
        AreaInfo cityInfo = null;
        AreaInfo zoneInfo = null;
        if (panelIndex > -1) {
            provinceInfo = this.changeAddressDialog.provincePanel.getDropDownItemInfo();
            if (panelIndex > 0) {
                cityInfo = this.changeAddressDialog.cityPanel.getDropDownItemInfo();
                if (panelIndex > 1)
                    zoneInfo = this.changeAddressDialog.zonePanel.getDropDownItemInfo();
            }
        }
        AddressInfo addressInfo = new AddressInfo();
        // TODO: 2018/2/14 设置对应参数
        addressInfo.setAccount(getMemberInfo().getAccount());
        addressInfo.setToken(getMemberInfo().getToken());
        if (provinceInfo != null)
            addressInfo.setProvinceId(provinceInfo.getAreaId());
        if (cityInfo != null)
            addressInfo.setCityId(cityInfo.getAreaId());
        if (zoneInfo != null)
            addressInfo.setZoneId(zoneInfo.getAreaId());

        this.httpReuqest.sendMessage(Api.User_address, addressInfo, false, new HttpReuqest.CallBack<AddressInfo>() {
            @Override
            public void onSuccess(Message message, AddressInfo result) {
                if (result == null)
                    return;
                if (Api.OK == result.getCode()) {
                    List<AreaInfo> provinceList = result.getProvinceList(); // 省份列表--全部
                    List<AreaInfo> cityList = result.getCityList(); // 城市列表--省份ID下的所有城市
                    List<AreaInfo> zoneList = result.getZoneList(); // 区域列表--城市ID下的所有区域
                    List<AreaInfo> streetList = result.getStreetList(); // 街道列表--区域下的所有街道（注：有些区域没有街道数据）

                    UserHomeActivity.this.changeAddressDialog.provincePanel.append(provinceList, result.getProvinceId());
                    if (panelIndex == -1) {
                        UserHomeActivity.this.changeAddressDialog.provincePanel.scrollActive();
                    }

                    UserHomeActivity.this.changeAddressDialog.cityPanel.append(cityList, result.getCityId());
                    if (panelIndex <= 1) {
                        UserHomeActivity.this.changeAddressDialog.cityPanel.scrollActive();
                    }

                    UserHomeActivity.this.changeAddressDialog.zonePanel.append(zoneList, result.getZoneId());
                    if (panelIndex <= 2) {
                        UserHomeActivity.this.changeAddressDialog.zonePanel.scrollActive();
                    }

                    if (streetList != null && streetList.size() > 0) {
                        UserHomeActivity.this.changeAddressDialog.streetPanel.setVisibility(View.VISIBLE);
                        UserHomeActivity.this.changeAddressDialog.streetPanel.append(streetList, result.getStreetId());
                        UserHomeActivity.this.changeAddressDialog.streetPanel.scrollActive();
                    } else
                        UserHomeActivity.this.changeAddressDialog.streetPanel.setVisibility(View.GONE);
                    UserHomeActivity.this.changeAddressDialog.changeSize();
                }
                return;
            }

            @Override
            public void onError(Exception exc) {
                logI(exc + "");
            }

            @Override
            public void onFinish() {
                UserHomeActivity.this.changeAddressDialog.hideLoad();
                return;
            }
        });

        return;
    }

    /**
     * @param dialog 地址下拉框选择事件
     * @param panel
     * @param item
     */
    @Override
    public void onItemChange(DropDownAlertDialog dialog, DropDownAlertDialog.DropDownAlertDialogPanel panel, DropDownAlertDialog.DropDownAlertDialogItem item) {
        if (dialog == this.changeAddressDialog && panel.getIndex() < 3) {
            this.loadAddressInfo(panel.getIndex());
        }
        return;
    }

    /**
     * 选择下拉框子项确定事件
     */
    @Override
    public void onEnter(DropDownAlertDialog dialog) {
        if (dialog == this.changeAddressDialog) {
            this.changeAddressDialog.cancel();
            String text = this.changeAddressDialog.getChooseText();
            if (!TextUtils.isEmpty(text)) {
//                this.etSearch.setHint(text);
                etSearch.setHint("");
                etSearch.setHint("请输入地址");
                etSearch.setHintTextColor(Color.parseColor("#cccccc"));//先重置hint

                this.etSearch.setText(text);//再查询②中用到
                this.etSearch.setSelection(text.length());
            }
            AreaInfo areaInfo = dialog.cityPanel.getDropDownItemInfo();//当断网选择会为null
            if (areaInfo != null) {
                String cityName = areaInfo.getAreaName();
                locationZoneCode = dialog.zonePanel.getDropDownItemInfo().getAreaId();
                if (!TextUtils.isEmpty(cityName)) {
                    if (cityName.endsWith("市")) {
                        cityName = cityName.substring(0, cityName.length() - 1);
                    }
                    tvCity.setText(cityName);
                }
                logI("locationZoneCode = " + locationZoneCode + " -----cityName = " + cityName);
                resetRadioButton();
                /** 查询 ② 地址选择弹窗，传四级联动地址id dialogSearchRequest*/
                dialogSearchRequest(this.changeAddressDialog);
            }
        }
        return;
    }

    /**
     * ② 地址选择弹窗，传四级联动地址id dialogSearchRequest
     */
    private void dialogSearchRequest(DropDownAlertDialog addressDialog) {
        if (addressDialog == null) {
            toast("地址选择异常，请重试");
            return;
        }
        SearchInfo searchInfo = new SearchInfo();
        // TODO: 2018/2/14 设置对应参数
        searchInfo.setAccount(getMemberInfo().getAccount());
        searchInfo.setToken(getMemberInfo().getToken());
        infoProvince = addressDialog.provincePanel.getDropDownItemInfo();
        infoCity = addressDialog.cityPanel.getDropDownItemInfo();
        infoZone = addressDialog.zonePanel.getDropDownItemInfo();
        infoStreet = addressDialog.streetPanel.getDropDownItemInfo();
        if (infoProvince != null)
            searchInfo.setProvinceId(infoProvince.getAreaId());
        if (infoCity != null)
            searchInfo.setCityId(infoCity.getAreaId());
        if (infoZone != null)
            searchInfo.setZoneId(infoZone.getAreaId());
        if (infoStreet != null)
            searchInfo.setStreetId(infoStreet.getAreaId());
        logI("dialogSearchRequest 地址栏选择后查询:");
        last_search_way = SEARCH_WAY_DIALOG;
        httpReuqest.sendMessage(Api.User_search, searchInfo, true, new HttpReuqest.CallBack<SearchInfo>() {
            @Override
            public void onSuccess(Message message, SearchInfo result) {
                // TODO: 2018/2/16 获取相应数据做对应显示
                setInputOrDialogSearchResult(result);
            }

            @Override
            public void onError(Exception exc) {
                logI(exc + "");
            }

            @Override
            public void onFinish() {

            }
        });
    }

    private void setInputOrDialogSearchResult(SearchInfo result) {
        mDefaultSearchInfo = result;//搜索返回
        if (Api.OK == result.getCode()) {

            String text = result.getText();
            logI(text + "");
            // TODO: 2018/2/16 获取相应数据做对应显示
            setSearchInfoData(result);
            List<BillboardInfo> biList = result.getBillboardInfoList();
            if (biList != null && biList.size() > 0) {//当有广告位列表时遍历列表，//若其中一个广告位带经纬度就移动到该位置

                //遍历广告位列表获取经纬度列表，然后计算距离做自动缩放级别适配
                List<LatLng> latLngList = getLatLngList(result);
                if (latLngList != null && latLngList.size() > 0) {
                    double distance = MapUtils.getDistance(latLngList);
                    LatLng center = MapUtils.getCenter(latLngList);
                    moveToNewCenter(center.latitude, center.longitude);
                    setLevel(distance);
                }

//                for (BillboardInfo billboardInfo : biList) {
//                    LatLng latLng = getLatLng(billboardInfo);
//                    if (latLng != null) {
//                        moveToNewCenter(latLng.latitude, latLng.longitude);
//                        break;
//                    }
//                }
            } else {//可能地址栏选择，或者
                if (last_search_way == SEARCH_WAY_INPUT) {//用户输入
                    if (!inputTextGetLatLng) {// 点击搜索时不能能根据输入文本获取经纬度
                        toast("获取对应地理位置失败,请重试");
                    }

                } else {
                    String addressTrim = etSearch.getText().toString().trim();//可能地址栏选择，或者
                    LatLng latLngBystr = getLatLngBystrAndMoveTo(addressTrim);
                    if (latLngBystr == null) {
                        toast("获取对应地理位置失败,请重试");
                    }
                }
            }

//            List<LatLng> latLngList = getLatLngList(result);
//            if (latLngList != null && latLngList.size() > 0) {
//                double distance = MapUtils.getDistance(latLngList);
//                LatLng center = MapUtils.getCenter(latLngList);
//                moveToNewCenter(center.latitude, center.longitude);
//                setLevel(distance);
//            }

        }
    }

    private List<LatLng> getLatLngList(SearchInfo result) {
        List<LatLng> llList = new ArrayList<>();
        List<BillboardInfo> billboardInfoList = result.getBillboardInfoList();
        if (billboardInfoList != null && billboardInfoList.size() > 0) {
            for (BillboardInfo billboardInfo : billboardInfoList) {
                String lat = billboardInfo.getLocationLat();
                String lng = billboardInfo.getLocationLng();
                if (!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng)) {
                    llList.add(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
                }
            }
        }
        return llList;
    }

}
