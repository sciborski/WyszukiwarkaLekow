package com.example.dariusz.wyszukiwarkalekow;

import android.app.Application;
import android.content.Context;

import com.example.dariusz.wyszukiwarkalekow.application.Logout.LogoutUseCase;
import com.example.dariusz.wyszukiwarkalekow.application.add.AddLocalizationUseCase;
import com.example.dariusz.wyszukiwarkalekow.application.add.AddProductUseCase;
import com.example.dariusz.wyszukiwarkalekow.application.base.UseCaseExecutor;
import com.example.dariusz.wyszukiwarkalekow.application.login.CheckLoginUseCase;
import com.example.dariusz.wyszukiwarkalekow.application.login.LoginUseCase;
import com.example.dariusz.wyszukiwarkalekow.application.login.RefreshTokenUseCase;
import com.example.dariusz.wyszukiwarkalekow.application.register.RegisterUseCase;
import com.example.dariusz.wyszukiwarkalekow.application.search.SearchMedicinesUseCase;
import com.example.dariusz.wyszukiwarkalekow.application.search.SearchQrUseCase;
import com.example.dariusz.wyszukiwarkalekow.data.DataBaseHandler;
import com.example.dariusz.wyszukiwarkalekow.data.model.OAuthEntity;
import com.example.dariusz.wyszukiwarkalekow.data.repository.AuthorizationRepository;
import com.example.dariusz.wyszukiwarkalekow.provider.OAuthWebService;
import com.example.dariusz.wyszukiwarkalekow.provider.RESTProvider;
import com.example.dariusz.wyszukiwarkalekow.provider.RESTWebService;
import com.example.dariusz.wyszukiwarkalekow.provider.TokenAuthenticator;
import com.example.dariusz.wyszukiwarkalekow.provider.TokenInterceptor;
import com.j256.ormlite.dao.Dao;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MedicinesStorageApplication extends Application {

    private static final String HOST =  "http://192.168.122.19";//"http://10.0.2.2";
    private static final String API_URL = "/web/app_dev.php/"; //"/REST_API/Rest_api/web/app_dev.php/";


    private static final String CLIENT_ID = "5_21vemuwjz78kww8sscccgkwg84ogs4c8gw0scg4wogsgkcg444";
    private static final String CLIENT_SECRET = "ymotev8phys8wgw4okcgosc44w4884cc80kk8w4gkk4cgwccs";


    private DataBaseHandler mDataBaseHandler;
    private RESTWebService mRESTWebService;
    private OAuthWebService mOAuthWebService;

    public static MedicinesStorageApplication get(Context context){
        return (MedicinesStorageApplication)context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDataBaseHandler = new DataBaseHandler(this);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client2 = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit2 = new Retrofit.Builder()
                .client(client2)
                .baseUrl(HOST + API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mOAuthWebService = retrofit2.create(OAuthWebService.class);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .authenticator(provideTokenAuthenticator())
                .addInterceptor(provideTokenInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
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

    public TokenAuthenticator provideTokenAuthenticator(){
        return new TokenAuthenticator( provideAuthorizationRepository() , provideRefreshTokenUseCase() );
    }

    public TokenInterceptor provideTokenInterceptor(){
        return new TokenInterceptor( provideAuthorizationRepository() );
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

    public OAuthWebService provideOAuthWebService(){
        return mOAuthWebService;
    }

    public LoginUseCase provideLoginUseCase(){
        return new LoginUseCase( provideRESTProvider() , provideAuthorizationRepository() , CLIENT_ID , CLIENT_SECRET );
    }

    public RefreshTokenUseCase provideRefreshTokenUseCase(){
        return new RefreshTokenUseCase( provideRESTProvider() , provideAuthorizationRepository() , CLIENT_ID , CLIENT_SECRET );
    }

    public CheckLoginUseCase provideCheckLoginUseCase(){
        return new CheckLoginUseCase( provideAuthorizationRepository() );
    }

    public LogoutUseCase proviceLogoutUseCase(){
        return new LogoutUseCase( provideAuthorizationRepository());
    }

    public SearchMedicinesUseCase provideSearchMedicinesUseCase(){
        return new SearchMedicinesUseCase(provideRESTProvider());
    }

    public SearchQrUseCase provideSearchQrUseCase(){
        return new SearchQrUseCase(provideRESTProvider());
    }

    public AddProductUseCase provideAddProductUseCase(){
        return new AddProductUseCase(provideRESTProvider());
    }

    public AddLocalizationUseCase provideAddLocationUseCase(){
        return new AddLocalizationUseCase(provideRESTProvider());
    }
    public RegisterUseCase provideRegisterUseCase(){
        return new RegisterUseCase(provideRESTProvider());
    }

    public RESTProvider provideRESTProvider(){
        return new RESTProvider(provideRESTWebService(), provideOAuthWebService() ,provideAuthorizationRepository());
    }

}
