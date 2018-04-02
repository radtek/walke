package com.mabang.android.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.util.List;

import walke.base.BaseActivity;

/**
 * Created by Walke.Z
 * on 2017/7/21. 56.
 * email：1126648815@qq.com
 */
public class CameraUtil {

    private static boolean checkCameraFacing(final int facing) {
        if (getSdkVersion() < Build.VERSION_CODES.GINGERBREAD) {
            return false;
        }
        final int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, info);
            if (facing == info.facing) {
                return true;
            }
        }
        return false;
    }

    /** 是否具有后置摄像头
     * @return
     */
    public static boolean hasBackFacingCamera() {
        final int CAMERA_FACING_BACK = 0;
        return checkCameraFacing(CAMERA_FACING_BACK);
    }

    /** 是否具有前置摄像头
     * @return
     */
    public static boolean hasFrontFacingCamera() {
        final int CAMERA_FACING_BACK = 1;
        return checkCameraFacing(CAMERA_FACING_BACK);
    }

    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 判断系统中是否存在可以启动的相机应用
     *
     * @return 存在返回true，不存在返回false
     */
    public static boolean hasCamera(AppCompatActivity activity) {
        PackageManager packageManager = activity.getPackageManager();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }


    /**
     * Open camera  permission.WRITE_SETTINGS
     */
    public static void showCameraAction(BaseActivity ba, int request_camera, File mTmpFile) {
        try {
            if (mTmpFile!=null) {
                if (!mTmpFile.exists()){
                    mTmpFile.createNewFile();
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(ba, mTmpFile));
                ba.startActivityForResult(intent, request_camera);
            }
        }catch (Exception exc){
            exc.printStackTrace();
        }

    }

    private static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri  uri = FileProvider.getUriForFile(context, context.getPackageName()+".file_provider", file);
       /* if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context, context.getPackageName()+".file_provider", file);
        } else {
            uri = Uri.fromFile(file);
        }*/
        return uri;
    }




}
