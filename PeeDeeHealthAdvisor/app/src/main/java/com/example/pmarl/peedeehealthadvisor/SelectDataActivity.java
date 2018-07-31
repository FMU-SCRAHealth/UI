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
import android.widget.Button;
import android.widget.ImageButton;

public class SelectDataActivity extends AppCompatActivity
{

    private ImageButton home;
    private ImageButton backBtn;
    private Button enterBloodPressure;
    private Button enterBloodSugar;
    private Button enterCholesterol;
    private Button enterVaccinationData;


    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_select_data);

        home = (ImageButton) findViewById(R.id.Home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

        backBtn = (ImageButton) findViewById(R.id.BackBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchPrevActivity();
            }
        });

        enterBloodPressure = (Button) findViewById(R.id.EnterBloodPressure);

        enterBloodPressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchEnterBloodPressureActivity();
            }
        });

        enterBloodSugar = (Button) findViewById(R.id.EnterBloodSugar);

        enterBloodSugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchEnterBloodSugarActivity();
            }
        });

        enterCholesterol = (Button) findViewById(R.id.EnterCholesterol);

        enterCholesterol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchEnterCholesterolActivity();
            }
        });

        enterVaccinationData = (Button) findViewById(R.id.EnterVaccinationData);

        enterVaccinationData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchEnterVaccinationDataActivity();
            }
        });
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
        Intent intent = new Intent (this, MyHealthDataActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void launchEnterBloodPressureActivity()
    {
        Intent intent = new Intent (this, EnterBloodPressureDataActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void launchEnterBloodSugarActivity()
    {
        Intent intent = new Intent (this, EnterBloodSugarDataActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void launchEnterCholesterolActivity()
    {
        Intent intent = new Intent (this, EnterCholesterolDataActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void launchEnterVaccinationDataActivity()
    {
        Intent intent = new Intent (this, EnterVaccinationDataActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
