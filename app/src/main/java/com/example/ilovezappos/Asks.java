package com.example.ilovezappos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class Asks extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList value = new ArrayList<>();
    ArrayList amt = new ArrayList<>();
    ArrayList ask = new ArrayList<>();
    ArrayList<AskGetters> askgetters = new ArrayList<>();
    String TAG = "Asks.java";
    View v;
    float  temp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.asks, container,false);


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

                for(List<String> asks : response.body().getAsks())
                {
                    float tempask = Float.parseFloat(asks.get(0));
                    float tempamt = Float.parseFloat(asks.get(1));

                    //ask.add(asks.get(0));
                    //amt.add(asks.get(1));
                    temp = tempask * tempamt;
                    //value.add(String.valueOf(temp));
                    askgetters.add(new AskGetters(asks.get(0) , asks.get(1),String.valueOf(temp) ));
                }
                Log.i(TAG, "In response");
                //Log.i(TAG, String.valueOf(value.get(0)));

                for(int i = 0; i< ask.size(); i++)
                {
                    //askgetters.add(new AskGetters((float)value.get(i) , (float)amt.get(i), (float)ask.get(i)));
                }

                mRecyclerView = v.findViewById(R.id.recyclerView);
                //mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getContext());
                mAdapter = new AsksAdapter(askgetters);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);



            }

            @Override
            public void onFailure(Call<GetAsksBidsData> call, Throwable t) {



            }
        });


        return v;
    }
}
