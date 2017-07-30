package com.example.dariusz.wyszukiwarkalekow.view.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dariusz.wyszukiwarkalekow.MedicinesStorageApplication;
import com.example.dariusz.wyszukiwarkalekow.R;
import com.example.dariusz.wyszukiwarkalekow.application.base.UseCaseExecutor;
import com.example.dariusz.wyszukiwarkalekow.application.login.LoginCredentials;
import com.example.dariusz.wyszukiwarkalekow.application.login.LoginResult;
import com.example.dariusz.wyszukiwarkalekow.application.login.LoginUseCase;
import com.example.dariusz.wyszukiwarkalekow.view.home.MenuActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.nickname)
    EditText nicknameView;

    @BindView(R.id.password)
    EditText passwordView;

    private LoginUseCase loginUseCase;
    private UseCaseExecutor useCaseExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loginUseCase = MedicinesStorageApplication.get(this).provideLoginUseCase();
        useCaseExecutor = MedicinesStorageApplication.get(this).provideUseCaseExecutor();
    }

    @OnClick(R.id.login_button)
    public void onLogin(View view){
        String nicknameText = nicknameView.getText().toString();
        String passwordText = passwordView.getText().toString();

        LoginCredentials credentials = new LoginCredentials(nicknameText,passwordText);
        useCaseExecutor.executeUseCase( loginUseCase, credentials, new UseCaseExecutor.Listener<LoginResult>() {
            @Override
            public void onResult(LoginResult loginResult) {
                Intent intent = new Intent(getBaseContext(), MenuActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(Exception ex) {
              Toast.makeText(getBaseContext(), ex.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onRegister(View view){
        Toast toast=Toast.makeText(this, "jeszcze nie działa", Toast.LENGTH_SHORT);
        toast.show();
    }
}
