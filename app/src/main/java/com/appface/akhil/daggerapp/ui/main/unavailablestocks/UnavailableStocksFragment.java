package com.appface.akhil.daggerapp.ui.main.unavailablestocks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appface.akhil.daggerapp.R;
import com.appface.akhil.daggerapp.model.StockUnavailable;
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

public class UnavailableStocksFragment extends DaggerFragment {

    private static final String TAG = "UnavailableStocksFragment";

    private StockViewModel viewModel;
    private TextView email, username, website;
    private RecyclerView recyclerView;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    UnavailbaleStocksRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);
        initRecyclerView();
        viewModel = ViewModelProviders.of(this, providerFactory).get(StockViewModel.class);
        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        website = view.findViewById(R.id.website);
        subscribeUnavailableStockObservers();
    }

    private void subscribeUnavailableStockObservers(){
        viewModel.observeUnavailableStocks().removeObservers(getViewLifecycleOwner());
        viewModel.observeUnavailableStocks().observe(getViewLifecycleOwner(), new Observer<Resource<List<StockUnavailable>>>() {
            @Override
            public void onChanged(Resource<List<StockUnavailable>> listResource) {
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

//TODO:CURRENT USER
//    private void subscribeObservers() {
//        viewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
//        viewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(), new Observer<AuthResource<User>>() {
//            @Override
//            public void onChanged(AuthResource<User> userAuthResource) {
//                if (userAuthResource != null) {
//                    switch (userAuthResource.status) {
//                        case AUTHENTICATED: {
//                            setUserDetails(userAuthResource.data);
//                            break;
//                        }
//
//                        case ERROR: {
//                            setErrorDetails(userAuthResource.message);
//                            break;
//                        }
//                    }
//                }
//            }
//        });
//    }



    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
    }

}
