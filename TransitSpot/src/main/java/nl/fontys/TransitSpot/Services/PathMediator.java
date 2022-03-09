package nl.fontys.TransitSpot.Services;

import nl.fontys.TransitSpot.Collections.StationEntityCollection;
import nl.fontys.TransitSpot.DTO.Route.RouteResponseDTO;
import nl.fontys.TransitSpot.DTO.Path.PathDTO;
import nl.fontys.TransitSpot.Entity.Route;
import nl.fontys.TransitSpot.SearchService.PathSearchEngine;
import nl.fontys.TransitSpot.Entity.Station;
import nl.fontys.TransitSpot.SearchService.RouteStationsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PathMediator implements PathSearchEngine {
    public RouteStationsManager routes;
    public StationEntityCollection stations;
    public Station currentPointer = null;
    public HashMap<Station, Integer> stationValueMap;
    public HashMap<Station, Station> shortestPathFrom;
    public List<Station> visited;

    @Autowired
    public PathMediator(RouteStationsManager routes, StationEntityCollection stations) {
        this.routes = routes;
        this.stations = stations;
    }
    public PathDTO SearchForRoute(UUID originID, UUID destinationID) {
        PathDTO pathDTO;

        this.initFields();
        this.initializeStationValueMap(originID);
        this.initializeShortestPathFromMap(originID);

        while (this.currentPointer != null) {
            this.currentPointer = this.getSmallestNotVisitedNode();
            HashMap<Station, Integer> neighbours = this.routes.GetAdjacentStations(currentPointer);
            for (Station neighbour : neighbours.keySet()) {
                int newLength = this.stationValueMap.get(this.currentPointer) + neighbours.get(neighbour);
                if (newLength < this.stationValueMap.get(containsStation(neighbour))) {
                    this.stationValueMap.replace(containsStation(neighbour), newLength);
                    this.shortestPathFrom.replace(containsStation(neighbour), this.currentPointer);
                }
            }
            visited.add(currentPointer);
            if (visited.size() == stationValueMap.size())
                currentPointer = null;
        }
        List<RouteResponseDTO> routeSeq =
                this.GetRouteFromStationSequence(
                    this.backtrackPath(
                        this.stations.GetStationByID(originID),
                            this.stations.GetStationByID(destinationID)));
        String name =  this.stations.GetStationByID(originID).getName() + " - " +  this.stations.GetStationByID(destinationID).getName();
        pathDTO = new PathDTO(name, routeSeq, this.stationValueMap.get(this.stations.GetStationByID(destinationID)),getTotalPriceForPath(routeSeq));
        return pathDTO;
    }
    public void initializeStationValueMap(UUID originID){
        for (Station station : this.stations.GetAllStations()) {
            if (station.getID().equals(originID)) {
                this.stationValueMap.put(station, 0);
                this.currentPointer = station;
            } else
                this.stationValueMap.put(station, Integer.MAX_VALUE);
        }
    }
    public void initializeShortestPathFromMap(UUID originID){
        for (Station station : this.stations.GetAllStations()) {
            if (station.getID().equals(originID))
                this.shortestPathFrom.put(station, station);
              else
                this.shortestPathFrom.put(station, null);
        }
    }
    public void initFields(){
        this.currentPointer = null;
        this.stationValueMap = new HashMap<Station, Integer>();
        this.shortestPathFrom = new HashMap<Station, Station>();
        this.visited = new ArrayList<Station>();
    }
    public Station containsStation(Station station) {
        for (Station s : this.stationValueMap.keySet()) {
            if (s.getID() == station.getID())
                return s;
        }
        return null;
    }
    public Station getStationByDuration(HashMap<Station, Integer> stationValueMap, Integer duration) {
        for (Station s : stationValueMap.keySet()) {
            if (stationValueMap.get(s) == duration)
                return s;
        }
        return null;
    }
    public Station getSmallestNotVisitedNode() {
        HashMap<Station, Integer> temp = new HashMap<Station, Integer>();
        temp.putAll(this.stationValueMap);
        for (Station st : this.visited) {
            if (temp.containsKey(st))
                temp.remove(st);
        }
        return this.getStationByDuration(temp, Collections.min(temp.values()));
    }
    public List<Station> backtrackPath(Station origin, Station destination) {
        List<Station> path = new ArrayList<Station>();
        Station currentPointer = destination;
        while (currentPointer != null) {
            for (Station station : this.shortestPathFrom.keySet()) {
                if (station.getID() == currentPointer.getID()) {
                    path.add(station);
                    currentPointer = shortestPathFrom.get(currentPointer);
                }
            }
            for(Station station : path){
                if(station.getID() == origin.getID())
                    currentPointer = null;
            }
        }
        return path;
    }
    public List<RouteResponseDTO> GetRouteFromStationSequence(List<Station> sequence) {
        Collections.reverse(sequence);
        List<RouteResponseDTO> routeSeq = new ArrayList<RouteResponseDTO>();
        Station previous = null;
        for(Station st : sequence){
            if(previous!=null){
                routeSeq.add(this.routes.GetRouteByEndpoints(previous, st));
            }
            previous = st;
        }
        return routeSeq;
    }

    public Double getTotalPriceForPath(List<RouteResponseDTO> seq){
        Double result = 0.0;
        for(RouteResponseDTO route : seq){
            result +=route.getPrice();
        }
        return result;
    }
}
