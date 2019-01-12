package com.example.pmarl.peedeehealthadvisor;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AppSwitcherAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflaterSwitcher;

    public AppSwitcherAdapter(Context context) {
        this.context = context;
    }


    public int[] slide_slot_top_left = {

            R.drawable.ic_bloodpressure,
            R.drawable.ic_blood_pressure

    };

    public int[] slide_slot_top_right = {

            R.drawable.ic_bslogo,
            R.drawable.ic_blankspacesongrid

    };

    public int[] slide_slot_bottom_left = {

            R.drawable.ic_cholesterol,
            R.drawable.ic_blankspacesongrid

    };

    public int[] slide_slot_bottom_right = {

            R.drawable.ic_vaccination,
            R.drawable.ic_blankspacesongrid

    };

//    public String[] slide_headings = {
//
//            "Welcome To Health Companion",
//            "Blood Pressure",
//            "Blood Sugar",
//            "Cholesterol",
//            "Vaccinations",
//            "Resources",
//            "User Setup"
//
//    };


//    public String[] slide_descriptions = {
//
//            "Pee Dee Health Companion is here to help you keep track of your health daily! "
//                    + "Swipe to see what is offered.",
//            "Keep up with your blood pressure readings by updating your journal daily.",
//            "Compare blood sugar values with daily fasting and non-fasting journal entries.",
//            "Monitor your cholesterol with daily journal entries.",
//            "Log vaccination records to make sure you're up-to-date!",
//            "Get access to local resources whenever you need a checkup just by searching!",
//            "Let's get started by filling out the information on the next screen by clicking the button below!"
//
//    };


    @Override
    public int getCount() {
        return slide_slot_top_left.length;
    }


    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (View) o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflaterSwitcher = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflaterSwitcher.inflate(R.layout.activity_select_data, container, false);

//        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
//
        ImageButton topLeft = (ImageButton) view.findViewById(R.id.topLeft);
        ImageButton topRight = (ImageButton) view.findViewById(R.id.topRight);
        ImageButton bottomLeft = (ImageButton) view.findViewById(R.id.bottomLeft);
        ImageButton bottomRight = (ImageButton) view.findViewById(R.id.bottomRight);

        topLeft.setImageResource(slide_slot_top_left[position]);
        topRight.setImageResource(slide_slot_top_right[position]);
        bottomLeft.setImageResource(slide_slot_bottom_left[position]);
        bottomRight.setImageResource(slide_slot_bottom_right[position]);


        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }

}