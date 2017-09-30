package com.example.dariusz.wyszukiwarkalekow.application.add;

import com.example.dariusz.wyszukiwarkalekow.application.base.UseCase;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Products;
import com.example.dariusz.wyszukiwarkalekow.provider.RESTProvider;

import java.util.List;


public class AddProductUseCase implements UseCase<AddProductArgument,List<Products>> {

    private RESTProvider restProvider;

    public AddProductUseCase(RESTProvider restProvider){
        this.restProvider=restProvider;
    }

    @Override
    public List<Products> execute(AddProductArgument argument) throws Exception {
        return restProvider.addProduct(argument);
    }
}
