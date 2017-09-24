package com.example.dariusz.wyszukiwarkalekow.view.scan;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

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
import com.android.volley.toolbox.Volley;
import com.example.dariusz.wyszukiwarkalekow.MedicinesStorageApplication;
import com.example.dariusz.wyszukiwarkalekow.application.base.UseCaseExecutor;
import com.example.dariusz.wyszukiwarkalekow.application.search.SearchQrArgument;
import com.example.dariusz.wyszukiwarkalekow.application.search.SearchQrUseCase;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Products;
import com.example.dariusz.wyszukiwarkalekow.R;
import com.example.dariusz.wyszukiwarkalekow.data.repository.AuthorizationRepository;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

public class ScanActivity extends AppCompatActivity {
    SurfaceView cameraPreview;

    private SearchQrUseCase searchQrUseCase;
    private UseCaseExecutor useCaseExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        searchQrUseCase = MedicinesStorageApplication.get(getBaseContext()).provideSearchQrUseCase();
        useCaseExecutor = MedicinesStorageApplication.get(getBaseContext()).provideUseCaseExecutor();
        cameraPreview = (SurfaceView) findViewById(R.id.scan_preview);
        createCameraSource();
    }

    private void createCameraSource() {
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).build();
        final CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1600, 1024)
                .build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScanActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if(barcodes.size()>0){
                    //System.out.println(barcodes.valueAt(0).displayValue);
                    /*Intent intent = new Intent(ScanActivity.this,AddActivity.class);
                    intent.putExtra("barcode",barcodes.valueAt(0).displayValue);
                    startActivity(intent);*/
                    connect(barcodes.valueAt(0).displayValue);

                }
            }
        });
    }

    public void connect(final String barcode){
        SearchQrArgument argument = new SearchQrArgument(
                barcode
        );
        useCaseExecutor.executeUseCase(searchQrUseCase, argument, new UseCaseExecutor.Listener<List<Products>>() {
            @Override
            public void onResult(List<Products> products) {
                System.out.println(products.get(0).getName());
                compare(products,barcode);
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        });
        /*RequestQueue queue = Volley.newRequestQueue(this); // łączenie się ze stroną w celu pobrania danych z bazy danych
        //String url = "http://10.0.2.2:8000/search/"+barcode;
        String url = "http://192.168.122.19/web/app_dev.php/search/"+barcode;
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // respone zawiera odpowiedz w postaci obueku JSON
                        compare(response.toString(),barcode);

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
        queue.add(getRequest);*/
    }
    public void compare(List<Products> productsArray,String barcode){     //(String response,String barcode)


        Products product;

        /*Gson gson = new Gson();                                       wczesniej poprawnie działało
        Products[] productsArray = gson.fromJson(response,Products[].class);
        Products product;*/


        /*Intent intent = new Intent(this,AddV2Activity.class);          tymczasowy komentarz
        if(productsArray.length>0) {
            for (int i = 0; i < productsArray.length; i++) {
                if (productsArray[i].getQrCode().equals(barcode)) {
                    product = productsArray[i];
                    //intent.putExtra("barcode",productsArray[i]);
                    intent.putExtra(AddV2Activity.EXTRA_PRODUCT, product);
                    startActivity(intent);
                    //finish();
                }
            }
        } else {
            intent.putExtra(AddV2Activity.EXTRA_BARCODE, barcode);
            startActivity(intent);
            //finish();
        }*/

        Intent intent = new Intent();
        if(productsArray.size()>0) {
            for (int i = 0; i < productsArray.size(); i++) {
                System.out.println("Print ln do wyswietlenia czegos"+productsArray.get(i).getQrCode());
                if (productsArray.get(i).getQrCode().equals(barcode)) {
                    product = productsArray.get(i);
                    //intent.putExtra("barcode",productsArray[i]);
                    intent.putExtra("product", product);
                    setResult(1,intent);
                    finish();
                }
            }
        } else {
            intent.putExtra("barcode", barcode);
            setResult(2,intent);
            finish();
        }
        /*Intent intent = new Intent();                             wcześniej poprawnie działało
        if(productsArray.length>0) {
            for (int i = 0; i < productsArray.length; i++) {
                if (productsArray[i].getQrCode().equals(barcode)) {
                    product = productsArray[i];
                    //intent.putExtra("barcode",productsArray[i]);
                    intent.putExtra("product", product);
                    setResult(1,intent);
                    finish();
                }
            }
        } else {
            intent.putExtra("barcode", barcode);
            setResult(2,intent);
            finish();
        }*/
        //Toast toast=Toast.makeText(this, "coś niedziała", Toast.LENGTH_SHORT);
        //toast.show();
    }
}
