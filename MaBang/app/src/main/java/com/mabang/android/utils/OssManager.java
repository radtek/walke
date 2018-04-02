package com.mabang.android.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.mabang.android.activity.base.AppActivity;
import com.mabang.android.entity.vo.AliyunInfo;

import walke.base.tool.LoadingDialog;
import walke.base.tool.ToastUtil;


/**
 * Created by Administrator on 2017/3/1.
 */

public class OssManager {
    private static final String TAG="OssManager";
    //测试oss配置
    public static final String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    public static final String accessKeyId = "LTAI5mx2BHGJq66f";
    public static final String accessKeySecret = "AxwnDmhG0OTR0qIfUqzWSnUsTA2e4O";
    private String mBucketName ="mabang-web";


    private OSS oss;
    public static OssManager getInstance() {
        return OssInstance.instance;
    }
    private static class OssInstance{
        private static final OssManager instance= new OssManager();
    }
    private OssManager(){}

    /**
     * 初始化
     * **/
    public OssManager init(Context context){
        if(oss==null){
            OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
            oss= new OSSClient(context, endpoint, credentialProvider);
        }
        return  OssInstance.instance;
    }

    /**
     * 初始化
     * **/
    public OssManager init(AppActivity appActivity, AliyunInfo aliyunInfo){
        if (aliyunInfo ==null){
            ToastUtil.showToast(appActivity,"未获取到上传图片配置，请返回首页后重试!");
            return null;
        }
        if (TextUtils.isEmpty(aliyunInfo.getAccessKeyId())
                ||TextUtils.isEmpty(aliyunInfo.getAccessKeySecret())
                ||TextUtils.isEmpty(aliyunInfo.getBucket())
                ||TextUtils.isEmpty(aliyunInfo.getRegion())){
            ToastUtil.showToast(appActivity,"未获取到上传图片齐全配置，请返回首页后重试!");
            return null;
        }
        mBucketName=aliyunInfo.getBucket();
        if(oss==null){
            OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(aliyunInfo.getAccessKeyId(), aliyunInfo.getAccessKeySecret());
            oss= new OSSClient(appActivity, "http://"+aliyunInfo.getRegion()+".aliyuncs.com", credentialProvider);
        }
        return  OssInstance.instance;
    }


    /**OSS上传文件普通上传，比如image**/
    OSSAsyncTask task;

    public void uploading(final AppActivity appActivity, String objectName, String filePath, final OnPictureUploadListener listener){
        Log.i(TAG,"objectName"+objectName+":"+filePath);
        if (TextUtils.isEmpty(filePath)) {
            ToastUtil.showToast(appActivity,"请选择图片!");
            return;
        }
        final Dialog loadDialog = LoadingDialog.createDialog(appActivity, "请稍等");
        loadDialog.show();
        // 构造上传请求


        PutObjectRequest put = new PutObjectRequest(mBucketName, objectName, filePath);
        ObjectMetadata metadata = new ObjectMetadata();
        put.setMetadata(metadata);
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.i(TAG, "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        task= oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(final PutObjectRequest request, final PutObjectResult result) {
                // 只有设置了servercallback，这个值才有数据
                String serverCallbackReturnJson = result.getServerCallbackReturnBody();
                Log.i(TAG, "onSuccess:---------oss上传成功 servercallbackJson="+serverCallbackReturnJson);
                appActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (loadDialog!=null&&loadDialog.isShowing())
                            loadDialog.dismiss();
                        if (listener!=null)
                            listener.success(request,result);
                    }
                });
            }

            @Override
            public void onFailure(final PutObjectRequest request, final ClientException clientExcepion, final ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
                appActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (loadDialog!=null&&loadDialog.isShowing())
                            loadDialog.dismiss();
                        if (listener!=null)
                            listener.error(request,clientExcepion,serviceException);
                    }
                });
            }
        });
    }

    public void uploadTest(final Activity activity, String objectName, String filePath, final OnPictureUploadListener listener){
        Log.i(TAG,"objectName"+objectName+":"+filePath);
        if (TextUtils.isEmpty(filePath)) {
            ToastUtil.showToast(activity,"请选择图片!");
            return;
        }
        final Dialog loadDialog = LoadingDialog.createDialog(activity, "请稍等");
        loadDialog.show();
        // 构造上传请求
//        PutObjectRequest put = new PutObjectRequest(mBucketName+"error", objectName, filePath);
        PutObjectRequest put = new PutObjectRequest(mBucketName, objectName, filePath);
        ObjectMetadata metadata = new ObjectMetadata();
        put.setMetadata(metadata);
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.i(TAG, "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        task= oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(final PutObjectRequest request, final PutObjectResult result) {
                String tName = Thread.currentThread().getName();
                Log.i(TAG, "onSuccess:---------oss上传成功 tName="+tName);
                // 只有设置了servercallback，这个值才有数据
                String serverCallbackReturnJson = result.getServerCallbackReturnBody();
                Log.i(TAG, "onSuccess:---------oss上传成功 servercallbackJson="+serverCallbackReturnJson);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (loadDialog!=null&&loadDialog.isShowing())
                            loadDialog.dismiss();
                        if (listener!=null)
                            listener.success(request,result);
                    }
                });
            }

            @Override
            public void onFailure(final PutObjectRequest request, final ClientException clientExcepion, final ServiceException serviceException) {
                // 请求异常
                String tName = Thread.currentThread().getName();
                Log.i(TAG, "onFailure:---------oss上传失败 tName="+tName);
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (loadDialog!=null&&loadDialog.isShowing())
                            loadDialog.dismiss();
                        if (listener!=null)
                            listener.error(request,clientExcepion,serviceException);
                    }
                });
            }
        });
        // task.cancel(); // 可以取消任务

        // task.waitUntilFinished(); // 可以等待直到任务完成
    }

    public interface OnPictureUploadListener{
        void success(PutObjectRequest request, PutObjectResult result);
        void error(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException);
    }

}
