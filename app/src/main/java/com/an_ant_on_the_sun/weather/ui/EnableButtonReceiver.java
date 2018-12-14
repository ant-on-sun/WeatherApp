package com.an_ant_on_the_sun.weather.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;

public class EnableButtonReceiver extends BroadcastReceiver {
    public static String ACTION_ENABLE_BUTTON = "com.an_ant_on_the_sun.weather.ENABLE_BUTTON";

    Button mButtonSearch;

    public EnableButtonReceiver(Button buttonSearch) {
        mButtonSearch = buttonSearch;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(!mButtonSearch.isEnabled()){
            mButtonSearch.setEnabled(true);
        }
    }
}
