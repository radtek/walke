package walke.base.tool;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Created by walke.Z on 2018/1/18.
 */

public class WindowUtil {
    /**
     *
     * @param context
     * @return width
     */
    public static int getWindowWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }
    /**
     *
     * @param context
     * @return ：height
     */
    public static int getWindowHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    /** 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取屏幕原始尺寸高度，包括虚拟功能键高度
     * @param context
     * @return
     */
    public static int[] getWindowPixels(Context context){
        int[] wh=new int[2];
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics",DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            wh[0]=displayMetrics.widthPixels;
            wh[1]=displayMetrics.heightPixels;

        }catch(Exception e){
            e.printStackTrace();
        }
        return wh;
    }

    /**
     *
     * @param context
     * @return 0：width   1：height
     */
    public static  int[] getWindowSize(Context context){
        int [] size=new int[2];
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        size[0]=width;
        size[1]=height;
        return  size;
    }

}
