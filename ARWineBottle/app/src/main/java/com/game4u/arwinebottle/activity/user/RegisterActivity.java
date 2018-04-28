package com.game4u.arwinebottle.activity.user;//package com.walke.winelabel.login.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.game4u.arwinebottle.R;
import com.game4u.arwinebottle.activity.HomeActivity;
import com.game4u.arwinebottle.activity.TitleActivity;
import com.game4u.arwinebottle.bean.ResultCode;
import com.game4u.arwinebottle.bean.ResultLogin;
import com.game4u.arwinebottle.bean.UserInfo;
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

public class RegisterActivity extends TitleActivity {


    @BindView(R.id.register_logo)
    ImageView mLogo;
    @BindView(R.id.register_iivPhone)
    IconInputView mIivPhone;
    @BindView(R.id.register_iivPhoneCode)
    IconInputView mIivAuthCode;
    @BindView(R.id.register_tvGetCode)
    CountdownView mTvGetCode;//TextView
    @BindView(R.id.register_iivPassword)
    IconInputView mIivPassword;
    @BindView(R.id.register_iivPasswordConfirm)
    IconInputView mIivPasswordConfirm;
    @BindView(R.id.register_iivSellCode)
    IconInputView mIivSellCode;
    @BindView(R.id.register_btRegister)
    Button mBtRegister;

    private EditText etPhone;
    private EditText etPassword;
    private EditText etAuthCode;
    private EditText etPasswordConfirm;
    private EditText etSellCode;


    @Override
    protected int rootLayoutId() {
        // TODO: add setContentView(...) invocation
//        ButterKnife.bind(this);
        return R.layout.activity_register;
    }

    @Override
    public void initContentView(TitleBar titleBar) {
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        titleBar.setTitleText("注册");
        mRootView.setBackgroundResource(R.mipmap.bg_register);
        //手机
        etPhone = mIivPhone.getEtInput();

        //密码
        etPassword = mIivPassword.getEtInput();
        //密码检验
        etPasswordConfirm = mIivPasswordConfirm.getEtInput();
        //验证码
        etAuthCode = mIivAuthCode.getEtInput();
        //销售代码
        etSellCode = mIivSellCode.getEtInput();

        etPasswordConfirm = mIivPasswordConfirm.getEtInput();

    }

    @Override
    protected void initData() {
//        etPhone.setText("18218649345");
//        etAuthCode.setText("1234");
//        etPassword.setText("123456");
//        etPasswordConfirm.setText("123456");
//        etSellCode.setText("1111");


        mTvGetCode.init(etPhone,etAuthCode);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @OnClick({R.id.register_tvGetCode, R.id.register_btRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_tvGetCode:
                //toast("获取手机验证码--待完善");
                if (!mIivPhone.isFitPhone()){
                    editTextGetFocus(etPhone);
                    return;
                }
                requestIdentifyCode();

                break;
            case R.id.register_btRegister:
                if (!mIivPhone.isFitPhone()){
                    editTextGetFocus(etPhone);
                    return;
                }
                if (!mIivPassword.isFitPassword()){
                    editTextGetFocus(etPassword);
                    return;
                }
                if (!mIivPasswordConfirm.isFitPassword()){
                    editTextGetFocus(mIivPasswordConfirm.getEtInput());
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
                requestRegister();
                break;
        }
    }

    private void requestIdentifyCode() {
        // TODO: 2017/11/1 请求服务器发送手机验证码
        String phone = etPhone.getText().toString().trim();
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
                //{code=5098}
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


            }

        });
    }

    private void requestRegister() {
        // TODO: 2017/11/1 注册请求
        UserInfo userInfo = new UserInfo();
        String authCode = etAuthCode.getText().toString().trim();

        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String sellCode = etSellCode.getText().toString().trim();
        password = MD5Utils.MD5Encode(Api.PRIVATE_KEY + password + Api.PRIVATE_KEY,"utf-8");
        userInfo.setPhone(phone);
        userInfo.setPassword(password);
        userInfo.setSalesCode(sellCode);
        //参数：1.链接地址拼接，2.object，提交参数，3.回调
        RequestUtil.getInstance(this).register(Api.REGISTER, userInfo,authCode, new RequestUtil.MyCallBack<ResultLogin>() {
            @Override
            public void onSuccess(ResultLogin reponse) {
                //status int 操作状态(1：成功，0：失败)
                if (reponse==null)return;
                toast(reponse.getDesc()+"");
                if (reponse.getCode()== Api.SUCCESS){// 操作状态(1：成功，0：失败)
                    SharepreUtil.putString(RegisterActivity.this, Contants.LAST_LOGIN_NAME,etPhone.getText().toString().trim());//保存最后一个密码登录用户名
                    getWineBottleApp().setUserInfo(reponse.getUserInfo());
                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                    finish();

                    if (mTvGetCode.isTimeDowning()){
                        mTvGetCode.stopCountdownTime();
                    }

                }
            }

            @Override
            public void onSuccess(String str) {
                //{"result":{"func":"register","servertime":"2017-11-11 17:13:48","status":1},"userInfo":{},"code":1,"desc":"注册成功！"}
                //{"result":{"func":"register","servertime":"2017-11-11 17:39:57","status":1},"code":-1,"desc":"账号已被注册过！"}
               /* GsonObject dataGson = new GsonObject(gson.fromJson(str, JsonObject.class)); //对数据转换成json后进行对象封装
                GsonObject result = dataGson.getGsonObject("result");
                if (result!=null){
                    int status = result.getInt("status", 0);//status=1:处理成功；status=-1:服务器处理出错；status=-2:业务处理异常；
                    if (status==1){
                        toast(dataGson.getString("desc")+"");
                        finish();
                    }else if (status==-1){
                        toast("异常，请重试");
                    }else if (status==2){
                        toast("异常，请重试");
                    }
                }*/

                /*try {
                    JSONObject jsonObject = new JSONObject(str);
                    JSONObject personObject = jsonObject.getJSONObject("desc");
                } catch (Exception e) {
                    // TODO: handle exception
                }*/

            }

            @Override
            public void onError(Exception exc) {
                logE(exc.getMessage()+"");
            }
        });

    }
}
