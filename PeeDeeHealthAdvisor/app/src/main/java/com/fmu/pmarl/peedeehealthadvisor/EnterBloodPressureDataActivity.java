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

public class EnterBloodPressureDataActivity extends AppCompatActivity //github test
{
    private Button enterData, clearData;

    private ImageButton home;

    private TextInputEditText systolicInput, diastolicInput;

    private Context context = this;
    private EditText editDate, editTime;
    private Calendar myCalendar = Calendar.getInstance();
    private String dateFormat = "MMM dd yyyy ";
    private String timeFormat = "HH:mm:ss.SSS zzz";
    private DatePickerDialog.OnDateSetListener date;
    private String dateEpoch;
    private Date dateInsertion;
    private Long epoch;
    private SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    private NotificationManagerCompat notificationManager;

    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_enter_blood_pressure_data);

        editTime = (EditText) findViewById(R.id.editTime);
        editDate = (EditText) findViewById(R.id.editDate);
        home = (ImageButton) findViewById(R.id.Home);
        enterData = (Button) findViewById(R.id.EnterData);
        clearData = (Button) findViewById(R.id.ClearData);
        systolicInput = (TextInputEditText) findViewById(R.id.systolicInput);
        diastolicInput = (TextInputEditText) findViewById(R.id.diastolicInput);
        notificationManager = NotificationManagerCompat.from(this);


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
                new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteOfHour) {
                        editTime.setText(hourOfDay + ":" + minuteOfHour);
                    }
                }, hour, minute, false);

                timePickerDialog.show();
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
                try {

                    if (systolicInput.getText().toString().equals("") ||
                            diastolicInput.getText().toString().equals("") ||
                            editTime.getText().toString().equals("")) {
                        showDataNotEnteredWarning();

                    } else if (Integer.parseInt(systolicInput.getText().toString()) >= 275 ||
                            Integer.parseInt(diastolicInput.getText().toString()) >= 150 ||
                            Integer.parseInt(systolicInput.getText().toString()) <= 0 ||
                            Integer.parseInt(diastolicInput.getText().toString()) <= 0) {
                            showDataIncorrectRange(); // if data is outside of reasonable range of entry
                    } else {
                        dateEpoch = editDate.getText().toString() + editTime.getText().toString();
                        dateInsertion = new SimpleDateFormat("MMM dd yyyy HH:mm").parse(dateEpoch);
                        epoch = dateInsertion.getTime();
                        boolean isInserted =

                                MainActivity.myDB.insertBloodPressure(
                                editDate.getText().toString(),

                                editTime.getText().toString() + ":00.000 EST",
                                        epoch.toString(),
                                Integer.parseInt(systolicInput.getText().toString()),
                                Integer.parseInt(diastolicInput.getText().toString()));

                        if (isInserted = true) {

                            showDataEntryCheckmark();

                            //                        if (Integer.parseInt(systolicInput.getText().toString()) >= 120 && Integer.parseInt(diastolicInput.getText().toString()) >= 90 || Integer.parseInt(systolicInput.getText().toString()) <= 90 && Integer.parseInt(diastolicInput.getText().toString()) <= 60) {
                            //                            sendOnChannel1();
                            //                        } // statement to show if both systolic and diastolic are high

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

        clearData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                systolicInput.setText("");
                diastolicInput.setText("");
                editDate.setText("");
                editTime.setText("");
            }
        });

        // allows the user to click off the keyboard when activated
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
    private void showDataIncorrectRange()
    {

        Toast toast = Toast.makeText(getApplicationContext(), "Incorrect Value Range Entered", Toast.LENGTH_SHORT);
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

    public void sendOnChannel1() {
        String title = "";
        String message = "";

        // this is for making the app open on this screen if the notification is clicked.
        Intent intent = new Intent(this, BloodPressureGraph.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // this creates the notification
        Notification notification = new NotificationCompat.Builder(this, "CHANNEL_1_ID")
                .setContentTitle("Blood Pressure Alert")
                .setSmallIcon(R.drawable.ic_blood_pressure)
                .setContentText("If the values entered are correct...")
                .setPriority(1)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("If the values entered are correct, please contact a health professional."))
                .setContentIntent(pendingIntent)
                .build();

        notificationManager.notify(1,notification);
    }
}
