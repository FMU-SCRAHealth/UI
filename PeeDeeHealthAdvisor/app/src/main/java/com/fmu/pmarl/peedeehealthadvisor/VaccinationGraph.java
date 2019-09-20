/*
    Author: Patrick Marlowe
    Email Address: pmarlowe782@gmail.com
    Written: June 22, 2018
    Last Updated: June 25, 2018

    Compilation:
    Execution:

    Description of Execution:
 */
package com.fmu.pmarl.peedeehealthadvisor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;

public class VaccinationGraph extends AppCompatActivity
{
    private ImageButton home;
    BarChart barChart;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    /*
     * HOW TO ADD VACCINATION
     * STEP 1: add the new vaccination name to the string array in the strings.xml file.
     * STEP 2: add new VaccinationsDataObject object with name in VaccinationGraph.java using results.add()
     * STEP 3: test to make sure the vaccination's card is showing up.
     * STEP 4: make new if-statements inside of while-loop with cursor for finding if new vaccination added to results list.
     * STEP 5: make new if-statements for notifications if they apply.
     * STEP 6: test to make sure the vaccination's card is updating if taken.
     * */


    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_card_view_vaccinations);

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
        String time = "Not Taken";
        int immunized;
        String tableImmunized ="No";
        String vaccinationName ="";
        ArrayList results = new ArrayList<VaccinationsDataObject>();
        VaccinationsDataObject fluShotCardDefault = new VaccinationsDataObject("Flu Shot", "No", "Not Taken", "0");
//        VaccinationsDataObject shinglesCardDefault = new VaccinationsDataObject("Shingles", "No", "Not Taken", "0");
        VaccinationsDataObject shingrixCardDefault = new VaccinationsDataObject("Shingrix (RZV)", "No", "Not Taken", "0");
        VaccinationsDataObject prevnar13Default = new VaccinationsDataObject("Prevnar 13", "No", "Not Taken", "0");
        VaccinationsDataObject pneumovax23Default = new VaccinationsDataObject("Pneumovax 23", "No", "Not Taken", "0");
        VaccinationsDataObject tetanusDefault = new VaccinationsDataObject("Tetanus (TD)", "No", "Not Taken", "0");


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_vaccinations);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyVaccinationRecyclerViewAdapter(results);
        mRecyclerView.setAdapter(mAdapter);
        // these are the indexes to the ArrayList added for default values
        results.add(fluShotCardDefault); // 0
//        results.add(shinglesCardDefault); // 1
        results.add(shingrixCardDefault); // 1
        results.add(prevnar13Default); // 2
        results.add(pneumovax23Default); // 3
        results.add(tetanusDefault); // 4



        if(cursorVaccinations != null) {
            cursorVaccinations.moveToFirst();
        }

        // had to use isAfterLast() to get the first entry to be read
        while(!cursorVaccinations.isAfterLast()) {

            date = cursorVaccinations.getString(0);

            time = cursorVaccinations.getString(2);

            immunized = cursorVaccinations.getInt(3);

            vaccinationName = cursorVaccinations.getString(4);

            Log.d("DATABASETEST", time);



            if (immunized == 0) {

                tableImmunized = "No";

            }
            else
            {
                tableImmunized = "Yes";
            }

            if (vaccinationName.equals("Flu Shot"))
            {
                VaccinationsDataObject fluShot = new VaccinationsDataObject(vaccinationName, tableImmunized, date, time);
                results.set(0, fluShot);
            }
            else if (vaccinationName.equals("Shingrix (RZV)"))
            {
                VaccinationsDataObject shingles = new VaccinationsDataObject(vaccinationName, tableImmunized, date, time);
                results.set(1, shingles);

            }
            else if (vaccinationName.equals("Prevnar 13"))
            {
                VaccinationsDataObject prevnar13 = new VaccinationsDataObject(vaccinationName, tableImmunized, date, time);

                results.set(2, prevnar13);
            }

            else if (vaccinationName.equals("Pneumovax 23"))
            {
                VaccinationsDataObject pneumovax23 = new VaccinationsDataObject(vaccinationName, tableImmunized, date, time);

                results.set(3, pneumovax23);
            }
            else if (vaccinationName.equals("Tetanus (TD)"))
            {
                VaccinationsDataObject tetanusTD = new VaccinationsDataObject(vaccinationName, tableImmunized, date, time);

                results.set(4, tetanusTD);
            }
            else {

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