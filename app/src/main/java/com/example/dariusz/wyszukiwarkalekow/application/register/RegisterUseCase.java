package com.example.dariusz.wyszukiwarkalekow.application.register;

import com.example.dariusz.wyszukiwarkalekow.application.base.UseCase;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Users;
import com.example.dariusz.wyszukiwarkalekow.provider.RESTProvider;


public class RegisterUseCase implements UseCase<RegisterArgument, Users> {

    private RESTProvider restProvider;

    public RegisterUseCase(RESTProvider restProvider){
        this.restProvider = restProvider;
    }

    @Override
    public Users execute(RegisterArgument registerArgument) throws Exception {
        return restProvider.register(registerArgument);
    }
}
