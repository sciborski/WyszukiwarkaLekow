package com.example.dariusz.wyszukiwarkalekow.provider;

import android.location.Location;

import com.example.dariusz.wyszukiwarkalekow.application.add.AddLocalizationArgument;
import com.example.dariusz.wyszukiwarkalekow.application.register.RegisterArgument;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Localizations;
import com.example.dariusz.wyszukiwarkalekow.data.dto.LoginResponseDTO;
import com.example.dariusz.wyszukiwarkalekow.data.dto.MedicinesResponse;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Products;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


public interface RESTWebService {

    //@GET("api/medicines/{city}/{name}")
    //Call<List<Localizations>> searchMedicines( @Path("city") String city, @Path("name") String name );
    @GET("api/medicines/{city}/{name}")
    Call<List<MedicinesResponse>> searchMedicines(@Path("city") String city, @Path("name") String name );

    @GET("api/search/{qr}")
    Call<List<Products>> searchQr(@Path("qr") String qr);

    @POST("api/add_product")
    Call<Products> addProduct(@Body Products products);

    @POST("api/add_location")
    Call<Localizations> addLocation(@Body AddLocalizationArgument addLocalizationArgument);

    @POST("reg")
    Call<Users> register(@Body RegisterArgument registerArgument);
}
