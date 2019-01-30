package com.example.pmarl.peedeehealthadvisor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class EnterMedicationDataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    //DatabaseHelper myDB;
    private Button enterData;
    private Button clearData;
    private Spinner spinner;
    private TextInputEditText medNameInput, medDoseInput, medDelivery, medRxNum, medPharmName, medPhoneNum;
    private Context context = this;

    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_enter_med_data);

        ImageButton home = findViewById(R.id.Home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

        // spinner for delivery
        spinner =  findViewById(R.id.medicationDeliverySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.delivery,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        medNameInput = (TextInputEditText) findViewById(R.id.medicationNameInput);
        medDoseInput = (TextInputEditText) findViewById(R.id.medDoseInput);
        medRxNum = (TextInputEditText) findViewById(R.id.medRXInput);
        medPharmName = (TextInputEditText) findViewById(R.id.medPharmNameInput);
        medPhoneNum = (TextInputEditText) findViewById(R.id.medPharmNumberInput);


        enterData = (Button) findViewById(R.id.enterData);

        enterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String medInputString = medNameInput.getText().toString() + " " +
                        " " + medNameInput.getText().toString() +  " " + spinner.getSelectedItem().toString() + " " +
                        medRxNum.getText().toString() +  " " + medPharmName.getText().toString() +
                        " " + medPhoneNum.getText().toString();
                if (medNameInput.getText().toString().equals("") || medDoseInput.getText().toString().equals("")
                        || spinner.getSelectedItemPosition()==0 || medRxNum.getText().toString().equals("")
                        || medPharmName.getText().toString().equals("") || medPhoneNum.getText().toString().equals("")) {
                    showDataNotEnteredWarning();

                } else {

                    boolean isInserted =
                            MainActivity.myDB.insertMedication(medNameInput.getText().toString(),
                                    medDoseInput.getText().toString(), spinner.getSelectedItem().toString(),
                                    medRxNum.getText().toString(), medPharmName.getText().toString(), medPhoneNum.getText().toString());

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
                medNameInput.setText("");
                medDoseInput.setText("");
                spinner.setSelection(0);
                medRxNum.setText("");
                medPharmName.setText("");
                medPhoneNum.setText("");

            }
        });


        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.medicationEntry);
        mainLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                InputMethodManager inputMethodManager = (InputMethodManager)  EnterMedicationDataActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(EnterMedicationDataActivity.this.getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        launchPrevActivity();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l)
    {
        if(position != 0)
        {
            String text = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        Intent intent = new Intent (this, SelectMedicationActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Show images in Toast prompt.
    private void showDataEntryCheckmark()
    {

        Toast toast = Toast.makeText(getApplicationContext(), "Medication Entered", Toast.LENGTH_SHORT);
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
