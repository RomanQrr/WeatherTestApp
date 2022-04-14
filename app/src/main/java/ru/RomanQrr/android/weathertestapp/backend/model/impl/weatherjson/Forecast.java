package ru.RomanQrr.android.weathertestapp.backend.model.impl.weatherjson;

import java.util.ArrayList;
import java.util.Date;

import ru.RomanQrr.android.weathertestapp.backend.model.interfaces.ForecastInterface;

public class Forecast implements ForecastInterface {
    private int dt;
    private Main main;
    private ArrayList<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    private double visibility;
    private double pop;
    private Sys sys;
    private String dt_txt;

    @Override
    public double getTemp() {
        return main.temp;
    }

    @Override
    public double getPressure() {
        return main.pressure;
    }

    @Override
    public Date getTime() {
        return new Date(dt);
    }

    @Override
    public String getWeatherImageName() {
        return weather.get(0).icon;
    }

    @Override
    public double getHumidity() {
        return main.humidity;
    }
}
