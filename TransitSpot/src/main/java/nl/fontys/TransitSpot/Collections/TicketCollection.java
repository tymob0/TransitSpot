package nl.fontys.TransitSpot.Collections;

import nl.fontys.TransitSpot.DTO.Ticket.TicketRequestDTO;
import nl.fontys.TransitSpot.DTO.Ticket.TicketResponseDTO;

import java.util.List;
import java.util.UUID;

public interface TicketCollection {
    List<TicketResponseDTO> GetAll();
    TicketResponseDTO GetByID(UUID ID);
    UUID Add(TicketRequestDTO ticket);
    List<TicketResponseDTO> GetByUserID(UUID id, Integer page);
    Long GetCount();
}
