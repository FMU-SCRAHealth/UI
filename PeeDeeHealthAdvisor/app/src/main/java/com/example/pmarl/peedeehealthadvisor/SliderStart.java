package com.example.pmarl.peedeehealthadvisor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SliderStart extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDots;

    private SliderAdapter sliderAdapter;

    private Button mNextBtn;
    private int mCurrentPage;


    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.slider_splash);

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        mNextBtn = (Button) findViewById(R.id.finishButton);

        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchFirstTimeLogIn();
            }
        });
    }


        public void addDotsIndicator(int position) {
            mDots = new TextView[10];
            mDotLayout.removeAllViews();

            for (int i = 0; i < mDots.length; i++) {
                mDots[i] = new TextView(this);
                mDots[i].setTextSize(35);
                mDots[i].setText(Html.fromHtml("&#8226;"));
                mDots[i].setTextColor(getResources().getColor(R.color.GreenHuesLight));

                mDotLayout.addView(mDots[i]);
            }

            if(mDots.length > 0) {
                mDots[position].setTextColor(getResources().getColor(R.color.GreenHuesDark));
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

                if(i <= mDots.length - 2) {

                    mNextBtn.setEnabled(false);
                    mNextBtn.setVisibility(View.INVISIBLE);

                } else if (i == mDots.length - 1) {
                    mNextBtn.setEnabled(true);
                    mNextBtn.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }

    };


        private void launchFirstTimeLogIn()
        {
            Intent intent = new Intent(this, FirstTimeLogin.class);
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

    @Override
    public void onBackPressed() {

    }
}
