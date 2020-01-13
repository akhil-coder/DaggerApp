package com.appface.akhil.daggerapp.di.scanner;

import com.appface.akhil.daggerapp.di.ViewModelKey;
import com.appface.akhil.daggerapp.ui.main.posts.StockViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ScannerViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(StockViewModel.class)
    public abstract ViewModel bindScannerViewModel(StockViewModel viewModel);
}
