package com.example.pmarl.peedeehealthadvisor;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MyBodyWeightRecyclerViewAdapter extends RecyclerView
        .Adapter<MyBodyWeightRecyclerViewAdapter
        .DataObjectHolder> {
    //    private static String LOG_TAG = "MyMedicationRecyclerViewAdapter";
    private ArrayList<BodyWeightDataObject> mDataset; // make sure this is Blood Sugar Data Object
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        TextView weightValueView;
        TextView weightDateView;


        public DataObjectHolder(View itemView) {
            super(itemView);
            weightValueView = (TextView) itemView.findViewById(R.id.bodyWeightValueCardText);
            weightDateView = (TextView) itemView.findViewById(R.id.bodyWeightDateCardText);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyBodyWeightRecyclerViewAdapter(ArrayList<BodyWeightDataObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.body_weight_card_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;

    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.weightValueView.setText(mDataset.get(position).getWeightValue());
        holder.weightDateView.setText(mDataset.get(position).getWeightDate());

    }

    public void addItem(BodyWeightDataObject dataObj, int index) {
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