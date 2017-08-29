package com.example.dariusz.wyszukiwarkalekow.application.login;

import com.example.dariusz.wyszukiwarkalekow.application.base.UseCase;
import com.example.dariusz.wyszukiwarkalekow.data.repository.AuthorizationRepository;

/**
 * Created by Seweryn on 29.08.2017.
 */

public class CheckLoginUseCase implements UseCase<Boolean,Boolean> {

    private AuthorizationRepository authorizationRepository;

    public CheckLoginUseCase(AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }

    @Override
    public Boolean execute(Boolean aBoolean) throws Exception {
        String token = authorizationRepository.getAuthToken();
        if(token != null){
            return true;
        }
        return false;
    }
}
