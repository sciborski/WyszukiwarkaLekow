package com.example.dariusz.wyszukiwarkalekow.application.login;

import android.util.Log;

import com.example.dariusz.wyszukiwarkalekow.application.base.UseCase;
import com.example.dariusz.wyszukiwarkalekow.data.dto.LoginResponseDTO;
import com.example.dariusz.wyszukiwarkalekow.data.repository.AuthorizationRepository;
import com.example.dariusz.wyszukiwarkalekow.provider.RESTProvider;
import com.example.dariusz.wyszukiwarkalekow.provider.RESTWebService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Seweryn on 30.07.2017.
 */

public class LoginUseCase implements UseCase<LoginCredentials,LoginResult> {

    private RESTProvider restProvider;
    private AuthorizationRepository authorizationRepository;

    private String clientId;
    private String clientSecret;

    public LoginUseCase(RESTProvider restProvider, AuthorizationRepository authorizationRepository, String clientId, String clientSecret) {
        this.restProvider = restProvider;
        this.authorizationRepository = authorizationRepository;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public LoginResult execute(LoginCredentials loginCredentials) throws Exception {

        LoginResponseDTO responseDTO = restProvider.authorization(
                loginCredentials.getUsername(),
                loginCredentials.getPassword(),
                clientId , clientSecret
        );

        authorizationRepository.saveAuthToken(responseDTO.getAccessToken());
        authorizationRepository.saveRefreshToken(responseDTO.getRefreshToken());

        Log.e("LoginUseCase","getAccessToken" + responseDTO.getAccessToken());

        LoginResult result = new LoginResult();
        return result;
    }


}
