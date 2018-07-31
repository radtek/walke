package com.grandsea.ticketvendingapplication.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by View on 2016/10/8.
 * <p/>
 * 单例吐司
 */
public class ToastUtil {
    private static final Object SYNC_LOCK = new Object();
    private static Toast mToast;
    /**
     * 上下文
     */
    public static Context context;

    public static Context getContext() {
        return ToastUtil.context;
    }

    public static void setContext(Context context) {
        ToastUtil.context = context;
    }

    /**
     * 获取toast环境，为toast加锁
     *
     * @param
     * @return
     */
    private static void initToastInstance() {
        if (ToastUtil.mToast == null) {
            synchronized (SYNC_LOCK) {
                if (ToastUtil.mToast == null) {
                    ToastUtil.mToast = Toast.makeText(ToastUtil.context, "", Toast.LENGTH_LONG);
                }
            }
        }
        return;
    }

    /**
     * 展示吐司
     *
     * @param context 环境
     * @param text    内容
     */
    public static void showToast(Context context, String text) {
        ToastUtil.setContext(context);
        if (getContext() != null && text != null) {
            ToastUtil.initToastInstance();
            ToastUtil.mToast.setDuration(Toast.LENGTH_LONG);
            ToastUtil.mToast.setText(text);
            ToastUtil.mToast.show();
        }
        return;
    }

    /**
     * 展示吐司
     *
     * @param context 环境
     * @param text    内容
     */
    public static void showMidlleToast(Context context, String text) {
        ToastUtil.setContext(context);
        if (getContext() != null && text != null) {
            ToastUtil.initToastInstance();
            ToastUtil.mToast.setDuration(Toast.LENGTH_LONG);
            ToastUtil.mToast.setText(text);
            ToastUtil.mToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER, 0, 0);
            ToastUtil.mToast.show();

        }
        return;
    }



}
