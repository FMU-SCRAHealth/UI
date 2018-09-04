/**
 Author: Patrick Marlowe
 Email Address: pmarlowe782@gmail.com
 Written: August 30, 2018
 Last Updated:

 Compilation: MainActivity.java
 Execution: activity_main.xml

 Description of Execution:
 This Class brings up the Home Screen in the App
 */
package com.example.pmarl.peedeehealthadvisor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private Button MyHealthData;
    private Button  MyHealthResources;
    private boolean firstStart;

    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        /*First time open code*/
        SharedPreferences app_preferences = getApplicationContext()
                .getSharedPreferences("MyPref",0);
        SharedPreferences.Editor editor = app_preferences.edit();

        firstStart = app_preferences.getBoolean("first_time_start",true);

        /*If statement to see if the app has been open before or not*/
        if(firstStart)
        {

            /*If the app hasn't been opened before, it runs the following code
            * and sets firstStart to false*/
            editor.putBoolean("first_time_start",false);
            editor.commit();

            //do your one time code here
            launchFirstTimeLogIn();
            //Toast.makeText(this,"This is first app run!", Toast.LENGTH_LONG).show();

        }
        else
        {
            //app open directly
            Toast.makeText(this, "Welcome!!!", Toast.LENGTH_LONG).show();
        }




        this.MyHealthData = (Button) findViewById(R.id.MyHealthData);

        this.MyHealthData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMyHealthData();
            }
        });

        this.MyHealthResources = (Button) findViewById(R.id.HealthResources);



        this.MyHealthResources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSearchServiceActivity();
            }
        });

    }


    private void launchMyHealthData()
    {
        Intent intent = new Intent (this, MyHealthDataActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void launchSearchServiceActivity()
    {
        Intent intent = new Intent (this, SearchServiceActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void launchFirstTimeLogIn()
    {
        Intent intent = new Intent(this, FirstTimeLogin.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
