package nl.fontys.TransitSpot.Storages;

import nl.fontys.TransitSpot.Entity.Route;
import nl.fontys.TransitSpot.Entity.Station;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface RouteStorage {
    List<Route> ReadAll();
    Page<Route> ReadAllPage(Integer page);
    Long GetCount();
    Route ReadByID(UUID ID);
    Route ReadByCode(String Code);
    boolean Create(Route routeM);
    boolean Update(Route routeM);
    boolean Delete(UUID ID);
}
