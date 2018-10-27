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

                R.drawable.ic_appicon,
                R.drawable.ic_appicon,
                R.drawable.ic_appicon,
                R.drawable.ic_appicon,
                R.drawable.ic_appicon,
                R.drawable.ic_appicon

        };

        public String[] slide_headings = {

                "Welcome To Health Companion",
                "Blood Pressure",
                "Blood Sugar",
                "Cholesterol",
                "Vaccinations",
                "Clinics"

        };


        public String[] slide_descriptions = {

                "Pee Dee Health Companion is here to help you keep track of your health in four main categories. Swipe to see what is offered.",
                "Keep up with your blood pressure readings by updating your journal.",
                "Compare your blood sugar day to daywith journal entries; with fasting and non fasting options to give an accurate reading.",
                "Monitor your cholesterol with daily journal entries. ",
                "Keep up to date with your vaccination records to make sure you're up-to-date!",
                "Get access to local clinics whenever you need a checkup."

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
