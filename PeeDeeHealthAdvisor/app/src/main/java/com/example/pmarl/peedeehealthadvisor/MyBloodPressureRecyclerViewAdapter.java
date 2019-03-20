package com.example.pmarl.peedeehealthadvisor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyBloodPressureRecyclerViewAdapter extends RecyclerView
        .Adapter<MyBloodPressureRecyclerViewAdapter
        .DataObjectHolder> {
    //    private static String LOG_TAG = "MyMedicationRecyclerViewAdapter";
    private ArrayList<BloodPressureDataObject> mDataset; // make sure this is BPDO
    private static MyClickListener myClickListener;
    private Context context;
    static final String LOG_TAG = "Blood Pressure";

    public MyBloodPressureRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        TextView bpTopView;
        TextView bpBottomView;
        TextView bpDateView;
        ImageView deleteButton;


        public DataObjectHolder(View itemView) {
            super(itemView);
            bpTopView = (TextView) itemView.findViewById(R.id.topValueBPCardText);
            bpBottomView = (TextView) itemView.findViewById(R.id.bottomValueBPCardText);
            bpDateView = (TextView) itemView.findViewById(R.id.bpDateCardText);
            deleteButton = (ImageView) itemView.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(this);
        }

        @Override
        // DELETE CARDS
        public void onClick(View v) {

        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyBloodPressureRecyclerViewAdapter(ArrayList<BloodPressureDataObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blood_pressure_card_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;

    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.bpTopView.setText(mDataset.get(position).getBpTop());
        holder.bpBottomView.setText(mDataset.get(position).getBpBottom());
        holder.bpDateView.setText(mDataset.get(position).getBpDate());

    }

    public void addItem(BloodPressureDataObject dataObj, int index) {
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