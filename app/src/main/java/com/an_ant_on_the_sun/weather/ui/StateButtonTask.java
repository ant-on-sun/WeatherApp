package com.an_ant_on_the_sun.weather.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class StateButtonTask extends AsyncTask {
    private static final String TAG = StateButtonTask.class.getSimpleName();

      private Context mAppContext;

    public StateButtonTask(Context context){
        mAppContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Intent intentDisableButton = new Intent(DisableButtonReceiver.ACTION_DISABLE_BUTTON);
        LocalBroadcastManager.getInstance(mAppContext).sendBroadcast(intentDisableButton);
        Log.i(TAG, "Starting disabling button Search");


    }

    @Override
    protected Object doInBackground(Object[] objects) {

        Integer[] timeInterval = new Integer[2];
        for (int minutes = 9; minutes >= 0; minutes--){
            timeInterval[0] = minutes;
            for (int seconds = 59; seconds >= 0; seconds--){
                timeInterval[1] = seconds;
                publishProgress(timeInterval);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Log.e(TAG, "in doInBackground(), while TimeUnit.SECONDS.sleep ", e);
                }
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);

        Integer[] resultArray = (Integer[]) values;
        String minutes = "" + resultArray[0];
        String seconds;
        if(resultArray[1] < 10){
            seconds = "0" + resultArray[1];
        } else {
            seconds = "" + resultArray[1];
        }
        String timeLeft = minutes + ":" + seconds;
        Intent intentSetTextInfo = new Intent(ChangeTextInfoReceiver.ACTION_CHANGE_TEXT);
        intentSetTextInfo.putExtra("text", timeLeft);
        LocalBroadcastManager.getInstance(mAppContext).sendBroadcast(intentSetTextInfo);
        //Log.i(TAG, "Time left " + minutes + " : " + seconds);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        Intent intentEnableButton = new Intent(EnableButtonReceiver.ACTION_ENABLE_BUTTON);
        LocalBroadcastManager.getInstance(mAppContext).sendBroadcast(intentEnableButton);

        Intent intentSetTextInfo = new Intent(ChangeTextInfoReceiver.ACTION_CHANGE_TEXT);
        intentSetTextInfo.putExtra("text", "");
        LocalBroadcastManager.getInstance(mAppContext).sendBroadcast(intentSetTextInfo);
    }
}
