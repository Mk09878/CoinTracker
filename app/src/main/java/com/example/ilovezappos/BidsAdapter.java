package com.example.ilovezappos;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BidsAdapter extends RecyclerView.Adapter<BidsAdapter.BidsViewHolder> {

    private ArrayList<BidGetters> arrayList;
    private String TAG = "BidsAdapter";



    @NonNull
    @Override
    public BidsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "inviewholder");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.carditembids, parent, false);
        BidsViewHolder avh = new BidsViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull BidsViewHolder holder, int position) {

        BidGetters current = arrayList.get(position);
        Log.i(TAG, String.valueOf(current.getBids()));

        holder.value.setText(String.valueOf(current.getVal()));
        holder.amount.setText(String.valueOf(current.getAmt()));
        holder.bidslayout.setText(String.valueOf(current.getBids()));


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public BidsAdapter(ArrayList<BidGetters> list){
       Log.i(TAG, "incons");
        arrayList = list;
    }

    public static class BidsViewHolder extends RecyclerView.ViewHolder
    {
        public TextView value;
        public TextView amount;
        public TextView bidslayout;

        public BidsViewHolder(@NonNull View itemView) {
            super(itemView);
            value = itemView.findViewById(R.id.Value);
            amount = itemView.findViewById(R.id.Amount);
            bidslayout = itemView.findViewById(R.id.BidsLayout);
        }
    }
}
