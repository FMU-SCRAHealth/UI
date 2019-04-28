package com.fmu.pmarl.peedeehealthadvisor;

public class BloodSugarDataObject {

    private String bsValue;
    private String bsFasting;
    private String bsDate;
    private String bsTime;


    BloodSugarDataObject(Float top, Boolean fasting, String date, Long time) {

        bsValue = top.toString() + " mg/dL";
        if(fasting.equals(true)) {
            bsFasting = "Fasting";
        }
        else {
            bsFasting = "Non-Fasting";
        }

        bsDate = date;

        bsTime = time.toString();

    }

    public String getBsValue() {
        return bsValue;
    }

    public void setBsValue(String bsValue) {
        this.bsValue = bsValue;
    }

    public String getBsFasting() {
        return bsFasting;
    }

    public void setBsFasting(String bsFasting) {
        this.bsFasting = bsFasting;
    }

    public String getBsDate() {
        return bsDate;
    }

    public void setBsDate(String bsDate) {
        this.bsDate = bsDate;
    }

    public String getBsTime() {
        return bsTime;
    }

    public void setBsTime(String bsTime) {
        this.bsTime = bsTime;
    }



}