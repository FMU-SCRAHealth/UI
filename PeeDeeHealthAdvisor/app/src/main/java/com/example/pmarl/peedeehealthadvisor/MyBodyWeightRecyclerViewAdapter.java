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
import android.widget.TextView;

import java.util.ArrayList;

public class MyBodyWeightRecyclerViewAdapter extends RecyclerView
        .Adapter<MyBodyWeightRecyclerViewAdapter
        .DataObjectHolder> {
    //    private static String LOG_TAG = "MyMedicationRecyclerViewAdapter";
    private ArrayList<BodyWeightDataObject> mDataset; // make sure this is Blood Sugar Data Object
    private static MyClickListener myClickListener;
    private Context context;

    public MyBodyWeightRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        TextView weightValueView;
        TextView weightDateView;
        TextView weightTimeView;
        ImageButton deleteButton;


        public DataObjectHolder(View itemView) {
            super(itemView);
            weightValueView = (TextView) itemView.findViewById(R.id.bodyWeightValueCardText);
            weightDateView = (TextView) itemView.findViewById(R.id.bodyWeightDateCardText);
            weightTimeView = (TextView) itemView.findViewById(R.id.bodyWeightTimeCardText);
            deleteButton = (ImageButton) itemView.findViewById(R.id.deleteButtonBodyWeight);
            weightTimeView.setVisibility(View.GONE);

            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            context = itemView.getContext();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());

            alertDialog.setTitle("Delete this Body Weight Entry?");
            alertDialog.setMessage("Are you sure you want to delete this Body Weight Entry?");

            alertDialog.setPositiveButton(
                    "Delete",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.myDB.deleteWeight(weightTimeView.getText().toString());
                            final Intent intent;
                            intent = new Intent(context, BodyWeightGraph.class);
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
        holder.weightTimeView.setText(mDataset.get(position).getBodyWeightTime());

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