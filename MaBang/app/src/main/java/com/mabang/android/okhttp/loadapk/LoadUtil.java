package com.mabang.android.okhttp.loadapk;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import walke.base.tool.FileSizeUtil;
import walke.base.tool.LogUtil;

/**
 * 吾日三省吾身：看脸，看秤，看余额。
 * Created by lanso on 2017/3/9.
 */
public class LoadUtil {

    private Context context;
    private static final String TAG = "LoadUtil";
    /**
     * 文件保存路径
     */
    private String savePath;
    /**
     * 文件下载路径
     */
    private String loadUrl;
    private Handler mHandler;//消息处理器
    /**
     * 下载的目标文件大小
     */
    private int fileSize;//
    /**
     * 当前下载文件长度
     */
    private int downloadLength = 0;
    public static final int START = 1;//定义5种下载状态，START-- 开始；//----------
    public static final int STOP = 2;//----------PAUSE--暂停；
    public static final int LOADING = 3;//  LOADING--正在加载;
    public static final int FINISH = 4;//  FINISH--完成；
    private int state = START;//默认初始状态为--开始
    private String mFileLength;


    public LoadUtil(Context context, String loadUrl, String savePath, Handler handler) {
        this.loadUrl = loadUrl;
        this.savePath = savePath;
        this.context = context;
        mHandler = handler;
        mFileLength = "fileLength" + loadUrl;

    }

    public void load() {
        FileDownloadThread downloadThread = new FileDownloadThread();
        downloadThread.start();
    }

    /**
     * *判断是否正在下载
     */
    public boolean isLoading() {
        return state == LOADING;
    }


    public class FileDownloadThread extends Thread {

        /**
         * 初始化
         */
        private boolean init() {
            int rspCode = 0;
            try {
                URL url = new URL(loadUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                rspCode = connection.getResponseCode();
                fileSize = connection.getContentLength();

                File file = new File(savePath);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdir();
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
                //本地访问文件
                RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
                //accessFile.setLength(fileSize);
                accessFile.close();
                connection.disconnect();
                return true;
            } catch (Exception e) {
                Message msg = mHandler.obtainMessage();
                msg.what = STOP;
                boolean networkAvailable = NetWorkUtils.isNetworkAvailable((Activity) context);
                ExceptionBean eb = new ExceptionBean(e, networkAvailable, rspCode);
                msg.obj = eb;
                msg.arg1 = downloadLength;
                msg.arg2 = fileSize;
                mHandler.sendMessage(msg);
                e.printStackTrace();
                return false;
            }

        }

        @Override
        public void run() {

            if (state == LOADING) {
                LogUtil.i(TAG, "----  -----------           -----------   正为你努力加载中 ");
                return;
            }
            boolean initSuccess = init();

            if (!initSuccess) return;

            double fileOrFilesSize = FileSizeUtil.getFileOrFilesSize(savePath, 1);
            downloadLength = (int) fileOrFilesSize;

            // 将要下载的文件写到保存在保存路径下的文件中
            state = START;
            Message startMsg = mHandler.obtainMessage();
            startMsg.what = START;
            startMsg.arg1 = fileSize;
            startMsg.arg2 = downloadLength;
            mHandler.sendMessage(startMsg);

            /*if (downloadLength>=fileSize){
                LogUtil.i(TAG, "----  -----------      已下载成功，path： "+savePath);
                state = FINISH;
                Message msg = mHandler.obtainMessage();
                msg.what = FINISH;
                FileBean fb = new FileBean(savePath, loadUrl);
                msg.obj = fb;
                mHandler.sendMessage(msg);
                return;
            }*/
            HttpURLConnection connection = null;
            RandomAccessFile accessFile = null;
            InputStream is = null;
            int responseCode = 0;
            try {
                URL url = new URL(loadUrl);
                connection = ((HttpURLConnection) url.openConnection());
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
               // responseCode = connection.getResponseCode();

                // 读取下载文件总大小
                //int fs = connection.getContentLength();// 加了下一行报错: 故需要init()
                // IllegalStateException: Cannot set request property after connection is made
                connection.setRequestProperty("Range", "bytes=" + downloadLength + "-" + fileSize);
                accessFile = new RandomAccessFile(savePath, "rwd");
                accessFile.seek(downloadLength);

                responseCode = connection.getResponseCode();

                if (downloadLength>=fileSize){
                    LogUtil.i(TAG, "----  -----------      已下载成功，path： "+savePath);
                    state = FINISH;
                    Message msg = mHandler.obtainMessage();
                    msg.what = FINISH;
                    FileBean fb = new FileBean(savePath, loadUrl);
                    msg.obj = fb;
                    mHandler.sendMessage(msg);
                    return;
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
                    message.arg2 = fileSize;
                    mHandler.sendMessage(message);
                }
                state = FINISH;
                Message msg = mHandler.obtainMessage();
                msg.what = FINISH;
                FileBean fb = new FileBean(savePath, loadUrl);
                msg.obj = fb;
                mHandler.sendMessage(msg);


            } catch (IOException e) {
                //System.out.println("run:   catch IOException-------        downloadLength------------------>>> " + downloadLength);
                //Log.i(TAG, "run:   catch IOException-------        downloadLength------------------>>> " + downloadLength);
                String string = e.toString();
                e.printStackTrace();
                state = STOP;
                Message stopMsg = mHandler.obtainMessage();
                stopMsg.what = STOP;
                boolean networkAvailable = NetWorkUtils.isNetworkAvailable((Activity) context);
                ExceptionBean eb = new ExceptionBean(e, networkAvailable, responseCode);
                stopMsg.obj = eb;
                stopMsg.arg1 = downloadLength;
                stopMsg.arg2 = fileSize;
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
    }

}
