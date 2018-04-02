package com.mabang.android.okhttp.loadimg;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by suxq on 2017/4/7.
 */

public class ImgFileUtils {

    private static final String TAG = "ImgFileUtils";

    /**
     * 判断外部存储是否可用
     *
     * @return true: 可用
     */
    public static boolean isSDcardAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * 复制文件，默认删除原文件
     *
     * @param sourceFile
     * @param destFile   destFile的所在文件夹必须先存在
     */
    public static void copyFile(File sourceFile, File destFile) {
        copyFile(sourceFile, destFile, true);
    }

    /**
     * 复制文件， 默认
     *
     * @param sourceFile
     * @param destFile       destFile的所在文件夹必须先存在
     * @param isDeleteSource
     */
    public static boolean copyFile(File sourceFile, File destFile, boolean isDeleteSource) {
        try {
            if (!sourceFile.exists()) {
                Log.i("walke", "copyFile: --------------sourceFile不存在");
                return false;
            }
            if (!checkAndInitFile(destFile)) {//当目标文件不存在
                Log.i("walke", "copyFile: --------------destFile 初始化失败");
                return false;
            }
            int byteRead = 0;
            if (sourceFile.exists()) {
                FileInputStream is = new FileInputStream(sourceFile);
                FileOutputStream os = new FileOutputStream(destFile);
                byte[] buffer = new byte[1024];
                while ((byteRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, byteRead);
                }
                if (isDeleteSource) {
                    sourceFile.delete();
                }
                is.close();
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /** 检测file，当存在是直接return true，
     * @param destFile
     * @return true：文件存在或者初始化成功，false：文件不存在或初始化失败
     */
    public static boolean checkAndInitFile(File destFile) {
        try {
            if (destFile.exists())
                return true;
            File parentFile = destFile.getParentFile();
            if (!parentFile.exists()) {
                boolean mkdirs = parentFile.mkdirs();//当有比较多层文件夹时使用，只有一层父目录也推荐使用
                Log.i("walke", "checkAndInitFile: mkdirs = " + mkdirs);
                if (mkdirs) {
                    destFile.createNewFile();
                    return true;
                }
            } else {
                destFile.createNewFile();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 检测file，当存在并可以转成图片 return true，否则初始化文件，并return false
     * //3.用Bitmap工厂转化。try catch一下，异常则删除并重新下载
     *
     * @param destFile
     * @return
     */
    public static boolean checkInitImageFile(File destFile) {
        try {
            Bitmap bitmap = ImgBitmapUtil.getBitmap(destFile.getAbsolutePath());
            if (bitmap != null) {
                return true;
            } else {
                if (!destFile.exists()) {
                    File parentFile = destFile.getParentFile();
                    if (!parentFile.exists()) {
                        boolean mkdirs = parentFile.mkdirs();//当有比较多层文件夹时使用，只有一层父目录也推荐使用
                        Log.i("walke", "checkAndInitFile: mkdirs = " + mkdirs);
                        if (mkdirs)
                            destFile.createNewFile();
                    } else {
                        destFile.createNewFile();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
