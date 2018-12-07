package com.an_ant_on_the_sun.weather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("speed") //Wind speed.
    // Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
    @Expose
    private Double speed;

    @SerializedName("deg") //Wind direction, degrees (meteorological)
    @Expose
    private Double deg;

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDeg() {
        return deg;
    }

    public void setDeg(Double deg) {
        this.deg = deg;
    }
}
