package ru.RomanQrr.android.weathertestapp;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import ru.RomanQrr.android.weathertestapp.backend.DTO.CoordinatesDTO;
import ru.RomanQrr.android.weathertestapp.backend.Exceptions.ForecastNotFoundException;
import ru.RomanQrr.android.weathertestapp.backend.model.impl.weatherjson.Forecast;
import ru.RomanQrr.android.weathertestapp.backend.services.WeatherService;

public class WeatherServiceTest {
    private static final String APIKEY = "931a6e2dd4fb0d3bb301a28edc77c44d";
    private static final double DELTA = 0.000000001d;

    @Test
    public void weatherServiceTest1(){
        WeatherService weatherService = new WeatherService(APIKEY, new TypeToken<ArrayList<Forecast>>() {}.getType());
        try{
            weatherService.fetchForecasts(new CoordinatesDTO(54.991375, 73.371529));
        } catch (ForecastNotFoundException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void weatherServiceTest2(){
        String list;
        try(BufferedReader reader = new BufferedReader(
                new FileReader("D:\\Progects\\Work\\Android\\WeatherTestApp\\testWeatherForecast.txt"))){
            list = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            fail("read from file failed");
            return;
        }
        WeatherService weatherService = new WeatherService(APIKEY,list, new TypeToken<ArrayList<Forecast>>() {}.getType());
        assertEquals(18.83d, weatherService.getTemp(0), DELTA);
        assertEquals(30d, weatherService.getHumidity(1), DELTA);
        assertEquals(new Date(1650358800), weatherService.getTime(39));
        assertEquals("https://openweathermap.org/img/wn/04d", weatherService.getWeatherImageURL(38));
    }
    @Test
    public void weatherServiceTest3() {
        String list;
        try (BufferedReader reader = new BufferedReader(
                new FileReader("D:\\Progects\\Work\\Android\\WeatherTestApp\\testWeatherForecast.txt"))) {
            list = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            fail("read from file failed");
            return;
        }
        Type type = new TypeToken<ArrayList<Forecast>>() {}.getType();
        WeatherService weatherService = new WeatherService(APIKEY, list, type);
        WeatherService weatherService1 = new WeatherService(APIKEY, weatherService.getListAsJSON(), type);
        for(int i = 0; i < 40; i++){
            assertEquals(weatherService.getHumidity(i), weatherService1.getHumidity(i), DELTA);
            assertEquals(weatherService.getWeatherImageURL(i), weatherService1.getWeatherImageURL(i));
            assertEquals(weatherService.getTemp(i), weatherService1.getTemp(i), DELTA);
            assertEquals(weatherService.getPressure(i), weatherService1.getPressure(i), DELTA);
            assertEquals(weatherService.getTime(i),weatherService1.getTime(i));
        }
    }

}
