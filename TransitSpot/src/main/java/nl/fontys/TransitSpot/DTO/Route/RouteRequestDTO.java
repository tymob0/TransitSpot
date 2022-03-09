package nl.fontys.TransitSpot.DTO.Route;

import lombok.Getter;
import lombok.Setter;
import nl.fontys.TransitSpot.Entity.Station;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.UUID;

public class RouteRequestDTO {
    protected @Getter @Setter UUID ID;
    @Pattern(regexp = "^[a-zA-Z ,.'-]+$")
    protected @Getter @Setter String Name;
    @Min(1)
    @Max(10000)
    protected @Getter @Setter int Duration;
    private @Getter @Setter UUID Origin;
    private @Getter @Setter UUID Destination;
    @Pattern(regexp = "^[0-9a-zA-Z ,.'-]+$")
    private @Getter @Setter String Code;
    @Min(1)
    @Max(500)
    private @Getter @Setter Double Price;
    public RouteRequestDTO( String name, int duration, String Code, Double Price) {
        this.Name = name;
        this.Duration = duration;
        this.Code = Code;
        this.Price = Price;
    }
    public RouteRequestDTO(){};
}
