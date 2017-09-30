package com.example.dariusz.wyszukiwarkalekow.view.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dariusz.wyszukiwarkalekow.MedicinesStorageApplication;
import com.example.dariusz.wyszukiwarkalekow.application.base.UseCaseExecutor;
import com.example.dariusz.wyszukiwarkalekow.application.search.SearchMedicinesArgument;
import com.example.dariusz.wyszukiwarkalekow.application.search.SearchMedicinesUseCase;
import com.example.dariusz.wyszukiwarkalekow.data.dto.MedicinesResponse;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Products;
import com.example.dariusz.wyszukiwarkalekow.R;
import com.example.dariusz.wyszukiwarkalekow.view.scan.ScanActivity;


import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LookingForActivity extends AppCompatActivity {

    //private static final int SECOND_ACTIVITY_RESULT_CODE = 0;

    private SearchMedicinesUseCase searchMedicinesUseCase;
    private UseCaseExecutor useCaseExecutor;

    @BindView(R.id.nameLookingFor_edittext)
    EditText nameView;

    @BindView(R.id.townLookingFor_edittext)
    EditText townView;

    @BindView(R.id.progressLayoutLookingFor_button)
    RelativeLayout progressLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looking_for);
        searchMedicinesUseCase = MedicinesStorageApplication.get(getBaseContext()).provideSearchMedicinesUseCase();
        useCaseExecutor = MedicinesStorageApplication.get(getBaseContext()).provideUseCaseExecutor();
        ButterKnife.bind(this);
        hideProgress();
    }

    @OnClick(R.id.search_dataLookingFor_button)
    public void onSearchData(View view){
        showProgress();
        String nameText = nameView.getText().toString();
        String townText = townView.getText().toString();
        if(nameText.isEmpty() || townText.isEmpty()){
            Toast toast=Toast.makeText(this, "Nie uzupełniłeś pól", Toast.LENGTH_SHORT);
            toast.show();
        }else {

            SearchMedicinesArgument argument = new SearchMedicinesArgument(
                    townText,
                    nameText
            );
            useCaseExecutor.executeUseCase(searchMedicinesUseCase, argument, new UseCaseExecutor.Listener<List<MedicinesResponse>>() {
                @Override
                public void onResult(List<MedicinesResponse> localizations) {
                    hideProgress();
                    System.out.println(localizations);
                    Intent intent = new Intent(getBaseContext(), MedicinesListActivity.class);
                    intent.putExtra(MedicinesListActivity.EXTRA_MESSAGE, (Serializable) localizations);
                    startActivity(intent);
                }

                @Override
                public void onError(Exception ex) {
                    hideProgress();
                    ex.printStackTrace();
                }
            });
        }
    }
    @OnClick(R.id.scan_dataLookingFor_button)
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
                    nameView.setText(products.getName());
                    break;
                case 2:
                    Toast toast=Toast.makeText(this, "nieznany produkt", Toast.LENGTH_SHORT);
                    toast.show();
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
