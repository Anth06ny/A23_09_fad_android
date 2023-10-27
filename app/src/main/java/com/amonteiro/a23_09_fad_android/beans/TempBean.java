package com.amonteiro.a23_09_fad_android.beans;

public class TempBean {

    private double temp;

    @Override
    public String toString() {
        return "TempBean{" +
                "temp=" + temp +
                '}';
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
