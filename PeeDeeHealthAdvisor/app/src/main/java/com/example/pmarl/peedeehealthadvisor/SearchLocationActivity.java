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
    //List of the instantiated attributes
    private ImageButton home;               //Home Button
    private Spinner Spinner;                //Spinner for the distance radius
    private CheckBox nearBy;                //Near By check box
    private CheckBox city;                  //City Check box
    private CheckBox ZIP;                   //ZIP Code check box
    private TextInputLayout cityInpute;     //city input text field
    private TextInputLayout ZIPInpute;      //ZIP input text field

    @Override
    //This is the onCreate method or Min method that is activated when the
    //class is launched
    protected void  onCreate(Bundle saveInstanceState)
    {

        super.onCreate(saveInstanceState);

        //Sets the layout to the activity_search_location layout
        setContentView(R.layout.activity_search_location);

        //Connects the city check box in the java class to the check box located on the screen
        this.city = (CheckBox) findViewById(R.id.CityCheckBox);
        //sets an on-click listener for the city check box
        city.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                /*When the check box is checked the the Zip checkbox and the
                * near by check box disappear and the city input text field appears*/
                if(city.isChecked())
                {
                    ZIP.setVisibility(View.INVISIBLE);

                    nearBy.setVisibility(View.INVISIBLE);

                    cityInpute.setVisibility(View.VISIBLE);
                }

                /*if the city check box is unchecked then the zip check box and the
                * near by check box appears and the city input text field disappears.*/
                else if(city.isChecked() == false)
                {
                    ZIP.setVisibility(View.VISIBLE);

                    nearBy.setVisibility(View.VISIBLE);

                    cityInpute.setVisibility(View.INVISIBLE);
                }
            }
        });

        /*This is connecting the ZIP check box to the Zip check box that is on the screen*/
        this.ZIP = (CheckBox) findViewById(R.id.ZIPcheckBox);
        //Setting an on click listener for the check box to detect if the check box is checked or not.
        ZIP.setOnClickListener(new View.OnClickListener()
        {
            /*Code for the check box if it is checked or unchecked*/
            @Override
            public void onClick(View view)
            {

                /*When the check box is checked then the city and the near by check box are set to be invisible
                * and the Zip iinput text field is set to be seen*/
                if(ZIP.isChecked())
                {
                    city.setVisibility(View.INVISIBLE);

                    nearBy.setVisibility(View.INVISIBLE);

                    ZIPInpute.setVisibility(View.VISIBLE);

                }

                /*if the ZIP check box is set to be unchecked then the city and the near by check box are
                * set to be visible and the zip input text field are set to be invisible*/
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


        Spinner =  findViewById(R.id.milesSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.distances,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner.setAdapter(adapter);
        Spinner.setOnItemSelectedListener(this);
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
