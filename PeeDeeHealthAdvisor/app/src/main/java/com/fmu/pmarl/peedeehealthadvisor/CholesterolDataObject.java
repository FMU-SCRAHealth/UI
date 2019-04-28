package com.fmu.pmarl.peedeehealthadvisor;

public class CholesterolDataObject {

    private String tcValue;
    private String hdlValue;
    private String trigValue;
    private String ldlValue;
    private String cholesterolDate;
    private String cholEpoch;



    CholesterolDataObject(Float tc, Float hdl, Float trig, Float ldl, String date, Long time) {

             tcValue = tc.toString() + " mg/dL";
             hdlValue = hdl.toString() + " mg/dL";
             trigValue = trig.toString() + " mg/dL";
             ldlValue = ldl.toString() + " mg/dL";
             cholesterolDate = date;
             cholEpoch = time.toString();

    }

    public String getTcValue() {
        return tcValue;
    }

    public void setTcValue(String tcValue) {
        this.tcValue = tcValue;
    }

    public String getHdlValue() {
        return hdlValue;
    }

    public void setHdlValue(String hdlValue) {
        this.hdlValue = hdlValue;
    }

    public String getTrigValue() {
        return trigValue;
    }

    public void setTrigValue(String trigValue) {
        this.trigValue = trigValue;
    }

    public String getLdlValue() {
        return ldlValue;
    }

    public void setLdlValue(String ldlValue) {
        this.ldlValue = ldlValue;
    }

    public String getCholesterolDate() {
        return cholesterolDate;
    }

    public void setCholesterolDate(String cholesterolDate) {
        this.cholesterolDate = cholesterolDate;
    }

    public String getCholEpoch() {
        return cholEpoch;
    }

    public void setCholEpoch(String cholEpoch) {
        this.cholEpoch = cholEpoch;
    }



}
