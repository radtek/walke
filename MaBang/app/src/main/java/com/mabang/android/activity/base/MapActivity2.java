//package com.mabang.android.activity.base;
//
//import android.app.Dialog;
//import android.graphics.Point;
//import android.location.Address;
//import android.location.Geocoder;
//import android.text.TextUtils;
//import android.util.DisplayMetrics;
//import android.util.Log;
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
//import com.mabang.android.entity.vo.BillboardInfo;
//import com.mabang.android.entity.vo.Message;
//import com.mabang.android.entity.vo.SearchInfo;
//import com.mabang.android.okhttp.HttpReuqest;
//
//import java.io.IOException;
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
//public abstract class MapActivity2 extends AppActivity {
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
//    private float mMapScale = 16.0f;//200米，15.0f:500米
//
//    // 初始化全局 bitmap 信息，不用时及时 recycle
//    BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
//    BitmapDescriptor bdB = BitmapDescriptorFactory.fromResource(R.drawable.icon_markb);
//    BitmapDescriptor bdC = BitmapDescriptorFactory.fromResource(R.drawable.icon_markc);
//    BitmapDescriptor bdD = BitmapDescriptorFactory.fromResource(R.drawable.icon_markd);
//    BitmapDescriptor bdE = BitmapDescriptorFactory.fromResource(R.drawable.icon_marke);
//    BitmapDescriptor bdF = BitmapDescriptorFactory.fromResource(R.drawable.icon_markf);
//    BitmapDescriptor bdG = BitmapDescriptorFactory.fromResource(R.drawable.icon_markg);
//    BitmapDescriptor bdH = BitmapDescriptorFactory.fromResource(R.drawable.icon_markh);
//    BitmapDescriptor bdI = BitmapDescriptorFactory.fromResource(R.drawable.icon_marki);
//    BitmapDescriptor bdJ = BitmapDescriptorFactory.fromResource(R.drawable.icon_markj);
//    BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.mipmap.icon_overlay_red);
//    BitmapDescriptor bind = BitmapDescriptorFactory.fromResource(R.mipmap.icon_overlay_blue);
//    BitmapDescriptor bdGround = BitmapDescriptorFactory.fromResource(R.drawable.ground_overlay);
//
//    protected BDLocation mBDLocation;
//    protected double[] mStartPoint;
//    protected double[] mEndPoint;
//    private Marker mMarkerA;
//    private Marker mMarkerBind;
//
//    private Dialog locatingDialog ;
//
//    protected String locationAddress="";//定位地址
//    protected int locationZoneCode;//定位到的区号
//    /**
//     * @param mapView 地图初始化
//     */
//    protected void initMapView(final MapView mapView) {
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
//                mapMarkerClick(marker);
//                /*moveToNewCenter(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude), new LocateDoneListener() {
//                    @Override
//                    public void done() {
//                        mapMarkerClickAndMoveEnd(marker);
//                    }
//                });*/
//                if (mMarkerBind != null && mMarkerBind == marker) {
//                    onBindMarkerClick();
//                }
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
//                logI("MyLocationListener onReceiveLocation --- if (isFirstLoc) --- isFirstLoc = "+isFirstLoc);
//                isFirstLoc = false;//改为 onMapStatusChangeFinish方法里修改值--这个有异常 因为onMapStatusChange不一定执行
//                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(ll).zoom(mMapScale);
//                mBaiduMap.setOnMapStatusChangeListener(mapStatusChangelistener);//每次移动后都要设置一下才可以继续监听
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));//每次移动
//
//                String city = mBDLocation.getCity();
//                com.baidu.location.Address address = mBDLocation.getAddress();
//                locationAddress = mBDLocation.getAddrStr();//纤细地址
//                List<Poi> poiList = mBDLocation.getPoiList();
//                String buildingName = mBDLocation.getBuildingName();
//
//                if (locationAddress.startsWith("中国"))
//                    locationAddress  = locationAddress.substring(2, locationAddress.length());
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
//                        if(geoCodeResult!=null)
//                            locationZoneCode=geoCodeResult.getAdcode();
//                    }
//                });
//                // 反Geo搜索
//                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
//            }
//        }
//
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
//            logI("onMapStatusChangeFinish: isFirstLoc = "+isFirstLoc);
//            mStartPoint = new double[2];
//            mEndPoint = new double[2];
//            mStartPoint[0] = ll.latitude;
//            mStartPoint[1] = ll.longitude;
//            mEndPoint[0] = lly.latitude;
//            mEndPoint[1] = lly.longitude;
//            if (isFirstSearch) {
//                isFirstSearch = false;
////                if (MapActivity2.this instanceof UserHomeActivity) {
////                    searchRequestFirst(Api.User_search, mStartPoint, mEndPoint);
////                } else {
////                    searchRequestFirst(Api.Worker_search, mStartPoint, mEndPoint);
////                }
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
//        logI("(定位成功)/enterFirstData: result=" + result.getText());
//        initTypeOverlay(result);
//    }
//
//    public void initTypeOverlay(final SearchInfo searchInfo) {
//        List<BillboardInfo> billboardInfoList = searchInfo.getBillboardInfoList();
//        if (billboardInfoList != null) {
//            mBaiduMap.clear();
//        } else {
//            return;
//        }
//        for (int i = 0; i < billboardInfoList.size(); i++) {
//
//            String locationLng = billboardInfoList.get(i).getLocationLng();
//            String locationLat = billboardInfoList.get(i).getLocationLat();
//            if (TextUtils.isEmpty(locationLat) || TextUtils.isEmpty(locationLng)) {
//                logI("经纬度为null");
//                break;
//            }
//            LatLng latLng = new LatLng(Double.parseDouble(locationLat), Double.parseDouble(locationLng));
//            if (i == 0) {
//                MarkerOptions ooA = new MarkerOptions().position(latLng).icon(bdA).zIndex(2).draggable(true);
//                mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
//            } else if (i == 1) {
//                MarkerOptions oo = new MarkerOptions().position(latLng).icon(bdB).zIndex(9).draggable(true);
//                mBaiduMap.addOverlay(oo);
//            } else if (i == 2) {
//                MarkerOptions oo = new MarkerOptions().position(latLng).icon(bdC).zIndex(2).draggable(true);
//                mBaiduMap.addOverlay(oo);
//            } else if (i == 3) {
//                MarkerOptions oo = new MarkerOptions().position(latLng).icon(bdD).zIndex(2).draggable(true);
//                mBaiduMap.addOverlay(oo);
//            } else if (i == 4) {
//                MarkerOptions oo = new MarkerOptions().position(latLng).icon(bdE).zIndex(2).draggable(true);
//                mBaiduMap.addOverlay(oo);
//            } else if (i == 5) {
//                MarkerOptions oo = new MarkerOptions().position(latLng).icon(bdF).zIndex(2).draggable(true);
//                mBaiduMap.addOverlay(oo);
//            } else if (i == 6) {
//                MarkerOptions oo = new MarkerOptions().position(latLng).icon(bdG).zIndex(2).draggable(true);
//                mBaiduMap.addOverlay(oo);
//            } else if (i == 7) {
//                MarkerOptions oo = new MarkerOptions().position(latLng).icon(bdH).zIndex(2).draggable(true);
//                mBaiduMap.addOverlay(oo);
//            } else if (i == 8) {
//                MarkerOptions oo = new MarkerOptions().position(latLng).icon(bdI).zIndex(2).draggable(true);
//                mBaiduMap.addOverlay(oo);
//            } else if (i == 9) {
//                MarkerOptions oo = new MarkerOptions().position(latLng).icon(bdJ).zIndex(2).draggable(true);
//                mBaiduMap.addOverlay(oo);
//            } else {
//                MarkerOptions oo = new MarkerOptions().position(latLng).icon(bd).zIndex(2).draggable(true);
//                mBaiduMap.addOverlay(oo);
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
//        for (int i = 0; i < billboardInfoList.size(); i++) {
//            String locationLng = billboardInfoList.get(i).getLocationLng();
//            String locationLat = billboardInfoList.get(i).getLocationLat();
//            if (TextUtils.isEmpty(locationLat) || TextUtils.isEmpty(locationLng)) {
//                break;
//            }
//            LatLng latLng = new LatLng(Double.parseDouble(locationLat), Double.parseDouble(locationLng));
//
//            if (billboardInfo != null && billboardInfoList.get(i).getId().equals(billboardInfo.getId())) {
//                MarkerOptions ooBind = new MarkerOptions().position(latLng).icon(bind).zIndex(9).draggable(true);
//                mMarkerBind = (Marker) (mBaiduMap.addOverlay(ooBind));
//            } else {
//                if (i == 0) {
//                    MarkerOptions ooA = new MarkerOptions().position(latLng).icon(bdA).zIndex(2).draggable(true);
//                    mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
//                } else if (i == 1) {
//                    MarkerOptions oo = new MarkerOptions().position(latLng).icon(bdB).zIndex(9).draggable(true);
//                    mBaiduMap.addOverlay(oo);
//                } else if (i == 2) {
//                    MarkerOptions oo = new MarkerOptions().position(latLng).icon(bdC).zIndex(2).draggable(true);
//                    mBaiduMap.addOverlay(oo);
//                } else if (i == 3) {
//                    MarkerOptions oo = new MarkerOptions().position(latLng).icon(bdD).zIndex(2).draggable(true);
//                    mBaiduMap.addOverlay(oo);
//                } else if (i == 4) {
//                    MarkerOptions oo = new MarkerOptions().position(latLng).icon(bdE).zIndex(2).draggable(true);
//                    mBaiduMap.addOverlay(oo);
//                } else if (i == 5) {
//                    MarkerOptions oo = new MarkerOptions().position(latLng).icon(bdF).zIndex(2).draggable(true);
//                    mBaiduMap.addOverlay(oo);
//                } else if (i == 6) {
//                    MarkerOptions oo = new MarkerOptions().position(latLng).icon(bdG).zIndex(2).draggable(true);
//                    mBaiduMap.addOverlay(oo);
//                } else if (i == 7) {
//                    MarkerOptions oo = new MarkerOptions().position(latLng).icon(bdH).zIndex(2).draggable(true);
//                    mBaiduMap.addOverlay(oo);
//                } else if (i == 8) {
//                    MarkerOptions oo = new MarkerOptions().position(latLng).icon(bdI).zIndex(2).draggable(true);
//                    mBaiduMap.addOverlay(oo);
//                } else if (i == 9) {
//                    MarkerOptions oo = new MarkerOptions().position(latLng).icon(bdJ).zIndex(2).draggable(true);
//                    mBaiduMap.addOverlay(oo);
//                } else {
//                    MarkerOptions oo = new MarkerOptions().position(latLng).icon(bd).zIndex(2).draggable(true);
//                    mBaiduMap.addOverlay(oo);
//                }
//            }
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
//            Geocoder gc = new Geocoder(MapActivity2.this, Locale.CHINA);
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
//                logE("getLatLngBystrAndMoveTo  e="+e.getMessage());
//            }
//        }
//        return latLng;
//    }
//
//    /** 获取某广告位的经纬度
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
//        bdA.recycle();
//        bdB.recycle();
//        bdC.recycle();
//        bdD.recycle();
//        bd.recycle();
//        bdGround.recycle();
//    }
//
//}
