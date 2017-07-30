package com.example.dariusz.wyszukiwarkalekow.provider;

import android.util.Log;

import com.example.dariusz.wyszukiwarkalekow.data.dto.Localizations;
import com.example.dariusz.wyszukiwarkalekow.data.dto.LoginResponseDTO;
import com.example.dariusz.wyszukiwarkalekow.data.repository.AuthorizationRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

/**
 * Created by Seweryn on 30.07.2017.
 */

public class RESTProvider {

    private RESTWebService restWebService;
    private AuthorizationRepository authorizationRepository;

    public RESTProvider(RESTWebService restWebService, AuthorizationRepository authorizationRepository) {
        this.restWebService = restWebService;
        this.authorizationRepository = authorizationRepository;
    }

    public LoginResponseDTO authorization(String username, String password, String clientId, String clientSecret)throws IOException{
        Map<String, String> headers = new HashMap<>();
        headers.put( "Content-Type" , "application/x-www-form-urlencoded" );

        Response<LoginResponseDTO> response = restWebService.authorization(
                username, password, clientId, clientSecret, "password", headers ).execute();
        assertIsResponseSuccessful(response);
        return response.body();
    }

    public List<Localizations> searchMedicines(String city, String name)throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put( "Authorization" ,"Bearer " + authorizationRepository.getAuthToken() );
        Response<List<Localizations>> response = restWebService.searchMedicines( city, name, headers ).execute();
        assertIsResponseSuccessful(response);
        return response.body();
    }

    private void assertIsResponseSuccessful(retrofit2.Response response)throws IOException{
        if (!response.isSuccessful()){
            throw new IOException(response.message() + response.code());
        }
    }

}
