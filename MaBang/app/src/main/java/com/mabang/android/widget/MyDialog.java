package com.mabang.android.widget;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by walke on 2018/2/26.
 * email:1032458982@qq.com
 */

public class MyDialog extends Dialog{

    // 当前设备屏幕分辨率密度比
    private float density;
    // 当前设备屏幕宽度
    private int widthPixels;
    // 当前设备屏幕高度
    private int heightPixels;

    public MyDialog(@NonNull Context context) {
        super(context);
    }

    public MyDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.setTranslucentStatus(true);
        this.density = context.getResources().getDisplayMetrics().density;
        this.widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        this.heightPixels = context.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public void show() {
        super.show();
//        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
//        lp.width = this.widthPixels; //设置宽度
//        lp.height = this.heightPixels; //设置宽度
//        this.getWindow().setAttributes(lp);
        return;
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = this.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
        return;
    }
}
