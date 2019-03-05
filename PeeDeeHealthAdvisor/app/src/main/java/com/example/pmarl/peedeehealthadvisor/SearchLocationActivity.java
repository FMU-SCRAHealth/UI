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

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.Manifest;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.data.Entry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import java.util.Comparator;


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
    boolean sendVar = false;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;

    private ProgressBar spinner;

    // this of way to select values you want by selection
    SearchServiceActivity valuesClicked = new SearchServiceActivity();




    @Override
    //This is the onCreate method or Min method that is activated when the
    //class is launched
    protected void  onCreate(Bundle saveInstanceState)
    {

        super.onCreate(saveInstanceState);

        //Sets the layout to the activity_search_location layout
        setContentView(R.layout.activity_card_view_search_services);


        spinner = (ProgressBar)findViewById(R.id.progressBar1);

        spinner.setVisibility(View.VISIBLE);


        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        SearchLocationActivity test = new SearchLocationActivity();

        final LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        db.collection("Locations").orderBy("location") // orders largest to smallest
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {

                                    // getting current location
                                    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                    Location locationGPS = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                                    isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

                                    isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                                    if (isNetworkEnabled) {

                                        lm.requestLocationUpdates(
                                                LocationManager.NETWORK_PROVIDER,
                                                MIN_TIME_BW_UPDATES,
                                                MIN_DISTANCE_CHANGE_FOR_UPDATES, listener);

                                        if (lm != null) {
                                            locationGPS = lm
                                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                                            if (locationGPS != null) {

                                                latitudeGPS = locationGPS.getLatitude();
                                                longitudeGPS = locationGPS.getLongitude();
                                            }
                                        }

                                    }

                                    if(isGPSEnabled) {
                                        if(locationGPS == null) {
                                            lm.requestLocationUpdates(
                                                    LocationManager.GPS_PROVIDER,
                                                    MIN_TIME_BW_UPDATES,
                                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, listener);
                                            if(lm != null) {
                                                locationGPS = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                                                if(locationGPS != null) {
                                                    latitudeGPS = locationGPS.getLatitude();
                                                    longitudeGPS = locationGPS.getLongitude();
                                                }
                                            }
                                        }
                                    }

                                    Location locationService = new Location("");

                                    // pulling the location values
                                    String name = document.getString("clinicName");
                                    String city = document.getString("city");
                                    double latitude = document.getGeoPoint("location").getLatitude();
                                    double longitude = document.getGeoPoint("location").getLongitude();
                                    String phone = document.getString("phone");
                                    String schedule = document.getString("hoursNormal");
                                    String services = "No Services";
                                    String bloodPressure = "";
                                    String bloodSugar = "";
                                    String cholesterol = "";
                                    String fluShot = "";
                                    String pneumonia = "";
                                    String shingles = "";
                                    locationService.setLatitude(latitude);
                                    locationService.setLongitude(longitude);

                                    if (dayOfWeek == 7) {
                                        schedule = document.getString("hoursSat");
                                    } else if (dayOfWeek == 1) {
                                        schedule = document.getString("hoursSun");
                                    } else {
                                        schedule = document.getString("hoursNormal");
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

                                    if (serviceBloodPressure == true && serviceBloodSugar == true && serviceCholesterol == true
                                            && serviceFlu == true && servicePneumonia == true && serviceShingles == true) {
                                        services = "All Services Available";
                                    } else {
                                        services = bloodPressure + " " + bloodSugar + " " + cholesterol + " " + fluShot + " " + pneumonia + " " + shingles;
                                    }

                                    double distance = 0.0;
                                    String state = document.getString("state");
                                    String streetAddress = document.getString("address");
                                    String url = document.getString("website");
                                    String zip = document.getString("zip");

                                    if (locationGPS!= null) {
                                        distance = locationGPS.distanceTo(locationService) / 1000;
                                    } else {
                                        showDataError();
                                        launchPrevActivity();
                                    }

                                    String address = streetAddress + ", " + city + ", " + state + ", " + zip;
                                Log.d("TESTING", document.getId() + " => " + document.getData());
                                Log.d("TESTING", String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)));
                                    SearchServiceDataObject resultsObject = new SearchServiceDataObject(name, address, distance, phone, schedule, services, url, latitude, longitude);
//                                treeMap.put(distance, new ArrayList<SearchServiceDataObject>());
//                                treeMap.get(distance).add(resultsObject);



                                    results.add(resultsObject);

                                } catch (SecurityException e) {
                                    e.printStackTrace();
                                    showDataError();
//                                    launchPrevActivity();
                                }
                            }


                            send(results); // this sends the results list to the RecyclerViewAdapter for the Card Views


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

    private void launchMainActivity() {
        Intent intent = new Intent (this, MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void launchPrevActivity() {

        // this will reset the boolean values of the selection buttons **IMPORTANT**
        valuesClicked.onRestart();

        // This will reset the adapter each time so it is freshly updated on each query.
//        results.removeAll(results);
//        mAdapter.notifyDataSetChanged();
//        mRecyclerView.setAdapter(mAdapter);

        Intent intent = new Intent(this, SearchServiceActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        if(position != 0)
        {
            String text = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    /* This method is for sending the objects to the Recycler Adapter after all have been loaded into the ArrayList from Firebase*/
    public void send(ArrayList<SearchServiceDataObject> list) {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_search_services);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        ArrayList<SearchServiceDataObject> newList = new ArrayList<>();

        for(int i = 0; i < list.size(); i++) {
            if(valuesClicked.isClickedBP() && (list.get(i).getServices().contains("Blood Pressure") || list.get(i).getServices().contains("All Services Available"))) {
                newList.add(list.get(i));
                Log.d("TESTBP", list.get(i).getName());
            }

            if(valuesClicked.isClickedBS() && (list.get(i).getServices().contains("Blood Sugar") || list.get(i).getServices().contains("All Services Available"))) {
                newList.add(list.get(i));
                Log.d("TESTBS", list.get(i).name);
            }

            if(valuesClicked.isClickedCholesterol() && (list.get(i).getServices().contains("Cholesterol") || list.get(i).getServices().contains("All Services Available"))) {
                newList.add(list.get(i));
                Log.d("TESTCHOL", list.get(i).name);
            }

            if(valuesClicked.isClickedFlu() && (list.get(i).getServices().contains("Flu Shot") || list.get(i).getServices().contains("All Services Available"))) {
                newList.add(list.get(i));
                Log.d("TESTFLU", list.get(0).services);
            }

            if(valuesClicked.isClickedPneumonia() && (list.get(i).getServices().contains("Pneumonia") || list.get(i).getServices().contains("All Services Available"))) {
                newList.add(list.get(i));
                Log.d("TESTPNEU", list.get(0).services);
            }

            if(valuesClicked.isClickedShingles() && (list.get(i).getServices().contains("Shingles") || list.get(i).getServices().contains("All Services Available"))) {
                newList.add(list.get(i));
                Log.d("TESTSHING", list.get(0).services);
            }

            Log.d("TESTLOOP",list.get(i).getName());

        }

        // this sorts the ArrayList by distance in the app.
        newList = getSortedLocationByDistance(newList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        Collections.reverse(newList); // flip to show shortest distance first.
        mAdapter = new MySearchResultRecyclerViewAdapter(newList); // make sure to change this up copied. This is where the list is passed to the other class
        mRecyclerView.setAdapter(mAdapter);
        spinner.setVisibility(View.GONE);

    } // end of send method

    // Show images in Toast prompt.
    private void showDataError() {

        Toast toast = Toast.makeText(getApplicationContext(), "Can't Find Location. Make Sure Location Is On!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.ic_error);
        toastContentView.addView(imageView, 0);
        toast.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        valuesClicked.setClickedBP(false);
        valuesClicked.setClickedBS(false);
        valuesClicked.setClickedCholesterol(false);
        valuesClicked.setClickedFlu(false);
        valuesClicked.setClickedPneumonia(false);
        valuesClicked.setClickedShingles(false);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
//        savedInstanceState.clear();
        valuesClicked.setClickedBP(false);
        valuesClicked.setClickedBS(false);
        valuesClicked.setClickedCholesterol(false);
        valuesClicked.setClickedFlu(false);
        valuesClicked.setClickedPneumonia(false);
        valuesClicked.setClickedShingles(false);

        // etc.
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

//    @Override
//    public int compareTo(SearchServiceDataObject location) {
//        return (location.getDistance() < candidate.getAge() ? -1 :
//                (this.getAge() == candidate.getAge() ? 0 : 1));
//    }


    // this method sorts the locations by distance.
    public static Comparator<SearchServiceDataObject> ageComparator = new Comparator<SearchServiceDataObject>() {
        @Override
        public int compare(SearchServiceDataObject jc1, SearchServiceDataObject jc2) {
            return (jc2.getDistance() < jc1.getDistance() ? -1 :
                    (jc2.getDistance() == jc1.getDistance() ? 0 : 1));
        }
    };

    // this method sorts the list itself by calling the above method
    public ArrayList<SearchServiceDataObject> getSortedLocationByDistance(ArrayList<SearchServiceDataObject> list) {
        Collections.sort(list, SearchLocationActivity.ageComparator);
        return list;
    }
}
