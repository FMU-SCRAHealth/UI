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
    private static final String measurementTypeUser = "meas_type";

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
    private static final String measurementTC = "TC";
    private static final String measurementSystolic = "systolic";
    private static final String measurementDiastolic = "diastolic";

    private static final String measurementAllergy = "allergy";
    private static final String measurementAllergyDescriptions = "description";

    private static final String measurementBodyWeight = "body_weight";

    private static final String medTable = "Medications";
    private static final String medName = "name";
    private static final String medDose = "dosage";
    private static final String medDelivery = "delivery";
    private static final String medRxNum = "rxNumber";
    private static final String medPharmName = "pharmacyName";
    private static final String medPharmNum = "pharmacyNumber";


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
                userDOB+" TEXT, "+userGender+" TEXT, "+measurementTypeUser+" TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE " + measurementTable + "("+measurementDate+" TEXT, "
                +measurementTime+" TEXT, "+measurementUserName+" TEXT, "+measurementType+" TEXT, "
                +measurementFasting+" INTEGER, "+measurementBloodSugar+" INTEGER, "
                +measurementImmunized+" INTEGER, "+measurementVirus+" TEXT, "+measurementLDL
                +" INTEGER, "+measurementHDL+" INTEGER, "+measurementTRIG+" INTEGER, "
                +measurementTC+" REAL, "
                +measurementSystolic+" INTEGER, "+measurementDiastolic
                +" INTEGER, "+measurementAllergy+" TEXT, "+measurementAllergyDescriptions+" TEXT, "
                +measurementBodyWeight+" REAL, "+
                "PRIMARY KEY("+measurementDate+", "+measurementTime+"))");

        sqLiteDatabase.execSQL("CREATE TABLE " + medTable + " ("+ medName + " TEXT PRIMARY KEY,"
            + medDose + " TEXT, " + medDelivery + " TEXT, " + medRxNum + " TEXT, "
            + medPharmName + " TEXT, " + medPharmNum + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + userTable);
        onCreate(sqLiteDatabase);

    }

    // inserting methods

    public boolean insertUserData(String name, String DOB, String gender)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String meas_type = "User";

        this.user_name = name;

        ContentValues contentValues = new ContentValues();

        contentValues.put(userName,name);
        contentValues.put(userDOB,DOB);
        contentValues.put(userGender,gender);
        contentValues.put(measurementTypeUser, meas_type);

        long result = db.insert(userTable,null,contentValues);
        return result != -1;

    }

    public boolean updateUserData(String name, String DOB, String gender)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String meas_type = "User";

        this.user_name = name;

        ContentValues contentValues = new ContentValues();

        contentValues.put(userName,name);
        contentValues.put(userDOB,DOB);
        contentValues.put(userGender,gender);
        contentValues.put(measurementTypeUser, meas_type);

        long result = db.update(userTable, contentValues, "name="+userName, null);
        return result != -1;

    }

    //Insert Medication Data
    public boolean insertMedication(String name, String dose, String delivery,
                                    String rxnum, String pharmname, String pharmnum)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        String meas_type = "Medications";

        ContentValues contentValues = new ContentValues();

        contentValues.put(medName, name);
        contentValues.put(medDose, dose);
        contentValues.put(medDelivery, delivery);
        contentValues.put(medRxNum, rxnum);
        contentValues.put(medPharmName, pharmname);
        contentValues.put(medPharmNum, pharmnum);

        long result = db.insert(medTable,null,contentValues);
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
    public boolean insertCholesterol(String date, int LDL, int HDL, int TRIG, double TC)
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
        contentValues.put(measurementTC, TC);


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

    public boolean insertAllergies(String allergy, String allergyDescription)
    {

        SQLiteDatabase db = this.getWritableDatabase();

//        Calendar calendar = Calendar.getInstance();
//        String time = simpleDateFormat.format(calendar.getTime());



        String meas_type = "Allergies";


        ContentValues contentValues = new ContentValues();

//        contentValues.put(measurementDate,date);
//        contentValues.put(measurementTime,time);
        contentValues.put(measurementUserName,this.user_name);
        contentValues.put(measurementType,meas_type);
        contentValues.put(measurementAllergy,allergy);
        contentValues.put(measurementAllergyDescriptions,allergyDescription);

        long result = db.insert(measurementTable,null,contentValues);
        return result != -1;

    }

    public boolean insertBodyWeight(String date, double weight)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Calendar calendar = Calendar.getInstance();
        String time = simpleDateFormat.format(calendar.getTime());



        String meas_type = "Body Weight";

        ContentValues contentValues = new ContentValues();

        contentValues.put(measurementDate,date);
        contentValues.put(measurementTime,time);
        contentValues.put(measurementUserName,this.user_name);
        contentValues.put(measurementType,meas_type);
        contentValues.put(measurementBodyWeight,weight);


        long result = db.insert(measurementTable,null,contentValues);
        return result != -1;

    }


    public Cursor readBodyWeight()
    {
        String[] columns = {measurementDate, measurementTime, measurementBodyWeight};

        String whereClause = measurementType + " = ?";

        String[] whereArgs = new String[]{"Body Weight"};

        this.cursor = this.getReadableDatabase().query(measurementTable, columns, whereClause,
                whereArgs,null,null,null);

        return cursor;
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
                measurementTRIG, measurementTC};

        String whereClause = measurementType +" = ?";

        String[] whereArgs = new String[]{"Cholesterol"};

        this.cursor = this.getReadableDatabase().query(measurementTable,
                columns,whereClause,whereArgs,null,null,null);

        return cursor;
    }

    public Cursor readVaccinationRecords()
    {
        String[] columns = {measurementDate,measurementTime,measurementImmunized, measurementVirus};

        String selection = "meas_type = ?";

        String[] selectionArgs = {"Vaccination"};

        Cursor cursorVaccinations = this.getReadableDatabase().query(measurementTable,
                columns,selection, selectionArgs, null,null,null);

        return cursorVaccinations;
    }

    public Cursor readAllergyRecords()
    {
        String[] columns = {measurementAllergy, measurementAllergyDescriptions};

        String selection = "meas_type = ?";

        String[] selectionArgs = {"Allergies"};

        Cursor cursorAllergies = this.getReadableDatabase().query(measurementTable,
                columns,selection, selectionArgs, null,null,null);

        return cursorAllergies;
    }



    public Cursor readUserData()
    {
        String[] columns = {userName,userDOB,userGender};

        String selection = "meas_type = ?";

        String[] selectionArgs = {"User"};

        Cursor cursorUserData = this.getReadableDatabase().query(userTable,
                columns, selection, selectionArgs, null,null,null);

        return cursorUserData;
    }

    public Cursor readMedData() {
        String[] columns = {medName, medDose, medDelivery, medRxNum, medPharmName, medPharmNum};

//        String selection = medName + "= ?";
//
//        String[] selectionArgs = {"Medication"};

        Cursor medCursor = this.getReadableDatabase().query(medTable,
                columns,null,null,null,null,null);

        return medCursor;
    }
}
