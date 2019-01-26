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

public class EditUserProfile extends AppCompatActivity
{

    //DatabaseHelper myDB;
    private Button enterData;
    private Button clearData;
    private TextInputEditText firstNameText, lastNameText;
    private RadioButton femaleToggle, maleToggle, otherToggle;
    private String genderText;
    private Context context = this;
    private EditText editDate;
    private Calendar myCalendar = Calendar.getInstance();
    private String dateFormat = "MM.dd.yyyy";
    private DatePickerDialog.OnDateSetListener date;
    private SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    private ImageButton home;

    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_user_edit_profile);

        editDate = (EditText) findViewById(R.id.editDateProfile);

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



        firstNameText = (TextInputEditText) findViewById(R.id.firstNameTextProfile);
        lastNameText = (TextInputEditText) findViewById(R.id.lastNameTextProfile);
        maleToggle = (RadioButton) findViewById(R.id.maleToggleProfile);
        femaleToggle = (RadioButton) findViewById(R.id.femaleToggleProfile);
        otherToggle = (RadioButton) findViewById((R.id.otherToggleProfile));





        enterData = (Button) findViewById(R.id.enterDataProfile);

        enterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String userFullName = firstNameText.getText().toString() + " " +
                        lastNameText.getText().toString();
                if (firstNameText.getText().toString().equals("") || lastNameText.getText().toString().equals("")) {
                    showDataNotEnteredWarning();

                } else {

                    if (femaleToggle.isChecked()) {
                        genderText = "Female";
                    }

                    else if (maleToggle.isChecked()) {
                        genderText = "Male";
                    }

                    else if (otherToggle.isChecked()) {
                        genderText = "Other";
                    }

                    boolean isInserted =
                            MainActivity.myDB.updateUserData(userFullName,
                                    editDate.getText().toString(), genderText);

                    if (isInserted = true) {
                        showDataEditCheckmarkUser();
                    } else {
                        showDataError();
                    }
                    launchHomeScreenActivity();
                }
            }
        });

        clearData = (Button) findViewById(R.id.clearDataProfile);

        clearData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                firstNameText.setText("");
                lastNameText.setText("");
                editDate.setText("");


            }
        });


        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.firstTimeLogInEntryProfile);
        mainLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                InputMethodManager inputMethodManager = (InputMethodManager)  EditUserProfile.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(EditUserProfile.this.getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });

        this.home = (ImageButton) findViewById(R.id.Home);
        this.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

    }

    @Override
    public void onBackPressed() {
        launchHomeScreenActivity();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.femaleToggle:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.maleToggle:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.otherToggle:
                if(checked)
                    break;
        }
    }


    private void launchMainActivity()
    {
        Intent intent = new Intent (this, MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void launchHomeScreenActivity()
    {
        Intent intent = new Intent (this, AppSwitcher.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void updateDate()
    {
        editDate.setText(sdf.format(myCalendar.getTime()));
    }

    // Show images in Toast prompt.
    private void showDataEntryCheckmarkUser()
    {

        String welcomeText = "Welcome To Health Companion " + firstNameText.getText().toString() + "!";
        Toast toast = Toast.makeText(getApplicationContext(), welcomeText, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.ic_userenteredswipe);
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

    private void showDataEditCheckmarkUser()
    {

        String welcomeText = "Profile Updated " + firstNameText.getText().toString() + "!";
        Toast toast = Toast.makeText(getApplicationContext(), welcomeText, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.ic_userenteredswipe);
        toastContentView.addView(imageView, 0);
        toast.show();

    }
}
