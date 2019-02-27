package com.example.pmarl.peedeehealthadvisor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
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

import java.util.concurrent.TimeUnit;

public class SearchServiceDataObject {

    String name;
    String address;
    String phone;
    String schedule;
    private View.OnClickListener callClickListener;
    private View.OnClickListener listenerClickListener;

    double distance;
//    boolean serviceBloodPressure;
//    boolean serviceBloodSugar;
//    boolean serviceCholesterol;
//    boolean serviceFlu;
//    boolean servicePneumonia;
//    boolean serviceShingles;
    String services;
    String url;



    SearchServiceDataObject(String locationName, String searchAddress, double searchDistance, String searchPhone, String searchSchedule, String searchServices, String searchUrl) {
        name = locationName;
        address = searchAddress;
        distance = searchDistance;
        phone = searchPhone;
        schedule = searchSchedule;
        services = searchServices;
        address = searchAddress;
        url = searchUrl;

    }


    public String getAddress() {
        return address;
    }


//    public double getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(double latitude) {
//        this.latitude = latitude;
//    }

//    public double getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(double longitude) {
//        this.longitude = longitude;
//    }

    public View.OnClickListener createCall() {
        callClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String temp = "tel:" + phone;
                intent.setData(Uri.parse(temp));
                v.getContext().startActivity(intent);
            }
        };

        return callClickListener;
    }

    public String getName() {
        return name;
    }


    public String getPhone() {
        return phone;
    }



    public double getDistance() {
        return distance;
    }



    public String getSchedule() {
        return schedule;
    }


    public String getUrl() {
        return url;
    }


    public String getServices() {
        return services;
    }








}