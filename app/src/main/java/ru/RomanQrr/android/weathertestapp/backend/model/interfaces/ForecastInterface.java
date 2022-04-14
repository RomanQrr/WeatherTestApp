package ru.RomanQrr.android.weathertestapp.backend.model.interfaces;

import java.util.Date;

public interface ForecastInterface {
    double getTemp();
    double getPressure();
    Date getTime();
    String getWeatherImageName();
    double getHumidity();
}
