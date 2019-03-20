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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

public class MyBloodPressureRecyclerViewAdapter extends RecyclerView
        .Adapter<MyBloodPressureRecyclerViewAdapter
        .DataObjectHolder> {
    //    private static String LOG_TAG = "MyMedicationRecyclerViewAdapter";
    private ArrayList<BloodPressureDataObject> mDataset; // make sure this is BPDO
    private Context context;
    private static MyClickListener myClickListener;
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
        TextView bpTimeView;
        ImageButton deleteButton;


        public DataObjectHolder(View itemView) {
            super(itemView);
            bpTopView = (TextView) itemView.findViewById(R.id.topValueBPCardText);
            bpBottomView = (TextView) itemView.findViewById(R.id.bottomValueBPCardText);
            bpDateView = (TextView) itemView.findViewById(R.id.bpDateCardText);
            bpTimeView = (TextView) itemView.findViewById(R.id.bpTimeCardText);
            deleteButton = (ImageButton) itemView.findViewById(R.id.deleteButton);
            bpTimeView.setVisibility(View.GONE);

            deleteButton.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i(LOG_TAG, bpTimeView.getText().toString());

            context = itemView.getContext();

            Log.i(LOG_TAG, "confirmed");

            AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());

            alertDialog.setTitle("Delete this item?");
            alertDialog.setMessage("Are you sure you want to delete this?");

            alertDialog.setPositiveButton(
                    "Delete",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.myDB.deleteBloodPressure(bpTimeView.getText().toString());
                            final Intent intent;
                            intent = new Intent(context, BloodPressureGraph.class);
                            context.startActivity(intent);
                        }
                    }
            );

            alertDialog.show();
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
        holder.bpTimeView.setText(mDataset.get(position).getBpTime());

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