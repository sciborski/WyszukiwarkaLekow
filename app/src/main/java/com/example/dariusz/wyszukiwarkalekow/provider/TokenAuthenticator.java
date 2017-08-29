package com.example.dariusz.wyszukiwarkalekow.provider;

import com.example.dariusz.wyszukiwarkalekow.application.login.RefreshTokenUseCase;
import com.example.dariusz.wyszukiwarkalekow.data.repository.AuthorizationRepository;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by Seweryn on 2016-08-17.
 */
public class TokenAuthenticator implements Authenticator {

    private AuthorizationRepository mAuthorizationRepository;
    private RefreshTokenUseCase mRefreshTokenUseCase;

    public TokenAuthenticator( AuthorizationRepository authorizationRepository, RefreshTokenUseCase refreshTokenUseCase ) {
        this.mAuthorizationRepository = authorizationRepository;
        this.mRefreshTokenUseCase = refreshTokenUseCase;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        if (responseCount(response) >= 3) {
            return null; // If we've failed 3 times, give up. - in real life, never give up!!
        }

        if (response.code() == 401) {
            mAuthorizationRepository.deleteAuthToken();
        }

        String newAccessToken = null;
        try {
            newAccessToken = mRefreshTokenUseCase.execute(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(newAccessToken == null){
            return null;
        }

        return response.request().newBuilder()
                .header("Authorization", String.format("Bearer %s", newAccessToken))
                .build();
    }

    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

}
