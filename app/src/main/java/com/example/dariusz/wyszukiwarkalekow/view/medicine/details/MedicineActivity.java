package com.example.dariusz.wyszukiwarkalekow.view.medicine.details;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dariusz.wyszukiwarkalekow.data.dto.Localizations;
import com.example.dariusz.wyszukiwarkalekow.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class MedicineActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String EXTRA_DATA="data";
    public static final String EXTRA_ID="id";
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        Intent intent = getIntent();
        Localizations[] localizationTab = (Localizations[]) intent.getSerializableExtra(EXTRA_DATA);
        int prodId = (int)intent.getExtras().get(EXTRA_ID);
        //wyświetlenie danych o leku
        TextView name = (TextView)findViewById(R.id.medicineName);
        name.setText("name: "+localizationTab[prodId].getProducts().getName());

        TextView price = (TextView)findViewById(R.id.medicinePrice);
        price.setText("price: "+localizationTab[prodId].getPrice()+" zł");

        TextView town = (TextView)findViewById(R.id.medicineTown);
        town.setText("town: "+localizationTab[prodId].getTown());

        TextView street = (TextView)findViewById(R.id.medicineStreet);
        street.setText("street: "+localizationTab[prodId].getStreet());

        //mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap){
        //pobranie danych lokalizacyjnych
        Intent intent = getIntent();
        Localizations[] localizationTab = (Localizations[]) intent.getSerializableExtra(EXTRA_DATA);
        int prodId = (int)intent.getExtras().get(EXTRA_ID);
        String locationName = localizationTab[prodId].getStreet()+" "+localizationTab[prodId].getTown();
        //przekonwertowanie na LatLng
        LatLng city;
        try {
            List<Address> result = new Geocoder(this).getFromLocationName(locationName, 10);
            city = new LatLng(result.get(0).getLatitude(),result.get(0).getLongitude());
            //podanie danych do mapy
            mMap = googleMap;
            mMap.addMarker(new MarkerOptions().position(city));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(city,14));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}