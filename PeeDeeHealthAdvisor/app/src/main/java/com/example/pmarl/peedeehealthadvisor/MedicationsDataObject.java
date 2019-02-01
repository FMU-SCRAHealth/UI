package com.example.pmarl.peedeehealthadvisor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MedicationsDataObject extends AppCompatActivity implements View.OnClickListener {

    private String medName;
    private String medDose;
    private String medDelivery;
    private String medRxNum;
    private String medPharmName;
    private String medPharmNum;
    private ImageButton medCall;
    private View.OnClickListener mOnClickListener;



    MedicationsDataObject(String name, String dose, String delivery, String rxNum,
                          String pharmName, String pharmNum
    ){
        medName = name;
        medDose = dose;
        medDelivery = delivery;
        medRxNum = rxNum;
        medPharmName = pharmName;
        medPharmNum = pharmNum;
//        medCall = call;


    }


    @Override
    public void onClick(View v) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    String temp = "tel:" + medPharmNum;
                    intent.setData(Uri.parse(temp));
                    v.getContext().startActivity(intent);
                }
            });
    }

    public View.OnClickListener createCall() {
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String temp = "tel:" + medPharmNum;
                intent.setData(Uri.parse(temp));
                v.getContext().startActivity(intent);
            }
        };

        return mOnClickListener;
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