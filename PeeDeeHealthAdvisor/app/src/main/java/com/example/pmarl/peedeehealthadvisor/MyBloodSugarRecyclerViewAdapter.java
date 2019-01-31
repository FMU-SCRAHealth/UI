package com.example.pmarl.peedeehealthadvisor;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MyBloodSugarRecyclerViewAdapter extends RecyclerView
        .Adapter<MyBloodSugarRecyclerViewAdapter
        .DataObjectHolder> {
    //    private static String LOG_TAG = "MyMedicationRecyclerViewAdapter";
    private ArrayList<BloodSugarDataObject> mDataset; // make sure this is Blood Sugar Data Object
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        TextView bsValueView;
        TextView bsFastingView;
        TextView bsDateView;


        public DataObjectHolder(View itemView) {
            super(itemView);
            bsValueView = (TextView) itemView.findViewById(R.id.bloodSugarValueCardText);
            bsFastingView = (TextView) itemView.findViewById(R.id.fastingCardText);
            bsDateView = (TextView) itemView.findViewById(R.id.bsDateCardText);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyBloodSugarRecyclerViewAdapter(ArrayList<BloodSugarDataObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blood_sugar_card_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;

    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.bsValueView.setText(mDataset.get(position).getBsValue());
        holder.bsFastingView.setText(mDataset.get(position).getBsFasting());
        holder.bsDateView.setText(mDataset.get(position).getBsDate());

    }

    public void addItem(BloodSugarDataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}