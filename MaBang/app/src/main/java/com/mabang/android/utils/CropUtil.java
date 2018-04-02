package com.mabang.android.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

import walke.base.BaseActivity;

/**
 * Created by Walke.Z
 * on 2017/12/13. 52.
 * email：1126648815@qq.com
 */
public class CropUtil {

    /**
     * 裁剪从图片墙获取到的原始图片,将截取出来的图片保存
     */
    public static void cropPhotoWall24(BaseActivity baseActivity, Uri contentUri, File outputfile, int crop_request) {

//        String ofPath = (baseActivity.getExternalFilesDir("LuckyCoin") + File.separator + "cropPhotoWall24.jpg");//成功
//        File outputfile = new File(ofPath);

        try {
            Intent intent = new Intent("com.android.camera.action.CROP");

            intent.setDataAndType(contentUri, "image/*");//自己使用Content Uri替换File Uri
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 250);
            intent.putExtra("outputY", 250);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputfile));//定义输出的File Uri
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            baseActivity.startActivityForResult(intent, crop_request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 裁剪图片
     */
    public static void cropTakePhoto24(BaseActivity activity, File pictureFile, File outputfile, int crop_request) {
        // 创建File对象，用于存储裁剪后的图片，避免更改原图
//        String savePath = (activity.getExternalFilesDir("LuckyCoin") + File.separator +"cropTakePhoto24.jpg");
//        File outputfile = new File(savePath);
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            Uri contentUri = getImageContentUri(activity, pictureFile);
            intent.setDataAndType(contentUri, "image/*");//自己使用Content Uri替换File Uri
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 250);
            intent.putExtra("outputY", 250);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputfile));//定义输出的File Uri
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            activity.startActivityForResult(intent, crop_request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }



}
