package com.mabang.android.okhttp.loadimg;//package com.walke.cachedemo.mine.loadimg;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Build;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Message;
//import android.os.SystemClock;
//import android.support.annotation.RequiresApi;
//import android.util.Log;
//
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.ResponseBody;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * 吾日三省吾身：看脸，看秤，看余额。
// * Created by lanso on 2017/3/9.
// */
//public class ImgLoading3 {
//
//    private static final String TAG = "ImgLoading";
//    /**
//     * 文件保存路径
//     */
//    private String mSaveDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AHui";
//    /**
//     * 文件下载路径
//     */
//    private String loadUrl;
//
//    /**
//     * 下载的目标文件大小
//     */
//    private int fileSize;//
//    /**
//     * 当前下载文件长度
//     */
//    private int downloadLength = 0;
//    public static final int START = 1;//定义5种下载状态，START-- 开始；//----------
//    public static final int STOP = 2;//----------PAUSE--暂停；
//    public static final int LOADING = 3;//  LOADING--正在加载;
//    public static final int FINISH = 4;//  FINISH--完成；
//    private int state = START;//默认初始状态为--开始
//    private File mImgFile;
//
//    static ExecutorService executorService;//线程池
//
//    //静态代码块
//    //初始化
//    static {
//        executorService = Executors.newFixedThreadPool(5);//开启线程池
//    }
//
//    private ImgLoadListener mListener;
//
//    //    private Handler mHandler;//消息处理器
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case ImgLoading3.START:
//                    int fileSize = msg.arg1;
//                    int startSize = msg.arg2;
//                    if (mListener != null)
//                        mListener.loadStart(startSize, fileSize);
//                    break;
//                case ImgLoading3.STOP:
//                    Exception eb = (Exception) msg.obj;
//                    if (mListener != null)
//                        mListener.loadStop(eb);
//                    break;
//                case ImgLoading3.LOADING:
//                    int current = msg.arg1;
//                    int total = msg.arg2;
//                    if (mListener != null)
//                        mListener.loading(current, total);
//                    break;
//                case ImgLoading3.FINISH:
////                    File file = (File) msg.obj;
//                    Bitmap file = (Bitmap) msg.obj;
//                    if (mListener != null)
//                        mListener.loadFinish(file);
//                    break;
//            }
//        }
//    };
//
//
//    public ImgLoading3(String loadUrl, ImgLoadListener listener) {
//        this.loadUrl = loadUrl;
//        mListener = listener;
//    }
//
//    public void load() {
//        Log.i("walke", "load: -----------------------------executorService.execute");
//        executorService.execute(new Runnable() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void run() {
//
//                if (state == LOADING) {
//                    Log.i(TAG, "----  -----------           -----------   正为你努力加载中 ");
//                    return;
//                }
//                double fileOrFilesSize = ImgFileSizeUtil.getFileOrFilesSize(mImgFile, ImgFileSizeUtil.SIZETYPE_B);
//                if (fileOrFilesSize >= 0)
//                    downloadLength = (int) fileOrFilesSize;
//                // 将要下载的文件写到保存在保存路径下的文件中
//                state = START;
//                Message startMsg = mHandler.obtainMessage();
//                startMsg.what = START;
//                startMsg.arg1 = fileSize;
//                startMsg.arg2 = downloadLength;
//                mHandler.sendMessage(startMsg);
//                InputStream in=null;
//                try {
//                    OkHttpClient client = new OkHttpClient();
//                    //获取请求对象
//                    Request request = new Request.Builder().url(loadUrl).build();
//                    state = START;
//                    //获取响应体
//                    Log.i(TAG, "----  -----------           -----------   正为你努力加载中  time1 "+ SystemClock.currentThreadTimeMillis());
//                    ResponseBody body = client.newCall(request).execute().body();//execute同步
//                    Log.i(TAG, "----  -----------           -----------   正为你努力加载中  time2 "+ SystemClock.currentThreadTimeMillis());
//                    //获取流
//                    in = body.byteStream();
//                    Bitmap bitmap = BitmapFactory.decodeStream(in);// java.lang.OutOfMemoryError: Failed to allocate a 97376012 byte allocation with 4194208 free bytes and 84MB until OOM
//                    Log.i(TAG, "----  -----------           -----------   正为你努力加载中  time3 "+ SystemClock.currentThreadTimeMillis());
//                    state = FINISH;
//                    Message msg = mHandler.obtainMessage();
//                    msg.what = FINISH;
//                    msg.obj = bitmap;
//                    mHandler.sendMessage(msg);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    state = STOP;
//                    Message stopMsg = mHandler.obtainMessage();
//                    stopMsg.what = STOP;
//                    Exception eb = new Exception(e);
//                    stopMsg.obj = eb;
//                    mHandler.sendMessage(stopMsg);
//
//                } finally {
//                    try {
//                        if (in != null)
//                            in.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//
//
//    }
//
//    /**
//     * *判断是否正在下载
//     */
//    public boolean isLoading() {
//        return state == LOADING;
//    }
//
//}
