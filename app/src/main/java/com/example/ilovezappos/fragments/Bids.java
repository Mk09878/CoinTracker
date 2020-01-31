package com.example.ilovezappos.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovezappos.adapters.BidsAdapter;
import com.example.ilovezappos.getters.GetAsksBidsData;
import com.example.ilovezappos.R;
import com.example.ilovezappos.api_requests.AsksBidsJson_Req;
import com.example.ilovezappos.getters.BidGetters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Bids extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    View v;
    float temp;
    String TAG = "Bids.java";

    ArrayList<BidGetters> bidsgetters = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.bids_fragment, container,false);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.bitstamp.net/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        AsksBidsJson_Req jsonPlaceHolderApi = retrofit.create(AsksBidsJson_Req.class);
        Call<GetAsksBidsData> call = jsonPlaceHolderApi.getAsksBids();

        call.enqueue(new Callback<GetAsksBidsData>() {
            @Override
            public void onResponse(Call<GetAsksBidsData> call, Response<GetAsksBidsData> response) {
                /*
                Takes bids, amount, calculates value.
                Animates the recycler view
                Sets Adapter
                 */

                if (!response.isSuccessful()) {

                    Log.i(TAG,"Code: " + response.code());
                    return;
                }

                for(List<String> bids : response.body().getBids())
                {
                    float tempbid = Float.parseFloat(bids.get(0));
                    float tempamt = Float.parseFloat(bids.get(1));


                    temp = tempbid * tempamt;
                    Log.i(TAG, String.valueOf(temp));
                    Log.i(TAG, String.valueOf(tempbid));
                    Log.i(TAG, String.valueOf(tempamt));
                    bidsgetters.add(new BidGetters(bids.get(0) , bids.get(1),String.valueOf(temp) ));

                }

                mRecyclerView = v.findViewById(R.id.recyclerViewBids);
                mLayoutManager = new LinearLayoutManager(getContext());
                mAdapter = new BidsAdapter(bidsgetters,getContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_animation);
                mRecyclerView.setLayoutAnimation(animationController);
                mRecyclerView.getAdapter().notifyDataSetChanged();
                mRecyclerView.scheduleLayoutAnimation();



            }

            @Override
            public void onFailure(Call<GetAsksBidsData> call, Throwable t) {

                Log.i(TAG, t.getMessage());

            }
        });

        return v;
    }
}
