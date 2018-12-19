/*
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

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    public static DatabaseHelper myDB;
    private NotificationManagerCompat notificationManager;



    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);

        /*First time open code*/
        SharedPreferences app_preferences = getApplicationContext()
                .getSharedPreferences("MyPref",0);
        SharedPreferences.Editor editor = app_preferences.edit();

        boolean firstStart = app_preferences.getBoolean("first_time_start", true);

        /*If statement to see if the app has been open before or not*/
        if(firstStart)
        {

            /*If the app hasn't been opened before, it runs the following code
            * and sets firstStart to false*/
            editor.putBoolean("first_time_start",false);
            editor.apply();

            //do your one time code here
            launchFirstTimeLogIn();
            //Toast.makeText(this,"This is first app run!", Toast.LENGTH_LONG).show();

        }



        boolean firstStartSlider = app_preferences.getBoolean("first_time_slider", true);

        /*If statement to see if the app has been open before or not*/
        if(firstStartSlider)
        {

            /*If the app hasn't been opened before, it runs the following code
             * and sets firstStart to false*/
            editor.putBoolean("first_time_slider",false);
            editor.commit();

            //do your one time code here
            launchSliderScreen();


        }


        Button myHealthData =  findViewById(R.id.MyHealthData);

        myHealthData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMyHealthData();
            }
        });

        Button myHealthResources = findViewById(R.id.HealthResources);


        myHealthResources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSearchServiceActivity();
            }
        });

        createNotificationChannels(); // this creates the notification channels on creation
        notificationManager = NotificationManagerCompat.from(this);



    }

    @Override
    public void onBackPressed() {

    }

    private void launchMyHealthData()
    {
        Intent intent = new Intent (this, SelectDataActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void launchSearchServiceActivity()
    {
        Intent intent = new Intent (this, SearchServiceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void launchFirstTimeLogIn()
    {
        Intent intent = new Intent(this, FirstTimeLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void launchSliderScreen()
    {
        Intent intent = new Intent(this, SliderStart.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    "CHANNEL_1_ID",
                    "Blood Pressure Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel1.setDescription("Blood Pressure Notifications");


            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(channel1);


            NotificationChannel channel2 = new NotificationChannel(
                    "CHANNEL_2_ID",
                    "Blood Sugar Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel1.setDescription("Blood Sugar Notifications");

            manager.createNotificationChannel(channel2);


            NotificationChannel channel3 = new NotificationChannel(
                    "CHANNEL_3_ID",
                    "Cholesterol Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel1.setDescription("Cholesterol Notifications");

            manager.createNotificationChannel(channel3);

            NotificationChannel channel4 = new NotificationChannel(
                    "CHANNEL_4_ID",
                    "Vaccination Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel1.setDescription("Vaccinations Notifications");

            manager.createNotificationChannel(channel4);


        }
    }




}
