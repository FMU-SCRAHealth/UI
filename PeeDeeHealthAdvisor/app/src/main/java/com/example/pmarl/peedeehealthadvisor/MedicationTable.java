package com.example.pmarl.peedeehealthadvisor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MedicationTable extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";

    String medName = "";
    String medDose = "";
    String medDelivery;
    String medRxNum;
    String medPharmName;
    String medPharmNum;
    ArrayList results = new ArrayList<MedicationsDataObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_medications);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_medications);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyMedicationRecyclerViewAdapter(results);
        mRecyclerView.setAdapter(mAdapter);

        //Database Stuff

        //CursorInstantiation (VACCINATIONSs)
        Cursor cursorMed = MainActivity.myDB.readMedData();

//        LinearLayout parent = findViewById(R.id.allergiesParent);
        ImageButton home = findViewById(R.id.Home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

        // ALLERGY BOX UPDATE

        // Check for values (ALLERGIES)
        if (cursorMed != null) {
            cursorMed.moveToFirst();
        }

        // iterate
        while (!cursorMed.isAfterLast()) {

            this.medName = cursorMed.getString(0);
            this.medDose = cursorMed.getString(1);
            this.medDelivery = cursorMed.getString(2);
            this.medRxNum = cursorMed.getString(3);
            this.medPharmName = cursorMed.getString(4);
            this.medPharmNum = cursorMed.getString(5);


            // creates a data object to hold the card's contents
            MedicationsDataObject newData = new MedicationsDataObject(medName, medDose, medDelivery, medRxNum,
                    medPharmName, medPharmNum);

            results.add(newData);

            cursorMed.moveToNext();

        }

    }




    @Override
    protected void onResume() {
        super.onResume();
    }

    //CREATE A NEW CARD OBJECT
     private ArrayList<MedicationsDataObject> getDataSet() {
        return results;
    }

    //Button Functionality
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
        Intent intent = new Intent (this, SelectMedicationActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
