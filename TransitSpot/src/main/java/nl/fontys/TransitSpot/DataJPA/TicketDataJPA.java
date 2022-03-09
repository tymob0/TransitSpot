package nl.fontys.TransitSpot.DataJPA;

import nl.fontys.TransitSpot.Entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.List;
import java.util.UUID;

public interface TicketDataJPA extends JpaRepository<Ticket, UUID> {
    Ticket findByID(UUID ID);
    @Query(value="SELECT * FROM ticket WHERE traveler_user_id =:userID", nativeQuery = true)
    Page<Ticket> findByUserID(@Param("userID") String id, Pageable pageable);
    Long countByIDIsNotNull();
    @Query(value="SELECT station.name as station , COUNT(*) as count FROM ticket INNER JOIN station ON station.station_id = ticket.destination_station_id " +
            "    WHERE WEEKDAY(ticket.trip_date_time) =:week_day GROUP BY ticket.destination_station_id", nativeQuery = true)
    List<Tuple> groupedSalesByMonth(@Param("week_day") int day);


}
