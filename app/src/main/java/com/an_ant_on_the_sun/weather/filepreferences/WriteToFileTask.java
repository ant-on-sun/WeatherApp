package com.an_ant_on_the_sun.weather.filepreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class WriteToFileTask extends AsyncTask {
    private static final String TAG = WriteToFileTask.class.getSimpleName();

    private SharedPreferences mSharedPreferences;
    private int cityId;

    public WriteToFileTask(Context context, int cityId) {
        mSharedPreferences = context.getSharedPreferences(FileNameForPreferences.FILE_NAME,
                Context.MODE_PRIVATE);
        this.cityId = cityId;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if(cityId == 0){
            return null;
        }
        editor.putInt(FileNameForPreferences.FILE_CITY_ID, cityId);
        editor.apply();
        Log.i(TAG, "Data (cityId) has been written to a file "
                + FileNameForPreferences.FILE_NAME + ", cityId = " + cityId);
        return null;
    }
}
