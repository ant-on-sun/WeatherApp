package com.an_ant_on_the_sun.weather.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.an_ant_on_the_sun.weather.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DisableButtonTask extends AsyncTask {
    private static final String TAG = DisableButtonTask.class.getSimpleName();

    //private Context mContext;
    private MainActivity mainActivity;
    private Button mButtonSearch;
    private TextView mTextViewInfoAboutDisabling;
//    private Calendar calendar = Calendar.getInstance();
//    Date startTime;

    public DisableButtonTask(MainActivity mainActivity,
                             Button buttonSearch,
                             TextView textViewInfoAboutDisabling){
        this.mainActivity = mainActivity;
        mButtonSearch = buttonSearch;
        mTextViewInfoAboutDisabling = textViewInfoAboutDisabling;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mButtonSearch.setEnabled(false);
        Log.i(TAG, "Starting disabling button Search");
        //startTime = calendar.getTime();

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
        String infoAboutDisabling = mainActivity.getResources()
                .getString(R.string.info_about_disabling, minutes, seconds);
        mTextViewInfoAboutDisabling.setText(infoAboutDisabling);
        //Log.i(TAG, "Time left " + minutes + " : " + seconds);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        mButtonSearch.setEnabled(true);
        mTextViewInfoAboutDisabling.setText("");
    }
}
