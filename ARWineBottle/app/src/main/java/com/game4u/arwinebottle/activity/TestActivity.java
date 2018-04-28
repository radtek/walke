package com.game4u.arwinebottle.activity;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.game4u.arwinebottle.R;

public class TestActivity extends AppCompatActivity {
    private Camera mCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout_scan);
        RelativeLayout rootView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.activity_test,null);
        setContentView(rootView);

        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_scan1,null);
        rootView.addView(inflate);

    }

    public void jump(View view) {

    }



    private boolean isFlashLighting;//闪光灯是否开启

    /** 新加：打开闪关灯
     * @param v
     */
    public void openFlashLight(View v){
        if (isFlashLighting){
            isFlashLighting=false;
            //FlashUtil.setFlashlightEnabled(false);
            //关闭
            close();
        }else {
            //开启
            isFlashLighting=true;
            //FlashUtil.setFlashlightEnabled(true);
            open();
        }

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

}
