package com.appface.akhil.daggerapp.di.main;

import com.appface.akhil.daggerapp.ui.main.availablestocks.AvailableStocksFragments;
import com.appface.akhil.daggerapp.ui.main.availablestocks.subcategory.SubCategoryFragment;
import com.appface.akhil.daggerapp.ui.main.unavailablestocks.UnavailableStocksFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract UnavailableStocksFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract AvailableStocksFragments contributePostsFragment();

    @ContributesAndroidInjector
    abstract SubCategoryFragment contributeSubCategoryFragment();
}
