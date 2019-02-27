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

public class SearchServiceDataObject {

    String city;
    String latitude;
    String longitude;
    String phone;
    String scheduleMonFri;
    String scheduleSat;
    String scheduleSun;
    boolean serviceBloodPressure;
    boolean serviceBloodSugar;
    boolean serviceCholesterol;
    boolean serviceFlu;
    boolean servicePneumonia;
    boolean serviceShingles;
    String state;
    String streetAddress;
    String url;



    String zip;


    SearchServiceDataObject(String city,String latitude, String longitude, String phone, String scheduleMonFri, String scheduleSat, String scheduleSun, boolean serviceBloodPressure, boolean serviceBloodSugar, boolean serviceCholesterol, boolean serviceFlu, boolean servicePneumonia, boolean serviceShingles, String state, String streetAddress, String url, String zip) {
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.scheduleMonFri = scheduleMonFri;
        this.scheduleSat = scheduleSat;
        this.scheduleSun = scheduleSun;
        this.serviceBloodPressure = serviceBloodPressure;
        this.serviceBloodSugar =  serviceBloodSugar;
        this.serviceCholesterol = serviceCholesterol;
        this.serviceFlu = serviceFlu;
        this.servicePneumonia = servicePneumonia;
        this.serviceShingles = serviceShingles;
        this.state = state;
        this.streetAddress = streetAddress;
        this.url = url;
        this.zip = zip;




    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getScheduleMonFri() {
        return scheduleMonFri;
    }

    public void setScheduleMonFri(String scheduleMonFri) {
        this.scheduleMonFri = scheduleMonFri;
    }

    public String getScheduleSat() {
        return scheduleSat;
    }

    public void setScheduleSat(String scheduleSat) {
        this.scheduleSat = scheduleSat;
    }

    public String getScheduleSun() {
        return scheduleSun;
    }

    public void setScheduleSun(String scheduleSun) {
        this.scheduleSun = scheduleSun;
    }

    public boolean isServiceBloodPressure() {
        return serviceBloodPressure;
    }

    public void setServiceBloodPressure(boolean serviceBloodPressure) {
        this.serviceBloodPressure = serviceBloodPressure;
    }

    public boolean isServiceBloodSugar() {
        return serviceBloodSugar;
    }

    public void setServiceBloodSugar(boolean serviceBloodSugar) {
        this.serviceBloodSugar = serviceBloodSugar;
    }

    public boolean isServiceCholesterol() {
        return serviceCholesterol;
    }

    public void setServiceCholesterol(boolean serviceCholesterol) {
        this.serviceCholesterol = serviceCholesterol;
    }

    public boolean isServiceFlu() {
        return serviceFlu;
    }

    public void setServiceFlu(boolean serviceFlu) {
        this.serviceFlu = serviceFlu;
    }

    public boolean isServicePneumonia() {
        return servicePneumonia;
    }

    public void setServicePneumonia(boolean servicePneumonia) {
        this.servicePneumonia = servicePneumonia;
    }

    public boolean isServiceShingles() {
        return serviceShingles;
    }

    public void setServiceShingles(boolean serviceShingles) {
        this.serviceShingles = serviceShingles;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }


}