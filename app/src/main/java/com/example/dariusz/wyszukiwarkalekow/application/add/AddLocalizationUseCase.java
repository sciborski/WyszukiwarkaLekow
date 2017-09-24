package com.example.dariusz.wyszukiwarkalekow.application.add;

import android.location.Location;

import com.example.dariusz.wyszukiwarkalekow.application.base.UseCase;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Localizations;
import com.example.dariusz.wyszukiwarkalekow.provider.RESTProvider;


public class AddLocalizationUseCase implements UseCase<AddLocalizationArgument,Localizations> {

    private RESTProvider restProvider;

    public AddLocalizationUseCase(RESTProvider restProvider){
        this.restProvider = restProvider;
    }

    @Override
    public Localizations execute(AddLocalizationArgument argument) throws Exception {
        return restProvider.addLocalization(argument);
    }
}
