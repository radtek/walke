package com.game4u.arwinebottle.login.presenter;

/**
 * Created by walke.Z on 2017/8/31.
 *
 * Class Note:登陆的Presenter 的接口，实现类为LoginPresenter，完成登陆的验证，以及销毁当前view
 */
public interface LoginPresenterEvent {
    //验证凭证(验证用户信息)
    void validateCredentials(String username, String password);

    void onDestroy();
}