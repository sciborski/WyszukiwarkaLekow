package com.example.dariusz.wyszukiwarkalekow.provider;

import com.example.dariusz.wyszukiwarkalekow.data.dto.LoginResponseDTO;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;



public interface OAuthWebService {

    @FormUrlEncoded
    @POST("oauth/v2/token")
    Call<LoginResponseDTO> authorization(
            @Field("username") String username,
            @Field("password") String password,
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("grant_type") String grant_type,
            @HeaderMap Map<String, String> headers);

    @FormUrlEncoded
    @POST("oauth/v2/token")
    Call<LoginResponseDTO> refreshToken(
            @Field("refresh_token") String refreshToken,
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("grant_type") String grant_type,
            @HeaderMap Map<String, String> headers);

}
