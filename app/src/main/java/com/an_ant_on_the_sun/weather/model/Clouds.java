package com.an_ant_on_the_sun.weather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clouds {

    @SerializedName("all") //Cloudiness, %
    @Expose
    private Integer all;

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }
}
