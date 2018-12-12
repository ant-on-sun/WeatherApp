package com.an_ant_on_the_sun.weather.network;

import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static OkHttpClient sClient;
    private static volatile WeatherService sService;

    private ApiFactory() {}

    @NonNull
    public static WeatherService getWeatherService() {
        WeatherService service = sService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sService;
                if (service == null) {
                    service = sService = buildRetrofit().create(WeatherService.class);
                }
            }
        }
        return service;
    }

    @NonNull
    private static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @NonNull
    private static OkHttpClient getClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (ApiFactory.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
//        // See http://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(new ApiKeyInterceptor())
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }
}
