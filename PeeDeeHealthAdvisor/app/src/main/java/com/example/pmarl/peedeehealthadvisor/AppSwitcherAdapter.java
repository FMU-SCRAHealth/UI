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
            R.drawable.ic_reports2

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