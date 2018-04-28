package com.game4u.arwinebottle.login.view;

/**
 * Created by walke.Z on 2017/8/31.
 */

public interface LoginEvent {

    void toastTips(String text);

    void showProgress();

    void hideProgress();
}
