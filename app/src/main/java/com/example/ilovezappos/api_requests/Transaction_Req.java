package com.example.ilovezappos.api_requests;

import com.example.ilovezappos.getters.GetData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface Transaction_Req {

    @GET("api/v2/transactions/btcusd/")
    Call<List<GetData>> getPosts();

}
