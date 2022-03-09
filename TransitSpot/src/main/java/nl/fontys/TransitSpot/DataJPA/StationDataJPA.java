package nl.fontys.TransitSpot.DataJPA;

import nl.fontys.TransitSpot.Entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;


public interface StationDataJPA extends JpaRepository<Station, UUID> {
    Station findByID(UUID ID);
    Station findByCode(String Code);
    Long countByIDIsNotNull();
    @Query(
            value = "SELECT * FROM Station ORDER BY Name ASC",
            nativeQuery = true)
    List<Station> find();
}
