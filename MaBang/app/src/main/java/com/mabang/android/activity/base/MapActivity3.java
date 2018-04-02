//package com.mabang.android.activity.base;
//
//import android.app.Dialog;
//import android.graphics.Point;
//import android.location.Address;
//import android.location.Geocoder;
//import android.text.TextUtils;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.View;
//
//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.baidu.location.Poi;
//import com.baidu.mapapi.map.BaiduMap;
//import com.baidu.mapapi.map.BitmapDescriptor;
//import com.baidu.mapapi.map.BitmapDescriptorFactory;
//import com.baidu.mapapi.map.MapStatus;
//import com.baidu.mapapi.map.MapStatusUpdate;
//import com.baidu.mapapi.map.MapStatusUpdateFactory;
//import com.baidu.mapapi.map.MapView;
//import com.baidu.mapapi.map.Marker;
//import com.baidu.mapapi.map.MarkerOptions;
//import com.baidu.mapapi.map.MyLocationData;
//import com.baidu.mapapi.model.LatLng;
//import com.baidu.mapapi.search.geocode.GeoCodeResult;
//import com.baidu.mapapi.search.geocode.GeoCoder;
//import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
//import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
//import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
//import com.mabang.android.R;
//import com.mabang.android.activity.user.UserHomeActivity;
//import com.mabang.android.activity.worker.WorkerHomeActivity;
//import com.mabang.android.config.Api;
//import com.mabang.android.entity.vo.BillboardInfo;
//import com.mabang.android.entity.vo.Message;
//import com.mabang.android.entity.vo.SearchInfo;
//import com.mabang.android.okhttp.HttpReuqest;
//import com.mabang.android.utils.ShopCarUtil;
//import com.mabang.android.widget.MapOverlayView;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//import walke.base.tool.LoadingDialog;
//
///**
// * Created by walke on 2018/2/3.
// * email:1032458982@qq.com
// */
//
//public abstract class MapActivity3 extends AppActivity {
//
//    protected MapView mMapView;
//    protected BaiduMap mBaiduMap;
//    // 定位相关
//    public MyLocationListener myListener = new MyLocationListener();
//    protected LocationClient mLocClient;
//    protected int mCurrentDirection = 0;
//    protected double mCurrentLat = 0.0;
//    protected double mCurrentLon = 0.0;
//    protected float mCurrentAccracy;
//    boolean isFirstLoc = true; // 是否首次定位
//    boolean isFirstSearch = true; // 是否首次获取默认信息
//    //    private float mMapScale=21.0f;//最大21.0f即最明细地址5米,最小1.0是范围最广效果
//    private float mMapScale = 16.0f;//16.0f: 200米，15.0f:500米  12.0f: 5公里
//
//    // 初始化全局 bitmap 信息，不用时及时 recycle
////    BitmapDescriptor bind = BitmapDescriptorFactory.fromResource(R.drawable.ico);
//    private BitmapDescriptor mBd;
//
//    protected BDLocation mBDLocation;
//    protected double[] mStartPoint;
//    protected double[] mEndPoint;
//    private Marker mMarkerA;
//    private Marker mMarkerBind;
//
//    private Dialog locatingDialog;
//
//    protected String locationAddress = "";//定位地址
//    protected int locationZoneCode;//定位到的区号
//
////    private View mOverlayView;//覆盖物视图
////    private TextView tvOverlayNumber;
////    private ImageView ivOverlay;
//
//    List<View> overlayViewList=new ArrayList<>();
//    private List<BillboardInfo> mBillboardInfoList;
//
//
//    /**
//     * @param mapView 地图初始化
//     */
//    protected void initMapView(final MapView mapView) {
//
////        mOverlayView = View.inflate(this, R.layout.view_to_bitmap, null);
////        tvOverlayNumber = ((TextView) mOverlayView.findViewById(R.id.vtb_tvNumber));
////        ivOverlay = ((ImageView) mOverlayView.findViewById(R.id.vtb_ivOverlay));
//
//        locatingDialog = LoadingDialog.createDialog(this, "定位中，请稍等");
//        // 地图初始化
//        mMapView = mapView;
//        mBaiduMap = mMapView.getMap();
//        mBaiduMap.setOnMapStatusChangeListener(mapStatusChangelistener);
//        // 开启定位图层
//        mBaiduMap.setMyLocationEnabled(true);
//        // 定位初始化
//        mLocClient = new LocationClient(this);
//        mLocClient.registerLocationListener(myListener);
//        LocationClientOption option = new LocationClientOption();
//        option.setOpenGps(true); // 打开gps
//        option.setIsNeedAddress(true);//返回的定位结果包含地址信息
//        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(1000);
//        mLocClient.setLocOption(option);
//        mLocClient.start();
//        locatingDialog.show();
//
//        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
//            public boolean onMarkerClick(final Marker marker) {
//                LatLng position = marker.getPosition();
//                Point point = mBaiduMap.getProjection().toScreenLocation(position);
////                ShopCarUtil.setAnim2(MapActivity.this,mOverlayView,1000);
////                ScaleAnimation sa = new ScaleAnimation(0.5f, 1.5f, 0.5f, 1.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
////                sa.setRepeatCount(2);
////                sa.setDuration(600);
////                mOverlayView.startAnimation(sa);
//
////                mapMarkerClick(marker);
////                if (mMarkerBind != null && mMarkerBind == marker) {
////                    onBindMarkerClick();
////                }
//                if (mBillboardInfoList != null) {
//                    for (int i = 0; i < mBillboardInfoList.size(); i++) {
//                        double mLatitude = marker.getPosition().latitude;
//                        String lat = mLatitude + "";
//                        double mLongitude = marker.getPosition().longitude;
//                        String lng = mLongitude + "";
//                        BillboardInfo billboardInfo = mBillboardInfoList.get(i);
//                        String locationLat = billboardInfo.getLocationLat();
//                        String locationLng = billboardInfo.getLocationLng();
//                        if (lat.startsWith(locationLat) && lng.startsWith(locationLng)) {//判断点击了谁
////                            ShopCarUtil.setAnim3(MapActivity.this,overlayViewList.get(i),point,500);
//                            ShopCarUtil.setAnim(MapActivity3.this, overlayViewList.get(i), point, 500, new ShopCarUtil.AnimListener() {
//                                @Override
//                                public void end() {
//                                    mapMarkerClick(marker);
//                                    if (mMarkerBind != null && mMarkerBind == marker)
//                                        onBindMarkerClick();
//                                }
//                            });
//
//                        }
//                    }
//                }
//
//
//                return true;
//            }
//        });
//        mMapView.showZoomControls(false);
//    }
//
//    protected void onBindMarkerClick() {
//    }
//
//    protected abstract void mapMarkerClick(Marker marker);
//
//    /**
//     * 重新地位
//     */
//    protected void resetLocation() {
//        mLocClient.start();
//        locatingDialog.show();
//        isFirstLoc = true;
//        isFirstSearch = true;
//        mMapScale = 16.0f;
//    }
//
//    /**
//     * 定位SDK监听函数
//     */
//    public class MyLocationListener implements BDLocationListener {
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            mBDLocation = location;
//            if (locatingDialog != null && locatingDialog.isShowing())
//                locatingDialog.dismiss();
//            if (location == null || mMapView == null)
//                return;
//            mCurrentLat = location.getLatitude();
//            mCurrentLon = location.getLongitude();
//            mCurrentAccracy = location.getRadius();
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(mCurrentDirection).latitude(location.getLatitude())
//                    .longitude(location.getLongitude()).build();
//            mBaiduMap.setMyLocationData(locData);
////            logI("MyLocationListener onReceiveLocation --- isFirstLoc = "+isFirstLoc);
//            if (isFirstLoc) {
//                logI("MyLocationListener onReceiveLocation --- if (isFirstLoc) --- isFirstLoc = " + isFirstLoc);
//                isFirstLoc = false;//改为 onMapStatusChangeFinish方法里修改值--这个有异常 因为onMapStatusChange不一定执行
//                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(ll).zoom(mMapScale);
//                mBaiduMap.setOnMapStatusChangeListener(mapStatusChangelistener);//每次移动后都要设置一下才可以继续监听
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));//每次移动
//
//                String city = mBDLocation.getCity();
//                final com.baidu.location.Address address = mBDLocation.getAddress();
//                locationAddress = mBDLocation.getAddrStr();//纤细地址
//                List<Poi> poiList = mBDLocation.getPoiList();
//                String buildingName = mBDLocation.getBuildingName();
//
//                if (locationAddress.startsWith("中国"))
//                    locationAddress = locationAddress.substring(2, locationAddress.length());
//                locationAddress(locationAddress, city);
//
//                GeoCoder mSearch = GeoCoder.newInstance();
//                mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
//                    @Override
//                    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
//                        Log.i("walke", "onGetGeoCodeResult: geoCodeResult");
//                    }
//
//                    @Override
//                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult geoCodeResult) {
//                        ReverseGeoCodeResult.AddressComponent addressDetail = geoCodeResult.getAddressDetail();
//                        String district = addressDetail.district;
//                        String streetNumber = addressDetail.streetNumber;
//                        if (geoCodeResult != null)
//                            locationZoneCode = geoCodeResult.getAdcode();
//                        if (MapActivity3.this instanceof WorkerHomeActivity)
//                            requestZoneDatas(district, locationZoneCode);
//
//                    }
//                });
//                // 反Geo搜索
//                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
//            }
//        }
//
//    }
//
//    private void requestZoneDatas(String zoneName, int locationZoneCode) {
//        SearchInfo searchInfo = new SearchInfo();
//        // TODO: 2018/2/14 设置对应参数
//        searchInfo.setAccount(getMemberInfo().getAccount());
//        searchInfo.setToken(getMemberInfo().getToken());
//        if (locationZoneCode == 0) {
//            toast("无法准确获取当前位置，请到网络较好的地方重试");
//            return;
//        }
////        searchInfo.setAddress(zoneName + "");
//        searchInfo.setZoneId(locationZoneCode);
//        logI("requesrZoneDatas 工人首页定位后默认查询");
//        httpReuqest.sendMessage(Api.Worker_search, searchInfo, true, new HttpReuqest.CallBack<SearchInfo>() {
//            @Override
//            public void onSuccess(Message message, SearchInfo result) {
////                initTypeOverlay(result);
//                enterFirstData(result);
//            }
//
//            @Override
//            public void onError(Exception exc) {
//                logI(exc + "");
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        });
//    }
//
//    BaiduMap.OnMapStatusChangeListener mapStatusChangelistener = new BaiduMap.OnMapStatusChangeListener() {
//        /** 手势操作地图，设置地图状态等操作导致地图状态开始改变。
//         * @param mapStatus 地图状态改变开始时的地图状态
//         */
//        @Override
//        public void onMapStatusChangeStart(MapStatus mapStatus) {
//            logI("onMapStatusChangeStart");
//        }
//
//        @Override
//        public void onMapStatusChangeStart(MapStatus mapStatus, int i) {
//        }
//
//        /** 地图状态变化中
//         * @param mapStatus 当前地图状态
//         */
//        @Override
//        public void onMapStatusChange(MapStatus mapStatus) {
//        }
//
//        /** 地图状态改变结束
//         * @param mapStatus 地图状态改变结束后的地图状态
//         */
//        @Override
//        public void onMapStatusChangeFinish(MapStatus mapStatus) {
//            //左上角经纬度
//            Point pt = new Point();
//            pt.x = 0;
//            pt.y = 0;
//            LatLng ll = mBaiduMap.getProjection().fromScreenLocation(pt);
//            //右下角经纬度
//            DisplayMetrics dm = new DisplayMetrics();
//            getWindowManager().getDefaultDisplay().getMetrics(dm);
//            Point pty = new Point();
//            pty.x = dm.widthPixels;
//            pty.y = dm.heightPixels;
//            LatLng lly = mBaiduMap.getProjection().fromScreenLocation(pty);
//            logI("onMapStatusChangeFinish: isFirstLoc = " + isFirstLoc);
////            mStartPoint = new double[2];
////            mEndPoint = new double[2];
////            mStartPoint[0] = ll.latitude;
////            mStartPoint[1] = ll.longitude;
////            mEndPoint[0] = lly.latitude;
////            mEndPoint[1] = lly.longitude;
//            if (isFirstSearch) {
//                isFirstSearch = false;
//
//                mStartPoint = new double[2];
//                mEndPoint = new double[2];
//                mStartPoint[0] = ll.latitude;
//                mStartPoint[1] = ll.longitude;
//                mEndPoint[0] = lly.latitude;
//                mEndPoint[1] = lly.longitude;
//
//                if (MapActivity3.this instanceof UserHomeActivity) {
//                    searchRequestFirst(Api.User_search, mStartPoint, mEndPoint);
//                } else {
////                    searchRequestFirst(Api.Worker_search, mStartPoint, mEndPoint);
//                }
//            }
//
//        }
//    };
//
//    private void searchRequestFirst(String apiType, double[] startPoint, double[] endPoint) {
//
//        SearchInfo searchInfo = new SearchInfo();
//        // TODO: 2018/2/14 设置对应参数
//        searchInfo.setAccount(getMemberInfo().getAccount());
//        searchInfo.setToken(getMemberInfo().getToken());
//        searchInfo.setStartPoint(startPoint);
//        searchInfo.setEndPoint(endPoint);
//        httpReuqest.sendMessage(apiType, searchInfo, false, new HttpReuqest.CallBack<SearchInfo>() {
//            @Override
//            public void onSuccess(Message message, SearchInfo result) {
//                enterFirstData(result);
//            }
//
//            @Override
//            public void onError(Exception exc) {
//                logI(exc + "");
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        });
//    }
//
//    protected void enterFirstData(SearchInfo result) {
//        mBillboardInfoList=result.getBillboardInfoList();
//        logI("(定位成功)/enterFirstData: result=" + result.getText());
//        initTypeOverlay(result);
//    }
//
//    public void initTypeOverlay(final SearchInfo searchInfo) {
//        List<BillboardInfo> billboardInfoList = searchInfo.getBillboardInfoList();
//        if (billboardInfoList != null) {
//            overlayViewList.clear();
//
//            mBaiduMap.clear();
//        } else {
//            return;
//        }
//        for (int i = 0; i < billboardInfoList.size(); i++) {
//            String locationLng = billboardInfoList.get(i).getLocationLng();
//            String locationLat = billboardInfoList.get(i).getLocationLat();
//            if (TextUtils.isEmpty(locationLat) || TextUtils.isEmpty(locationLng)) {
//                logI("经纬度为null");
//                break;
//            }
//            LatLng latLng = new LatLng(Double.parseDouble(locationLat), Double.parseDouble(locationLng));
//            if (i < 100) {
////                tvOverlayNumber.setText("" + (i + 1));
////                ivOverlay.setImageResource(R.mipmap.icon_overlay_red);
////                mBd = BitmapDescriptorFactory.fromView(mOverlayView);
//                MapOverlayView overlayView = new MapOverlayView(this, "" + (i + 1), R.mipmap.icon_overlay_red);
//                mBd = BitmapDescriptorFactory.fromView(overlayView);
//                MarkerOptions ooA = new MarkerOptions().position(latLng).icon(mBd).zIndex(2).draggable(true);
//                mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
//                overlayViewList.add(overlayView);
//            }
//        }
//
//    }
//
//    public void initTypeOverlay(final SearchInfo searchInfo, BillboardInfo billboardInfo) {
//        List<BillboardInfo> billboardInfoList = searchInfo.getBillboardInfoList();
//        if (billboardInfoList != null) {
//            mBaiduMap.clear();
//        } else {
//            return;
//        }
//        int selectItem = -1;
//        LatLng selectlatLng = null;
//        for (int i = 0; i < billboardInfoList.size(); i++) {
//            String locationLng = billboardInfoList.get(i).getLocationLng();
//            String locationLat = billboardInfoList.get(i).getLocationLat();
//            if (TextUtils.isEmpty(locationLat) || TextUtils.isEmpty(locationLng)) {
//                break;
//            }
//            LatLng latLng = new LatLng(Double.parseDouble(locationLat), Double.parseDouble(locationLng));
//            if (i < 100) {
//                if (billboardInfo != null && billboardInfoList.get(i).getId().equals(billboardInfo.getId())) {
////                    ivOverlay.setImageResource(R.mipmap.icon_overlay_blue);
//                    selectItem = i;
//                    selectlatLng = latLng;
//                } else {
////                    ivOverlay.setImageResource(R.mipmap.icon_overlay_red);
//                }
////                ivOverlay.setImageResource(R.mipmap.icon_overlay_red);
////                tvOverlayNumber.setText("" + (i + 1));
////                mBd = BitmapDescriptorFactory.fromView(mOverlayView);
//                MapOverlayView overlayView = new MapOverlayView(this, "" + (i + 1), R.mipmap.icon_overlay_red);
//                mBd = BitmapDescriptorFactory.fromView(overlayView);
//                MarkerOptions ooA = new MarkerOptions().position(latLng).icon(mBd).zIndex(2).draggable(true);
//                mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
//                overlayViewList.add(overlayView);
//            }
//        }
//        if (selectItem != -1 && selectlatLng != null) {
////            ivOverlay.setImageResource(R.mipmap.icon_overlay_blue);
////            tvOverlayNumber.setText("" + (selectItem + 1));
////            mBd = BitmapDescriptorFactory.fromView(mOverlayView);
//            MapOverlayView overlayView = new MapOverlayView(this, "" + (selectItem + 1), R.mipmap.icon_overlay_blue);
//            mBd = BitmapDescriptorFactory.fromView(overlayView);
//            MarkerOptions ooA = new MarkerOptions().position(selectlatLng).icon(mBd).zIndex(2).draggable(true);
//            mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
//            overlayViewList.remove(selectItem);
//            overlayViewList.add(selectItem,overlayView);
//        }
//    }
//
//    protected void locationAddress(String address, String city) {
//
//    }
//
//    public void moveToNewCenter(double lat, double lng) {
//        LatLng llCenter = new LatLng(lat, lng); //设定中心点坐标
//        MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
//                .target(llCenter)
//                .zoom(mMapScale)
//                .build();  //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
//        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//        mBaiduMap.animateMapStatus(mMapStatusUpdate);//动画移动
//        mBaiduMap.setMapStatus(mMapStatusUpdate);//改变地图状态
//
//    }
//
//    protected boolean isSetMapCenterChangeMapStatus;
//
//    public void setMapCenter(LatLng llCenter, final LocateDoneListener locateDoneListener) {
//        isSetMapCenterChangeMapStatus = true;
//        MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
//                .target(llCenter)
//                .zoom(mMapScale)
//                .build();  //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
//        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//        mBaiduMap.animateMapStatus(mMapStatusUpdate);//动画移动
//        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
//            @Override
//            public void onMapStatusChangeStart(MapStatus mapStatus) {
//            }
//
//            @Override
//            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {
//            }
//
//            @Override
//            public void onMapStatusChange(MapStatus mapStatus) {
//            }
//
//            @Override
//            public void onMapStatusChangeFinish(MapStatus mapStatus) {
//                if (isSetMapCenterChangeMapStatus) {
//                    isSetMapCenterChangeMapStatus = false;
//                    if (locateDoneListener != null)
//                        locateDoneListener.done();
//                }
//            }
//        });
//    }
//
//    public interface LocateDoneListener {
//        void done();
//    }
//
//    /**
//     * 通过文本地址获取经纬度并移动该定位
//     *
//     * @param str
//     * @return
//     */
//    public LatLng getLatLngBystrAndMoveTo(String str) {
//        LatLng latLng = null;
//        if (str != null) {
//            Geocoder gc = new Geocoder(MapActivity3.this, Locale.CHINA);
//            List<Address> addressList = null;
//            try {
//                addressList = gc.getFromLocationName(str, 1);
//                if (!addressList.isEmpty()) {
//                    Address address_temp = addressList.get(0);
//                    //计算经纬度
//                    double latitude = address_temp.getLatitude();
//                    double longitude = address_temp.getLongitude();
//                    System.out.println("经度：" + latitude);
//                    System.out.println("纬度：" + longitude);
//                    //生产GeoPoint
//                    latLng = new LatLng(latitude, longitude);
//                    moveToNewCenter(latitude, longitude);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                logE("getLatLngBystrAndMoveTo  e=" + e.getMessage());
//            }
//        }
//        return latLng;
//    }
//
//    /**
//     * 获取某广告位的经纬度
//     *
//     * @param info
//     * @return
//     */
//    protected LatLng getLatLng(BillboardInfo info) {
//        String lat = info.getLocationLat();
//        String lng = info.getLocationLng();
//        if (!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng)) {
//            LatLng ll = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
//            return ll;
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * 根据距离判断地图级别
//     */
//    public void setLevel(double distance) {
//        int mapLevel = 16;//200米
////        int zoom[] = {200, 500, 1000, 2000, 5000, 1000, 2000, 25000, 50000, 100000};
//        int zoom[] = {10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 25000, 50000, 100000, 200000, 500000, 1000000, 2000000};
////        int zoom[] = {20, 50, 90, 150, 300, 700, 1300, 2300, 5300, 10500, 20500, 25500, 51000, 101000, 201000, 502000, 1005000, 2005000};
//
//        logI("distance = " + distance);
//        for (int i = 0; i < zoom.length; i++) {
//            int zoomNow = zoom[i];
//            if (zoomNow - distance * 1000 > 0) {//
//                mapLevel = 18 - i + 6;
//                if (mapLevel > 16) {
//                    mapLevel = 16;
//                } else if (mapLevel < 11) {
//                    mapLevel = 11;
//                }
//                //设置地图显示级别为计算所得level
//                mMapScale = mapLevel;
//                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(mapLevel).build()));
//                break;
//            }
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
//        mMapView.onPause();
//        super.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
//        mMapView.onResume();
//        super.onResume();
//    }
//
//    @Override
//    protected void onDestroy() {
//        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
//        mMapView.onDestroy();
//        super.onDestroy();
//        // 回收 bitmap 资源
//        mBd.recycle();
//    }
//
//}
