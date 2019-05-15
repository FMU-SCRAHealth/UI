package com.fmu.pmarl.peedeehealthadvisor;

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

public class MyVaccinationRecyclerViewAdapter extends RecyclerView
        .Adapter<MyVaccinationRecyclerViewAdapter
        .DataObjectHolder> {
    //    private static String LOG_TAG = "MyMedicationRecyclerViewAdapter";
    private ArrayList<VaccinationsDataObject> mDataset;
    private static MyClickListener myClickListener;
    private Context context;

    public MyVaccinationRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        TextView vaccinationNameView;
        TextView vaccinationTakenView;
        TextView vaccinationDateView;
        TextView vaccinationTimeView;
        ImageButton deleteButton;


        public DataObjectHolder(View itemView) {
            super(itemView);
            vaccinationNameView = (TextView) itemView.findViewById(R.id.vaccinationNameCardText);
            vaccinationTakenView = (TextView) itemView.findViewById(R.id.takenCardText);
            vaccinationDateView = (TextView) itemView.findViewById(R.id.vaccinationDateCardText);
            vaccinationTimeView = (TextView) itemView.findViewById(R.id.vaccinationTimeCardText);
            deleteButton = (ImageButton) itemView.findViewById(R.id.deleteButtonVaccinations);
            vaccinationTimeView.setVisibility(View.GONE);
            deleteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            context = itemView.getContext();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());

            alertDialog.setTitle("Remove this Vaccination Entry?");
            alertDialog.setMessage("Are you sure you want to delete this Vaccination Entry?");

            alertDialog.setPositiveButton(
                    "Delete",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("DATABASE", vaccinationTimeView.getText().toString());
                            MainActivity.myDB.deleteVaccination(vaccinationTimeView.getText().toString());
                            final Intent intent;
                            intent = new Intent(context, VaccinationGraph.class);
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

    public MyVaccinationRecyclerViewAdapter(ArrayList<VaccinationsDataObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vaccinations_card_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;

    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.vaccinationNameView.setText(mDataset.get(position).getVaccinationName());
        holder.vaccinationTakenView.setText(mDataset.get(position).getVaccinationTaken());
        holder.vaccinationDateView.setText(mDataset.get(position).getVaccinationDate());
        holder.vaccinationTimeView.setText(mDataset.get(position).getVaccineEpoch());

    }

    public void addItem(VaccinationsDataObject dataObj, int index) {
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