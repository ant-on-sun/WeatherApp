package com.an_ant_on_the_sun.weather.model;

public class DataToDisplay {

    private static final DataToDisplay sInstance = new DataToDisplay();

    public static DataToDisplay getsInstance(){
        return sInstance;
    }

    private DataToDisplay(){}

    private int cityId;
    private String cityName;
    private double longitude;
    private double latitude;
    private String country;
    private int sunrise;
    private int sunset;
    private String description;
    private String icon;
    private double temperature;
    private int humidity;
    private double pressure;
    private double tempMin;
    private double tempMax;
    private double windSpeed;
    private double windDegree;
    private int clouds;
    private double rain;
    private double snow;

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindDegree(double windDegree) {
        this.windDegree = windDegree;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public void setSnow(double snow) {
        this.snow = snow;
    }


    public int getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getCountry() {
        return country;
    }

    public int getSunrise() {
        return sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDegree() {
        return windDegree;
    }

    public int getClouds() {
        return clouds;
    }

    public double getRain() {
        return rain;
    }

    public double getSnow() {
        return snow;
    }
}
