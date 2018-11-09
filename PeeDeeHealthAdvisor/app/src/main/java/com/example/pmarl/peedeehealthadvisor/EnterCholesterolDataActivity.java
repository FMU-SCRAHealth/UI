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

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EnterCholesterolDataActivity extends AppCompatActivity
{
    private Button enterData, clearData;
    private TextInputEditText hdlInput, ldlInput, tcInput;


    private ImageButton home;
    private Context context = this;
    private EditText editDate;
    private Calendar myCalendar = Calendar.getInstance();
    private String dateFormat = "MM.dd.yy";
    private DatePickerDialog.OnDateSetListener date;
    private SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);



    /*************************************************************************************
     * This method creates the enter cholesterol activity and makes connections to the
     * different classes in the .xml layout that was created.
     *************************************************************************************/
    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_enter_cholesterol_data);

        editDate = (EditText) findViewById(R.id.editDate);
        enterData = (Button) findViewById(R.id.enterData);
        clearData = (Button) findViewById(R.id.clearData);
        hdlInput = (TextInputEditText) findViewById(R.id.hdlInput);
        ldlInput = (TextInputEditText) findViewById(R.id.ldlInput);
        tcInput = (TextInputEditText) findViewById(R.id.tcInput);

        // init - set date to current date
        long currentdate = System.currentTimeMillis();
        String dateString = sdf.format(currentdate);
        editDate.setText(dateString);

        // set calendar date and update editDate
        date = new DatePickerDialog.OnDateSetListener()
        {


            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth)
            {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };

        // onclick - popup datepicker
        editDate.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
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



        enterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ldlInput.getText().toString().equals("")||hdlInput.getText().toString().equals("")||
                        tcInput.getText().toString().equals(""))
                {
                    Toast.makeText(EnterCholesterolDataActivity.this,
                            "Please enter all fields.", Toast.LENGTH_LONG).show();
                }
//                else if(ldlInput.getText().toString()=="")
//                {
//                    Toast.makeText(EnterCholesterolDataActivity.this,
//                            "Please enter a valid value for LDL.",Toast.LENGTH_LONG).show();
//                }
//                else if(hdlInput.getText().toString()=="")
//                {
//                    Toast.makeText(EnterCholesterolDataActivity.this,
//                            "Please enter a valid value for HDL.",Toast.LENGTH_LONG).show();
//                }
//                else if(tcInput.getText().toString()=="")
//                {
//                    Toast.makeText(EnterCholesterolDataActivity.this,
//                            "Please enter a valid value for TC", Toast.LENGTH_SHORT).show();
//                }

                else
                {
                    boolean isInserted = MainActivity.myDB.insertCholesterol(editDate.getText().toString()
                            , Integer.parseInt(ldlInput.getText().toString())
                            , Integer.parseInt(hdlInput.getText().toString())
                            , Integer.parseInt(tcInput.getText().toString())
                    );
                    if (isInserted = true)
                        Toast.makeText(EnterCholesterolDataActivity.this, "Cholesterol Saved",
                                Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(EnterCholesterolDataActivity.this, "Cholesterol NOT Saved",
                                Toast.LENGTH_LONG).show();
                    launchPrevActivity();
                }
            }
        });

        clearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hdlInput.setText("");
                ldlInput.setText("");
                tcInput.setText("");
                editDate.setText("");
            }
        });

    }

    private void updateDate()
    {
        editDate.setText(sdf.format(myCalendar.getTime()));
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
        Intent intent = new Intent (this, SelectCholesterolActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
