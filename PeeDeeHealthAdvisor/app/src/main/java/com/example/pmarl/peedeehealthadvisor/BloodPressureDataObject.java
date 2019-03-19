package com.example.pmarl.peedeehealthadvisor;

public class BloodPressureDataObject {

    private String bpTop;
    private String bpBottom;
    private String bpDate;


    BloodPressureDataObject(Integer top, Integer bottom, String date) {
        bpTop = top.toString() + " (Systolic)";
        bpBottom = bottom.toString() + " (Diastolic)";
        bpDate = date;

    }

    public String getBpTop() {
        return bpTop;
    }

    public void setBpTop(String bpTop) {
        this.bpTop = bpTop;
    }

    public String getBpBottom() {
        return bpBottom;
    }

    public void setBpBottom(String bpBottom) {
        this.bpBottom = bpBottom;
    }

    public String getBpDate() {
        return bpDate;
    }

    public void setBpDate(String bpDate) {
        this.bpDate = bpDate;
    }

}