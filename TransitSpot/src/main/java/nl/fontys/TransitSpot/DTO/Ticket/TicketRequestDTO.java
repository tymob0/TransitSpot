package nl.fontys.TransitSpot.DTO.Ticket;

import lombok.Getter;
import lombok.Setter;
import nl.fontys.TransitSpot.DTO.Station.StationFullDTO;
import nl.fontys.TransitSpot.DTO.UserDTO.UserDTO;
import nl.fontys.TransitSpot.Entity.AppUser;
import nl.fontys.TransitSpot.Entity.Station;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class TicketRequestDTO {
    private @Getter @Setter UUID ID;
    private @Getter @Setter UUID origin;
    private @Getter @Setter UUID destination;
    private @Getter @Setter UUID traveler;
    @FutureOrPresent
    private @Getter @Setter LocalDate tripDateTime;
    private @Getter @Setter LocalDateTime purchaseDateTime;
    @Pattern(regexp = "^[a-zA-Z ,.'-]+$")
    private @Getter @Setter String firstName;
    @Pattern(regexp = "^[a-zA-Z ,.'-]+$")
    private @Getter @Setter String lastName;
    @Pattern(regexp = "^[0-9a-zA-Z ,.'-]+$")
    private @Getter @Setter String travelDocumentNr;
    public TicketRequestDTO(UUID ID, LocalDate tripDateTime, String firstName, String lastName, String travelDocumentNr) {
        this.ID = ID;
        this.tripDateTime = tripDateTime;
        this.purchaseDateTime = LocalDateTime.now();
        this.firstName = firstName;
        this.lastName = lastName;
        this.travelDocumentNr = travelDocumentNr;
    }
    public TicketRequestDTO() {}
}
