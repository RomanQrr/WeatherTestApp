package ru.RomanQrr.android.weathertestapp.backend.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.RomanQrr.android.weathertestapp.backend.DTO.CoordinatesDTO;
import ru.RomanQrr.android.weathertestapp.backend.Exceptions.ForecastNotFoundException;
import ru.RomanQrr.android.weathertestapp.backend.Exceptions.LocationNotFoundException;
import ru.RomanQrr.android.weathertestapp.backend.model.impl.weatherjson.Forecast;
import ru.RomanQrr.android.weathertestapp.backend.model.impl.weatherjson.Root;
import ru.RomanQrr.android.weathertestapp.backend.model.interfaces.ForecastInterface;

public class WeatherService {
    private ArrayList<Forecast> forecasts;
    private Type listType;
    private String apikey;

    public WeatherService(String apikey, Type listType){
        this.apikey = apikey;
        this.listType = listType;
    }

    public WeatherService(String apikey, String rootJson, Type jsonType){
        this(apikey, jsonType);
        Gson gson = new Gson();
        forecasts = gson.fromJson(rootJson, jsonType);
    }

    //Method builds the API URL and passes it to makeAPICall(String url)
    public void fetchForecasts(CoordinatesDTO coordinates) throws ForecastNotFoundException{
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://api.openweathermap.org/data/2.5/forecast?lat=")
                .append(coordinates.getLat())
                .append("&lon=")
                .append(coordinates.getLon())
                .append("&appid=")
                .append(apikey)
                .append("&units=metric");
        makeAPICall(stringBuilder.toString());
    }

    //Method makes the API call, receives the forecast JSON, and parses it into forecasts list.
    private void makeAPICall(String url) throws ForecastNotFoundException {
        URL apiCall;
        try {
            apiCall = new URL(url);
        } catch (MalformedURLException e) {
            throw new ForecastNotFoundException("URL didn't form", e);
        }

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(apiCall.openStream()));
            Gson gson = new Gson();
            Root bla = gson.fromJson(reader, Root.class);
            forecasts = bla.getList();
        } catch (IOException e) {
            throw new ForecastNotFoundException("IO problems", e);
        }
    }

    //Method returns URL of an image corresponding to weather in id position in the forecasts list
    public String getWeatherImageURL(int id){
        return new StringBuilder()
                .append("https://openweathermap.org/img/wn/")
                .append(forecasts.get(id).getWeatherImageName())
                .append(".png")
                .toString();
    }

    //Method returns temperature of the weather in id position in the forecasts list
    public double getTemp(int id) {
        return forecasts.get(id).getTemp();
    }

    //Method returns air pressure of the weather in id position in the forecasts list
    public double getPressure(int id) {
        return forecasts.get(id).getPressure();
    }

    //Method returns when the weather in id positing in the forecasts list is expected to occur
    public Date getTime(int id) {
        return forecasts.get(id).getTime();
    }

    //Method returns humidity of the weather in id position in the forecasts list
    public double getHumidity(int id) {
        return forecasts.get(id).getHumidity();
    }

    //Method returns JSON string of forecasts list
    public String getListAsJSON(){
        return new Gson().toJson(forecasts);
    }

}
