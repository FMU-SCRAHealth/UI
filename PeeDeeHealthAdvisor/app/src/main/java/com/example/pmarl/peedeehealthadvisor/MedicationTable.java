package com.example.pmarl.peedeehealthadvisor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MedicationTable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_medications);

        Cursor cursorMedications = MainActivity.myDB.readMedData();

        String medName = "";
        String medDose = "";
        String medDelivery = "";
        String medRXnum = "";
        String medPharmName = "";
        String medPharmNum = "";
        TextView medicationName = findViewById(R.id.medNameText);
        TextView medicationDose = findViewById(R.id.medDoseText);
        TextView medicationDelivery = findViewById(R.id.medDeliveryText);
        TextView medicationRxNum = findViewById(R.id.medRXNumText);
        TextView medicationPharmName = findViewById(R.id.medPharnNameText);
        TextView medicationPharmNum = findViewById(R.id.medPharmNumText);


        ImageButton home = findViewById(R.id.Home);
        TableLayout tableLayout = findViewById(R.id.medTable);

        // ALLERGY BOX UPDATE

        // Check for values (ALLERGIES)
        if (cursorMedications != null) {
            cursorMedications.moveToFirst();
        }

        // iterat
        do {

            try {
                medName = cursorMedications.getString(0);
                medDose = cursorMedications.getString(1);
                medDelivery = cursorMedications.getString(2);
                medRXnum = cursorMedications.getString(3);
                medPharmName = cursorMedications.getString(4);
                medPharmNum = cursorMedications.getString(5);
            } catch (Exception e) {
                launchPrevActivity();
            }

            TableLayout table = (TableLayout)MedicationTable.this.findViewById(R.id.medTable);

            // Inflate your row "template" and fill out the fields.
            TableRow row = (TableRow) LayoutInflater.from(MedicationTable.this).inflate(R.layout.med_row, null);
            ((TextView)row.findViewById(R.id.medNameText)).setText(medName);
            ((TextView)row.findViewById(R.id.medDoseText)).setText(medDose);
            ((TextView)row.findViewById(R.id.medDeliveryText)).setText(medDelivery);
            ((TextView)row.findViewById(R.id.medRXNumText)).setText(medRXnum);
            ((TextView)row.findViewById(R.id.medPharnNameText)).setText(medPharmName);
            ((TextView)row.findViewById(R.id.medPharmNumText)).setText(medPharmNum);
            table.addView(row);

            table.requestLayout();

        } while (cursorMedications.moveToNext());

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

    }

    //Button Functionality
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
        Intent intent = new Intent (this, SelectMedicationActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
