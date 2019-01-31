package com.example.pmarl.peedeehealthadvisor;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCholesterolRecyclerViewAdapter extends RecyclerView
        .Adapter<MyCholesterolRecyclerViewAdapter
        .DataObjectHolder> {
    //    private static String LOG_TAG = "MyMedicationRecyclerViewAdapter";
    private ArrayList<CholesterolDataObject> mDataset; // make sure this is Blood Sugar Data Object
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        TextView cholesterolTCView;
        TextView cholesterolHDLView;
        TextView cholesterolTrigView;
        TextView cholesterolLDLView;
        TextView cholesterolDateView;


        public DataObjectHolder(View itemView) {
            super(itemView);
            cholesterolTCView = (TextView) itemView.findViewById(R.id.totalCholesterolCardText);
            cholesterolHDLView = (TextView) itemView.findViewById(R.id.hdlCardViewText);
            cholesterolTrigView = (TextView) itemView.findViewById(R.id.trigCardViewText);
            cholesterolLDLView = (TextView) itemView.findViewById(R.id.ldlCardViewText);
            cholesterolDateView = (TextView) itemView.findViewById(R.id.cholesterolDateCardText);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyCholesterolRecyclerViewAdapter(ArrayList<CholesterolDataObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cholesterol_card_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;

    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.cholesterolTCView.setText(mDataset.get(position).getTcValue());
        holder.cholesterolHDLView.setText(mDataset.get(position).getHdlValue());
        holder.cholesterolTrigView.setText(mDataset.get(position).getTrigValue());
        holder.cholesterolLDLView.setText(mDataset.get(position).getLdlValue());
        holder.cholesterolDateView.setText(mDataset.get(position).getCholesterolDate());

    }

    public void addItem(CholesterolDataObject dataObj, int index) {
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