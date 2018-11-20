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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

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


        // cursor instantiations have to be different names or they will conflict with one another
        Cursor cursorVaccinations = MainActivity.myDB.readVaccinationRecords();

        // Strings
        String date = "Not Taken";
        int immunized;
        String tableImmunized ="No";
        String vaccinationName ="";

        // Flu Shot Row
        TextView fluShotTableNameView = findViewById(R.id.fluShotTable);
        TextView fluShotTakenView = findViewById(R.id.fluShotTaken);
        TextView fluShotDateView = findViewById(R.id.fluShotDate);

        // Shingles Row
        TextView shingleTaken = findViewById(R.id.shingleTaken);
        TextView shingleDate = findViewById(R.id.shingleDate);

        // Pneumonia Row
        TextView pneumoniaTaken = findViewById(R.id.pneumoniaTaken);
        TextView pneumoniaDate = findViewById(R.id.pneumoniaDate);

        // default table text
        fluShotTakenView.setText(tableImmunized);
        fluShotDateView.setText(date);

        shingleTaken.setText(tableImmunized);
        shingleDate.setText(date);

        pneumoniaTaken.setText(tableImmunized);
        pneumoniaDate.setText(date);


        if(cursorVaccinations != null) {
            cursorVaccinations.moveToFirst();
        }

        // had to use isAfterLast() to get the first entry to be read
        while(!cursorVaccinations.isAfterLast()) {

            date = cursorVaccinations.getString(0);

            immunized = cursorVaccinations.getInt(1);

            vaccinationName = cursorVaccinations.getString(2);

            if (immunized == 0) {

                tableImmunized = "No";

            }
            else
            {
                tableImmunized = "Yes";
            }

            if (vaccinationName.equals("Flu Shot"))
            {
                fluShotDateView.setText(date);
                fluShotTakenView.setText(tableImmunized);
            }
            else if (vaccinationName.equals("Shingle"))
            {
                shingleDate.setText((date));
                shingleTaken.setText(tableImmunized);
            }
            else if (vaccinationName.equals("Pneumonia"))
            {
                pneumoniaDate.setText(date);
                pneumoniaTaken.setText(tableImmunized);
            }
            else {
                fluShotDateView.setText("Test");
            }
            cursorVaccinations.moveToNext();
        }


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