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

import java.util.Date;

public class BloodPressureDataObject {

    private String bpTop;
    private String bpBottom;
    private String bpDate;
    private String bpTime;



    BloodPressureDataObject(Integer top, Integer bottom, String date, Long time) {
        bpTop = top.toString() + " (Systolic)";
        bpBottom = bottom.toString() + " (Diastolic)";
        bpDate = date;
        bpTime = time.toString();

    }

    public String getBpTop() {
        return bpTop;
    }

    public void setBpTop(String bpTop) {
        this.bpTop = bpTop;
    }

    public String getBpBottom() {
        return bpBottom;
    }

    public void setBpBottom(String bpBottom) {
        this.bpBottom = bpBottom;
    }

    public String getBpDate() {
        return bpDate;
    }

    public void setBpDate(String bpDate) {
        this.bpDate = bpDate;
    }

    public String getBpTime() {
        return bpTime;
    }

    public void setBpTime(String bpTime) {
        this.bpTime = bpTime;
    }

}