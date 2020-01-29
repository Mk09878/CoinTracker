package com.example.ilovezappos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface JsonConn {

    @GET("api/v2/transactions/btcusd/")
    Call<List<GetData>> getPosts();
}
