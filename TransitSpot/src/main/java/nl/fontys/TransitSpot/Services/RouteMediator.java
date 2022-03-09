package nl.fontys.TransitSpot.Services;

import nl.fontys.TransitSpot.Converters.RoutesConverter;
import nl.fontys.TransitSpot.DTO.Route.RouteRequestDTO;
import nl.fontys.TransitSpot.DTO.Route.RouteResponseDTO;
import nl.fontys.TransitSpot.Entity.Route;
import nl.fontys.TransitSpot.Entity.Station;
import nl.fontys.TransitSpot.Collections.RouteCollection;
import nl.fontys.TransitSpot.SearchService.RouteStationsManager;
import nl.fontys.TransitSpot.Storages.RouteStorage;
import nl.fontys.TransitSpot.Storages.StationStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RouteMediator implements RouteCollection, RouteStationsManager {
    private RouteStorage routes;
    private StationStorage stations;
    private RoutesConverter converter;
    @Autowired
    public RouteMediator(RouteStorage routes, StationStorage stations, RoutesConverter converter) {
        this.routes = routes;
        this.stations = stations;
        this.converter = converter;
    }
    @Override
    public List<RouteResponseDTO> GetAll() {
        return this.converter.EntityToDTO(routes.ReadAll());
    }

    @Override
    public List<RouteResponseDTO> GetAll(Integer page) {
        Page<Route> pageResult = this.routes.ReadAllPage(page);
        List<Route> list = pageResult.getContent();
        return this.converter.EntityToDTO(list);
    }

    @Override
    public RouteResponseDTO GetByID(UUID ID) {
        return this.converter.EntityToDTO(routes.ReadByID(ID));
    }

    @Override
    public Long GetCount() {
        return routes.GetCount();
    }

    @Override
    public HashMap<Station, Integer> GetAdjacentStations(Station station) {
        HashMap<Station, Integer> neighbours = new HashMap<Station, Integer>();
        for (Route route : this.routes.ReadAll()) {
            if (route.getOrigin().getID() == station.getID()) {
                Station temporaryStation = this.containsStation(neighbours.keySet(), route.getDestination());
                if (temporaryStation != null) {
                    if (neighbours.get(temporaryStation) > route.getDuration())
                        neighbours.replace(temporaryStation, route.getDuration());
                } else neighbours.put(route.getDestination(), route.getDuration());
            }
        }
        return neighbours;
    }

    @Override
    public RouteResponseDTO GetRouteByEndpoints(Station origin, Station destination) {
        RouteResponseDTO  minimalTimeRoute = null;
        for(RouteResponseDTO route : this.GetAll()){
            if(route.getOrigin().getID() == origin.getID() && route.getDestination().getID() == destination.getID()){
                if(minimalTimeRoute == null || route.getDuration() < minimalTimeRoute.getDuration())
                    minimalTimeRoute = route;
            }
        }
        return minimalTimeRoute;
    }

    private Station containsStation(Set<Station> stations, Station station) {
        for (Station s : stations) {
            if (s.getID() == station.getID())
                return s;
        }
        return null;
    }


    @Override
    public boolean Add(RouteRequestDTO routeDTO) {
        if(this.isCodeNotTaken(routeDTO.getCode())){
            Station origin = this.stations.ReadByID(routeDTO.getOrigin());
            Station destination = this.stations.ReadByID(routeDTO.getDestination());
            if(origin != null && destination != null){
                Route route = converter.DTOtoEntity(routeDTO);
                route.setOrigin(origin);
                route.setDestination(destination);
                return routes.Create(route);
            }
            return false;
        }
        return false;
    }
    @Override
    public boolean Update(RouteRequestDTO routeDTO) {
         Route route = converter.DTOtoEntity(routeDTO);
         return routes.Update(route);
    }
    @Override
    public boolean Remove(UUID ID) {
        return routes.Delete(ID);
    }

    private boolean isCodeNotTaken(String Code){
        return routes.ReadByCode(Code) == null;
    }
}
