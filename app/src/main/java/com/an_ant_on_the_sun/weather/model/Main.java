package com.an_ant_on_the_sun.weather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp") //Temperature.
    // Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    @Expose
    private Double temp;

    @SerializedName("pressure") //Atmospheric pressure
    // (on the sea level, if there is no sea_level or grnd_level data), hPa
    @Expose
    private Integer pressure;

    @SerializedName("humidity") //Humidity, %
    @Expose
    private Integer humidity;

    @SerializedName("temp_min") //Minimum temperature at the moment.
    // This is deviation from current temp that is possible for large cities and megalopolises
    // geographically expanded (use these parameter optionally).
    // Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    @Expose
    private Double tempMin;

    @SerializedName("temp_max") //Maximum temperature at the moment.
    // This is deviation from current temp that is possible for large cities and megalopolises
    // geographically expanded (use these parameter optionally).
    // Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    @Expose
    private Double tempMax;

    @SerializedName("sea_level") //Atmospheric pressure on the sea level, hPa
    @Expose
    private Double seaLevelPressure;

    @SerializedName("grnd_level") //Atmospheric pressure on the ground level, hPa
    @Expose
    private Double grndLevelPressure;

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public Double getSeaLevelPressure() {
        return seaLevelPressure;
    }

    public Double getGrndLevelPressure() {
        return grndLevelPressure;
    }

    public void setSeaLevelPressure(Double seaLevelPressure) {
        this.seaLevelPressure = seaLevelPressure;
    }

    public void setGrndLevelPressure(Double grndLevelPressure) {
        this.grndLevelPressure = grndLevelPressure;
    }
}
