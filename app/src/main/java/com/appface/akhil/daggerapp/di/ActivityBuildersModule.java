package com.appface.akhil.daggerapp.di;

import com.appface.akhil.daggerapp.di.main.MainFragmentBuildersModule;
import com.appface.akhil.daggerapp.di.main.MainModule;
import com.appface.akhil.daggerapp.di.main.MainViewModelsModule;
import com.appface.akhil.daggerapp.di.scanner.ScannerModule;
import com.appface.akhil.daggerapp.di.scanner.ScannerViewModelsModule;
import com.appface.akhil.daggerapp.ui.auth.AuthActivity;
import com.appface.akhil.daggerapp.di.auth.AuthModule;
import com.appface.akhil.daggerapp.di.auth.AuthViewModelsModule;
import com.appface.akhil.daggerapp.ui.main.MainActivity;
import com.appface.akhil.daggerapp.ui.main.scanner.ScannerActivity;
import com.appface.akhil.daggerapp.ui.splashscreen.SplashScreenActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module                             //Contains all the dependencies needed
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector( modules = {AuthViewModelsModule.class, AuthModule.class})
    abstract AuthActivity contributeAuthActivity();

    @ContributesAndroidInjector( modules = {MainFragmentBuildersModule.class, MainViewModelsModule.class, MainModule.class, ScannerViewModelsModule.class})
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector( modules = {ScannerModule.class, ScannerViewModelsModule.class})
    abstract ScannerActivity contributeScannerActivity();

    @ContributesAndroidInjector
    abstract SplashScreenActivity contributeSplashScreenActivity();
}
