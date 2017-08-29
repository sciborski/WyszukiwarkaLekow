package com.example.dariusz.wyszukiwarkalekow.provider;

import com.example.dariusz.wyszukiwarkalekow.data.dto.Localizations;
import com.example.dariusz.wyszukiwarkalekow.data.dto.LoginResponseDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Seweryn on 30.07.2017.
 */

public interface RESTWebService {

    @GET("api/medicines/{city}/{name}")
    Call<List<Localizations>> searchMedicines( @Path("city") String city, @Path("name") String name );
}
