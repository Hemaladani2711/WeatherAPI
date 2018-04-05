package com.example.hemaladani.weatherapi.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.hemaladani.weatherapi.services.updateCounter;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by hemaladani on 4/5/18.
 */

public class Alarms {
    private static String TAG=Alarms.class.getSimpleName();
    public static void manageScheduling(Context context){

        //Interval 5 Minutes
        long jobInterval=300000;

        Log.i(TAG,"Scheduling Alarm");

        AlarmManager alarmManager=(AlarmManager)context.getSystemService(ALARM_SERVICE);
        Intent intent=new Intent(context,updateCounter.class);
        PendingIntent pendingIntent=PendingIntent.getService(context,0,intent, 0);

        //start alarm now and update 5 Minutes
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(),jobInterval,pendingIntent);
    }
}
