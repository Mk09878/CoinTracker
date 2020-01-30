package com.example.ilovezappos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AsksBidsJson {
    @GET("api/v2/order_book/btcusd/")
    Call<GetAsksBidsData> getAsksBids();
}
