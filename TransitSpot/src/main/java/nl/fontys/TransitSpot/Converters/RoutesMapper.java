package nl.fontys.TransitSpot.Converters;

import nl.fontys.TransitSpot.DTO.Route.RouteRequestDTO;
import nl.fontys.TransitSpot.DTO.Route.RouteResponseDTO;
import nl.fontys.TransitSpot.Entity.Route;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoutesMapper implements RoutesConverter{
    @Override
    public RouteResponseDTO EntityToDTO(Route route) {
        ModelMapper mapper = new ModelMapper();
        RouteResponseDTO map = mapper.map(route, RouteResponseDTO.class);
        return map;
    }

    @Override
    public Route DTOtoEntity(RouteRequestDTO routeRequestDTO) {
        ModelMapper mapper = new ModelMapper();
        Route map = mapper.map(routeRequestDTO, Route.class);
        return map;
    }

    @Override
    public List<RouteResponseDTO> EntityToDTO(List<Route> routes) {
        return routes.stream().map(x->EntityToDTO(x)).collect(Collectors.toList());
    }

    @Override
    public List<Route> DTOtoEntity(List<RouteRequestDTO> routes) {
        return routes.stream().map(x->DTOtoEntity(x)).collect(Collectors.toList());
    }
}
