package com.example.hemaladani.weatherapi.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.hemaladani.weatherapi.MainActivity;
import com.example.hemaladani.weatherapi.data.WeatherObj;
import com.example.hemaladani.weatherapi.WebService.parseWeatherInfo;

/**
 * Created by hemaladani on 4/5/18.
 */

public class updateCounter extends IntentService {


    int count=0;
    private String COUNTER="COUNTER";
    private  String TAG=updateCounter.class.getSimpleName();

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     **/

    public updateCounter(){
        super(updateCounter.class.getSimpleName());
    }

    public updateCounter(String TAG) {
        super(TAG);
    }




    private WeatherObj writeCounter(WeatherObj weatherObj){
        SharedPreferences sharedPreferences=getReferenceMng();

        //check if Counter exists in sharedPref
        if(sharedPreferences.contains(COUNTER))
            count=sharedPreferences.getInt(COUNTER,0);

        //Update counter
        ++count;

        weatherObj.setCounter(count);


        // write values to shared preferences in case app is offline

        sharedPreferences.edit().putInt(COUNTER,count).commit();
        sharedPreferences.edit().putString(MainActivity.SUMMARY,weatherObj.getSummary()).commit();
        sharedPreferences.edit().putLong(MainActivity.TIME,weatherObj.getTime()).commit();

        return weatherObj;

    }
    private  SharedPreferences getReferenceMng(){
        return PreferenceManager.getDefaultSharedPreferences(this);
    }



    @Override
    protected void onHandleIntent(@Nullable Intent intent1) {

        if(isNetworkAvailable()){

            new fetchWeatherInfo().execute();

        }
    }

    private class fetchWeatherInfo extends AsyncTask<WeatherObj,Void,WeatherObj>{


        @Override
        protected WeatherObj doInBackground(WeatherObj... weatherObjs) {
            Log.i(TAG,"Do in Background");

            WeatherObj weatherObj=new parseWeatherInfo().downloadGalleryItems(MainActivity.URL);
            weatherObj=writeCounter(weatherObj);

            //weatherObj.setCounter(count);
            return weatherObj;
        }

        @Override
        protected void onPostExecute(WeatherObj weatherObj) {
            super.onPostExecute(weatherObj);
            Log.i(TAG,"Post execute: Counter: "+weatherObj.getCounter()+" Releasing broadcast");
            Intent intent=new Intent();
            intent.putExtra(MainActivity.COUNTER,weatherObj.getCounter());
            intent.putExtra(MainActivity.TIME,weatherObj.getTime());
            intent.putExtra(MainActivity.SUMMARY,weatherObj.getSummary());

            intent.setAction(MainActivity.ACTION);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);


        }
    }
}
