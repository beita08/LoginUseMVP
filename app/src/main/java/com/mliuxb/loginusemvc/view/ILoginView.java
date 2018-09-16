package com.mliuxb.loginusemvc.view;

import com.mliuxb.loginusemvc.bean.LoginData;

/**
 * Description:
 * Author     : liuxb
 * Date       : 2018/9/17 0:51
 */
public interface ILoginView {

    public void showProgressBar();

    public void hideProgressBar();

    public void showToast(String toast);

    public void jumpSuccessActivity(LoginData loginData);
}
