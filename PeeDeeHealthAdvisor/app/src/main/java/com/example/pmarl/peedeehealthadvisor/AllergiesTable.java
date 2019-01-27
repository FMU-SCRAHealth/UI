package com.example.pmarl.peedeehealthadvisor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AllergiesTable extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_allergies);

        //CursorInstantiation (VACCINATIONSs)
        Cursor cursorAllergies = MainActivity.myDB.readAllergyRecords();
        String allergyTitle = "";
        String allergyDescription = "";
        TextView allergyName = findViewById(R.id.allergies_name);
        TextView allergyDescriptionView = findViewById(R.id.allergies_reactions);
//        LinearLayout parent = findViewById(R.id.allergiesParent);
        ImageButton home = findViewById(R.id.Home);
        TableLayout tableLayout = findViewById(R.id.allergiesTable);

        // ALLERGY BOX UPDATE

        // Check for values (ALLERGIES)
        if (cursorAllergies != null) {
            cursorAllergies.moveToFirst();
        }

        // iterate
        while (!cursorAllergies.isAfterLast()) {

            allergyTitle = cursorAllergies.getString(0);
            allergyDescription = cursorAllergies.getString(1);


            TableLayout table = (TableLayout)AllergiesTable.this.findViewById(R.id.allergiesTable);

                // Inflate your row "template" and fill out the fields.
                TableRow row = (TableRow)LayoutInflater.from(AllergiesTable.this).inflate(R.layout.allergies_row, null);
                ((TextView)row.findViewById(R.id.attrib_name)).setText(allergyTitle);
                ((TextView)row.findViewById(R.id.attrib_value)).setText(allergyDescription);
                table.addView(row);

            table.requestLayout();
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