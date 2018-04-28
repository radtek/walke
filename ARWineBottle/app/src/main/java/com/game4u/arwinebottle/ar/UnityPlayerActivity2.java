package com.game4u.arwinebottle.ar;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.game4u.arwinebottle.R;
import com.game4u.arwinebottle.config.FlashUtil;
import com.game4u.arwinebottle.config.Permission;

import walke.base.BaseActivity;
import walke.base.tool.LayoutParamsUtil;

public class UnityPlayerActivity2 extends BaseActivity {
    protected MyUnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code
    private Camera mCamera;

    // Setup activity layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy

        initView();

        mUnityPlayer.requestFocus();

        initPermission(Permission.PERMISSIONS, Permission.REQUEST_CODE);

    }

    private void initView() {
        mUnityPlayer = new MyUnityPlayer(this);//已测试只能这样new，不能在布局中直接引用
        RelativeLayout rootView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.activity_unity_layer,null);
        setContentView(rootView);
        rootView.addView(mUnityPlayer);
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_scan,null);
        inflate.setLayoutParams(LayoutParamsUtil.getL_P_MM());
        rootView.addView(inflate);

        ImageView ivFlashLight = (ImageView) inflate.findViewById(R.id.scan_ivFlashLight);
        ivFlashLight.setVisibility(View.INVISIBLE);

    }

    private boolean isFlashLighting;//闪光灯是否开启

    /** 新加：打开闪关灯
     * @param v
     */
    public void openFlashLight(View v){
        if (isFlashLighting){
            isFlashLighting=false;
            //关闭
//            close();
            FlashUtil.setFlashlightEnabled(false);
            Bundle settings = mUnityPlayer.getSettings();

        }else {
            //开启
            isFlashLighting=true;
            //open();
            FlashUtil.setFlashlightEnabled(true);
        }

       /* try {
            Camera  mCamera = Camera.open();
            Camera.Parameters parameter = mCamera.getParameters();
            if (isFlashLighting){
                //关闭
                parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameter);
                isFlashLighting=false;
            }else {
                //开启
                isFlashLighting=true;
                parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameter);
            }
            mCamera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
            logE(e.getMessage());
        }*/
    }

    /**
     * 打开闪光灯
     *
     * @return
     */
    private void open() {
        try {
            mCamera = Camera.open();
            mCamera.startPreview();
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 关闭闪光灯
     *
     * @return
     */
    private void close() {
        try {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parameters);
            mCamera.release();
            mCamera = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**新加：返回
     * @param v
     */
    public void back(View v){
        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });*/

        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // To support deep linking, we need to make sure that the client can get access to
        // the last sent intent. The clients access this through a JNI api that allows them
        // to get the intent set on launch. To update that after launch we have to manually
        // replace the intent with the one caught here.
        setIntent(intent);
    }

    // Quit Unity
    @Override
    protected void onDestroy() {
//        mUnityPlayer.quit();//原來有，會報錯
        if (mUnityPlayer != null) {
            try {
                mUnityPlayer.quit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //mUnityPlayer.quit();//原來有，會報錯
    }

    // Pause Unity
    @Override
    protected void onPause() {
        super.onPause();

        try {
            mUnityPlayer.pause();
//            mUnityPlayer.quit();//原來有，會報錯
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Resume Unity
    @Override
    protected void onResume() {
        super.onResume();
        mUnityPlayer.resume();
    }

    // Low Memory Unity
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mUnityPlayer.lowMemory();
    }

    // Trim Memory Unity
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_RUNNING_CRITICAL) {
            mUnityPlayer.lowMemory();
        }
    }

    // This ensures the layout will be correct.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    /*API12*/
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }
}
