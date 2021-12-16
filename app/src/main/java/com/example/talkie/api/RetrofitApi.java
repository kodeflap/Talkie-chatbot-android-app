package com.example.talkie.api;

import com.example.talkie.model.MessageModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitApi {

    @GET
    Call<MessageModel> getMessage(@Url String url);
}
