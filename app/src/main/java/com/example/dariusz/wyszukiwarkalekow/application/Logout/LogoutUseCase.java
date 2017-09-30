package com.example.dariusz.wyszukiwarkalekow.application.Logout;

import com.example.dariusz.wyszukiwarkalekow.application.base.UseCase;
import com.example.dariusz.wyszukiwarkalekow.data.repository.AuthorizationRepository;


public class LogoutUseCase implements UseCase<Boolean,Boolean> {

    private AuthorizationRepository authorizationRepository;

    public LogoutUseCase(AuthorizationRepository authorizationRepository){
        this.authorizationRepository = authorizationRepository;
    }

    @Override
    public Boolean execute(Boolean aBoolean) throws Exception {
        authorizationRepository.deleteAuthToken();
        String token = authorizationRepository.getAuthToken();
        if(token != null)
        {
            return false;
        }
        return true;
    }
}
