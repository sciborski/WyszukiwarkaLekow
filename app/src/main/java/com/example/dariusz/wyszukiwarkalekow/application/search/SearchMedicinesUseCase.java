package com.example.dariusz.wyszukiwarkalekow.application.search;

import com.example.dariusz.wyszukiwarkalekow.application.base.UseCase;
import com.example.dariusz.wyszukiwarkalekow.data.dto.Localizations;
import com.example.dariusz.wyszukiwarkalekow.provider.RESTProvider;

import java.util.List;

/**
 * Created by Seweryn on 30.07.2017.
 */

public class SearchMedicinesUseCase implements UseCase<SearchMedicinesArgument,List<Localizations>> {

    private RESTProvider restProvider;

    public SearchMedicinesUseCase(RESTProvider restProvider) {
        this.restProvider = restProvider;
    }

    @Override
    public List<Localizations> execute(SearchMedicinesArgument argument) throws Exception {
        return restProvider.searchMedicines(argument.getCity(),argument.getName());
    }
}
