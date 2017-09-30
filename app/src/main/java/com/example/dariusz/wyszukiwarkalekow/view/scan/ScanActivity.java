package com.example.dariusz.wyszukiwarkalekow.view.scan;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.dariusz.wyszukiwarkalekow.MedicinesStorageApplication;
import com.example.dariusz.wyszukiwarkalekow.application.base.UseCaseExecutor;
import com.example.dariusz.wyszukiwarkalekow.application.search.SearchQrArgument;
import com.example.dariusz.wyszukiwarkalekow.application.search.SearchQrUseCase;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Products;
import com.example.dariusz.wyszukiwarkalekow.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.List;

public class ScanActivity extends AppCompatActivity {
    private SurfaceView cameraPreview;

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
                    connect(barcodes.valueAt(0).displayValue);
                }
            }
        });
    }

    private void connect(final String barcode){
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
    }
    private void compare(List<Products> productsArray,String barcode){

        Products product;

        Intent intent = new Intent();
        if(productsArray.size()>0) {
            for (int i = 0; i < productsArray.size(); i++) {
                System.out.println("Print ln do wyswietlenia czegos"+productsArray.get(i).getQrCode());
                if (productsArray.get(i).getQrCode().equals(barcode)) {
                    product = productsArray.get(i);
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
    }
}
