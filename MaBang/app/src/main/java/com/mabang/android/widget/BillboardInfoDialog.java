package com.mabang.android.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mabang.android.R;
import com.mabang.android.activity.worker.PictureUploadingActivity;
import com.mabang.android.config.Constants;
import com.mabang.android.entity.vo.BillboardInfo;
import com.mabang.android.utils.AppUtils;

import walke.base.tool.SpannableUtil;
import walke.base.tool.WindowUtil;


/**
 * @author View
 * @date 2016/12/16 8:35
 * <p>
 * 发现
 * 快速连续创建2个dialog，会产生指引异常，
 * 第二次创建时：确实会新建对象(mDialog+内部控件)，但使用这些对象时会指向第一次创建的对象
 * 故需要设置为全局变量
 */
public class BillboardInfoDialog extends MyDialog {
    private ImageView mIvLocation;
    private ImageView mIvCancel;
    private TextView mTvAddress;
    private TextView mTvManagerCode;
    private TextView mTvSoleCode;
    private TextTextView mTtvCompany;
    private TextTextView mTtvDetailsAddress;
    private TextTextView mTtvFacilitySituation;
    private TextTextView mTtvAdSituation;
    private TextTextView mTtvSpecification;
    private TextTextView mTtvRemark;
    private Button mBtBindLocation;
    private Button mBtUploadPhotoAd;
    private Button mBtUploadPhotoDown;
    private BillboardInfo mBillboardInfo;

    public BillboardInfoDialog(@NonNull Context context) {
        this(context, R.style.dialog_default);
    }

    public BillboardInfoDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init(context);

    }

    private void init(final Context context) {

        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_base_info, null);
        RelativeLayout layout = (RelativeLayout) inflate.findViewById(R.id.dbi_layout);
        ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
        int wWidth = WindowUtil.getWindowWidth(context);
        int dWidth = (int) (wWidth * 0.86);
        layoutParams.width = dWidth;
        layoutParams.height = (int) (dWidth * 1.32);
        layout.setLayoutParams(layoutParams);

        //“X”图标
        mIvCancel = (ImageView) inflate.findViewById(R.id.dbi_ivCancel);
        //地址
        mTvAddress = (TextView) inflate.findViewById(R.id.dbi_tvAddress);
        //定位图标
        mIvLocation = (ImageView) inflate.findViewById(R.id.dbi_ivLocation);
        //管理编号
        mTvManagerCode = (TextView) inflate.findViewById(R.id.dbi_tvManagerCode);
        //唯一码
        mTvSoleCode = (TextView) inflate.findViewById(R.id.dbi_tvSoleCode);
        //公司---广告使用：广州技客科技
        mTtvCompany = (TextTextView) inflate.findViewById(R.id.dbi_ttvCompany);
        //详细地址
        mTtvDetailsAddress = (TextTextView) inflate.findViewById(R.id.dbi_ttvDetailsAddress);
        //设备状态：雨棚状态
        mTtvFacilitySituation = (TextTextView) inflate.findViewById(R.id.dbi_ttvFacilitySituation);
        //广告状态：已使用/空闲
        mTtvAdSituation = (TextTextView) inflate.findViewById(R.id.dbi_ttvAdSituation);
        //规格
        mTtvSpecification = (TextTextView) inflate.findViewById(R.id.dbi_ttvSpecification);
        //备注
        mTtvRemark = (TextTextView) inflate.findViewById(R.id.dbi_ttvRemark);

        //绑定当前定位
        mBtBindLocation = (Button) inflate.findViewById(R.id.dbi_btBindLocation);
        //广告图片上传
        mBtUploadPhotoAd = (Button) inflate.findViewById(R.id.dbi_btUploadPhotoAd);
        //验收图片上传
        mBtUploadPhotoDown = (Button) inflate.findViewById(R.id.dbi_btUploadPhotoDown);


        mIvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BillboardInfoDialog.this.isShowing()) {
                    BillboardInfoDialog.this.dismiss();
                }
            }
        });
        mIvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnButtonClickListener != null)
                    mOnButtonClickListener.onClickLocation(mBillboardInfo);
            }
        });
        mBtBindLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnButtonClickListener != null)
                    mOnButtonClickListener.onClickBinding(mBillboardInfo);
            }
        });
        mBtUploadPhotoAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (BillboardInfoDialog.this.isShowing())
//                    BillboardInfoDialog.this.dismiss();
                if (mBillboardInfo!=null) {
                    // TODO: 2018/2/9 去上传广告图片
                    Intent intent = new Intent(context, PictureUploadingActivity.class);
                    //添加一些参数
                    intent.putExtra(Constants.BILLBOARD_INFO, mBillboardInfo);
                    intent.putExtra(Constants.PICTURE_UPLOAD_TYPE, Constants.ADS_PICTURE_UPLOAD);
                    context.startActivity(intent);
                }
            }
        });
        mBtUploadPhotoDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (BillboardInfoDialog.this.isShowing())
//                    BillboardInfoDialog.this.dismiss();
                if (mBillboardInfo!=null) {
                    // TODO: 2018/2/9 去上传广告图片
                    Intent intent = new Intent(context, PictureUploadingActivity.class);
                    //添加一些参数
                    intent.putExtra(Constants.BILLBOARD_INFO, mBillboardInfo);
                    intent.putExtra(Constants.PICTURE_UPLOAD_TYPE, Constants.DOWN_PICTURE_UPLOAD);
                    context.startActivity(intent);
                }
            }
        });

        this.setContentView(inflate, new ViewGroup.LayoutParams(WindowUtil.getWindowWidth(context), WindowUtil.getWindowHeight(context)));
        this.setCancelable(false);//点击其他区域无响应
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    /**
     * @return 基本信息弹窗
     */
    public void setBillboardInfo(@NonNull  BillboardInfo billboardInfo) {
        if (billboardInfo == null)
            return;
        mBillboardInfo = billboardInfo;
        mTvAddress.setText(AppUtils.textReplace(billboardInfo.getShortName(), ""));
        SpannableUtil.spannableOneColor(mTvManagerCode, "管理编号: ",
                AppUtils.textReplace(billboardInfo.getManageCode(), ""),
                "", Color.parseColor(Constants.color_textGray));
        SpannableUtil.spannableOneColor(mTvSoleCode, "唯一码: ",
                AppUtils.textReplace(billboardInfo.getUniqueCode(), ""),
                "", Color.parseColor(Constants.color_textGray));

        // TODO: 2018/3/6 改成对应字段
        if (TextUtils.isEmpty(billboardInfo.getStatusDesc())) {
            mTtvCompany.setVisibility(View.GONE);
        } else {
            mTtvCompany.getTvDesc().setText(billboardInfo.getStatusDesc());//广告使用(者)
        }
        mTtvAdSituation.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getStatusText(), ""));//广告状态
        mTtvDetailsAddress.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getLongAddress(), ""));//详细地址
        mTtvFacilitySituation.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getShedMaterial(), ""));//材质
        mTtvSpecification.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getSpec(), ""));//规格
        mTtvRemark.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getOtherDescribe(), "无"));//备注

        String locationLat = billboardInfo.getLocationLat();
        String locationLng = billboardInfo.getLocationLng();
        if (TextUtils.isEmpty(locationLat) || TextUtils.isEmpty(locationLng)) {
            mBtBindLocation.setVisibility(View.VISIBLE);
            mIvLocation.setVisibility(View.GONE);
            mBtUploadPhotoAd.setVisibility(View.GONE);
            mBtUploadPhotoDown.setVisibility(View.GONE);
        } else {
            mBtBindLocation.setVisibility(View.GONE);
            mBtUploadPhotoAd.setVisibility(View.VISIBLE);
            mBtUploadPhotoDown.setVisibility(View.VISIBLE);
            mIvLocation.setVisibility(View.VISIBLE);
        }

    }

    public interface OnButtonClickListener {
        void onClickLocation(BillboardInfo billboardInfo);

        void onClickBinding(BillboardInfo info);
    }

    private OnButtonClickListener mOnButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        mOnButtonClickListener = onButtonClickListener;
    }
}
