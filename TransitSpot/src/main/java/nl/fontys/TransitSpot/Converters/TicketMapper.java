package nl.fontys.TransitSpot.Converters;

import nl.fontys.TransitSpot.DTO.Ticket.TicketRequestDTO;
import nl.fontys.TransitSpot.DTO.Ticket.TicketResponseDTO;
import nl.fontys.TransitSpot.Entity.Ticket;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TicketMapper implements TicketConverter {
    StationsConverter stationsMapper = new StationsMapper();
    UsersConverter usersMapper = new UsersMapper();
    @Override
    public TicketResponseDTO EntityToDTO(Ticket ticket) {
        TicketResponseDTO dto =  new TicketResponseDTO();
        dto.setID(ticket.getID());
        dto.setDestination(ticket.getDestination().getName());
        dto.setOrigin(ticket.getOrigin().getName());
        dto.setTripDateTime(ticket.getTripDateTime());
        dto.setPurchaseDateTime(ticket.getPurchaseDateTime());
        dto.setFirstName(ticket.getFirstName());
        dto.setLastName(ticket.getLastName());
        dto.setTravelDocumentNr(ticket.getTravelDocumentNr());
        return dto;
    }
    @Override
    public Ticket DTOtoEntity(TicketRequestDTO dto) {
        Ticket ticket =  new Ticket();
        ticket.setID(dto.getID());
        ticket.setTripDateTime(dto.getTripDateTime());
        ticket.setFirstName(dto.getFirstName());
        ticket.setLastName(dto.getLastName());
        ticket.setTravelDocumentNr(dto.getTravelDocumentNr());
        return ticket;
    }
    @Override
    public List<TicketResponseDTO> EntityToDTO(List<Ticket> tickets) {
        return tickets.stream().map(x->EntityToDTO(x)).collect(Collectors.toList());
    }
    @Override
    public List<Ticket> DTOtoEntity(List<TicketRequestDTO> tickets) {
        return tickets.stream().map(x->DTOtoEntity(x)).collect(Collectors.toList());
    }
}
