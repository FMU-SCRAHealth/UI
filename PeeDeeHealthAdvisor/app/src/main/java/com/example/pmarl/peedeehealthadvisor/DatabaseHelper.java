package com.example.pmarl.peedeehealthadvisor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper  extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "healthRecords.db";

    public static final String USER_TABLE_NAME = "user";
    public static final String USER_COL_1 = "name";
    public static final String USER_COL_2 = "DOB";
    public static final String USER_COL_3 = "gender";

    public static final String MEASUREMENT_TABLE_NAME = "measurement";
    public static final String MEASUREMENT_COL_1 = "date";
    public static final String MEASUREMENT_COL_2 = "time";
    public static final String MEASUREMENT_COL_3 = "user_name";
    public static final String MEASUREMENT_COL_4 = "meas_type";
    //Going to combine fasting and non fasting into one col
    public static final String MEASUREMENT_COL_5 = "fasting";
    public static final String MEASUREMENT_COL_6 = "immunized";
    public static final String MEASUREMENT_COL_7 = "virus";
    public static final String MEASUREMENT_COL_8 = "LDL";
    public static final String MEASUREMENT_COL_9 = "HDL";
    public static final String MEASUREMENT_COL_10 = "systolic";
    public static final String MEASUREMENT_COL_11 = "diastolic";



    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("CREATE TABLE " + USER_TABLE_NAME + " (name TEXT PRIMARY KEY," +
                " DOB TEXT, gender TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE " + MEASUREMENT_TABLE_NAME + "(date TEXT, time TEXT," +
                " user_name TEXT, meas_type TEXT, fasting INTEGER, immunized INTEGER, virus TEXT," +
                " LDL INTEGER, HDL INTEGER, systolic INTEGER, diastolic INTEGER," +
                " PRIMARY KEY(date, time))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
