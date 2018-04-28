package com.game4u.arwinebottle.login.presenter;


import com.game4u.arwinebottle.login.view.LoginEvent;
import com.game4u.arwinebottle.login.view.OnLoginFinishedListener;

/**
 * Created by walke.Z on 2017/8/31.
 *  1 完成presenter的实现。这里面主要是Model层和View层的交互和操作。
 *  2  presenter里面还有个OnLoginFinishedListener，
 * 其在Presenter层实现，给Model层回调，更改View层的状态，
 * 确保 Model层不直接操作View层。如果没有这一接口在LoginPresenterImpl实现的话，
 * LoginPresenterImpl只 有View和Model的引用那么Model怎么把结果告诉View呢？
 */

public class LoginPresenter implements LoginPresenterEvent,OnLoginFinishedListener {
//    private LoginModelEvent mLoginModelEvent;
    private LoginEvent mLoginEvent;

    public LoginPresenter(LoginEvent LoginEvent) {
        mLoginEvent = LoginEvent;
//        mLoginModelEvent = new LoginModel();
    }

    @Override
    public void validateCredentials(String username, String password) {
        if (mLoginEvent != null) {
            mLoginEvent.showProgress();
        }
//        mLoginModelEvent.login(username,password,this);
    }

    /**
     * 给Activity调用
     */
    @Override
    public void onDestroy() {
        if (null!=mLoginEvent)
            mLoginEvent = null;
    }

    @Override
    public void onError(String text) {
        mLoginEvent.hideProgress();
        mLoginEvent.toastTips(text);
    }


    @Override
    public void onSuccess() {
        mLoginEvent.toastTips("登录成功");
        mLoginEvent.hideProgress();
    }
}
