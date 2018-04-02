package com.mabang.android.activity.worker;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
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
import com.mabang.android.config.Api;
import com.mabang.android.entity.vo.BillboardInfo;
import com.mabang.android.entity.vo.Message;
import com.mabang.android.entity.vo.SearchInfo;
import com.mabang.android.okhttp.HttpReuqest;
import com.mabang.android.utils.AppUtils;
import com.mabang.android.utils.DialogManager;
import com.mabang.android.utils.PopupWindowManager;
import com.mabang.android.widget.KeyboardPatch;

import java.util.List;

import walke.base.tool.NetWorkUtil;
import walke.base.widget.DialogUtil;
import walke.base.widget.ImageTextView;

/**
 * Created by walke on 2018/2/3.
 * email:1032458982@qq.com
 */

public class WorkerHomeActivity extends MapActivity {

    private ImageTextView itvExit, itvPreview;
    private Button btSearch;
    private EditText etTop, etBottom;
    private List<BillboardInfo> mBillboardInfoList;
    /**
     * 当前定位的广告位信息，初始为null，赋值情况：①加到到初始数据后，检测数据列表是否有符合的，②广告更新绑定当前定位的请求成功之后
     */
    private BillboardInfo currentLocationBillboard;

    private ImageView ivLocationTop;
    private SearchInfo mSearchInfo;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_home_worker;
    }

    @Override
    protected void initView() {
        AppUtils.adaptiveNdk_StatusBar(this, findViewById(R.id.homeWorker_statusBar));
//        initSearchView(findViewById(R.id.homeWorker_svBottom));
        itvExit = ((ImageTextView) findViewById(R.id.homeWorker_exit));
        itvPreview = ((ImageTextView) findViewById(R.id.homeWorker_preview));

        btSearch = ((Button) findViewById(R.id.homeWorker_btSearch));
        etTop = ((EditText) findViewById(R.id.homeWorker_etTop));
        etBottom = ((EditText) findViewById(R.id.homeWorker_etBottom));
        ivLocationTop = ((ImageView) findViewById(R.id.homeWorker_ivLocationTop));

        itvExit.setOnClickListener(this);
        itvPreview.setOnClickListener(this);
        btSearch.setOnClickListener(this);
        ivLocationTop.setOnClickListener(this);
        itvPreview.getvCover().setVisibility(View.VISIBLE);//显示"预览"蒙层

        // 地图初始化
        initMapView((MapView) findViewById(R.id.homeWorker_mapView));
        //将etTop设为用户不可编辑
        etTop.setEnabled(false);
        etTop.setFocusable(false);
        etTop.clearFocus();
        etTop.setTextColor(Color.BLACK);


    }

    @Override
    protected void initData() {

//        View contentView = View.inflate(this, R.layout.activity_home_worker, null);
        View contentView = findViewById(R.id.homeWorker_linearLayout);
        KeyboardPatch keyboardPatch = new KeyboardPatch(this, contentView);
        keyboardPatch.enable();
    }

    @Override
    protected void enterFirstData(SearchInfo result) {
        super.enterFirstData(result);
        mSearchInfo = result;
        //广告位列表
        mBillboardInfoList = result.getBillboardInfoList();
        if (mBillboardInfoList != null && mBillboardInfoList.size() > 0) {
            for (BillboardInfo billboardInfo : mBillboardInfoList) {
                logI("billboardInfo = "+billboardInfo.toString());
            }
            boolean isExits = checkCurrentLocationBillboard(mBDLocation, mBillboardInfoList);
            if (isExits)
                itvPreview.getvCover().setVisibility(View.GONE);//去掉"预览"蒙层
        }
    }

    @Override
    protected void mapMarkerClick(Marker marker, final BillboardInfo markerInfo) {
        // ②点击地图上的覆盖物，
//        showUploadPhotoDialog(billboardInfo);
       setMapCenter(marker.getPosition(), new LocateDoneListener() {
            @Override
            public void done() {
                showUploadPhotoDialog(markerInfo);
            }
        });

    }

    /**
     * 显示上传图片弹窗，情况：①按照管理码查询后，返回的广告位有经纬度，②点击地图上的覆盖物，③点击”预览“按钮，即当前定位有对应一个广告位
     *
     * @param billboardInfo
     */
    private void showUploadPhotoDialog(final BillboardInfo billboardInfo) {
        if (billboardInfo == null)
            return;
        DialogManager.dialogBaseInfoUploadPhoto(WorkerHomeActivity.this, billboardInfo, new DialogManager.LocationClickListener() {
            @Override
            public void click() {//进行定位校正
                DialogUtil.tipsTwoButton(WorkerHomeActivity.this, "取消", "确定", "确定绑定到当前定位？", new DialogUtil.TwoButtonClickListener() {
                    @Override
                    public void leftOnClick() {
                    }

                    @Override
                    public void rightOnClick() { //定位校正 更新修改广告位经纬度为 当前定位
                        updateCoordinates(billboardInfo);//更新广告位位置坐标
                    }
                });
            }
        });
    }

    @Override
    protected void locationAddress(String address, String city) {
        if (etTop != null)
            etTop.setText(address + "");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.homeWorker_exit://退出
                PopupWindowManager.showExitTips(this, itvExit, Api.Worker_Exit);
                break;
            case R.id.homeWorker_preview://预览刚绑定定位的广告位信息
                boolean unUse = itvPreview.getvCover().getVisibility() == View.VISIBLE;//显示"预览"蒙层
                if (currentLocationBillboard != null && !unUse) {
                    //③点击”预览“按钮，即当前定位有对应一个广告位
                    showUploadPhotoDialog(currentLocationBillboard);
                }
                break;
            case R.id.homeWorker_ivLocationTop://定位当

                final String etTopText = etTop.getText().toString().trim();
                if (TextUtils.isEmpty(etTopText) || etTopText.equals(locationAddress)) {
                    // ①当输入框为null或者与定位地址一样，执行重定位即可
                    resetLocation();
                } else {
                    // ②当输入框有输入与定位地址不一样，   基本不會用到
                    LatLng latLngBystr = getLatLngBystrAndMoveTo(etTopText);
                    if (latLngBystr == null) {
                        toast("获取对应地理位置失败,请重试");
                        return;
                    }
                    GeoCoder mSearch = GeoCoder.newInstance();
                    mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                        @Override
                        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                            Log.i("walke", "onGetGeoCodeResult: geoCodeResult");
                        }

                        @Override
                        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult geoCodeResult) {
                            int adcode = geoCodeResult.getAdcode();
                            requestSearchInfo(etTopText, adcode);
                        }
                    });
                    if (!NetWorkUtil.isNetworkConnected(WorkerHomeActivity.this)) {
                        toast("网络连接不可用");
                        return;
                    }
                    mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLngBystr));
                }
                break;
            case R.id.homeWorker_btSearch://底部查询按钮
                requestBillboardInfo();
                break;
        }
    }

    /**
     * 查询请求 区号一定要传
     * 默认进来传经纬度+(地图左上角经纬度、地图右下角经纬度
     * ①地址选择弹窗，传四级联动地址id
     * ②输入用户信息(模糊地址)，传区号id，
     */
    private void requestSearchInfo(String address, int zoneId) {
        SearchInfo searchInfo = new SearchInfo();
        // TODO: 2018/2/14 设置对应参数
        searchInfo.setAccount(getMemberInfo().getAccount());
        searchInfo.setToken(getMemberInfo().getToken());
        if (zoneId == 0) {
            toast("无法准确获取当前位置，请到网络较好的地方重试");
            return;
        }
        searchInfo.setAddress(address + "");
        searchInfo.setZoneId(zoneId);
        httpReuqest.sendMessage(Api.Worker_search, searchInfo, true, new HttpReuqest.CallBack<SearchInfo>() {
            @Override
            public void onSuccess(Message message, SearchInfo result) {
                String text = result.getText();
                if (Api.OK == result.getCode()) {
                    // TODO: 2018/2/16 获取相应数据做对应显示
                    initWorkerOverlay(result);
                }//
                logI(text + "");
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

    /**
     * 广告位信息 直接按管理码查询
     */
    private void requestBillboardInfo() {
        final BillboardInfo billboardInfo = new BillboardInfo();
        // TODO: 2018/2/14 设置对应参数
        billboardInfo.setAccount(getMemberInfo().getAccount());
        billboardInfo.setToken(getMemberInfo().getToken());
        // TODO: 2018/2/21  替换具体数据
        String trim = etBottom.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            toast("请输入广告位唯一码或管理码");
            return;
        }
        billboardInfo.setManageCode(trim);
        httpReuqest.sendMessage(Api.Worker_billboard, billboardInfo, true, new HttpReuqest.CallBack<BillboardInfo>() {
            @Override
            public void onSuccess(Message message, final BillboardInfo result) {
                String text = result.getText();
                if (Api.OK == result.getCode()) {
                    //可以对输入框清空
                    etBottom.setText("");
                    // TODO: 2018/2/16 获取相应数据做对应显示
                    logI("直接按管理码查询:onSuccess: result = "+result.toString());
                    if (getLatLng(result) != null) {//已绑定经纬度，则显示广告位图片上传弹框（见3.1.7）
                        boolean isCurrentLocationHasBillboard = checkCurrentLocationBillboard(mBDLocation, result);
                        if (isCurrentLocationHasBillboard)
                            itvPreview.getvCover().setVisibility(View.GONE);//去掉"预览"蒙层
                        // ①按照管理码查询后，返回的广告位有经纬度
                        showUploadPhotoDialog(result);
                    } else {
                        // 当查询到的广告位信息没有经纬度
                        DialogManager.dialogBillboardInfoOneButton(WorkerHomeActivity.this, result, "绑定当前定位", new DialogManager.OneButtonClickListener() {
                            @Override
                            public void onClicked(final BillboardInfo info) {
//                                updateCoordinates(info);//更新广告位位置坐标
                                DialogUtil.tipsTwoButton(WorkerHomeActivity.this, "取消", "确定", "确定绑定到当前定位？", new DialogUtil.TwoButtonClickListener() {
                                    @Override
                                    public void leftOnClick() {
                                    }
                                    @Override
                                    public void rightOnClick() { //定位校正 更新修改广告位经纬度为 当前定位
                                        updateCoordinates(info);//更新广告位位置坐标
                                    }
                                });
                            }
                        });
                    }
                }else {
                    toast(text + "");
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

    /**
     * 更新广告位位置坐标
     */
    private void updateCoordinates(final BillboardInfo billboardInfo) {
        if (mBDLocation == null) {
            toast("很抱歉，未获取到有效定位");
            return;
        }
        // TODO: 2018/2/14 设置对应参数
        billboardInfo.setAccount(getMemberInfo().getAccount());
        billboardInfo.setToken(getMemberInfo().getToken());
        // TODO: 2018/2/21  替换具体数据 id和经
        billboardInfo.setAreaId(locationZoneCode);
        billboardInfo.setLongAddress(locationAddress);
        billboardInfo.setLocationLng(mBDLocation.getLongitude() + "");//
        billboardInfo.setLocationLat(mBDLocation.getLatitude() + "");
        httpReuqest.sendMessage(Api.Worker_updateCoordinates, billboardInfo, true, new HttpReuqest.CallBack<BillboardInfo>() {
            @Override
            public void onSuccess(Message message, final BillboardInfo result) {
                String text = result.getText();
                if (Api.OK == result.getCode()) {
                    // TODO: 2018/2/16 获取相应数据做对应显示
                    logI("更新广告位位置坐标:onSuccess: result.getText = "+text+"  BillboardInfo = "+result.toString());
                    DialogManager.dialogDismiss(WorkerHomeActivity.this);
                    String lng = result.getLocationLng();
                    billboardInfo.setLocationLng(lng);
                    String lat = result.getLocationLat();
                    billboardInfo.setLocationLat(lat);
                    currentLocationBillboard = billboardInfo;//②广告更新绑定当前定位的请求成功之后
                    itvPreview.getvCover().setVisibility(View.GONE);//去掉"预览"蒙层
                    //更新广告位位置成功后将该位置移动至中心,本来就在中心
//                    moveToNewCenter(Double.parseDouble(lat),Double.parseDouble(lng));

                    // ①按照管理码查询后，返回的广告位有经纬度
                    showUploadPhotoDialog(currentLocationBillboard);

                    reSearchRequest();//重新请求搜索信息，目的是用特殊覆盖物突出显示刚绑定的广告位[此时该广告位就在定位位置]
//                    DialogManager.dialogBaseInfoUploadPhoto(WorkerHomeActivity.this,result);
                }//

                toast(text + "");
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

    /**
     * 重新请求搜索信息
     */
    private void reSearchRequest() {

        SearchInfo searchInfo = new SearchInfo();
        // TODO: 2018/2/14 设置对应参数
        searchInfo.setAccount(getMemberInfo().getAccount());
        searchInfo.setToken(getMemberInfo().getToken());
        if (locationZoneCode==0){
            toast("无法准确获取当前位置，请到网络较好的地方重试");
            return;
        }
        searchInfo.setZoneId(locationZoneCode);
//        searchInfo.setStartPoint(mStartPoint);
//        searchInfo.setEndPoint(mEndPoint);
        logI("绑定后重新请求搜索信息：");
        httpReuqest.sendMessage(Api.Worker_search, searchInfo, true, new HttpReuqest.CallBack<SearchInfo>() {
            @Override
            public void onSuccess(Message message, SearchInfo result) {
                initWorkerOverlay(result, currentLocationBillboard);
                if (result!=null) {
                    List<BillboardInfo> list = result.getBillboardInfoList();
                    if (list != null) {
                        for (BillboardInfo billboardInfo : list) {
                            logI(" billboardInfo = " + billboardInfo);
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

    /**
     * 检测当前定位是否有广告位，例如用于比较工人首页，获取到的广告位列表，然后控制“预览”蒙层的显示与否
     * @param location
     * @param billboardInfoList
     * @return
     *  http://www.ab126.com/Geography/1884.html  经度纬度距离计算
     */
    private boolean checkCurrentLocationBillboard(BDLocation location, List<BillboardInfo> billboardInfoList) {
        if (location == null) {
            toast("很抱歉，未获取到有效定位");
            return false;
        }
        for (BillboardInfo billboardInfo : billboardInfoList) {
            double mLatitude = location.getLatitude();
            String lat = mLatitude + "";
            double mLongitude = location.getLongitude();
            String lng = mLongitude + "";
            String locationLat = billboardInfo.getLocationLat();
            String locationLng = billboardInfo.getLocationLng();
            /*boolean bLat = lat.startsWith(locationLat);
            boolean bLng = lng.startsWith(locationLng);
            if (bLat && bLng) {//当前定位与某个广告位位置重合
                currentLocationBillboard = billboardInfo;//①加到到初始数据后，检测数据列表是否有符合的
                return true;
            }*/
//            if (locationLat==null||locationLng==null)
//                break;//不该用

            if (locationLat!=null||locationLng!=null) {
                double parseLat = Double.parseDouble(locationLat);
                double parseLng = Double.parseDouble(locationLng);
                double v1 = Math.abs(parseLat - mLatitude);//
                double v2 = Math.abs(parseLng - mLongitude);//113.273581
                if (v1 < 0.00002 && v2 < 0.00002) {//0.00005误差许可，用网络计算过，约8米误差,0.00002约3米
                    currentLocationBillboard = billboardInfo;//①加到到初始数据后，检测数据列表是否有符合的
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检测有广告位是否就在当前定位
     *
     * @param location
     * @param billboardInfo
     * @return
     */
    private boolean checkCurrentLocationBillboard(BDLocation location, BillboardInfo billboardInfo) {
        if (location == null) {
            toast("很抱歉，未获取到有效定位");
            return false;
        }
        double mLatitude = location.getLatitude();
        String lat = mLatitude + "";
        double mLongitude = location.getLongitude();
        String lng = mLongitude + "";
        String locationLat = billboardInfo.getLocationLat();
        String locationLng = billboardInfo.getLocationLng();
        if (locationLat==null||locationLng==null)
            return false;
        if (lat.startsWith(locationLat) && lng.startsWith(locationLng)) {//当前定位与某个广告位位置重合
            currentLocationBillboard = billboardInfo;//①加到到初始数据后，检测数据列表是否有符合的
            return true;
        }

        return false;
    }


}
