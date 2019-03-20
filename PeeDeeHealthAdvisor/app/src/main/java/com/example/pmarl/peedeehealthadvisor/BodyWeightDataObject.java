package com.example.pmarl.peedeehealthadvisor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class BodyWeightDataObject {

    private String weightValue;
    private String weightDate;
    private String bodyWeightTime;


    BodyWeightDataObject(Float weight, String date, Long time) {

        weightValue = weight.toString() + " lbs";
        weightDate = date;
        bodyWeightTime = time.toString();

    }

    public String getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(String weightValue) {
        this.weightValue = weightValue;
    }

    public String getWeightDate() {
        return weightDate;
    }

    public void setWeightDate(String weightDate) {
        this.weightDate = weightDate;
    }

    public String getBodyWeightTime() {
        return bodyWeightTime;
    }

    public void setBodyWeightTime(String bodyWeightTime) {
        this.bodyWeightTime = bodyWeightTime;
    }

}