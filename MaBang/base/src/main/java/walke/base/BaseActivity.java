package walke.base;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import walke.base.tool.LogUtil;
import walke.base.tool.PermissionUtil;
import walke.base.tool.ToastUtil;


/**
 * Created by Walke.Z on 2017/4/21.
 * 这是底层(第一层)封装
 */
public class BaseActivity extends AppCompatActivity {

    public BaseApp getBaseApp() {
        return (BaseApp) getApplication();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式状态栏
        //StatusBarCompat.compat(this, Color.RED);
        //hideVirtualButtons();
        initState();
        //设置仅能竖屏显示
//        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //  //设置仅能横屏屏显示
//        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 调用该方法后，虚拟案件就会被隐藏 从屏幕底部向上拉，可以再次显示
     * 与布局xml文件 以下两个属性连用[而6.0异常]
     * android:clipToPadding="true"
     * android:fitsSystemWindows="true"
     * 沉浸式导航栏(虚拟按键栏)会导致上下边界异常
     */
    @SuppressLint("NewApi")
    private void hideVirtualButtons() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }

    /**
     * 在需要实现沉浸式状态栏的Activity的布局中添加以下参数
     * android:fitsSystemWindows="true"
     * android:clipToPadding="true"
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 只沉浸式状态栏，与布局xml文件 以下两个属性连用[而6.0也正常]
            // android:clipToPadding="true"
            // android:fitsSystemWindows="true"

            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (isGetAllPermission(grantResults)) {

            permissionsGetSuccess(requestCode);

        } else {
            permissionsGetFail(requestCode);
            logI("doNext: ------------------未获得获得全部授权");
        }
    }
    private boolean isGetAllPermission(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                //只要存在一个权限未授权 则返回false
                return false;
            }
        }
        return true;
    }
    public void permissionsGetSuccess(int requestCode){

    }

    public void permissionsGetFail(int requestCode) {

    }

    /**
     * @param editText 获取焦点
     */
    public void editTextGetFocus(EditText editText) {
        String trim = editText.getText().toString().trim();
        if (!TextUtils.isEmpty(trim)){
            editText.setSelection(trim.length());
        }

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        /*boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开 
        if (!isOpen) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }*/
        //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public String replaceEmptyText(String emptyText, String tagText) {
        if (TextUtils.isEmpty(emptyText))
            return tagText;
        else
            return emptyText;
    }

    /**
     * @param editText 进入界面后，输入框获取焦点
     *                 需要延迟
     */
    public void enterGetFocus(final EditText editText) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                editTextGetFocus(editText);
            }
        }, 200);
    }

    protected void initPermission(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (PermissionUtil.checkPermissionSetLack(this, permissions)) {
                Log.i("walke", "initPermission: --------------------------------------");
                requestPermissions(permissions, requestCode);
            }
        }
    }

    public void showRequestPermissionDialog() {
       /* DialogUtil.createTwoButtonDialog(this, "兑奖使用帮助","需允许摄像头进行扫一扫:", "取消", "设置", "", new DialogUtil.TwoButtonClickListener() {
            @Override
            public void leftOnClick(WindowManager.LayoutParams lp, Dialog dialog) {

            }

            @Override
            public void rightOnClick(WindowManager.LayoutParams lp, Dialog dialog) {
                dialog.dismiss();
                openSystemSetting();
            }
        });*/

    }

    /**
     * 打开系统设置界面
     */
    public void openSystemSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    /**
     * 清除应用的task栈，如果程序正常运行这会导致应用退回到桌面
     */
    public final void appExit() {

        /** 进程会被kill掉 http://blog.csdn.net/chonbj/article/details/10182369 */
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            System.exit(0);
        } else {// android2.1 
            ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            am.restartPackage(getPackageName());
        }
        //进程没被kill掉 使用finlish activity不一定会被gc回收掉
        /*for (Iterator<Map.Entry<String, SoftReference<Activity>>> iterator = this.taskMap.entrySet().iterator(); iterator.hasNext(); ) {
            SoftReference<Activity> activityReference = iterator.next().getValue();
            Activity activity = activityReference.get();
            if (activity != null) {
                activity.finish();
            }
        }
        this.taskMap.clear();*/
        return;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        /** 点击其他地方隐藏输入框 */
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了  
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置  
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件  
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    public void toast(String message) {
        ToastUtil.showToast(this, message);
    }

//    public void middleToast(String message) {
//        ToastUtil.showMiddleToast(this, message);
//    }
//
//    public void toastTime(String message, int time) {
//        ToastUtil.showToastWithTime(this, message, time);
//    }

    public void logI(String message) {
        if (!TextUtils.isEmpty(message))
            LogUtil.i(this.getClass().getSimpleName(), "-------------------> " + message);
    }

    public void logD(String message) {
        if (!TextUtils.isEmpty(message))
            LogUtil.d(this.getClass().getSimpleName(), "--------> " + message);
    }

    public void logE(String message) {
        if (!TextUtils.isEmpty(message))
            LogUtil.e(this.getClass().getSimpleName(), "---------      -----------> " + message);
    }


}
