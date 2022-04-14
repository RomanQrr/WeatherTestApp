package ru.RomanQrr.android.weathertestapp.backend.builders.interfaces;

import ru.RomanQrr.android.weathertestapp.backend.DTO.CoordinatesDTO;
import ru.RomanQrr.android.weathertestapp.backend.model.interfaces.Location;

public interface LocationBuilder {
    Location build();
    LocationBuilder setName(String name);
    LocationBuilder setCoordinated(CoordinatesDTO coordinates);
    LocationBuilder setLat(double lat);
    LocationBuilder setLon(double lon);
    LocationBuilder reset();
}
