package com.example.dariusz.wyszukiwarkalekow.application.login;

import com.example.dariusz.wyszukiwarkalekow.application.base.UseCase;
import com.example.dariusz.wyszukiwarkalekow.data.dto.LoginResponseDTO;
import com.example.dariusz.wyszukiwarkalekow.data.repository.AuthorizationRepository;
import com.example.dariusz.wyszukiwarkalekow.provider.RESTProvider;



public class RefreshTokenUseCase implements UseCase<Boolean,String> {

    private RESTProvider                restProvider;
    private AuthorizationRepository     authorizationRepository;

    private String clientId;
    private String clientSecret;

    public RefreshTokenUseCase(RESTProvider restProvider, AuthorizationRepository authorizationRepository, String clientId, String clientSecret) {
        this.restProvider = restProvider;
        this.authorizationRepository = authorizationRepository;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public String execute(Boolean aBoolean) throws Exception {
        String refreshToken = authorizationRepository.getRefreshToken();
        LoginResponseDTO responseDTO = restProvider.refreshToken(refreshToken, clientId, clientSecret);

        authorizationRepository.saveAuthToken(responseDTO.getAccessToken());
        authorizationRepository.saveRefreshToken(responseDTO.getRefreshToken());

        return responseDTO.getAccessToken();
    }
}
