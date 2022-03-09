package nl.fontys.TransitSpot.Collections;

import nl.fontys.TransitSpot.DTO.Station.StationDTO;
import nl.fontys.TransitSpot.DTO.Station.StationFullDTO;
import nl.fontys.TransitSpot.Entity.Station;

import java.util.List;
import java.util.UUID;

public interface StationCollection {
    List<StationFullDTO> GetAll();
    List<StationFullDTO> GetAll(Integer page);
    StationFullDTO GetByID(UUID ID);
    Long GetCount();
    boolean Add(StationFullDTO station);
    boolean Update(StationFullDTO station);
    boolean Remove(UUID ID);
}
