package nl.fontys.TransitSpot;

import nl.fontys.TransitSpot.Converters.RoutesConverter;
import nl.fontys.TransitSpot.Converters.RoutesMapper;
import nl.fontys.TransitSpot.Converters.StationsConverter;
import nl.fontys.TransitSpot.Converters.StationsMapper;
import nl.fontys.TransitSpot.DTO.Route.RouteRequestDTO;
import nl.fontys.TransitSpot.DTO.Route.RouteResponseDTO;
import nl.fontys.TransitSpot.DTO.Station.StationDTO;
import nl.fontys.TransitSpot.Entity.Route;
import nl.fontys.TransitSpot.Entity.Station;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest
public class RoutesConverterTest {

    @Test
    public void ConvertRouteDTOReturnsRouteTest()
    {
        // arrange
        RoutesConverter converter = new RoutesMapper();

        Station origin = new Station("A");
        Station destination = new Station("B");
        String name = "AB";
        int duration = 10;
        double price = 10.0;
        String code = "AB1";

        RouteRequestDTO routeDTO = new RouteRequestDTO(name, duration, code, price);
        // act
        Route route = converter.DTOtoEntity(routeDTO);
        // assert
        Assertions.assertEquals(route.getName(), name);
        Assertions.assertEquals(route.getCode(),code);
        Assertions.assertEquals(route.getDuration(),duration);
        Assertions.assertEquals(route.getPrice(),price);
    }

    @Test
    public void ConvertRouteReturnsRouteDTOTest()
    {
        // arrange
        RoutesConverter converter = new RoutesMapper();

        Station origin = new Station("A");
        Station destination = new Station("B");
        String name = "AB";
        int duration = 10;
        double price = 10.0;
        String code = "AB1";

        Route route = new Route(name, duration, code, price);
        // act
        RouteResponseDTO routeDTO = converter.EntityToDTO(route);
        // assert
        Assertions.assertEquals(routeDTO.getName(), name);
        Assertions.assertEquals(routeDTO.getCode(),code);
        Assertions.assertEquals(routeDTO.getDuration(),duration);
        Assertions.assertEquals(routeDTO.getPrice(),price);
    }
}
