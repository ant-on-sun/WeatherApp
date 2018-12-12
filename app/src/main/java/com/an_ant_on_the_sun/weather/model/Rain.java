package com.an_ant_on_the_sun.weather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain {

    @SerializedName("3h") //Rain volume for the last 3 hours
    @Expose
    private Double _3h;

    @SerializedName("1h") //Rain volume for the last 1 hour
    @Expose
    private Double _1h;

    public Double get3h() {
        return _3h;
    }

    public void set3h(Double _3h) {
        this._3h = _3h;
    }

    public Double get1h() {
        return _1h;
    }

    public void set1h(Double _1h) {
        this._1h = _1h;
    }
}
