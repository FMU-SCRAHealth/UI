package com.example.pmarl.peedeehealthadvisor;

import android.app.Activity;
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
import android.widget.ArrayAdapter;
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
    private String medFreq;
    private String medTaking;
    private Cursor cursorMed;
    private ImageButton medCall;
    private Switch mySwitch;
    private View.OnClickListener callClickListener;
    private View.OnClickListener listenerClickListener;
    private Switch.OnCheckedChangeListener usingSwitchListener;
    private CardView background;
    private RelativeLayout backgroundRel;
    private LinearLayout backgroundMed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.med_card_row);
        View activity = findViewById(R.layout.med_card_row);
        backgroundRel = (RelativeLayout) findViewById(R.id.card_view_background);
        background = (CardView) findViewById(R.id.card_view);
//        mySwitch = (Switch) findViewById(R.id.switchTaking);
        backgroundMed = (LinearLayout) findViewById(R.id.layoutMedCard);
        cursorMed = MainActivity.myDB.readMedData();
//        mySwitch = (Switch) findViewById(R.id.switchTaking);
        mySwitch.setOnCheckedChangeListener(usingSwitchListener);



    }



    MedicationsDataObject(String name, String dose, String delivery, String rxNum,
                          String pharmName, String pharmNum, String freq, String taking
    ){
        medName = name;
        medDose = dose;
        medDelivery = delivery;
        medRxNum = rxNum;
        medPharmName = pharmName;
        medPharmNum = pharmNum;
        medFreq = freq + " Per Day";
        medTaking = taking;

    }







    // this satisfies the interface
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        if (isChecked) {
//            changeBackgroundToPink();
//        } else {
//            changeBackgroundToGrey();
//        }
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

    public View.OnClickListener updateTaken() {
        listenerClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (medTaking.equals("Yes")){
                    medTaking = "No";
                    v.findViewById(R.id.card_view_background).setBackgroundColor(Color.parseColor("#afdfe3"));
                } else
                {
                    medTaking = "Yes";
                    v.findViewById(R.id.card_view_background).setBackgroundColor(Color.parseColor("#afdfe3"));
                }

                v.findViewById(R.id.card_view_background).setBackgroundColor(Color.parseColor("#afdfe3"));


            }
        };

        return listenerClickListener;
    }


//    public CompoundButton.OnCheckedChangeListener createSwitch() {
//        usingSwitchListener = new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
////                    changeBackgroundToPink();
//                    buttonView.findViewById(R.id.card_view).setBackgroundColor(Color.parseColor("#afdfe3"));
//
//                } else {
////                    changeBackgroundToGrey();
//                }
//
//
//            }
//        };
//
//        return usingSwitchListener;
//    }

    public void changeBackgroundToGrey() {

        getBackground().setCardBackgroundColor(Color.parseColor("#afdfe3"));
        getBackgroundRel().setBackgroundColor(Color.parseColor("#afdfe3"));
        MainActivity.myDB.updateMedicationData(medName, medTaking);
    }

    public void changeBackgroundToPink() {
//        backgroundRel.setBackgroundColor(Color.parseColor("#fd91e7"));
//        background.setCardBackgroundColor(Color.parseColor("#fd91e7"));
//        MainActivity.myDB.updateMedicationData(medName, medTaking);

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

    public String getMedFreq() {
        return medFreq;
    }

    public void setMedFreq(String medFreq) {
        this.medFreq = medFreq;
    }

    public String getMedTaking() {
        return medTaking;
    }

    public void setMedTaking(String medTaking) {
        this.medTaking = medTaking;
    }

    public CardView getBackground() {
        return background;
    }

    public RelativeLayout getBackgroundRel() {
        return backgroundRel;
    }

}