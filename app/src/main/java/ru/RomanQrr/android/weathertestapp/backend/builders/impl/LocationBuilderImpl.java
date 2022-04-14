package ru.RomanQrr.android.weathertestapp.backend.builders.impl;

import ru.RomanQrr.android.weathertestapp.backend.DTO.CoordinatesDTO;
import ru.RomanQrr.android.weathertestapp.backend.builders.interfaces.LocationBuilder;
import ru.RomanQrr.android.weathertestapp.backend.model.impl.LocationImpl;
import ru.RomanQrr.android.weathertestapp.backend.model.interfaces.Location;

public class LocationBuilderImpl implements LocationBuilder {
    private String name;
    private double lat;
    private double lon;

    public LocationBuilderImpl(){
        name = "";
        lat = 0;
        lon = 0;
    }

    @Override
    public Location build() {
        return new LocationImpl(name, lat, lon);
    }

    @Override
    public LocationBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public LocationBuilder setCoordinated(CoordinatesDTO coordinates) {
        lat = coordinates.getLat();
        lon = coordinates.getLon();
        return this;
    }

    @Override
    public LocationBuilder setLat(double lat) {
        this.lat = lat;
        return this;
    }

    @Override
    public LocationBuilder setLon(double lon) {
        this.lon = lon;
        return this;
    }

    @Override
    public LocationBuilder reset() {
        name = "";
        lat = 0;
        lon = 0;
        return this;
    }
}
