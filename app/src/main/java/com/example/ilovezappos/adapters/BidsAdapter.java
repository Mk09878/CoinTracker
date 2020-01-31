package com.example.ilovezappos.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovezappos.R;
import com.example.ilovezappos.getters.BidGetters;

import java.util.ArrayList;

public class BidsAdapter extends RecyclerView.Adapter<BidsAdapter.BidsViewHolder> {

    private ArrayList<BidGetters> arrayList;
    private String TAG = "BidsAdapter";
    Context context;



    @NonNull
    @Override
    public BidsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "inviewholder");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.carditem_bids, parent, false);
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
        setAnimation(holder.item_parent);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public BidsAdapter(ArrayList<BidGetters> list, Context context){
       Log.i(TAG, "incons");
        arrayList = list;
        this.context = context;
    }

    public static class BidsViewHolder extends RecyclerView.ViewHolder
    {
        public TextView value;
        public TextView amount;
        public TextView bidslayout;
        ConstraintLayout item_parent;

        public BidsViewHolder(@NonNull View itemView) {
            super(itemView);
            value = itemView.findViewById(R.id.Value);
            amount = itemView.findViewById(R.id.Amount);
            bidslayout = itemView.findViewById(R.id.BidsLayout);
            item_parent = itemView.findViewById(R.id.parent_bids);
        }
    }
    private void setAnimation(View viewToAnimate) {

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down);
        viewToAnimate.startAnimation(animation);


    }
}
