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

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultXAxisValueFormatter;
import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.EntryXIndexComparator;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class BloodPressureGraph extends AppCompatActivity
{
    private ImageButton home;
    LineChart lineChart;


    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.blood_pressure_graph);

        home = (ImageButton) findViewById(R.id.Home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

        // finding the graph by element id.
        lineChart = (LineChart) findViewById(R.id.lineGraph);

        /*Creating a cursor, which is a table that stores the data from
        * the sql query*/
        Cursor  cursor = MainActivity.myDB.readBloodPressure();

        /*Creating an array list that will hold the systolic values and the dates that
        * the values were taken on*/
        ArrayList<Entry> systolic = new ArrayList<>();

        /*Creating an array list that will hold the diastolic values and the dates that
        * the values were taken on*/
        ArrayList<Entry> diastolic = new ArrayList<>();

        /*Just a test array for the x values for the graph*/
        final ArrayList<String> xLabels = new ArrayList<>();

        /*Test values for the xLabels array*/
        Integer i = 0;

            /*Loop through the rows of the cursor and set the (y,x) values
            * cursor.moveToNext() returns a boolean value and performs an action to move to the
            * next cursor*/

            String date;

            Date date1;
            long epoch;
            long referenceTimeStamp=0;

        TreeMap<Long,ArrayList<Integer>>  treeMap = new TreeMap<>();


            while(cursor.moveToNext())
            {

                date = cursor.getString(0)+cursor.getString(1);
                try {
                    date1 = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz").parse(date);
                    epoch = date1.getTime();
//                    if(cursor.isFirst())
//                    {
//                        referenceTimeStamp = epoch;
//                    }

                    //epoch = (epoch -referenceTimeStamp)/60;
                    treeMap.put( epoch, new ArrayList<Integer>());
                    /*Systolic Added*/
                    treeMap.get(epoch).add(Integer.parseInt(cursor.getString(2)));
                    /*Diastolic added*/
                    treeMap.get(epoch).add(Integer.parseInt(cursor.getString(3)));

                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            Set set = treeMap.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry  mentry = (Map.Entry)iterator.next();
            ArrayList<Integer> arrayList =(ArrayList<Integer>)mentry.getValue();

            /*Adding the systolic value from the DB and the date that corresponds
             * with it*/
            systolic.add(new Entry(arrayList.get(0), i));

            /*Adding the diastolic value from the DB and the date that corresponds
             * with it*/
            diastolic.add(new Entry(arrayList.get(1), i));

            /*Adding test values for the systolic and diastolic values*/
            Long epoch1 = (Long) mentry.getKey();
            //long epoch2 = (long) epoch1 ;
            Date date2 = new Date(epoch1);


            xLabels.add(new SimpleDateFormat("MMM dd").format(date2));
            //xLabels.add(xAxisValueFormatter.toString());

            i++;
        }
           //Collections.sort(systolic, new EntryXIndexComparator());
           //Collections.sort(diastolic, new EntryXIndexComparator());

        //XAxisValueFormatter xAxisValueFormatter= new MonthAxisValueFormatter(referenceTimeStamp);

        /*Creating the systolic data set and setting different attributes for it*/
        LineDataSet systolicSet = new LineDataSet(systolic,"Systolic");
        systolicSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        systolicSet.setColor(getResources().getColor(R.color.RedHuesDark));
        systolicSet.setValueTextSize(13f);
        systolicSet.setDrawCubic(true);//Allows for curved lines instead of linear lines
        systolicSet.setCircleRadius(4f);
        systolicSet.setCircleColor(getResources().getColor(R.color.RedHuesLight));
        systolicSet.setLineWidth(2f);


        /*Creatin the diastolic data set and setting different attributes for it*/
        LineDataSet diastolicSet = new LineDataSet(diastolic,"Diastolic");
        diastolicSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        diastolicSet.setColor(getResources().getColor(R.color.RedHuesLight));
        diastolicSet.setValueTextSize(13f);
        diastolicSet.setDrawCubic(true);//Allows for curved lines instead of linear lines
        diastolicSet.setCircleRadius(4f);
        diastolicSet.setCircleColor(getResources().getColor(R.color.RedHuesDark));
        diastolicSet.setLineWidth(2f);
        diastolicSet.enableDashedLine(10f,5f,0f);


        XAxis xAxis = lineChart.getXAxis();
       // xAxis.setValueFormatter(xAxisValueFormatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);
        xAxis.setLabelRotationAngle(-90);


        /*Creating an array list for your data sets
        * "A set of sets"*/
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(systolicSet);
        dataSets.add(diastolicSet);

        /*creating the data to be plotted in the line chart*/
        LineData data = new LineData(xLabels,dataSets);


        /*Setting the data and attributes for the line chart*/
        lineChart.setDescription("Blood Pressure");
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");
        lineChart.setData(data);
        //lineChart.setVisibleXRangeMaximum(30);
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
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void launchPrevActivity()
    {
        Intent intent = new Intent (this, SelectBloodPressureActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}