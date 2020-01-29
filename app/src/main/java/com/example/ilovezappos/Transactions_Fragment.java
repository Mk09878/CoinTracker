package com.example.ilovezappos;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Transactions_Fragment extends Fragment {
    TextView tv;
    LineChart lc;
    String TAG = "MainActivity.java";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.transcations, null);
        lc = v.findViewById(R.id.lc);

        XAxis xAxis = lc.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        lc.getAxisRight().setEnabled(false);
        lc.animateX(2000);
        lc.animateY(2000);


        // Set an alternative background color
        lc.setBackgroundColor(Color.TRANSPARENT);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.bitstamp.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonConn jsonPlaceHolderApi = retrofit.create(JsonConn.class);

        Call<List<GetData>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<GetData>>() {
            @Override
            public void onResponse(Call<List<GetData>> call, Response<List<GetData>> response) {

                if (!response.isSuccessful()) {
                    tv.setText("Code: " + response.code());
                    return;
                }
                HashMap hash = new HashMap();
                List<GetData> posts = response.body();
                ArrayList<Entry> a = new ArrayList<>();
                for(GetData post: posts)
                {
                    Float date = Float.valueOf(post.getDate());
                    Float price = Float.valueOf(post.getPrice());
                    //a.add(new Entry(date, price));
                    if(hash.containsKey(date))
                    {
                        //int index = dates.indexOf(date);
                        float pricetocomp = (float) hash.get(date);
                        if(pricetocomp < price)
                        {
                            hash.remove(date);

                            hash.put(date, price);

                            continue;
                        }
                        continue;
                    }


                    hash.put(date,price);

                }

                TreeMap<Float, Float> sorted = new TreeMap<>();
                sorted.putAll(hash);

                Iterator it = sorted.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    float price = (float) pair.getValue();
                    float date = (float) pair.getKey();
                    a.add(new Entry(date, price));
                    it.remove();
                }


                LineDataSet set = new LineDataSet(a, "DS1");
                set.setColor(ColorTemplate.getHoloBlue());
                set.setLineWidth(3f);
                set.setDrawCircles(false);
                set.setDrawValues(false);
                set.setDrawFilled(true);
                set.setFillAlpha(50);
                set.setFillColor(ColorTemplate.getHoloBlue());
                set.setDrawCircleHole(false);
                set.setDrawHighlightIndicators(false);
                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(set);
                Description des = lc.getDescription();
                des.setEnabled(false);
                Legend leg = lc.getLegend();
                leg.setEnabled(false);
                LineData data = new LineData(dataSets);
                lc.setData(data);
            }

            @Override
            public void onFailure(Call<List<GetData>> call, Throwable t) {

                tv.setText(t.getMessage());

            }
        });

        return v;
    }
}
