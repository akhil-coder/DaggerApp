package com.appface.akhil.daggerapp.network.main;

import com.appface.akhil.daggerapp.model.Stock;
import com.appface.akhil.daggerapp.model.StockResponse;
import com.appface.akhil.daggerapp.models.Post;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApi {

    @GET("posts")
    Flowable<List<Post>> getPostsFromUser( @Query("userId") int id);

    @GET("retrieveproduct")
    Single<StockResponse> retrieveproduct(@Query("barcode") long id);
}
