package com.mliuxb.loginusemvc.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mliuxb.loginusemvc.LoginSuccessActivity;
import com.mliuxb.loginusemvc.R;
import com.mliuxb.loginusemvc.bean.LoginData;
import com.mliuxb.loginusemvc.presenter.LoginPresenter;
import com.mliuxb.loginusemvc.view.ILoginView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginView {
    private static final String TAG = "LoginActivity";

    private EditText    etPhoneNumber;
    private EditText    etPassword;
    private Button      btnLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:    //登录
                String phoneNumber = etPhoneNumber.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                //login(phoneNumber, password);
                LoginPresenter loginPresenter = new LoginPresenter(this);
                loginPresenter.login(phoneNumber, password);
                break;
            default:
                break;
        }
    }

    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }
    
    public void showToast(String toast){
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    public void jumpSuccessActivity(LoginData loginData){
        Intent intent = new Intent(LoginActivity.this, LoginSuccessActivity.class);
        startActivity(intent);
        //登录页面直接消失
        finish();
    }
}