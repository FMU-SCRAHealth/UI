package com.example.pmarl.peedeehealthadvisor;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
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

public class FirstTimeLogin extends AppCompatActivity
{

    //DatabaseHelper myDB;
    private Button enterData;
    private Button clearData;
    private TextInputEditText firstNameText, lastNameText, genderText;

    private Context context = this;
    private EditText editDate;
    private Calendar myCalendar = Calendar.getInstance();
    private String dateFormat = "MM.dd.yyyy";
    private DatePickerDialog.OnDateSetListener date;
    private SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_first_time_login);

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



        firstNameText = (TextInputEditText) findViewById(R.id.firstNameText);
        lastNameText = (TextInputEditText) findViewById(R.id.lastNameText);
        genderText = (TextInputEditText) findViewById(R.id.genderText);





        enterData = (Button) findViewById(R.id.enterData);

        enterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String userFullName = firstNameText.getText().toString() + " " +
                        lastNameText.getText().toString();
                if (firstNameText.getText().toString().equals("") || lastNameText.getText().toString().equals("")
                        || genderText.getText().toString().equals("")) {
                    showDataNotEnteredWarning();
                } else {

                    boolean isInserted =
                            MainActivity.myDB.insertUserData(userFullName,
                                    editDate.getText().toString(), genderText.getText().toString());
                    if (isInserted = true)
                        showDataEntryCheckmarkUser();
                    else
                        showDataError();

                    launchMainActivity();
                }
            }
        });

        clearData = (Button) findViewById(R.id.clearData);

        clearData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                firstNameText.setText("");
                lastNameText.setText("");
                genderText.setText("");
                editDate.setText("");
            }
        });

        /*
        clearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchprevActivity();
            }
        });
        */

    }

    @Override
    public void onBackPressed() {

    }



    private void launchMainActivity()
    {
        Intent intent = new Intent (this, MainActivity.class);
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

        Toast toast = Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.ic_userentered);
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
