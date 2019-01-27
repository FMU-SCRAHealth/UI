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

public class BodyWeightGraph extends AppCompatActivity
{
    LineChart lineChart;


    @SuppressLint("SimpleDateFormat")
    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.body_weight_graph);

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
        Cursor cursor = MainActivity.myDB.readBodyWeight();

        /*Creating an array list that will hold the systolic values and the dates that
         * the values were taken on*/
        ArrayList<Entry> weight = new ArrayList<>();



        /*Just a test array for the x values for the graph*/
        final ArrayList<String> xLabels = new ArrayList<>();

        /*Test values for the xLabels array*/
        Integer i = 0;
        String date;
        Date date1;
        long epoch;
        TreeMap<Long,ArrayList<Float>> treeMap = new TreeMap<>();


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
//

                treeMap.put(epoch, new ArrayList<Float>());
                /*weight Added*/
                treeMap.get(epoch).add(Float.parseFloat(cursor.getString(2)));


            } catch (ParseException e)
            {
                e.printStackTrace();
            }

        } while (cursor.moveToPrevious());



        Set<Map.Entry<Long, ArrayList<Float>>> set = treeMap.entrySet();
        Iterator<Map.Entry<Long, ArrayList<Float>>> iterator = set.iterator();
        while(iterator.hasNext())
        {
            Map.Entry<Long, ArrayList<Float>> mEntry = iterator.next();
            ArrayList<Float> arrayList = mEntry.getValue();

            /*Adding the weight value from the DB and the date that corresponds
             * with it*/
            weight.add(new Entry(arrayList.get(0), i));



            /*Adding test values for the systolic and diastolic values*/
            Long epoch1 = mEntry.getKey();

            Date date2 = new Date(epoch1);


            xLabels.add(new SimpleDateFormat("MMM dd").format(date2));

            i++;
        }



        /*Creating the systolic data set and setting different attributes for it*/
        LineDataSet weightSet = new LineDataSet(weight,"Weight");
        weightSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        weightSet.setColor(getResources().getColor(R.color.BodyWeightHuesDark));
        weightSet.setValueTextSize(13f);
        weightSet.setDrawCubic(true);//Allows for curved lines instead of linear lines
        weightSet.setCircleRadius(4f);
        weightSet.setCircleColor(getResources().getColor(R.color.RedHuesLight));
        weightSet.setLineWidth(2f);





        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);
        xAxis.setLabelRotationAngle(-90);


        /*Creating an array list for your data sets
         * "A set of sets"*/
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(weightSet);


        /*creating the data to be plotted in the line chart*/
        LineData data = new LineData(xLabels,dataSets);


        /*Setting the data and attributes for the line chart*/
        lineChart.setDescription("Body Weight");
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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void launchPrevActivity()
    {
        Intent intent = new Intent (this, SelectBodyWeightActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
