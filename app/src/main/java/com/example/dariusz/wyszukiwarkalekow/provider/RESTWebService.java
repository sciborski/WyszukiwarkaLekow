package com.example.dariusz.wyszukiwarkalekow.provider;


import com.example.dariusz.wyszukiwarkalekow.application.add.AddLocalizationArgument;
import com.example.dariusz.wyszukiwarkalekow.application.add.AddProductArgument;
import com.example.dariusz.wyszukiwarkalekow.application.register.RegisterArgument;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Localizations;
import com.example.dariusz.wyszukiwarkalekow.data.dto.MedicinesResponse;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Products;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface RESTWebService {

    @GET("api/medicines/{city}/{name}")
    Call<List<MedicinesResponse>> searchMedicines(@Path("city") String city, @Path("name") String name );

    @GET("api/search/{qr}")
    Call<List<Products>> searchQr(@Path("qr") String qr);

    @POST("api/add_product")
    Call<List<Products>> addProduct(@Body AddProductArgument argument);

    @POST("api/add_location")
    Call<Localizations> addLocation(@Body AddLocalizationArgument addLocalizationArgument);

    @POST("reg")
    Call<Users> register(@Body RegisterArgument registerArgument);
}
