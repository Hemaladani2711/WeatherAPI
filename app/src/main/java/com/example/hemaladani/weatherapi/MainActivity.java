package com.example.hemaladani.weatherapi;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.hemaladani.weatherapi.services.UpdateService;

public class MainActivity extends AppCompatActivity {

private IntentFilter mIntent;
private int Count;
public static String ACTION="NOW";
public static String COUNTER="COUNTER";
    private static String TAG=MainActivity.class.getSimpleName();
    private static int JOB_ID=1;
    //public static EventBus weatherEvent=EventBus.getDefault();
    TextView tCounter,tSummary,tTime,tTemp;

    @Override
    protected void onStart() {
        super.onStart();
//     EventBus.getDefault().register(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(COUNTER,Count);

        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tCounter.setText(""+savedInstanceState.getInt(COUNTER,0));
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(statusReceiver,new IntentFilter(ACTION));
      //  EventBus.getDefault().register(this);

    }
    @Override
    protected void onStop() {
        super.onStop();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tCounter=findViewById(R.id.txtCounter);
        tSummary=findViewById(R.id.txtSummary);
        tTime=findViewById(R.id.txtTime);
        tTemp=findViewById(R.id.txtTemp);
        if(savedInstanceState!=null){
            tCounter.setText(savedInstanceState.getInt(COUNTER,0));
        }

         // EventBus.getDefault().register(this);
        manageCleanUpJob();





    }
    private BroadcastReceiver statusReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Count=intent.getIntExtra(COUNTER,0);

            //Toast.makeText(context,""+,Toast.LENGTH_LONG).show();
            tCounter.setText(""+Count);
        }
    };

  /*  @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WeatherObj weatherObj){
        tCounter.setText(""+weatherObj.getCounter());

    }*/


    private void manageCleanUpJob(){
        Log.i(TAG,"Job Scheduling");

        JobScheduler jobScheduler=(JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);

        //Job interval every 5 minutes
        long jobInterval=300000;

        ComponentName jobService=new ComponentName(getApplicationContext(), UpdateService.class);

        JobInfo task;


            task = new JobInfo.Builder(JOB_ID, jobService)
                    .setMinimumLatency(jobInterval).setBackoffCriteria(0,JobInfo.BACKOFF_POLICY_LINEAR)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY).setPersisted(true).build();


        if(jobScheduler.getPendingJob(JOB_ID)==null){
        if(jobScheduler.schedule(task)!=JobScheduler.RESULT_SUCCESS){
            Log.i(TAG,"failed to schedule job");
        }}


    }
}
