package com.example.dariusz.wyszukiwarkalekow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }
    public void onAdd(View view) {
        //pobranie informacji z pól
        EditText nameAdd = (EditText) findViewById(R.id.nameAdd);
        String nameText = nameAdd.getText().toString();

        EditText townAdd = (EditText) findViewById(R.id.townAdd);
        String townText = townAdd.getText().toString();

        EditText streetAdd = (EditText) findViewById(R.id.streetAdd);
        String streetText = streetAdd.getText().toString();

        EditText priceAdd = (EditText) findViewById(R.id.priceAdd);
        String priceText = priceAdd.getText().toString();

        //wysłanie json na serwer
        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("name", nameText);
        jsonParams.put("town", townText);
        jsonParams.put("street", streetText);
        jsonParams.put("price", priceText);
        jsonParams.put("id", "0");//todo dodawanie uzytkownika
        jsonParams.put("type","0");
        JSONObject postData = new JSONObject(jsonParams);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8000/add";

        JsonObjectRequest   postRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // response zawiera odpowiedź w postaci obiektu JSON
                            TextView textView = (TextView)findViewById(R.id.testAdd);
                            textView.setText(response.toString());
                            }
                    },
                new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Błąd!
                            }
                    }) {
        };
        queue.add(postRequest);



        }

}
