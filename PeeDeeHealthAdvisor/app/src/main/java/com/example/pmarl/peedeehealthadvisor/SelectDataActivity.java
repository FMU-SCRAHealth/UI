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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class SelectDataActivity extends AppCompatActivity
{

    private ImageButton home;
    private ImageButton enterBloodPressure;
    private ImageButton enterBloodSugar;
    private ImageButton enterCholesterol;
    private ImageButton enterVaccinationData;
    private RecyclerView.LayoutManager layoutManager;
    private List<View> listData = new ArrayList<>();


    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_select_data);

        setupList();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        layoutManager =  new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(listData, this);
        recyclerView.setAdapter(adapter);


//
        home = (ImageButton) findViewById(R.id.Home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

//        enterBloodPressure = (ImageButton) findViewById(R.id.EnterBloodPressure);
//
//        enterBloodPressure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                launchEnterBloodPressureActivity();
//            }
//        });
//
//        enterBloodSugar = (ImageButton) findViewById(R.id.EnterBloodSugar);
//
//        enterBloodSugar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                launchEnterBloodSugarActivity();
//            }
//        });
//
//        enterCholesterol = (ImageButton) findViewById(R.id.EnterCholesterol);
//
//        enterCholesterol.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                launchEnterCholesterolActivity();
//            }
//        });
//
//        enterVaccinationData = (ImageButton) findViewById(R.id.EnterVaccinationData);
//
//        enterVaccinationData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                launchEnterVaccinationDataActivity();
//            }
//        });
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
        Intent intent = new Intent (this, MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void launchEnterBloodPressureActivity()
    {
        Intent intent = new Intent (this, SelectBloodPressureActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void launchEnterBloodSugarActivity()
    {
        Intent intent = new Intent (this, SelectBloodSugarActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void launchEnterCholesterolActivity()
    {
        Intent intent = new Intent (this, SelectCholesterolActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void launchEnterVaccinationDataActivity()
    {
        Intent intent = new Intent (this, SelectVaccinationActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void setupList() {
        listData.add(findViewById(R.layout.card_view));
        listData.add(findViewById(R.layout.card_view_2));
        listData.add(findViewById(R.layout.card_view_3));
    }
}
