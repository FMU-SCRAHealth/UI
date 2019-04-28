/*
    Author: Matt Harrington
    Email Address: matt2harrington@gmail.com
    Written: October 25th, 2018
    Last Updated: October 25th, 2018

    Compilation:
    Execution:

    Description of Execution:
 */
package com.example.pmarl.peedeehealthadvisor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SelectAllergiesActivity extends AppCompatActivity
{
    private ImageButton home;
    private Button graph;
    private Button enterData;


    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.select_allergy_activity);

        this.home = (ImageButton) findViewById(R.id.Home);

        this.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

        this.graph = (Button) findViewById(R.id.ViewAllergyData);

        this.graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchGraphActivity();
            }
        });

        this.enterData = (Button) findViewById(R.id.EnterAllergyData);

        this.enterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSelectDataActivity();
            }
        });
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
        Intent intent = new Intent (this, AppSwitcher.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }
    private void showDataError()
    {

        Toast toast = Toast.makeText(getApplicationContext(), "There are no values to display.", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.ic_error);
        toastContentView.addView(imageView, 0);
        toast.show();
    }

    private void launchGraphActivity()
    {

        Intent intent = new Intent(this, AllergiesTable.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        Cursor cursor = MainActivity.myDB.readAllergyRecords();
        if(cursor.getCount()==0)
        {
            showDataError();
        }
        else{
            startActivity(intent);
            finish();
        }

    }

    private void launchSelectDataActivity()
    {
        Intent intent = new Intent(this, EnterAllergyDataActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}