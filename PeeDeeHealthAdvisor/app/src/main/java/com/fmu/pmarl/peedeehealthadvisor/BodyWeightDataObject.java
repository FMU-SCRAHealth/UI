package com.example.pmarl.peedeehealthadvisor;

public class BodyWeightDataObject {

    private String weightValue;
    private String weightDate;
    private String bodyWeightTime;


    BodyWeightDataObject(Float weight, String date, Long time) {

        weightValue = weight.toString() + " lbs";
        weightDate = date;
        bodyWeightTime = time.toString();

    }

    public String getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(String weightValue) {
        this.weightValue = weightValue;
    }

    public String getWeightDate() {
        return weightDate;
    }

    public void setWeightDate(String weightDate) {
        this.weightDate = weightDate;
    }

    public String getBodyWeightTime() {
        return bodyWeightTime;
    }

    public void setBodyWeightTime(String bodyWeightTime) {
        this.bodyWeightTime = bodyWeightTime;
    }

}