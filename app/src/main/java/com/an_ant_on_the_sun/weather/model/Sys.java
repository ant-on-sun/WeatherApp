package com.an_ant_on_the_sun.weather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys {

    @SerializedName("type") //Internal parameter
    @Expose
    private Integer type;

    @SerializedName("id") //Internal parameter
    @Expose
    private Integer id;

    @SerializedName("message") //Internal parameter
    @Expose
    private Double message;

    @SerializedName("country") //Country code (GB, JP etc.)
    @Expose
    private String country;

    @SerializedName("sunrise") //Sunrise time, unix, UTC
    @Expose
    private Integer sunrise;

    @SerializedName("sunset") //Sunset time, unix, UTC
    @Expose
    private Integer sunset;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }
}
