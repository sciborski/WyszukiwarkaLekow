package com.example.dariusz.wyszukiwarkalekow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddV2Activity extends AppCompatActivity {
    public static final String EXTRA_PRODUCT="product";
    public static final String EXTRA_BARCODE="barcode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_v2);

        Intent intentProd = getIntent();
        Products products =(Products) intentProd.getSerializableExtra(EXTRA_PRODUCT);

        Intent intentQr = getIntent();
        String qr = (String) intentQr.getStringExtra(EXTRA_BARCODE);

        TextView nameAddv2 = (TextView) findViewById(R.id.nameAddv2);
        TextView qrAddv2 = (TextView) findViewById(R.id.qrAddv2);
        if(products!=null) {
            nameAddv2.setText(products.getName());
            qrAddv2.setText(products.getQrCode());
        }else {
            nameAddv2.setText("pusty tekst");
            qrAddv2.setText(qr);
        }
    }
    public void onAdd(View view){
        Intent intent = getIntent();
        Products products =(Products) intent.getSerializableExtra("barcode");
        //pobranie informacji z pól
        EditText townAdd = (EditText) findViewById(R.id.townAddv2);
        String townText = townAdd.getText().toString();

        EditText streetAdd = (EditText) findViewById(R.id.streetAddv2);
        String streetText = streetAdd.getText().toString();

        EditText priceAdd = (EditText) findViewById(R.id.priceAddv2);
        String priceText = priceAdd.getText().toString();

        //wysłanie json na serwer
        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("town", townText);
        jsonParams.put("street", streetText);
        jsonParams.put("price", priceText);
        jsonParams.put("idUser", "2");//todo dodawanie uzytkownika
        jsonParams.put("idProduct", ""+products.getIdProduct());
        JSONObject postData = new JSONObject(jsonParams);
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "http://10.0.2.2:8000/add_product";
        String url = "http://192.168.122.19/web/app_dev.php/add_product";

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response zawiera odpowiedź w postaci obiektu JSON
                        TextView textView = (TextView)findViewById(R.id.testAddv2);
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
