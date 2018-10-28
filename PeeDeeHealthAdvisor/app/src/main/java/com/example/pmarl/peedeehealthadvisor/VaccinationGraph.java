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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

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

        // finding the graph by element id.
        barChart = (BarChart) findViewById(R.id.barGraph);


        // This is the array list that holds the values for the bar graph.
        // We can take data from the database, then store in this list.
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        // method for entering data into the graph.
        barEntries.add(new BarEntry(65f, 0));
        barEntries.add(new BarEntry(59f, 1));
        barEntries.add(new BarEntry(80f, 2));
        barEntries.add(new BarEntry(79f, 3));
        barEntries.add(new BarEntry(72f, 4));
        barEntries.add(new BarEntry(69f, 5));
        barEntries.add(new BarEntry(75f, 6));
        barEntries.add(new BarEntry(80f, 7));
        barEntries.add(new BarEntry(79f, 8));
        barEntries.add(new BarEntry(65f, 9));
        barEntries.add(new BarEntry(74f, 10));
        barEntries.add(new BarEntry(71f, 11));

        // This constructor creates a data-set from the data above.
        BarDataSet barDataSet = new BarDataSet(barEntries, "Vaccinations");

        // This ArrayList holds the dates for the x-axis
        ArrayList<String> theDates = new ArrayList();

        theDates.add("January");
        theDates.add("February");
        theDates.add("March");
        theDates.add("April");
        theDates.add("May");
        theDates.add("June");
        theDates.add("July");
        theDates.add("August");
        theDates.add("September");
        theDates.add("October");
        theDates.add("November");
        theDates.add("December");

        // Constructor for adding the dates with the data from above. Works on gradle version v2.2.4
        BarData theData = new BarData(theDates, barDataSet);
        barChart.setData(theData);
        barDataSet.setColor(getResources().getColor(R.color.GreenHuesMedium));
        barChart.setDescription("");
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(false);

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