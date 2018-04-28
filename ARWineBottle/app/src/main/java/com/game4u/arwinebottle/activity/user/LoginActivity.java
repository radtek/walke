package com.game4u.arwinebottle.activity.user;//package com.walke.winelabel.login.view;

import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.game4u.arwinebottle.R;
import com.game4u.arwinebottle.activity.HomeActivity;
import com.game4u.arwinebottle.activity.TitleActivity;
import com.game4u.arwinebottle.bean.ResultLogin;
import com.game4u.arwinebottle.config.Contants;
import com.game4u.arwinebottle.config.Permission;
import com.game4u.arwinebottle.net.Api;
import com.game4u.arwinebottle.net.MD5Utils;
import com.game4u.arwinebottle.net.RequestUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import walke.base.tool.PermissionUtil;
import walke.base.tool.SharepreUtil;
import walke.base.widget.IconInputView;
import walke.base.widget.ImageTextView;
import walke.base.widget.TitleBar;


/**
 * Created by walke.Z on 2017/8/30.
 */

public class LoginActivity extends TitleActivity {


    @BindView(R.id.login_logo)
    ImageView mLogo;
    @BindView(R.id.login_iivUsername)
    IconInputView mIivUsername;
    @BindView(R.id.login_iivPassword)
    IconInputView mIivPassword;
    @BindView(R.id.login_tvRegister)
    TextView mTvRegister;
    @BindView(R.id.login_tvForgotPassword)
    TextView mTvForgotPassword;
    @BindView(R.id.login_btLogin)
    Button mBtLogin;
    @BindView(R.id.tl_qqLogin)
    ImageTextView mQQLogin;
    @BindView(R.id.tl_wechatLogin)
    ImageTextView mWechatLogin;
    private EditText etUserName;
    private EditText etPassword;

   /* @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initData();
    }
*/
    @Override
    protected int rootLayoutId() {
        return R.layout.activity_login;

    }

    @Override
    public void initContentView(TitleBar titleBar) {
        ButterKnife.bind(this);
        titleBar.setTitleText("登录");
        mRootView.setBackgroundResource(R.mipmap.bg_login);
        //手机号
        etUserName = mIivUsername.getEtInput();
        //密码
        etPassword=mIivPassword.getEtInput();

        String lastName = SharepreUtil.getString(this, Contants.LAST_LOGIN_NAME);
        if (!TextUtils.isEmpty(lastName)) {
            etUserName.setText(lastName);
            etUserName.setSelection(etUserName.getText().length());
            enterGetFocus(etPassword);
        }else {
        }



    }
    @Override
    protected void initData() {
//        etUserName.setText("18218649345");
//        etPassword.setText("123456");

        initPermission(Permission.PERMISSIONS, Permission.REQUEST_CODE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.login_tvRegister, R.id.login_tvForgotPassword, R.id.login_btLogin})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login_tvRegister:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_tvForgotPassword:
                Intent intentReset = new Intent(this, ResetPasswordActivity.class);
                startActivity(intentReset);
                break;
            case R.id.login_btLogin:
                //startActivity(new Intent(this, HomeActivity.class));
                if (!mIivUsername.isFitPhone()){
                    editTextGetFocus(etUserName);
                    return;
                }
                if (!mIivPassword.isFitPassword()){
                    editTextGetFocus(etPassword);
                    return;
                }

                requestLogin();

                break;
        }
    }

    private void requestLogin() {
        // TODO: 2017/11/1 登录请求

        if (Build.VERSION.SDK_INT >= 23) {
            if (PermissionUtil.checkPermissionSetLack(this, Permission.PERMISSIONS)){
                requestPermissions(Permission.PERMISSIONS,Permission.REQUEST_CODE);
                return;
            }
        }

        String userName = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        password = MD5Utils.MD5Encode(Api.PRIVATE_KEY + password + Api.PRIVATE_KEY,"utf-8");
        RequestUtil.getInstance(this).login(Api.LOGIN, userName, password, "", new RequestUtil.MyCallBack<ResultLogin>() {

            @Override
            public void onError(Exception exc) {
                logE(exc.getMessage());
            }
            @Override
            public void onSuccess(ResultLogin reponse) {
                if (reponse==null)return;
                toast(reponse.getDesc()+"");
                if (reponse.getCode()== Api.SUCCESS){// 操作状态(1：成功，0：失败)
                    SharepreUtil.putString(LoginActivity.this, Contants.LAST_LOGIN_NAME,etUserName.getText().toString().trim());//保存最后一个密码登录用户名
                    getWineBottleApp().setUserInfo(reponse.getUserInfo());
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }

            }

            @Override
            public void onSuccess(String str) {
                logI(str);
                //{"result":{"func":"login","servertime":"2017-11-11 19:07:29","status":1},"code":-2,"desc":"验证码不正确"}
               /* ResultBean resultBean = gson.fromJson(str, ResultBean.class);
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
