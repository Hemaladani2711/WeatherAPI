package com.example.hemaladani.weatherapi;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.hemaladani.weatherapi.services.UpdateService;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private static String TAG=MainActivity.class.getSimpleName();
    private static int JOB_ID=1;
    //public static EventBus weatherEvent=EventBus.getDefault();
    TextView tCounter,tSummary,tTime,tTemp;

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();

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
       // EventBus.getDefault().register(this);
        manageCleanUpJob();



    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doThis(weatherObj weatherObj){
        tCounter.setText(""+weatherObj.getCounter());

    }


    private void manageCleanUpJob(){
        Log.i(TAG,"Job Scheduling");

        JobScheduler jobScheduler=(JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);

        //Job interval every 10 seconds
        long jobInterval=5000L;

        ComponentName jobService=new ComponentName(getApplicationContext(), UpdateService.class);

        JobInfo task=new JobInfo.Builder(JOB_ID,jobService).setPersisted(true).setPeriodic(jobInterval).setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY).build();

        if(jobScheduler.getPendingJob(JOB_ID)==null){
        if(jobScheduler.schedule(task)!=JobScheduler.RESULT_SUCCESS){
            Log.i(TAG,"failed to schedule job");
        }}


    }
}
