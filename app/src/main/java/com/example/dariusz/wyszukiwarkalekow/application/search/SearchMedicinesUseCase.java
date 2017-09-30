package com.example.dariusz.wyszukiwarkalekow.application.search;

import com.example.dariusz.wyszukiwarkalekow.application.base.UseCase;
import com.example.dariusz.wyszukiwarkalekow.data.dto.MedicinesResponse;
import com.example.dariusz.wyszukiwarkalekow.provider.RESTProvider;

import java.util.List;



public class SearchMedicinesUseCase implements UseCase<SearchMedicinesArgument,List<MedicinesResponse>> {

    private RESTProvider restProvider;

    public SearchMedicinesUseCase(RESTProvider restProvider) {
        this.restProvider = restProvider;
    }

    @Override
    public List<MedicinesResponse> execute(SearchMedicinesArgument argument) throws Exception {
        return restProvider.searchMedicines(argument.getCity(),argument.getName());
    }
}
