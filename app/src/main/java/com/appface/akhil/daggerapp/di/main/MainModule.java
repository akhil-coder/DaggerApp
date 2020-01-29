package com.appface.akhil.daggerapp.di.main;

import com.appface.akhil.daggerapp.network.main.MainApi;
import com.appface.akhil.daggerapp.ui.main.availablestocks.AvailableStocksRecyclerAdapter;
import com.appface.akhil.daggerapp.ui.main.availablestocks.subcategory.SubCategoryRecyclerAdapter;
import com.appface.akhil.daggerapp.ui.main.unavailablestocks.UnavailbaleStocksRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @Provides
    static AvailableStocksRecyclerAdapter provideAdapter(){
        return new AvailableStocksRecyclerAdapter();
    }

    @Provides
    static UnavailbaleStocksRecyclerAdapter provideUnavailableAdapter(){
        return new UnavailbaleStocksRecyclerAdapter();
    }

    @Provides
    static SubCategoryRecyclerAdapter provideSubCategoryAdapter(){
        return new SubCategoryRecyclerAdapter();
    }

    @Provides
    static MainApi provideMainApi(Retrofit retrofit){
        return retrofit.create(MainApi.class);
    }
}
