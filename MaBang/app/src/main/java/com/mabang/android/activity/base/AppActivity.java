package com.mabang.android.activity.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.mabang.android.MaBangApplication;
import com.mabang.android.activity.user.UserHomeActivity;
import com.mabang.android.activity.worker.WorkerHomeActivity;
import com.mabang.android.config.MBPermissions;
import com.mabang.android.entity.vo.AliyunInfo;
import com.mabang.android.entity.vo.MemberInfo;
import com.mabang.android.okhttp.HttpReuqest;
import com.mabang.android.widget.TitleView;

import walke.base.BaseActivity;
import walke.base.ExitApplication;
import walke.base.tool.PermissionUtil;

/**
 * Created by walke on 2018/2/2.
 * email:1032458982@qq.com
 */

public abstract class AppActivity extends BaseActivity implements TitleView.TitleViewClickListener,View.OnClickListener {

    protected TitleView mTitleView;
    protected HttpReuqest httpReuqest;
    private long mPreTime;//点两次退出，辅助时间：前一次点击时间

    public MaBangApplication getMaBangApplication() {
        return ((MaBangApplication) getApplication());
    }

    public MemberInfo getMemberInfo() {
        return getMaBangApplication().getMemberInfo();
    }

    /** 登录成功后调用
     * @param memberInfo
     */
    public void setMemberInfo(MemberInfo memberInfo) {
        getMaBangApplication().setMemberInfo(memberInfo);
    }

    public AliyunInfo getAliyunInfo(){
        return getMaBangApplication().getAliyunInfo();
    }

    public void setAliyunInfo(AliyunInfo aliyunInfo) {
        getMaBangApplication().setAliyunInfo(aliyunInfo);
    }


//    public MemberInfo getWorkerMemberInfo() {
//        return getMaBangApplication().getWorkerMemberInfo();
//    }
//
//    /** 登录成功后调用
//     * @param memberInfo
//     */
//    public void setWorkerMemberInfo(MemberInfo memberInfo) {
//        getMaBangApplication().setWorkerMemberInfo(memberInfo);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(rootLayoutId());
        httpReuqest = new HttpReuqest(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //设置仅能竖屏显示
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        initData();
        initPermission(MBPermissions.PERMISSIONS,MBPermissions.PERMISSION_CODE);
        ExitApplication.getInstance().addActivity(this);
    }

    protected abstract int rootLayoutId();

    protected void initTitleViewById(int titleViewId) {
        if (titleViewId==0)
            return;
        mTitleView = ((TitleView) findViewById(titleViewId));
        mTitleView.setTitleBarClickListener(this);
    }

    /**
     * 有TitleView的记得先调用方法：initTitleViewById(int titleViewId)
     */
    protected abstract void initView();

    protected abstract void initData();
    public void checkPermission(int permissionCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (PermissionUtil.checkPermissionSetLack(this, MBPermissions.PERMISSIONS)) {
                requestPermissions(MBPermissions.PERMISSIONS, permissionCode);
                logI("请授权后重试");

            }
        }

    }

    @Override
    public void titleViewLeftClick() {
        finish();
    }

    @Override
    public void titleViewRightClick() {
        logI("titleViewRightClick");
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onBackPressed() {
        if (this instanceof UserHomeActivity ||this instanceof WorkerHomeActivity) {
            if (System.currentTimeMillis() - mPreTime > 2000) {
                toast("再按一次,退出应用");
                mPreTime = System.currentTimeMillis();
                //getLucyCoinApplication().exit();
                return;
            }
            //getLucyCoinApplication().exit();
//            getMaBangApplication().
            ExitApplication.getInstance().exit();
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideSoft();
//        hideInputMethod();
//
//        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开
//        if (isOpen) {
//            View currentFocus = this.getCurrentFocus();
//            if (currentFocus!=null) {
//                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
//                        .hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        }
    }
    /**  隐藏软键盘
     * http://blog.csdn.net/h7870181/article/details/8332991
     */
    public void hideInputMethod(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected void hideSoft() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
    }

}
