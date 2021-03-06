package com.an_ant_on_the_sun.weather.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;

public class DisableButtonReceiver extends BroadcastReceiver {
    public static String ACTION_DISABLE_BUTTON = "com.an_ant_on_the_sun.weather.DISABLE_BUTTON";

    Button mButtonSearch;

    public DisableButtonReceiver(Button buttonSearch) {
        mButtonSearch = buttonSearch;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(mButtonSearch.isEnabled()){
            mButtonSearch.setEnabled(false);
        }
    }
}
