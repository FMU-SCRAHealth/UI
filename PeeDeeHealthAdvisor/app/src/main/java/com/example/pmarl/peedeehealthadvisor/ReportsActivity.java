package com.example.pmarl.peedeehealthadvisor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_reports);

        //CursorInstantiation (USER)
        Cursor cursorUser = MainActivity.myDB.readUserData();
        String reportUserName = "";
        String reportUserAge = "";
        String reportUserGender = "";
        TextView textBoxUsername = findViewById(R.id.ReportsUserBoxName);
//        TextView textBoxGender = findViewById(R.id.ReportsUserBoxGender);
        TextView textBoxAge = findViewById(R.id.ReportsUserBoxAge);
        ImageButton home = findViewById(R.id.Home);

        //CursorInstantiation (BLOOD PRESSURE)
        Cursor cursorBloodPressure = MainActivity.myDB.readBloodPressure();
        String reportBPDate = "";
        String reportDia = "";
        String reportSys = "";
        TextView bpDateText = findViewById(R.id.BloodPressureBoxDate);
        TextView bpDiaText = findViewById(R.id.BloodPressureBoxDia);
        TextView bpSysText = findViewById(R.id.BloodPressureBoxSys);

        //CursorInstantiation (BLOOD SUGAR)
        Cursor cursorBloodSugar = MainActivity.myDB.readBloodSugar();
        String reportBSDate = "";
        String reportFasting = "";
        String reportBS = "";
        TextView bsDateText = findViewById(R.id.BloodSugarDateBox);
        TextView bsFasting = findViewById(R.id.BloodSugarFastingBox);
        TextView bsLevelText = findViewById(R.id.BloodSugarBox);

        //CursorInstantiation (Cholesterol)
        Cursor cursorChol = MainActivity.myDB.readCholesterol();
        String reportCholDate = "";
        String reportHDL = "";
        String reportLDL = "";
        String reportTrig = "";
        String reportTC = "";
        TextView cholDateText = findViewById(R.id.CholesterolDateBox);
        TextView cholHDLText = findViewById(R.id.CholesterolHDLBox);
        TextView cholLDLText = findViewById(R.id.CholesterolLDLBox);
        TextView cholTrigText = findViewById(R.id.CholesterolTrigBox);
        TextView cholTCText = findViewById(R.id.CholesterolTCBox);
        //CursorInstantiation (VACCINATIONSs)
        Cursor cursorVax = MainActivity.myDB.readVaccinationRecords();
        String reportVaxDate = "";
        String reportImmunized = "";
        String reportVaccine = "";
        TextView vaxDateBox = findViewById(R.id.VaxDateBox);
        TextView vaxNameBox = findViewById(R.id.VaxNameBox);

        //USER BOX UPDATE

        //Check for values (USER)
        if (cursorUser != null) {
            cursorUser.moveToFirst();
        }

            while (!cursorUser.isAfterLast()) {

                reportUserName = cursorUser.getString(0);
                reportUserAge = cursorUser.getString(1);
                reportUserGender = cursorUser.getString(2);

                try {

                    Date dateFormatted = new SimpleDateFormat("MM.dd.yyyy").parse(reportUserAge);
                    String formattedDate = new SimpleDateFormat(" MMM dd yyyy").format(dateFormatted);

                    textBoxUsername.setText(reportUserName);
//                    textBoxGender.setText(reportUserGender);
                    textBoxAge.setText(formattedDate);

                } catch (Exception e) {

                }
                cursorUser.moveToNext();

            }


        //BLOOD PRESSURE BOX UPDATE

        //Check for values (Blood Pressure)
        if (cursorBloodPressure != null) {
            cursorBloodPressure.moveToFirst();
        }

        //iterate
        while (!cursorBloodPressure.isAfterLast()) {

            reportBPDate = cursorBloodPressure.getString(0);
            reportDia = cursorBloodPressure.getString(2);
            reportSys = cursorBloodPressure.getString(3);
            bpDateText.setText(bpDateText.getText() + "\n" + reportBPDate);
            bpDiaText.setText(bpDiaText.getText() + "\n" + reportDia);
            bpSysText.setText(bpSysText.getText() + "\n" + reportSys);
            cursorBloodPressure.moveToNext();
        }

        //BLOOD SUGAR BOX UPDATE
        //Check for values (Blood Sugar)
        if (cursorBloodSugar != null) {
            cursorBloodSugar.moveToFirst();
        }

        //iterate
        while (!cursorBloodSugar.isAfterLast()) {

            reportBSDate = cursorBloodSugar.getString(0);
            reportFasting = cursorBloodSugar.getString(2);
            reportBS = cursorBloodSugar.getString(3);

            if (reportFasting.equals("1")) {
                bsDateText.setText(bsDateText.getText() + "\n" + reportBSDate);
                bsFasting.setText(bsFasting.getText() + "\n" + "Yes");
                bsLevelText.setText(bsLevelText.getText() + "\n" + reportBS);
            } else {
                bsDateText.setText(bsDateText.getText() + "\n" + reportBSDate);
                bsFasting.setText(bsFasting.getText() + "\n" + "No");
                bsLevelText.setText(bsLevelText.getText() + "\n" + reportBS);
            }
            cursorBloodSugar.moveToNext();
        }

        //CHOLESTEROL BOX UPDATE
        //Check for values (Cholesterol)
        if (cursorChol != null) {
            cursorChol.moveToFirst();
        }

        //iterate
        while (!cursorChol.isAfterLast()) {

            reportCholDate = cursorChol.getString(0);
            reportHDL = cursorChol.getString(2);
            reportLDL = cursorChol.getString(3);
            reportTrig = cursorChol.getString(4);
            reportTC = cursorChol.getString(5);
            cholDateText.setText(cholDateText.getText() + "\n" + reportCholDate);
            cholHDLText.setText(cholHDLText.getText() + "\n" + reportHDL);
            cholLDLText.setText(cholLDLText.getText() + "\n" + reportLDL);
            cholTrigText.setText(cholTrigText.getText() + "\n" + reportTrig);
            cholTCText.setText(cholTCText.getText() + "\n" + reportTC);
            cursorChol.moveToNext();
        }

        //VAX BOX UPDATE
        //Check for values (Blood Sugar)
        if (cursorVax != null) {
            cursorVax.moveToFirst();
        }

        //iterate
        while (!cursorVax.isAfterLast()) {

            reportVaxDate = cursorVax.getString(0);
            reportVaccine = cursorVax.getString(2);

            if (reportVaccine.equals("1")) {
                vaxDateBox.setText(vaxDateBox.getText() + "\n" + reportVaxDate);
                vaxNameBox.setText(vaxNameBox.getText() + "\n" + "Taken");
            } else {
                vaxDateBox.setText(vaxDateBox.getText() + "\n" + reportVaxDate);
                vaxNameBox.setText(vaxNameBox.getText() + "\n" + "Not Taken");
            }

            cursorVax.moveToNext();
        }

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
        Intent intent = new Intent (this, AppSwitcher.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }



}
