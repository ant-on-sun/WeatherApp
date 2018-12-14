package com.an_ant_on_the_sun.weather.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;

public class StateButtonReceiver extends BroadcastReceiver {

    private Button mButtonSearch;

    public StateButtonReceiver(Button buttonSearch) {
        mButtonSearch = buttonSearch;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Check if search button is enabled. If it is not than do nothing.
        if(!mButtonSearch.isEnabled()){
            return;
        }
        StateButtonTask stateButtonTask = new StateButtonTask(context.getApplicationContext());
        stateButtonTask.execute();
    }
}
