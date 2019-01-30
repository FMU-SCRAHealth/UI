/*
    Author: Patrick Marlowe
    Email Address: pmarlowe782@gmail.com
    Written: June 22, 2018
    Last Updated: June 25, 2018

    Compilation:
    Execution:

    Description of Execution:
 */
package com.example.pmarl.peedeehealthadvisor;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class BloodSugarGraph extends AppCompatActivity
{
    LineChart lineChart;


    @SuppressLint("SimpleDateFormat")
    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.blood_sugar_graph);

        ImageButton home = findViewById(R.id.Home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

        // finding the graph by element id.
        lineChart =  findViewById(R.id.lineGraph);

        /*Creating a cursor, which is a table that stores the data from
         * the sql query*/
        Cursor cursor = MainActivity.myDB.readBloodSugar();

        /*Creating an array list that will hold the blood pressure values and the dates that
         * the values were taken on*/


        /*Just a test array for the x values for the graph*/
        final ArrayList<String> xLabels = new ArrayList<>();

        /*Test values for the xLabels array*/
        Integer i = 0;
        String date;
        Date date1;
        long epoch;
        TreeMap<Long,BloodSugarValue> treeMap = new TreeMap<>();
        Boolean fasting;


        /*Loop through the rows of the cursor and set the (y,x) values
         * cursor.moveToNext() returns a boolean value and performs an action to move to the
         * next cursor*/
        cursor.moveToLast();
        do
        {


            try
            {
                date = cursor.getString(0) + cursor.getString(1);
                date1 = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz").parse(date);
                epoch = date1.getTime();

                fasting = Integer.parseInt(cursor.getString(2)) == 1;

                treeMap.put(epoch, new BloodSugarValue(fasting,Float.parseFloat(cursor.getString(3))));



            } catch (ParseException e)
            {
                e.printStackTrace();
            }

        }while (cursor.moveToPrevious());



        Set<Map.Entry<Long, BloodSugarValue>> set = treeMap.entrySet();
        Iterator<Map.Entry<Long, BloodSugarValue>> iterator = set.iterator();
        ArrayList<Entry> bloodSugar = new ArrayList<>();
        while(iterator.hasNext())
        {
            Map.Entry<Long, BloodSugarValue> mEntry = iterator.next();
            BloodSugarValue bloodSugarValue = mEntry.getValue();


            /*Adding the systolic value from the DB and the date that corresponds
             * with it*/
            bloodSugar.add(new Entry(bloodSugarValue.getBloodSugar(), i));



            /*Adding test values for the systolic and diastolic values*/
            Long epoch1 = mEntry.getKey();

            Date date2 = new Date(epoch1);


            xLabels.add(new SimpleDateFormat("MMM dd HH:mm").format(date2));

            i++;
        }



        /*Creating the systolic data set and setting different attributes for it*/
        LineDataSet bloodSugarSet = new LineDataSet(bloodSugar,"Blood Sugar");
        bloodSugarSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        bloodSugarSet.setColor(getResources().getColor(R.color.PurpleHuesDark));
        bloodSugarSet.setValueTextSize(13f);
        bloodSugarSet.setDrawCubic(true);//Allows for curved lines instead of linear lines
        bloodSugarSet.setCircleRadius(4f);
        bloodSugarSet.setCircleColor(getResources().getColor(R.color.PurpleHuesLight));
        bloodSugarSet.setLineWidth(2f);



        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);
        xAxis.setLabelRotationAngle(-90);


        /*Creating an array list for your data sets
         * "A set of sets"*/
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(bloodSugarSet);


        /*creating the data to be plotted in the line chart*/
        LineData data = new LineData(xLabels,dataSets);


        /*Setting the data and attributes for the line chart*/
        lineChart.setDescription("Blood Sugar");
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");
        lineChart.setData(data);
        lineChart.setVisibleXRangeMaximum(30);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setDoubleTapToZoomEnabled(true);
        lineChart.invalidate();
    }

    @Override
    public void onBackPressed() {
        launchPrevActivity();
    }

    private void launchMainActivity()
    {
        Intent intent = new Intent (this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void launchPrevActivity()
    {
        Intent intent = new Intent (this, SelectBloodSugarActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}