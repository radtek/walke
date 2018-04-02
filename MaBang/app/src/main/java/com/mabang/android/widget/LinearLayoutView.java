package com.mabang.android.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by walke.Z on 2018/3/27.
 */

public class LinearLayoutView extends LinearLayout {
    public static final int KEYBORAD_HIDE = 0;
    public static final int KEYBORAD_SHOW = 1;
    private static final int SOFTKEYPAD_MIN_HEIGHT = 50;
    private Handler uiHandler = new Handler();
    private Runnable mRunable;

    public LinearLayoutView(Context context) {
        super(context);
    }
    public LinearLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onSizeChanged(int w,final int h, int oldw,final int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        mRunable = new Runnable() {
            @Override
            public void run() {
                if (oldh - h > SOFTKEYPAD_MIN_HEIGHT){
                    keyBordStateListener.stateChange(KEYBORAD_SHOW);
                }else {
                    if(keyBordStateListener != null){
                        keyBordStateListener.stateChange(KEYBORAD_HIDE);}
                }
            }
        };
        uiHandler.post(mRunable);
    }
    private KeyBordStateListener  keyBordStateListener;
    public void setKeyBordStateListener(KeyBordStateListener keyBordStateListener) {
        this.keyBordStateListener = keyBordStateListener;
    }
    public interface KeyBordStateListener{
        public void stateChange(int state);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (uiHandler!=null&&mRunable!=null)
            uiHandler.removeCallbacks(mRunable);
    }
}
