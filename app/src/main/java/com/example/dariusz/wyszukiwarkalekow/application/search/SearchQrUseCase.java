package com.example.dariusz.wyszukiwarkalekow.application.search;

import com.example.dariusz.wyszukiwarkalekow.application.base.UseCase;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Products;
import com.example.dariusz.wyszukiwarkalekow.provider.RESTProvider;

import java.util.List;


public class SearchQrUseCase implements UseCase <SearchQrArgument,List<Products>> {

    private RESTProvider restProvider;

    public SearchQrUseCase(RESTProvider restProvider){
        this.restProvider=restProvider;
    }

    @Override
    public List<Products> execute(SearchQrArgument argument) throws Exception {
        return restProvider.searchQr(argument.getQr());
    }
}
