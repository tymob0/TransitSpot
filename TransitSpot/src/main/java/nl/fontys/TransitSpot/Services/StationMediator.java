package nl.fontys.TransitSpot.Services;

import nl.fontys.TransitSpot.Collections.StationEntityCollection;
import nl.fontys.TransitSpot.Converters.StationsConverter;
import nl.fontys.TransitSpot.DTO.Station.StationDTO;
import nl.fontys.TransitSpot.DTO.Station.StationFullDTO;
import nl.fontys.TransitSpot.Entity.Station;
import nl.fontys.TransitSpot.Collections.StationCollection;
import nl.fontys.TransitSpot.Storages.StationStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StationMediator implements StationCollection, StationEntityCollection {
    private StationStorage storage;
    private StationsConverter converter;
    @Autowired
    public StationMediator(StationStorage storage, StationsConverter converter) {
        this.storage = storage;
        this.converter = converter;
    }
    @Override
    public List<StationFullDTO> GetAll() {
        return this.converter.EntityToFullDTO(this.storage.ReadAll());
    }

    @Override
    public List<StationFullDTO> GetAll(Integer page) {
        Page<Station> pageResult = this.storage.ReadAllPage(page);
        List<Station> list = pageResult.getContent();
        return this.converter.EntityToFullDTO(list);
    }

    @Override
    public StationFullDTO GetByID(UUID ID) {
        return this.converter.EntityToFullDTO(this.storage.ReadByID(ID));
    }

    @Override
    public Long GetCount() {
        return storage.GetCount();
    }

    @Override
    public boolean Add(StationFullDTO station) {
        if(this.isCodeNotTaken(station.getCode())){
            Station temp = this.converter.FullDTOtoEntity(station);
            return this.storage.Create(temp);
        }
        return false;
    }
    @Override
    public boolean Update(StationFullDTO station) {
      Station temp = this.converter.FullDTOtoEntity(station);
       return this.storage.Update(temp);
    }
    @Override
    public boolean Remove(UUID ID) {
        return this.storage.Delete(ID);
    }

    @Override
    public List<Station> GetAllStations() {
        return this.storage.ReadAll();
    }

    @Override
    public Station GetStationByID(UUID ID) {
        return this.storage.ReadByID(ID);
    }

    private Boolean isCodeNotTaken(String code){
        return storage.ReadByCode(code) == null;
    }
}
