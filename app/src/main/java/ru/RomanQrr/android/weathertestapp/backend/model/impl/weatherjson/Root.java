package ru.RomanQrr.android.weathertestapp.backend.model.impl.weatherjson;

import java.util.ArrayList;

import ru.RomanQrr.android.weathertestapp.backend.model.interfaces.ForecastInterface;

public class Root{
    public String cod;
    public int message;
    public int cnt;
    private ArrayList<Forecast> list;
    public City city;

    public ArrayList<Forecast> getList() {
        return list;
    }
}
