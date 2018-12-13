package com.an_ant_on_the_sun.weather.ui;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;

import com.an_ant_on_the_sun.weather.filepreferences.FileNameForPreferences;
import com.an_ant_on_the_sun.weather.network.RequestDataFromWebReceiver;

import java.util.concurrent.TimeUnit;

public class RegularDataUpdateService extends IntentService {
    private static final String TAG = RegularDataUpdateService.class.getSimpleName();
    private static final int TIME_INTERVAL_MINUTES = 20;
    private Context mContext;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public RegularDataUpdateService() {
        super("RegularDataUpdateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mContext = getApplicationContext();
        int cityId = 0;
        while (true){
            try{
                try {
                    TimeUnit.MINUTES.sleep(TIME_INTERVAL_MINUTES);
                } catch (InterruptedException e) {
                    Log.e(TAG, "in service, while TimeUnit.SECONDS.sleep ", e);
                }
                //Read cityId from file
                SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                        FileNameForPreferences.FILE_NAME,
                        Context.MODE_PRIVATE);
                if(sharedPreferences.contains(FileNameForPreferences.FILE_CITY_ID)){
                    cityId = sharedPreferences
                            .getInt(FileNameForPreferences.FILE_CITY_ID, 0);
                    Log.i(TAG, "Read data from file, cityId = " + cityId);
                }
                if(cityId == 0){
                    continue;
                }
                Intent intentNeedDataFromWeb = new Intent(RequestDataFromWebReceiver
                        .ACTION_NEED_DATA_FROM_WEB);
                intentNeedDataFromWeb.putExtra("cityId", cityId);
                sendBroadcast(intentNeedDataFromWeb);
                Log.i(TAG, "Call for web data from service");
            } catch (Exception e){
                Log.e(TAG, "onHandleIntent() in while() catch Exception: ", e);
            }
        }
    }

}
