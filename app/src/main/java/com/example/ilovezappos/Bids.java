package com.example.ilovezappos;

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

        v = inflater.inflate(R.layout.bids, container,false);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.bitstamp.net/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        AsksBidsJson jsonPlaceHolderApi = retrofit.create(AsksBidsJson.class);
        Call<GetAsksBidsData> call = jsonPlaceHolderApi.getAsksBids();

        call.enqueue(new Callback<GetAsksBidsData>() {
            @Override
            public void onResponse(Call<GetAsksBidsData> call, Response<GetAsksBidsData> response) {
                if (!response.isSuccessful()) {

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

            }
        });

        return v;
    }
}
