package com.example.ilovezappos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface Ticker {

    @GET("api/v2/ticker_hour/btcusd/")
    Call<TickerGetters> getPrice();

}
