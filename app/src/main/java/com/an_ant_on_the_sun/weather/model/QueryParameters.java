package com.an_ant_on_the_sun.weather.model;

public class QueryParameters {

    private static final QueryParameters sInstance = new QueryParameters();

    public static QueryParameters getInstance() {
        return sInstance;
    }

    private QueryParameters() {
    }

    private int cityId;
    private String cityName;
    private double longitude;
    private double latitude;

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
