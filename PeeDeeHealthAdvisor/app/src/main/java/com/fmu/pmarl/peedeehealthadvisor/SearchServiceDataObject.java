package com.fmu.pmarl.peedeehealthadvisor;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;

import java.util.Locale;

public class SearchServiceDataObject {

    String name;
    String address;
    String phone;
    String schedule;
    private View.OnClickListener callClickListener;
    private View.OnClickListener listenerClickListener;
    private View.OnClickListener mapClickListener;
    private View.OnClickListener urlClickListener;

    double distance;
//    boolean serviceBloodPressure;
//    boolean serviceBloodSugar;
//    boolean serviceCholesterol;
//    boolean serviceFlu;
//    boolean servicePneumonia;
//    boolean serviceShingles;
    String services;
    String url;
    double latitude;
    double longitude;



    SearchServiceDataObject(String locationName, String searchAddress, double searchDistance, String searchPhone, String searchSchedule, String searchServices, String searchUrl, double searchLatitude, double searchLongitude) {
        name = locationName;
        address = searchAddress;
        distance = searchDistance;
        phone = searchPhone;
        schedule = searchSchedule;
        services = searchServices;
        address = searchAddress;
        url = searchUrl;
        latitude = searchLatitude;
        longitude= searchLongitude;

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

    public View.OnClickListener openMap() {
        mapClickListener = (new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", latitude, longitude, "Getting Directions");
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setPackage("com.google.android.apps.maps");
                        v.getContext().startActivity(intent);
                    }
                }, 1000);
            }
        });

        return mapClickListener;
    }

    public View.OnClickListener openURL() {
        urlClickListener = (new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Uri uriUrl = Uri.parse("http://" + url);
                        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                        v.getContext().startActivity(launchBrowser);
                    }
                }, 1000);
            }
        });

        return urlClickListener;
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