package com.mliuxb.loginusemvc.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.mliuxb.loginusemvc.bean.LoginData;
import com.mliuxb.loginusemvc.global.Constants;
import com.mliuxb.loginusemvc.global.MD5Utils;
import com.mliuxb.loginusemvc.model.LoginModel;
import com.mliuxb.loginusemvc.view.ILoginView;

/**
 * Description:
 * Author     : liuxb
 * Date       : 2018/9/16 23:32
 */

//登录模块的业务逻辑
public class LoginPresenter {
    private static final String TAG = "LoginPresenter";

    private ILoginView loginView;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
    }

    public void login(String phoneNumber, String password) {
        //本地对输入情况做校验
        boolean validateOk = validateInput(phoneNumber, password);
        if (validateOk) {
            loginView.showProgressBar();
            String md5Password = MD5Utils.getMd5(password);

            LoginModel loginModel = new LoginModel();
            loginModel.gotoLogin(phoneNumber, md5Password, new LoginModel.OnNetResponseListener() {
                @Override
                public void onNetResposeError(String msg) {
                    loginView.hideProgressBar();
                    loginView.showToast(msg);
                }

                @Override
                public void onNetReponseSuccess(LoginData loginData) {
                    loginView.hideProgressBar();
                    switch (loginData.status) {
                        case 200: //用户名未注册
                        case 201: //密码有误
                        case 203: //登录失败
                            loginView.showToast(loginData.message);
                            Log.i(TAG, "onResponse: = " + loginData.message);
                            break;
                        case 202:   //登录成功
                            loginView.showToast(loginData.message);
                            Log.i(TAG, "onResponse: = " + loginData.message);

                            //本地保存必要的用户信息
                            //......
                            loginView.jumpSuccessActivity(loginData);
                            break;
                        default:
                            loginView.showToast("登录出现未知异常");
                            break;
                    }
                }
            });
        }
    }

    private boolean validateInput(String phoneNumber, String password) {
        if (TextUtils.isEmpty(phoneNumber)) {
            loginView.showToast("手机号不能为空");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            loginView.showToast("密码不能为空");
            return false;
        }
        if (!phoneNumber.matches(Constants.STR_PHONE_REGEX2)) {  //匹配正则表达式
            loginView.showToast("请输入正确的手机号");
            return false;
        }
        return true;
    }
}
