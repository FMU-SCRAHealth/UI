package com.fmu.pmarl.peedeehealthadvisor;

public class BloodPressureDataObject {

    private String bpTop;
    private String bpBottom;
    private String bpDate;
    private String bpTime;



    BloodPressureDataObject(Integer top, Integer bottom, String date, Long time) {
        bpTop = top.toString() + " (Systolic)";
        bpBottom = bottom.toString() + " (Diastolic)";
        bpDate = date;
        bpTime = time.toString();

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

    public String getBpTime() {
        return bpTime;
    }

    public void setBpTime(String bpTime) {
        this.bpTime = bpTime;
    }

}