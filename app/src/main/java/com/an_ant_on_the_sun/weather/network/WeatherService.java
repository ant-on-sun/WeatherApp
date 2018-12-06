package com.an_ant_on_the_sun.weather.network;

import android.support.annotation.NonNull;

import com.an_ant_on_the_sun.weather.model.GeneralRespond;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("data/2.5/weather?units=metric") //Example: api.openweathermap.org/data/2.5/weather?id=2172797
    Call<GeneralRespond> getWeatherByCityId(@NonNull @Query("id") Integer cityId);

    @GET("data/2.5/weather?units=metric") //Example: api.openweathermap.org/data/2.5/weather?lat=35&lon=139
    Call<GeneralRespond> getWeatherByCoordinates(@NonNull @Query("lat") Double lat,
                                                 @NonNull @Query("lon") Double lon);
    @GET("data/2.5/weather?units=metric") //Example: api.openweathermap.org/data/2.5/weather?q=London,uk
    Call<GeneralRespond> getWeatherByCityName(@NonNull @Query("q") String cityNameCommaCountryCode);

}
