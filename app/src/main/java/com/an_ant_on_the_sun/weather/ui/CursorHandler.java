package com.an_ant_on_the_sun.weather.ui;

import android.database.Cursor;

import com.an_ant_on_the_sun.weather.db.WeatherContract;
import com.an_ant_on_the_sun.weather.model.DataToDisplay;

public class CursorHandler {

    public static boolean getDataFromCursor(Cursor mData, int cityId){
        boolean dataExistInCursor = false;
        DataToDisplay dataToDisplay = DataToDisplay.getsInstance();
        int columnIndexCityId = mData.getColumnIndex(WeatherContract.CityEntry.COLUMN_CITY_ID);
        int columnIndexCityName = mData.getColumnIndex(WeatherContract.CityEntry.COLUMN_CITY_NAME);
        int columnIndexLon = mData.getColumnIndex(WeatherContract.CityEntry.COLUMN_LON);
        int columnIndexLat = mData.getColumnIndex(WeatherContract.CityEntry.COLUMN_LAT);
        int columnIndexCountry = mData.getColumnIndex(WeatherContract.CityEntry.COLUMN_COUNTRY);
        int columnIndexSunrise = mData.getColumnIndex(WeatherContract.CityEntry.COLUMN_SUNRISE);
        int columnIndexSunset = mData.getColumnIndex(WeatherContract.CityEntry.COLUMN_SUNSET);
        int columnIndexDescription = mData
                .getColumnIndex(WeatherContract.CityEntry.COLUMN_DESCRIPTION);
        int columnIndexIcon = mData.getColumnIndex(WeatherContract.CityEntry.COLUMN_ICON);
        int columnIndexTemp = mData.getColumnIndex(WeatherContract.CityEntry.COLUMN_TEMP);
        int columnIndexHumidity = mData.getColumnIndex(WeatherContract.CityEntry.COLUMN_HUMIDITY);
        int columnIndexPressure = mData.getColumnIndex(WeatherContract.CityEntry.COLUMN_PRESSURE);
        int columnIndexTempMin = mData.getColumnIndex(WeatherContract.CityEntry.COLUMN_TEMP_MIN);
        int columnIndexTempMax = mData.getColumnIndex(WeatherContract.CityEntry.COLUMN_TEMP_MAX);
        int columnIndexWindSpeed = mData
                .getColumnIndex(WeatherContract.CityEntry.COLUMN_WIND_SPEED);
        int columnIndexWindDegree = mData
                .getColumnIndex(WeatherContract.CityEntry.COLUMN_WIND_DEGREE);
        int columnIndexClouds = mData.getColumnIndex(WeatherContract.CityEntry.COLUMN_CLOUDS);
        int columnIndexRain = mData.getColumnIndex(WeatherContract.CityEntry.COLUMN_RAIN);
        int columnIndexSnow = mData.getColumnIndex(WeatherContract.CityEntry.COLUMN_SNOW);
        try{
            while (mData.moveToNext()){
                //Ищем по CityId
                if(mData.getInt(columnIndexCityId) == cityId){
                    dataToDisplay.setCityId(mData.getInt(columnIndexCityId));
                    dataToDisplay.setCityName(mData.getString(columnIndexCityName));
                    dataToDisplay.setLongitude(mData.getDouble(columnIndexLon));
                    dataToDisplay.setLatitude(mData.getDouble(columnIndexLat));
                    dataToDisplay.setCountry(mData.getString(columnIndexCountry));
                    dataToDisplay.setSunrise(mData.getInt(columnIndexSunrise));
                    dataToDisplay.setSunset(mData.getInt(columnIndexSunset));
                    dataToDisplay.setDescription(mData.getString(columnIndexDescription));
                    dataToDisplay.setIcon(mData.getString(columnIndexIcon));
                    dataToDisplay.setTemperature(mData.getDouble(columnIndexTemp));
                    dataToDisplay.setHumidity(mData.getInt(columnIndexHumidity));
                    dataToDisplay.setPressure(mData.getDouble(columnIndexPressure));
                    dataToDisplay.setTempMin(mData.getDouble(columnIndexTempMin));
                    dataToDisplay.setTempMax(mData.getDouble(columnIndexTempMax));
                    dataToDisplay.setWindSpeed(mData.getDouble(columnIndexWindSpeed));
                    dataToDisplay.setWindDegree(mData.getDouble(columnIndexWindDegree));
                    dataToDisplay.setClouds(mData.getInt(columnIndexClouds));
                    dataToDisplay.setRain(mData.getInt(columnIndexRain));
                    dataToDisplay.setSnow(mData.getInt(columnIndexSnow));
                    dataExistInCursor = true;
                }
            }
        } finally {
            mData.close();
        }
        return dataExistInCursor;
    }
}
