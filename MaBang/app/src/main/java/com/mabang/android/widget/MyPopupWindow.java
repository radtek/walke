package com.mabang.android.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.mabang.android.activity.base.AppActivity;

/**
 * Created by walke on 2018/2/28.
 * email:1032458982@qq.com
 */

public class MyPopupWindow extends PopupWindow {
    public MyPopupWindow(Context context) {
        super(context);
    }

    public MyPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
        this.setTranslucentStatus(contentView,true);
    }

    @TargetApi(19)
    private void setTranslucentStatus(View contentView,boolean on) {
//        Window win = contentView.getContext().getWindow();
        Window win = ((AppActivity) contentView.getContext()).getWindow();
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
