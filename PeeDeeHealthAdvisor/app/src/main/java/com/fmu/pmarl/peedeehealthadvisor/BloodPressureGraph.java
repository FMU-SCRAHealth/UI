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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

public class BloodPressureGraph extends AppCompatActivity
{
    LineChart lineChart;
    LineChart lineChartLand;
    Date now = new Date();
    TimeZone tz = Calendar.getInstance().getTimeZone();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList results = new ArrayList<BloodPressureDataObject>();

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.blood_pressure_graph);

        ImageButton home = findViewById(R.id.Home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

        // finding the graph by element id.
        lineChart =  findViewById(R.id.lineGraph);
        lineChartLand =  findViewById(R.id.lineGraphBPLand);
        // for landscape version have to duplicate code with different name and settings
        /*Creating a cursor, which is a table that stores the data from
        * the sql query*/
        Cursor cursor = MainActivity.myDB.readBloodPressure();

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
        String date;
        String dateCard;
        Date date1;
        long epoch;
        TreeMap<Long,ArrayList<Integer>>  treeMap = new TreeMap<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_blood_pressure);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyBloodPressureRecyclerViewAdapter(results); // make sure to check this
        mRecyclerView.setAdapter(mAdapter);


        /*Loop through the rows of the cursor and set the (y,x) values
            * cursor.moveToNext() returns a boolean value and performs an action to move to the
            * next cursor*/
            cursor.moveToLast();
            do
            {


                try
                {
                    if(cursor.getCount() > 0) {
                        date = cursor.getString(0) + cursor.getString(1);
                        date1 = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz").parse(date);

                        if(tz.inDaylightTime(date1)) {
                            epoch = date1.getTime() - 3600000;
                        } else {
                            epoch = date1.getTime();
                        }
//

                    treeMap.put(epoch, new ArrayList<Integer>());
                    /*Systolic Added*/
                    treeMap.get(epoch).add(Integer.parseInt(cursor.getString(2)));
                    /*Diastolic added*/
                    treeMap.get(epoch).add(Integer.parseInt(cursor.getString(3)));
                    } else {
                        launchPrevActivity();
                    }


                } catch (ParseException e) {

                    e.printStackTrace();

                }

            } while (cursor.moveToPrevious());


        Set<Map.Entry<Long, ArrayList<Integer>>> set = treeMap.entrySet();
        Iterator<Map.Entry<Long, ArrayList<Integer>>> iterator = set.iterator();

        while(iterator.hasNext())
        {
            Map.Entry<Long, ArrayList<Integer>> mEntry = iterator.next();
            ArrayList<Integer> arrayList = mEntry.getValue();

            /*Adding the systolic value from the DB and the date that corresponds
             * with it*/
            systolic.add(new Entry(arrayList.get(0), i));

            /*Adding the diastolic value from the DB and the date that corresponds
             * with it*/
            diastolic.add(new Entry(arrayList.get(1), i));

            /*Adding test values for the systolic and diastolic values*/
            Long epoch1 = mEntry.getKey();

            Date date2 = new Date(epoch1);

            dateCard = new SimpleDateFormat("MMM dd hh:mm a").format(date2);

            xLabels.add(new SimpleDateFormat("MMM dd hh:mm a").format(date2));

            BloodPressureDataObject bloodPressureEntry = new BloodPressureDataObject(arrayList.get(0), arrayList.get(1), dateCard, epoch1);

            results.add(bloodPressureEntry);



            i++;
        }



        /*Creating the systolic data set and setting different attributes for it*/
        LineDataSet systolicSet = new LineDataSet(systolic,"Systolic");
        systolicSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        systolicSet.setColor(getResources().getColor(R.color.RedHuesDark));
        systolicSet.setValueTextSize(0f);
        systolicSet.setDrawCubic(true);//Allows for curved lines instead of linear lines
        systolicSet.setCircleRadius(8f);
        systolicSet.setCircleColor(getResources().getColor(R.color.RedHuesLight));
        systolicSet.setLineWidth(5f);


        /*Creating the diastolic data set and setting different attributes for it*/
        LineDataSet diastolicSet = new LineDataSet(diastolic,"Diastolic");
        diastolicSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        diastolicSet.setColor(getResources().getColor(R.color.RedHuesLight));
        diastolicSet.setValueTextSize(0f);
        diastolicSet.setDrawCubic(true);//Allows for curved lines instead of linear lines
        diastolicSet.setCircleRadius(8f);
        diastolicSet.setCircleColor(getResources().getColor(R.color.RedHuesDark));
        diastolicSet.setLineWidth(5f);
        diastolicSet.enableDashedLine(10f,5f,0f);

        // vertical graph
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawAxisLine(false);
//        xAxis.setDrawGridLines(false);
        xAxis.setEnabled(false); // turns off bottom and lines
        xAxis.setLabelRotationAngle(-90);

        // landscape graph
        XAxis xAxisLand = lineChartLand.getXAxis();
        xAxisLand.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisLand.setEnabled(true);
        xAxisLand.setLabelRotationAngle(-90);

        // vertical graph
        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setEnabled(false);
        yAxisRight.setDrawGridLines(false);

        // landscape graph
        YAxis yAxisRightLand = lineChartLand.getAxisRight();
        yAxisRightLand.setDrawAxisLine(false);
        yAxisRightLand.setEnabled(false);
        yAxisRightLand.setDrawGridLines(false);

        // vertical graph
        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setDrawZeroLine(true);
        yAxisLeft.setEnabled(false);

        // landscape graph
        YAxis yAxisLeftLand = lineChartLand.getAxisLeft();
        yAxisLeftLand.setDrawZeroLine(true);
        yAxisLeftLand.setEnabled(true);


        /*Creating an array list for your data sets
        * "A set of sets"*/
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(systolicSet);
        dataSets.add(diastolicSet);

        /*creating the data to be plotted in the line chart*/
        LineData data = new LineData(xLabels,dataSets);


        /*Setting the data and attributes for the line chart*/
        lineChart.setDescription("");
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");
        lineChart.setData(data);
        lineChart.setVisibleXRangeMaximum(29);
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.animateXY(1700,0000);
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.invalidate();



        lineChartLand.setDescription("");
        lineChartLand.setNoDataTextDescription("You need to provide data for the chart.");
        lineChartLand.setData(data);
        lineChartLand.setVisibleXRangeMaximum(29);
        lineChartLand.setDrawGridBackground(false);
        lineChartLand.setDrawBorders(false);
        lineChartLand.setTouchEnabled(true);
        lineChartLand.setDragEnabled(true);
        lineChartLand.setScaleEnabled(false);
        lineChartLand.setPinchZoom(false);
        lineChartLand.animateXY(1700,0000);
        lineChartLand.setDoubleTapToZoomEnabled(false);
        lineChartLand.invalidate();
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
        Intent intent = new Intent (this, SelectBloodPressureActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}