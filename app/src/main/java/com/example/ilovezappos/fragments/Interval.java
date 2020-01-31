package com.example.ilovezappos.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.ilovezappos.NotificationWorker;
import com.example.ilovezappos.R;
import com.example.ilovezappos.api_requests.Ticker_Req;
import com.example.ilovezappos.getters.TickerGetters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Interval extends Fragment {


    Button clear;
    Button submit;
    Button cancel;
    EditText interval;
    TextView tracking;
    TextView currentPrice;
    String TAG = "Interval.java";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String price;
        View v = inflater.inflate(R.layout.interval_fragment, null);
        submit = v.findViewById(R.id.submit);
        interval = v.findViewById(R.id.interval);
        cancel = v.findViewById(R.id.cancel);
        tracking = v.findViewById(R.id.tracking);
        clear = v.findViewById(R.id.clear);
        currentPrice = v.findViewById(R.id.currentPrice);



        FileInputStream fileInputStream;
        try {

            fileInputStream = getContext().openFileInput("price.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            price = bufferedReader.readLine();
            tracking.setText("You are tracking: "+price);
            Log.i(TAG, "In try");
        } catch (FileNotFoundException e) {
            Log.i(TAG, "In catch");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.bitstamp.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Ticker_Req jsonPlaceHolderApi = retrofit.create(Ticker_Req.class);

        Call<TickerGetters> call = jsonPlaceHolderApi.getPrice();

        call.enqueue(new Callback<TickerGetters>() {
            @Override
            public void onResponse(Call<TickerGetters> call, Response<TickerGetters> response) {
                String price = response.body().getLast();
                currentPrice.setText("The current price is: "+price);
            }

            @Override
            public void onFailure(Call<TickerGetters> call, Throwable t) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(interval.getText().toString().matches(""))
                {
                    Toast.makeText(getContext(), "Please enter a value",Toast.LENGTH_LONG).show();
                }
                else
                {
                    FileOutputStream fileOutputStream;
                    try {
                        fileOutputStream = getContext().openFileOutput("price.txt",getContext().MODE_PRIVATE);
                        System.out.println(interval.getText().toString());
                        tracking.setText("You are tracking: "+interval.getText().toString());
                        fileOutputStream.write(interval.getText().toString().getBytes());
                        fileOutputStream.close();
                        final WorkManager mWorkManager = WorkManager.getInstance();
                        final PeriodicWorkRequest mRequest = new PeriodicWorkRequest.Builder(NotificationWorker.class,1, TimeUnit.HOURS)
                                .build();
                        mWorkManager.cancelAllWork();
                        mWorkManager.enqueue(mRequest);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getContext(), "Amount Set", Toast.LENGTH_LONG).show();
                }





            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File file = new File("/data/data/com.example.ilovezappos/files/price.txt");
                file.delete();
                final WorkManager mWorkManager = WorkManager.getInstance();
                mWorkManager.cancelAllWork();
                tracking.setText("You are currently not tracking");
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(interval.getText().toString().matches(""))
                {
                    Toast.makeText(getContext(), "Please enter a value",Toast.LENGTH_LONG).show();
                }
                else
                {
                    interval.getText().clear();
                }

            }
        });




        return v;




    }
}
