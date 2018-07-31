package com.aipiao.bkpkold.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.Manifest;

import com.aipiao.bkpkold.utils.PermissionUtil;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.pedaily.yc.ycdialoglib.selectDialog.CustomSelectDialog;
import com.aipiao.bkpkold.R;

import java.util.List;

/**
 * Created by caihui on 2018/3/20.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public FragmentManager fm;
    public FragmentTransaction fragmentTransaction;
    protected SVProgressHUD svProgressHUD;
    protected Gson gson;


    /**
     * 配置app需要的所有权限集
     */
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
    };

    /** 初始化检查权限
     * @param permissions
     * @param requestCode
     */
    protected void initPermission(String[] permissions, int requestCode) {

        if (Build.VERSION.SDK_INT >= 23) {
            if (PermissionUtil.checkPermissionSetLack(this, permissions)){
                requestPermissions(permissions,requestCode);
            }
        }


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            gson=new Gson();
            fm = getSupportFragmentManager();
            fragmentTransaction= fm.beginTransaction();
            fragmentTransaction.commit();
            setContentView(getLayout());
            initView();
            svProgressHUD=new SVProgressHUD(this);
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 展示对话框视图，构造方法创建对象
     */
    public CustomSelectDialog showCustomSelectDialog(CustomSelectDialog.SelectDialogListener listener, List<String> names) {
        CustomSelectDialog dialog = new CustomSelectDialog(this,
                R.style.transparentFrameWindowStyle, listener, names);
        dialog.setItemColor(R.color.colorAccent,R.color.colorPrimary);
        //判断activity是否finish
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }


    protected void toast(String value) {
        Toast.makeText(BaseActivity.this, value, Toast.LENGTH_SHORT).show();

    }

    protected  void showLoading(){
        svProgressHUD.showWithStatus("加载数据中.....");

    }
    protected void  hideLoading(){
        svProgressHUD.dismiss();
    }

    protected abstract void initData();

    protected abstract void initView();


    protected abstract int getLayout();
}
