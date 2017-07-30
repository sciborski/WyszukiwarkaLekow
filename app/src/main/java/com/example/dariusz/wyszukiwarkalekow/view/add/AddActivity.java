package com.example.dariusz.wyszukiwarkalekow.view.add;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
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
import com.example.dariusz.wyszukiwarkalekow.R;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Products;
import com.example.dariusz.wyszukiwarkalekow.view.scan.ScanActivity;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.dariusz.wyszukiwarkalekow.R.layout.activity_add);

        /*Intent intent = getIntent();
        String kod = intent.getStringExtra("barcode");
        TextView testTextView = (TextView) findViewById(R.id.testAddv2);
        testTextView.setText(kod);*/


        //do gps!


    }

    public void onAdd(View view) {
        //pobranie informacji z pól
        EditText nameAdd = (EditText) findViewById(R.id.nameAdd);
        String nameText = nameAdd.getText().toString();

        EditText qrCodeAdd = (EditText) findViewById(R.id.qrCodeAdd);
        String qrCodeText = qrCodeAdd.getText().toString();

        EditText townAdd = (EditText) findViewById(R.id.townAdd);
        final String townText = townAdd.getText().toString();

        EditText streetAdd = (EditText) findViewById(R.id.streetAdd);
        final String streetText = streetAdd.getText().toString();

        EditText priceAdd = (EditText) findViewById(R.id.priceAdd);
        final String priceText = priceAdd.getText().toString();

      /*  //wysłanie json na serwer
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
        queue.add(postRequest);*/

        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("name", nameText);
        jsonParams.put("qrCode", qrCodeText);
        JSONObject postData = new JSONObject(jsonParams);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.122.19/web/app_dev.php/add_product";
        final String urlv2 = "http://192.168.122.19/web/app_dev.php/add_location";
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response zawiera odpowiedź w postaci obiektu JSON
                        TextView textView = (TextView) findViewById(R.id.testAdd);
                        textView.setText(response.toString());
                        //int idProd = response.getInt("idProduct");
                        Gson gson = new Gson();
                        Products products = gson.fromJson(response.toString(), Products.class);
                        products.getIdProduct();

                        //wiem ze to jest kiepskie! :(
                        connect(urlv2, townText, streetText, priceText, products.getIdProduct());

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

    private void connect(String url, String town, String street, String price, int idProduct) {

        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("town", town);
        jsonParams.put("street", street);
        jsonParams.put("price", price);
        jsonParams.put("idUser", "2");
        jsonParams.put("idProduct", idProduct + "");
        JSONObject postData = new JSONObject(jsonParams);
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response zawiera odpowiedź w postaci obiektu JSON
                        TextView textView = (TextView) findViewById(R.id.testAdd);
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

    public void onGps(View view) {
        LocationManager locationManager;
        LocationListener locationListener;

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double lon=location.getLongitude();
                double lat=location.getLatitude();
                int max = 5;
                Geocoder geo = new Geocoder(AddActivity.this, Locale.getDefault());
                try{
                    List<Address> address=geo.getFromLocation(lat,lon, 1);
                    TextView test = (TextView) findViewById(R.id.testAddv2);
                    test.setText(address.get(0).getLocality()+" "+address.get(0).getAddressLine(0));
                }catch(Exception e){

                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET}, 10);
                 }
                return;
            }
        //locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        locationManager.requestSingleUpdate("gps",locationListener,null);

    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if(grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED){

                }
        }
    }*/

    public void onScan(View view){
        Intent intent = new Intent(this,ScanActivity.class);
        startActivityForResult(intent,5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EditText nameAdd = (EditText) findViewById(R.id.nameAdd);

        EditText qrCodeAdd = (EditText) findViewById(R.id.qrCodeAdd);

        if(requestCode == 5){
            switch (resultCode){
                case 1:
                    Products products =(Products) data.getSerializableExtra("product");
                    nameAdd.setText(products.getName());
                    qrCodeAdd.setText(products.getQrCode());
                    break;
                case 2:
                    String qrcode = data.getStringExtra("barcode");
                    qrCodeAdd.setText(qrcode);
                    break;
            }
        }
    }
}
