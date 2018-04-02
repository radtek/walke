package com.mabang.android.okhttp.loadapk;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.text.DecimalFormat;


/**
 * Created by caihui on 2016/9/23.
 */
public class Loader {


    private LoadUtil mLoadUtil;
    private LoadingListenner mListener;
    private static Loader instance;
    private Context context;
    private String loadUrl;

    private Loader(Context context) {
        this.context=context;

    }
    public static synchronized Loader getInstance(Context context){
        if (instance==null)
            instance = new Loader(context);
        return instance;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LoadUtil.START:
                    int fileSize = msg.arg1;
                    int startSize = msg.arg2;
                    if (mListener != null)
                        mListener.loadStart(startSize,fileSize);
                    break;

                case LoadUtil.STOP:
                    ExceptionBean eb = (ExceptionBean) msg.obj;
                    int pause_current = msg.arg1;
                    int pause_total = msg.arg2;
                    if (mListener != null)
                        mListener.loadStop(eb,pause_current, pause_total);
                    break;
                case LoadUtil.LOADING:
                    int current = msg.arg1;
                    int total = msg.arg2;
                    if (mListener != null) {
                        mListener.loading(current, total);
                    }

                    break;
                case LoadUtil.FINISH:
                    FileBean fb = (FileBean) msg.obj;
                    if (mListener != null)
                        mListener.loadFinish(fb.getLoadUrl(), fb.getLocalPath());
                    break;
            }

        }
    };

    /**
     * @param fileUrl 要加载的目标文件地址
     * @param savePath 文件下载后存放路径
     * @param listener 构造方法传入接口实例，监听下载情况
     */
    public void loadFile(String fileUrl, String savePath, LoadingListenner listener){
        loadUrl=fileUrl;
        if (mLoadUtil==null)
            mLoadUtil = new LoadUtil(context,fileUrl, savePath, handler);
        if (mListener==null)
            mListener = listener;
        mLoadUtil.load();
    }

    /**
     * 格式化文件大小
     *
     * @param size 原始大小(单位b)
     * @return
     */
    public static String formatFileSize(int size) {
        double size_k = ((double) size) / 1024;
        double size_m = ((double) size_k) / 1024;
        double size_g = size_m / 1024;
        if (size_k < 500) {
            return d2(size_k) + "kb";
        } else if (size_m < 100) {
            return d2(size_m) + "mb";
        } else {
            return d2(size_g) + "gb";
        }
    }

    /**
     * DecimalFormat转换最简便
     */
    public static String d2(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        String format = df.format(d);
        return format;
    }

    /**
     * 格式化进度
     *
     * @param current
     * @param total
     * @return
     */
    public static int formatProgress(int current, int total) {
        int v = (int) (((double) current) / ((double) total) * 100);
        return v;
    }


}
