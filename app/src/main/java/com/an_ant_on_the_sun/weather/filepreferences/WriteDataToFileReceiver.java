package com.an_ant_on_the_sun.weather.filepreferences;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WriteDataToFileReceiver extends BroadcastReceiver {
    public static String ACTION_WRITE_DATA_TO_FILE = "com.an_ant_on_the_sun.weather.WRITE_DATA_TO_FILE";

    @Override
    public void onReceive(Context context, Intent intent) {
        int cityId = intent.getIntExtra("cityId", 0);
        WriteToFileTask writeToFileTask = new WriteToFileTask(context, cityId);
        writeToFileTask.execute();
    }
}
