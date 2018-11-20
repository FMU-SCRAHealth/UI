package com.example.pmarl.peedeehealthadvisor;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHelper  extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "healthRecords.db";

    private static final String userTable = "user";
    private static final String userName = "name";
    private static final String userDOB = "DOB";
    private static final String userGender = "gender";

    private static final String measurementTable = "measurement";
    private static final String measurementDate = "date";
    private static final String measurementTime = "time";
    private static final String measurementUserName = "user_name";
    private static final String measurementType = "meas_type";

    private static final String measurementFasting = "fasting";
    private static final String measurementBloodSugar = "blood_sugar";
    private static final String measurementImmunized = "immunized";
    private static final String measurementVirus = "virus";
    private static final String measurementLDL = "LDL";
    private static final String measurementHDL = "HDL";

    private static final String measurementTRIG = "TRIG";
    private static final String measurementSystolic = "systolic";
    private static final String measurementDiastolic = "diastolic";

    private String user_name;
    private Cursor cursor;
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.SSS zzz");

    //this can not be private *below
    DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {

        sqLiteDatabase.execSQL("CREATE TABLE " + userTable + " ("+userName+" TEXT PRIMARY KEY," +
                userDOB+" TEXT, "+userGender+" TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE " + measurementTable + "("+measurementDate+" TEXT, "
                +measurementTime+" TEXT, "+measurementUserName+" TEXT, "+measurementType+" TEXT, "
                +measurementFasting+" INTEGER, "+measurementBloodSugar+" INTEGER, "
                +measurementImmunized+" INTEGER, "+measurementVirus+" TEXT, "+measurementLDL
                +" INTEGER, "+measurementHDL+" INTEGER, "+measurementTRIG+" INTEGER, "
                +measurementSystolic+" INTEGER, "+measurementDiastolic
                +" INTEGER, PRIMARY KEY("+measurementDate+", "+measurementTime+"))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + userTable);
        onCreate(sqLiteDatabase);

    }

    public boolean insertUserData(String name, String DOB, String gender)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        this.user_name = name;

        ContentValues contentValues = new ContentValues();

        contentValues.put(userName,name);
        contentValues.put(userDOB,DOB);
        contentValues.put(userGender,gender);

        long result = db.insert(userTable,null,contentValues);
        return result != -1;

    }

    public boolean insertBloodPressure(String date, int systolic, int diastolic)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        Calendar calendar = Calendar.getInstance();
        String time = this.simpleDateFormat.format(calendar.getTime());



        String meas_type = "Blood Pressure";


        ContentValues contentValues = new ContentValues();

        contentValues.put(measurementDate,date);
        contentValues.put(measurementTime,time);
        contentValues.put(measurementUserName,this.user_name);
        contentValues.put(measurementType,meas_type);
        contentValues.put(measurementSystolic,systolic);
        contentValues.put(measurementDiastolic,diastolic);

        long result = db.insert(measurementTable,null,contentValues);
        return result != -1;

    }
    public boolean insertBloodSugar(String date, int fasting, int blood_sugar)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        Calendar calendar = Calendar.getInstance();
        String time = this.simpleDateFormat.format(calendar.getTime());



        String meas_type = "Blood Sugar";

        ContentValues contentValues = new ContentValues();

        contentValues.put(measurementDate,date);
        contentValues.put(measurementTime,time);
        contentValues.put(measurementUserName,this.user_name);
        contentValues.put(measurementType,meas_type);

        contentValues.put(measurementFasting,fasting);
        contentValues.put(measurementBloodSugar,blood_sugar);

        long result = db.insert(measurementTable,null,contentValues);
        return result != -1;

    }
    public boolean insertCholesterol(String date, int LDL, int HDL, int TRIG)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        Calendar calendar = Calendar.getInstance();
        String time = simpleDateFormat.format(calendar.getTime());



        String meas_type = "Cholesterol";

        ContentValues contentValues = new ContentValues();

        contentValues.put(measurementDate,date);
        contentValues.put(measurementTime,time);
        contentValues.put(measurementUserName,this.user_name);
        contentValues.put(measurementType,meas_type);
        contentValues.put(measurementLDL,LDL);
        contentValues.put(measurementHDL,HDL);

        contentValues.put(measurementTRIG,TRIG);


        long result = db.insert(measurementTable,null,contentValues);
        return result != -1;

    }
    public boolean insertVaccination(String date, String virus)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        Calendar calendar = Calendar.getInstance();
        String time = simpleDateFormat.format(calendar.getTime());



        String meas_type = "Vaccination";

        Integer immunized = 1;

        ContentValues contentValues = new ContentValues();

        contentValues.put(measurementDate,date);
        contentValues.put(measurementTime,time);
        contentValues.put(measurementUserName,this.user_name);
        contentValues.put(measurementType,meas_type);
        contentValues.put(measurementImmunized,immunized);
        contentValues.put(measurementVirus,virus);

        long result = db.insert(measurementTable,null,contentValues);
        return result != -1;

    }

    public Cursor readBloodPressure()
    {
        String[] columns = {measurementDate, measurementTime, measurementSystolic,
                measurementDiastolic};

        String whereClause = measurementType +" = ?";

        String[] whereArgs = new String[]{"Blood Pressure"};

        this.cursor = this.getReadableDatabase().query(measurementTable,
                columns,whereClause,whereArgs,null,null,null);

        return cursor;
    }

    public Cursor readBloodSugar()
    {
        String[] columns = {measurementDate, measurementTime, measurementFasting,
                measurementBloodSugar};

        String whereClause = measurementType +" = ?";

        String[] whereArgs = new String[]{"Blood Sugar"};

        this.cursor = this.getReadableDatabase().query(measurementTable,
                columns,whereClause,whereArgs,null,null,null);

        return cursor;
    }

    public  Cursor readCholesterol()
    {
        String[] columns = {measurementDate, measurementTime, measurementHDL, measurementLDL,
                measurementTRIG};

        String whereClause = measurementType +" = ?";

        String[] whereArgs = new String[]{"Cholesterol"};

        this.cursor = this.getReadableDatabase().query(measurementTable,
                columns,whereClause,whereArgs,null,null,null);

        return cursor;
    }

    public Cursor readVaccinationRecords()
    {
        String[] columns = {measurementDate,measurementImmunized, measurementVirus};

        String selection = "meas_type = ?";

        String[] selectionArgs = {"Vaccination"};

        Cursor cursorVaccinations = this.getReadableDatabase().query(measurementTable,
                columns,selection, selectionArgs, null,null,null);

        return cursorVaccinations;
    }

}
