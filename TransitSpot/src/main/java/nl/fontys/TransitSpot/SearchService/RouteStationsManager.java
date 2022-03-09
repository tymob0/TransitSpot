package nl.fontys.TransitSpot.SearchService;

import nl.fontys.TransitSpot.DTO.Route.RouteResponseDTO;
import nl.fontys.TransitSpot.Entity.Route;
import nl.fontys.TransitSpot.Entity.Station;

import java.util.HashMap;
import java.util.List;

public interface RouteStationsManager {
    HashMap<Station, Integer> GetAdjacentStations(Station station);
    RouteResponseDTO GetRouteByEndpoints(Station origin, Station destination);
}
