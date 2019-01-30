package com.example.pmarl.peedeehealthadvisor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MedicationTable extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";

    String medName = "";
    ArrayList results = new ArrayList<DataObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(results);
        mRecyclerView.setAdapter(mAdapter);

        //Database Stuff

        //CursorInstantiation (VACCINATIONSs)
        Cursor cursorMed = MainActivity.myDB.readMedData();

//        LinearLayout parent = findViewById(R.id.allergiesParent);
        ImageButton home = findViewById(R.id.Home);

        // ALLERGY BOX UPDATE

        // Check for values (ALLERGIES)
        if (cursorMed != null) {
            cursorMed.moveToFirst();
        }

        // iterate
        while (!cursorMed.isAfterLast()) {

            this.medName = cursorMed.getString(0);

            DataObject newData = new DataObject(medName);

            results.add(newData);

            cursorMed.moveToNext();

        }
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    //CREATE A NEW CARD OBJECT
     private ArrayList<DataObject> getDataSet() {
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
