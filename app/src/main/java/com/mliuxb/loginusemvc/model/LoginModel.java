package com.mliuxb.loginusemvc.model;

import android.util.Log;

import com.google.gson.Gson;
import com.mliuxb.loginusemvc.bean.LoginData;
import com.mliuxb.loginusemvc.global.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Description: Model层只用来产生数据的
 * Author     : liuxb
 * Date       : 2018/7/15 18:29
 */
//Model层只用来产生数据的
public class LoginModel {
    private static final String TAG = "LoginModel";

    public void gotoLogin(String phoneNumber, String md5Password, final OnNetResponseListener listener) {
        OkHttpUtils
                .post()
                .url(Constants.URL_LOGIN)
                .addParams("phoneNumber", phoneNumber)
                .addParams("password", md5Password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        Log.i(TAG, "onError: ---网络访问出现异常---" + e.getMessage());
                        e.printStackTrace();
                        listener.onNetResposeError("网络访问出现异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "onResponse: 登录成功 response = " + response + " ---");
                        Gson gson = new Gson();
                        LoginData loginData = gson.fromJson(response, LoginData.class);
                        listener.onNetReponseSuccess(loginData);
                    }
                });
    }

    public interface OnNetResponseListener {

        void onNetResposeError(String msg);

        void onNetReponseSuccess(LoginData loginData);
    }
}
