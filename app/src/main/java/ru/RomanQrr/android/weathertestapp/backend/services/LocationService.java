package ru.RomanQrr.android.weathertestapp.backend.services;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import ru.RomanQrr.android.weathertestapp.backend.DTO.CoordinatesDTO;
import ru.RomanQrr.android.weathertestapp.backend.Exceptions.LocationNotFoundException;
import ru.RomanQrr.android.weathertestapp.backend.builders.interfaces.LocationBuilder;
import ru.RomanQrr.android.weathertestapp.backend.model.interfaces.Location;

public class LocationService {
    private Location location;
    private LocationBuilder builder;
    private String apiKey;

    public LocationService(String apiKey, LocationBuilder builder){
        this.apiKey = apiKey;
        this.builder = builder;
    }

    public LocationService(String apiKey, LocationBuilder builder, String locationName, double locationLat, double lpcationLon){
        this(apiKey,builder);
        location = this.builder.reset()
                .setName(locationName)
                .setLat(locationLat)
                .setLon(lpcationLon)
                .build();
    }

    public LocationService(String apiKey, LocationBuilder builder, String locationJson){
        this(apiKey,builder);
        location = new Gson().fromJson(locationJson, builder.getLocationType());
    }

    //Method attempts to fetch information about requested town and save it in location
    public void setLocation(String name) throws LocationNotFoundException {
        //Setting up the API call URL
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://api.openweathermap.org/geo/1.0/direct?q=")
                .append(name)
                .append("&limit=1&appid=")
                .append(apiKey);
        //Getting the location JSON from the API
        String response = makeAPICall(stringBuilder.toString());

        //Manually extracting information we need out of JSON
        int nameIndex = response.indexOf("\"name\":") + 8;
        int latIndex = response.indexOf("\"lat\":") + 6;
        int lonIndex = response.indexOf("\"lon\":")+6;
        if(nameIndex <= 5 || latIndex <= 5 || lonIndex <= 5) {
            throw new LocationNotFoundException("No location with such name.");
        }
        //Building the location
        location = builder.reset()
                .setName(response.subSequence(nameIndex, response.indexOf("\",", nameIndex)).toString())
                .setLat(Double.parseDouble(response.subSequence(latIndex, response.indexOf(",", latIndex)).toString()))
                .setLon(Double.parseDouble(response.subSequence(lonIndex, response.indexOf(",", lonIndex)).toString()))
                .build();
    }

    //method contacts the API through the given URL and returns the resulting JSON string.
    private String makeAPICall(String url) throws LocationNotFoundException{
        URL apiCall;
        try {
            apiCall = new URL(url);
        } catch (MalformedURLException e) {
            throw new LocationNotFoundException("URL didn't form", e);
        }

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(apiCall.openStream()));
            return reader.readLine();
        } catch (IOException e) {
            throw new LocationNotFoundException("IO problems", e);
        }
    }

    public String getName(){
        return location.getName();
    }

    public CoordinatesDTO getCoordinates(){
        return location.getCoordinates();
    }

    public String getLocationAsJson(){
        return new Gson().toJson(location);
    }



}
