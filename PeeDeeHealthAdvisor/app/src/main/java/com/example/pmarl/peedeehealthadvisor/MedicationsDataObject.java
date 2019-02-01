package com.example.pmarl.peedeehealthadvisor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.v7.widget.CardView;

public class MedicationsDataObject extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private String medName;
    private String medDose;
    private String medDelivery;
    private String medRxNum;
    private String medPharmName;
    private String medPharmNum;
    private ImageButton medCall;
    private Switch mySwitch;
    private View.OnClickListener callClickListener;
    private Switch.OnCheckedChangeListener usingSwitchListener;
    private CardView background;
    private RelativeLayout backgroundRel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.med_card_row);
        backgroundRel = (RelativeLayout) findViewById(R.id.card_view_background);
        background = (CardView) findViewById(R.id.card_view);
        mySwitch = (Switch) findViewById(R.id.switch1);

    }



    MedicationsDataObject(String name, String dose, String delivery, String rxNum,
                          String pharmName, String pharmNum
    ){
        medName = name;
        medDose = dose;
        medDelivery = delivery;
        medRxNum = rxNum;
        medPharmName = pharmName;
        medPharmNum = pharmNum;

    }




    // this satisfies the interface
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(CompoundButton button, boolean bool) {

    }



    // this is the method that is called for the phone call button
    /*
    * If a new button is added, just make a return type of View.OnClickListener and do the following
    * below, making the call in the OnBindViewHolder in the MyMedicationRecyclerViewAdapter.java file
    * */
    public View.OnClickListener createCall() {
        callClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String temp = "tel:" + medPharmNum;
                intent.setData(Uri.parse(temp));
                v.getContext().startActivity(intent);
            }
        };

        return callClickListener;
    }


    public Switch.OnCheckedChangeListener createSwitch() {
        usingSwitchListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (mySwitch.isChecked()) {
                    backgroundRel.setBackgroundColor(Color.parseColor("#afdfe3"));
                    background.setCardBackgroundColor(Color.parseColor("#fd91e7"));
                }
                else if (!mySwitch.isChecked())  {
                    backgroundRel.setBackgroundColor(Color.parseColor("#afdfe3"));
                    background.setCardBackgroundColor(Color.parseColor("#afdfe3"));
                }

            }
        };

        return usingSwitchListener;
    }


    public String getName() {
        return medName;
    }


    public String getDose() {
        return medDose;
    }

    public String getDelivery() {
        return medDelivery;
    }

    public String getRxNum() {
        return medRxNum;
    }

    public String getPharmName() {
        return medPharmName;
    }

    public String getMedPharmNum() {
        return medPharmNum;
    }

//    public ImageButton getMedCall() {
//        return medCall;
//    }
//
//    public void setMedCall(ImageButton medCall) {
//        this.medCall = medCall;
//    }

}