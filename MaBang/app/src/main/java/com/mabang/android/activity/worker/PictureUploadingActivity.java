package com.mabang.android.activity.worker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.mabang.android.R;
import com.mabang.android.activity.base.AppActivity;
import com.mabang.android.adapter.PictureUploadingAdapter;
import com.mabang.android.config.Api;
import com.mabang.android.config.Constants;
import com.mabang.android.config.MBPermissions;
import com.mabang.android.entity.vo.BillboardImageInfo;
import com.mabang.android.entity.vo.BillboardInfo;
import com.mabang.android.entity.vo.Message;
import com.mabang.android.okhttp.HttpReuqest;
import com.mabang.android.utils.AppUtils;
import com.mabang.android.utils.CameraUtil;
import com.mabang.android.utils.OssManager;
import com.mabang.android.widget.AdDetailsView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import walke.base.tool.BitmapUtil;
import walke.base.tool.FileSizeUtil;
import walke.base.tool.FileUtil;
import walke.base.tool.LoadingDialog;
import walke.base.tool.PermissionUtil;
import walke.base.tool.UriUtil;
import walke.base.widget.DialogUtil;

/**
 * Created by walke on 2018/2/4.
 * email:1032458982@qq.com
 */

public class PictureUploadingActivity extends AppActivity {

    private static final String mDirGuangGao = "cover/";//上传OSS广告图片文件
    private static final String mDirYanShou = "snapshot/";

    private AdDetailsView adDetailsView;
    private Button btCommit;
    private PictureUploadingAdapter mBitmapAdapter;
    private BillboardInfo mBillboardInfo;
    private OssManager mOssManager;
    private String mDir = mDirGuangGao;
    private Dialog ossUploadDialog;

    private List<BillboardImageInfo> mImageInfos = new ArrayList<>();

    private RecyclerView rvPictures;
    private TextView tvUploadType;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_picture_uploading;
    }

    @Override
    protected void initView() {
        initTitleViewById(R.id.apu_TitleVIew);
        rvPictures = ((RecyclerView) findViewById(R.id.apu_rvPictures));
        // 设置布局管理器  3表示列数
        rvPictures.setLayoutManager(new GridLayoutManager(this, 4));
        rvPictures.setItemAnimator(new DefaultItemAnimator());

        adDetailsView = ((AdDetailsView) findViewById(R.id.apu_adDetailsView));
        btCommit = ((Button) findViewById(R.id.apu_btCommit));
        tvUploadType = ((TextView) findViewById(R.id.apu_tvUploadType));
        btCommit.setOnClickListener(this);

        ossUploadDialog = LoadingDialog.createDialog(this, "请稍等");
    }

    @Override
    protected void initData() {
        checkPermission(MBPermissions.PERMISSION_CODE);

        initPictures();
        Intent intent = getIntent();
        if (intent != null) {
            String type = intent.getStringExtra(Constants.PICTURE_UPLOAD_TYPE);
            if (Constants.ADS_PICTURE_UPLOAD.equals(type)) {
                mTitleView.getTvCenter().setText("广告图片上传");
                tvUploadType.setText("上传广告位图片");
                mDir = mDirGuangGao;
            } else {
                mTitleView.getTvCenter().setText("验收图片上传");
                tvUploadType.setText("上传验收图片");
                mDir = mDirYanShou;
            }
            mBillboardInfo = (BillboardInfo) intent.getSerializableExtra(Constants.BILLBOARD_INFO);
            if (mBillboardInfo != null) {
                requestBillboardInfo(mBillboardInfo.getManageCode());
                adDetailsView.setViewByData(mBillboardInfo);
            } else {
                logI("信息有误请重试");
            }
        }

    }

    /**
     * 广告位信息 直接按管理码查询
     */
    private void requestBillboardInfo(String manageCode) {
        BillboardInfo billboardInfo = new BillboardInfo();
        // TODO: 2018/2/14 设置对应参数
        billboardInfo.setAccount(getMemberInfo().getAccount());
        billboardInfo.setToken(getMemberInfo().getToken());
        // TODO: 2018/2/21  替换具体数据
        billboardInfo.setManageCode(manageCode);
        httpReuqest.sendMessage(Api.Worker_billboard, billboardInfo, true, new HttpReuqest.CallBack<BillboardInfo>() {
            @Override
            public void onSuccess(Message message, BillboardInfo result) {
                if (Api.OK == result.getCode()) {
                    // TODO: 2018/2/16 获取相应数据做对应显示
                    if (result.getAliyunInfo()!=null){
                        mOssManager = OssManager.getInstance().init(PictureUploadingActivity.this,result.getAliyunInfo());
                    }
                    mBillboardInfo = result;
                    if (mDir.equals(mDirYanShou)) {
                        List<BillboardImageInfo> acceptanceImageList = mBillboardInfo.getAcceptanceImageList();
                        mImageInfos = acceptanceImageList;//验收图片集合
                    } else {
                        List<BillboardImageInfo> advertisingImageList = mBillboardInfo.getAdvertisingImageList();
                        mImageInfos = advertisingImageList;//广告图片集合
                    }
                    initPictures();

                }//
//                toast(result.getText() + "");
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


    private void initPictures() {
        mBitmapAdapter = new PictureUploadingAdapter(this, mImageInfos);
        rvPictures.setAdapter(mBitmapAdapter);
        mBitmapAdapter.setOnPictureSelectListener(new PictureUploadingAdapter.OnPictureSelectListener() {
            @Override
            public void onAdd(int position) {
                // TODO: 2018/2/5 获取手机相册图片或者启动相机拍照上传
                DialogUtil.showChangeHeaderTips(PictureUploadingActivity.this, rvPictures, new DialogUtil.PWTwoButtonClickListener() {
                    @Override
                    public void OnTopClick() {//
                        toPhotos();
                    }

                    @Override
                    public void OnBottomClick() {
                        toCamera();
                    }
                });
            }

            @Override
            public void onDelete(final int position) {
                DialogUtil.tipsTwoButton(PictureUploadingActivity.this, "否", "是", "您要删除该图片？", new DialogUtil.TwoButtonClickListener() {
                    @Override
                    public void leftOnClick() {

                    }

                    @Override
                    public void rightOnClick() {
                        if (position<mImageInfos.size()) {
                            String objName = mImageInfos.get(position).getKey();
                            upLoadPhotoInfo(objName, 1, position);
                        }else {
                            logI("onDelete  数组越界异常");
                        }
                    }
                });

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.apu_btCommit:
                finish();
                break;
        }
    }

    /**
     * 拍照保存的图片文件
     */
    private File mPictureFile;
    /**
     * 拍照压缩保存的图片文件
     */
    private File mTagFile;

    /**
     * 请求识别码
     */
    private static final int CODE_GALLERY_REQUEST = 0xa0;//启动相册意图请求码
    private static final int CODE_CAMERA_REQUEST = 0xa1; //启动照相机意图请求码
    private int REQUEST_CAMERA = 0xa2;
    private int REQUEST_SDCARD = 0xa3;

    /**
     * 从本地相册选取图片作为头像
     */

    public void toPhotos() {
        if (PermissionUtil.isFitPermissions(this, MBPermissions.PERMISSIONS2)) {
            openPhotoWall();
        } else {
            toast("请授予必要权限后重试");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(MBPermissions.PERMISSIONS2, REQUEST_SDCARD);
            }
        }
    }

    private void openPhotoWall() {
        Intent intentFromGallery = new Intent(Intent.ACTION_PICK, null);
        intentFromGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    /**
     * 启动手机相机拍摄照片作为头像
     */
    public void toCamera() {
        if (!CameraUtil.hasCamera(this)) {
            toast("抱歉，发现无可用相机");
            return;
        }
        if (PermissionUtil.isFitPermissions(this, MBPermissions.PERMISSIONS2)) {
            openCamera();
        } else {
            toast("请授予必要权限后重试");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(MBPermissions.PERMISSIONS2, REQUEST_CAMERA);
            }
        }
    }

    private void openCamera() {
        long currentThreadTimeMillis = SystemClock.currentThreadTimeMillis();
        if (android.os.Build.VERSION.SDK_INT < 24) {
            Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 判断存储卡是否可用，存储照片文件
            if (FileUtil.isSDcardAvailable()) {
                AppUtils.makeAppRootDir();
                mPictureFile = new File(Constants.APP_LOCATION, currentThreadTimeMillis + ".jpg");
                intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPictureFile));
            }
            startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
        } else {//Android 7.0后 不能之前的，会报错
            String cachePath = (getExternalFilesDir(Constants.FILE_PROVIDER_PATHS) + File.separator + currentThreadTimeMillis + ".jpg");//
            mPictureFile = new File(cachePath);
            CameraUtil.showCameraAction(this, CODE_CAMERA_REQUEST, mPictureFile);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PermissionUtil.isGetAllPermission(grantResults)) {
            logI("onRequestPermissionsResult: ---已获得获得全部授权");
            //取得全部权限
            if (requestCode == REQUEST_CAMERA) {
                openCamera();
            } else if (requestCode == REQUEST_SDCARD) {
                openPhotoWall();
            }
        } else {
            logI("onRequestPermissionsResult: ---未获得获得全部授权");
        }
    }


    /**
     * 1.以“content://”打头的是ContentProvider应用，可以表示数据库中的一张表，或者一条 数据。
     * 当表示具体的一条数据时，往往是以数据表的ID为结尾的：content://com.test.tab/1。
     * 2.以"file://"打头的表示引用的是一个文件的路径地址
     * 3.当然还有其他的格式：“http://”,"ftp://"等等
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 用户没有进行有效的设置操作，返回
        if (resultCode == Activity.RESULT_CANCELED) {
            toast("取消");
            return;
        }
        switch (requestCode) {
            case CODE_GALLERY_REQUEST://Android 7.0后一样获取图片，调用系统截图不同
                Uri dataUri = data.getData();
                Bundle extras = data.getExtras();//extras=null
                String realFilePath = UriUtil.getRealFilePath(this, dataUri);
                double imgSize = FileSizeUtil.getFileOrFilesSize(realFilePath, FileSizeUtil.SIZETYPE_B);
//                commit(realFilePath);
                Bitmap bitmap = BitmapUtil.getBitmap(realFilePath);
                mTagFile = BitmapUtil.bitmapToFile(bitmap,"aa"+System.currentTimeMillis());
                commit(mTagFile.getAbsolutePath());
                break;
            case CODE_CAMERA_REQUEST:
                if (android.os.Build.VERSION.SDK_INT < 24) {
                    boolean b = mPictureFile != null && mPictureFile.exists();
                    if (b) {
                        double filesSize = FileSizeUtil.getFileOrFilesSize(mPictureFile.getAbsolutePath(), FileSizeUtil.SIZETYPE_B);
                        if (filesSize > 0) {
//                            commit(mPictureFile.getAbsolutePath());
                            Bitmap bm = BitmapUtil.getBitmap(mPictureFile.getAbsolutePath());
                            long lTime = System.currentTimeMillis();
                            mTagFile = BitmapUtil.bitmapToFile(bm,"aa"+ lTime);
                            commit(mTagFile.getAbsolutePath());
                        }
                    }
                } else {//Android 7.0后
                    boolean b = mPictureFile != null && mPictureFile.exists();
                    if (b) {
                        double filesSize = FileSizeUtil.getFileOrFilesSize(mPictureFile.getAbsolutePath(), FileSizeUtil.SIZETYPE_B);
                        if (filesSize > 0) {
//                            commit(mPictureFile.getAbsolutePath());
                            Bitmap bm = BitmapUtil.getBitmap(mPictureFile.getAbsolutePath());
                            long lTime = System.currentTimeMillis();
                            mTagFile = BitmapUtil.bitmapToFile(bm,"aa"+ lTime);
                            commit(mTagFile.getAbsolutePath());
                        }
                    }
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void commit(String filePath) {
        String replace = UUID.randomUUID().toString().replace("-", "");
        final String objectName = mDir + replace + ".jpg";
        mOssManager.uploading(this, objectName, filePath, new OssManager.OnPictureUploadListener() {
            @Override
            public void success(PutObjectRequest request, PutObjectResult result) {
                logI("success 上传成功");
                upLoadPhotoInfo(objectName, 0, -1);
            }

            @Override
            public void error(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                toast("提交失败，请重试");
                if (ossUploadDialog != null && ossUploadDialog.isShowing())
                    ossUploadDialog.dismiss();
                logI("success 上传失败");
            }
        });
    }

    /**
     * @param objectName
     * @param type
     * @param position   //  0：更新 1.删除
     */
    private void upLoadPhotoInfo(final String objectName, final int type, final int position) {
        BillboardImageInfo imageInfo = new BillboardImageInfo();
        // TODO: 2018/2/14 设置对应参数
        imageInfo.setAccount(getMemberInfo().getAccount());
        imageInfo.setToken(getMemberInfo().getToken());
        // TODO: 2018/2/21  替换具体数据 图片列表
        imageInfo.setBillboardId(mBillboardInfo.getId());
        imageInfo.setKey(objectName);
        imageInfo.setType(type); // 0：更新 1.删除
        if (type == 1) {//1.删除
            if (position >= 0 && position < mImageInfos.size()) {
                Long id = mImageInfos.get(position).getId();
                imageInfo.setId(id);
            }
        }
        if (mDir.equals(mDirGuangGao))
            imageInfo.setImageType(1);//1:广告图片，2：验收图片
        else
            imageInfo.setImageType(2);//1:广告图片，2：验收图片
        httpReuqest.sendMessage(Api.Worker_uploadImage, imageInfo, true, new HttpReuqest.CallBack<BillboardImageInfo>() {
            @Override
            public void onSuccess(Message message, BillboardImageInfo result) {
                if (Api.OK == result.getCode()) {
                    // TODO: 2018/2/16 获取相应数据做对应显示
                    String text = result.getText();
                    if (type == 1) {//1.删除
//                        mImageInfos.remove(position);//相当于调用了2次remove，因为delete里面也执行了一次
                        mBitmapAdapter.delete(position);
                    } else {// 0：更新
                        int size = mImageInfos.size();
                        mBitmapAdapter.addBillboardImageInfo(result);
                        logI("0：添加了一张图片 mImageInfos.size()"+mImageInfos.size());
                    }
                    toast(text + "");
                }
            }

            @Override
            public void onError(Exception exc) {
                toast("提交失败，请重试");
                logI(exc + "");
            }

            @Override
            public void onFinish() {

            }
        });
    }


}
