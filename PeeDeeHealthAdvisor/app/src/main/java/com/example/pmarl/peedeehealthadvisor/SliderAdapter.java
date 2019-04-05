package com.example.pmarl.peedeehealthadvisor;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {

        Context context;
        LayoutInflater layoutInflater;

        public SliderAdapter(Context context) {
            this.context = context;
        }


        public int[] slide_images = {

                R.drawable.ic_ha_icon,
                R.drawable.ic_blood_pressure,
                R.drawable.ic_blood_sugar,
                R.drawable.ic_cholesterol_arrows,
                R.drawable.ic_vaccinations,
                R.drawable.ic_weight,
                R.drawable.ic_allergiesintro,
                R.drawable.ic_medicationsintro,
                R.drawable.ic_compass,
                R.drawable.ic_userenteredswipe

        };

        public String[] slide_headings = {

                "Welcome To SCHealthPlusMe",
                "Blood Pressure",
                "Blood Sugar",
                "Cholesterol",
                "Vaccinations",
                "Body Weight",
                "Allergies",
                "Medications",
                "Resources",
                "User Setup"

        };


        public String[] slide_descriptions = {

                "SCHealthPlusMe will help you keep up with your health information.\n\n\n"
                    + "Swipe to see what is offered.",
                " Enter your blood pressure numbers.",
                "  Enter your blood sugar numbers.",
                "   Enter your cholesterol numbers.",
                "  Enter your vaccination dates.",
                "   Enter your body weight.",
                "   Enter your allergies. ",
                "  Enter your medications.",
                " Find your local health resources!",
                "Let's get started by clicking the button below!"

        };


        @Override
        public int getCount() {
            return slide_headings.length;
        }


        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == (RelativeLayout) o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

            ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
            TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
            TextView slideDescription = (TextView) view.findViewById(R.id.slide_description);

            slideImageView.setImageResource(slide_images[position]);
            slideHeading.setText(slide_headings[position]);
            slideDescription.setText(slide_descriptions[position]);

            container.addView(view);

            return view;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout)object);
        }

}
