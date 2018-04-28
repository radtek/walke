package com.game4u.arwinebottle.activity.user;//package com.walke.winelabel.login.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.game4u.arwinebottle.R;
import com.game4u.arwinebottle.activity.TitleActivity;
import com.game4u.arwinebottle.bean.ResultCode;
import com.game4u.arwinebottle.bean.ResultLogin;
import com.game4u.arwinebottle.config.Contants;
import com.game4u.arwinebottle.net.Api;
import com.game4u.arwinebottle.net.MD5Utils;
import com.game4u.arwinebottle.net.RequestUtil;
import com.google.gson.Gson;
import walke.base.widget.CountdownView;
import walke.base.widget.IconInputView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import walke.base.tool.SharepreUtil;
import walke.base.widget.TitleBar;


/**
 * Created by walke.Z on 2017/8/30.
 */

public class ResetPasswordActivity extends TitleActivity {


    @BindView(R.id.arp_iivPhone)
    IconInputView mIivPhone;
    @BindView(R.id.arp_iivPhoneCode)
    IconInputView mIivAuthCode;
    @BindView(R.id.arp_tvGetCode)
    CountdownView mTvGetCode;//TextView
    @BindView(R.id.arp_iivPassword)
    IconInputView mIivPassword;
    @BindView(R.id.arp_iivPasswordConfirm)
    IconInputView mIivPasswordConfirm;
    @BindView(R.id.arp_btReset)
    Button mBtReset;

    private EditText etPhone;
    private EditText etPassword;
    private EditText etAuthCode;
    private EditText etPasswordConfirm;


    @Override
    protected int rootLayoutId() {
        // TODO: add setContentView(...) invocation
//        ButterKnife.bind(this);
        return R.layout.activity_reset_password;
    }

    @Override
    public void initContentView(TitleBar titleBar) {
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        titleBar.setTitleText("重置密码");
        mRootView.setBackgroundResource(R.mipmap.bg_forgot);
        //手机
        etPhone = mIivPhone.getEtInput();

        //密码
        etPassword = mIivPassword.getEtInput();
        //密码检验
        etPasswordConfirm = mIivPasswordConfirm.getEtInput();

        //验证码
        etAuthCode = mIivAuthCode.getEtInput();

        etPasswordConfirm = mIivPasswordConfirm.getEtInput();

    }

    @Override
    protected void initData() {
//        etPhone.setText("18218649345");
//        etPassword.setText("654321");
//        etPasswordConfirm.setText("654321");
//        etAuthCode.setText("");


        mTvGetCode.init(etPhone,etAuthCode);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @OnClick({R.id.arp_tvGetCode, R.id.arp_btReset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arp_tvGetCode:
//                toast("获取手机验证码--待完善");
                if (!mIivPhone.isFitPhone()){
                    editTextGetFocus(etPhone);
                    return;
                }
                requestIdentifyCode();

                break;
            case R.id.arp_btReset:
                if (!mIivPhone.isFitPhone()){
                    editTextGetFocus(etPhone);
                    return;
                }
                if (!mIivPassword.isFitPassword()){
                    editTextGetFocus(etPassword);
                    return;
                }
                if (!mIivAuthCode.isFitPhoneAuthCode()){
                    editTextGetFocus(etAuthCode);
                    return;
                }
                if (!etPassword.getText().toString().trim().equals(etPasswordConfirm.getText().toString().toLowerCase())){
                    toast("两次输入的密码不一致");
                    editTextGetFocus(etPassword);
                    return;
                }
                requestResetPassword();
                break;
        }
    }

    private void requestIdentifyCode() {
        // TODO: 2017/11/1 请求服务器发送手机验证码
        String phone = etPhone.getText().toString().trim();
//        SharepreUtil.putString(mTvGetCode.getContext(), Contants.LAST_REGISTER_PHONE,phone);//保存获取手机验证码最近的手机号
        mTvGetCode.savePhone(phone);
        RequestUtil.getInstance(this).phoneAuthCode(phone, new RequestUtil.MyCallBack<ResultLogin>() {

            @Override
            public void onError(Exception exc) {
                logE(exc.getMessage());
            }
            @Override
            public void onSuccess(ResultLogin reponse) {
                if (reponse==null)return;

            }

            @Override
            public void onSuccess(String str) {
                //{code=5098}以前  {"code":"7401"}现在
                logI(str);
                ResultCode resultCode = new Gson().fromJson(str, ResultCode.class);
                if (resultCode.getCode()== Api.SUCCESS){
                    /*mTvGetCode.startTimeDown(CountdownView.MAX_TIME);//默认倒计时长 Contants.COUNT_DOWN_TIME
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            editTextGetFocus(etAuthCode);
                        }
                    },200);*/
                    mTvGetCode.startTimeDown(CountdownView.MAX_TIME,etAuthCode);//默认倒计时长 Contants.COUNT_DOWN_TIME
                    toast("验证码已发送到您的手机");
                }else {
                    toast("验证码获取失败，请稍后重试");
                }
                //{"result":{"func":"login","servertime":"2017-11-11 19:07:29","status":1},"code":-2,"desc":"验证码不正确"}
                /*ResultBean resultBean = gson.fromJson(str, ResultBean.class);
                if (null!= resultBean.getResult()){
                    int status = resultBean.getResult().getStatus();
                    if (1==status){//成功

                    }
                }
                toast(resultBean.getDesc()+"");*/
            }

        });
    }

    private void requestResetPassword() {
        // TODO: 2017/11/1 重置密码
        // TODO: 2017/11/1 登录请求

        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String authCode = etAuthCode.getText().toString().trim();
        password = MD5Utils.MD5Encode(Api.PRIVATE_KEY + password + Api.PRIVATE_KEY,"utf-8");
        RequestUtil.getInstance(this).resetPassword(phone, password, authCode, new RequestUtil.MyCallBack<ResultLogin>() {

            @Override
            public void onError(Exception exc) {
                logE(exc.getMessage());
            }
            @Override
            public void onSuccess(ResultLogin reponse) {
                if (reponse==null)return;
                toast(reponse.getDesc()+"");
                if (reponse.getCode()== Api.SUCCESS){// 操作状态(1：成功，0：失败)
                    SharepreUtil.putString(ResetPasswordActivity.this, Contants.LAST_LOGIN_NAME,etPhone.getText().toString().trim());//保存最后一个密码登录用户名
                    if (mTvGetCode.isTimeDowning()){
                        mTvGetCode.stopCountdownTime();
                    }
                    finish();
                }
            }

            @Override
            public void onSuccess(String str) {
                logI(str);
                //{"result":{"func":"login","servertime":"2017-11-11 19:07:29","status":1},"code":-2,"desc":"验证码不正确"}
                /*ResultBean resultBean = gson.fromJson(str, ResultBean.class);
                if (null!= resultBean.getResult()){
                    int status = resultBean.getResult().getStatus();
                    if (1==status){//成功

                    }
                }
                toast(resultBean.getDesc()+"");*/
            }

        });
    }
}
