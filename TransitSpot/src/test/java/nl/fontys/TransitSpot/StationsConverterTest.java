package nl.fontys.TransitSpot;

import nl.fontys.TransitSpot.Collections.StationCollection;
import nl.fontys.TransitSpot.Converters.StationsConverter;
import nl.fontys.TransitSpot.Converters.StationsMapper;
import nl.fontys.TransitSpot.DTO.Station.StationDTO;
import nl.fontys.TransitSpot.DTO.Station.StationFullDTO;
import nl.fontys.TransitSpot.Entity.Station;
import nl.fontys.TransitSpot.Services.StationMediator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest
public class StationsConverterTest {

    @Test
    public void ConvertStationDTOReturnsStationTest()
    {
        // arrange
        StationsConverter converter = new StationsMapper();
        UUID id = UUID.randomUUID();
        String name = "station";
        StationDTO stationDTO = new StationDTO(id, name);
        // act
        Station station = converter.DTOtoEntity(stationDTO);
        // assert
        Assertions.assertEquals(station.getID(),id);
        Assertions.assertEquals(station.getName(),name);
    }

    @Test
    public void ConvertStationReturnsStationDTOTest()
    {
        // arrange
        StationsConverter converter = new StationsMapper();
        String name = "station";
        Station station = new Station(name);
        // act
        StationDTO stationDTO = converter.EntityToDTO(station);
        // assert
        Assertions.assertEquals(stationDTO.getName(),name);
    }

    @Test
    public void ConvertStationFullDTOReturnsStationTest()
    {
        // arrange
        StationsConverter converter = new StationsMapper();
        UUID id = UUID.randomUUID();
        String name = "station";
        String code = "code";
        String city = "city";
        StationFullDTO stationDTO = new StationFullDTO(id, name, code, city);
        // act
        Station station = converter.FullDTOtoEntity(stationDTO);
        // assert
        Assertions.assertEquals(station.getID(),id);
        Assertions.assertEquals(station.getName(),name);
        Assertions.assertEquals(station.getCode(),code);
        Assertions.assertEquals(station.getCity(),city);
    }

    @Test
    public void ConvertStationReturnsStationFullDTOTest()
    {
        // arrange
        StationsConverter converter = new StationsMapper();
        String name = "station";
        Station station = new Station(name);
        // act
        StationFullDTO stationDTO = converter.EntityToFullDTO(station);
        // assert
        Assertions.assertEquals(stationDTO.getName(),name);
    }
}
