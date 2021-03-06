/*
    Author: Patrick Marlowe
    Email Address: pmarlowe782@gmail.com
    Written: June 22, 2018
    Last Updated: June 25, 2018

    Compilation:
    Execution:

    Description of Execution:
 */
package com.fmu.pmarl.peedeehealthadvisor;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class UserProfile extends AppCompatActivity
{
    //List of the instantiated attributes
    private ImageButton home;               //Home Button


    @Override
    //This is the onCreate method or Min method that is activated when the
    //class is launched
    protected void  onCreate(Bundle saveInstanceState)
    {

        super.onCreate(saveInstanceState);

        //Sets the layout to the activity_search_location layout
        setContentView(R.layout.activity_user_profile);

        //CursorInstantiation (USER)
        Cursor cursorUser = MainActivity.myDB.readUserData();
        String userName = "";
        String userAge = "";
        String userGender = "";
        TextView textBoxUsername = findViewById(R.id.user_name);
        TextView textBoxGender = findViewById(R.id.gender);
        TextView textBoxAge = findViewById(R.id.date_of_birth);
        Button editUser = findViewById(R.id.editUserButton);
        TextView privacyPolicy = findViewById(R.id.privacyPolicyText);

        this.home = (ImageButton) findViewById(R.id.Home);
        this.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

    // Check for values (USER)
        if (cursorUser != null) {
            cursorUser.moveToLast();
        }

        do {

            userName = cursorUser.getString(0);
            userAge = cursorUser.getString(1);
            userGender = cursorUser.getString(2);

            try {

                Date dateFormatted = new SimpleDateFormat("MM.dd.yyyy").parse(userAge);
                String formattedDate = new SimpleDateFormat(" MMM dd yyyy").format(dateFormatted);

                textBoxUsername.setText(userName);
                textBoxGender.setText(userGender);
                textBoxAge.setText(formattedDate);

            } catch (Exception e) {

            }

        } while (cursorUser.moveToNext());




        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchEditUserActivity();
            }
        });

        privacyPolicy.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view)
            {
                Intent browser= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.schealthplusme.com/privacy"));
                startActivity(browser);
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
        Intent intent = new Intent(this, AppSwitcher.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void launchEditUserActivity()
    {
        Intent intent = new Intent(this, EditUserProfile.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}