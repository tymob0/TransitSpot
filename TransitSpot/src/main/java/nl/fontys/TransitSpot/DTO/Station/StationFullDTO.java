package nl.fontys.TransitSpot.DTO.Station;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import java.util.UUID;

public class StationFullDTO {
    private @Getter @Setter UUID ID;
    @Pattern(regexp = "^[a-zA-Z ,.'-]+$")
    private @Getter @Setter String name;
    @Pattern(regexp = "^[0-9a-zA-Z ,.'-]+$")
    private @Getter @Setter String code;
    @Pattern(regexp = "^[a-zA-Z ,.'-]+$")
    private @Getter @Setter String city;
    public StationFullDTO(UUID ID, String name, String code, String city) {
        this.ID = ID;
        this.name = name;
        this.code = code;
        this.city = city;
    }
    public StationFullDTO(){}
}
