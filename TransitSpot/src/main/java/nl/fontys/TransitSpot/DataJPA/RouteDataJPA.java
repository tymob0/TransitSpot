package nl.fontys.TransitSpot.DataJPA;

import nl.fontys.TransitSpot.Entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RouteDataJPA extends JpaRepository<Route, UUID> {
    Route findByID(UUID ID);
    Route findByCode(String Code);
    Long countByIDIsNotNull();
    @Query(
            value = "SELECT * FROM Route ORDER BY Name ASC",
            nativeQuery = true)
    List<Route> find();
}
