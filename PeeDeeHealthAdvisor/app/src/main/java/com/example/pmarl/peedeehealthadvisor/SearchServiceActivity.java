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

public class SearchServiceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private ImageButton home;
    private Button search;
    private Spinner spinnerRadius;
    private ImageButton bloodPressureSearch;
    private ImageButton bloodSugarSearch;
    private ImageButton cholesterolSearch;
    private ImageButton fluShotSearch;
    private ImageButton ShinglesSearch;
    private ImageButton PneumoniaSearch;
    boolean clickedBP = true;
    boolean clickedBS = true;
    boolean clickedCholesterol = true;
    boolean clickedFlu = true;
    boolean clickedShingles = true;
    boolean clickedPneumonia = true;



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
//                launchSearchActivity();
            }
        });

        spinnerRadius =  findViewById(R.id.radiusSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.distances,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRadius.setAdapter(adapter);
        spinnerRadius.setOnItemSelectedListener(this);

        this.bloodPressureSearch = (ImageButton) findViewById(R.id.bloodPressureTEST1);

        this.bloodPressureSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickedBP) {
                    bloodPressureSearch.setBackgroundResource(R.drawable.ic_blood_pressure_checked);
                    // make method for query call it here
                    clickedBP = false;
                }
                else if (!clickedBP) {
                    bloodPressureSearch.setBackgroundResource(R.drawable.ic_blood_pressure);
                    clickedBP = true;
                }

            }
        });

        this.bloodSugarSearch = (ImageButton) findViewById(R.id.bloodSugarSearch);

        this.bloodSugarSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickedBS) {
                    bloodSugarSearch.setBackgroundResource(R.drawable.ic_blood_sugar_selected);
                    // make method for query call it here
                    clickedBS = false;
                }
                else if (!clickedBS) {
                    bloodSugarSearch.setBackgroundResource(R.drawable.ic_blood_sugar);
                    clickedBS = true;
                }

            }
        });

        this.cholesterolSearch = (ImageButton) findViewById(R.id.cholesterolSearch);

        this.cholesterolSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickedCholesterol) {
                    cholesterolSearch.setBackgroundResource(R.drawable.ic_cholesterol_icon_selected);
                    // make method for query call it here
                    clickedCholesterol = false;
                }
                else if (!clickedCholesterol) {
                    cholesterolSearch.setBackgroundResource(R.drawable.ic_cholesterol_icon);
                    clickedCholesterol = true;
                }

            }
        });

        this.fluShotSearch = (ImageButton) findViewById(R.id.fluShotSearch);

        this.fluShotSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickedFlu) {
                    fluShotSearch.setBackgroundResource(R.drawable.ic_vaccinations_selected);
                    // make method for query call it here
                    clickedFlu = false;
                }
                else if (!clickedFlu) {
                    fluShotSearch.setBackgroundResource(R.drawable.ic_vaccinations);
                    clickedFlu = true;
                }

            }
        });

        this.ShinglesSearch = (ImageButton) findViewById(R.id.shinglesSearch);

        this.ShinglesSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickedShingles) {
                    ShinglesSearch.setBackgroundResource(R.drawable.ic_vaccinations_selected);
                    // make method for query call it here
                    clickedShingles = false;
                }
                else if (!clickedShingles) {
                    ShinglesSearch.setBackgroundResource(R.drawable.ic_vaccinations);
                    clickedShingles = true;
                }

            }
        });

        this.PneumoniaSearch = (ImageButton) findViewById(R.id.pneumoniaSearch);

        this.PneumoniaSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickedPneumonia) {
                    PneumoniaSearch.setBackgroundResource(R.drawable.ic_vaccinations_selected);
                    // make method for query call it here
                    clickedPneumonia = false;
                }
                else if (!clickedPneumonia) {
                    PneumoniaSearch.setBackgroundResource(R.drawable.ic_vaccinations);
                    clickedPneumonia = true;
                }

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l)
    {
        if(position != 0)
        {
            String text = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
