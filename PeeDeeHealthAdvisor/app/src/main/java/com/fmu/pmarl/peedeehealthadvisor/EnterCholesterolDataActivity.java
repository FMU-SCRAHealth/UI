/*
    Author: Patrick Marlowe
    Email Address: pmarlowe782@gmail.com
    Written: June 22, 2018
    Last Updated: June 25, 2018

    Compilation: EnterCholesterolDataActivity.java
    Execution: activity_enter_cholesterol_data.xml

    Description of Execution:
    On executing the class creates the activity for the entering of cholesterol data
 */
package com.fmu.pmarl.peedeehealthadvisor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EnterCholesterolDataActivity extends AppCompatActivity
{
    private TextInputEditText hdlInput, ldlInput, TRIGinput, TCinput;
    private NotificationManagerCompat notificationManager;

    private Context context = this;
    private EditText editDate, editTime;
    private Calendar myCalendar = Calendar.getInstance();
    private String dateFormat = "MMM dd yyyy ";
    private String timeFormat = "HH:mm:ss.SSS zzz";
    private String dateEpoch;
    private Date dateInsertion;
    private Long epoch;
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

        editTime = (EditText) findViewById(R.id.editTime);
        editDate = findViewById(R.id.editDate);
        Button enterData = findViewById(R.id.enterData);
        Button clearData = findViewById(R.id.clearData);
        hdlInput = findViewById(R.id.hdlInput);
        ldlInput = findViewById(R.id.ldlInput);
        TRIGinput = findViewById(R.id.TRIGinput);
        TCinput = findViewById(R.id.TCinputThisOne);
        notificationManager = NotificationManagerCompat.from(this);

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
                new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Edit Time popup
        editTime.setOnClickListener(new View.OnClickListener() {
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            @Override
            public void onClick(View view)
            {
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker,
                                                  int hourOfDay,
                                                  int minuteOfHour) {
                                editTime.setText(hourOfDay + ":" + minuteOfHour);
                            }
                        }, hour, minute,false);
                timePickerDialog.show();
            }
        });


        ImageButton home = findViewById(R.id.Home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });



        enterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (ldlInput.getText().toString().equals("")
                            || hdlInput.getText().toString().equals("")
                            || TRIGinput.getText().toString().equals("")
                            || TCinput.getText().toString().equals("")
                            || editTime.getText().toString().equals("")) {

                        showDataNotEnteredWarning();

                    } else if (Integer.parseInt(ldlInput.getText().toString()) >= 250
                            || Integer.parseInt(ldlInput.getText().toString()) <= 25
                            || Integer.parseInt(TRIGinput.getText().toString()) >= 500
                            || Integer.parseInt(hdlInput.getText().toString()) <= 0
                            || Integer.parseInt(hdlInput.getText().toString()) >= 100
                            || Integer.parseInt(TRIGinput.getText().toString()) <= 25
                            || Integer.parseInt(TCinput.getText().toString()) <= 25
                            || Integer.parseInt(TCinput.getText().toString()) >= 400) {

                        showDataIncorrectRange();

                    } else {

                        dateEpoch = editDate.getText().toString() + editTime.getText().toString();
                        dateInsertion = new SimpleDateFormat("MMM dd yyyy HH:mm").parse(dateEpoch);
                        epoch = dateInsertion.getTime();

                        boolean isInserted = MainActivity.myDB.insertCholesterol(
                                editDate.getText().toString(),
                                editTime.getText().toString() + ":00.000 EST",
                                epoch.toString(),
                                Integer.parseInt(ldlInput.getText().toString()),
                                Integer.parseInt(hdlInput.getText().toString()),
                                Integer.parseInt(TRIGinput.getText().toString()),
                                Double.parseDouble(TCinput.getText().toString()));

                        if (isInserted = true) {

                            showDataEntryCheckmark();

                        } else {

                            showDataError();
                        }

                        launchPrevActivity();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        clearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hdlInput.setText("");
                ldlInput.setText("");
                TRIGinput.setText("");
                editDate.setText("");
                editTime.setText("");
                TCinput.setText("");
            }
        });

        // allows the user to click off the keyboard when activated
        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.cholesterolEntryLayout);
        mainLayout.setOnTouchListener(new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                // TODO Auto-generated method stub
                InputMethodManager inputMethodManager =
                        (InputMethodManager)EnterCholesterolDataActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(EnterCholesterolDataActivity.this.getCurrentFocus().getWindowToken(), 0);
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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void launchPrevActivity()
    {
        Intent intent = new Intent (this, SelectCholesterolActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Show images in Toast prompt.
    private void showDataEntryCheckmark()
    {

        Toast toast = Toast.makeText(getApplicationContext(), "Cholesterol Entered",
                Toast.LENGTH_SHORT);
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

        Toast toast = Toast.makeText(getApplicationContext(), "Please Complete All Fields",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.ic_warning);
        toastContentView.addView(imageView, 0);
        toast.show();
    }

    // Show images in Toast prompt.
    private void showDataIncorrectRange()
    {

        Toast toast = Toast.makeText(getApplicationContext(), "Incorrect Value Range Entered",
                Toast.LENGTH_SHORT);
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

        Toast toast = Toast.makeText(getApplicationContext(), "ERROR: Please Try Again",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.ic_error);
        toastContentView.addView(imageView, 0);
        toast.show();
    }

    public void sendOnChannel3() {
        String title = "";
        String message = "";

        // this is for making the app open on this screen if the notification is clicked.
        Intent intent = new Intent(this, CholesterolGraph.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification;
        notification = new NotificationCompat.Builder(this, "CHANNEL_3_ID")
        .setContentTitle("Cholesterol Alert")
        .setSmallIcon(R.drawable.ic_cholesterol_arrows)
        .setContentText("If the values entered are correct...")
        .setPriority(1)
        .setStyle(new NotificationCompat.BigTextStyle()
                .bigText("If the values entered are correct, please contact a health professional."))
        .setContentIntent(pendingIntent)
        .build();

        notificationManager.notify(3,notification);
    }
}
