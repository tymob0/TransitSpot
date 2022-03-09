package nl.fontys.TransitSpot;

import nl.fontys.TransitSpot.Collections.StationCollection;
import nl.fontys.TransitSpot.Collections.StationEntityCollection;
import nl.fontys.TransitSpot.Converters.RoutesMapper;
import nl.fontys.TransitSpot.Converters.StationsMapper;
import nl.fontys.TransitSpot.DTO.Path.PathDTO;
import nl.fontys.TransitSpot.DTO.Station.StationDTO;
import nl.fontys.TransitSpot.Entity.Route;
import nl.fontys.TransitSpot.Entity.Station;
import nl.fontys.TransitSpot.SearchService.PathSearchEngine;
import nl.fontys.TransitSpot.SearchService.RouteStationsManager;
import nl.fontys.TransitSpot.Services.PathMediator;
import nl.fontys.TransitSpot.Services.RouteMediator;
import nl.fontys.TransitSpot.Services.StationMediator;
import nl.fontys.TransitSpot.Storages.RouteStorage;
import nl.fontys.TransitSpot.Storages.StationStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
public class RoutePlannerMockTest {
    @Mock
    StationStorage stationDAL;
    @Mock
    RouteStorage routeDAL;

    Station EIN  = new Station("Eindhoven");
    Station UTR = new Station("Utrecht");
    Station AMS = new Station("Amstredam");
    Route EinUtr50 = new Route("Ein-Utr", 50, "EinUtr50", 25.0);
    Route EinUtr100 = new Route("Ein-Utr", 100, "EinUtr100", 30.0);
    Route UtrAms30 = new Route("Utr-Ams", 30, "UtrAms30", 15.0);
    UUID idEin = UUID.randomUUID();
    UUID idUtr = UUID.randomUUID();
    UUID idAms = UUID.randomUUID();
    UUID idEinUtr50 = UUID.randomUUID();
    UUID idEinUtr100 = UUID.randomUUID();
    UUID idUtrAms30 = UUID.randomUUID();

    @BeforeEach
    public void setUp()  {
        EIN.setID(idEin);
        UTR.setID(idUtr);
        AMS.setID(idAms);

        EinUtr50.setID(idEinUtr50);
        EinUtr100.setID(idEinUtr100);
        UtrAms30.setID(idUtrAms30);

        EinUtr50.setOrigin(EIN);
        EinUtr50.setDestination(UTR);
        EinUtr100.setOrigin(EIN);
        EinUtr100.setDestination(UTR);
        UtrAms30.setOrigin(UTR);
        UtrAms30.setDestination(AMS);

        List<Station> stations = List.of(EIN, UTR, AMS);
        List<Route> routes = List.of(EinUtr50, EinUtr100, UtrAms30);

        when(stationDAL.ReadAll()).thenReturn(stations);
        when(routeDAL.ReadAll()).thenReturn(routes);

        when(stationDAL.ReadByID(idEin)).thenReturn(EIN);
        when(stationDAL.ReadByID(idUtr)).thenReturn(UTR);
        when(stationDAL.ReadByID(idAms)).thenReturn(AMS);

        when(routeDAL.ReadByID(idUtrAms30)).thenReturn(UtrAms30);
        when(routeDAL.ReadByID(idEinUtr50)).thenReturn(EinUtr50);
        when(routeDAL.ReadByID(idEinUtr100)).thenReturn(EinUtr100);
    }

    @Test
    public void GetPathFromAToBReturnsPath()
    {
        // arrange
        RouteStationsManager routeManager = new RouteMediator(this.routeDAL, this.stationDAL, new RoutesMapper());
        StationEntityCollection stationsManager = new StationMediator(this.stationDAL, new StationsMapper());

        PathMediator pathFinder = new PathMediator(routeManager, stationsManager);
        // act
        PathDTO path = pathFinder.SearchForRoute(idEin, idAms);
        // assert
        Assertions.assertEquals(path.getTime(), 80);
        Assertions.assertEquals(path.getRoutes().get(0).getID(),idEinUtr50);
        Assertions.assertEquals(path.getRoutes().get(1).getID(),idUtrAms30);
    }

    @Test
    public void InitializeHashMapsReturnsVoid()
    {
        // arrange
        RouteStationsManager routeManager = new RouteMediator(this.routeDAL, this.stationDAL, new RoutesMapper());
        StationEntityCollection stationsManager = new StationMediator(this.stationDAL, new StationsMapper());

        PathMediator pathFinder = new PathMediator(routeManager, stationsManager);
        // act
        pathFinder.initFields();
        //assert
        Assertions.assertEquals(pathFinder.stationValueMap!=null, true);
        Assertions.assertEquals(pathFinder.shortestPathFrom!=null, true);
    }

    @Test
    public void GetStationByDurationFromHashMapReturnsStation()
    {
        // arrange
        RouteStationsManager routeManager = new RouteMediator(this.routeDAL, this.stationDAL, new RoutesMapper());
        StationEntityCollection stationsManager = new StationMediator(this.stationDAL, new StationsMapper());

        PathMediator pathFinder = new PathMediator(routeManager, stationsManager);
        // act
        pathFinder.initFields();
        pathFinder.stationValueMap.put(EIN, 90);
        Station result = pathFinder.getStationByDuration(pathFinder.stationValueMap, 90);
        //assert
        Assertions.assertEquals(result, EIN);
    }

    @Test
    public void GetSmallestNotVisitedNodeReturnsStation()
    {
        // arrange
        RouteStationsManager routeManager = new RouteMediator(this.routeDAL, this.stationDAL, new RoutesMapper());
        StationEntityCollection stationsManager = new StationMediator(this.stationDAL, new StationsMapper());

        PathMediator pathFinder = new PathMediator(routeManager, stationsManager);
        // act
        pathFinder.initFields();
        pathFinder.stationValueMap.put(EIN, 90);
        pathFinder.stationValueMap.put(UTR, 50);
        pathFinder.stationValueMap.put(AMS, 20);
        pathFinder.visited.add(AMS);
        Station result = pathFinder.getSmallestNotVisitedNode();
        //assert
        Assertions.assertEquals(result, UTR);
    }

    @Test
    public void BacktrackPathFromHashMapReturnsListStations()
    {
        // arrange
        RouteStationsManager routeManager = new RouteMediator(this.routeDAL, this.stationDAL, new RoutesMapper());
        StationEntityCollection stationsManager = new StationMediator(this.stationDAL, new StationsMapper());

        PathMediator pathFinder = new PathMediator(routeManager, stationsManager);
        // act
        pathFinder.initFields();
        pathFinder.shortestPathFrom.put(EIN,EIN);
        pathFinder.shortestPathFrom.put(UTR,EIN);
        pathFinder.shortestPathFrom.put(AMS,UTR);
        List<Station> result = pathFinder.backtrackPath(EIN, AMS);
        //assert
        Assertions.assertEquals(result.get(2), EIN);
        Assertions.assertEquals(result.get(1), UTR);
        Assertions.assertEquals(result.get(0), AMS);
    }
}
