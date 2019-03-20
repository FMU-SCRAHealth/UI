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

public class MyCholesterolRecyclerViewAdapter extends RecyclerView
        .Adapter<MyCholesterolRecyclerViewAdapter
        .DataObjectHolder> {
    //    private static String LOG_TAG = "MyMedicationRecyclerViewAdapter";
    private ArrayList<CholesterolDataObject> mDataset; // make sure this is Blood Sugar Data Object
    private static MyClickListener myClickListener;
    private Context context;

    public MyCholesterolRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        TextView cholesterolTCView;
        TextView cholesterolHDLView;
        TextView cholesterolTrigView;
        TextView cholesterolLDLView;
        TextView cholesterolDateView;
        TextView cholesterolTimeView;
        ImageButton deleteButton;


        public DataObjectHolder(View itemView) {
            super(itemView);
            cholesterolTCView = (TextView) itemView.findViewById(R.id.totalCholesterolCardText);
            cholesterolHDLView = (TextView) itemView.findViewById(R.id.hdlCardViewText);
            cholesterolTrigView = (TextView) itemView.findViewById(R.id.trigCardViewText);
            cholesterolLDLView = (TextView) itemView.findViewById(R.id.ldlCardViewText);
            cholesterolDateView = (TextView) itemView.findViewById(R.id.cholesterolDateCardText);
            cholesterolTimeView = (TextView) itemView.findViewById(R.id.cholesterolTimeCardText);
            deleteButton = (ImageButton) itemView.findViewById(R.id.deleteButtonCholesterol);
            cholesterolTimeView.setVisibility(View.GONE);

            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            context = itemView.getContext();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());

            alertDialog.setTitle("Delete this item?");
            alertDialog.setMessage("Are you sure you want to delete this?");

            alertDialog.setPositiveButton(
                    "Delete",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.myDB.deleteCholesterol(cholesterolTimeView.getText().toString());
                            final Intent intent;
                            intent = new Intent(context, CholesterolGraph.class);
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
        holder.cholesterolTimeView.setText(mDataset.get(position).getCholEpoch());

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