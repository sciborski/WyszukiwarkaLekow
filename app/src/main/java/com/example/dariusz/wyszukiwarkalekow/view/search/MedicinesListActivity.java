package com.example.dariusz.wyszukiwarkalekow.view.search;

import android.content.Intent;
import android.os.Bundle;
import android.app.ListActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dariusz.wyszukiwarkalekow.data.dto.Localizations;
import com.example.dariusz.wyszukiwarkalekow.data.dto.MedicinesResponse;
import com.example.dariusz.wyszukiwarkalekow.view.medicine.details.MedicineActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MedicinesListActivity extends ListActivity {

    public static final String EXTRA_MESSAGE="message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        //Localizations[] localizationTab = (Localizations[])intent.getSerializableExtra(EXTRA_MESSAGE);
        //List<Localizations> localizationTab = (List<Localizations>)intent.getSerializableExtra(EXTRA_MESSAGE);
        List<MedicinesResponse> localizationTab = (List<MedicinesResponse>)intent.getSerializableExtra(EXTRA_MESSAGE);
        ListView listMedicines = getListView();
        List<String> nameOfProducts = new ArrayList<String>();
        /*for(int i = 0;i<localizationTab.length;i++){
            nameOfProducts.add((localizationTab[i].getProducts().getName()+"    "+localizationTab[i].getPrice()+" zł"));
        }*/
        for(int i = 0;i<localizationTab.size();i++){
            //System.out.println(localizationTab.get(i).getProducts().getName());
            nameOfProducts.add((localizationTab.get(i)).getName()+"    "+localizationTab.get(i).getPrice()+" zł");
        }


        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                nameOfProducts
        );
        listMedicines.setAdapter(listAdapter);
        //setContentView(R.layout.activity_medicines_list);
    }
    @Override
    public void onListItemClick(ListView listView,
                                View itemView,
                                int position,
                                long id){
        Intent gIntent = getIntent();
        List<MedicinesResponse> localizationTab = (List<MedicinesResponse>)gIntent.getSerializableExtra(EXTRA_MESSAGE);
        Intent intent = new Intent(this,MedicineActivity.class);
        intent.putExtra(MedicineActivity.EXTRA_ID,(int)id);
        intent.putExtra(MedicineActivity.EXTRA_DATA, (Serializable) localizationTab);
        startActivity(intent);
    }
}
