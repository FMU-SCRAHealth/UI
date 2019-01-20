/*
    Author: Patrick Marlowe
    Email Address: pmarlowe782@gmail.com
    Written: June 22, 2018
    Last Updated: July 25, 2018

    Compilation:
    Execution:

    Description of Execution:
 */
package com.example.pmarl.peedeehealthadvisor;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.Notification;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class EnterVaccinationDataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private Button enterData, clearData;
    private ImageButton home;
    private Spinner Spinner;
    Context context = this;
    EditText editDate;
    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "MMM dd yyyy ";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    private NotificationManagerCompat notificationManager;
    Cursor cursorVaccinations = MainActivity.myDB.readVaccinationRecords();
    Cursor cursorUser = MainActivity.myDB.readUserData();
    String dateQuery = "";
    String vaccinationName = "";
    Date date1;
    long epoch;
    long check;
    long futureInMillis;
    long futureInMillisFlu; // makes three for four of these for the different types
    long checkFlu;
    long futureInMillisShingles;
    long checkShingles;
    long futureInMillisPneumonia;
    long checkPneumonia;
    int age;
    Date userBirthday;
    Date todayDate;





    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_enter_vaccination_data);
        notificationManager = NotificationManagerCompat.from(this);

        editDate = (EditText) findViewById(R.id.editDate);

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
                myCalendar.set(YEAR, year);
                myCalendar.set(MONTH, monthOfYear);
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
                        .get(YEAR), myCalendar.get(MONTH),
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

        Spinner =  findViewById(R.id.vaccinationSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.vaccinations,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner.setAdapter(adapter);
        Spinner.setOnItemSelectedListener(this);


        clearData = (Button) findViewById(R.id.clearData);
        clearData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // needs implementations
                Spinner.setSelection(0);
                editDate.setText("");
            }
        });

        //Check for values (USER)
        if (cursorUser != null) {
            cursorUser.moveToLast();
        }

            try {
                String reportUserAge = cursorUser.getString(1); // this pulls the date.

                userBirthday = new SimpleDateFormat("MM.dd.yyyy").parse(reportUserAge);
                todayDate = new SimpleDateFormat("MMM dd yyyy").parse(dateString);


                getCalendar(userBirthday); // gets birthday

                age = getDiffYears(userBirthday, todayDate); //computers the age.

            } catch (Exception e) {
                showDataError();
            }



        enterData = (Button) findViewById(R.id.enterData);
        enterData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(Spinner.getSelectedItemPosition()==0)
                {
                    showDataNotEnteredWarning();
                }
                else
                {
                    // needs implementations
                    boolean isInserted = MainActivity.myDB.insertVaccination(editDate.getText().toString(), Spinner.getSelectedItem().toString());

                    if (isInserted = true) {

                        showDataEntryCheckmark();

                        if(cursorVaccinations != null) {
                            cursorVaccinations.moveToLast();
                        }

                        // finds the correct notification to send
                        do {

                            vaccinationName = cursorVaccinations.getString(3);

                            try {

                                dateQuery = cursorVaccinations.getString(0) + cursorVaccinations.getString(1);
                                date1 = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz").parse(dateQuery);
                                epoch = date1.getTime();
                                futureInMillisFlu = new EnterVaccinationDataActivity().futureMillisTimeCalculator(epoch, 30326400000L); // makes three for four of these for the different types
                                checkFlu = futureInMillisFlu-System.currentTimeMillis();
                                futureInMillisShingles = new EnterVaccinationDataActivity().futureMillisTimeCalculator(epoch, 156556800000L); // makes three for four of these for the different types
                                checkShingles = futureInMillisShingles-System.currentTimeMillis();
                                futureInMillisPneumonia = new EnterVaccinationDataActivity().futureMillisTimeCalculator(epoch, 30326400000L); // makes three for four of these for the different types 30326400000L
                                checkPneumonia = futureInMillisPneumonia-System.currentTimeMillis();
                                // 86400000 is one day in our time we need epoch milliseconds this is in nanoseconds

                            } catch (ParseException e) {
                                e.printStackTrace();
                                showDataError();
                            }

                            if(vaccinationName.equals("Flu Shot") && checkFlu < -1000) {
                                scheduleNotification(getNotification("It is time for you to get your flu vaccination!", "Flu Shot Reminder"), futureInMillisFlu);
                                break;
                            }

                            if(vaccinationName.equals("Flu Shot")) {
                                scheduleNotification(getNotification("It is time for you to get your flu vaccination!", "Flu Shot Reminder"), futureInMillisFlu);
                                break;
                            }

                            if(vaccinationName.equals("Shingle") && checkShingles < -1000 && age >= 60) {
                                scheduleNotification(getNotification("It is time for you to get your shingle vaccination!", "Shingle Vaccination Reminder"), futureInMillisShingles);
                                break;
                            }

                            if(vaccinationName.equals("Shingle") && age >= 60) {
                                scheduleNotification(getNotification("It is time for you to get your shingle vaccination!", "Shingle Vaccination Reminder"), futureInMillisShingles);
                                break;
                            }

                            if(vaccinationName.equals("Pneumonia PPSV23") && checkPneumonia < -1000 && age >= 65) {
                                scheduleNotification(getNotification("It is time for you to get your pneumonia PVC13 vaccination!", "Pneumonia PVC13 Reminder"), futureInMillisPneumonia);
                                break;
                            }

                            if(vaccinationName.equals("Pneumonia PPSV23") && age >= 65) {
                                scheduleNotification(getNotification("It is time for you to get your pneumonia PVC13 vaccination!", "Pneumonia PVC13 Reminder"), futureInMillisPneumonia);
                                break;
                            }

                            if(vaccinationName.equals("Pneumonia PVC13") && age >= 65 ) {
                                scheduleNotification(getNotification("It is time for you to get your pneumonia PPSV23 vaccination!", "Pneumonia Reminder"), futureInMillisPneumonia);
                                break;
                            }

                            if(vaccinationName.equals("Pneumonia PVC13") && checkPneumonia < -1000 && age >= 65 ) {
                                scheduleNotification(getNotification("It is time for you to get your pneumonia PPSV23 vaccination!", "Pneumonia Reminder"), futureInMillisPneumonia);
                                break;
                            }


                        } while (cursorVaccinations.moveToPrevious());

                    } else {

                        showDataError();
                    }

                    launchPrevActivity();
                }


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
        Intent intent = new Intent (this, SelectVaccinationActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l)
    {
        if(position != 0)
        {
            String text = parent.getItemAtPosition(position).toString();
//            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // Show images in Toast prompt.
    private void showDataEntryCheckmark()
    {

        Toast toast = Toast.makeText(getApplicationContext(), "Vaccination Entered", Toast.LENGTH_SHORT);
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

    private long futureMillisTimeCalculator(long epoch, long delay){

        long futureInMillis = epoch + delay;

        return futureInMillis;
    }

    private void scheduleNotification(Notification notification, long futureInMillis) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 4);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent); // can use a repeating method to make notifications repeat after time or hard code
    }

    private Notification getNotification(String content, String title) {

        // this is for making the app open on this screen if the notification is clicked.
        Intent intent = new Intent(this, EnterVaccinationDataActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification = new NotificationCompat.Builder(this, "CHANNEL_4_ID")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_vaccinations)
                .setContentText(content)
                .setPriority(1)
                // can be used to make the notifications longer. Just add another parameter to the getNotification() and add it in.
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText("Remember to get your vaccinations in the recommended time-frame."))
                .setContentIntent(pendingIntent)
                .build();

        return notification;
    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

}
