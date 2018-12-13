package com.an_ant_on_the_sun.weather.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

public class DisableButtonReceiver extends BroadcastReceiver {

    private MainActivity mainActivity;
    private Button mButtonSearch;
    private TextView mTextViewInfoAboutDisabling;

    public DisableButtonReceiver(MainActivity mainActivity,
                                 Button buttonSearch,
                                 TextView textViewInfoAboutDisabling) {
        this.mainActivity = mainActivity;
        mButtonSearch = buttonSearch;
        mTextViewInfoAboutDisabling = textViewInfoAboutDisabling;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Check if search button is enabled. If it is not than do nothing.
        if(!mButtonSearch.isEnabled()){
            return;
        }
        DisableButtonTask disableButtonTask = new DisableButtonTask(mainActivity,
                mButtonSearch, mTextViewInfoAboutDisabling);
        disableButtonTask.execute();
    }
}
