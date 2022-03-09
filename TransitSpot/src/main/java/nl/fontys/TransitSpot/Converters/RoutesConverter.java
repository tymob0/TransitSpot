package nl.fontys.TransitSpot.Converters;

import nl.fontys.TransitSpot.DTO.Route.RouteRequestDTO;
import nl.fontys.TransitSpot.DTO.Route.RouteResponseDTO;
import nl.fontys.TransitSpot.Entity.Route;

import java.util.List;

public interface RoutesConverter {
    RouteResponseDTO EntityToDTO(Route route);

    Route DTOtoEntity(RouteRequestDTO routeRequestDTO);

    List<RouteResponseDTO> EntityToDTO(List<Route> routes);

    List<Route> DTOtoEntity(List<RouteRequestDTO> routes);
}
