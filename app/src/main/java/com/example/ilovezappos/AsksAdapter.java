package com.example.ilovezappos;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AsksAdapter extends RecyclerView.Adapter<AsksAdapter.AsksViewHolder> {

    private ArrayList<AskGetters> arrayList;
    private String TAG = "AsksAdapter";



    @NonNull
    @Override
    public AsksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "inviewholder");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.carditem, parent, false);
        AsksViewHolder avh = new AsksViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AsksViewHolder holder, int position) {

        AskGetters current = arrayList.get(position);
        Log.i(TAG, String.valueOf(current.getVal()));

        holder.value.setText(String.valueOf(current.getVal()));
        holder.amount.setText(String.valueOf(current.getAmt()));
        holder.askslayout.setText(String.valueOf(current.getAsk()));


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public AsksAdapter(ArrayList<AskGetters> list){
       Log.i(TAG, "incons");
        arrayList = list;
    }

    public static class AsksViewHolder extends RecyclerView.ViewHolder
    {
        public TextView value;
        public TextView amount;
        public TextView askslayout;

        public AsksViewHolder(@NonNull View itemView) {
            super(itemView);
            value = itemView.findViewById(R.id.Value);
            amount = itemView.findViewById(R.id.Amount);
            askslayout = itemView.findViewById(R.id.AsksLayout);
        }
    }
}
