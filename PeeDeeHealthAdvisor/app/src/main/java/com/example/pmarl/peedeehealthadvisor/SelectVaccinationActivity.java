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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SelectVaccinationActivity extends AppCompatActivity
{
    private ImageButton home;
    private Button graph;
    private Button enterData;


    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.select_vaccination_activity);

        this.home = (ImageButton) findViewById(R.id.Home);

        this.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

        this.graph = (Button) findViewById(R.id.ViewData);

        this.graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchGraphActivity();
            }
        });

        this.enterData = (Button) findViewById(R.id.EnterData);

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

    private void launchGraphActivity()
    {
        Intent intent = new Intent(this, VaccinationGraph.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void launchSelectDataActivity()
    {
        Intent intent = new Intent(this, EnterVaccinationDataActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}