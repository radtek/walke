package walke.base.tool;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by View on 2016/10/8. ：单例吐司
 * walke 改
 */
public class ToastUtil {
    private static final Object SYNC_LOCK = new Object();
    private static Toast mToast;

//    public static Context context;
//
//    public static Context getContext() {
//        return ToastUtil.context;
//    }
//
//    public static void setContext(Context context) {
//        ToastUtil.context = context;
//    }

    /**
     * 获取toast环境，为toast加锁
     *
     * @param
     * @return
     */
    private static void initToastInstance(Context context) {
        if (ToastUtil.mToast == null) {
            synchronized (SYNC_LOCK){
                if (ToastUtil.mToast == null) {
                    ToastUtil.mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                }
            }
        }
        return;
    }
//    private static void initToastInstance() {
//        if (ToastUtil.mToast == null) {
//            synchronized (SYNC_LOCK) {
//                if (ToastUtil.mToast == null) {
//                    ToastUtil.mToast = Toast.makeText(ToastUtil.context, "", Toast.LENGTH_SHORT);
//                }
//            }
//        }
//        return;
//    }

    /**
     * 展示吐司
     *
     * @param context 环境
     * @param text    内容
     */
    public static void showToast(Context context, String text) {
//        ToastUtil.setContext(context);
//        if (getContext() != null && text != null) {
        if (TextUtils.isEmpty(text))
            return;
        if ("null".equals(text)||"NULL".equals(text)||"Null".equals(text)) {
            return;
        }
        ToastUtil.initToastInstance(context);
        ToastUtil.mToast.setDuration(Toast.LENGTH_SHORT);
        ToastUtil.mToast.setText(text);
        ToastUtil.mToast.show();

//        MyToast.getInstance(context).makeText(context, text, MyToast.LENGTH_SHORT).show(context);//会发生not attached to window manager等异常
//        XToast.getInstance(context).makeText(context, text, XToast.LENGTH_SHORT).show();
        return;
    }
    /**
     * 展示吐司
     *
     * @param context 环境
     * @param text    内容
     */
    public static void toastLong(Context context, String text) {
//        ToastUtil.setContext(context);
//        if (getContext() != null && text != null) {
        if (!TextUtils.isEmpty(text)||!"null".equals(text.toLowerCase())) {
            ToastUtil.initToastInstance(context);
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
    public static void showMiddleToast(Context context, String text) {
//        ToastUtil.setContext(context);
//        if (getContext() != null && text != null) {
        if (!TextUtils.isEmpty(text)||!"null".equals(text.toLowerCase())) {
            ToastUtil.initToastInstance(context);
            ToastUtil.mToast.setDuration(Toast.LENGTH_SHORT);
            ToastUtil.mToast.setText(text);
            ToastUtil.mToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER, 0, 0);
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
    public static void showToastXY(Context context, String text, int x, int y) {
//        ToastUtil.setContext(context);
//        if (getContext() != null && text != null) {
        if (!TextUtils.isEmpty(text)||!"null".equals(text.toLowerCase())) {
            ToastUtil.initToastInstance(context);
            ToastUtil.mToast.setDuration(Toast.LENGTH_LONG);
            ToastUtil.mToast.setText(text);
            ToastUtil.mToast.setGravity(Gravity.TOP|Gravity.LEFT,x,y);
            ToastUtil.mToast.show();
        }
        return;
    }

    /**
     * 可设置时间的默认展示吐司
     *
     * @param context 环境
     * @param text    内容
     */
    public static void showToastWithTime(Context context, final String text, int time) {
        if (!TimeUtil.isMeetIntervalTime(time)){
            return;
        }
//        ToastUtil.setContext(context);
//        if (getContext() != null && text != null) {
        if (!TextUtils.isEmpty(text)||!"null".equals(text.toLowerCase())) {
            ToastUtil.initToastInstance(context);
            final Timer timer = new Timer();
            ToastUtil.mToast.setText(text);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    ToastUtil.mToast.show();
                }
            }, 0, 2000);// 2000表示点击按钮之后，Toast延迟3000ms后显示
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    ToastUtil.mToast.cancel();
                    timer.cancel();
                }
            }, time);// 5000表示Toast显示时间为5秒
        }
        return;
    }

}
