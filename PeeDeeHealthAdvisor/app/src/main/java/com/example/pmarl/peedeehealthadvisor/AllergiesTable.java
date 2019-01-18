package com.example.pmarl.peedeehealthadvisor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
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
        TextView allergyDescriptionView = findViewById(R.id.allergies_descriptions);
//        LinearLayout parent = findViewById(R.id.allergiesParent);
        ImageButton home = findViewById(R.id.Home);
//        TableLayout tableLayout = findViewById(R.id.allergiesTable);

        // ALLERGY BOX UPDATE

        // Check for values (ALLERGIES)
        if (cursorAllergies != null) {
            cursorAllergies.moveToFirst();
        }

        // iterate
        while (!cursorAllergies.isAfterLast()) {

            allergyTitle = cursorAllergies.getString(0);
            allergyDescription = cursorAllergies.getString(1);

//            if(allergyName.getParent()!=null) {
//                ((ViewGroup) allergyName.getParent()).removeView(allergyName); // <- fix
//                tableLayout.addView(allergyName);
//            }
//
//            if(allergyDescriptionView.getParent()!=null) {
//                ((ViewGroup) allergyDescriptionView.getParent()).removeView(allergyDescriptionView); // <- fix
//            tableLayout.addView(allergyDescriptionView);
//            }

                allergyName.setText(allergyName.getText()  + "\n\n\n" + allergyTitle);
                allergyDescriptionView.setText(allergyDescriptionView.getText() + "\n\n\n" + allergyDescription);

//                tableLayout.addView(allergyName);
//                tableLayout.addView(allergyDescriptionView);

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
        Intent intent = new Intent (this, AppSwitcher.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }



}