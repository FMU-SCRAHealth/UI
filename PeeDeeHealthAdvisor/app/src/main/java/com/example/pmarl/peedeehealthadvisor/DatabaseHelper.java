package com.example.pmarl.peedeehealthadvisor;

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

    private static final String USER_TABLE_NAME = "user";
    private static final String USER_COL_1 = "name";
    private static final String USER_COL_2 = "DOB";
    private static final String USER_COL_3 = "gender";

    private static final String MEASUREMENT_TABLE_NAME = "measurement";
    private static final String MEASUREMENT_COL_1 = "date";
    private static final String MEASUREMENT_COL_2 = "time";
    private static final String MEASUREMENT_COL_3 = "user_name";
    private static final String MEASUREMENT_COL_4 = "meas_type";

    private static final String MEASUREMENT_COL_5 = "fasting";
   // private static final String MEASUREMENT_COL_6 = "non_fasting";
    private static final String MEASUREMENT_COL_6_5 = "blood_sugar";
    private static final String MEASUREMENT_COL_7 = "immunized";
    private static final String MEASUREMENT_COL_8 = "virus";
    private static final String MEASUREMENT_COL_9 = "LDL";
    private static final String MEASUREMENT_COL_10 = "HDL";
    private static final String MEASUREMENT_COL_10_5 = "TC";
    private static final String MEASUREMENT_COL_11 = "systolic";
    private static final String MEASUREMENT_COL_12 = "diastolic";

    private String user_name;

    //this can not be private *below
    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("CREATE TABLE " + USER_TABLE_NAME + " (name TEXT PRIMARY KEY," +
                " DOB TEXT, gender TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE " + MEASUREMENT_TABLE_NAME + "(date TEXT, time TEXT," +
                " user_name TEXT, meas_type TEXT, fasting INTEGER, blood_sugar INTEGER," +
                " immunized INTEGER, virus TEXT, LDL INTEGER, HDL INTEGER, TC INTEGER, systolic INTEGER," +
                " diastolic INTEGER, PRIMARY KEY(date, time))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public boolean insertUserData(String name, String DOB, String gender)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        this.user_name = name;

        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_COL_1,name);
        contentValues.put(USER_COL_2,DOB);
        contentValues.put(USER_COL_3,gender);

        long result = db.insert(USER_TABLE_NAME,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }

    public boolean insertBloodPressure(String date, int systolic, int diastolic)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = simpleDateFormat.format(calendar.getTime());



        String meas_type = "Blood Pressure";
        /*
        String fasting = null;
        String non_fasting = null;
        Integer blood_sugar = null;
        Integer immunized = null;
        String virus = null;
        Integer LDL = null;
        Integer HDL = null;
        Integer TC = null;
        */

        ContentValues contentValues = new ContentValues();

        contentValues.put(MEASUREMENT_COL_1,date);
        contentValues.put(MEASUREMENT_COL_2,time);
        contentValues.put(MEASUREMENT_COL_3,this.user_name);
        contentValues.put(MEASUREMENT_COL_4,meas_type);
        /*
        contentValues.put(MEASUREMENT_COL_5,fasting);
        contentValues.put(MEASUREMENT_COL_6,non_fasting);
        contentValues.put(MEASUREMENT_COL_6_5, blood_sugar);
        contentValues.put(MEASUREMENT_COL_7,immunized);
        contentValues.put(MEASUREMENT_COL_8,virus);
        contentValues.put(MEASUREMENT_COL_9,LDL);
        contentValues.put(MEASUREMENT_COL_10,HDL);
        contentValues.put(MEASUREMENT_COL_10_5,TC);
        */
        contentValues.put(MEASUREMENT_COL_11,systolic);
        contentValues.put(MEASUREMENT_COL_12,diastolic);

        long result = db.insert(MEASUREMENT_TABLE_NAME,null,contentValues);
        if( result == -1)
            return false;
        else
            return true;

    }
    public boolean insertBloodSugar(String date, int fasting, int blood_sugar)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = simpleDateFormat.format(calendar.getTime());



        String meas_type = "Blood Sugar";

        /*
        String fasting = null;
        String non_fasting = null;
        Integer blood_sugar = null;
        Integer immunized = null;
        String virus = null;
        Integer LDL = null;
        Integer HDL = null;
        Integer TC = null;
        */

        ContentValues contentValues = new ContentValues();

        contentValues.put(MEASUREMENT_COL_1,date);
        contentValues.put(MEASUREMENT_COL_2,time);
        contentValues.put(MEASUREMENT_COL_3,this.user_name);
        contentValues.put(MEASUREMENT_COL_4,meas_type);

        contentValues.put(MEASUREMENT_COL_5,fasting);
        //contentValues.put(MEASUREMENT_COL_6,non_fasting);
        contentValues.put(MEASUREMENT_COL_6_5,blood_sugar);
        //contentValues.put(MEASUREMENT_COL_7,immunized);
        //contentValues.put(MEASUREMENT_COL_8,virus);
        //contentValues.put(MEASUREMENT_COL_9,LDL);
        //contentValues.put(MEASUREMENT_COL_10,HDL);
        //contentValues.put(MEASUREMENT_COL_10_5,TC);

        //contentValues.put(MEASUREMENT_COL_11,systolic);
        //contentValues.put(MEASUREMENT_COL_12,diastolic);

        long result = db.insert(MEASUREMENT_TABLE_NAME,null,contentValues);
        if( result == -1)
            return false;
        else
            return true;

    }
    public boolean insertCholesterol(String date, int LDL, int HDL, int TC)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = simpleDateFormat.format(calendar.getTime());



        String meas_type = "Cholesterol";

        /*
        String fasting = null;
        String non_fasting = null;
        Integer blood_sugar = null;
        Integer immunized = null;
        String virus = null;
        Integer LDL = null;
        Integer HDL = null;
        Integer TC = null;
        */

        ContentValues contentValues = new ContentValues();

        contentValues.put(MEASUREMENT_COL_1,date);
        contentValues.put(MEASUREMENT_COL_2,time);
        contentValues.put(MEASUREMENT_COL_3,this.user_name);
        contentValues.put(MEASUREMENT_COL_4,meas_type);

        //contentValues.put(MEASUREMENT_COL_5,fasting);
        //contentValues.put(MEASUREMENT_COL_6,non_fasting);
        //contentValues.put(MEASUREMENT_COL_6_5,blood_sugar);
        //contentValues.put(MEASUREMENT_COL_7,immunized);
        //contentValues.put(MEASUREMENT_COL_8,virus);
        contentValues.put(MEASUREMENT_COL_9,LDL);
        contentValues.put(MEASUREMENT_COL_10,HDL);
        contentValues.put(MEASUREMENT_COL_10_5,TC);

        //contentValues.put(MEASUREMENT_COL_11,systolic);
        //contentValues.put(MEASUREMENT_COL_12,diastolic);

        long result = db.insert(MEASUREMENT_TABLE_NAME,null,contentValues);
        if( result == -1)
            return false;
        else
            return true;

    }
    public boolean insertVaccination(String date, String virus)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = simpleDateFormat.format(calendar.getTime());



        String meas_type = "Vaccination";


        //String fasting = null;
        //String non_fasting = null;
        //Integer blood_sugar = null;
        Integer immunized = 1;
        //String virus = null;
        //Integer LDL = null;
        //Integer HDL = null;
        //Integer TC = null;


        ContentValues contentValues = new ContentValues();

        contentValues.put(MEASUREMENT_COL_1,date);
        contentValues.put(MEASUREMENT_COL_2,time);
        contentValues.put(MEASUREMENT_COL_3,this.user_name);
        contentValues.put(MEASUREMENT_COL_4,meas_type);

        //contentValues.put(MEASUREMENT_COL_5,fasting);
        //contentValues.put(MEASUREMENT_COL_6,non_fasting);
        //contentValues.put(MEASUREMENT_COL_6_5,blood_sugar);
        contentValues.put(MEASUREMENT_COL_7,immunized);
        contentValues.put(MEASUREMENT_COL_8,virus);
        //contentValues.put(MEASUREMENT_COL_9,LDL);
        //contentValues.put(MEASUREMENT_COL_10,HDL);
        //contentValues.put(MEASUREMENT_COL_10_5,TC);

        //contentValues.put(MEASUREMENT_COL_11,systolic);
        //contentValues.put(MEASUREMENT_COL_12,diastolic);

        long result = db.insert(MEASUREMENT_TABLE_NAME,null,contentValues);
        if( result == -1)
            return false;
        else
            return true;

    }

    public Cursor readBloodPressure()
    {
        String[] columns = {MEASUREMENT_COL_1,MEASUREMENT_COL_11,MEASUREMENT_COL_12};

        Cursor cursor = this.getReadableDatabase().query(MEASUREMENT_TABLE_NAME,
                columns,null,null,null,null,null);

        return cursor;
    }



}
