package com.example.ilovezappos;

import android.os.Bundle;
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

import java.io.BufferedReader;
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

import static android.content.Context.MODE_PRIVATE;

public class Interval extends Fragment {

    Button submit;
    Button cancel;
    EditText interval;
    TextView tracking;
    TextView currentPrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.interval, null);
        submit = v.findViewById(R.id.submit);
        interval = v.findViewById(R.id.interval);
        cancel = v.findViewById(R.id.cancel);
        tracking = v.findViewById(R.id.tracking);
        currentPrice = v.findViewById(R.id.currentPrice);
        String price;

        FileInputStream fileInputStream;
        try {
            fileInputStream = getContext().openFileInput("price.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            price = bufferedReader.readLine();
            tracking.setText("You are tracking: "+price);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.bitstamp.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Ticker jsonPlaceHolderApi = retrofit.create(Ticker.class);

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
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final WorkManager mWorkManager = WorkManager.getInstance();
                mWorkManager.cancelAllWork();
                tracking.setText("You are currently not tracking");
            }
        });






        return v;




    }
}
