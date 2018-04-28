package com.game4u.arwinebottle.login.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.game4u.arwinebottle.R;
import com.game4u.arwinebottle.login.presenter.LoginPresenter;

import walke.base.activity.ActionBarActivity;
import walke.base.widget.TitleBar;


/**
 * Created by walke.Z on 2017/8/30.
 * MVP模式中View层对应一个activity，这里是登陆的activity
 * demo的代码流程：Activity做了一些UI初始化的东西并需要实例化对应
 * 实现 LoginView的接口，监听界面动作(回调对应设置界面显示)、由LoginPresenter调用，
 * 在onClick里接收到即通过LoginPresenter
 * 的引用把它交给LoginPresenter处理。LoginPresenter接收到了登陆的逻辑就知道要登陆了，
 * 然后LoginPresenter显示进度条并且把逻辑交给我们的Model去处理，也就是这里面的LoginModel，
 * （LoginModel的实现类LoginModelImpl），同时会把OnLoginFinishedListener也就是LoginPresenter
 * 自身传递给我们的Model（LoginModel）。LoginModel处理完逻辑之后，结果通过
 * OnLoginFinishedListener回调通知LoginPresenter，LoginPresenter再把结果返回给view层的Activity，
 * 最后activity显示结果
 *
 */

public class Login2Activity extends ActionBarActivity implements LoginEvent {
    private EditText etUserName;
    private EditText etPassword;
    private Button bLogin;
    private LoginPresenter mLoginPresenter;
    private ProgressBar mProgressBar;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_login2;
    }

    @Override
    protected void initView(TitleBar titleBar) {
        titleBar.setTitleText("登录");
        etUserName = ((EditText) findViewById(R.id.login2_username));
        etPassword = ((EditText) findViewById(R.id.login2_password));
        mProgressBar = ((ProgressBar) findViewById(R.id.login2_progress));
        mProgressBar.setVisibility(View.INVISIBLE);
        bLogin = ((Button) findViewById(R.id.button));
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.validateCredentials(
                        etUserName.getText().toString().trim(),
                        etPassword.getText().toString().trim()
                );
            }
        });
    }

    @Override
    protected void initData() {
        mLoginPresenter = new LoginPresenter(this);
    }


    @Override
    public void toastTips(final String text) {
        toast(text);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.onDestroy();
    }
}
