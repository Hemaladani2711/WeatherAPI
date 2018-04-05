package com.example.hemaladani.weatherapi.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.util.Log;

import com.example.hemaladani.weatherapi.weatherObj;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by hemaladani on 4/4/18.
 */

public class UpdateService extends JobService {
    @Override
    public void onDestroy() {
        super.onDestroy();
        //MainActivity.weatherEvent.unregister(this);
        EventBus.getDefault().unregister(this);
    }

    int count=1;
    private static String TAG=UpdateService.class.getSimpleName();
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG,"OnStartJob");
        EventBus.getDefault().register(this);
        //MainActivity.weatherEvent.register(this);
        new CleanupTask().execute(params);



        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG,"OnStopJob");
        return false;
    }

    //Download data from web service

    private class CleanupTask extends AsyncTask<JobParameters, Void, JobParameters> {

        @Override
        protected JobParameters doInBackground(JobParameters... params) {

           // int count = getContentResolver().delete(DatabaseContract.CONTENT_URI, where, args);
            //Log.d(TAG, "Cleaned up " + count + " completed tasks");

            count++;
            return params[0];
        }

        @Override
        protected void onPostExecute(JobParameters jobParameters) {

            EventBus.getDefault().postSticky(new weatherObj(count,0,0,null));

            //Notify that the work is now done
            jobFinished(jobParameters, true);
        }
    }

}