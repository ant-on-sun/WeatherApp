package com.an_ant_on_the_sun.weather.network;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

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
        Log.i(TAG, "Start query for web");
    }

    //Does not have access to UI
    @Override
    protected Object doInBackground(Object[] objects) {
        Call<GeneralRespond> call = ApiFactory.getWeatherService().getWeatherByCityId(cityId);
        int resultCode = 0;
        if(!CheckInternetAccess.isOnline()){
            resultCode = 1;
            return resultCode;
        }
        try {
            Response<GeneralRespond> response = call.execute();
            Log.i(TAG, "Have made a call.execute()");
            int statusCode = response.code();
            Log.i(TAG, "response status code is " + statusCode);
            generalRespond = response.body();
            if(generalRespond == null){
                Log.e(TAG, "response body (generalRespond) is null");
            }
        } catch (IOException e) {
            Log.e(TAG, "in doInBackground(), in call to web try{}, Exception e: ", e);
            resultCode = 2;
            return resultCode;
        }
        try {
            ContentValues values = FillContentValuesWithData.uploadData(generalRespond);
            Uri updateOrInsertCityUri = Uri.withAppendedPath(WeatherContract.CityEntry.CONTENT_URI,
                    String.valueOf(generalRespond.getId()));
            int resultOfUpdateOrInsert = mContext.getContentResolver()
                    .update(updateOrInsertCityUri, values, null, null);
        } catch (Exception e) {
            Log.e(TAG, "in doInBackground(), in DB update try{}, Exception e: ", e);
            resultCode = 3;
            return resultCode;
        }
        //To show progress if needed (onProgressUpdate() will be call)
        //publishProgress();
        //return generalRespond;
        return resultCode;
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

        Integer resultCode = (Integer)o;
        if(resultCode > 0){
            //Show error message
            switch (resultCode){
                case 1:
                    Toast.makeText(mContext, "Can't get internet connection",
                            Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(mContext, "Something goes wrong. Can't get data from web",
                            Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(mContext, "Something goes wrong. " +
                                    "Can't write data to database",
                            Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(mContext, "Something goes wrong. Have no idea why...",
                            Toast.LENGTH_LONG).show();
            }
            return;
        }
        Intent intentDatabaseChanged = new Intent(DatabaseChangedReceiver.ACTION_DATABASE_CHANGED);
        intentDatabaseChanged.putExtra("cityId", cityId);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intentDatabaseChanged);
        Log.i(TAG, "Data from web loaded");
    }

    //Has access to UI
    @Override
    protected void onCancelled() {
        super.onCancelled();
        mContext = null;
        generalRespond = null;
        Log.i(TAG, "Query task was cancelled");
    }

}
