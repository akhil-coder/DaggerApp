package com.appface.akhil.daggerapp.ui.main.availablestocks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appface.akhil.daggerapp.R;
import com.appface.akhil.daggerapp.model.Category;
import com.appface.akhil.daggerapp.ui.main.Resource;
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

public class AvailableStocksFragments extends DaggerFragment {

    private static final String TAG = "AvailableStocks";

    private StockViewModel viewModel;
    private RecyclerView recyclerView;

    @Inject
    AvailableStocksRecyclerAdapter adapter;

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
        subscribeObservers();
    }

    private void subscribeObservers(){
        viewModel.observeAllCategories().removeObservers(getViewLifecycleOwner());
        viewModel.observeAllCategories().observe(getViewLifecycleOwner(), new Observer<Resource<List<Category>>>() {
            @Override
            public void onChanged(Resource<List<Category>> listResource) {
                if(listResource != null) {
                    Log.d(TAG, "onChanged: " + listResource.data);
                    switch (listResource.status){
                        case LOADING: {
                            break;
                        }
                        case SUCCESS:{
                            Log.d(TAG, "onChanged: got posts");
                            adapter.setPosts(listResource.data);
                            break;
                        }
                        case ERROR:{
                            Log.d(TAG, "onChanged: " + listResource.message );
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
