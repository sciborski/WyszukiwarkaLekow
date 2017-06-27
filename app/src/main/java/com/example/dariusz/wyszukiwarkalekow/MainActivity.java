package com.example.dariusz.wyszukiwarkalekow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLogin(View view){
        EditText nicknameView = (EditText) findViewById(R.id.nickname);
        String nicknameText = nicknameView.getText().toString();
        EditText passwordView = (EditText) findViewById(R.id.password);
        String passwordText = passwordView.getText().toString();
        passwordView.setText("test");
        if(nicknameText.length()>3 && passwordText.length()>3) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }else{
            Toast toast=Toast.makeText(this, "brak danych", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void onRegister(View view){
        Toast toast=Toast.makeText(this, "jeszcze nie działa", Toast.LENGTH_SHORT);
        toast.show();
    }
}
