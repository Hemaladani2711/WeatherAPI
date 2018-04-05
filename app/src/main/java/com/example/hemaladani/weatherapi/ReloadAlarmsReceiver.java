package com.example.hemaladani.weatherapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.hemaladani.weatherapi.Alarm.Alarms;

/**
 * Created by hemaladani on 4/5/18.
 */

public class ReloadAlarmsReceiver extends BroadcastReceiver {
    private static String TAG=ReloadAlarmsReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"REscheduling alarms");
        Alarms.manageScheduling(context);

    }
}
