package nl.fontys.TransitSpot.DTO.Path;

import lombok.Getter;
import lombok.Setter;
import nl.fontys.TransitSpot.DTO.Route.RouteResponseDTO;
import nl.fontys.TransitSpot.Entity.Route;

import java.util.List;

public class PathDTO {
    private @Getter @Setter String name;
    private @Getter @Setter List<RouteResponseDTO> routes;
    private @Getter @Setter int time;
    private @Getter @Setter Double price;
    public PathDTO(String name, List<RouteResponseDTO> routes, int time, Double price) {
        this.name = name;
        this.routes = routes;
        this.time = time;
        this.price = price;
    }
}
