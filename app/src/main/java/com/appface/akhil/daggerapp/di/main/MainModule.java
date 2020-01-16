package com.appface.akhil.daggerapp.di.main;

import com.appface.akhil.daggerapp.network.main.MainApi;
import com.appface.akhil.daggerapp.ui.main.posts.PostsRecyclerAdapter;
import com.appface.akhil.daggerapp.ui.main.profile.UnavailbaleStocksRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @Provides
    static PostsRecyclerAdapter provideAdapter(){
        return new PostsRecyclerAdapter();
    }

    @Provides
    static UnavailbaleStocksRecyclerAdapter provideUnavailableAdapter(){
        return new UnavailbaleStocksRecyclerAdapter();
    }

    @Provides
    static MainApi provideMainApi(Retrofit retrofit){
        return retrofit.create(MainApi.class);
    }
}
