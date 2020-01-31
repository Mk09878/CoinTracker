package com.example.ilovezappos.api_requests;

import com.example.ilovezappos.getters.GetAsksBidsData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AsksBidsJson_Req {
    @GET("api/v2/order_book/btcusd/")
    Call<GetAsksBidsData> getAsksBids();
}
