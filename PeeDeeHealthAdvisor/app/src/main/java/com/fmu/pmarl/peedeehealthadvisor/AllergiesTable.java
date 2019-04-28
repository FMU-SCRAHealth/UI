package com.fmu.pmarl.peedeehealthadvisor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class AllergiesTable extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList results = new ArrayList<AllergiesDataObject>();

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_card_view_allergies);


        //CursorInstantiation (VACCINATIONSs)
        Cursor cursorAllergies = MainActivity.myDB.readAllergyRecords();
        String allergyTitle = "No Allergies";
        String allergyDescription = "No Allergies";
//        TextView allergyName = findViewById(R.id.allergies_name);
//        TextView allergyDescriptionView = findViewById(R.id.allergies_reactions);
        ImageButton home = findViewById(R.id.Home);
//        TableLayout tableLayout = findViewById(R.id.allergiesTable);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_allergies);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAllergiesRecyclerViewAdapter(results); // make sure to change this up copied
        mRecyclerView.setAdapter(mAdapter);

        // ALLERGY BOX UPDATE

        // Check for values (ALLERGIES)
        if (cursorAllergies != null) {
            cursorAllergies.moveToFirst();
        }

        // iterate
        while (!cursorAllergies.isAfterLast()) {

            allergyTitle = cursorAllergies.getString(0);
            allergyDescription = cursorAllergies.getString(1);


//            TableLayout table = (TableLayout)AllergiesTable.this.findViewById(R.id.allergiesTable);
//
//                // Inflate your row "template" and fill out the fields.
//                TableRow row = (TableRow)LayoutInflater.from(AllergiesTable.this).inflate(R.layout.allergies_row, null);
//                ((TextView)row.findViewById(R.id.attrib_name)).setText(allergyTitle);
//                ((TextView)row.findViewById(R.id.attrib_value)).setText(allergyDescription);
//                table.addView(row);
//
//            table.requestLayout();

            AllergiesDataObject allergyEntry = new AllergiesDataObject(allergyTitle, allergyDescription);

            results.add(allergyEntry);

            cursorAllergies.moveToNext();
        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

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
        Intent intent = new Intent (this, SelectAllergiesActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }



}