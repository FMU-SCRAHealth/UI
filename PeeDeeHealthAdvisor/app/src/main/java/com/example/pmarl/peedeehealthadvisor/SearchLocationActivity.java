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
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchLocationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private ImageButton home;
    private ImageButton backBtn;
    private Spinner Spinner;
    private CheckBox nearBy;
    private CheckBox city;
    private CheckBox ZIP;
    private TextInputLayout cityInpute;
    private TextInputLayout ZIPInpute;

    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_search_location);

        this.city = (CheckBox) findViewById(R.id.CityCheckBox);
        city.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(city.isChecked())
                {
                    ZIP.setVisibility(View.INVISIBLE);

                    nearBy.setVisibility(View.INVISIBLE);

                    cityInpute.setVisibility(View.VISIBLE);
                }
                else if(city.isChecked() == false)
                {
                    ZIP.setVisibility(View.VISIBLE);

                    nearBy.setVisibility(View.VISIBLE);

                    cityInpute.setVisibility(View.INVISIBLE);
                }
            }
        });

        this.ZIP = (CheckBox) findViewById(R.id.ZIPcheckBox);
        ZIP.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(ZIP.isChecked())
                {
                    city.setVisibility(View.INVISIBLE);

                    nearBy.setVisibility(View.INVISIBLE);

                    ZIPInpute.setVisibility(View.VISIBLE);

                }
                else if(ZIP.isChecked() == false)
                {
                    city.setVisibility(View.VISIBLE);

                    nearBy.setVisibility(View.VISIBLE);

                    ZIPInpute.setVisibility(View.INVISIBLE);
                }
            }
        });

        this.cityInpute = (TextInputLayout) findViewById(R.id.CityInput);

        this.ZIPInpute = (TextInputLayout) findViewById(R.id.ZIPinpute);


        this.nearBy = (CheckBox) findViewById(R.id.nearByCheckBox);
        nearBy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(nearBy.isChecked())
                {
                    city.setVisibility(View.INVISIBLE);

                    ZIP.setVisibility(View.INVISIBLE);

                    Spinner.setVisibility(View.VISIBLE);
                }
                else if(nearBy.isChecked() == false)
                {
                    Spinner.setVisibility(View.INVISIBLE);

                    city.setVisibility(View.VISIBLE);

                    ZIP.setVisibility(View.VISIBLE);
                }
            }
        });

        this.home = (ImageButton) findViewById(R.id.Home);
        this.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });


        this.backBtn = (ImageButton) findViewById(R.id.BackBtn);
        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchPrevActivity();
            }
        });



        Spinner =  findViewById(R.id.milesSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.distances,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner.setAdapter(adapter);
        Spinner.setOnItemSelectedListener(this);
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
        Intent intent = new Intent(this, SearchServiceActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l)
    {
        if(position != 0)
        {
            String text = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
