package com.example.dariusz.wyszukiwarkalekow.view.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.dariusz.wyszukiwarkalekow.MedicinesStorageApplication;
import com.example.dariusz.wyszukiwarkalekow.R;
import com.example.dariusz.wyszukiwarkalekow.application.Logout.LogoutUseCase;
import com.example.dariusz.wyszukiwarkalekow.application.base.UseCaseExecutor;
import com.example.dariusz.wyszukiwarkalekow.view.login.LoginActivity;
import com.example.dariusz.wyszukiwarkalekow.view.search.LookingForActivity;
import com.example.dariusz.wyszukiwarkalekow.view.add.AddActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity {
    private LogoutUseCase logoutUseCase;
    private UseCaseExecutor useCaseExecutor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        logoutUseCase = MedicinesStorageApplication.get(this).proviceLogoutUseCase();
        useCaseExecutor = MedicinesStorageApplication.get(this).provideUseCaseExecutor();
    }
    @OnClick(R.id.searchMenu_button)
    public void onSearch(View view){
        Intent intent = new Intent(this,LookingForActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.addMenu_button)
    public void onAdd(View view){
        Intent intent = new Intent(this,AddActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.logoutMenu_button)
    public void onLogout(View view){
        useCaseExecutor.executeUseCase(logoutUseCase,null, new UseCaseExecutor.Listener<Boolean>() {
            @Override
            public void onResult(Boolean isLoggedOff) {
                if(isLoggedOff){
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(Exception ex) {

            }
        });
    }
}
