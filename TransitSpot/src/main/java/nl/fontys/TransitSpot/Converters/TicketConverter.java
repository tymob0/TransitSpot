package nl.fontys.TransitSpot.Converters;

import nl.fontys.TransitSpot.DTO.Ticket.TicketRequestDTO;
import nl.fontys.TransitSpot.DTO.Ticket.TicketResponseDTO;
import nl.fontys.TransitSpot.Entity.Ticket;

import java.util.List;

public interface TicketConverter {
    TicketResponseDTO EntityToDTO(Ticket ticket);
    Ticket DTOtoEntity(TicketRequestDTO ticketRequestDTO);
    List<TicketResponseDTO> EntityToDTO(List<Ticket> tickets);
    List<Ticket> DTOtoEntity(List<TicketRequestDTO> tickets);
}
