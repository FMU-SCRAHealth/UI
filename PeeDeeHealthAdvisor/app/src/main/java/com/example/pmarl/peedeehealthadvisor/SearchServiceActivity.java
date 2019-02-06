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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchServiceActivity extends AppCompatActivity
{
    private ImageButton home;
    private Button search;
    private ImageButton bloodPressureSearch;
    boolean clicked = true;


    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_search_service);

        this.home = (ImageButton) findViewById(R.id.Home);

        this.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

        this.search = (Button) findViewById(R.id.Search);

        this.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSearchActivity();
            }
        });

        this.bloodPressureSearch = (ImageButton) findViewById(R.id.bloodPressureTEST1);

        this.bloodPressureSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                clicked = true;
                if (clicked) {
                    bloodPressureSearch.setBackgroundResource(R.drawable.ic_blood_pressure_checked);
                    // make method for query call it here
                    clicked = false;
                }
                else if (!clicked) {
                    bloodPressureSearch.setBackgroundResource(R.drawable.ic_blood_pressure);
                    clicked = true;
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        launchMainActivity();
    }

    private void launchMainActivity()
    {
        Intent intent = new Intent (this, MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void launchSearchActivity()
    {
        Intent intent = new Intent (this, SearchLocationActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}
