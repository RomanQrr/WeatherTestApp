package ru.RomanQrr.android.weathertestapp.backend.model.impl;

import ru.RomanQrr.android.weathertestapp.backend.DTO.CoordinatesDTO;
import ru.RomanQrr.android.weathertestapp.backend.model.interfaces.Location;

public class LocationImpl implements Location {
    private String name;
    private double lat;
    private double lon;

    public LocationImpl(String name, double lat, double lon){
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public CoordinatesDTO getCoordinates() {
        return new CoordinatesDTO(lat, lon);
    }
}
