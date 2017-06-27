package com.example.dariusz.wyszukiwarkalekow;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LookingForActivity extends AppCompatActivity {

    private static final int SECOND_ACTIVITY_RESULT_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looking_for);
    }
    public void onSearchData(View view){
        EditText nameView = (EditText) findViewById(R.id.name);
        String nameText = nameView.getText().toString();
        EditText townView = (EditText) findViewById(R.id.town);
        String townText = townView.getText().toString();


        RequestQueue queue = Volley.newRequestQueue(this); // łączenie się ze stroną w celu pobrania danych z bazy danych
        //String url = "http://10.0.2.2:8000/search/"+townText+"/"+nameText;
        String url = "http://192.168.122.19/web/app_dev.php/search/"+townText+"/"+nameText;
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                // respone zawiera odpowiedz w postaci obueku JSON
                                TextView testView = (TextView) findViewById(R.id.test);
                                try{

                                    double lek = response.getJSONObject(0).getDouble("price");
                                    testView.setText("Response: "+lek);
                                    JsonParsing(response.toString());
                                }catch(JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        TextView testView = (TextView) findViewById(R.id.test);
                        String message = null;
                        if (volleyError instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                        } else if (volleyError instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                        } else if (volleyError instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                        }
                        testView.setText("Response: "+message);
            }
        });

        queue.add(getRequest);
    }

    private void JsonParsing(String response){ //parsowanie z json na tablice obiektów products
        Gson gson = new Gson();
        //Products[] productsArray = gson.fromJson(response,Products[].class);
        Localizations[] localizationsArray = gson.fromJson(response,Localizations[].class);
        Intent intent = new Intent(this,MedicinesListActivity.class);
        //intent.putExtra(MedicinesListActivity.EXTRA_MESSAGE,productsArray);
        intent.putExtra(MedicinesListActivity.EXTRA_MESSAGE,localizationsArray);
        startActivity(intent);
    }
    public void onScanData(View view){
        Intent intent = new Intent(this,ScanActivity.class);
        startActivityForResult(intent,5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 5){
            switch (resultCode){
                case 1:
                    Products products =(Products) data.getSerializableExtra("product");
                    EditText nameView = (EditText) findViewById(R.id.name);
                    nameView.setText(products.getName());
                    break;
                case 2:
                    Toast toast=Toast.makeText(this, "nieznany produkt", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
            }
        }
    }
}
