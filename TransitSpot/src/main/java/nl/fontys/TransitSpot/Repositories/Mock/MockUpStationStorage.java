package nl.fontys.TransitSpot.Repositories.Mock;

import nl.fontys.TransitSpot.Entity.Station;
import nl.fontys.TransitSpot.Storages.StationStorage;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@Repository
public class MockUpStationStorage implements StationStorage {
    private List<Station> stations;
    public MockUpStationStorage(){
        stations = new ArrayList<>();
        stations.add(new Station("Eindhoven"));
        stations.add(new Station( "Utrecht"));
        stations.add(new Station("Den Bosch"));
        stations.add(new Station("Rotterdam"));
        stations.add(new Station("Groningen"));
    }
    @Override
    public List<Station> ReadAll() {
        return this.stations;
    }

    @Override
    public Page<Station> ReadAllPage(Integer page) {
        return null;
    }

    @Override
    public Station ReadByID(UUID ID) {
        for (Station station: this.stations) {
            if(station.getID().equals(ID))
                return station;
        }
        return null;
    }

    @Override
    public Station ReadByCode(String Code) {
        return null;
    }

    @Override
    public Long GetCount() {
        return null;
    }

    @Override
    public boolean Create(Station station){
        if(this.ReadByID(station.getID())==null){
            return this.stations.add(station);
        }
        else return false;
    }
    @Override
    public boolean Update(Station station) {
        Station old = ReadByID(station.getID());
        if(old!=null)
            return this.stations.set(this.stations.indexOf(old), station) != null;
        return false;
    }
    @Override
    public boolean Delete(UUID ID) {
        Station old = this.ReadByID(ID);
        if(old!=null)
            return this.stations.remove(old);
        else return false;
    }
}
