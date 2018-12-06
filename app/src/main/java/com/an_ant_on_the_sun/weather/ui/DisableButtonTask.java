package com.an_ant_on_the_sun.weather.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.an_ant_on_the_sun.weather.R;

import java.util.concurrent.TimeUnit;

public class DisableButtonTask extends AsyncTask {

    //private Context mContext;
    private MainActivity mainActivity;
    private Button mButtonSearch;
    private TextView mTextViewInfoAboutDisabling;

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
        mButtonSearch.setVisibility(View.INVISIBLE);
        String infoAboutDisabling = mainActivity.getResources()
                .getString(R.string.info_about_disabling);
        mTextViewInfoAboutDisabling.setText(infoAboutDisabling);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        mButtonSearch.setVisibility(View.VISIBLE);
        mTextViewInfoAboutDisabling.setText("");
    }
}
