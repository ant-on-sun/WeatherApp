package com.an_ant_on_the_sun.weather.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.an_ant_on_the_sun.weather.model.QueryParameters;

public class RequestDataFromWebReceiver extends BroadcastReceiver {
    public static String ACTION_NEED_DATA_FROM_WEB = "com.an_ant_on_the_sun.weather.NEED_DATA_FROM_WEB";
    private QueryParameters queryParameters = QueryParameters.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {
        int cityId = intent.getIntExtra("cityId", 0);
        queryParameters.setCityId(cityId);
        QueryTask queryTask = new QueryTask(queryParameters, context);
        queryTask.execute();
    }
}
