package com.example.pmarl.peedeehealthadvisor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MyMedicationRecyclerViewAdapter extends RecyclerView
        .Adapter<MyMedicationRecyclerViewAdapter
        .DataObjectHolder> {
//    private static String LOG_TAG = "MyMedicationRecyclerViewAdapter";
    private ArrayList<MedicationsDataObject> mDataset;
    private static MyClickListener myClickListener;
    static String phoneNumber;
    private Context context;


    public MyMedicationRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView medNameView;
        TextView medDoseView;
        TextView medDeliveryView;
        TextView medRxNumView;
        TextView medPharmNameView;
        TextView medPharmNumView;
        ImageButton callButton;
        Switch background;


        public DataObjectHolder(View itemView) {
            super(itemView);
            medNameView = (TextView) itemView.findViewById(R.id.medNameCardText);
            medDoseView = (TextView) itemView.findViewById(R.id.medDoseCardText);
            medDeliveryView = (TextView) itemView.findViewById(R.id.medDeliveryCardText);
            medRxNumView = (TextView) itemView.findViewById(R.id.medRXNumCardText);
            medPharmNameView = (TextView) itemView.findViewById(R.id.medPharmNameCardText);
            medPharmNumView = (TextView) itemView.findViewById(R.id.medPharmNumCardText);
            callButton = (ImageButton) itemView.findViewById(R.id.phoneCallButton);
            background = (Switch) itemView.findViewById(R.id.switch1);

//            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
        }

    } // end of class


    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyMedicationRecyclerViewAdapter(ArrayList<MedicationsDataObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.med_card_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;

    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.medNameView.setText(mDataset.get(position).getName());
        holder.medDoseView.setText(mDataset.get(position).getDose());
        holder.medDeliveryView.setText(mDataset.get(position).getDelivery());
        holder.medRxNumView.setText(mDataset.get(position).getRxNum());
        holder.medPharmNameView.setText(mDataset.get(position).getPharmName());
        holder.medPharmNumView.setText(mDataset.get(position).getMedPharmNum());
        holder.callButton.setOnClickListener(mDataset.get(position).createCall());
        holder.background.setOnCheckedChangeListener(mDataset.get(position).createSwitch());


    }

    public void addItem(MedicationsDataObject dataObj, int index) {
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