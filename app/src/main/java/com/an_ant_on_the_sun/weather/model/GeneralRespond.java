package com.an_ant_on_the_sun.weather.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralRespond implements Serializable{
    private static final GeneralRespond sInstance = new GeneralRespond();
    public static GeneralRespond getsInstance(){
        return sInstance;
    }
    private GeneralRespond(){}

    @SerializedName("coord") //City geo location, longitude and latitude
    @Expose
    private Coord coord;

    @SerializedName("weather") //info Weather condition codes
    @Expose
    private List<Weather> weather = null;

    @SerializedName("base") //Internal parameter
    @Expose
    private String base;

    @SerializedName("main") //main parameters: Temperature, Atmospheric pressure, Humidity
    @Expose
    private Main main;

    @SerializedName("wind") //wind speed and direction
    @Expose
    private Wind wind;

    @SerializedName("clouds") //Cloudiness, %
    @Expose
    private Clouds clouds;

    @SerializedName("rain") //Rain volume
    @Expose
    private Rain rain;

    @SerializedName("snow") //Snow volume
    @Expose
    private Snow snow;

    @SerializedName("dt") //Time of data calculation, unix, UTC
    @Expose
    private Integer dt;

    @SerializedName("sys") //Internal parameters, Country code (GB, JP etc.),
    // Sunrise and sunset time, unix, UTC
    @Expose
    private Sys sys;

    @SerializedName("id") //City ID
    @Expose
    private Integer id;

    @SerializedName("name") //City name
    @Expose
    private String name;

    @SerializedName("cod") //Code of respond (200, 403, 404, 500 etc.)
    @Expose
    private Integer cod;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Snow getSnow() {
        return snow;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

}
