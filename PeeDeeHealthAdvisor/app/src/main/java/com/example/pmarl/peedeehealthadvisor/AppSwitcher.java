package com.example.pmarl.peedeehealthadvisor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AppSwitcher extends AppCompatActivity {

    private ViewPager mSlideViewPagerSwitcher;
    private LinearLayout mDotLayoutSwitcher;
//    private ImageButton home;


    private TextView[] mDotsSwitcher;

    private AppSwitcherAdapter sliderAdapterSwitcher;

    private Button mNextBtn;
    private ImageButton bloodPressure;
    private ImageButton bloodSugar;
    private ImageButton cholesterol;
    private ImageButton vaccinatons;

    private int mCurrentPage;


    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.card_view);

//        home = (ImageButton) findViewById(R.id.Home);
//
//        home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                launchMainActivity();
//            }
//        });

        mSlideViewPagerSwitcher = (ViewPager) findViewById(R.id.slideViewPagerSwitcher);
        mDotLayoutSwitcher = (LinearLayout) findViewById(R.id.dotsLayoutSwitcher);

      mNextBtn = (Button) findViewById(R.id.finishButton);

        bloodPressure = (ImageButton) findViewById(R.id.bloodPressureTEST);
        bloodSugar = (ImageButton) findViewById(R.id.bloodSugarTEST);
        cholesterol = (ImageButton) findViewById(R.id.cholesterolTEST);
        vaccinatons = (ImageButton) findViewById(R.id.vaccinationsTEST);

        sliderAdapterSwitcher = new AppSwitcherAdapter(this);

        mSlideViewPagerSwitcher.setAdapter(sliderAdapterSwitcher);

        addDotsIndicator(0);

        mSlideViewPagerSwitcher.addOnPageChangeListener(viewListener);

        bloodPressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchEnterBloodPressureActivity();
            }
        });

        bloodSugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { launchEnterBloodSugarActivity();
            }
        });

        cholesterol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchEnterCholesterolActivity();
            }
        });

        vaccinatons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchEnterVaccinationDataActivity();
            }
        });


//        mNextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                launchFirstTimeLogIn();
//            }
//        });
    }


    public void addDotsIndicator(int position) {
        mDotsSwitcher = new TextView[2];
        mDotLayoutSwitcher.removeAllViews();

        for (int i = 0; i < mDotsSwitcher.length; i++) {
            mDotsSwitcher[i] = new TextView(this);
            mDotsSwitcher[i].setTextSize(35);
            mDotsSwitcher[i].setText(Html.fromHtml("&#8226;"));
            mDotsSwitcher[i].setTextColor(getResources().getColor(R.color.GreenHuesLight));

            mDotLayoutSwitcher.addView(mDotsSwitcher[i]);
        }

        if(mDotsSwitcher.length > 0) {
            mDotsSwitcher[position].setTextColor(getResources().getColor(R.color.GreenHuesDark));
        }
    }


    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            addDotsIndicator(i);
            mCurrentPage = i;

            if(i == 1) {
                bloodPressure.setEnabled(true);

                bloodSugar.setEnabled(true);
                bloodSugar.setEnabled(true);
                cholesterol.setEnabled(true);
                vaccinatons.setEnabled(true);
//              mNextBtn.setVisibility(View.VISIBLE);

            } else if (i == 2){
                bloodSugar.setEnabled(false);
                bloodSugar.setEnabled(false);
                cholesterol.setEnabled(false);
                vaccinatons.setEnabled(false);
            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }

    };


    private void launchPrevActivity()
    {
        Intent intent = new Intent (this, MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void launchMainActivity()
    {
        Intent intent = new Intent (this, MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void launchEnterBloodPressureActivity()
    {
        Intent intent = new Intent (this, SelectBloodPressureActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void launchEnterBloodSugarActivity()
    {
        Intent intent = new Intent (this, SelectBloodSugarActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void launchEnterCholesterolActivity()
    {
        Intent intent = new Intent (this, SelectCholesterolActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void launchEnterVaccinationDataActivity()
    {
        Intent intent = new Intent (this, SelectVaccinationActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        launchPrevActivity();
    }
}