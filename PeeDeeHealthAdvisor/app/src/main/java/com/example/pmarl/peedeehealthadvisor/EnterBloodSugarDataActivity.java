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


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EnterBloodSugarDataActivity extends AppCompatActivity
{
    private Button clearData, enterData;
    private TextInputEditText bloodSugarInput;
    private ImageButton home;
    private RadioButton fastingToggle, nonfastingToggle;
    private Context context = this;
    private EditText editDate;
    private Calendar myCalendar = Calendar.getInstance();
    private String dateFormat = "MM.dd.yyyy";
    private DatePickerDialog.OnDateSetListener date;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);




     @Override
    protected void  onCreate(Bundle saveInstanceState)
     {
         super.onCreate(saveInstanceState);
         setContentView(R.layout.acativty_enter_blood_sugar_data);

         editDate = (EditText) findViewById(R.id.editDate);
         clearData = (Button) findViewById(R.id.clearData);
         enterData = (Button) findViewById(R.id.enterData);
         bloodSugarInput = (TextInputEditText) findViewById(R.id.bloodSugarInput);
         fastingToggle = (RadioButton) findViewById((R.id.fastingToggle));
         nonfastingToggle = (RadioButton) findViewById(R.id.nonfastingToggle);

         fastingToggle.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View view) {
                 if (fastingToggle.isChecked()) {nonfastingToggle.setChecked(false);}
                 else if(fastingToggle.isChecked()==false) {nonfastingToggle.setChecked(true);}
             }
         });

         //set date to current date
         long currentdate = System.currentTimeMillis();
         String dateString = simpleDateFormat.format(currentdate);
         editDate.setText(dateString);

         //set calendar date and update editDate
         date = new DatePickerDialog.OnDateSetListener()
         {
             @Override
             public void onDateSet(DatePicker datePicker, int year, int month, int dayOfmonth)
             {
                 myCalendar.set(Calendar.YEAR, year);
                 myCalendar.set(Calendar.MONTH, month);
                 myCalendar.set(Calendar.DAY_OF_MONTH, dayOfmonth);
                 updateDate();
             }
         };

         //Onclick popup datepicker
         editDate.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View view)
             {
                 new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR),
                         myCalendar.get(Calendar.MONTH),
                         myCalendar.get(Calendar.DAY_OF_MONTH)).show();
             }
         });

         home = (ImageButton) findViewById(R.id.Home);

         home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

         enterData.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View view)
             {

                 if(bloodSugarInput.getText().toString().equals(""))
                 {
                     Toast.makeText(EnterBloodSugarDataActivity.this,
                             "Please enter a valid blood sugar level.", Toast.LENGTH_LONG).show();
                 }


                 else {
                     boolean isInserted = MainActivity.myDB.insertBloodSugar(editDate.getText().toString()
                             , Integer.parseInt((fastingToggle.isChecked() ? "1" : "0")),
                             Integer.parseInt(bloodSugarInput.getText().toString()));

                     if (isInserted = true)
                         Toast.makeText(EnterBloodSugarDataActivity.this, "Blood Sugar Saved",
                                 Toast.LENGTH_LONG).show();
                     else
                         Toast.makeText(EnterBloodSugarDataActivity.this, "Blood Sugar NOT Saved",
                                 Toast.LENGTH_LONG).show();


                     launchPrevActivity();
                 }
             }
         });

         clearData.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View view)
             {
                bloodSugarInput.setText("");
                editDate.setText("");
                fastingToggle.setChecked(true);
                nonfastingToggle.setChecked(false);

             }
         });

     }


     private void updateDate()
     {
         editDate.setText(simpleDateFormat.format(myCalendar.getTime()));
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
        Intent intent = new Intent (this, SelectBloodSugarActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    // method for the radio buttons for fasting and non fasting
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.fastingToggle:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.nonfastingToggle:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }
}
