package nl.fontys.TransitSpot.DTO.Route;

import lombok.Getter;
import lombok.Setter;
import nl.fontys.TransitSpot.Entity.Station;

import java.util.UUID;

public class RouteResponseDTO {
    protected @Getter @Setter UUID ID;
    protected @Getter @Setter String Name;
    protected @Getter @Setter int Duration;
    private @Getter @Setter Station Origin;
    private @Getter @Setter Station Destination;
    private @Getter @Setter String Code;
    private @Getter @Setter Double Price;
    public RouteResponseDTO( String name, int duration, String Code,    Double Price) {
        this.Name = name;
        this.Duration = duration;
        this.Code = Code;
        this.Price = Price;
    }
    public RouteResponseDTO(){};
}
