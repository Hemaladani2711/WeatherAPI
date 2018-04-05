package com.example.hemaladani.weatherapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.hemaladani.weatherapi.Alarm.Alarms;

public class MainActivity extends AppCompatActivity {


public static String ACTION="NOW";
public static String COUNTER="COUNTER";
public static String SUMMARY="SUMMARY",TIME="TIME";


public static String URL="https://api.darksky.net/forecast/c63d1a62f0f56add8fff7deb5f217aac/37.8267,-122.4233";
    private static String TAG=MainActivity.class.getSimpleName();



    TextView tCounter,tSummary,tTime;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(statusReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(statusReceiver,new IntentFilter(ACTION));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tCounter=findViewById(R.id.txtCounter);
        tSummary=findViewById(R.id.txtSummary);
        tTime=findViewById(R.id.txtTime);



        SharedPreferences sharedPreferences=getReferenceMng();
        if(sharedPreferences.contains(COUNTER)){
            tCounter.setText(""+sharedPreferences.getInt(COUNTER,0));
            tSummary.setText(""+sharedPreferences.getString(SUMMARY,""));
            tTime.setText(""+sharedPreferences.getLong(TIME,0));

        }


        Alarms.manageScheduling(this);



    }



    private BroadcastReceiver statusReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.i(TAG,"Broadcast received");
            tCounter.setText(""+intent.getIntExtra(COUNTER,0));
            tSummary.setText(""+intent.getStringExtra(SUMMARY));
            tTime.setText(""+intent.getLongExtra(TIME,0));
        }
    };

    private SharedPreferences getReferenceMng(){
        return PreferenceManager.getDefaultSharedPreferences(this);
    }



 /* private void manageScheduling(){

      //Interval 5 Minutes
      long jobInterval=300000;

      Log.i(TAG,"Scheduling Alarm");

      AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
      Intent intent=new Intent(MainActivity.this,updateCounter.class);
      PendingIntent pendingIntent=PendingIntent.getService(this,0,intent, 0);

      //start alarm now and update 5 Minutes
      alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,Calendar.getInstance().getTimeInMillis(),jobInterval,pendingIntent);
  }*/
}
