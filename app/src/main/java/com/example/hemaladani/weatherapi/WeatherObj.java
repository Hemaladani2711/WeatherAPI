package com.example.hemaladani.weatherapi;

/**
 * Created by hemaladani on 4/4/18.
 */

public class WeatherObj {
    private int counter,temp;
    private long time;
    private String Summary;

    public WeatherObj(int counter, int temp, long time, String summary) {
        this.counter = counter;
        this.temp = temp;
        this.time = time;
        Summary = summary;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
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
