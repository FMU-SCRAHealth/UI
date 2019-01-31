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

public class BloodSugarDataObject {

    private String bsValue;
    private String bsFasting;
    private String bsDate;


    BloodSugarDataObject(Float top, Boolean fasting, String date) {

        bsValue = top.toString() + " mg/dL";
        if(fasting.equals(true)) {
            bsFasting = "Fasting";
        }
        else {
            bsFasting = "Non-Fasting";
        }

        bsDate = date;

    }

    public String getBsValue() {
        return bsValue;
    }

    public void setBsValue(String bsValue) {
        this.bsValue = bsValue;
    }

    public String getBsFasting() {
        return bsFasting;
    }

    public void setBsFasting(String bsFasting) {
        this.bsFasting = bsFasting;
    }

    public String getBsDate() {
        return bsDate;
    }

    public void setBsDate(String bsDate) {
        this.bsDate = bsDate;
    }



}