package nl.fontys.TransitSpot.DTO.Ticket;

import lombok.Getter;
import lombok.Setter;
import nl.fontys.TransitSpot.DTO.Station.StationDTO;
import nl.fontys.TransitSpot.DTO.Station.StationFullDTO;
import nl.fontys.TransitSpot.DTO.UserDTO.UserDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class TicketResponseDTO {
    private @Getter @Setter UUID ID;
    private @Getter @Setter String origin;
    private @Getter @Setter String destination;
    private @Getter @Setter LocalDate tripDateTime;
    private @Getter @Setter LocalDateTime purchaseDateTime;
    private @Getter @Setter String firstName;
    private @Getter @Setter String lastName;
    private @Getter @Setter String travelDocumentNr;
    public TicketResponseDTO(UUID ID, LocalDate tripDateTime, LocalDateTime purchaseDateTime,
            String firstName, String lastName, String travelDocumentNr) {
        this.ID = ID;
        this.tripDateTime = tripDateTime;
        this.purchaseDateTime = purchaseDateTime;
        this.firstName = firstName;
        this.lastName = lastName;
        this.travelDocumentNr = travelDocumentNr;
    }
    public TicketResponseDTO() {}
}