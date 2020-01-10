package com.appface.akhil.daggerapp.network.main;

import com.appface.akhil.daggerapp.models.Post;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApi {

    @GET
    Flowable<List<Post>> getPostsFromUser( @Query("userId") int id);
}
