package com.appface.akhil.daggerapp.di.main;

import com.appface.akhil.daggerapp.ui.main.posts.PostsFragments;
import com.appface.akhil.daggerapp.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract PostsFragments contributePostsFragment();
}
