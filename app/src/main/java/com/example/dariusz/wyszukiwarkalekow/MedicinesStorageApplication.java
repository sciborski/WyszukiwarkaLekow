package com.example.dariusz.wyszukiwarkalekow;

import android.app.Application;
import android.content.Context;

import com.example.dariusz.wyszukiwarkalekow.application.base.UseCaseExecutor;
import com.example.dariusz.wyszukiwarkalekow.application.login.LoginUseCase;
import com.example.dariusz.wyszukiwarkalekow.application.search.SearchMedicinesUseCase;
import com.example.dariusz.wyszukiwarkalekow.data.DataBaseHandler;
import com.example.dariusz.wyszukiwarkalekow.data.model.OAuthEntity;
import com.example.dariusz.wyszukiwarkalekow.data.repository.AuthorizationRepository;
import com.example.dariusz.wyszukiwarkalekow.provider.RESTProvider;
import com.example.dariusz.wyszukiwarkalekow.provider.RESTWebService;
import com.j256.ormlite.dao.Dao;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Seweryn on 30.07.2017.
 */

public class MedicinesStorageApplication extends Application {

    private static final String HOST =  "http://10.0.2.2";//"http://192.168.122.19";
    private static final String API_URL = "/REST_API/Rest_api/web/app_dev.php/";


    private static final String CLIENT_ID = "4_p29gmzmrmdwsw4kcsgww0kcokowg48c8ssco0w4ckgss4s08o";
    private static final String CLIENT_SECRET = "5vt2x7cped4wgow4w4wwk0wcs8c0880o8csww4okgk8k00cwso";


    private DataBaseHandler mDataBaseHandler;
    private RESTWebService mRESTWebService;

    public static MedicinesStorageApplication get(Context context){
        return (MedicinesStorageApplication)context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDataBaseHandler = new DataBaseHandler(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST + API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mRESTWebService = retrofit.create(RESTWebService.class);
    }

    public DataBaseHandler provideDataBaseHandler(){
        return mDataBaseHandler;
    }

    public Dao<OAuthEntity, String> provideOAuthEntityDAO(){
        return provideDataBaseHandler().provideDao(OAuthEntity.class);
    }

    public AuthorizationRepository provideAuthorizationRepository(){
        return new AuthorizationRepository(provideOAuthEntityDAO());
    }

    public UseCaseExecutor provideUseCaseExecutor(){
        return new UseCaseExecutor(this);
    }

    public RESTWebService provideRESTWebService(){
        return mRESTWebService;
    }

    public LoginUseCase provideLoginUseCase(){
        return new LoginUseCase( provideRESTProvider() , provideAuthorizationRepository() , CLIENT_ID , CLIENT_SECRET );
    }

    public SearchMedicinesUseCase provideSearchMedicinesUseCase(){
        return new SearchMedicinesUseCase(provideRESTProvider());
    }

    public RESTProvider provideRESTProvider(){
        return new RESTProvider(provideRESTWebService(),provideAuthorizationRepository());
    }

}
