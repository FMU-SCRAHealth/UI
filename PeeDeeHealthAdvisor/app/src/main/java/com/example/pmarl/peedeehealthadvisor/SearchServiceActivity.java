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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

public class SearchServiceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LocationListener {
    private ImageButton home;
    private Button search;
    private Spinner spinnerRadius;
    private ImageButton bloodPressureSearch;
    private ImageButton bloodSugarSearch;
    private ImageButton cholesterolSearch;
    private ImageButton fluShotSearch;
    private ImageButton ShinglesSearch;
    private ImageButton PneumoniaSearch;
    protected Location mLastLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private LocationRequest mLocationRequest;

    private String mLatitudeLabel;
    private String mLongitudeLabel;
    private TextView mLatitudeText;
    private TextView mLongitudeText;


    boolean notClickedBP = true;
    boolean notClickedBS = true;
    boolean notClickedCholesterol = true;
    boolean notClickedFlu = true;
    boolean notClickedShingles = true;
    boolean notClickedPneumonia = true;
    private String TAG = "TESTING: ";
//    String city;
//    String latitude;
//    String longitude;
//    String phone;
//    String scheduleMonFri;
//    String scheduleSat;
//    String scheduleSun;
//    boolean serviceBloodPressure;
//    boolean serviceBloodSugar;
//    boolean serviceCholesterol;
//    boolean serviceFlu;
//    boolean servicePneumonia;
//    boolean serviceShingles;
//    String state;
//    String streetAddress;
//    String url;
//    String zip;

    public SearchServiceActivity() {
        boolean notClickedBP = isNotClickedBP();
        boolean notClickedBS = isNotClickedBS();
        boolean notClickedCholesterol = isNotClickedCholesterol();
        boolean notClickedFlu = isNotClickedFlu();
        boolean notClickedShingles = isNotClickedShingles();
        boolean notClickedPneumonia = isNotClickedPneumonia();
    }


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_search_service);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
        } else {
//            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    0);
//            launchPrevActivity();

        }

        mLatitudeLabel = getResources().getString(R.string.diastolic);
        mLongitudeLabel = getResources().getString(R.string.description);
        mLatitudeText = (TextView) findViewById((R.id.date_of_birth));
        mLongitudeText = (TextView) findViewById((R.id.ldlCardTextLabel));


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
                if (notClickedBP == true && notClickedBS == true && notClickedCholesterol == true && notClickedFlu == true && notClickedPneumonia == true && notClickedShingles == true) {
                    showDataIncorrectRange();
                } else {
                    launchSearchActivity();
                }
            }
        });

        spinnerRadius = findViewById(R.id.radiusSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.distances, android.R.layout.simple_spinner_item);
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
                } else if (!notClickedBP) {
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
                } else if (!notClickedBS) {
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
                } else if (!notClickedCholesterol) {
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
                } else if (!notClickedFlu) {
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
                } else if (!notClickedShingles) {
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
                } else if (!notClickedPneumonia) {
                    PneumoniaSearch.setBackgroundResource(R.drawable.ic_vaccinations);
                    notClickedPneumonia = true;
                }

            }
        });


    }

    public boolean isNotClickedBP() {
        return notClickedBP;
    }

    public void setNotClickedBP(boolean notClickedBP) {
        this.notClickedBP = notClickedBP;
    }

    public boolean isNotClickedBS() {
        return notClickedBS;
    }

    public void setNotClickedBS(boolean notClickedBS) {
        this.notClickedBS = notClickedBS;
    }

    public boolean isNotClickedCholesterol() {
        return notClickedCholesterol;
    }

    public void setNotClickedCholesterol(boolean notClickedCholesterol) {
        this.notClickedCholesterol = notClickedCholesterol;
    }

    public boolean isNotClickedFlu() {
        return notClickedFlu;
    }

    public void setNotClickedFlu(boolean notClickedFlu) {
        this.notClickedFlu = notClickedFlu;
    }

    public boolean isNotClickedShingles() {
        return notClickedShingles;
    }

    public void setNotClickedShingles(boolean notClickedShingles) {
        this.notClickedShingles = notClickedShingles;
    }

    public boolean isNotClickedPneumonia() {
        return notClickedPneumonia;
    }

    public void setNotClickedPneumonia(boolean notClickedPneumonia) {
        this.notClickedPneumonia = notClickedPneumonia;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        if (position != 0) {
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

    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void launchSearchActivity() {
        Intent intent = new Intent(this, SearchLocationActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Show images in Toast prompt.
    private void showDataIncorrectRange() {

        Toast toast = Toast.makeText(getApplicationContext(), "No Selection Made!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.ic_warning);
        toastContentView.addView(imageView, 0);
        toast.show();
    }


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

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        if (!checkPermissions()) {
//            requestPermissions();
//        } else {
//            getLastLocation();
//        }
//    }
//
//    private void showSnackbar(final String text) {
//        View container = findViewById(R.id.resourceSearch);
//        if (container != null) {
//            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
//        }
//    }
//
//    private void showSnackbar(final int mainTextStringId, final int actionStringId,
//                              View.OnClickListener listener) {
//        Snackbar.make(findViewById(android.R.id.content),
//                getString(mainTextStringId),
//                Snackbar.LENGTH_INDEFINITE)
//                .setAction(getString(actionStringId), listener).show();
//    }
//
//    @SuppressWarnings("MissingPermission")
//    private void getLastLocation() {
//        mFusedLocationClient.getLastLocation()
//                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        if (task.isSuccessful() && task.getResult() != null) {
//                            mLastLocation = task.getResult();
//
//                            mLastLocation.getLatitude();
//
//                            mLastLocation.getLongitude();
//                        } else {
//                            Log.w(TAG, "getLastLocation:exception", task.getException());
//                            showDataIncorrectRange();
//                        }
//                    }
//                });
//    }
//
//    private boolean checkPermissions() {
//        int permissionState = ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION);
//        return permissionState == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void startLocationPermissionRequest() {
//        ActivityCompat.requestPermissions(SearchServiceActivity.this,
//                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                REQUEST_PERMISSIONS_REQUEST_CODE);
//    }
//
//    private void requestPermissions() {
//        boolean shouldProvideRationale =
//                ActivityCompat.shouldShowRequestPermissionRationale(this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION);
//
//        // Provide an additional rationale to the user. This would happen if the user denied the
//        // request previously, but didn't check the "Don't ask again" checkbox.
//        if (shouldProvideRationale) {
//
//            showSnackbar(R.string.project_id, android.R.string.ok,
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            // Request permission
//                            startLocationPermissionRequest();
//                        }
//                    });
//
//        } else {
//            Log.i(TAG, "Requesting permission");
//            // Request permission. It's possible this can be auto answered if device policy
//            // sets the permission in a given state or the user denied the permission
//            // previously and checked "Never ask again".
//            startLocationPermissionRequest();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        Log.i(TAG, "onRequestPermissionResult");
//        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
//            if (grantResults.length <= 0) {
//                // If user interaction was interrupted, the permission request is cancelled and you
//                // receive empty arrays.
//                Log.i(TAG, "User interaction was cancelled.");
//            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted.
//                getLastLocation();
//            } else {
//                // Permission denied.
//
//                // Notify the user via a SnackBar that they have rejected a core permission for the
//                // app, which makes the Activity useless. In a real app, core permissions would
//                // typically be best requested during a welcome-screen flow.
//
//                // Additionally, it is important to remember that a permission might have been
//                // rejected without asking the user for permission (device policy or "Never ask
//                // again" prompts). Therefore, a user interface affordance is typically implemented
//                // when permissions are denied. Otherwise, your app could appear unresponsive to
//                // touches or interactions which have required permissions.
//                showSnackbar(R.string.bottomValue, R.string.google_app_id,
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                // Build intent that displays the App settings screen.
//                                Intent intent = new Intent();
//                                intent.setAction(
//                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                Uri uri = Uri.fromParts("package",
//                                        BuildConfig.APPLICATION_ID, null);
//                                intent.setData(uri);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                            }
//                        });
//            }
//        }
//    }

    private void createLocationRequest(){
        mLocationRequest = new LocationRequest();

        // 0 means here receive location as soon as possible
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);

        // setNumUpdates(1); stops location requests after receiving 1 location
        mLocationRequest.setNumUpdates(1);

        // PRIORITY_HIGH_ACCURACY option uses your GPS
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

}
