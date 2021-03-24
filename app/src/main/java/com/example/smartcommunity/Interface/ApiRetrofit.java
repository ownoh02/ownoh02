package com.example.smartcommunity.Interface;

import com.example.smartcommunity.models.UserModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiRetrofit {
    @GET("/todos/{id}")
    Call<UserModel> getUser(@Path("id") Integer getUserId);
}
