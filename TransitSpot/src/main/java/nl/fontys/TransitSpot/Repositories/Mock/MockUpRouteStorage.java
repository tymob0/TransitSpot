package nl.fontys.TransitSpot.Repositories.Mock;

import nl.fontys.TransitSpot.Entity.Route;
import nl.fontys.TransitSpot.Storages.RouteStorage;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

//@Repository
public class MockUpRouteStorage implements RouteStorage {
    private List<Route> routeMS;
    public MockUpRouteStorage() {
//        this.routeMS = new ArrayList<>();
//        this.Create(new RouteModel("1","Eindhoven-Utrecht",50, new Station("1","Eindhoven"), new Station("2","Utrecht")));
//        this.Create(new RouteModel("13","Eindhoven-Utrecht",1000, new Station("1","Eindhoven"), new Station("2","Utrecht")));
//        this.Create(new RouteModel("2","Utrecht-Eindhoven",50, new Station("2","Utrecht"), new Station("1","Eindhoven")));
//        this.Create(new RouteModel("3","Eindhoven-Den Bosch",20, new Station("1","Eindhoven"), new Station("3","Den Bosch")));
//        this.Create(new RouteModel("4","Den Bosch-Eindhoven",20, new Station("3","Den Bosch"), new Station("1","Eindhoven")));
//        this.Create(new RouteModel("5","Den Bosch-Rotterdam",40, new Station("3","Den Bosch"), new Station("4","Rotterdam")));
//        this.Create(new RouteModel("6","Rotterdam-Den Bosch",40, new Station("4","Rotterdam"), new Station("3","Den Bosch")));
//        this.Create(new RouteModel("7","Rotterdam-Utrecht",25, new Station("4","Rotterdam"), new Station("2","Utrecht")));
//        this.Create(new RouteModel("8","Utrecht-Rotterdam",25, new Station("2","Utrecht"), new Station("4","Rotterdam")));
//        this.Create(new RouteModel("9","Rotterdam-Groningen",90, new Station("4","Rotterdam"), new Station("5","Groningen")));
//        this.Create(new RouteModel("10","Groningen-Rotterdam",90, new Station("5","Groningen"), new Station("4","Rotterdam")));
//        this.Create(new RouteModel("11","Utrecht-Groningen",70, new Station("2","Utrecht"), new Station("5","Groningen")));
//        this.Create(new RouteModel("12","Groningen-Utrecht",70,new Station("5","Groningen"), new Station("2","Utrecht")));
    }
    @Override
    public List<Route> ReadAll() {
        return this.routeMS;
    }

    @Override
    public Page<Route> ReadAllPage(Integer page) {
        return null;
    }

    @Override
    public Long GetCount() {
        return null;
    }

    @Override
    public Route ReadByID(UUID ID) {
        for (Route routeM : this.routeMS) {
            if(routeM.getID().equals(ID))
                return routeM;
        }
        return null;
    }

    @Override
    public Route ReadByCode(String Code) {
        return null;
    }

    @Override
    public boolean Create(Route routeM) {
        if(this.ReadByID(routeM.getID())==null){
            return this.routeMS.add(routeM);
        }
        else return false;
    }
    @Override
    public boolean Update(Route routeM) {
        Route old = ReadByID(routeM.getID());
        if(old!=null)
            return this.routeMS.set(this.routeMS.indexOf(old), routeM) != null;
        return false;
    }
    @Override
    public boolean Delete(UUID ID) {
        Route old = this.ReadByID(ID);
        if(old!=null){
            return this.routeMS.remove(old);
        }
        else return false;
    }
}
