/*
    Author: Patrick Marlowe
    Email Address: pmarlowe782@gmail.com
    Written: June 22, 2018
    Last Updated: June 25, 2018

    Compilation:
    Execution:

    Description of Execution:
 */
package com.fmu.pmarl.peedeehealthadvisor;

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

public class CholesterolGraph extends AppCompatActivity
{
    LineChart lineChart;
    LineChart lineChartLand;
    TimeZone tz = Calendar.getInstance().getTimeZone();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList results = new ArrayList<CholesterolDataObject>(); // make sure to change this to Cholesterol Data Object


    @SuppressLint("SimpleDateFormat")
    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.cholesterol_graph);

        ImageButton home = findViewById(R.id.Home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

        // finding the graph by element id.
        lineChart =  findViewById(R.id.lineGraph);
        lineChartLand =  findViewById(R.id.lineGraphCholesterolLand);

        /*Creating a cursor, which is a table that stores the data from
         * the sql query*/
        Cursor cursor = MainActivity.myDB.readCholesterol();

        /*Creating an array list that will hold the HDL values and the dates that
         * the values were taken on*/
        ArrayList<Entry> hdl = new ArrayList<>();

        /*Creating an array list that will hold the LDL values and the dates that
         * the values were taken on*/
        ArrayList<Entry> ldl = new ArrayList<>();

        /*Creating an array list that will hold the TRIG values and the dates that the values
        * were taken*/
        ArrayList<Entry> trig = new ArrayList<>();

        ArrayList<Entry> tc = new ArrayList<>();

        /*Just a test array for the x values for the graph*/
        final ArrayList<String> xLabels = new ArrayList<>();

        /*Test values for the xLabels array*/
        Integer i = 0;
        String date;
        String dateCard;
        Date date1;
        long epoch;
        TreeMap<Long,CholesterolValues> treeMap = new TreeMap<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_cholesterol);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyCholesterolRecyclerViewAdapter(results); // make sure to check this
        mRecyclerView.setAdapter(mAdapter);

        /*Loop through the rows of the cursor and set the (y,x) values
         * cursor.moveToNext() returns a boolean value and performs an action to move to the
         * next cursor*/
        cursor.moveToLast();
        do {


            try
            {
                if(cursor.getCount() > 0) {
                    date = cursor.getString(0) + cursor.getString(1);
                    date1 = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz").parse(date);

                    if (tz.inDaylightTime(date1)) {
                        epoch = date1.getTime() - 3600000;
                    } else {
                        epoch = date1.getTime();
                    }


                    treeMap.put(epoch, new CholesterolValues(Float.parseFloat(cursor.getString(2)),
                            Float.parseFloat(cursor.getString(3)),
                            Float.parseFloat(cursor.getString(4)),
                            Float.parseFloat(cursor.getString(5))));
                } else {
                    launchPrevActivity();
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        } while (cursor.moveToPrevious());



        Set<Map.Entry<Long, CholesterolValues>> set = treeMap.entrySet();
        Iterator<Map.Entry<Long, CholesterolValues>> iterator = set.iterator();
        while(iterator.hasNext())
        {
            Map.Entry<Long, CholesterolValues> mEntry = iterator.next();
            CholesterolValues cholesterolValues = mEntry.getValue();

            /*Adding the hdl value from the DB and the date that corresponds
             * with it*/
            hdl.add(new Entry(cholesterolValues.getHdl(), i));

            /*Adding the ldl value from the DB and the date that corresponds
             * with it*/
            ldl.add(new Entry(cholesterolValues.getLdl(), i));

            /*Adding the trig value from the DB and the date that corresponds with it*/
            trig.add(new Entry(cholesterolValues.getTrig(),i));

            tc.add(new Entry(cholesterolValues.getTc(), i));

            /*Adding test values for the hdl and ldl values*/
            Long epoch1 = mEntry.getKey();

            Date date2 = new Date(epoch1);

            dateCard = new SimpleDateFormat("MMM dd hh:mm a").format(date2);

            xLabels.add(new SimpleDateFormat("MMM dd").format(date2));

            CholesterolDataObject cholesterolEntry = new CholesterolDataObject(cholesterolValues.getTc(), cholesterolValues.getHdl(), cholesterolValues.getTrig(), cholesterolValues.getLdl(),dateCard, epoch1);

            results.add(cholesterolEntry);

            i++;
        }



        /*Creating the hdl data set and setting different attributes for it*/
        LineDataSet hdlSet = new LineDataSet(hdl,"HDL");
        hdlSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        hdlSet.setColor(getResources().getColor(R.color.YellowHuesDark));
        hdlSet.setValueTextSize(0f);
        hdlSet.setDrawCubic(true);//Allows for curved lines instead of linear lines
        hdlSet.setCircleRadius(8f);
        hdlSet.setCircleColor(getResources().getColor(R.color.YellowHuesDark));
        hdlSet.setLineWidth(5f);


        /*Creating the ldl data set and setting different attributes for it*/
        LineDataSet ldlSet = new LineDataSet(ldl,"LDL");
        ldlSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        ldlSet.setColor(getResources().getColor(R.color.YellowHuesLight));
        ldlSet.setValueTextSize(0f);
        ldlSet.setDrawCubic(true);//Allows for curved lines instead of linear lines
        ldlSet.setCircleRadius(8f);
        ldlSet.setCircleColor(getResources().getColor(R.color.YellowHuesDark));
        ldlSet.setLineWidth(5f);
        ldlSet.enableDashedLine(10f,5f,0f);


        /*Creating the trig data set and setting different attributes for it*/
        LineDataSet trigSet = new LineDataSet(trig,"TRIG");
        trigSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        trigSet.setColor(getResources().getColor(R.color.YellowHuesDark));
        trigSet.setValueTextSize(0f);
        trigSet.setDrawCubic(true);//Allows for curved lines instead of linear lines
        trigSet.setCircleRadius(8f);
        trigSet.setCircleColor(getResources().getColor(R.color.YellowHuesLight));
        trigSet.setLineWidth(5f);
        trigSet.enableDashedLine(10f,5f,0f);

        /*Creating the tc data set and setting different attributes for it*/
        LineDataSet tcSet = new LineDataSet(tc,"TC");
        tcSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        tcSet.setColor(getResources().getColor(R.color.YellowHuesLight));
        tcSet.setValueTextSize(0f);
        tcSet.setDrawCubic(true);//Allows for curved lines instead of linear lines
        tcSet.setCircleRadius(8f);
        tcSet.setCircleColor(getResources().getColor(R.color.YellowHuesDark));
        tcSet.setLineWidth(5f);



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
        dataSets.add(hdlSet);
        dataSets.add(ldlSet);
        dataSets.add(trigSet);
        dataSets.add(tcSet);

        /*creating the data to be plotted in the line chart*/
        LineData data = new LineData(xLabels,dataSets);


        /*Setting the data and attributes for the line chart*/
        lineChart.setDescription("");
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");
        lineChart.setData(data);
        lineChart.setVisibleXRangeMaximum(30);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.animateXY(1700,0000);
        lineChart.setPinchZoom(true);
        lineChart.setDoubleTapToZoomEnabled(true);
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
        Intent intent = new Intent (this, SelectCholesterolActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}