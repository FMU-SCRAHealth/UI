package com.example.pmarl.peedeehealthadvisor;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EnterAllergyDataActivity extends AppCompatActivity
{

    //DatabaseHelper myDB;
    private Button enterData;
    private Button clearData;
    private TextInputEditText allergyNameInput, allergyDescriptionInput;
    private Context context = this;
//    private EditText editDate;
//    private Calendar myCalendar = Calendar.getInstance();
//    private String dateFormat = "MM.dd.yyyy";
//    private DatePickerDialog.OnDateSetListener date;
//    private SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_enter_allergies);

        ImageButton home = findViewById(R.id.Home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

//        editDate = (EditText) findViewById(R.id.editDate);

        // init - set date to current date
//        long currentdate = System.currentTimeMillis();
//        String dateString = sdf.format(currentdate);
//        editDate.setText(dateString);

        // set calendar date and update editDate
//        date = new DatePickerDialog.OnDateSetListener()
//        {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth)
//            {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateDate();
//            }
//
//        };

        // onclick - popup datepicker
//        editDate.setOnClickListener(new View.OnClickListener()
//        {
//
//            @Override
//            public void onClick(View v)
//            {
//                // TODO Auto-generated method stub
//                new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });



        allergyNameInput = (TextInputEditText) findViewById(R.id.allergyNameInput);
        allergyDescriptionInput = (TextInputEditText) findViewById(R.id.allergyDescriptionInput);
//        maleToggle = (RadioButton) findViewById(R.id.maleToggle);
//        femaleToggle = (RadioButton) findViewById(R.id.femaleToggle);
//        otherToggle = (RadioButton) findViewById((R.id.otherToggle));





        enterData = (Button) findViewById(R.id.enterData);

        enterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String userFullName = allergyNameInput.getText().toString() + " " +
                        allergyNameInput.getText().toString();
                if (allergyNameInput.getText().toString().equals("") || allergyDescriptionInput.getText().toString().equals("")) {
                    showDataNotEnteredWarning();

                } else {

                    boolean isInserted =
                            MainActivity.myDB.insertAllergies(allergyNameInput.getText().toString(),
                                    allergyDescriptionInput.getText().toString());

                    if (isInserted = true)
                        showDataEntryCheckmark();
                    else
                        showDataError();

                    launchPrevActivity();
                }
            }
        });

        clearData = (Button) findViewById(R.id.clearData);

        clearData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                allergyNameInput.setText("");
                allergyDescriptionInput.setText("");
//                editDate.setText("");


            }
        });

        // allows the user to click off the keyboard when activated
        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.allergyEntry);
        mainLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                InputMethodManager inputMethodManager = (InputMethodManager)  EnterAllergyDataActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(EnterAllergyDataActivity.this.getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });

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
        Intent intent = new Intent (this, SelectAllergiesActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

//    private void updateDate()
//    {
//        editDate.setText(sdf.format(myCalendar.getTime()));
//    }

    // Show images in Toast prompt.
    private void showDataEntryCheckmark()
    {

        Toast toast = Toast.makeText(getApplicationContext(), "Allergy Entered", Toast.LENGTH_SHORT);
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