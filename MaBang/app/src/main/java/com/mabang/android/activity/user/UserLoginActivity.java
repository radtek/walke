package com.mabang.android.activity.user;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mabang.android.R;
import com.mabang.android.activity.base.AppActivity;
import com.mabang.android.activity.worker.WorkerHomeActivity;
import com.mabang.android.activity.worker.WorkerLoginActivity;
import com.mabang.android.config.Api;
import com.mabang.android.config.Constants;
import com.mabang.android.entity.ApiType;
import com.mabang.android.entity.vo.MemberInfo;
import com.mabang.android.entity.vo.Message;
import com.mabang.android.entity.vo.MobileValidateCodeInfo;
import com.mabang.android.okhttp.HttpReuqest;

import walke.base.tool.SharepreUtil;
import walke.base.widget.LoginCountdownView;
import walke.base.widget.LoginInputView;

/**
 * Created by walke on 2018/2/2.
 * email:1032458982@qq.com
 */

public class UserLoginActivity extends AppActivity {

    private Button btLogin;
    private LoginInputView livPhone, livAuthCode;
    private TextView tvWorkerLogin;
    private EditText etPhone, etAuthCode;
    private ImageView ivWelcome;
    private LoginCountdownView mCountdownView;
    private int mMaxTime = 30;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_login_user;
    }

    @Override
    protected void initView() {

        String lastLogintype = SharepreUtil.getString(UserLoginActivity.this, Constants.LAST_LOGIN_TYPE);
        if (Constants.LOGIN_WORKER.equals(lastLogintype)) {//本地登录最后角色为客户
            startActivity(new Intent(UserLoginActivity.this, WorkerLoginActivity.class));
        }

        initTitleViewById(R.id.alu_TitleView);
        mTitleView.getIvLeft().setVisibility(View.INVISIBLE);
        livPhone = ((LoginInputView) findViewById(R.id.alu_inputPhone));
        livAuthCode = ((LoginInputView) findViewById(R.id.alu_inputAuthCode));

        etPhone = ((LoginInputView) findViewById(R.id.alu_inputPhone)).getEtInput();
        etAuthCode = ((LoginInputView) findViewById(R.id.alu_inputAuthCode)).getEtInput();
        mCountdownView = ((LoginInputView) findViewById(R.id.alu_inputAuthCode)).getCountdownView();

        btLogin = ((Button) findViewById(R.id.alu_btLogin));
        tvWorkerLogin = ((TextView) findViewById(R.id.alu_tvWorkerLogin));
        btLogin.setOnClickListener(this);
        tvWorkerLogin.setOnClickListener(this);

        mCountdownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPhoneIdentifyCode();

            }
        });

//        etPhone.setText("18218649345");
    }

    @Override
    protected void initData() {
        int countdownTime = getBaseApp().getCountdownTime();
        if (countdownTime>0&&countdownTime<mMaxTime){
            mCountdownView.startTimeDown(countdownTime);
        }
        String lastLoginName = SharepreUtil.getString(this, Constants.LAST_USER_LOGIN_NAME);
        etPhone.setText("");
        etAuthCode.setText("");
        if (!TextUtils.isEmpty(lastLoginName)){
            etPhone.setText(lastLoginName);
            etPhone.setSelection(lastLoginName.length());
        }
        checkLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();// 会出现当输入密码后，没有马上登录，而是home回到桌面，再进来发现密码或者账号清空了
//        String lastLoginName = SharepreUtil.getString(this, Constants.LAST_USER_LOGIN_NAME);
//        etPhone.setText("");
//        etAuthCode.setText("");
//        if (!TextUtils.isEmpty(lastLoginName)){
//            etPhone.setText(lastLoginName);
//            etPhone.setSelection(lastLoginName.length());
//        }

        //现象：当输入了手机+验证码。还未登陆点击Home键后，再打开APP，发现验证码填充到了手机栏
        String lastLoginName = SharepreUtil.getString(this, Constants.LAST_USER_LOGIN_NAME);
        etPhone.setText("");
        if (!TextUtils.isEmpty(lastLoginName)){
            etPhone.setText(lastLoginName);
            etPhone.setSelection(lastLoginName.length());
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        //现象：当输入了手机+密码。还未登陆点击Home键后，再打开APP，发现密码填充到了手机栏
        if (livPhone!=null){
            String username = livPhone.getEtInput().getText().toString().trim();
            SharepreUtil.putString(UserLoginActivity.this,Constants.LAST_USER_LOGIN_NAME,username);
        }
    }


    private void checkLogin() {
//        VoBase voBase = new VoBase();//
        MemberInfo memberInfo = new MemberInfo();//
        memberInfo.setAccount(getMemberInfo().getAccount());
        memberInfo.setToken(getMemberInfo().getToken());
        httpReuqest.sendMessage(Api.User_checkLogin, memberInfo, true, new HttpReuqest.CallBack<MemberInfo>() {
            @Override
            public void onSuccess(Message message, MemberInfo result) {
                if (result != null && result.getCode() == Api.OK) {
//
                    ApiType type = result.getType();
                    if (type!=null) {
                        if (ApiType.MEMBER.getValue() == type.getValue()) {
                            startActivity(new Intent(UserLoginActivity.this, UserHomeActivity.class));
                            finish();
                        } else if (ApiType.WORKER.getValue() == type.getValue()) {
                            startActivity(new Intent(UserLoginActivity.this, WorkerHomeActivity.class));
                            finish();
                        }
                    }else {
                        //本地缓存判断：在角色登录成功的时候保存一下Constants.LAST_LOGIN_TYPE
                        // 【两个值对应：Constants.LOGIN_USER 或者 Constants.LOGIN_WORKER 】
                        String lastLogintype = SharepreUtil.getString(UserLoginActivity.this, Constants.LAST_LOGIN_TYPE);
                        if (Constants.LOGIN_USER.equals(lastLogintype)) {//本地登录最后角色为客户
                            startActivity(new Intent(UserLoginActivity.this, UserHomeActivity.class));
                        } else {
                            startActivity(new Intent(UserLoginActivity.this, WorkerHomeActivity.class));
                        }

                    }
                    toast(result.getText());
                }else {
                    enterGetFocus(etPhone);
                }

            }

            @Override
            public void onError(Exception exc) {
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
        switch (v.getId()) {
            case R.id.alu_btLogin:
                login();
//                startActivity(new Intent(this, UserHomeActivity.class));
//                String certificateSHA1Fingerprint = AppUtils.getCertificateSHA1Fingerprint(this);
//                logI(certificateSHA1Fingerprint);//77:55:31:06:A6:71:C3:63:05:3D:DB:B1:41:B9:FC:BD:C3:97:8C:FE
//                etPhone.setText(certificateSHA1Fingerprint);
                break;
            case R.id.alu_tvWorkerLogin:
                startActivity(new Intent(this, WorkerLoginActivity.class));
                break;
        }
    }

    /**
     * 请求手机校验码
     */
    private void requestPhoneIdentifyCode() {
        if (!livPhone.isFitPhone()) {
            editTextGetFocus(etPhone);
            return;
        }

        MobileValidateCodeInfo mobileValidateCode = new MobileValidateCodeInfo();
        String mobile = etPhone.getText().toString();
        SharepreUtil.putString(this,Constants.LAST_USER_LOGIN_NAME,mobile);
        mobileValidateCode.setMobile(mobile);
        httpReuqest.sendMessage(Api.User_validateCode, mobileValidateCode, true, new HttpReuqest.CallBack<MobileValidateCodeInfo>() {

            @Override
            public void onSuccess(Message message, MobileValidateCodeInfo mobileValidatecode) {
                if (mobileValidatecode != null && mobileValidatecode.getCode() == Api.OK) {

                    mCountdownView.startTimeDown(mMaxTime);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            editTextGetFocus(etAuthCode);
                        }
                    }, 200);
                }
                toast(mobileValidatecode.getText());
            }

            @Override
            public void onError(Exception exc) {
                logE(exc + "");
            }

            @Override
            public void onFinish() {
                return;
            }
        });
    }

    private void login() {
        if (!livPhone.isFitPhone()) {
            editTextGetFocus(etPhone);
            return;
        }
        if (!livAuthCode.isFitPhoneAuthCode()) {
            editTextGetFocus(etAuthCode);
            return;
        }
        MemberInfo memberInfo = new MemberInfo();
        final String username = etPhone.getText().toString();
//        memberInfo.setMobile(username);
        memberInfo.setAccount(username);
        memberInfo.setMobile(username);
        memberInfo.setValidate(etAuthCode.getText().toString());
        httpReuqest.sendMessage(Api.User_Login, memberInfo, true, new HttpReuqest.CallBack<MemberInfo>() {
            @Override
            public void onSuccess(Message message, MemberInfo result) {
                if (result == null) {
                    logI("User_Login  result==null");
                    return;
                }
                if (result.getCode() == Api.OK) {
                    if(mCountdownView.isTimeDowning()){
                        mCountdownView.stopCountdownTime();
                    }
                    setMemberInfo(result);
                    //保存最后的登录角色
                    SharepreUtil.putString(UserLoginActivity.this, Constants.LAST_LOGIN_TYPE,Constants.LOGIN_USER);
                    startActivity(new Intent(UserLoginActivity.this, UserHomeActivity.class));

                } else {
                    etAuthCode.setText("");
                }

                toast(result.getText() + "");
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
