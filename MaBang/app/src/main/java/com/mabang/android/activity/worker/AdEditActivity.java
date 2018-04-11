package com.mabang.android.activity.worker;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mabang.android.R;
import com.mabang.android.activity.base.AppActivity;
import com.mabang.android.config.Api;
import com.mabang.android.config.Constants;
import com.mabang.android.config.IntentCode;
import com.mabang.android.entity.vo.AddressInfo;
import com.mabang.android.entity.vo.AreaInfo;
import com.mabang.android.entity.vo.BillboardInfo;
import com.mabang.android.entity.vo.Message;
import com.mabang.android.okhttp.HttpReuqest;
import com.mabang.android.widget.AdItemView;
import com.mabang.android.widget.DropDownAlertDialog;

import java.util.List;

import walke.base.widget.DialogUtil;

/**
 * Created by walke on 2018/2/2.
 * email:1032458982@qq.com
 */

public class AdEditActivity extends AppActivity implements DropDownAlertDialog.OnDropDownAlertDialogEvent, DropDownAlertDialog.OnAlertDialogEvent {

    private Button btSave;
    private BillboardInfo mBillboardInfo;
    private AdItemView itemManageCode,itemUniqueCode,itemProvince,itemCity,itemZone,itemStreet;
    private AdItemView itemDetailAddress,itemShedMaterial,itemSpec,itemMarks, itemUseStatus;
    private EditText etManageCode,etUniqueCode;
    private EditText etDetailAddress,etShedMaterial,etSpec,etMarks, etUseStatus;
    private TextView tvProvince,tvCity,tvZone,tvStreet;

    private DropDownAlertDialog mChoceAddressDialog;
    private AreaInfo mCityInfo,mProvinceInfo,mZoneInfo,mStreetInfo;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_ad_edit;
    }

    @Override
    protected void initView() {
        initTitleViewById(R.id.aae_TitleView);
        btSave = ((Button) findViewById(R.id.aae_btSave));
        itemManageCode = ((AdItemView) findViewById(R.id.aae_itemManageCode));//唯一码
        itemUniqueCode = ((AdItemView) findViewById(R.id.aae_itemUniqueCode));//唯一码
        itemProvince = ((AdItemView) findViewById(R.id.aae_itemProvince));//省份
        itemCity = ((AdItemView) findViewById(R.id.aae_itemCity));//城市
        itemZone = ((AdItemView) findViewById(R.id.aae_itemZone));//区域
        itemStreet = ((AdItemView) findViewById(R.id.aae_itemStreet));//街道
        itemDetailAddress = ((AdItemView) findViewById(R.id.aae_itemDetailAddress));//具体地址
        itemShedMaterial = ((AdItemView) findViewById(R.id.aae_itemShedMaterial));//材质
        itemSpec = ((AdItemView) findViewById(R.id.aae_itemSpec));//规格
        itemMarks = ((AdItemView) findViewById(R.id.aae_itemMarks));//备注
        itemUseStatus = ((AdItemView) findViewById(R.id.aae_itemUseStatus));//可使用状态

        etManageCode = itemManageCode.getEtDesc();
        etUniqueCode = itemUniqueCode.getEtDesc();

        etDetailAddress = itemDetailAddress.getEtDesc();
        etShedMaterial = itemShedMaterial.getEtDesc();
        etSpec = itemSpec.getEtDesc();
        etMarks = itemMarks.getEtDesc();
        etUseStatus = itemUseStatus.getEtDesc();

        tvProvince = itemProvince.getTvDesc();
        tvCity = itemCity.getTvDesc();
        tvZone = itemZone.getTvDesc();
        tvStreet = itemStreet.getTvDesc();

        etManageCode.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        etUniqueCode.setInputType(EditorInfo.TYPE_CLASS_NUMBER);

        btSave.setOnClickListener(this);


    }

    @Override
    protected void initData() {

        Intent intent = getIntent();
        if (intent != null) {
            mBillboardInfo = (BillboardInfo) intent.getSerializableExtra(Constants.BILLBOARD_INFO);
            if (mBillboardInfo != null) {
                setViewByData(mBillboardInfo);

                mChoceAddressDialog = new DropDownAlertDialog(this);
                mChoceAddressDialog.setOnItemChangeEvent(this);
                mChoceAddressDialog.setOnAlertDialogEvent(this);
                enterLoadAddressInfo(-1);

            } else {
                logI("信息有误请重试");
            }
        }
    }
    public void setViewByData(BillboardInfo billboardInfo) {


        if (TextUtils.isEmpty(billboardInfo.getManageCode()))
            etManageCode.setHint("请输入管理码");//管理码
        else
            etManageCode.setText(billboardInfo.getManageCode());

        if (TextUtils.isEmpty(billboardInfo.getUniqueCode()))
            etUniqueCode.setHint("请输入唯一码");//唯一码
        else
            etUniqueCode.setText(billboardInfo.getUniqueCode());

        // TODO: 2018/4/11 分析省份 预设 当传送过来的广告位有初始地址时，怎么获取对应文本
        // TODO: 2018/4/11 通过传过来的地方id请求获取

        if (TextUtils.isEmpty(billboardInfo.getLongAddress()))
            etDetailAddress.setHint("请输入详细地址");//详细地址
        else
            etDetailAddress.setText(billboardInfo.getAddress());

        if (TextUtils.isEmpty(billboardInfo.getShedMaterial()))
            etShedMaterial.setHint("请输入雨棚材质");//雨棚材质
        else
            etShedMaterial.setText(billboardInfo.getShedMaterial());

        if (TextUtils.isEmpty(billboardInfo.getSpec()))
            etSpec.setHint("请输入规格");//规格
        else
            etSpec.setText(billboardInfo.getSpec());

        if (TextUtils.isEmpty(billboardInfo.getOtherDescribe()))
            etMarks.setHint("请输入备注");//备注
        else
            etMarks.setText(billboardInfo.getOtherDescribe());


/*
        if (mChoceAddressDialog != null&&!TextUtils.isEmpty(mChoceAddressDialog.getChooseText())) {
            //mChoceAddressDialog不为空 且 有文本
            mChoceAddressDialog.show();

        } else {//
            mChoceAddressDialog = new DropDownAlertDialog(this);
            mChoceAddressDialog.setOnItemChangeEvent(this);
            mChoceAddressDialog.setOnAlertDialogEvent(this);
//            mChoceAddressDialog.show();
            loadAddressInfo(-1);
        }

        */

        itemProvince.setOnClickListener(this);
        itemCity.setOnClickListener(this);
        itemZone.setOnClickListener(this);
        itemStreet.setOnClickListener(this);



    }

    /** 加载地址信息
     * @param panelIndex 这是联动级别，[-1：默认进入无数据]、[0：省级]、[1：市级]、[2：区级]
     */
    public void enterLoadAddressInfo(final int panelIndex) {
        mChoceAddressDialog.showLoad();
        AreaInfo provinceInfo = null;
        AreaInfo cityInfo = null;
        AreaInfo zoneInfo = null;

        AddressInfo addressInfo = new AddressInfo();
        // TODO: 2018/2/14 设置对应参数
        addressInfo.setAccount(getMemberInfo().getAccount());
        addressInfo.setToken(getMemberInfo().getToken());

        if (panelIndex > -1) {
            provinceInfo = mChoceAddressDialog.provincePanel.getDropDownItemInfo();
            if (panelIndex > 0) {
                cityInfo = mChoceAddressDialog.cityPanel.getDropDownItemInfo();
                if (panelIndex > 1)
                    zoneInfo = mChoceAddressDialog.zonePanel.getDropDownItemInfo();
            }
        }else {
            provinceInfo=mProvinceInfo;
            cityInfo=mCityInfo;
            zoneInfo=mZoneInfo;
        }

        if (provinceInfo != null)
            addressInfo.setProvinceId(provinceInfo.getAreaId());
        if (cityInfo != null)
            addressInfo.setCityId(cityInfo.getAreaId());
        if (zoneInfo != null)
            addressInfo.setZoneId(zoneInfo.getAreaId());

        this.httpReuqest.sendMessage(Api.Worker_address, addressInfo, false, new HttpReuqest.CallBack<AddressInfo>() {
            @Override
            public void onSuccess(Message message, AddressInfo result) {
                if (result == null)
                    return;
                if (Api.OK == result.getCode()) {
                    List<AreaInfo> provinceList = result.getProvinceList(); // 省份列表--全部
                    List<AreaInfo> cityList = result.getCityList(); // 城市列表--省份ID下的所有城市
                    List<AreaInfo> zoneList = result.getZoneList(); // 区域列表--城市ID下的所有区域
                    List<AreaInfo> streetList = result.getStreetList(); // 街道列表--区域下的所有街道（注：有些区域没有街道数据）

                    mChoceAddressDialog.provincePanel.append(provinceList, result.getProvinceId());
//                    if (panelIndex == -1) {
//                        mChoceAddressDialog.provincePanel.scrollActive();
//                    }

                    mChoceAddressDialog.cityPanel.append(cityList, result.getCityId());
                    if (panelIndex <= 1) {
                        mChoceAddressDialog.cityPanel.scrollActive();
                    }

                    mChoceAddressDialog.zonePanel.append(zoneList, result.getZoneId());
                    if (panelIndex <= 2) {
                        mChoceAddressDialog.zonePanel.scrollActive();
                    }

                    if (streetList != null && streetList.size() > 0) {
                        mChoceAddressDialog.streetPanel.setVisibility(View.VISIBLE);
                        mChoceAddressDialog.streetPanel.append(streetList, result.getStreetId());
                        mChoceAddressDialog.streetPanel.scrollActive();
                    } else
                        mChoceAddressDialog.streetPanel.setVisibility(View.GONE);

                    mChoceAddressDialog.changeSize();

                    if (panelIndex == -1) {
                        mChoceAddressDialog.provincePanel.scrollActive();

                        DropDownAlertDialog.DropDownAlertDialogPanel provincePanel = mChoceAddressDialog.provincePanel;
                        AreaInfo pInfo = provincePanel.getDropDownItemInfo();
                        if (pInfo!=null)
                            tvProvince.setText(pInfo.getAreaName()+"");
                        AreaInfo cInfo = mChoceAddressDialog.cityPanel.getDropDownItemInfo();
                        if (cInfo!=null)
                            tvCity.setText(cInfo.getAreaName()+"");
                        AreaInfo zInfo = mChoceAddressDialog.zonePanel.getDropDownItemInfo();
                        if (zInfo!=null)
                            tvZone.setText(zInfo.getAreaName()+"");
                        AreaInfo sInfo = mChoceAddressDialog.streetPanel.getDropDownItemInfo();
                        if (sInfo!=null)
                            tvStreet.setText(sInfo.getAreaName()+"");
                    }
                }
                return;
            }

            @Override
            public void onError(Exception exc) {
                logI(exc + "");
            }

            @Override
            public void onFinish() {
                mChoceAddressDialog.hideLoad();
                return;
            }
        });

        return;
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    public void onItemChange(DropDownAlertDialog dialog, DropDownAlertDialog.DropDownAlertDialogPanel panel, DropDownAlertDialog.DropDownAlertDialogItem item) {
        int index = panel.getIndex();
        if (dialog == mChoceAddressDialog && index < 3) {
//            loadAddressInfo(index);
            enterLoadAddressInfo(index);
        }
    }

    /** 加载地址信息
     * @param panelIndex 这是联动级别，-1、[0：省级]、[1：市级]、[2：区级]
     */
    public void loadAddressInfo(final int panelIndex) {
        mChoceAddressDialog.showLoad();
        AreaInfo provinceInfo = null;
        AreaInfo cityInfo = null;
        AreaInfo zoneInfo = null;
        if (panelIndex > -1) {
            provinceInfo = mChoceAddressDialog.provincePanel.getDropDownItemInfo();
            if (panelIndex > 0) {
                cityInfo = mChoceAddressDialog.cityPanel.getDropDownItemInfo();
                if (panelIndex > 1)
                    zoneInfo = mChoceAddressDialog.zonePanel.getDropDownItemInfo();
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

        this.httpReuqest.sendMessage(Api.Worker_address, addressInfo, false, new HttpReuqest.CallBack<AddressInfo>() {
            @Override
            public void onSuccess(Message message, AddressInfo result) {
                if (result == null)
                    return;
                if (Api.OK == result.getCode()) {
                    List<AreaInfo> provinceList = result.getProvinceList(); // 省份列表--全部
                    List<AreaInfo> cityList = result.getCityList(); // 城市列表--省份ID下的所有城市
                    List<AreaInfo> zoneList = result.getZoneList(); // 区域列表--城市ID下的所有区域
                    List<AreaInfo> streetList = result.getStreetList(); // 街道列表--区域下的所有街道（注：有些区域没有街道数据）

                    mChoceAddressDialog.provincePanel.append(provinceList, result.getProvinceId());
                    if (panelIndex == -1) {
                        mChoceAddressDialog.provincePanel.scrollActive();
                    }

                    mChoceAddressDialog.cityPanel.append(cityList, result.getCityId());
                    if (panelIndex <= 1) {
                        mChoceAddressDialog.cityPanel.scrollActive();
                    }

                    mChoceAddressDialog.zonePanel.append(zoneList, result.getZoneId());
                    if (panelIndex <= 2) {
                        mChoceAddressDialog.zonePanel.scrollActive();
                    }

                    if (streetList != null && streetList.size() > 0) {
                        mChoceAddressDialog.streetPanel.setVisibility(View.VISIBLE);
                        mChoceAddressDialog.streetPanel.append(streetList, result.getStreetId());
                        mChoceAddressDialog.streetPanel.scrollActive();
                    } else
                        mChoceAddressDialog.streetPanel.setVisibility(View.GONE);
                    mChoceAddressDialog.changeSize();
                }
                return;
            }

            @Override
            public void onError(Exception exc) {
                logI(exc + "");
            }

            @Override
            public void onFinish() {
                mChoceAddressDialog.hideLoad();
                return;
            }
        });

        return;
    }

    @Override
    public void onEnter(DropDownAlertDialog dialog) {
        if (dialog == mChoceAddressDialog) {
            mChoceAddressDialog.cancel();

            mProvinceInfo = dialog.provincePanel.getDropDownItemInfo();//当断网选择会为null
            if (mProvinceInfo != null) {
                String name = mProvinceInfo.getAreaName();
                tvProvince.setText(name+"");
            }

            mCityInfo = dialog.cityPanel.getDropDownItemInfo();//当断网选择会为null
            if (mCityInfo != null) {
                String name = mCityInfo.getAreaName();
                tvCity.setText(name+"");
            }

            mZoneInfo = dialog.zonePanel.getDropDownItemInfo();//当断网选择会为null
            if (mZoneInfo != null) {
                String name = mZoneInfo.getAreaName();
                tvZone.setText(name+"");
            }
            mStreetInfo = dialog.streetPanel.getDropDownItemInfo();//当断网选择会为null
            if (mStreetInfo != null) {
                String name = mStreetInfo.getAreaName();
                tvStreet.setText(name+"");
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.aae_itemProvince:
            case R.id.aae_itemCity:
            case R.id.aae_itemZone:
            case R.id.aae_itemStreet:
                if (mChoceAddressDialog==null)
                    return;
                mChoceAddressDialog.show();
                if (mChoceAddressDialog.provincePanel!=null)
                    mChoceAddressDialog.provincePanel.scrollActive();//省份面板滑动到选中item
                /*if (mChoceAddressDialog != null&&!TextUtils.isEmpty(mChoceAddressDialog.getChooseText())) {
                    //mChoceAddressDialog不为空 且 有文本
                    mChoceAddressDialog.show();

                } else {//
                    mChoceAddressDialog = new DropDownAlertDialog(this);
                    mChoceAddressDialog.setOnItemChangeEvent(this);
                    mChoceAddressDialog.setOnAlertDialogEvent(this);
                    mChoceAddressDialog.show();
//                    loadAddressInfo(-1);
                    enterLoadAddressInfo(-1);
                }*/
                break;
            case R.id.aae_btSave:// 保存
//                if (!fixInput()){
//                    return;
//                }
                DialogUtil.tipsTwoButton(this, "取消", "确定", "保存当前修改？", new DialogUtil.TwoButtonClickListener() {
                    @Override
                    public void leftOnClick() {
                    }

                    @Override
                    public void rightOnClick() {
                        // TODO: 2018/4/10 a.做一个提交请求，b.成功后返回工人首页，并且带着参数
//
                        modifyBillboardInfo();
                    }
                });

                break;


        }
    }

    private boolean fixInput() {

       /* if (TextUtils.isEmpty(etManageCode.getText().toString().trim())
                ||TextUtils.isEmpty(etUniqueCode.getText().toString().trim())
                ||TextUtils.isEmpty(etProvince.getText().toString().trim())
                ||TextUtils.isEmpty(etCity.getText().toString().trim())
                ||TextUtils.isEmpty(etZone.getText().toString().trim())
                ||TextUtils.isEmpty(etStreet.getText().toString().trim())
                ||TextUtils.isEmpty(etDetailAddress.getText().toString().trim())
                ||TextUtils.isEmpty(etShedMaterial.getText().toString().trim())
                ||TextUtils.isEmpty(etSpec.getText().toString().trim())
                ||TextUtils.isEmpty(etUseStatus.getText().toString().trim())
                )
            return false;*/
        if (TextUtils.isEmpty(etManageCode.getText().toString().trim())){
            toast("请输入管理码");
            enterGetFocus(etManageCode);
            return false;
        }
        if (TextUtils.isEmpty(etUniqueCode.getText().toString().trim())){
            toast("请输入唯一码");
            enterGetFocus(etUniqueCode);
            return false;
        }
        if (TextUtils.isEmpty(tvProvince.getText().toString().trim())){
            toast("请选择省份");
            return false;
        }
        if (TextUtils.isEmpty(tvCity.getText().toString().trim())){
            toast("请选择城市");
            return false;
        }
        if (TextUtils.isEmpty(tvZone.getText().toString().trim())){
            toast("请选择区域");
            return false;
        }
        if (TextUtils.isEmpty(tvStreet.getText().toString().trim())){
            toast("请选择街道");
            return false;
        }
        if (TextUtils.isEmpty(etDetailAddress.getText().toString().trim())){
            toast("请输入具体地址");
            enterGetFocus(etDetailAddress);
            return false;
        }
        if (TextUtils.isEmpty(etShedMaterial.getText().toString().trim())){
            toast("请输入雨棚材质");
            enterGetFocus(etShedMaterial);
            return false;
        }
        if (TextUtils.isEmpty(etSpec.getText().toString().trim())){
            toast("请输入规格");
            enterGetFocus(etSpec);
            return false;
        }
        if (TextUtils.isEmpty(etMarks.getText().toString().trim())){
            enterGetFocus(etMarks);
            toast("请输入备注");
            return false;
        }
        return true;
    }

    /**
     * 修改广告位信息
     */
    private void modifyBillboardInfo() {
//        BillboardInfo billboardInfo = new BillboardInfo();
//        billboardInfo.setAccount(getMemberInfo().getAccount());
//        billboardInfo.setToken(getMemberInfo().getToken());

        BillboardInfo billboardInfo = mBillboardInfo;

        billboardInfo.setManageCode(etManageCode.getText().toString().trim());
        billboardInfo.setUniqueCode(itemUniqueCode.getEtDesc().getText().toString().trim());
        if (mProvinceInfo != null)
            billboardInfo.setProvinceId(mProvinceInfo.getAreaId());
        if (mCityInfo != null)
            billboardInfo.setCityId(mCityInfo.getAreaId());
        if (mZoneInfo != null)
            billboardInfo.setAreaId(mZoneInfo.getAreaId());//区域id
        if (mStreetInfo != null)
            billboardInfo.setStreetId(mStreetInfo.getAreaId());

        billboardInfo.setAddress(itemDetailAddress.getEtDesc().getText().toString().trim());
        billboardInfo.setShedMaterial(itemShedMaterial.getEtDesc().getText().toString().trim());
        billboardInfo.setSpec(itemSpec.getEtDesc().getText().toString().trim());
        billboardInfo.setOtherDescribe(itemMarks.getEtDesc().getText().toString().trim());//备注

        httpReuqest.sendMessage(Api.Worker_Ad_modify, billboardInfo, true, new HttpReuqest.CallBack<BillboardInfo>() {
            @Override
            public void onSuccess(Message message, BillboardInfo result) {
                if (Api.OK == result.getCode()) {
                    // 修改广告位信息 成功
                    BillboardInfo billboardInfo = new BillboardInfo();
                    Intent data = new Intent(AdEditActivity.this, WorkerHomeActivity.class);
                    data.putExtra(Constants.BILLBOARD_INFO,billboardInfo);
                    setResult(IntentCode.WORKER_TO_EDIT_AD, data);
                    finish();

                }//
                 toast(result.getText() + "");
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

}
