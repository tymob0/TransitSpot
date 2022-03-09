package nl.fontys.TransitSpot.Collections;

import nl.fontys.TransitSpot.DTO.Route.RouteRequestDTO;
import nl.fontys.TransitSpot.DTO.Route.RouteResponseDTO;
import nl.fontys.TransitSpot.DTO.Station.StationFullDTO;
import nl.fontys.TransitSpot.Entity.Station;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface RouteCollection {
    List<RouteResponseDTO> GetAll();
    List<RouteResponseDTO> GetAll(Integer page);
    RouteResponseDTO GetByID(UUID ID);
    Long GetCount();
    boolean Add(RouteRequestDTO routeDTO);
    boolean Update(RouteRequestDTO routeM);
    boolean Remove(UUID ID);
}
