package nl.fontys.TransitSpot.Repositories.JPA;

import nl.fontys.TransitSpot.DataJPA.RouteDataJPA;
import nl.fontys.TransitSpot.Entity.Route;
import nl.fontys.TransitSpot.Entity.Station;
import nl.fontys.TransitSpot.Storages.RouteStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class RouteStorageJPA implements RouteStorage {
    @Autowired
    RouteDataJPA repo;
    @Override
    public List<Route> ReadAll() {
        return repo.findAll(Sort.by("name").ascending());
    }

    @Override
    public Page<Route> ReadAllPage(Integer page) {
        return repo.findAll(PageRequest.of(page,5));
    }

    @Override
    public Long GetCount() {
        return repo.countByIDIsNotNull();
    }

    @Override
    public Route ReadByID(UUID ID) {
        return repo.findByID(ID);
    }

    @Override
    public Route ReadByCode(String Code) {
        return repo.findByCode(Code);
    }

    @Override
    public boolean Create(Route routeM) {
        repo.save(routeM);
        return true;
    }
    @Override
    public boolean Update(Route routeM) {
        Route temp = this.ReadByID(routeM.getID());
        temp.setName(routeM.getName());
        temp.setDuration(routeM.getDuration());
        temp.setOrigin(routeM.getOrigin());
        temp.setDestination(routeM.getDestination());
        temp.setCode(routeM.getCode());
        temp.setPrice(routeM.getPrice());
        repo.save(temp);
        return true;
    }
    @Override
    public boolean Delete(UUID ID) {
        repo.deleteById(ID);
        return true;
    }
}
