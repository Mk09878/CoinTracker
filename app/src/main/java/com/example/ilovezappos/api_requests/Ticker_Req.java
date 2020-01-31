package com.example.ilovezappos.api_requests;

import com.example.ilovezappos.getters.TickerGetters;

import retrofit2.Call;
import retrofit2.http.GET;


public interface Ticker_Req {

    @GET("api/v2/ticker_hour/btcusd/")
    Call<TickerGetters> getPrice();

}
