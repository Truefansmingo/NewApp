package com.flagcamp.secondhands.network;

import com.flagcamp.secondhands.model.User;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {

    @POST("User/")
    Call<User> checkLogin(@Header("Authorization") String authToken);
}
