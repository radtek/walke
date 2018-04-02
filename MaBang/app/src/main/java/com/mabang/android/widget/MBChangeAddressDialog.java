//package com.mabang.android.widget;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.annotation.StyleRes;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.TextView;
//
//import com.google.gson.Gson;
//import com.hui.wheelviewlibrary.wheelview.OnWheelChangedListener;
//import com.hui.wheelviewlibrary.wheelview.OnWheelScrollListener;
//import com.hui.wheelviewlibrary.wheelview.WheelView;
//import com.hui.wheelviewlibrary.wheelview.adapter.AbstractWheelTextAdapter1;
//import com.mabang.android.R;
//import com.mabang.android.entity.vo.AddressInfo;
//import com.mabang.android.entity.vo.AreaInfo;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MBChangeAddressDialog extends Dialog implements View.OnClickListener {
//
//    private WheelView wvProvince;
//    private WheelView wvCitys;
//    private WheelView wvArea;
//    private View lyChangeAddress;
//    private View lyChangeAddressChild;
//    private TextView btnSure;
//    private TextView btnCancel;
//
//    private ArrayList<String> arrProvinces = new ArrayList<String>();
//    private ArrayList<String> arrCitys = new ArrayList<String>();
//    private ArrayList<String> arrAreas = new ArrayList<String>();
//    private AddressTextAdapter provinceAdapter;
//    private AddressTextAdapter cityAdapter;
//    private AddressTextAdapter areaAdapter;
//
//    private String strProvince = "广东";
//    private String strCity = "广州";
//    private String strArea = "荔湾区";
//    private String street = "西村街道";
//    private OnAddressCListener onAddressCListener;
//
//    private int maxsize = 14;
//    private int minsize = 12;
//
//    private Context mContext;
//    private String mAddressStr;
//
//    public MBChangeAddressDialog(@NonNull final Context context, @StyleRes int themeResId, int width) {
//        super(context, themeResId);
//        mContext = context;
//        View view = View.inflate(context, com.hui.wheelviewlibrary.R.layout.dialog_change_address, null);
//
//        wvProvince = (WheelView) view.findViewById(R.id.wv_address_province);
//        wvCitys = (WheelView) view.findViewById(R.id.wv_address_city);
//        wvArea = (WheelView) view.findViewById(R.id.wv_address_area);
//        lyChangeAddress = view.findViewById(R.id.ly_myinfo_changeaddress);
//        lyChangeAddressChild = view.findViewById(R.id.ly_myinfo_changeaddress_child);
//        btnSure = (TextView) view.findViewById(R.id.btn_myinfo_sure);
//        btnCancel = (TextView) view.findViewById(R.id.btn_myinfo_cancel);
//
//        lyChangeAddress.setBackgroundResource(R.drawable.bg_corners_white);
//        // 设置背景颜色变暗
//        final WindowManager.LayoutParams lp = ((AppCompatActivity) context).getWindow().getAttributes();
//        lp.alpha = 0.5f;
//        ((AppCompatActivity) context).getWindow().setAttributes(lp);
//        this.setContentView(view, new ViewGroup.LayoutParams(width, (int) (width * 0.6)));
//        setCancelable(false);//点击其他区域无响应
//
//        lyChangeAddressChild.setOnClickListener(this);
//        btnSure.setOnClickListener(this);
//        btnCancel.setOnClickListener(this);
//
//        initJsonData();
//        initDatas();
//
//        initProvinces();
//        provinceAdapter = new AddressTextAdapter(context, arrProvinces, getProvinceItem(strProvince), maxsize, minsize);
//        wvProvince.setVisibleItems(5);
//        wvProvince.setViewAdapter(provinceAdapter);
//        wvProvince.setCurrentItem(getProvinceItem(strProvince));
//
//        initCitys();
//        cityAdapter = new AddressTextAdapter(context, arrCitys, getCityItem(strCity), maxsize, minsize);
//        wvCitys.setVisibleItems(5);
//        wvCitys.setViewAdapter(cityAdapter);
//        wvCitys.setCurrentItem(getCityItem(strCity));
//
//        initAreas(mAreaDatasMap.get(strCity));
//        areaAdapter = new AddressTextAdapter(context, arrAreas, getAreaItem(strArea), maxsize, minsize);
//        wvArea.setVisibleItems(5);
//        wvArea.setViewAdapter(areaAdapter);
//        wvArea.setCurrentItem(getAreaItem(strArea));
//
//        wvProvince.addChangingListener(new OnWheelChangedListener() {
//
//            @Override
//            public void onChanged(WheelView wheel, int oldValue, int newValue) {
//                // TODO Auto-generated method stub
//                String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
//                strProvince = currentText;
//                setTextviewSize(currentText, provinceAdapter);
//
//                String[] citys = mCitisDatasMap.get(currentText);
//                initCitys(citys);
//                cityAdapter = new AddressTextAdapter(context, arrCitys, 0, maxsize, minsize);
//                wvCitys.setVisibleItems(5);
//                wvCitys.setViewAdapter(cityAdapter);
//                wvCitys.setCurrentItem(0);
//                setTextviewSize("0", cityAdapter);
//
//                //根据市，地区联动
//                String[] areas = mAreaDatasMap.get(citys[0]);
//                initAreas(areas);
//                areaAdapter = new AddressTextAdapter(context, arrAreas, 0, maxsize, minsize);
//                wvArea.setVisibleItems(5);
//                wvArea.setViewAdapter(areaAdapter);
//                wvArea.setCurrentItem(0);
//                setTextviewSize("0", areaAdapter);
//            }
//        });
//
//        wvProvince.addScrollingListener(new OnWheelScrollListener() {
//
//            @Override
//            public void onScrollingStarted(WheelView wheel) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onScrollingFinished(WheelView wheel) {
//                // TODO Auto-generated method stub
//                String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
//                setTextviewSize(currentText, provinceAdapter);
//            }
//        });
//
//        wvCitys.addChangingListener(new OnWheelChangedListener() {
//
//            @Override
//            public void onChanged(WheelView wheel, int oldValue, int newValue) {
//                // TODO Auto-generated method stub
//                String currentText = (String) cityAdapter.getItemText(wheel.getCurrentItem());
//                strCity = currentText;
//                setTextviewSize(currentText, cityAdapter);
//
//                //根据市，地区联动
//                String[] areas = mAreaDatasMap.get(currentText);
//                initAreas(areas);
//                areaAdapter = new AddressTextAdapter(context, arrAreas, 0, maxsize, minsize);
//                wvArea.setVisibleItems(5);
//                wvArea.setViewAdapter(areaAdapter);
//                wvArea.setCurrentItem(0);
//                setTextviewSize("0", areaAdapter);
//
//
//            }
//        });
//
//        wvCitys.addScrollingListener(new OnWheelScrollListener() {
//
//            @Override
//            public void onScrollingStarted(WheelView wheel) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onScrollingFinished(WheelView wheel) {
//                // TODO Auto-generated method stub
//                String currentText = (String) cityAdapter.getItemText(wheel.getCurrentItem());
//                setTextviewSize(currentText, cityAdapter);
//            }
//        });
//
//        wvArea.addChangingListener(new OnWheelChangedListener() {
//
//            @Override
//            public void onChanged(WheelView wheel, int oldValue, int newValue) {
//                // TODO Auto-generated method stub
//                String currentText = (String) areaAdapter.getItemText(wheel.getCurrentItem());
//                strArea = currentText;
//                setTextviewSize(currentText, cityAdapter);
//            }
//        });
//
//        wvArea.addScrollingListener(new OnWheelScrollListener() {
//
//            @Override
//            public void onScrollingStarted(WheelView wheel) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onScrollingFinished(WheelView wheel) {
//                // TODO Auto-generated method stub
//                String currentText = (String) areaAdapter.getItemText(wheel.getCurrentItem());
//                setTextviewSize(currentText, areaAdapter);
//            }
//        });
//    }
//
//
//    private class AddressTextAdapter extends AbstractWheelTextAdapter1 {
//        ArrayList<String> list;
//
//        protected AddressTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
//            super(context, com.hui.wheelviewlibrary.R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
//            this.list = list;
//            setItemTextResource(com.hui.wheelviewlibrary.R.id.tempValue);
//        }
//
//        @Override
//        public View getItem(int index, View cachedView, ViewGroup parent) {
//            View view = super.getItem(index, cachedView, parent);
//            return view;
//        }
//
//        @Override
//        public int getItemsCount() {
//            return list.size();
//        }
//
//        @Override
//        protected CharSequence getItemText(int index) {
//            return list.get(index) + "";
//        }
//    }
//
//    /**
//     * 设置字体大小
//     *
//     * @param curriteItemText
//     * @param adapter
//     */
//    public void setTextviewSize(String curriteItemText, AddressTextAdapter adapter) {
//        ArrayList<View> arrayList = adapter.getTestViews();
//        int size = arrayList.size();
//        String currentText;
//        for (int i = 0; i < size; i++) {
//            TextView textvew = (TextView) arrayList.get(i);
//            currentText = textvew.getText().toString();
//            if (curriteItemText.equals(currentText)) {
//                textvew.setTextSize(14);
//            } else {
//                textvew.setTextSize(12);
//            }
//        }
//    }
//
//    public void setAddresskListener(OnAddressCListener onAddressCListener) {
//        this.onAddressCListener = onAddressCListener;
//    }
//
//    @Override
//    public void onClick(View v) {
//        // TODO Auto-generated method stub
//        if (v == btnSure) {
//            if (onAddressCListener != null) {
//                onAddressCListener.onClick(strProvince, strCity, strArea);
//            }
//        } else if (v == btnCancel) {
//
//        } else if (v == lyChangeAddressChild) {
//            return;
//        } else {
////			dismiss();
//        }
//        dismiss();
//    }
//
//    /**
//     * 回调接口
//     *
//     * @author Administrator
//     */
//    public interface OnAddressCListener {
//        public void onClick(String province, String city, String area);
//    }
//
//    /**
//     * 从文件中读取地址数据
//     */
//    private void initJsonData() {
//        try {
//            StringBuffer sb = new StringBuffer();
//            InputStream is = mContext.getClass().getClassLoader().getResourceAsStream("assets/" + "city_street.json");
//            int len = -1;
//            byte[] buf = new byte[1024];
//            while ((len = is.read(buf)) != -1) {
//                sb.append(new String(buf, 0, len, "utf-8"));
//            }
//            is.close();
//            mAddressStr = sb.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * 解析整个Json对象，完成后释放Json对象的内存
//     */
//    private void initDatas() {
//        AddressInfo addressInfo = new Gson().fromJson(mAddressStr, AddressInfo.class);
//        List<AreaInfo> provinceList = addressInfo.getProvinceList();
//        for (AreaInfo areaInfo : provinceList) {
//            arrProvinces.add(areaInfo.getAreaName());
//
//        }
//
//    }
//
//    /**
//     * 初始化省会
//     */
//    public void initProvinces() {
//
//    }
//
//    /**
//     * 根据省会，生成该省会的所有城市
//     *
//     * @param citys
//     */
//    public void initCitys(String[] citys) {
//
//    }
//
//    /**
//     * 根据城市，生成该城市的所有地区
//     *
//     * @param areas
//     */
//    public void initAreas(String[] areas) {
//
//    }
//
//    /**
//     * 初始化地点
//     *
//     * @param province
//     * @param city
//     */
//    public void setAddress(String province, String city, String area) {
//        if (province != null && province.length() > 0) {
//            this.strProvince = province;
//        }
//        if (city != null && city.length() > 0) {
//            this.strCity = city;
//        }
//
//        if (area != null && area.length() > 0) {
//            this.strArea = area;
//        }
//    }
//
//    /**
//     * 返回省会索引，没有就返回默认“广东”
//     *
//     * @param province
//     * @return
//     */
//    public int getProvinceItem(String province) {
//        int size = arrProvinces.size();
//        int provinceIndex = 0;
//        boolean noprovince = true;
//        for (int i = 0; i < size; i++) {
//            if (province.equals(arrProvinces.get(i))) {
//                noprovince = false;
//                return provinceIndex;
//            } else {
//                provinceIndex++;
//            }
//        }
//        if (noprovince) {
//            strProvince = "广东";
//            return 18;
//        }
//        return provinceIndex;
//    }
//
//    /**
//     * 得到城市索引，没有返回默认“深圳”
//     *
//     * @param city
//     * @return
//     */
//    public int getCityItem(String city) {
//        int size = arrCitys.size();
//        int cityIndex = 0;
//        boolean nocity = true;
//        for (int i = 0; i < size; i++) {
//            System.out.println(arrCitys.get(i));
//            if (city.equals(arrCitys.get(i))) {
//                nocity = false;
//                return cityIndex;
//            } else {
//                cityIndex++;
//            }
//        }
//        if (nocity) {
//            strCity = "深圳";
//            return 2;
//        }
//        return cityIndex;
//    }
//
//    /**
//     * 得到地区索引，没有返回默认“福田区”
//     *
//     * @param area
//     * @return
//     */
//    public int getAreaItem(String area) {
//        int size = arrAreas.size();
//        int areaIndex = 0;
//        boolean noarea = true;
//        for (int i = 0; i < size; i++) {
//            System.out.println(arrAreas.get(i));
//            if (area.equals(arrAreas.get(i))) {
//                noarea = false;
//                return areaIndex;
//            } else {
//                areaIndex++;
//            }
//        }
//        if (noarea) {
//            strArea = "福田区";
//            return 1;
//        }
//        return areaIndex;
//    }
//
//
//}