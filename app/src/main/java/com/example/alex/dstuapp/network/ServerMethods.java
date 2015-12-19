package com.example.alex.dstuapp.network;


import com.example.alex.dstuapp.network.responses.AllJournalsResponse;
import com.example.alex.dstuapp.network.responses.AllMyInfoResponse;
import com.example.alex.dstuapp.network.responses.LoginResponse;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/*
серверные методы
(такой синтаксис требует библиотека Retrofit 2.0)
*/

public interface ServerMethods {

    @FormUrlEncoded
    @POST("base/login/true/true")
    Call<LoginResponse> login(@Field("username") String login, @Field("password") String password);

    @GET("smartphone_api/getAllMyInfo")
    Call<AllMyInfoResponse> getMyInfo();

    @GET("smartphone_api/getJournalList")
    Call<AllJournalsResponse> getJournals();
}
