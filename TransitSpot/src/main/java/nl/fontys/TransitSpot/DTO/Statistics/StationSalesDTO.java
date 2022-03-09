package nl.fontys.TransitSpot.DTO.Statistics;

import lombok.Getter;
import lombok.Setter;

public class StationSalesDTO {
    @Getter @Setter
    private String station;
    @Getter @Setter
    private String total;

    public StationSalesDTO(String station, String total) {
        this.station = station;
        this.total = total;
    }
    public StationSalesDTO() {}
}
