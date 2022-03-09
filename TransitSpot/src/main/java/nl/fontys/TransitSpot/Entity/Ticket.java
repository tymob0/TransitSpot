package nl.fontys.TransitSpot.Entity;
import lombok.Getter;
import lombok.Setter;
import nl.fontys.TransitSpot.DTO.Path.PathDTO;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name ="ticket")
public class Ticket {
  @Id
  @Column(name = "Ticket_ID")
  @Type(type="org.hibernate.type.UUIDCharType")
  private @Getter @Setter UUID ID;
  @ManyToOne
  private @Getter @Setter Station origin;
  @ManyToOne
  private @Getter @Setter Station destination;
  @ManyToOne
  private @Getter @Setter AppUser traveler;
  private @Getter @Setter LocalDate tripDateTime;
  private @Getter @Setter LocalDateTime purchaseDateTime;
  private @Getter @Setter String firstName;
  private @Getter @Setter String lastName;
  private @Getter @Setter String travelDocumentNr;
  public Ticket(AppUser traveler, Station origin, Station destination, LocalDate tripDateTime, LocalDateTime purchaseDateTime,
                String firstName, String lastName, String travelDocumentNr) {
    this.ID = UUID.randomUUID();
    this.origin = origin;
    this.destination = destination;
    this.traveler = traveler;
    this.tripDateTime = tripDateTime;
    this.purchaseDateTime = purchaseDateTime;
    this.firstName = firstName;
    this.lastName = lastName;
    this.travelDocumentNr = travelDocumentNr;
  }
  public Ticket() {
  }
}
