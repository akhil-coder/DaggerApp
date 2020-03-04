package com.appface.akhil.daggerapp.ui.main.availablestocks.subcategory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appface.akhil.daggerapp.R;
import com.appface.akhil.daggerapp.model.Stock;
import com.appface.akhil.daggerapp.ui.main.Resource;
import com.appface.akhil.daggerapp.ui.main.availablestocks.StockViewModel;
import com.appface.akhil.daggerapp.util.VerticalSpacingItemDecoration;
import com.appface.akhil.daggerapp.viewmodelproviderfactory.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerFragment;

public class SubCategoryFragment extends DaggerFragment {

    private static final String TAG = "AvailableStocksFragments";

    private StockViewModel viewModel;
    private RecyclerView recyclerView;

    @Inject
    SubCategoryRecyclerAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);
        viewModel = ViewModelProviders.of(this, providerFactory).get(StockViewModel.class);
        initRecyclerView();
        subscribeObservers(getArguments().getString("brandname"));
    }

    private void subscribeObservers(String brandName){
        viewModel.observeSelectedCategoryEntires(brandName).removeObservers(getViewLifecycleOwner());
        viewModel.observeSelectedCategoryEntires(brandName).observe(getViewLifecycleOwner(), new Observer<Resource<List<Stock>>>() {
            @Override
            public void onChanged(Resource<List<Stock>> listResource) {
                if(listResource != null) {
                    switch (listResource.status){
                        case LOADING: {
                            break;
                        }
                        case SUCCESS:{
                            adapter.setPosts(listResource.data);
                            break;
                        }
                        case ERROR:{
                            break;
                        }
                    }
                }
            }
        });
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
    }
}
