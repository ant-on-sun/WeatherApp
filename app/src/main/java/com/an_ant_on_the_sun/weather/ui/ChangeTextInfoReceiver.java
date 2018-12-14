package com.an_ant_on_the_sun.weather.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.an_ant_on_the_sun.weather.R;

public class ChangeTextInfoReceiver extends BroadcastReceiver {
    public static String ACTION_CHANGE_TEXT = "com.an_ant_on_the_sun.weather.CHANGE_TEXT";

    TextView mTextViewInfoAboutDisabling;

    public ChangeTextInfoReceiver(TextView textViewInfoAboutDisabling) {
        mTextViewInfoAboutDisabling = textViewInfoAboutDisabling;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String text = intent.getStringExtra("text");
        if(text.isEmpty()){
            mTextViewInfoAboutDisabling.setText("");
            return;
        }
        String info = mTextViewInfoAboutDisabling.getContext().getResources()
                .getString(R.string.info_about_disabling, text);
        mTextViewInfoAboutDisabling.setText(info);
    }
}
