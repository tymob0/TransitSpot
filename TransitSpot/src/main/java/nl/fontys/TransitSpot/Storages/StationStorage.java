package nl.fontys.TransitSpot.Storages;

import nl.fontys.TransitSpot.Entity.Station;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface StationStorage {
    List<Station> ReadAll();
    Page<Station> ReadAllPage(Integer page);
    Station ReadByID(UUID ID);
    Station ReadByCode(String Code);
    Long GetCount();
    boolean Create(Station station);
    boolean Update(Station station);
    boolean Delete(UUID ID);
}
