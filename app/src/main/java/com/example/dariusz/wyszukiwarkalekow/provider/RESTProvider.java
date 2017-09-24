package com.example.dariusz.wyszukiwarkalekow.provider;

import android.location.Location;
import android.util.Log;

import com.example.dariusz.wyszukiwarkalekow.application.add.AddLocalizationArgument;
import com.example.dariusz.wyszukiwarkalekow.application.register.RegisterArgument;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Localizations;
import com.example.dariusz.wyszukiwarkalekow.data.dto.LoginResponseDTO;
import com.example.dariusz.wyszukiwarkalekow.data.dto.MedicinesResponse;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Products;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Users;
import com.example.dariusz.wyszukiwarkalekow.data.repository.AuthorizationRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;



public class RESTProvider {

    private RESTWebService restWebService;
    private OAuthWebService oAuthWebService;

    private AuthorizationRepository authorizationRepository;

    public RESTProvider(RESTWebService restWebService,OAuthWebService oAuthWebService, AuthorizationRepository authorizationRepository) {
        this.restWebService = restWebService;
        this.authorizationRepository = authorizationRepository;
        this.oAuthWebService = oAuthWebService;
    }

    public LoginResponseDTO authorization(String username, String password, String clientId, String clientSecret)throws IOException{
        Map<String, String> headers = new HashMap<>();
        headers.put( "Content-Type" , "application/x-www-form-urlencoded" );
        Response<LoginResponseDTO> response = oAuthWebService.authorization(
                username, password, clientId, clientSecret, "password", headers ).execute();
        assertIsResponseSuccessful(response);
        return response.body();
    }

    public List<MedicinesResponse> searchMedicines(String city, String name)throws IOException {
        Response<List<MedicinesResponse>> response = restWebService.searchMedicines( city, name ).execute();
        assertIsResponseSuccessful(response);
        return response.body();
    }

    public List<Products> searchQr(String qr) throws IOException{
        Response<List<Products>> response = restWebService.searchQr(qr).execute();
        assertIsResponseSuccessful(response);
        return response.body();
    }

    public Products addProduct(Products products) throws IOException{
        Response<Products> response = restWebService.addProduct(products).execute();
        assertIsResponseSuccessful(response);
        return response.body();
    }

    public Localizations addLocalization(AddLocalizationArgument addLocalizationArgument) throws  IOException{
        Response<Localizations> response = restWebService.addLocation(addLocalizationArgument).execute();
        assertIsResponseSuccessful(response);
        return response.body();
    }

    public Users register(RegisterArgument registerArgument) throws  IOException{
        Response<Users> response = restWebService.register(registerArgument).execute();
        assertIsResponseSuccessful(response);
        return response.body();
    }

    private void assertIsResponseSuccessful(retrofit2.Response response)throws IOException{
        if (!response.isSuccessful()){
            if(response.code() == 403){
                //throw new UnauthorizedException()
            }
            throw new IOException(response.message() + response.code());
        }
    }

    public LoginResponseDTO refreshToken(String refreshToken, String clientId, String clientSecret)throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put( "Content-Type" , "application/x-www-form-urlencoded" );
        Response<LoginResponseDTO> response = oAuthWebService.refreshToken( refreshToken, clientId, clientSecret, "refresh_token", headers ).execute();
        assertIsResponseSuccessful(response);
        return response.body();
    }
}
