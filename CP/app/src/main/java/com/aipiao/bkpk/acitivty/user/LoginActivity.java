package com.aipiao.bkpk.acitivty.user;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aipiao.bkpk.views.ClearEditText;
import com.aipiao.bkpk.R;
import com.aipiao.bkpk.base.BaseActivity;
import com.aipiao.bkpk.bean.bmob.User;
import com.aipiao.bkpk.utils.MD5Utils;
import com.aipiao.bkpk.utils.SharepreUtil;
import com.aipiao.bkpk.utils.Utils;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by caihui on 2018/3/26.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {


    private RelativeLayout superContainer;
    private View layoutTitleBar;
    private TextView buttonBack;
    private TextView title;
    private Button buttonRight;
    private LinearLayout linearLayoutMain;
    private RelativeLayout relInputLayout;
    private View viewLineTop;
    private LinearLayout llPhone;
    private ClearEditText editTextAccount;
    private EditText editTextPwd;
    private Button buttonLogin;
    private CheckBox cbAgree;
    private TextView tvProtocolSelf;
    private ImageView imgLoginWeixin;


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        superContainer = (RelativeLayout) findViewById(R.id.super_container);
        layoutTitleBar = (View) findViewById(R.id.layout_title_bar);
        buttonBack = (TextView) findViewById(R.id.button_back);
        buttonBack.setOnClickListener(this);
        title = (TextView) findViewById(R.id.title);
        buttonRight = (Button) findViewById(R.id.button_right);
        buttonRight.setOnClickListener(this);
        linearLayoutMain = (LinearLayout) findViewById(R.id.linearLayout_main);
        relInputLayout = (RelativeLayout) findViewById(R.id.rel_input_layout);
        viewLineTop = (View) findViewById(R.id.view_line_top);
        llPhone = (LinearLayout) findViewById(R.id.ll_phone);
        editTextAccount = (ClearEditText) findViewById(R.id.editTextAccount);
        editTextPwd = (EditText) findViewById(R.id.editTextPwd);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);
        cbAgree = (CheckBox) findViewById(R.id.cb_agree);
        tvProtocolSelf = (TextView) findViewById(R.id.tv_protocol_self);
        imgLoginWeixin = (ImageView) findViewById(R.id.img_login_weixin);
    }

    @Override
    protected int getLayout() {
        return R.layout.login_activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.button_right:
                startActivity(new Intent(LoginActivity.this, KefuViewlActivity.class));
                break;

            case R.id.buttonLogin:
                String mPhoneaccount = editTextAccount.getText().toString();
                if (!Utils.isMobileNO(mPhoneaccount)) {
                    toast("请输入正确的手机号码");
                    return;
                }
                if (TextUtils.isEmpty(mPhoneaccount)) {
                    toast("手机号码/账号不能为空");
                    return;
                }
                String mPwd = editTextPwd.getText().toString();
                if (TextUtils.isEmpty(mPwd)) {
                    toast("密码不能为空");
                    return;
                }
                svProgressHUD.showWithStatus("正在玩命请求服务器.....");
                User user = new User();
                user.setPhone(mPhoneaccount);
                user.setPwd(MD5Utils.getPwd(mPwd));
                user.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e == null) {
                            SharepreUtil.putString(LoginActivity.this, "objectId", objectId);
                            BmobQuery<User> query = new BmobQuery<User>();
                            query.getObject(objectId, new QueryListener<User>() {
                                @Override
                                public void done(User object, BmobException e) {
                                    svProgressHUD.dismiss();
                                    if (e == null) {
                                        SharepreUtil.putString(LoginActivity.this, "user", gson.toJson(object));
                                        finish();
                                    }
                                }
                            });

                        } else {
                            svProgressHUD.showErrorWithStatus("请检测网络是否正常");
                        }
                    }
                });
                break;
        }
    }
}
