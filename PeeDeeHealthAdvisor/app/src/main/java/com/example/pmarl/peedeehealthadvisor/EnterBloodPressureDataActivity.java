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

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EnterBloodPressureDataActivity extends AppCompatActivity //github test
{
    private Button enterData, clearData;

    private ImageButton home;

    private TextInputEditText systolicInput, diastolicInput;

    private Context context = this;
    private EditText editDate;
    private Calendar myCalendar = Calendar.getInstance();
    private String dateFormat = "MMM dd yyyy ";
    private DatePickerDialog.OnDateSetListener date;
    private SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_enter_blood_pressure_data);

        editDate = (EditText) findViewById(R.id.editDate);
        home = (ImageButton) findViewById(R.id.Home);
        enterData = (Button) findViewById(R.id.EnterData);
        clearData = (Button) findViewById(R.id.ClearData);
        systolicInput = (TextInputEditText) findViewById(R.id.systolicInput);
        diastolicInput = (TextInputEditText) findViewById(R.id.diastolicInput);


        // init - set date to current date
        long currentdate = System.currentTimeMillis();
        String dateString = sdf.format(currentdate);
        editDate.setText(dateString);

        // set calendar date and update editDate
        date = new DatePickerDialog.OnDateSetListener()
        {

            @Override
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

                if(systolicInput.getText().toString().equals("")||diastolicInput.getText().toString().equals(""))
                {
//                    Toast.makeText(EnterBloodPressureDataActivity.this,
//                            "Please enter all fields.",Toast.LENGTH_LONG).show();
                    showDataNotEnteredWarning();
                }
//                else if(systolicInput.getText().toString()=="")
//                {
//                    Toast.makeText(EnterBloodPressureDataActivity.this,
//                            "Please enter a valid systolic input.", Toast.LENGTH_LONG).show();
//                }
//                else if(diastolicInput.getText().toString()=="")
//                {
//                    Toast.makeText(EnterBloodPressureDataActivity.this,
//                            "Please enter a valid diastolic input.", Toast.LENGTH_LONG).show();
//                }

                else
                {
                    boolean isInserted = MainActivity.myDB.insertBloodPressure(editDate.getText().toString(), Integer.parseInt(systolicInput.getText().toString()),
                            Integer.parseInt(diastolicInput.getText().toString()));

                    if (isInserted = true)
//                        Toast.makeText(EnterBloodPressureDataActivity.this, "Blood Pressure Saved",
//                                Toast.LENGTH_LONG).show();
                        showDataEntryCheckmark();
                    else
//                        Toast.makeText(EnterBloodPressureDataActivity.this, "Blood Pressure NOT Saved",
//                                Toast.LENGTH_LONG).show();
                        showDataError();

                    launchPrevActivity();
                }
            }
        });

        clearData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                systolicInput.setText("");
                diastolicInput.setText("");
                editDate.setText("");
            }
        });

        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.bloodPressureEntryLayout);
        mainLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                InputMethodManager inputMethodManager = (InputMethodManager)  EnterBloodPressureDataActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(EnterBloodPressureDataActivity.this.getCurrentFocus().getWindowToken(), 0);
                return false;
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
        Intent intent = new Intent (this, SelectBloodPressureActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Show images in Toast prompt.
    private void showDataEntryCheckmark()
    {

        Toast toast = Toast.makeText(getApplicationContext(), "Blood Pressure Entered", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.ic_checkmark);
        toastContentView.addView(imageView, 0);
        toast.show();

    }

    // Show images in Toast prompt.
    private void showDataNotEnteredWarning()
    {

        Toast toast = Toast.makeText(getApplicationContext(), "Please Complete All Fields", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.ic_warning);
        toastContentView.addView(imageView, 0);
        toast.show();
    }

    // Show images in Toast prompt.
    private void showDataError()
    {

        Toast toast = Toast.makeText(getApplicationContext(), "ERROR: Please Try Again", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.ic_error);
        toastContentView.addView(imageView, 0);
        toast.show();
    }
}
