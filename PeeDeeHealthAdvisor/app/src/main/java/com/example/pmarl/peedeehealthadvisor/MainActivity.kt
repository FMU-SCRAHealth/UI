/**
    Author: Patrick Marlowe
    Email Address: pmarlowe782@gmail.com
    Written: June 22, 2018
    Last Updated: June 25, 2018

    Compilation: MainActivity.kt
    Execution: activity_main.xml

    Description of Execution:
    This Class brings up the Home Screen in the App
 */
package com.example.pmarl.peedeehealthadvisor

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity()
{


    override fun onCreate(savedInstanceState: Bundle?)
    {
        // Instantiates the main activity on open
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get reference to button
        val myHealthData = findViewById(R.id.MyHealthData) as Button

        // Set on-click listener
        myHealthData.setOnClickListener {
            //Handler Code
            val intent = Intent(this, MyHealthDataActivity::class.java)
            startActivity(intent)
        }

        // get reference to button
        val healthResources = findViewById(R.id.HealthResources) as Button

        // Set on-click listener
        healthResources.setOnClickListener{
            val intent = Intent(this, SearchServiceActivity::class.java)
            startActivity(intent)
        }
    }
}
