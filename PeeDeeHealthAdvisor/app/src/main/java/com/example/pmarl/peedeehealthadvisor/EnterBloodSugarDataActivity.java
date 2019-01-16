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
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private String dateFormat = "MMM dd yyyy ";
    private DatePickerDialog.OnDateSetListener date;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
    private NotificationManagerCompat notificationManager;




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
         notificationManager = NotificationManagerCompat.from(this);

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
                 new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, date, myCalendar.get(Calendar.YEAR),
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
//                     Toast.makeText(EnterBloodSugarDataActivity.this,
//                             "Please enter a valid blood sugar level.", Toast.LENGTH_LONG).show();
                     showDataNotEnteredWarning();
                 }

                 else if (Integer.parseInt(bloodSugarInput.getText().toString()) >= 600 || Integer.parseInt(bloodSugarInput.getText().toString()) <= 0) {
                     showDataIncorrectRange();
                 }

                 else {
                     boolean isInserted = MainActivity.myDB.insertBloodSugar(editDate.getText().toString(), Integer.parseInt((fastingToggle.isChecked() ? "1" : "0")),
                             Integer.parseInt(bloodSugarInput.getText().toString()));

                     if (isInserted = true) {
//                         Toast.makeText(EnterBloodSugarDataActivity.this, "Blood Sugar Saved",
//                                 Toast.LENGTH_LONG).show();
                         showDataEntryCheckmark();

                         if (Integer.parseInt(bloodSugarInput.getText().toString()) >= 140 && Integer.parseInt((fastingToggle.getText().toString())) == 1 || Integer.parseInt(bloodSugarInput.getText().toString()) >= 100 && Integer.parseInt((fastingToggle.getText().toString())) == 0) {
                             sendOnChannel2();
                         }
                     } else {
//                         Toast.makeText(EnterBloodSugarDataActivity.this, "Blood Sugar NOT Saved",
//                                 Toast.LENGTH_LONG).show();
                         showDataError();

                     }
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

         LinearLayout mainLayout = (LinearLayout)findViewById(R.id.bloodSugarEntryLayout);
         mainLayout.setOnTouchListener(new View.OnTouchListener() {

             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 // TODO Auto-generated method stub
                 InputMethodManager inputMethodManager = (InputMethodManager)  EnterBloodSugarDataActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                 inputMethodManager.hideSoftInputFromWindow(EnterBloodSugarDataActivity.this.getCurrentFocus().getWindowToken(), 0);
                 return false;
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

    // Show images in Toast prompt.
    private void showDataEntryCheckmark()
    {

        Toast toast = Toast.makeText(getApplicationContext(), "Blood Sugar Entered", Toast.LENGTH_SHORT);
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

    public void sendOnChannel2() {
        String title = "";
        String message = "";

        // this is for making the app open on this screen if the notification is clicked.
        Intent intent = new Intent(this, BloodSugarGraph.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification = new NotificationCompat.Builder(this, "CHANNEL_2_ID")
                .setContentTitle("Blood Sugar Alert")
                .setSmallIcon(R.drawable.ic_blood_sugar)
                .setContentText("The value entered is higher...")
                .setPriority(1)
                .setLights(0xff00ff00, 300, 100)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("If the values entered are correct, please contact a health professional.\""))
                .setContentIntent(pendingIntent)
                .build();

        notificationManager.notify(2,notification);
    }
}
