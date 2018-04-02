package com.mabang.android.activity.worker;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mabang.android.R;
import com.mabang.android.activity.base.AppActivity;
import com.mabang.android.config.Api;
import com.mabang.android.config.Constants;
import com.mabang.android.entity.vo.MemberInfo;
import com.mabang.android.entity.vo.Message;
import com.mabang.android.entity.vo.VoBase;
import com.mabang.android.okhttp.HttpReuqest;

import walke.base.tool.SharepreUtil;
import walke.base.widget.LoginInputView;

/**
 * Created by walke on 2018/2/2.
 * email:1032458982@qq.com
 */

public class WorkerLoginActivity extends AppActivity {

    private Button btLogin;
    private LoginInputView livPhone, livPassword;
    private TextView tvUserLogin;
    private EditText etPhone, etPassword;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_login_worker;
    }

    @Override
    protected void initView() {
        checkLogin();
        initTitleViewById(R.id.alw_TitleView);
        mTitleView.getIvLeft().setVisibility(View.VISIBLE);//工人登录可以有返回键，客户登录不该有
        btLogin = ((Button) findViewById(R.id.alw_btLogin));
        livPhone = ((LoginInputView) findViewById(R.id.alw_inputPhone));
        livPassword = ((LoginInputView) findViewById(R.id.alw_inputPassword));
        tvUserLogin = ((TextView) findViewById(R.id.alw_tvUserLogin));

        etPhone = livPhone.getEtInput();
        etPassword = livPassword.getEtInput();

        btLogin.setOnClickListener(this);
        tvUserLogin.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        String lastLoginName = SharepreUtil.getString(this, Constants.LAST_WORKER_LOGIN_NAME);
        String workerPassword = SharepreUtil.getString(this, Constants.LOGIN_WORKER_PASSWORD);
        etPhone.setText("");//13632495711
        etPassword.setText("");//123456
        if (!TextUtils.isEmpty(lastLoginName)){
            etPhone.setText(lastLoginName);
            etPhone.setSelection(lastLoginName.length());
        }
        if (!TextUtils.isEmpty(workerPassword)){
            etPassword.setText(workerPassword);
            etPassword.setSelection(workerPassword.length());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();//会出现当输入密码后，没有马上登录，而是home回到桌面，再进来发现密码或者账号清空了
//        String lastLoginName = SharepreUtil.getString(this, Constants.LAST_WORKER_LOGIN_NAME);
//        String workerPassword = SharepreUtil.getString(this, Constants.LOGIN_WORKER_PASSWORD);
//        etPhone.setText("");//13632495711
//        etPassword.setText("");//123456
//        if (!TextUtils.isEmpty(lastLoginName)){
//            etPhone.setText(lastLoginName);
//            etPhone.setSelection(lastLoginName.length());
//        }
//        if (!TextUtils.isEmpty(workerPassword)){
//            etPassword.setText(workerPassword);
//            etPassword.setSelection(workerPassword.length());
//        }
    }

    private void checkLogin() {
        VoBase voBase = new VoBase();
        voBase.setAccount(getMemberInfo().getAccount());
        voBase.setToken(getMemberInfo().getToken());
        httpReuqest.sendMessage(Api.Worker_checkLogin, voBase,true, new HttpReuqest.CallBack<VoBase>() {
            @Override
            public void onSuccess(Message message, VoBase result) {
                if (result != null && result.getCode() == Api.OK) {
                    startActivity(new Intent(WorkerLoginActivity.this,WorkerHomeActivity.class));
                    toast(result.getText());
                }else {
                    if (!TextUtils.isEmpty(etPhone.getText().toString().trim())&&!TextUtils.isEmpty(etPassword.getText().toString().trim()))
                        enterGetFocus(etPassword);
                    else
                        enterGetFocus(etPhone);
                }
            }

            @Override
            public void onError(Exception exc) {
                if (!TextUtils.isEmpty(etPhone.getText().toString().trim())&&!TextUtils.isEmpty(etPassword.getText().toString().trim()))
                    enterGetFocus(etPassword);
                else
                    enterGetFocus(etPhone);
                logE(exc + "");
            }

            @Override
            public void onFinish() {
                return;
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.alw_btLogin:
                workerLogin();
                break;
            case R.id.alw_tvUserLogin:
//                startActivity(new Intent(this, UserLoginActivity.class));
                finish();
                break;
        }
    }

    private void workerLogin() {
        if (!livPhone.isFitPhone()){
            editTextGetFocus(etPhone);
            return;
        }
        if (!livPassword.isFitPassword()){
            editTextGetFocus(etPassword);
            return;
        }
        MemberInfo memberInfo = new MemberInfo();
        final String username = etPhone.getText().toString();
        memberInfo.setMobile(username);
        memberInfo.setAccount(username);
        final String password = etPassword.getText().toString().trim();//123456
        memberInfo.setPassword(password);

        httpReuqest.sendMessage(Api.Worker_Login, memberInfo,true,new HttpReuqest.CallBack<MemberInfo>() {
            @Override
            public void onSuccess(Message message, MemberInfo result) {
                if (result==null){
                    logI("Worker_Login  result==null");
                    return;
                }
                if (result.getCode() == Api.OK) {
                    setMemberInfo(result);
                    //保存最后的登录角色
                    SharepreUtil.putString(WorkerLoginActivity.this, Constants.LAST_LOGIN_TYPE,Constants.LOGIN_WORKER);

                    SharepreUtil.putString(WorkerLoginActivity.this,Constants.LAST_WORKER_LOGIN_NAME,username);
                    SharepreUtil.putString(WorkerLoginActivity.this, Constants.LOGIN_WORKER_PASSWORD,password);
                    //去工人首页
                    startActivity(new Intent(WorkerLoginActivity.this, WorkerHomeActivity.class));
//                    finish();
                } else {
                    etPassword.setText("");
                }

                toast(result.getText()+"");
            }

            @Override
            public void onError(Exception exc) {
                logE("" + exc);
            }

            @Override
            public void onFinish() {
                logI("onFinish");
            }
        });
    }



}
