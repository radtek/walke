package com.grandsea.ticketvendingapplication.util;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

/**
 * 吾日三省吾身：看脸，看秤，看余额。
 * Created by lanso on 2016/12/8.
 */
public class ViewUtil {

    public static int getViewHeight(View view){
        int layout_width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int layout_height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(layout_width, layout_height);
        int measuredHeight = view.getMeasuredHeight();
        return measuredHeight;
    }
    public static int getViewWidth(View view){
        int layout_width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int layout_height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(layout_width, layout_height);
        int measuredWidth = view.getMeasuredWidth();
        return measuredWidth;
    }

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
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dipTopx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
