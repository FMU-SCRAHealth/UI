package com.example.pmarl.peedeehealthadvisor;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;

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

        public DataObjectHolder(View itemView) {
            super(itemView);
            medNameView = (TextView) itemView.findViewById(R.id.medNameCardText);
            medDoseView = (TextView) itemView.findViewById(R.id.medDoseCardText);
            medDeliveryView = (TextView) itemView.findViewById(R.id.medDeliveryCardText);
            medRxNumView = (TextView) itemView.findViewById(R.id.medRXNumCardText);
            medPharmNameView = (TextView) itemView.findViewById(R.id.medPharmNameCardText);
            medPharmNumView = (TextView) itemView.findViewById(R.id.medPharmNumCardText);
//            callButton = (ImageButton) itemView.findViewById(R.id.phoneCallButton);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(ArrayList<DataObject> myDataset) {
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

    }

    public void addItem(DataObject dataObj, int index) {
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