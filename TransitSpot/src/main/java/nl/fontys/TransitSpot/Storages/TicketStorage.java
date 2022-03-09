package nl.fontys.TransitSpot.Storages;

import nl.fontys.TransitSpot.Entity.Route;
import nl.fontys.TransitSpot.Entity.Ticket;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface TicketStorage {
    List<Ticket> ReadAll();
    Ticket ReadByID(UUID ID);
    boolean Create(Ticket ticket);
    Page<Ticket> ReadTicketByUserID(UUID id, Integer Page);
    Long GetCount();
}
