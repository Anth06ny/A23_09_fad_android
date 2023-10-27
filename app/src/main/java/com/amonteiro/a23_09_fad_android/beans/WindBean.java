package com.amonteiro.a23_09_fad_android.beans;

public class WindBean {

    private double speed;

    @Override
    public String toString() {
        return "WindBean{" +
                "speed=" + speed +
                '}';
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
