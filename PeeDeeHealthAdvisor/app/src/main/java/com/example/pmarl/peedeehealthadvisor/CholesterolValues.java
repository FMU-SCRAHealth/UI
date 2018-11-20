package com.example.pmarl.peedeehealthadvisor;

public class CholesterolValues
{
    private Float hdl, ldl, trig;

    public CholesterolValues(Float hdl, Float ldl, Float trig)
    {
        this.hdl = hdl;
        this.ldl = ldl;
        this.trig = trig;
    }

    public CholesterolValues(){}

    public Float getHdl(){return hdl;}

    public void setHdl(Float hdl){this.hdl = hdl;}

    public Float getLdl(){return ldl;}

    public void setLdl(Float ldl){this.ldl = ldl;}

    public Float getTrig(){return trig;}

    public void setTrig(Float trig){this.trig = trig;}
}
