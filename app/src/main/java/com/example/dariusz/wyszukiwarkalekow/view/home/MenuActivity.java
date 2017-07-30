package com.example.dariusz.wyszukiwarkalekow.view.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.dariusz.wyszukiwarkalekow.R;
import com.example.dariusz.wyszukiwarkalekow.view.search.LookingForActivity;
import com.example.dariusz.wyszukiwarkalekow.view.add.AddActivity;
import com.example.dariusz.wyszukiwarkalekow.view.scan.ScanActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public void onSearch(View view){
        Intent intent = new Intent(this,LookingForActivity.class);
        startActivity(intent);
    }
    public void onAdd(View view){
        Intent intent = new Intent(this,AddActivity.class);
        startActivity(intent);
    }
    public void onAddv2(View view){
        Intent intent = new Intent(this,ScanActivity.class);
        startActivity(intent);
    }
}
