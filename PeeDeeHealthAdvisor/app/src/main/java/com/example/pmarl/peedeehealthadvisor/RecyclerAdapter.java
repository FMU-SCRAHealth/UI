package com.example.pmarl.peedeehealthadvisor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {


    public View pageOne;

    private ItemClickLIstener itemClickListener;


    public RecyclerViewHolder(View itemView) {
        super(itemView);
        pageOne = (View) itemView.findViewById(R.id.recyclerView);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickLIstener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition());
    }

    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition());
        return true;
    }
}

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<View> listData = new ArrayList<>();
    private Context context;

    public RecyclerAdapter(List<View> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }




    @Override
    public RecyclerViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.card_view, parent, false);

        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
//        holder.bloodPressureButton.set;

        holder.setItemClickListener(new ItemClickLIstener() {
            @Override
            public void onClick(View view, int position) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


}


