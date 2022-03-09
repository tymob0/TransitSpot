package nl.fontys.TransitSpot.Collections;

import nl.fontys.TransitSpot.Entity.Station;

import java.util.List;
import java.util.UUID;

public interface StationEntityCollection {
    List<Station> GetAllStations();
    Station GetStationByID(UUID ID);
}
