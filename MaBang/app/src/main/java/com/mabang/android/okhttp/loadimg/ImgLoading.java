package com.mabang.android.okhttp.loadimg;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 吾日三省吾身：看脸，看秤，看余额。
 * Created by lanso on 2017/3/9.
 */
public class ImgLoading {

    private static final String TAG = ImgLoading.class.getSimpleName();

    /**
     * 文件保存路径
     */
    private String mSaveDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AHui/img";

    /**
     * 临时文件保存路径
     */
    private String mTempDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AHui/img";

    /**
     * 文件下载路径
     */
    private String loadUrl;

    /**
     * 下载的目标文件大小
     */
    private int mFileSize=-1;//

    /**
     * 缓存key,可用于自定
     */
    private final String mCacheKey;
    /**
     * 下载中的临时文件
     */
    private File mImgTempFile;
    /**
     * 下载完成后的保存文件
     */
    private File mDestFile;

    /**
     * 当前下载文件长度
     */
    private int downloadLength = 0;
    public static final int START = 1;//定义5种下载状态，START-- 开始；//----------
    public static final int STOP = 2;//----------PAUSE--暂停；
    public static final int LOADING = 3;//  LOADING--正在加载;
    public static final int FINISH = 4;//  FINISH--完成；
    private int state = START;//默认初始状态为--开始

    static ExecutorService executorService;//线程池

    //静态代码块
    //初始化
    static {
        executorService = Executors.newFixedThreadPool(5);//初始化线程池
    }

    private ImgLoadListener mListener;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ImgLoading.START:
                    int fileSize = msg.arg1;
                    int startSize = msg.arg2;
                    if (mListener != null)
                        mListener.loadStart(startSize, fileSize);
                    break;
                case ImgLoading.STOP:
                    Exception eb = (Exception) msg.obj;
                    if (mListener != null)
                        mListener.loadStop(eb);
                    break;
                case ImgLoading.LOADING:
                    int current = msg.arg1;
                    int total = msg.arg2;
                    if (mListener != null)
                        mListener.loading(current, total);
                    break;
                case ImgLoading.FINISH:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    if (mListener != null)
                        mListener.loadFinish(bitmap);
                    break;
            }
        }
    };



    public ImgLoading(String loadUrl, String cacheKey, ImgLoadListener listener) {
        this.loadUrl = loadUrl;
        mCacheKey = cacheKey;
        mListener = listener;
        mSaveDir = ImgLoader.getInstance().getSaveDir();
        mTempDir = ImgLoader.getInstance().getTempDir();
    }

    public void load() {
        Log.i("walke", "load: -----------------------------executorService.execute");
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                if (isLoading()) {
                    Log.i(TAG, "----  -----------           -----------   正为你努力加载中 ");
                    return;
                }
                String md5EncodeImgName = ImgMD5Util.MD5Encode(loadUrl);
                mDestFile = new File(mSaveDir, md5EncodeImgName+".png");
                //1.在开始前判断该文件夹下的图片是否存在并且size>0、bitmapFactory转化成功
                boolean isExists = ImgFileUtils.checkInitImageFile(mDestFile);
                if (isExists){
                    Log.i(TAG, "----  -----------           -----------   图片文件已存在 ");
                    return;
                }
                //2.初始化临时文件
                if (mImgTempFile==null) {
                    mImgTempFile = new File(mTempDir, md5EncodeImgName);
                    ImgFileUtils.checkAndInitFile(mImgTempFile);
                }

                double fileOrFilesSize = ImgFileSizeUtil.getFileOrFilesSize(mImgTempFile, ImgFileSizeUtil.SIZETYPE_B);
                if (fileOrFilesSize >= 0)
                    downloadLength = (int) fileOrFilesSize;

                HttpURLConnection connection = null;
                RandomAccessFile accessFile = null;
                InputStream is = null;
                try {
                    URL url = new URL(loadUrl);
                    connection = ((HttpURLConnection) url.openConnection());
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    // 读取下载文件总大小
                    //int fs = connection.getContentLength();// 加了会导致下一行setRequestProperty报错:
                    //setRequestProperty 之前不能调用connection.getResponseCode(); connection.getContentLength();
                    // IllegalStateException: Cannot set request property after connection is made
                    connection.setRequestProperty("Range", "bytes=" + downloadLength + "-" );//不用fileSize
                    accessFile = new RandomAccessFile(mImgTempFile, "rwd");
                    accessFile.seek(downloadLength);
                    mFileSize = connection.getContentLength();
                    if (mFileSize>0&&downloadLength >= mFileSize) {
                        Log.i(TAG, "----  -----------      已下载成功，path： " + mImgTempFile.getAbsolutePath());
                        Bitmap bitmap = ImgBitmapUtil.getBitmap(mImgTempFile.getAbsolutePath());
                        if (bitmap!=null) {
                            state = FINISH;
                            Message msg = mHandler.obtainMessage();
                            msg.what = FINISH;
                            msg.obj = mImgTempFile;
                            mHandler.sendMessage(msg);
                            return;
                        }else {
                            throw new Exception("文件转化图片失败");
                        }
                    }else {
                        Log.i(TAG, "----  -----------      开始下载 loadUrl： " + loadUrl);
                        state = START;
                        Message startMsg = mHandler.obtainMessage();
                        startMsg.what = START;
                        startMsg.arg1 = mFileSize;
                        startMsg.arg2 = downloadLength;
                        mHandler.sendMessage(startMsg);
                    }
                    is = connection.getInputStream();
                    byte[] buffer = new byte[1024 * 6];
                    int len = -1;
                    while ((len = is.read(buffer)) != -1) {
                        state = LOADING;
                        accessFile.write(buffer, 0, len);
                        downloadLength += len;
                        // 用消息将下载信息传给LoadUtils
                        Message message = Message.obtain();
                        message.what = LOADING;
                        message.obj = true;//是否支持断续下载（用RandomAccessFile流）
                        message.arg1 = downloadLength;
                        message.arg2 = mFileSize;
                        mHandler.sendMessage(message);
                    }
                    Bitmap bitmap = ImgBitmapUtil.getBitmap(mImgTempFile.getAbsolutePath());
                    if (bitmap!=null) {
                        //1.加一个目标文件夹，存储下载完成的图片，用fileCopy,
                        //2.在开始前判断该文件夹下的图片是否存在并且size>0,
                        //3.用Bitmap工厂转化。try catch一下，异常则删除并重新下载
                        ImgFileUtils.copyFile(mImgTempFile, mDestFile);
                        BitmapCache.getInstance().putBitmap(mCacheKey,bitmap);
                        state = FINISH;
                        Message msg = mHandler.obtainMessage();
                        msg.what = FINISH;
                        msg.obj = bitmap;
                        mHandler.sendMessage(msg);
                    }else {
                        throw new Exception("文件转化图片失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    state = STOP;
                    Message stopMsg = mHandler.obtainMessage();
                    stopMsg.what = STOP;
                    Exception eb = new Exception(e);
                    stopMsg.obj = eb;
                    mHandler.sendMessage(stopMsg);
                } finally {
                    try {
                        if (is != null)
                            is.close();
                        if (accessFile != null)
                            accessFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * *判断是否正在下载
     */
    public boolean isLoading() {
        return state == LOADING;
    }

}
