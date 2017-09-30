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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dariusz.wyszukiwarkalekow.MedicinesStorageApplication;
import com.example.dariusz.wyszukiwarkalekow.R;
import com.example.dariusz.wyszukiwarkalekow.application.add.AddLocalizationArgument;
import com.example.dariusz.wyszukiwarkalekow.application.add.AddLocalizationUseCase;
import com.example.dariusz.wyszukiwarkalekow.application.add.AddProductArgument;
import com.example.dariusz.wyszukiwarkalekow.application.add.AddProductUseCase;
import com.example.dariusz.wyszukiwarkalekow.application.base.UseCaseExecutor;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Localizations;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Products;
import com.example.dariusz.wyszukiwarkalekow.view.home.MenuActivity;
import com.example.dariusz.wyszukiwarkalekow.view.scan.ScanActivity;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.id.list;


public class AddActivity extends AppCompatActivity {

    private AddProductUseCase addProductUseCase;
    private AddLocalizationUseCase addLocalizationUseCase;
    private UseCaseExecutor useCaseExecutor;

    @BindView(R.id.nameAdd_edittext)
    EditText name;

    @BindView(R.id.qrCodeAdd_edittext)
    EditText qr;

    @BindView(R.id.townAdd_edittext)
    EditText town;

    @BindView(R.id.streetAdd_edittext)
    EditText street;

    @BindView(R.id.priceAdd_edittext)
    EditText price;

    @BindView(R.id.progressLayoutAdd)
    RelativeLayout progressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.dariusz.wyszukiwarkalekow.R.layout.activity_add);

        addProductUseCase = MedicinesStorageApplication.get(getBaseContext()).provideAddProductUseCase();
        addLocalizationUseCase = MedicinesStorageApplication.get(getBaseContext()).provideAddLocationUseCase();
        useCaseExecutor = MedicinesStorageApplication.get(getBaseContext()).provideUseCaseExecutor();
        ButterKnife.bind(this);
        hideProgress();
    }
    @OnClick(R.id.addAdd_button)
    public void onAdd(View view) {
        showProgress();
        //pobranie informacji z pól
        final String nameText = name.getText().toString();

        final String qrCodeText = qr.getText().toString();

        final String townText = town.getText().toString();

        final String streetText = street.getText().toString();

        final String priceText = price.getText().toString();

        AddProductArgument productArgument = new AddProductArgument(nameText,qrCodeText);


        useCaseExecutor.executeUseCase(addProductUseCase, productArgument, new UseCaseExecutor.Listener<List<Products>>() {
            @Override
            public void onResult(List<Products> products2) {
                System.out.println("id produktu: "+products2.get(0).getIdProduct());

                AddLocalizationArgument localizationArgument= new AddLocalizationArgument(townText,streetText,priceText,"3",products2.get(0).getIdProduct()+"");
                connect(localizationArgument);
            }

            @Override
            public void onError(Exception ex) {
                hideProgress();
                ex.printStackTrace();
            }
        });

    }

    private void connect(AddLocalizationArgument argument) {

        useCaseExecutor.executeUseCase(addLocalizationUseCase,argument, new UseCaseExecutor.Listener<Localizations>(){
            @Override
            public void onResult(Localizations localizations){
                Toast toast=Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(getBaseContext(),MenuActivity.class);
                hideProgress();
                startActivity(intent);
            }
            @Override
            public void onError(Exception ex) {
                hideProgress();
                ex.printStackTrace();
            }
        });

    }
    @OnClick(R.id.gpsAdd_button)
    public void onGps(View view) {
        showProgress();
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

                    hideProgress();
                    town.setText(address.get(0).getLocality());
                    street.setText(address.get(0).getAddressLine(0));

                    System.out.println("test");

                }catch(Exception e){
                    hideProgress();
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
    @OnClick(R.id.scanAdd_button)
    public void onScan(View view){
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
                    name.setText(products.getName());
                    qr.setText(products.getQrCode());
                    break;
                case 2:
                    String qrcode = data.getStringExtra("barcode");
                    qr.setText(qrcode);
                    break;
            }
        }
    }
    private void showProgress(){
        progressLayout.setVisibility(View.VISIBLE);
    }

    private void hideProgress(){
        progressLayout.setVisibility(View.GONE);
    }
}
