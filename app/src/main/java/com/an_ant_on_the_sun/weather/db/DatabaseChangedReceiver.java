package com.an_ant_on_the_sun.weather.db;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.an_ant_on_the_sun.weather.ui.MainActivity;

public class DatabaseChangedReceiver extends BroadcastReceiver {

    public static String ACTION_DATABASE_CHANGED = "com.an_ant_on_the_sun.weather.DATABASE_CHANGED";
    private MainActivity mainActivity;

    public DatabaseChangedReceiver(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(mainActivity != null){
            mainActivity.updateDataOnScreen();
        }
    }
}
