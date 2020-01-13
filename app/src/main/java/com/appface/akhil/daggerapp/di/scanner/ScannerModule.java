package com.appface.akhil.daggerapp.di.scanner;

import android.app.Application;

import com.appface.akhil.daggerapp.model.StockRepository;
import com.appface.akhil.daggerapp.network.main.MainApi;
import com.appface.akhil.daggerapp.util.Constants;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ScannerModule {

    @Provides
    StockRepository provideStockRepository(Application application){
        return new StockRepository(application);
    }

    @Provides
    static MainApi provideMainApi(){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_STOCK)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(MainApi.class);
    }
}
