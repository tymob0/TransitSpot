package nl.fontys.TransitSpot.Converters;

import nl.fontys.TransitSpot.DTO.Station.StationDTO;
import nl.fontys.TransitSpot.DTO.Station.StationFullDTO;
import nl.fontys.TransitSpot.Entity.Station;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StationsMapper implements StationsConverter {
    @Override
    public StationDTO EntityToDTO(Station station){
        ModelMapper mapper = new ModelMapper();
        StationDTO map = mapper.map(station, StationDTO.class);
        return map;
    }
    @Override
    public Station DTOtoEntity(StationDTO stationDTO){
        ModelMapper mapper = new ModelMapper();
        Station map = mapper.map(stationDTO, Station.class);
        return map;
    }
    @Override
    public List<StationDTO> EntityToDTO(List<Station> stations){
        return stations.stream().map(x->EntityToDTO(x)).collect(Collectors.toList());
    }
    @Override
    public List<Station> DTOtoEntity(List<StationDTO> stations){
        return stations.stream().map(x->DTOtoEntity(x)).collect(Collectors.toList());
    }

    @Override
    public StationFullDTO EntityToFullDTO(Station station) {
        ModelMapper mapper = new ModelMapper();
        StationFullDTO map = mapper.map(station, StationFullDTO.class);
        return map;
    }

    @Override
    public Station FullDTOtoEntity(StationFullDTO stationDTO) {
        ModelMapper mapper = new ModelMapper();
        Station map = mapper.map(stationDTO, Station.class);
        return map;
    }

    @Override
    public List<StationFullDTO> EntityToFullDTO(List<Station> stations) {
        return stations.stream().map(x->EntityToFullDTO(x)).collect(Collectors.toList());
    }

    @Override
    public List<Station> FullDTOtoEntity(List<StationFullDTO> stations) {
        return stations.stream().map(x->FullDTOtoEntity(x)).collect(Collectors.toList());
    }
}
