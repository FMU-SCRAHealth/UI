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

public class MyAllergiesRecyclerViewAdapter extends RecyclerView
        .Adapter<MyAllergiesRecyclerViewAdapter
        .DataObjectHolder> {
    //    private static String LOG_TAG = "MyMedicationRecyclerViewAdapter";
    private ArrayList<AllergiesDataObject> mDataset;
    private static MyClickListener myClickListener;
    private Context context;
    static final String LOG_TAG = "ALLERGIES";

    public MyAllergiesRecyclerViewAdapter(Context context) {
        this.context = context;
    }


    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        TextView allergyNameView;
        TextView allergyDescriptionView;
        ImageButton deleteButton;


        public DataObjectHolder(View itemView) {
            super(itemView);
            allergyNameView = (TextView) itemView.findViewById(R.id.allergyNameCardText);
            allergyDescriptionView = (TextView) itemView.findViewById(R.id.allergyDescriptionCardText);
            deleteButton = (ImageButton) itemView.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            context = itemView.getContext();

            Log.i(LOG_TAG, "confirmed");

            AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());

            alertDialog.setTitle("Delete this item?");
            alertDialog.setMessage("Are you sure you want to delete this?");

            alertDialog.setPositiveButton(
                    "Delete",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.myDB.deleteAllergy(allergyNameView.getText().toString());
                            final Intent intent;
                            intent = new Intent(context, AllergiesTable.class);
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

    public MyAllergiesRecyclerViewAdapter(ArrayList<AllergiesDataObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allergies_card_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;

    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.allergyNameView.setText(mDataset.get(position).getAllergyName());
        holder.allergyDescriptionView.setText(mDataset.get(position).getAllergyDescription());

    }

    public void addItem(AllergiesDataObject dataObj, int index) {
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