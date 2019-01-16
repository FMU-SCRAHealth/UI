package com.example.pmarl.peedeehealthadvisor;

public class CholesterolValues
{
    private Float hdl;
    private Float ldl;
    private Float trig;

    private Float tc;

    public CholesterolValues(Float hdl, Float ldl, Float trig, Float tc)
    {
        this.hdl = hdl;
        this.ldl = ldl;
        this.trig = trig;
        this.tc = tc;
    }

    public CholesterolValues(){}

    public Float getHdl(){return hdl;}

    public void setHdl(Float hdl){this.hdl = hdl;}

    public Float getLdl(){return ldl;}

    public void setLdl(Float ldl){this.ldl = ldl;}

    public Float getTrig(){return trig;}

    public void setTrig(Float trig){this.trig = trig;}

    public void setTc(Float tc){this.tc = tc;}

    public Float getTc(){return tc;}
}
