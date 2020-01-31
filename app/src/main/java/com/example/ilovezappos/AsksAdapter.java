package com.example.ilovezappos;

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

import java.util.ArrayList;

public class AsksAdapter extends RecyclerView.Adapter<AsksAdapter.AsksViewHolder> {

    private ArrayList<AskGetters> arrayList;
    private String TAG = "AsksAdapter";
    Context context;




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
        setAnimation(holder.item_parent);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public AsksAdapter(ArrayList<AskGetters> list, Context context){
       Log.i(TAG, "incons");
        arrayList = list;
        this.context = context;
    }

    public static class AsksViewHolder extends RecyclerView.ViewHolder
    {
        public TextView value;
        public TextView amount;
        public TextView askslayout;
        ConstraintLayout item_parent;

        public AsksViewHolder(@NonNull View itemView) {
            super(itemView);
            value = itemView.findViewById(R.id.Value);
            amount = itemView.findViewById(R.id.Amount);
            askslayout = itemView.findViewById(R.id.AsksLayout);
            item_parent = itemView.findViewById(R.id.parent_asks);
        }
    }
    private void setAnimation(View viewToAnimate) {

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down);
        viewToAnimate.startAnimation(animation);


    }

}
