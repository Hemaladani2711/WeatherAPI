package com.example.hemaladani.weatherapi.WebService;

import android.util.Log;

import com.example.hemaladani.weatherapi.data.WeatherObj;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hemaladani on 4/5/18.
 */

public class parseWeatherInfo {
  private static String TAG=parseWeatherInfo.class.getSimpleName();

    public WeatherObj downloadGalleryItems(String url){

        WeatherObj weatherObj1=null;
        try{

            String jsonString=getUrlString(url);


            JSONObject jsonBody=new JSONObject(jsonString);
          weatherObj1 = parseItems(jsonBody);
            Log.i(TAG,"Received Json:"+jsonString);

        }catch (IOException|JSONException ioe){
            Log.e(TAG,"Failed to fetch items",ioe);
        }
        return weatherObj1;


    }

    private WeatherObj parseItems(JSONObject jsonBody)throws IOException,JSONException {
            JSONObject photoJsonObject=jsonBody.getJSONObject("currently");
            WeatherObj item=new WeatherObj();
            item.setSummary(photoJsonObject.getString("summary"));
            item.setTime(photoJsonObject.getLong("time"));
        return item;



        }





    private byte[] getUrlBytes(String urlSpec) throws IOException{
        //urlSpec="https://www.google.com/";
        URL url = new URL(urlSpec);
        Log.i("UrlSpec",""+urlSpec);

        HttpURLConnection connection=(HttpURLConnection) url.openConnection();
        try{
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            InputStream in=connection.getInputStream();
            if(connection.getResponseCode()!=HttpURLConnection.HTTP_OK){

                throw new IOException(connection.getResponseMessage()+":with"+urlSpec);
            }
            int bytesRead=0;
            byte[] buffer=new byte[1024];
            while((bytesRead=in.read(buffer))>0){
                out.write(buffer,0,bytesRead);
            }
            out.close();
            return out.toByteArray();}
        finally {
            connection.disconnect();
        }
    }

    private String getUrlString(String urlSpecs)throws IOException{
        // Log.i("UrlInfo",""+new String(getUrlBytes(urlSpecs)));
        return new String(getUrlBytes(urlSpecs));

    }

}
