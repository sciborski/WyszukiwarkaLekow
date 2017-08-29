package com.example.dariusz.wyszukiwarkalekow.provider;

import android.util.Log;

import com.example.dariusz.wyszukiwarkalekow.data.repository.AuthorizationRepository;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    private AuthorizationRepository mAuthorizationRepository;

    public TokenInterceptor(AuthorizationRepository authorizationRepository ) {
        this.mAuthorizationRepository = authorizationRepository;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        Log.i("Authorization" , String.format("Bearer %s", mAuthorizationRepository.getAuthToken() ) );

        Request.Builder builder = request.newBuilder();
        builder.header("Authorization", String.format("Bearer %s", mAuthorizationRepository.getAuthToken() ));
        request = builder.build();

        Response response = chain.proceed(request);

        if (response.code() == 401) {
            //invalidate auth token
        }

        return response;
    }

}
