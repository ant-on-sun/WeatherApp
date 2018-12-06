package com.an_ant_on_the_sun.weather.db;

import android.content.ContentValues;

import com.an_ant_on_the_sun.weather.model.GeneralRespond;

public class FillContentValuesWithData {

    public static ContentValues uploadData(GeneralRespond respond){
        ContentValues values = new ContentValues();
        if(respond.getId() != null){
            values.put(WeatherContract.CityEntry.COLUMN_CITY_ID, respond.getId());
        } else {
            values.put(WeatherContract.CityEntry.COLUMN_CITY_ID, 0.0);
        }
        if(respond.getName() != null){
            values.put(WeatherContract.CityEntry.COLUMN_CITY_NAME, respond.getName());
        } else {
            values.put(WeatherContract.CityEntry.COLUMN_CITY_NAME, "No city name in respond");
        }
        if(respond.getCoord() != null){
            values.put(WeatherContract.CityEntry.COLUMN_LON, respond.getCoord().getLon());
            values.put(WeatherContract.CityEntry.COLUMN_LAT, respond.getCoord().getLat());
        } else {
            values.put(WeatherContract.CityEntry.COLUMN_LON, 0.0);
            values.put(WeatherContract.CityEntry.COLUMN_LAT, 0.0);
        }
        if(respond.getSys() != null){
            values.put(WeatherContract.CityEntry.COLUMN_COUNTRY, respond.getSys().getCountry());
            values.put(WeatherContract.CityEntry.COLUMN_SUNRISE, respond.getSys().getSunrise());
            values.put(WeatherContract.CityEntry.COLUMN_SUNSET, respond.getSys().getSunset());
        } else {
            values.put(WeatherContract.CityEntry.COLUMN_COUNTRY, "No country in respond");
            values.put(WeatherContract.CityEntry.COLUMN_SUNRISE, 0);
            values.put(WeatherContract.CityEntry.COLUMN_SUNSET, 0);
        }
        if(respond.getWeather() != null && respond.getWeather().size() > 0){
            values.put(WeatherContract.CityEntry.COLUMN_DESCRIPTION,
                    respond.getWeather().get(0).getDescription());
            values.put(WeatherContract.CityEntry.COLUMN_ICON,
                    respond.getWeather().get(0).getIcon());
        } else {
            values.put(WeatherContract.CityEntry.COLUMN_DESCRIPTION, "No description in respond");
            values.put(WeatherContract.CityEntry.COLUMN_ICON, "");
        }
        if(respond.getMain() != null){
            values.put(WeatherContract.CityEntry.COLUMN_TEMP, respond.getMain().getTemp());
            values.put(WeatherContract.CityEntry.COLUMN_HUMIDITY, respond.getMain().getHumidity());
            values.put(WeatherContract.CityEntry.COLUMN_PRESSURE, respond.getMain().getPressure());
            values.put(WeatherContract.CityEntry.COLUMN_TEMP_MIN, respond.getMain().getTempMin());
            values.put(WeatherContract.CityEntry.COLUMN_TEMP_MAX, respond.getMain().getTempMax());
        } else {
            values.put(WeatherContract.CityEntry.COLUMN_TEMP, -1000.0);
            values.put(WeatherContract.CityEntry.COLUMN_HUMIDITY, -1000);
            values.put(WeatherContract.CityEntry.COLUMN_PRESSURE, -1000.0);
            values.put(WeatherContract.CityEntry.COLUMN_TEMP_MIN, -1000.0);
            values.put(WeatherContract.CityEntry.COLUMN_TEMP_MAX, -1000.0);
        }
        if(respond.getWind() != null){
            values.put(WeatherContract.CityEntry.COLUMN_WIND_SPEED, respond.getWind().getSpeed());
            values.put(WeatherContract.CityEntry.COLUMN_WIND_DEGREE, respond.getWind().getDeg());
        } else {
            values.put(WeatherContract.CityEntry.COLUMN_WIND_SPEED, -1000.0);
            values.put(WeatherContract.CityEntry.COLUMN_WIND_DEGREE, -1000.0);
        }
        if(respond.getClouds() != null){
            values.put(WeatherContract.CityEntry.COLUMN_CLOUDS, respond.getClouds().getAll());
        } else {
            values.put(WeatherContract.CityEntry.COLUMN_CLOUDS, -1000);
        }
        if(respond.getRain() != null){
            values.put(WeatherContract.CityEntry.COLUMN_RAIN, respond.getRain().get3h());
        } else {
            values.put(WeatherContract.CityEntry.COLUMN_RAIN, -1000);
        }
        if(respond.getSnow() != null){
            values.put(WeatherContract.CityEntry.COLUMN_SNOW, respond.getSnow().get_3h());
        } else {
            values.put(WeatherContract.CityEntry.COLUMN_SNOW, -1000);
        }
        return values;
    }
}
