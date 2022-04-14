package ru.RomanQrr.android.weathertestapp.backend.model.interfaces;

import ru.RomanQrr.android.weathertestapp.backend.DTO.CoordinatesDTO;

public interface Location {
    public String getName();
    public CoordinatesDTO getCoordinates();
}
