package com.appface.akhil.daggerapp.di;

import com.appface.akhil.daggerapp.di.main.MainFragmentBuildersModule;
import com.appface.akhil.daggerapp.di.main.MainModule;
import com.appface.akhil.daggerapp.di.main.MainViewModelsModule;
import com.appface.akhil.daggerapp.ui.auth.AuthActivity;
import com.appface.akhil.daggerapp.di.auth.AuthModule;
import com.appface.akhil.daggerapp.di.auth.AuthViewModelsModule;
import com.appface.akhil.daggerapp.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module                             //Contains all the dependencies needed
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector( modules = {AuthViewModelsModule.class, AuthModule.class})
    abstract AuthActivity contributeAuthActivity();

    @ContributesAndroidInjector( modules = {MainFragmentBuildersModule.class, MainViewModelsModule.class, MainModule.class})
    abstract MainActivity contributeMainActivity();
}
