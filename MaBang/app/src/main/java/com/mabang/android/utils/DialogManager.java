package com.mabang.android.utils;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mabang.android.R;
import com.mabang.android.activity.base.AppActivity;
import com.mabang.android.activity.worker.PictureUploadingActivity;
import com.mabang.android.config.Constants;
import com.mabang.android.entity.vo.BillboardInfo;
import com.mabang.android.widget.MyDialog;
import com.mabang.android.widget.TextTextView;

import walke.base.BaseActivity;
import walke.base.tool.SpannableUtil;
import walke.base.tool.ToastUtil;
import walke.base.tool.WindowUtil;
import walke.base.widget.TextInputView;

import static walke.base.tool.Util.IsMobile;
import static walke.base.tool.Util.IsPhone;


/**
 * @author View
 * @date 2016/12/16 8:35
 * <p>
 * 发现
 * 快速连续创建2个dialog，会产生指引异常，
 * 第二次创建时：确实会新建对象(mDialog+内部控件)，但使用这些对象时会指向第一次创建的对象
 * 故需要设置为全局变量
 */
public class DialogManager {
    private static Dialog mDialog;
    /**
     * @return 预定广告位弹窗
     */
    public static void dialogBookingAd(final BaseActivity baseActivity, final BookingButtonClickListener bookingButtonClickListener) {
        // 设置背景颜色变暗
        final WindowManager.LayoutParams lp = baseActivity.getWindow().getAttributes();
        lp.alpha = 0.5f;
        baseActivity.getWindow().setAttributes(lp);
        View inflate = LayoutInflater.from(baseActivity).inflate(R.layout.dialog_booking_ad, null);
        ImageView ivCancel = (ImageView) inflate.findViewById(R.id.dba_ivCancel);
        Button btBookNow = (Button) inflate.findViewById(R.id.dba_btBookNow);
        final TextInputView tivName = (TextInputView) inflate.findViewById(R.id.dba_tivName);
        final TextInputView tivPhone = (TextInputView) inflate.findViewById(R.id.dba_tivPhone);
        final TextInputView tivCompany = (TextInputView) inflate.findViewById(R.id.dba_tivCompany);
//        tivName.getEtInput().setText("walke");
//        tivPhone.getEtInput().setText("18218649345");
//        tivCompany.getEtInput().setText("广州技客科技");
        //手机输入限制
//        tivPhone.getEtInput().setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});//长度：11
//        tivPhone.getEtInput().setInputType(InputType.TYPE_CLASS_NUMBER);//类型：数字

        String digists = "0123456789-";
        tivPhone.getEtInput().setKeyListener(DigitsKeyListener.getInstance(digists));
        tivPhone.getEtInput().setFilters(new InputFilter[]{new MyInputFilter()});

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDismiss(baseActivity);
            }
        });
        btBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String company = tivCompany.getEtInput().getText().toString().trim();
                if (TextUtils.isEmpty(company)){
                    ToastUtil.showToast(baseActivity,"公司信息不能为空");
                    return;
                }
                String phone = tivPhone.getEtInput().getText().toString().trim();

                if(!TextUtils.isEmpty(phone)){//当联系电话有输入
                    if(!(IsPhone(phone) || IsMobile(phone))) {
                        ToastUtil.showToast(baseActivity, "联系方式格式错误");
                        return;
                    }

                }
                if (bookingButtonClickListener != null)
                    bookingButtonClickListener.onClicked(
                            tivName.getEtInput().getText().toString().trim(),
                            phone,
                            company);
            }
        });
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
        mDialog = new Dialog(baseActivity, R.style.dialog_default);
        int wWidth = WindowUtil.getWindowWidth(baseActivity);
        int dWidth = (int) (wWidth * 0.95);
        mDialog.setContentView(inflate, new ViewGroup.LayoutParams(dWidth, (int) (dWidth * 1.32)));
        mDialog.setCancelable(false);//点击其他区域无响应
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDialog.show();
    }

    /**
     * @return 基本信息弹窗
     */
    public static void dialogBillboardInfoOneButton(final BaseActivity baseActivity, final BillboardInfo billboardInfo, String btText, final OneButtonClickListener oneButtonClickListener) {
        // 设置背景颜色变暗 用这个与地图冲突
//        final WindowManager.LayoutParams lp = baseActivity.getWindow().getAttributes();
//        lp.alpha = 0.5f;
//        baseActivity.getWindow().setAttributes(lp);
        View inflate = LayoutInflater.from(baseActivity).inflate(R.layout.dialog_base_info, null);
        RelativeLayout layout = (RelativeLayout) inflate.findViewById(R.id.dbi_layout);
        ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
        int wWidth = WindowUtil.getWindowWidth(baseActivity);
        int dWidth = (int) (wWidth * 0.86);
        layoutParams.width=dWidth;
        layoutParams.height=(int) (dWidth * 1.32);
        layout.setLayoutParams(layoutParams);

        ImageView ivCancel = (ImageView) inflate.findViewById(R.id.dbi_ivCancel);//“X”图标
        TextView tvAddress = (TextView) inflate.findViewById(R.id.dbi_tvAddress);//地址
        ImageView ivLocation = (ImageView) inflate.findViewById(R.id.dbi_ivLocation);//定位图标
        ImageView ivEdit = (ImageView) inflate.findViewById(R.id.dbi_ivEdit);//定位图标
        TextView tvManagerCode = (TextView) inflate.findViewById(R.id.dbi_tvManagerCode);//管理编号
        TextView tvSoleCode = (TextView) inflate.findViewById(R.id.dbi_tvSoleCode);//唯一码
        TextTextView ttvCompany = (TextTextView) inflate.findViewById(R.id.dbi_ttvCompany);//公司---广告使用：广州技客科技
        TextTextView ttvDetailsAddress = (TextTextView) inflate.findViewById(R.id.dbi_ttvDetailsAddress);//详细地址
        TextTextView ttvFacilitySituation = (TextTextView) inflate.findViewById(R.id.dbi_ttvFacilitySituation);//设备状态：雨棚状态
        TextTextView ttvAdSituation = (TextTextView) inflate.findViewById(R.id.dbi_ttvAdSituation);//广告状态：已使用/空闲
        TextTextView ttvSpecification = (TextTextView) inflate.findViewById(R.id.dbi_ttvSpecification);//规格
        TextTextView ttvRemark = (TextTextView) inflate.findViewById(R.id.dbi_ttvRemark);//备注
        ivLocation.setVisibility(View.GONE);
        ivEdit.setVisibility(View.GONE);

//        tvAddress.setText(billboardInfo.getShortName());
        tvAddress.setText(AppUtils.textReplace(billboardInfo.getShortName(),""));
        SpannableUtil.spannableOneColor(tvManagerCode,"管理编号: ",
//                billboardInfo.getManageCode()+"",
                AppUtils.textReplace(billboardInfo.getManageCode(),""),
                "", Color.parseColor(Constants.color_textGray));
        SpannableUtil.spannableOneColor(tvSoleCode,"唯一码: ",
//                billboardInfo.getUniqueCode()+"",
                AppUtils.textReplace(billboardInfo.getUniqueCode(),""),
                "", Color.parseColor(Constants.color_textGray));

        // TODO: 2018/3/6 改成对应字段
        if (TextUtils.isEmpty(billboardInfo.getStatusDesc())){
            ttvCompany.setVisibility(View.GONE);
        }else {
            ttvCompany.getTvDesc().setText(billboardInfo.getStatusDesc());//广告使用(者)
        }
//        ttvAdSituation.getTvDesc().setText(billboardInfo.getStatusText());//广告状态
        ttvAdSituation.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getStatusText(),""));//广告状态
//        ttvDetailsAddress.getTvDesc().setText(billboardInfo.getLongAddress());//详细地址
        ttvDetailsAddress.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getLongAddress(),""));//详细地址
//        ttvFacilitySituation.getTvDesc().setText(billboardInfo.getShedMaterial());//材质
        ttvFacilitySituation.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getShedMaterial(),""));//材质
//        ttvSpecification.getTvDesc().setText(billboardInfo.getSpec());//规格
        ttvSpecification.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getSpec(),""));//规格
        ttvRemark.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getOtherDescribe(),"无"));//备注

        Button btBindLocation = (Button) inflate.findViewById(R.id.dbi_btBindLocation);//绑定当前定位
        Button btUploadPhotoAd = (Button) inflate.findViewById(R.id.dbi_btUploadPhotoAd);//广告图片上传
        Button btUploadPhotoDown = (Button) inflate.findViewById(R.id.dbi_btUploadPhotoDown);//验收图片上传
        btBindLocation.setVisibility(View.VISIBLE);
        btBindLocation.setText(btText+"");
        btUploadPhotoAd.setVisibility(View.GONE);
        btUploadPhotoDown.setVisibility(View.GONE);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDismiss(baseActivity);
            }
        });
        btBindLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oneButtonClickListener !=null)
                    oneButtonClickListener.onClicked(billboardInfo);
            }
        });

        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
        mDialog = new MyDialog(baseActivity, R.style.dialog_default);
//        int wWidth = WindowUtil.getWindowWidth(baseActivity);
//        int dWidth = (int) (wWidth * 0.86);
//        mDialog.setContentView(inflate, new ViewGroup.LayoutParams(dWidth, (int) (dWidth * 1.30)));//780/600
        mDialog.setContentView(inflate, new ViewGroup.LayoutParams(WindowUtil.getWindowWidth(baseActivity),WindowUtil.getWindowHeight(baseActivity)));
        mDialog.setCancelable(false);//点击其他区域无响应
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDialog.show();
    }

    /**
     * @return 基本信息弹窗
     */
    /**
     * @return 基本信息弹窗
     */
    public static void bindLocation(final BaseActivity baseActivity, final BillboardInfo billboardInfo, String btText, final BindLocationClickListener bindLocationClickListener) {

        View inflate = LayoutInflater.from(baseActivity).inflate(R.layout.dialog_base_info, null);
        RelativeLayout layout = (RelativeLayout) inflate.findViewById(R.id.dbi_layout);
        ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
        int wWidth = WindowUtil.getWindowWidth(baseActivity);
        int dWidth = (int) (wWidth * 0.86);
        layoutParams.width=dWidth;
        layoutParams.height=(int) (dWidth * 1.32);
        layout.setLayoutParams(layoutParams);

        ImageView ivCancel = (ImageView) inflate.findViewById(R.id.dbi_ivCancel);//“X”图标
        TextView tvAddress = (TextView) inflate.findViewById(R.id.dbi_tvAddress);//地址
        ImageView ivLocation = (ImageView) inflate.findViewById(R.id.dbi_ivLocation);//定位图标
        TextView tvManagerCode = (TextView) inflate.findViewById(R.id.dbi_tvManagerCode);//管理编号
        TextView tvSoleCode = (TextView) inflate.findViewById(R.id.dbi_tvSoleCode);//唯一码
        TextTextView ttvCompany = (TextTextView) inflate.findViewById(R.id.dbi_ttvCompany);//公司---广告使用：广州技客科技
        TextTextView ttvDetailsAddress = (TextTextView) inflate.findViewById(R.id.dbi_ttvDetailsAddress);//详细地址
        TextTextView ttvFacilitySituation = (TextTextView) inflate.findViewById(R.id.dbi_ttvFacilitySituation);//设备状态：雨棚状态
        TextTextView ttvAdSituation = (TextTextView) inflate.findViewById(R.id.dbi_ttvAdSituation);//广告状态：已使用/空闲
        TextTextView ttvSpecification = (TextTextView) inflate.findViewById(R.id.dbi_ttvSpecification);//规格
        TextTextView ttvRemark = (TextTextView) inflate.findViewById(R.id.dbi_ttvRemark);//备注
        ivLocation.setVisibility(View.VISIBLE);
        ivLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bindLocationClickListener!=null)
                    bindLocationClickListener.ivLocationClick();
            }
        });
//        tvAddress.setText(billboardInfo.getShortName());
        tvAddress.setText(AppUtils.textReplace(billboardInfo.getShortName(),""));
        SpannableUtil.spannableOneColor(tvManagerCode,"管理编号: ",
//                billboardInfo.getManageCode()+"",
                AppUtils.textReplace(billboardInfo.getManageCode(),""),
                "", Color.parseColor(Constants.color_textGray));
        SpannableUtil.spannableOneColor(tvSoleCode,"唯一码: ",
//                billboardInfo.getUniqueCode()+"",
                AppUtils.textReplace(billboardInfo.getUniqueCode(),""),
                "", Color.parseColor(Constants.color_textGray));

        // TODO: 2018/3/6 改成对应字段
        if (TextUtils.isEmpty(billboardInfo.getStatusDesc()))
            ttvCompany.setVisibility(View.GONE);
        else
            ttvCompany.getTvDesc().setText(billboardInfo.getStatusDesc());//广告使用(者)
//        ttvAdSituation.getTvDesc().setText(billboardInfo.getStatusText());//广告状态
        ttvAdSituation.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getStatusText(),""));//广告状态
//        ttvDetailsAddress.getTvDesc().setText(billboardInfo.getLongAddress());//详细地址
        ttvDetailsAddress.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getLongAddress(),""));//详细地址
//        ttvFacilitySituation.getTvDesc().setText(billboardInfo.getShedMaterial());//材质
        ttvFacilitySituation.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getShedMaterial(),""));//材质
//        ttvSpecification.getTvDesc().setText(billboardInfo.getSpec());//规格
        ttvSpecification.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getSpec(),""));//规格
        ttvRemark.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getOtherDescribe(),"无"));//备注

        Button btBindLocation = (Button) inflate.findViewById(R.id.dbi_btBindLocation);//绑定当前定位
        Button btUploadPhotoAd = (Button) inflate.findViewById(R.id.dbi_btUploadPhotoAd);//广告图片上传
        Button btUploadPhotoDown = (Button) inflate.findViewById(R.id.dbi_btUploadPhotoDown);//验收图片上传
        btBindLocation.setVisibility(View.VISIBLE);
        btBindLocation.setText(btText+"");
        btUploadPhotoAd.setVisibility(View.GONE);
        btUploadPhotoDown.setVisibility(View.GONE);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDismiss(baseActivity);
            }
        });
        btBindLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bindLocationClickListener!=null)
                    bindLocationClickListener.btBindingClick(billboardInfo);
            }
        });

        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
        mDialog = new MyDialog(baseActivity, R.style.dialog_default);
        mDialog.setContentView(inflate, new ViewGroup.LayoutParams(WindowUtil.getWindowWidth(baseActivity),WindowUtil.getWindowHeight(baseActivity)));
        mDialog.setCancelable(false);//点击其他区域无响应
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDialog.show();
    }

    /**
     * @return 基本信息弹窗
     */
    public static void dialogBaseInfoUploadPhoto(final AppActivity appActivity, final BillboardInfo billboardInfo, final LocationClickListener clickListener) {

        View inflate = LayoutInflater.from(appActivity).inflate(R.layout.dialog_base_info, null);
        RelativeLayout layout = (RelativeLayout) inflate.findViewById(R.id.dbi_layout);
        ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
        int wWidth = WindowUtil.getWindowWidth(appActivity);
        int dWidth = (int) (wWidth * 0.86);
        layoutParams.width=dWidth;
        layoutParams.height=(int) (dWidth * 1.32);
        layout.setLayoutParams(layoutParams);

        ImageView ivCancel = (ImageView) inflate.findViewById(R.id.dbi_ivCancel);//“X”图标
        TextView tvAddress = (TextView) inflate.findViewById(R.id.dbi_tvAddress);//地址
        ImageView ivLocation = (ImageView) inflate.findViewById(R.id.dbi_ivLocation);//定位图标
        TextView tvManagerCode = (TextView) inflate.findViewById(R.id.dbi_tvManagerCode);//管理编号
        TextView tvSoleCode = (TextView) inflate.findViewById(R.id.dbi_tvSoleCode);//唯一码
        TextTextView ttvCompany = (TextTextView) inflate.findViewById(R.id.dbi_ttvCompany);//公司---广告使用：广州技客科技
        TextTextView ttvDetailsAddress = (TextTextView) inflate.findViewById(R.id.dbi_ttvDetailsAddress);//详细地址
        TextTextView ttvFacilitySituation = (TextTextView) inflate.findViewById(R.id.dbi_ttvFacilitySituation);//雨棚材质
        TextTextView ttvAdSituation = (TextTextView) inflate.findViewById(R.id.dbi_ttvAdSituation);//广告状态：已使用/空闲
        TextTextView ttvSpecification = (TextTextView) inflate.findViewById(R.id.dbi_ttvSpecification);//规格
        TextTextView ttvRemark = (TextTextView) inflate.findViewById(R.id.dbi_ttvRemark);//备注
        ivLocation.setVisibility(View.VISIBLE);
        ivLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener!=null)
                    clickListener.click();
            }
        });

//        tvAddress.setText(billboardInfo.getShortName());
        tvAddress.setText(AppUtils.textReplace(billboardInfo.getShortName(),""));
        SpannableUtil.spannableOneColor(tvManagerCode,"管理编号: ",
//                billboardInfo.getManageCode()+"",
                AppUtils.textReplace(billboardInfo.getManageCode(),""),
                "", Color.parseColor(Constants.color_textGray));
        SpannableUtil.spannableOneColor(tvSoleCode,"唯一码: ",
//                billboardInfo.getUniqueCode()+"",
                AppUtils.textReplace(billboardInfo.getUniqueCode(),""),
                "", Color.parseColor(Constants.color_textGray));

        // TODO: 2018/3/6 改成对应字段
        if (TextUtils.isEmpty(billboardInfo.getStatusDesc())){
            ttvCompany.setVisibility(View.GONE);
        }else {
            ttvCompany.getTvDesc().setText(billboardInfo.getStatusDesc());//广告使用(者)
        }

//        ttvAdSituation.getTvDesc().setText(billboardInfo.getStatusText());//广告状态
        ttvAdSituation.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getStatusText(),""));//广告状态
//        ttvDetailsAddress.getTvDesc().setText(billboardInfo.getLongAddress());//详细地址
        ttvDetailsAddress.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getLongAddress(),""));//详细地址
//        ttvFacilitySituation.getTvDesc().setText(billboardInfo.getShedMaterial());//材质
        ttvFacilitySituation.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getShedMaterial(),""));//材质
//        ttvSpecification.getTvDesc().setText(billboardInfo.getSpec());//规格
        ttvSpecification.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getSpec(),""));//规格
        ttvRemark.getTvDesc().setText(AppUtils.textReplace(billboardInfo.getOtherDescribe(),"无"));//备注

        Button btBindLocation = (Button) inflate.findViewById(R.id.dbi_btBindLocation);//绑定当前定位
        Button btUploadPhotoAd = (Button) inflate.findViewById(R.id.dbi_btUploadPhotoAd);//广告图片上传
        Button btUploadPhotoDown = (Button) inflate.findViewById(R.id.dbi_btUploadPhotoDown);//验收图片上传
        btBindLocation.setVisibility(View.GONE);
        btUploadPhotoAd.setVisibility(View.VISIBLE);
        btUploadPhotoDown.setVisibility(View.VISIBLE);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDismiss(appActivity);
            }
        });

        btUploadPhotoAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2018/2/9 去上传广告图片
                Intent intent = new Intent(appActivity, PictureUploadingActivity.class);
                //添加一些参数
                intent.putExtra(Constants.BILLBOARD_INFO,billboardInfo);
                intent.putExtra(Constants.PICTURE_UPLOAD_TYPE,Constants.ADS_PICTURE_UPLOAD);
//                appActivity.startActivityForResult(intent,WorkerHomeActivity.CODE_START_ACTIVITY_FORRESULT);
                appActivity.startActivity(intent);
                dialogDismiss(appActivity);
            }
        });
        btUploadPhotoDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2018/2/9 去上传广告图片
                Intent intent = new Intent(appActivity, PictureUploadingActivity.class);
                //添加一些参数
                intent.putExtra(Constants.BILLBOARD_INFO,billboardInfo);
                intent.putExtra(Constants.PICTURE_UPLOAD_TYPE,Constants.DOWN_PICTURE_UPLOAD);
//                appActivity.startActivityForResult(intent,WorkerHomeActivity.CODE_START_ACTIVITY_FORRESULT);
                appActivity.startActivity(intent);
                dialogDismiss(appActivity);
            }
        });

        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
        mDialog = new MyDialog(appActivity, R.style.dialog_default);
        mDialog.setContentView(inflate, new ViewGroup.LayoutParams(WindowUtil.getWindowWidth(appActivity),WindowUtil.getWindowHeight(appActivity)));
        mDialog.setCancelable(false);//点击其他区域无响应
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDialog.show();
    }

    public interface LocationClickListener {
        void click();
    }

    public interface BookingButtonClickListener {
        void onClicked(String name,String phone,String company);
    }

    public interface OneButtonClickListener {
        void onClicked(BillboardInfo billboardInfo);
    }

    public interface BindLocationClickListener {
        void ivLocationClick();
        void btBindingClick(BillboardInfo info);
    }

    public interface UploadPhotoButtonClickListener {
        void onClickUploadAdPhoto();//广告图片上传
        void onClickUploadDownPhoto();//验收图片上传
    }

    public static void dialogDismiss(BaseActivity activity){
        if (mDialog!=null&&mDialog.isShowing()){
            mDialog.dismiss();
            mDialog=null;
            // 设置背景颜色变暗
            final WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = 1f;
            activity.getWindow().setAttributes(lp);
        }
    }

}
