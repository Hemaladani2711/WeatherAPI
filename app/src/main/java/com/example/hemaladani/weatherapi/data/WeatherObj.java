package com.example.hemaladani.weatherapi.data;

/**
 * Created by hemaladani on 4/4/18.
 */

public class WeatherObj {
    private int counter;
    private long time;

    private String Summary;

public WeatherObj(){

}


    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }



}
