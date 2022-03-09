package nl.fontys.TransitSpot.DTO.Station;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import java.util.UUID;

public class StationDTO {
    private @Getter @Setter UUID ID;
    private @Getter @Setter String name;
    public StationDTO(UUID ID, String name) {
        this.ID = ID;
        this.name = name;
    }
    public StationDTO(){}
}
