package com.mabang.android.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mabang.android.R;
import com.mabang.android.entity.vo.BillboardInfo;
import com.mabang.android.utils.AppUtils;

import walke.base.tool.SpannableUtil;


/**
 * Created by walke on 2018/2/3.
 * email:1032458982@qq.com
 */

public class AdDetailsView extends LinearLayout {

    private View lineBottom;
    private TextView tvAddress, tvAdManageCode,tvAdDetails,tvAdUniqueCode;
    private TextTextView ttvCompany,ttvDetailsAddress,ttvFacilitySituation,ttvAdSituation,ttvSpecification,ttvRemark;

    public AdDetailsView(Context context) {
        this(context,null);
    }

    public AdDetailsView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AdDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_ad_details, this);

        tvAddress = ((TextView) findViewById(R.id.vad_tvAddress));//头部地址
        tvAdManageCode = ((TextView) findViewById(R.id.vad_tvAdManageCode));//编号行使用SpanableString
        tvAdUniqueCode = ((TextView) findViewById(R.id.vad_tvAdUniqueCode));//编号行使用SpanableString

        ttvCompany = ((TextTextView) findViewById(R.id.vad_ttvCompany));//广告使用
        ttvDetailsAddress = ((TextTextView) findViewById(R.id.vad_ttvDetailsAddress));//详细地址
        ttvFacilitySituation = ((TextTextView) findViewById(R.id.vad_ttvFacilitySituation));//雨棚材质
        ttvAdSituation = ((TextTextView) findViewById(R.id.vad_ttvAdSituation));//广告状态
        ttvSpecification = ((TextTextView) findViewById(R.id.vad_ttvSpecification));//规格
        ttvRemark = ((TextTextView) findViewById(R.id.vad_ttvRemark));//备注

        lineBottom = findViewById(R.id.vad_lineBottom);
        lineBottom.setVisibility(GONE);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AdDetailsView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.AdDetailsView_showBottom) {
                boolean isShowBottom = a.getBoolean(attr, true);
                if (isShowBottom){
                    lineBottom.setVisibility(VISIBLE);
                }else {
                    lineBottom.setVisibility(GONE);
                }
            }
        }
        a.recycle();

    }

    public TextView getTvAddress() {
        return tvAddress;
    }

    public TextView getTvAdManageCode() {
        return tvAdManageCode;
    }

    public TextView getTvAdDetails() {
        return tvAdDetails;
    }

    public void setViewByData(BillboardInfo billboardInfo){
//        tvAddress.setText(billboardInfo.getShortName()+"");
        tvAddress.setText(AppUtils.textReplace(billboardInfo.getShortName(),""));
//        SpannableUtil.spannableTwoColor(tvAdManageCode, "管理编号：", billboardInfo.getManageCode()+"", "     唯一码：", billboardInfo.getUniqueCode()+"", "", Color.parseColor("#ffaaaaaa"));

        SpannableUtil.spannableOneColor(tvAdManageCode,"管理编号：",
//                billboardInfo.getManageCode()+"",
                AppUtils.textReplace(billboardInfo.getManageCode(),""),
                "",Color.parseColor("#ffaaaaaa"));
        SpannableUtil.spannableOneColor(tvAdUniqueCode,"唯一码：",
//                billboardInfo.getUniqueCode()+"",
                AppUtils.textReplace(billboardInfo.getUniqueCode(),""),
                "",Color.parseColor("#ffaaaaaa"));

        if (TextUtils.isEmpty(billboardInfo.getStatusDesc())){
            ttvCompany.setVisibility(GONE);
        }else {
            ttvCompany.getTvDesc().setText(billboardInfo.getStatusDesc());//广告使用
        }
//        ttvAdSituation.getTvDesc().setText(billboardInfo.getStatusText()+"");//广告状态
        ttvAdSituation.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getStatusText(),""));//广告状态
//        ttvDetailsAddress.getTvDesc().setText(""+billboardInfo.getLongAddress());//详细地址
        ttvDetailsAddress.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getLongAddress(),""));//详细地址
//        ttvFacilitySituation.getTvDesc().setText(""+billboardInfo.getShedMaterial());//雨棚材质
        ttvFacilitySituation.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getShedMaterial(),""));//雨棚材质
//        ttvSpecification.getTvDesc().setText(""+billboardInfo.getSpec());//规格
        ttvSpecification.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getSpec(),""));//规格
        ttvRemark.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getOtherDescribe(),"无"));//备注

    }


}
