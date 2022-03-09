package nl.fontys.TransitSpot.Converters;

import nl.fontys.TransitSpot.DTO.Station.StationDTO;
import nl.fontys.TransitSpot.DTO.Station.StationFullDTO;
import nl.fontys.TransitSpot.Entity.Station;

import java.util.List;

public interface StationsConverter {
    StationDTO EntityToDTO(Station station);

    Station DTOtoEntity(StationDTO stationDTO);

    List<StationDTO> EntityToDTO(List<Station> stations);

    List<Station> DTOtoEntity(List<StationDTO> stations);

    StationFullDTO EntityToFullDTO(Station station);

    Station FullDTOtoEntity(StationFullDTO stationDTO);

    List<StationFullDTO> EntityToFullDTO(List<Station> stations);

    List<Station> FullDTOtoEntity(List<StationFullDTO> stations);

}
