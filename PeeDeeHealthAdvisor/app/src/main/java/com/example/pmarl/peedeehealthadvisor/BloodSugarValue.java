package com.example.pmarl.peedeehealthadvisor;

public class BloodSugarValue
{
    private Boolean fasting;
    private Float bloodSugar;

    public BloodSugarValue(Boolean fasting, Float bloodSugar) {
        this.fasting = fasting;
        this.bloodSugar = bloodSugar;
    }

    public BloodSugarValue(){}


    public void setFasting(Boolean fasting) {
        this.fasting = fasting;
    }

    public void setBloodSugar(Float bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public Boolean getFasting() {
        return fasting;
    }

    public Float getBloodSugar() {
        return bloodSugar;
    }




}
