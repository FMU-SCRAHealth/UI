/*
    Author: Patrick Marlowe
    Email Address: pmarlowe782@gmail.com
    Written: June 22, 2018
    Last Updated: June 25, 2018

    Compilation:
    Execution:

    Description of Execution:
 */
package com.example.pmarl.peedeehealthadvisor;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class SearchLocationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList results = new ArrayList<SearchServiceDataObject>();
    private ImageButton home;               //Home Button
    private String TAG = "TESTING: ";
//    Date today = new Date();
    Calendar calendar = Calendar.getInstance();
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    double longitudeGPS = 0;
    double latitudeGPS = 0;
    Location locationGPS;
    Location locationService;





    @Override
    //This is the onCreate method or Min method that is activated when the
    //class is launched
    protected void  onCreate(Bundle saveInstanceState)
    {

        super.onCreate(saveInstanceState);

        //Sets the layout to the activity_search_location layout
        setContentView(R.layout.activity_card_view_search_services);



        //List of the instantiated attributes

//        String city;
//        String latitude;
//        String longitude;
//        String phone;
//        String scheduleMonFri;
//        String scheduleSat;
//        String scheduleSun;
//        boolean serviceBloodPressure;
//        boolean serviceBloodSugar;
//        boolean serviceCholesterol;
//        boolean serviceFlu;
//        boolean servicePneumonia;
//        boolean serviceShingles;
//        String state;
//        String streetAddress;
//        String url;
//        String zip;
//        String address;

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_search_services);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAllergiesRecyclerViewAdapter(results); // make sure to change this up copied
        mRecyclerView.setAdapter(mAdapter);



        FirebaseFirestore db = FirebaseFirestore.getInstance();

//      Create a new user with a first and last name EXAMPLE FOR ADDING
//        Map<String, Object> locations = new TreeMap<>();
//        locations.put("city", city);
//        locations.put("latitude", latitude);
//        locations.put("longitude", longitude);
//        locations.put("phone", phone);
//        locations.put("scheduleMonFri", scheduleMonFri);
//        locations.put("scheduleSat", scheduleSat);
//        locations.put("scheduleSun", scheduleSun);
//        locations.put("serviceBloodPressure", serviceBloodPressure);
//        locations.put("serviceBloodSugar", serviceBloodSugar);
//        locations.put("serviceCholesterol", serviceCholesterol);
//        locations.put("serviceFlu", serviceFlu);
//        locations.put("servicePneumonia", servicePneumonia);
//        locations.put("serviceShingles", serviceShingles);
//        locations.put("state", state);
//        locations.put("streetAddress", streetAddress);
//        locations.put("url", url);
//        locations.put("zip", zip);


        db.collection("Locations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String city = document.getString("city");
                                double latitude = document.getGeoPoint("latitude").getLatitude();
                                double longitude = document.getGeoPoint("latitude").getLongitude();
                                String phone = document.getString("phone");
                                String schedule = document.getString("scheduleMonFri");
                                String services = "No Services";
                                String bloodPressure = "";
                                String bloodSugar = "";
                                String cholesterol = "";
                                String fluShot = "";
                                String pneumonia = "";
                                String shingles = "";

                                if (dayOfWeek == 7 ) {
                                    schedule = document.getString("scheduleSat");
                                }
                                else if(dayOfWeek == 0) {
                                    schedule = document.getString("scheduleSun");
                                }

                                boolean serviceBloodPressure = document.getBoolean("serviceBloodPressure");
                                boolean serviceBloodSugar = document.getBoolean("serviceBloodSugar");
                                boolean serviceCholesterol = document.getBoolean("serviceCholesterol");
                                boolean serviceFlu = document.getBoolean("serviceFlu");
                                boolean servicePneumonia = document.getBoolean("servicePneumonia");
                                boolean serviceShingles = document.getBoolean("serviceShingles");

                                if (serviceBloodPressure == true) {
                                    bloodPressure = "Blood Pressure";
                                }
                                if (serviceBloodSugar == true) {
                                    bloodSugar = "Blood Sugar";
                                }
                                if (serviceCholesterol == true) {
                                    cholesterol = "Cholesterol";
                                }
                                if (serviceFlu == true) {
                                    fluShot = "Flu Shot";
                                }
                                if (servicePneumonia == true) {
                                    pneumonia = "Pneumonia";
                                }
                                if (serviceShingles == true) {
                                    shingles = "Shingles";
                                }

                                services = bloodPressure + " " + bloodSugar + " " + cholesterol + " " + fluShot + " " + pneumonia + " " + shingles;

                                String state = document.getString("state");
                                String streetAddress = document.getString("streetAddress");
                                String url = document.getString("url");
                                String zip = document.getString("zip");
                                double distance = 7;

                                String address = streetAddress + ", " + city + ", " + state + ", " + zip;
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Log.d(TAG, address);
                                Log.d(TAG, schedule);
                                Log.d(TAG, services);
//                                document.getString("city");

//                                Log.d(TAG, "City: " + document.getString("city"));

                                SearchServiceDataObject resultsObject = new SearchServiceDataObject(address, distance, phone, schedule, services, url);
                                results.add(resultsObject);
                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


        this.home = (ImageButton) findViewById(R.id.Home);
        this.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });
    }

    @Override
    public void onBackPressed() {
        launchPrevActivity();
    }

    private void launchMainActivity()
    {
        Intent intent = new Intent (this, MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void launchPrevActivity()
    {
        Intent intent = new Intent(this, SearchServiceActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

//    public double getDistance(double locationLat, double locationLong) {
//        try {
//            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//            Location locationGPS = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//            longitudeGPS = locationGPS.getLongitude();
//            latitudeGPS = locationGPS.getLatitude();
//            Location locationService = new Location("");
//            locationService.setLatitude(locationLat);
//            locationService.setLongitude(locationLong);
//
//
//
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
//
//        return locationGPS.distanceTo(locationService);
//
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l)
    {
        if(position != 0)
        {
            String text = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
