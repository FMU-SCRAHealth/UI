/**
    Author: Patrick Marlowe
    Email Address: pmarlowe782@gmail.com
    Written: June 22, 2018
    Last Updated: June 25, 2018

    Compilation: EnterCholesterolDataActivity.java
    Execution: activity_enter_cholesterol_data.xml

    Description of Execution:
    On executing the class creates the activity for the entering of cholesterol data
 */
package com.example.pmarl.peedeehealthadvisor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class EnterCholesterolDataActivity extends AppCompatActivity
{
    private ImageButton home;
    private ImageButton backBtn;


    /*************************************************************************************
     * This method creates the enter cholesterol activity and makes connections to the
     * different classes in the .xml layout that was created.
     *************************************************************************************/
    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_enter_cholesterol_data);


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
                launchprevActivity();
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

    private void launchprevActivity()
    {
        Intent intent = new Intent (this, SelectDataActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
