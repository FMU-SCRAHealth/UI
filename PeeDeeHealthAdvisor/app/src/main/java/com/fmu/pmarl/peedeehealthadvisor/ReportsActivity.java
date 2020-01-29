package com.fmu.pmarl.peedeehealthadvisor;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;




import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReportsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_reports);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        //CursorInstantiation (USER)
        Cursor cursorUser = MainActivity.myDB.readUserData();
        String reportUserName = "";
        String reportUserAge = "";
        String reportUserGender = "";
        TextView textBoxUsername = findViewById(R.id.ReportsUserBoxName);
        TextView textBoxAge = findViewById(R.id.ReportsUserBoxAge);
        ImageButton home = findViewById(R.id.Home);
        FloatingActionButton export = findViewById(R.id.fab);

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

        // CursorInstantiation (VACCINATIONSs)
        Cursor cursorVax = MainActivity.myDB.readVaccinationRecords();
        String reportVaxDate = "";
        String reportImmunized = "";
        String reportVaccine = "";
        TextView vaxDateBox = findViewById(R.id.VaxDateBox);
        TextView vaxNameBox = findViewById(R.id.VaxNameBox);
        TextView vaxImmunizedBox = findViewById(R.id.VaxImmunizedBox);

        // weight
        Cursor cursorWeight = MainActivity.myDB.readBodyWeight();
        String reportWeightDate = "";
        String reportWeight = "";
        TextView weightDateBox = findViewById(R.id.WeightDateBox);
        TextView weightNameBox = findViewById(R.id.WeightNameBox);

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

        //iterate for vacciantion data
        while (!cursorVax.isAfterLast()) {

            reportVaxDate = cursorVax.getString(0);
            reportImmunized = cursorVax.getString(3);
            reportVaccine = cursorVax.getString(4);

            if (reportImmunized.equals("1")) {
                vaxDateBox.setText(vaxDateBox.getText() + "\n" + reportVaxDate);
                vaxNameBox.setText(vaxNameBox.getText() + "\n" + reportVaccine);
                vaxImmunizedBox.setText(vaxImmunizedBox.getText() + "\n" + "Taken");
            } else {
                vaxDateBox.setText(vaxDateBox.getText() + "\n" + reportVaxDate);
                vaxNameBox.setText(vaxNameBox.getText() + "\n" + reportVaccine);
                vaxImmunizedBox.setText(vaxImmunizedBox.getText() + "\n" + "Not Taken");
            }

            cursorVax.moveToNext();
        }

        // body weight check for null
        if (cursorWeight != null) {
            cursorWeight.moveToFirst();
        }

        //iterate for body weight data.
        while (!cursorWeight.isAfterLast()) {

            reportWeightDate = cursorWeight.getString(0);
            reportWeight = cursorWeight.getString(2);

            weightDateBox.setText(weightDateBox.getText() + "\n" + reportWeightDate);
            weightNameBox.setText(weightNameBox.getText() + "\n" + reportWeight + " lbs");

            cursorWeight.moveToNext();
        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportReport();
            }
        });

    }

    //Button Functionality
    @Override
    public void onBackPressed() {
        launchPrevActivity();
    }



    private PdfDocument pd = new PdfDocument();
    private PdfDocument.PageInfo pi = new PdfDocument.PageInfo.Builder(792, 612, 1).create();

    private void exportReport(){
        pd = new PdfDocument();
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        /*Calendar past = Calendar.getInstance();
        past.setTime(date);
        int diff = (cal.get(Calendar.MONTH) - 6) % 12;
        past.set(Calendar.MONTH, diff);
        if (diff > cal.get(Calendar.MONTH)) past.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);*/
        Cursor c = MainActivity.myDB.readBloodPressure();
        bpPages(c, cal);
        c = MainActivity.myDB.readBloodSugar();
        bsPages(c, cal);
        c = MainActivity.myDB.readCholesterol();
        cholPages(c, cal);
        c = MainActivity.myDB.readBodyWeight();
        weightPages(c, cal);
        c = MainActivity.myDB.readVaccinationRecords();
        vaccPages(c, cal);

        if(pd.getPages().size() == 0){
            AlertDialog.Builder d = new AlertDialog.Builder(this);
            d.setTitle("Error");
            d.setMessage("There is no stored data. Please enter data into any of the following:\n\tBlood Pressure\n\tBlood Sugar\n\tCholesterol\n\tVaccinations\n\tBody Weight");
            d.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = d.create();
            dialog.show();
            return;
        }

        try{
            File f = new File(Environment.getExternalStorageDirectory() + "/" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DAY_OF_MONTH)
                    + "-" + cal.get(Calendar.YEAR) + "_Report.pdf");
            pd.writeTo(new FileOutputStream(f));
            AlertDialog.Builder d = new AlertDialog.Builder(this);
            d.setTitle("Success!");
            d.setMessage("Your report is now available at\n\n" + f.getAbsolutePath() + "\n\nWrite this path down so that you can find your report later.");
            d.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = d.create();
            dialog.show();
        }catch(FileNotFoundException e){
            e.printStackTrace();
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
            exportReport();
        }catch(IOException f){
            f.printStackTrace();
        }

        pd.close();


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

    private void bpPages(Cursor bp, Calendar curr){
        if(bp.getCount() == 0) return;
        int pageNum = 1;
        int maxPages = (int)(bp.getCount()/73)+1;

        bp.moveToFirst();
        String past = dateConversion(bp.getString(0));

        PdfDocument.Page p = bpPageSetup(curr, past, pageNum, maxPages);
        Canvas c = p.getCanvas();
        Paint text = new Paint();
        int tableOriginX = 36;
        int tableOriginY = 144;
        int tableNum = 0;
        int entryNum = 0;
        text.setTextSize(12);
        while(!bp.isAfterLast()){
            int entryOriginX = tableOriginX + tableNum * 194;
            int entryOriginY = tableOriginY + entryNum * 33;
            if((entryOriginX + 158) > 756){
                pd.finishPage(p);
                pageNum++;
                p = bpPageSetup(curr, past, pageNum, maxPages);
                c = p.getCanvas();
                tableNum = 0;
                entryNum = 0;

            }
            else if((entryOriginY + 33) > 576){
                tableNum++;
                entryNum = 0;
                continue;
            }
            c.drawLine(entryOriginX, entryOriginY, entryOriginX + 158, entryOriginY, new Paint());
            c.drawLine(entryOriginX, entryOriginY, entryOriginX, entryOriginY + 33, new Paint());
            c.drawLine(entryOriginX + 158, entryOriginY, entryOriginX+158, entryOriginY + 33, new Paint());
            c.drawLine(entryOriginX, entryOriginY+33, entryOriginX+158, entryOriginY+33, new Paint());
            c.drawLine(entryOriginX + 74, entryOriginY, entryOriginX + 74, entryOriginY + 33, new Paint());
            c.drawText(bp.getString(0), entryOriginX + 5, entryOriginY + 15,text);
            c.drawText(bp.getString(1).substring(0, 5), entryOriginX + 5, entryOriginY + 30, text);
            c.drawText("Systolic: " + bp.getString(2), entryOriginX + 77, entryOriginY+15, text);
            c.drawText("Diastolic: " + bp.getString(3), entryOriginX + 77, entryOriginY + 30, text);
            bp.moveToNext();
            entryNum++;
        }
        pd.finishPage(p);
    }

    private PdfDocument.Page bpPageSetup(Calendar curr, String past, int pageNum, int maxPages){
        PdfDocument.Page p = pd.startPage(pi);
        Canvas c = p.getCanvas();
        Paint text = new Paint();
        text.setTextSize(24);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.appicon);
        c.drawBitmap(Bitmap.createScaledBitmap(bitmap, 72, 72, true), 36, 36, new Paint());
        c.drawText("Report for ", 126, 66, text);
        c.drawText(past+ "-" + (curr.get(Calendar.MONTH)+1) + "/" + curr.get(Calendar.DAY_OF_MONTH)
                + "/" + curr.get(Calendar.YEAR), 126, 94, text);

        c.drawLine(396, 40, 396, 104, new Paint());
        c.drawText("Blood Pressure", 414, 66, text);
        c.drawText("Page " + pageNum + " of " + maxPages, 414, 94, text);

        c.drawLine(36, 124, 752, 124, new Paint());
        c.drawLine(36, 128, 752, 128, new Paint());

        text.setTextSize(8);
        c.drawText("This report was generated by SC Health Plus Me", 36, 594, text);
        text.setTextSize(12);

        return p;
    }

    private void bsPages(Cursor bp, Calendar curr){
        if(bp.getCount() == 0) return;
        int pageNum = 1;
        int maxPages = (int)(bp.getCount()/73)+1;

        bp.moveToFirst();
        String past = dateConversion(bp.getString(0));

        PdfDocument.Page p = bsPageSetup(curr, past, pageNum, maxPages);
        Canvas c = p.getCanvas();
        Paint text = new Paint();
        int tableOriginX = 36;
        int tableOriginY = 144;
        int tableNum = 0;
        int entryNum = 0;
        bp.moveToFirst();
        if(bp.isNull(0)) return;
        text.setTextSize(10);
        while(!bp.isAfterLast()){
            int entryOriginX = tableOriginX + tableNum * 194;
            int entryOriginY = tableOriginY + entryNum * 33;
            if((entryOriginX + 158) > 756){
                pd.finishPage(p);
                pageNum++;
                p = bsPageSetup(curr, past, pageNum, maxPages);
                c = p.getCanvas();
                tableNum = 0;
                entryNum = 0;

            }
            else if((entryOriginY + 33) > 576){
                tableNum++;
                entryNum = 0;
                continue;
            }
            c.drawLine(entryOriginX, entryOriginY, entryOriginX + 158, entryOriginY, new Paint());
            c.drawLine(entryOriginX, entryOriginY, entryOriginX, entryOriginY + 33, new Paint());
            c.drawLine(entryOriginX + 158, entryOriginY, entryOriginX+158, entryOriginY + 33, new Paint());
            c.drawLine(entryOriginX, entryOriginY+33, entryOriginX+158, entryOriginY+33, new Paint());
            c.drawLine(entryOriginX + 68, entryOriginY, entryOriginX + 68, entryOriginY + 33, new Paint());
            c.drawText(bp.getString(0), entryOriginX + 5, entryOriginY + 15,text);
            c.drawText(bp.getString(1).substring(0, 5), entryOriginX + 5, entryOriginY + 30, text);
            c.drawText("Fasting: " + ((bp.getString(2).equals("1"))?"Yes":"No"), entryOriginX + 72, entryOriginY+15, text);
            c.drawText("Blood Sugar: " + bp.getString(3), entryOriginX + 72, entryOriginY + 30, text);
            bp.moveToNext();
            entryNum++;
        }
        pd.finishPage(p);
        text.setTextSize(12);
    }

    private PdfDocument.Page bsPageSetup(Calendar curr, String past, int pageNum, int maxPages){
        PdfDocument.Page p = pd.startPage(pi);
        Canvas c = p.getCanvas();
        Paint text = new Paint();
        text.setTextSize(24);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.appicon);
        c.drawBitmap(Bitmap.createScaledBitmap(bitmap, 72, 72, true), 36, 36, new Paint());
        c.drawText("Report for ", 126, 66, text);
        c.drawText(past+ "-" + (curr.get(Calendar.MONTH)+1) + "/" + curr.get(Calendar.DAY_OF_MONTH)
                + "/" + curr.get(Calendar.YEAR), 126, 94, text);

        c.drawLine(396, 40, 396, 104, new Paint());
        c.drawText("Blood Sugar", 414, 66, text);
        c.drawText("Page " + pageNum + " of " + maxPages, 414, 94, text);

        c.drawLine(36, 124, 752, 124, new Paint());
        c.drawLine(36, 128, 752, 128, new Paint());

        text.setTextSize(8);
        c.drawText("This report was generated by SC Health Plus Me", 36, 594, text);
        text.setTextSize(12);
        return p;
    }

    private void cholPages(Cursor bp, Calendar curr){
        if(bp.getCount() == 0) return;
        int pageNum = 1;
        int maxPages = (int)(bp.getCount()/29)+1;

        bp.moveToFirst();
        String past = dateConversion(bp.getString(0));

        PdfDocument.Page p = cholPageSetup(curr, past, pageNum, maxPages);
        Canvas c = p.getCanvas();
        Paint text = new Paint();
        int tableOriginX = 36;
        int tableOriginY = 144;
        int tableNum = 0;
        int entryNum = 0;
        bp.moveToFirst();
        text.setTextSize(12);
        while(!bp.isAfterLast()){
            int entryOriginX = tableOriginX + tableNum * 194;
            int entryOriginY = tableOriginY + entryNum * 63;
            if((entryOriginX + 158) > 756){
                pd.finishPage(p);
                pageNum++;
                p = cholPageSetup(curr, past, pageNum, maxPages);
                c = p.getCanvas();
                tableNum = 0;
                entryNum = 0;

            }
            else if((entryOriginY + 63) > 576){
                tableNum++;
                entryNum = 0;
                continue;
            }
            c.drawLine(entryOriginX, entryOriginY, entryOriginX + 158, entryOriginY, new Paint());
            c.drawLine(entryOriginX, entryOriginY, entryOriginX, entryOriginY + 63, new Paint());
            c.drawLine(entryOriginX + 158, entryOriginY, entryOriginX+158, entryOriginY + 63, new Paint());
            c.drawLine(entryOriginX, entryOriginY+63, entryOriginX+158, entryOriginY+63, new Paint());
            c.drawLine(entryOriginX + 74, entryOriginY, entryOriginX + 74, entryOriginY + 63, new Paint());
            c.drawText(bp.getString(0), entryOriginX + 5, entryOriginY + 15,text);
            c.drawText(bp.getString(1).substring(0, 5), entryOriginX + 5, entryOriginY + 30, text);
            c.drawText("Total: " + bp.getString(5), entryOriginX + 77, entryOriginY+15, text);
            c.drawText("LDL: " + bp.getString(3), entryOriginX + 77, entryOriginY + 30, text);
            c.drawText("HDL: " + bp.getString(2), entryOriginX + 77, entryOriginY + 45, text);
            c.drawText("Trig: " + bp.getString(4), entryOriginX + 77, entryOriginY + 60, text);
            bp.moveToNext();
            entryNum++;
        }
        pd.finishPage(p);
    }

    private PdfDocument.Page cholPageSetup(Calendar curr, String past, int pageNum, int maxPages){
        PdfDocument.Page p = pd.startPage(pi);
        Canvas c = p.getCanvas();
        Paint text = new Paint();
        text.setTextSize(24);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.appicon);
        c.drawBitmap(Bitmap.createScaledBitmap(bitmap, 72, 72, true), 36, 36, new Paint());
        c.drawText("Report for ", 126, 66, text);
        c.drawText(past+ "-" + (curr.get(Calendar.MONTH)+1) + "/" + curr.get(Calendar.DAY_OF_MONTH)
                + "/" + curr.get(Calendar.YEAR), 126, 94, text);

        c.drawLine(396, 40, 396, 104, new Paint());
        c.drawText("Cholesterol", 414, 66, text);
        c.drawText("Page " + pageNum + " of " + maxPages, 414, 94, text);

        c.drawLine(36, 124, 752, 124, new Paint());
        c.drawLine(36, 128, 752, 128, new Paint());

        text.setTextSize(8);
        c.drawText("This report was generated by SC Health Plus Me", 36, 594, text);
        text.setTextSize(12);

        return p;
    }

    private void weightPages(Cursor bp, Calendar curr){
        if(bp.getCount() == 0) return;
        int pageNum = 1;
        int maxPages = (int)(bp.getCount()/73)+1;

        bp.moveToFirst();
        String past = dateConversion(bp.getString(0));

        PdfDocument.Page p = weightPageSetup(curr, past, pageNum, maxPages);
        Canvas c = p.getCanvas();
        Paint text = new Paint();
        int tableOriginX = 36;
        int tableOriginY = 144;
        int tableNum = 0;
        int entryNum = 0;
        bp.moveToFirst();
        text.setTextSize(12);
        while(!bp.isAfterLast()){
            int entryOriginX = tableOriginX + tableNum * 194;
            int entryOriginY = tableOriginY + entryNum * 33;
            if((entryOriginX + 158) > 756){
                pd.finishPage(p);
                pageNum++;
                p = weightPageSetup(curr, past, pageNum, maxPages);
                c = p.getCanvas();
                tableNum = 0;
                entryNum = 0;

            }
            else if((entryOriginY + 33) > 576){
                tableNum++;
                entryNum = 0;
                continue;
            }
            c.drawLine(entryOriginX, entryOriginY, entryOriginX + 158, entryOriginY, new Paint());
            c.drawLine(entryOriginX, entryOriginY, entryOriginX, entryOriginY + 33, new Paint());
            c.drawLine(entryOriginX + 158, entryOriginY, entryOriginX+158, entryOriginY + 33, new Paint());
            c.drawLine(entryOriginX, entryOriginY+33, entryOriginX+158, entryOriginY+33, new Paint());
            c.drawLine(entryOriginX + 74, entryOriginY, entryOriginX + 74, entryOriginY + 33, new Paint());
            c.drawText(bp.getString(0), entryOriginX + 5, entryOriginY + 15,text);
            c.drawText(bp.getString(1).substring(0, 5), entryOriginX + 5, entryOriginY + 30, text);
            c.drawText("Weight: " + bp.getString(2), entryOriginX + 77, entryOriginY+15, text);
            bp.moveToNext();
            entryNum++;
        }
        pd.finishPage(p);
    }

    private PdfDocument.Page weightPageSetup(Calendar curr, String past, int pageNum, int maxPages){
        PdfDocument.Page p = pd.startPage(pi);
        Canvas c = p.getCanvas();
        Paint text = new Paint();
        text.setTextSize(24);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.appicon);
        c.drawBitmap(Bitmap.createScaledBitmap(bitmap, 72, 72, true), 36, 36, new Paint());
        c.drawText("Report for ", 126, 66, text);
        c.drawText(past + "-" + (curr.get(Calendar.MONTH)+1) + "/" + curr.get(Calendar.DAY_OF_MONTH)
                + "/" + curr.get(Calendar.YEAR), 126, 94, text);

        c.drawLine(396, 40, 396, 104, new Paint());
        c.drawText("Body Weight", 414, 66, text);
        c.drawText("Page " + pageNum + " of " + maxPages, 414, 94, text);

        c.drawLine(36, 124, 752, 124, new Paint());
        c.drawLine(36, 128, 752, 128, new Paint());

        text.setTextSize(8);
        c.drawText("This report was generated by SC Health Plus Me", 36, 594, text);
        text.setTextSize(12);

        return p;
    }

    private void vaccPages(Cursor bp, Calendar curr){
        if(bp.getCount() == 0) return;
        int pageNum = 1;
        int maxPages = (int)(bp.getCount()/73)+1;

        bp.moveToFirst();
        String past = dateConversion(bp.getString(0));

        PdfDocument.Page p = vaccPageSetup(curr, past, pageNum, maxPages);
        Canvas c = p.getCanvas();
        Paint text = new Paint();
        int tableOriginX = 36;
        int tableOriginY = 144;
        int tableNum = 0;
        int entryNum = 0;
        bp.moveToFirst();
        text.setTextSize(12);
        while(!bp.isAfterLast()){
            int entryOriginX = tableOriginX + tableNum * 271;
            int entryOriginY = tableOriginY + entryNum * 33;
            if((entryOriginX + 158) > 756){
                pd.finishPage(p);
                pageNum++;
                p = vaccPageSetup(curr, past, pageNum, maxPages);
                c = p.getCanvas();
                tableNum = 0;
                entryNum = 0;

            }
            else if((entryOriginY + 33) > 576){
                tableNum++;
                entryNum = 0;
                continue;
            }
            c.drawLine(entryOriginX, entryOriginY, entryOriginX + 235, entryOriginY, new Paint());
            c.drawLine(entryOriginX, entryOriginY, entryOriginX, entryOriginY + 33, new Paint());
            c.drawLine(entryOriginX + 235, entryOriginY, entryOriginX+235, entryOriginY + 33, new Paint());
            c.drawLine(entryOriginX, entryOriginY+33, entryOriginX+235, entryOriginY+33, new Paint());
            c.drawLine(entryOriginX + 84, entryOriginY, entryOriginX + 84, entryOriginY + 33, new Paint());
            c.drawText("Date Taken:", entryOriginX + 5, entryOriginY + 15,text);
            c.drawText(bp.getString(0), entryOriginX + 5, entryOriginY + 30, text);
            c.drawText("Vaccine: ", entryOriginX + 87, entryOriginY+15, text);
            c.drawText(bp.getString(4), entryOriginX + 87, entryOriginY + 30, text);
            bp.moveToNext();
            entryNum++;
        }
        pd.finishPage(p);
    }

    private PdfDocument.Page vaccPageSetup(Calendar curr, String past, int pageNum, int maxPages){
        PdfDocument.Page p = pd.startPage(pi);
        Canvas c = p.getCanvas();
        Paint text = new Paint();
        text.setTextSize(24);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.appicon);
        c.drawBitmap(Bitmap.createScaledBitmap(bitmap, 72, 72, true), 36, 36, new Paint());
        c.drawText("Report for ", 126, 66, text);
        c.drawText(past + "-" + (curr.get(Calendar.MONTH)+1) + "/" + curr.get(Calendar.DAY_OF_MONTH)
                + "/" + curr.get(Calendar.YEAR), 126, 94, text);

        c.drawLine(396, 40, 396, 104, new Paint());
        c.drawText("Lifetime Vaccinations", 414, 66, text);
        c.drawText("Page " + pageNum + " of " + maxPages, 414, 94, text);

        c.drawLine(36, 124, 752, 124, new Paint());
        c.drawLine(36, 128, 752, 128, new Paint());

        text.setTextSize(8);
        c.drawText("This report was generated by SC Health Plus Me", 36, 594, text);
        text.setTextSize(12);

        return p;
    }

    private String dateConversion(String date){
        String[] list = date.split(" ");
        switch(list[0]){
            case "Jan": list[0] = "1"; break;
            case "Feb": list[0] = "2"; break;
            case "Mar": list[0] = "3"; break;
            case "Apr": list[0] = "4"; break;
            case "May": list[0] = "5"; break;
            case "Jun": list[0] = "6"; break;
            case "Jul": list[0] = "7"; break;
            case "Aug": list[0] = "8"; break;
            case "Sep": list[0] = "9"; break;
            case "Oct": list[0] = "10"; break;
            case "Nov": list[0] = "11"; break;
            case "Dec": list[0] = "12"; break;
        }
        return list[0] + "/" + list[1] + "/" + list[2];
    }
}
