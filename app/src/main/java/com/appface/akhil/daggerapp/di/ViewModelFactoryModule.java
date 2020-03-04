package com.appface.akhil.daggerapp.di;

import com.appface.akhil.daggerapp.viewmodelproviderfactory.ViewModelProviderFactory;

import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module                         // responsible for di of ViewModelProviderFactory class
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory modelProviderFactory);
}
