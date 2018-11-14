package com.example.pmarl.peedeehealthadvisor;
import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.utils.ViewPortHandler;
//import com.github.mikephil.charting.formatter.AxisValueFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class MonthAxisValueFormatter implements XAxisValueFormatter
{
    long referenceTimestamp; // minimum timestamp in your data set
    DateFormat mDataFormat;
    Date mDate;

    public MonthAxisValueFormatter(long referenceTimestamp) {
        this.referenceTimestamp = referenceTimestamp;
        this.mDataFormat = new SimpleDateFormat("MMM dd", Locale.ENGLISH);
        this.mDate = new Date();
    }


    /**
     * Called when a value from an axis is to be formatted
     * before being drawn. For performance reasons, avoid excessive calculations
     * and memory allocations inside this method.
     *
     * @param value the value to be formatted
     * @param axis  the axis the value belongs to
     * @return
     */

    public String getFormattedValue(float value, AxisBase axis) {
        // convertedTimestamp = originalTimestamp - referenceTimestamp
        long convertedTimestamp = (long) value;

        // Retrieve original timestamp
        long originalTimestamp = referenceTimestamp + convertedTimestamp;

        // Convert timestamp to hour:minute
        return getHour(originalTimestamp);
    }


    public int getDecimalDigits() {
        return 0;
    }

    private String getHour(long timestamp){
        try{
            mDate.setTime(timestamp*1000);
            return mDataFormat.format(mDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }

    @Override
    public String getXValue(String original, int index, ViewPortHandler viewPortHandler) {
        return null;
    }
}
