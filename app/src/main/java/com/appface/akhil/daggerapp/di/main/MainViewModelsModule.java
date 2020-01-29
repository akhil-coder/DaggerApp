package com.appface.akhil.daggerapp.di.main;

import com.appface.akhil.daggerapp.di.ViewModelKey;
import com.appface.akhil.daggerapp.ui.main.availablestocks.PostsViewModel;
import com.appface.akhil.daggerapp.ui.main.unavailablestocks.ProfileViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel.class)
    public abstract ViewModel bindPostsViewModel(PostsViewModel viewModel);
}
