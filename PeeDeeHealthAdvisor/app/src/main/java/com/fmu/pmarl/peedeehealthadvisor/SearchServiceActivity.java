/*
    Author: Patrick Marlowe
    Email Address: pmarlowe782@gmail.com
    Written: June 22, 2018
    Last Updated: June 25, 2018

    Compilation:
    Execution:

    Description of Execution:
 */
package com.fmu.pmarl.peedeehealthadvisor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

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
    private ImageButton hepbSearch;
    private ImageButton tetanusSearch;
    protected Location mLastLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private LocationRequest mLocationRequest;

    private String mLatitudeLabel;
    private String mLongitudeLabel;
    private TextView mLatitudeText;
    private TextView mLongitudeText;



    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    public boolean canGetLocation = false;

    Location location;

    double latitude;
    double longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    protected LocationManager locationManager;




    private static boolean clickedBP = false;
    private static boolean clickedBS = false;
    private static boolean clickedCholesterol = false;
    private static boolean clickedFlu = false;
    private static boolean clickedShingles = false;
    private static boolean clickedPneumonia = false;
    private static boolean clickedHepB = false;
    private static boolean clickedTetanus = false;

    private String TAG = "TESTING: ";

    public SearchServiceActivity() {

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
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    0);



        }

        mLatitudeLabel = getResources().getString(R.string.diastolic);
        mLongitudeLabel = getResources().getString(R.string.description);
        mLatitudeText = (TextView) findViewById((R.id.date_of_birth));
        mLongitudeText = (TextView) findViewById((R.id.ldlCardTextLabel));

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
                if (clickedBP == false && clickedBS == false && clickedCholesterol == false && clickedFlu == false && clickedPneumonia == false && clickedShingles == false && clickedHepB == false && clickedTetanus == false) {
                    showDataIncorrectRange();
                } else {
                    launchSearchActivity();
                }
            }
        });


        /* BELOW * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
        *  The following below change the selection icons on the buttons for searching.            *
        *  false will be the default and the onRestart() at the bottom is crucial to query results *
        *  by resetting the boolean values on each page restart.                                   *
        *  Make sure that you follow the format and get them switching values.                     *
        * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        this.bloodPressureSearch = (ImageButton) findViewById(R.id.bloodPressureTEST1);

        this.bloodPressureSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clickedBP) {
                    bloodPressureSearch.setBackgroundResource(R.drawable.ic_blood_pressure_checked);

                    clickedBP = true;
                } else if (clickedBP) {
                    bloodPressureSearch.setBackgroundResource(R.drawable.ic_blood_pressure);
                    clickedBP = false;
                }

            }
        });

        this.bloodSugarSearch = (ImageButton) findViewById(R.id.bloodSugarSearch);

        this.bloodSugarSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clickedBS) {
                    bloodSugarSearch.setBackgroundResource(R.drawable.ic_blood_sugar_selected);

                    clickedBS = true;
                } else if (clickedBS) {
                    bloodSugarSearch.setBackgroundResource(R.drawable.ic_blood_sugar);
                    clickedBS = false;
                }

            }
        });

        this.cholesterolSearch = (ImageButton) findViewById(R.id.cholesterolSearch);

        this.cholesterolSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clickedCholesterol) {
                    cholesterolSearch.setBackgroundResource(R.drawable.ic_cholesterol_icon_selected);

                    clickedCholesterol = true;
                } else if (clickedCholesterol) {
                    cholesterolSearch.setBackgroundResource(R.drawable.ic_cholesterol_icon);
                    clickedCholesterol = false;
                }

            }
        });

        this.fluShotSearch = (ImageButton) findViewById(R.id.fluShotSearch);

        this.fluShotSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clickedFlu) {
                    fluShotSearch.setBackgroundResource(R.drawable.ic_vaccinations_selected);

                    clickedFlu = true;
                } else if (clickedFlu) {
                    fluShotSearch.setBackgroundResource(R.drawable.ic_vaccinations);
                    clickedFlu = false;
                }

            }
        });

        this.ShinglesSearch = (ImageButton) findViewById(R.id.shinglesSearch);

        this.ShinglesSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clickedShingles) {
                    ShinglesSearch.setBackgroundResource(R.drawable.ic_vaccinations_selected);

                    clickedShingles = true;
                } else if (clickedShingles) {
                    ShinglesSearch.setBackgroundResource(R.drawable.ic_vaccinations);
                    clickedShingles = false;
                }

            }
        });

        this.PneumoniaSearch = (ImageButton) findViewById(R.id.pneumoniaSearch);

        this.PneumoniaSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clickedPneumonia) {
                    PneumoniaSearch.setBackgroundResource(R.drawable.ic_vaccinations_selected);

                    clickedPneumonia = true;
                } else if (clickedPneumonia) {
                    PneumoniaSearch.setBackgroundResource(R.drawable.ic_vaccinations);
                    clickedPneumonia = false;
                }

            }
        });

        this.hepbSearch = (ImageButton) findViewById(R.id.hepbSearch);

        this.hepbSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clickedHepB) {
                    hepbSearch.setBackgroundResource(R.drawable.ic_vaccinations_selected);

                    clickedHepB = true;
                } else if (clickedHepB) {
                    hepbSearch.setBackgroundResource(R.drawable.ic_vaccinations);
                    clickedHepB = false;
                }
            }
        });

        this.tetanusSearch = (ImageButton) findViewById(R.id.tetanusSearch);

        this.tetanusSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clickedTetanus) {
                    tetanusSearch.setBackgroundResource(R.drawable.ic_vaccinations_selected);

                    clickedTetanus = true;
                } else if (clickedTetanus) {
                    tetanusSearch.setBackgroundResource(R.drawable.ic_vaccinations);
                    clickedTetanus = false;
                }
            }
        });


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


    public boolean isClickedBP() {
        return clickedBP;
    }

    public void setClickedBP(boolean clickedBP) {
        this.clickedBP = clickedBP;
    }

    public boolean isClickedBS() {
        return clickedBS;
    }

    public void setClickedBS(boolean clickedBS) {
        this.clickedBS = clickedBS;
    }

    public boolean isClickedCholesterol() {
        return clickedCholesterol;
    }

    public void setClickedCholesterol(boolean clickedCholesterol) {
        this.clickedCholesterol = clickedCholesterol;
    }

    public boolean isClickedFlu() {
        return clickedFlu;
    }

    public void setClickedFlu(boolean clickedFlu) {
        this.clickedFlu = clickedFlu;
    }

    public boolean isClickedShingles() {
        return clickedShingles;
    }

    public void setClickedShingles(boolean clickedShingles) {
        this.clickedShingles = clickedShingles;
    }

    public boolean isClickedPneumonia() {
        return clickedPneumonia;
    }

    public void setClickedPneumonia(boolean clickedPneumonia) {
        this.clickedPneumonia = clickedPneumonia;
    }

    public boolean isClickedHepB() {
        return clickedHepB;
    }

    public void setClickedHepB(boolean clickedHepB) {
        this.clickedHepB = clickedHepB;
    }

    public boolean isClickedTetanus() {
        return clickedTetanus;
    }

    public void setClickedTetanus(boolean clickedTetanus) {
        this.clickedTetanus = clickedTetanus;
    }


    // this method is called in SearchLocationActivity to reset the boolean values each time the screen is reloaded
    @Override
    protected void onRestart() {
        super.onRestart();

        clickedBP = false;
        clickedBS = false;
        clickedCholesterol = false;
        clickedFlu = false;
        clickedPneumonia = false;
        clickedShingles = false;
        clickedHepB = false;
        clickedTetanus = false;


    }

}
