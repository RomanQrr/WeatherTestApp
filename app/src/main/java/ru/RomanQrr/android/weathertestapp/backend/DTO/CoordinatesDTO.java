package ru.RomanQrr.android.weathertestapp.backend.DTO;

public class CoordinatesDTO {
    private double lat;
    private double lon;
    public CoordinatesDTO(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
