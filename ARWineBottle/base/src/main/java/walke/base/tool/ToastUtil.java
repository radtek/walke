package walke.base.tool;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by View on 2016/10/8.
 * <p/>
 * 单例吐司
 */
public class ToastUtil {
    private static final String TAG = "ToastUtil";
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
                    ToastUtil.mToast = Toast.makeText(ToastUtil.context, "", Toast.LENGTH_SHORT);
                }
            }
        }
        return;
    }
    /**
     * 获取toast环境，为toast加锁
     *
     * @param
     * @return
     */
    private static void initInstance() {
        if (ToastUtil.mToast == null) {
            synchronized (SYNC_LOCK) {
                if (ToastUtil.mToast == null) {
                    ToastUtil.mToast = Toast.makeText(ToastUtil.context, "", Toast.LENGTH_SHORT);
                }
            }
        }
        return;
    }

    /**
     * 默认展示吐司
     *
     * @param context 环境
     * @param text    内容
     */
    public static void showToast(Context context, String text) {
        ToastUtil.setContext(context);
        if (getContext() != null && text != null) {
            ToastUtil.initToastInstance();
            ToastUtil.mToast.setDuration(Toast.LENGTH_SHORT);
            ToastUtil.mToast.setText(text);
            ToastUtil.mToast.show();
        }
        return;
    }

    /**
     * 显示屏中部展示吐司
     *
     * @param context 环境
     * @param text    内容
     */
    public static void showMidlleToast(Context context, String text) {
        ToastUtil.setContext(context);
        if (getContext() != null && text != null) {
            ToastUtil.initToastInstance();
            ToastUtil.mToast.setDuration(Toast.LENGTH_SHORT);
            ToastUtil.mToast.setText(text);
            ToastUtil.mToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER, 0, 0);
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
        ToastUtil.setContext(context);
        if (getContext() != null && text != null) {
            ToastUtil.initInstance();

            final Timer timer = new Timer();
            ToastUtil.mToast.setText(text);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    ToastUtil.mToast.show();
                }
            }, 0, 2000);// 3000表示点击按钮之后，Toast延迟3000ms后显示
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

    public static void CustomTimeToast(Context context,String text,int time) {
        boolean meetIntervalTime = TimeUtil.isMeetIntervalTime(time);
        Log.i(TAG, "CustomTimeToast: boolean = "+meetIntervalTime);
        if (!meetIntervalTime){
            return;
        }
        final Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);// 3000表示点击按钮之后，Toast延迟3000ms后显示
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, time);// 5000表示Toast显示时间为5秒

    }

}
