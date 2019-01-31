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

public class VaccinationsDataObject {



    private String vaccinationName;
    private String vaccinationTaken;
    private String vaccinationDate;



    VaccinationsDataObject(String name, String taken, String date) {
        vaccinationName = name;
        vaccinationTaken = taken;
        vaccinationDate = date;

    }


    public String getVaccinationName() {
        return vaccinationName;
    }

    public void setVaccinationName(String vaccinationName) {
        this.vaccinationName = vaccinationName;
    }

    public String getVaccinationTaken() {
        return vaccinationTaken;
    }

    public void setVacciantionTaken(String vaccinationTaken) {
        this.vaccinationTaken = vaccinationTaken;
    }

    public String getVaccinationDate() {
        return vaccinationDate;
    }

    public void setVaccinationDate(String vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }

}