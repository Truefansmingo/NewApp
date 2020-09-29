package com.flagcamp.secondhands.network;

import com.flagcamp.secondhands.model.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @GET("posts/search")
    Call<SearchResponse> search(@Query("keywords") String query, @Query("category") String category, @Query("state") String state, @Query("page") int page, @Query("pageSize") int pageSize);

    @POST("posts/post")
    Call<SearchResponse> postProduct();
}
