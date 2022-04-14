package ru.RomanQrr.android.weathertestapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import ru.RomanQrr.android.weathertestapp.backend.Exceptions.ForecastNotFoundException;
import ru.RomanQrr.android.weathertestapp.backend.Exceptions.LocationNotFoundException;
import ru.RomanQrr.android.weathertestapp.backend.builders.impl.LocationBuilderImpl;
import ru.RomanQrr.android.weathertestapp.backend.model.impl.weatherjson.Forecast;
import ru.RomanQrr.android.weathertestapp.backend.services.LocationService;
import ru.RomanQrr.android.weathertestapp.backend.services.WeatherService;

public class MainActivity extends AppCompatActivity {
    private static final String APIKEY = "931a6e2dd4fb0d3bb301a28edc77c44d";

    private LocationService locationService;
    private WeatherService weatherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Attempt to restart location service from saved, with default values being Omsk
        locationService = new LocationService(APIKEY, new LocationBuilderImpl(),
                savedInstanceState.getString("WeatherLocationName", "Omsk"),
                savedInstanceState.getDouble("WeatherLocationLat", 54.991375),
                savedInstanceState.getDouble("WeatherLocationLon", 73.371529));

        setContentView(R.layout.activity_main);

        //Start the weather service, fetch fresh forecast for initialized location,
        //and refresh UI if fetch was successful.
        weatherService = new WeatherService(APIKEY, new TypeToken<ArrayList<Forecast>>() {}.getType());
        updateForecast(null);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("WeatherLocationName", locationService.getName());
        outState.putDouble("WeatherLocationLat", locationService.getCoordinates().getLat());
        outState.putDouble("WeatherLocationLon", locationService.getCoordinates().getLon());
    }

    public void searchForLocation(View view){
        try {
            locationService.setLocation(((TextView) findViewById(R.id.location_search_bar)).getText().toString());
            weatherService.fetchForecasts(locationService.getCoordinates());
            refresh();
        } catch (LocationNotFoundException | ForecastNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateForecast(View view){
        try{
            weatherService.fetchForecasts(locationService.getCoordinates());
            refresh();
        }
        catch (ForecastNotFoundException e){
            e.printStackTrace();
        }
    }

    //Method refreshes the image and text views with available information.
    private void refresh(){
        //Set search bar text to name of current location
        ((TextView) findViewById(R.id.location_search_bar)).setText(locationService.getName());

        //set main weather image
        Glide.with(getApplicationContext())
                .load(weatherService.getWeatherImageURL(0))
                .into((ImageView)findViewById(R.id.mainWeatherImage));
        //set main weather text
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Humidity: ")
                .append(weatherService.getHumidity(0))
                .append("% Temperature: ")
                .append(weatherService.getTemp(0))
                .append("C Pressure: ")
                .append(weatherService.getPressure(0));
        ((TextView) findViewById(R.id.mainWeatherText)).setText(stringBuilder.toString());

        //set weather for day 2
        Glide.with(getApplicationContext())
                .load(weatherService.getWeatherImageURL(8))
                .into((ImageView)findViewById(R.id.weatherImage2));
        String temperature2 = weatherService.getTemp(8) + " C";
        ((TextView) findViewById(R.id.weatherText2)).setText(temperature2);

        //set weather for day 3
        Glide.with(getApplicationContext())
                .load(weatherService.getWeatherImageURL(16))
                .into((ImageView)findViewById(R.id.weatherImage3));
        String temperature3 = weatherService.getTemp(16) + " C";
        ((TextView) findViewById(R.id.weatherText3)).setText(temperature3);

        //set weather for day 4
        Glide.with(getApplicationContext())
                .load(weatherService.getWeatherImageURL(24))
                .into((ImageView)findViewById(R.id.weatherImage4));
        String temperature4 = weatherService.getTemp(24) + " C";
        ((TextView) findViewById(R.id.weatherText4)).setText(temperature4);

        //set weather for day 5
        Glide.with(getApplicationContext())
                .load(weatherService.getWeatherImageURL(32))
                .into((ImageView)findViewById(R.id.weatherImage5));
        String temperature5 = weatherService.getTemp(32) + " C";
        ((TextView) findViewById(R.id.weatherText5)).setText(temperature5);

    }

}