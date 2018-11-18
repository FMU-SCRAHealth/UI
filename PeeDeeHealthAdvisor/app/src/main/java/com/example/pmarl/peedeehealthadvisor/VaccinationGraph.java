/*
    Author: Patrick Marlowe
    Email Address: pmarlowe782@gmail.com
    Written: June 22, 2018
    Last Updated: June 25, 2018

    Compilation:
    Execution:

    Description of Execution:
 */
package com.example.pmarl.peedeehealthadvisor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Date;

public class VaccinationGraph extends AppCompatActivity
{
    private ImageButton home;
    BarChart barChart;


    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.vaccination_graph);

        home = (ImageButton) findViewById(R.id.Home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

//        // finding the graph by element id.
//        barChart = (BarChart) findViewById(R.id.barGraph);

//        String date;
//        ArrayList<String> immunized = new ArrayList<>();
//        ArrayList<String> vaccinationName = new ArrayList<>();

//        Cursor cursor = MainActivity.myDB.readVaccinationRecords();
//
//        if(cursor != null) {
//            cursor.moveToFirst();
//        }
//
//        while(cursor.moveToNext()) {
//            date = cursor.getString(0) + cursor.getString(1);
//
//            immunized.add(cursor.getString(2));
//
//            vaccinationName.add(cursor.getString(3));
//        }
//
//        TextView fluShotTakenView = findViewById(R.id.fluShotTaken);
//
//        fluShotTakenView.setText(immunized.get(0));







    }

    @Override
    public void onBackPressed() {
        launchPrevActivity();
    }

    private void launchMainActivity()
    {
        Intent intent = new Intent (this, MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void launchPrevActivity()
    {
        Intent intent = new Intent (this, SelectVaccinationActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}