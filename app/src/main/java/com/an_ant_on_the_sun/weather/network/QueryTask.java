package com.an_ant_on_the_sun.weather.network;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.an_ant_on_the_sun.weather.db.DatabaseChangedReceiver;
import com.an_ant_on_the_sun.weather.db.FillContentValuesWithData;
import com.an_ant_on_the_sun.weather.db.WeatherContract;
import com.an_ant_on_the_sun.weather.model.GeneralRespond;
import com.an_ant_on_the_sun.weather.model.QueryParameters;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class QueryTask extends AsyncTask {
    private static final String TAG = QueryTask.class.getSimpleName();

    private int cityId;
    private String cityName;
    private double longitude;
    private double latitude;
    private GeneralRespond generalRespond = GeneralRespond.getsInstance();
    private Context mContext;

    public QueryTask(QueryParameters queryParameters, Context context) {
        cityId = queryParameters.getCityId();
        cityName = queryParameters.getCityName();
        longitude = queryParameters.getLongitude();
        latitude = queryParameters.getLatitude();
        mContext = context;
    }

    //Has access to UI
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //Does not have access to UI
    @Override
    protected Object doInBackground(Object[] objects) {
        Call<GeneralRespond> call = ApiFactory.getWeatherService().getWeatherByCityId(cityId);
        try {
            Response<GeneralRespond> response = call.execute();
            int statusCode = response.code();
            generalRespond = response.body();
        } catch (IOException e) {
            Log.e(TAG, "in doInBackground(), in try{}, Exception e: ", e);
        }
        ContentValues values = FillContentValuesWithData.uploadData(generalRespond);
        Uri updateOrInsertCityUri = Uri.withAppendedPath(WeatherContract.CityEntry.CONTENT_URI,
                String.valueOf(generalRespond.getId()));
        int resultOfUpdateOrInsert = mContext.getContentResolver()
                .update(updateOrInsertCityUri, values, null, null);
        //To show progress if needed (onProgressUpdate() will be call)
        //publishProgress();
        return generalRespond;
    }

    //Has access to UI
    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    //Has access to UI
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Intent intentDatabaseChanged = new Intent(DatabaseChangedReceiver.ACTION_DATABASE_CHANGED);
        intentDatabaseChanged.putExtra("cityId", cityId);
        mContext.sendBroadcast(intentDatabaseChanged);
        Log.i(TAG, "Data from web loaded");
    }

    //Has access to UI
    @Override
    protected void onCancelled() {
        super.onCancelled();
        mContext = null;
        generalRespond = null;
    }
}
