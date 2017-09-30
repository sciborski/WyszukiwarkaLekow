package com.example.dariusz.wyszukiwarkalekow.view.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dariusz.wyszukiwarkalekow.MedicinesStorageApplication;
import com.example.dariusz.wyszukiwarkalekow.R;
import com.example.dariusz.wyszukiwarkalekow.application.base.UseCaseExecutor;
import com.example.dariusz.wyszukiwarkalekow.application.register.RegisterArgument;
import com.example.dariusz.wyszukiwarkalekow.application.register.RegisterUseCase;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Users;
import com.example.dariusz.wyszukiwarkalekow.view.login.LoginActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    private RegisterUseCase registerUseCase;
    private UseCaseExecutor useCaseExecutor;

    @BindView(R.id.nicknameRegister_edittext)
    EditText nicknameView;

    @BindView(R.id.passwordRegister_edittext)
    EditText passwordView;

    @BindView(R.id.password2Register_edittext)
    EditText passwordRepeatView;

    @BindView(R.id.emailRegister_edittext)
    EditText emailView;

    @BindView(R.id.progressLayoutRegister)
    RelativeLayout progressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        registerUseCase = MedicinesStorageApplication.get(this).provideRegisterUseCase();
        useCaseExecutor = MedicinesStorageApplication.get(this).provideUseCaseExecutor();
        hideProgress();

    }

    @OnClick(R.id.registerRegister_button)
    public void onRegisterRegister(View view){
        showProgress();
        String nickname = nicknameView.getText().toString();
        String password = passwordView.getText().toString();
        String passwordRepeat = passwordRepeatView.getText().toString();
        String email = emailView.getText().toString();
        if(!nickname.isEmpty()&&!password.isEmpty()&&!passwordRepeat.isEmpty()&&!email.isEmpty()){
            if (password.equals(passwordRepeat)) {
                RegisterArgument argument = new RegisterArgument(nickname, password, email);

                useCaseExecutor.executeUseCase(registerUseCase, argument, new UseCaseExecutor.Listener<Users>() {

                    @Override
                    public void onResult(Users users) {
                        hideProgress();
                        System.out.println(users.toString());

                        Toast toast = Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_SHORT);
                        toast.show();

                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Exception ex) {
                        hideProgress();
                    }
                });
            } else {
                hideProgress();
                Toast toast = Toast.makeText(getBaseContext(), "Hasła się różnią", Toast.LENGTH_SHORT);
                toast.show();
            }
        }else {
            hideProgress();
            Toast toast = Toast.makeText(getBaseContext(), "Wszystkie pola mają być uzupełnione", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
    private void showProgress(){
        progressLayout.setVisibility(View.VISIBLE);
    }

    private void hideProgress(){
        progressLayout.setVisibility(View.GONE);
    }
}
