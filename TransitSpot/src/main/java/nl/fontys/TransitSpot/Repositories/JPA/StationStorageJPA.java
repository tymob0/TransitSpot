package nl.fontys.TransitSpot.Repositories.JPA;

import nl.fontys.TransitSpot.DataJPA.StationDataJPA;
import nl.fontys.TransitSpot.Entity.Station;
import nl.fontys.TransitSpot.Storages.StationStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class StationStorageJPA implements StationStorage {
    @Autowired
    StationDataJPA repo;
    @Override
    public List<Station> ReadAll() {
        return repo.findAll(Sort.by("name").ascending());
    }
    @Override
    public Page<Station> ReadAllPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("name").ascending());
        return repo.findAll(pageable);
    }
    @Override
    public Station ReadByID(UUID ID) {
        return repo.findByID(ID);
    }

    @Override
    public Station ReadByCode(String Code) {
        return repo.findByCode(Code);
    }

    @Override
    public Long GetCount() {
        return repo.countByIDIsNotNull();
    }

    @Override
    public boolean Create(Station station) {
        repo.save(station);
        return true;
    }
    @Override
    public boolean Update(Station station) {
        Station temp = this.ReadByID(station.getID());
        temp.setName(station.getName());
        temp.setCode(station.getCode());
        temp.setCity(station.getCity());
        repo.save(temp);
        return true;
    }
    @Override
    public boolean Delete(UUID ID) {
        repo.deleteById(ID);
        return true;
    }
}
