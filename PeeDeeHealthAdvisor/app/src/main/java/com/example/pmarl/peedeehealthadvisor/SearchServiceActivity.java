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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SearchServiceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private ImageButton home;
    private Button search;
    private Spinner spinnerRadius;
    private ImageButton bloodPressureSearch;
    private ImageButton bloodSugarSearch;
    private ImageButton cholesterolSearch;
    private ImageButton fluShotSearch;
    private ImageButton ShinglesSearch;
    private ImageButton PneumoniaSearch;
    boolean notClickedBP = true;
    boolean notClickedBS = true;
    boolean notClickedCholesterol = true;
    boolean notClickedFlu = true;
    boolean notClickedShingles = true;
    boolean notClickedPneumonia = true;
    private String TAG = "TESTING: ";
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





    @Override
    protected void  onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_search_service);

        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
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



//
//
//        // Add a new document with a generated ID
//        db.collection("Locations")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error adding document", e);
//                    }
//                });

//        DocumentReference locations = db.collection("Locations").document("CVS Minute Clinic");

        db.collection("Locations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               streetAddress = document.getString("streetAddress");

                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Log.d(TAG, streetAddress);
                                document.getString("city");

                                Log.d(TAG, "City: " + document.getString("city"));
                                Toast toast = Toast.makeText(getApplicationContext(), "City: " + document.getString("city"), Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                LinearLayout toastContentView = (LinearLayout) toast.getView();
                                ImageView imageView = new ImageView(getApplicationContext());
                                imageView.setImageResource(R.drawable.ic_warning);
                                toastContentView.addView(imageView, 0);
                                toast.show();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });






//        myRef.setValue("Hello, World!");

        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });


        this.home = (ImageButton) findViewById(R.id.Home);

        this.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });

        this.search = (Button) findViewById(R.id.Search);

        this.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                launchSearchActivity();
            }
        });

        spinnerRadius =  findViewById(R.id.radiusSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.distances,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRadius.setAdapter(adapter);
        spinnerRadius.setOnItemSelectedListener(this);

        this.bloodPressureSearch = (ImageButton) findViewById(R.id.bloodPressureTEST1);

        this.bloodPressureSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notClickedBP) {
                    bloodPressureSearch.setBackgroundResource(R.drawable.ic_blood_pressure_checked);
                    // make method for query call it here
                    notClickedBP = false;
                }
                else if (!notClickedBP) {
                    bloodPressureSearch.setBackgroundResource(R.drawable.ic_blood_pressure);
                    notClickedBP = true;
                }

            }
        });

        this.bloodSugarSearch = (ImageButton) findViewById(R.id.bloodSugarSearch);

        this.bloodSugarSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notClickedBS) {
                    bloodSugarSearch.setBackgroundResource(R.drawable.ic_blood_sugar_selected);
                    // make method for query call it here
                    notClickedBS = false;
                }
                else if (!notClickedBS) {
                    bloodSugarSearch.setBackgroundResource(R.drawable.ic_blood_sugar);
                    notClickedBS = true;
                }

            }
        });

        this.cholesterolSearch = (ImageButton) findViewById(R.id.cholesterolSearch);

        this.cholesterolSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notClickedCholesterol) {
                    cholesterolSearch.setBackgroundResource(R.drawable.ic_cholesterol_icon_selected);
                    // make method for query call it here
                    notClickedCholesterol = false;
                }
                else if (!notClickedCholesterol) {
                    cholesterolSearch.setBackgroundResource(R.drawable.ic_cholesterol_icon);
                    notClickedCholesterol = true;
                }

            }
        });

        this.fluShotSearch = (ImageButton) findViewById(R.id.fluShotSearch);

        this.fluShotSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notClickedFlu) {
                    fluShotSearch.setBackgroundResource(R.drawable.ic_vaccinations_selected);
                    // make method for query call it here
                    notClickedFlu = false;
                }
                else if (!notClickedFlu) {
                    fluShotSearch.setBackgroundResource(R.drawable.ic_vaccinations);
                    notClickedFlu = true;
                }

            }
        });

        this.ShinglesSearch = (ImageButton) findViewById(R.id.shinglesSearch);

        this.ShinglesSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notClickedShingles) {
                    ShinglesSearch.setBackgroundResource(R.drawable.ic_vaccinations_selected);
                    // make method for query call it here
                    notClickedShingles = false;
                }
                else if (!notClickedShingles) {
                    ShinglesSearch.setBackgroundResource(R.drawable.ic_vaccinations);
                    notClickedShingles = true;
                }

            }
        });

        this.PneumoniaSearch = (ImageButton) findViewById(R.id.pneumoniaSearch);

        this.PneumoniaSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notClickedPneumonia) {
                    PneumoniaSearch.setBackgroundResource(R.drawable.ic_vaccinations_selected);
                    // make method for query call it here
                    notClickedPneumonia = false;
                }
                else if (!notClickedPneumonia) {
                    PneumoniaSearch.setBackgroundResource(R.drawable.ic_vaccinations);
                    notClickedPneumonia = true;
                }

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l)
    {
        if(position != 0)
        {
            String text = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        launchMainActivity();
    }

    private void launchMainActivity()
    {
        Intent intent = new Intent (this, MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void launchSearchActivity()
    {
        Intent intent = new Intent (this, SearchLocationActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}
