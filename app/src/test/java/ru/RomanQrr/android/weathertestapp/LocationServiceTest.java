package ru.RomanQrr.android.weathertestapp;

import org.junit.Test;

import static org.junit.Assert.*;

import ru.RomanQrr.android.weathertestapp.backend.Exceptions.LocationNotFoundException;
import ru.RomanQrr.android.weathertestapp.backend.builders.impl.LocationBuilderImpl;
import ru.RomanQrr.android.weathertestapp.backend.services.LocationService;

public class LocationServiceTest {
    private static final String APIKEY = "931a6e2dd4fb0d3bb301a28edc77c44d";
    private static final double DELTA = 0.000000001d;

    //,"Omsk",54.991375,73.371529

    @Test
    public void locationServiceTest1(){
        LocationService locationService = new LocationService(APIKEY, new LocationBuilderImpl());
        try{
            locationService.setLocation("Omsk");
        } catch (LocationNotFoundException e) {
            e.printStackTrace();
            fail();
            return;
        }
        assertEquals("Omsk", locationService.getName());
        assertEquals(54.991375, locationService.getCoordinates().getLat(), DELTA);
        assertEquals(73.371529, locationService.getCoordinates().getLon(), DELTA);
        LocationService locationService1 = new LocationService(APIKEY, new LocationBuilderImpl(), locationService.getLocationAsJson());
        assertEquals(locationService.getName(), locationService1.getName());
        assertEquals(locationService.getCoordinates().getLat(), locationService1.getCoordinates().getLat(), DELTA);
        assertEquals(locationService.getCoordinates().getLon(), locationService1.getCoordinates().getLon(), DELTA);

    }
}
