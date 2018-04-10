package com.mabang.android.activity.worker;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mabang.android.R;
import com.mabang.android.activity.base.AppActivity;
import com.mabang.android.config.Api;
import com.mabang.android.config.Constants;
import com.mabang.android.config.IntentCode;
import com.mabang.android.entity.vo.BillboardInfo;
import com.mabang.android.entity.vo.Message;
import com.mabang.android.okhttp.HttpReuqest;
import com.mabang.android.utils.AppUtils;
import com.mabang.android.widget.AdItemView;

import walke.base.widget.DialogUtil;

/**
 * Created by walke on 2018/2/2.
 * email:1032458982@qq.com
 */

public class AdEditActivity extends AppActivity {

    private Button btSave;
    private BillboardInfo mBillboardInfo;
    private AdItemView itemManageCode,itemUniqueCode,itemProvince,itemCity,itemZone,itemStreet;
    private AdItemView itemDetailAddress,itemShedMaterial,itemSpec,itemMarks, itemUseStatus;
    private EditText etManageCode,etUniqueCode,etProvince,etCity,etZone,etStreet;
    private EditText etDetailAddress,etShedMaterial,etSpec,etMarks, etUseStatus;


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
        etProvince = itemProvince.getEtDesc();
        etCity = itemCity.getEtDesc();
        etZone = itemZone.getEtDesc();
        etStreet = itemStreet.getEtDesc();
        etDetailAddress = itemDetailAddress.getEtDesc();
        etShedMaterial = itemShedMaterial.getEtDesc();
        etSpec = itemSpec.getEtDesc();
        etMarks = itemMarks.getEtDesc();
        etUseStatus = itemUseStatus.getEtDesc();

        btSave.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mBillboardInfo = (BillboardInfo) intent.getSerializableExtra(Constants.BILLBOARD_INFO);
            if (mBillboardInfo != null) {
                setViewByData(mBillboardInfo);
            } else {
                logI("信息有误请重试");
            }
        }
    }
    public void setViewByData(BillboardInfo billboardInfo) {

        etManageCode.setText(AppUtils.textReplace(billboardInfo.getManageCode(), ""));//管理编号
        itemUniqueCode.getEtDesc().setText(AppUtils.textReplace(billboardInfo.getUniqueCode(), ""));//唯一码

        // TODO: 2018/4/11 分析省份 预设 当传送过来的广告位有初始地址时，怎么获取对应文本
        // TODO: 2018/4/11 通过传过来的地方id请求获取
        itemDetailAddress.getEtDesc().setText(AppUtils.textReplace(billboardInfo.getLongAddress(), ""));//详细地址
        itemShedMaterial.getEtDesc().setText(AppUtils.textReplace(billboardInfo.getShedMaterial(), ""));//材质
        itemSpec.getEtDesc().setText(AppUtils.textReplace(billboardInfo.getSpec(), ""));//规格
        itemMarks.getEtDesc().setText(AppUtils.textReplace(billboardInfo.getOtherDescribe(), "无"));//备注
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
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.aae_btSave:// 保存

                DialogUtil.tipsTwoButton(this, "取消", "确定", "保存当前修改？", new DialogUtil.TwoButtonClickListener() {
                    @Override
                    public void leftOnClick() {
                    }

                    @Override
                    public void rightOnClick() {
                        // TODO: 2018/4/10 a.做一个提交请求，b.成功后返回工人首页，并且带着参数
//                        if (!fixInput()){
//                            return;
//                        }
                        modifyBillboardInfo();
                    }
                });


                break;

        }
    }

    private boolean fixInput() {

        if (TextUtils.isEmpty(etManageCode.getText().toString().trim())
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
            return false;
        return true;
    }

    /**
     * 修改广告位信息
     */
    private void modifyBillboardInfo() {
        BillboardInfo billboardInfo = new BillboardInfo();

        billboardInfo.setAccount(getMemberInfo().getAccount());
        billboardInfo.setToken(getMemberInfo().getToken());
        //
        billboardInfo.setManageCode(etManageCode.getText().toString().trim());
        billboardInfo.setUniqueCode(itemUniqueCode.getEtDesc().getText().toString().trim());
//        billboardInfo.setProvinceId();
//        billboardInfo.setCityId();
//        billboardInfo.setAreaId();//区域id
//        billboardInfo.setStreetId();

        billboardInfo.setAddress(itemDetailAddress.getEtDesc().getText().toString().trim());
        billboardInfo.setShedMaterial(itemShedMaterial.getEtDesc().getText().toString().trim());
        billboardInfo.setSpec(itemSpec.getEtDesc().getText().toString().trim());
        billboardInfo.setOtherDescribe(itemMarks.getEtDesc().getText().toString().trim());//备注

        httpReuqest.sendMessage(Api.Worker_billboard, billboardInfo, true, new HttpReuqest.CallBack<BillboardInfo>() {
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
