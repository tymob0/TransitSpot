package nl.fontys.TransitSpot;

import nl.fontys.TransitSpot.Collections.StationCollection;
import nl.fontys.TransitSpot.Converters.StationsConverter;
import nl.fontys.TransitSpot.Converters.StationsMapper;
import nl.fontys.TransitSpot.DTO.Station.StationDTO;
import nl.fontys.TransitSpot.DTO.Station.StationFullDTO;
import nl.fontys.TransitSpot.Entity.Station;
import nl.fontys.TransitSpot.Services.StationMediator;
import nl.fontys.TransitSpot.Storages.StationStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
public class StationServiceMockTest {
    @Mock
    StationStorage stationDAL;
    Station A = new Station("A");
    Station B = new Station("B");
    UUID id1 = UUID.randomUUID();
    UUID id2 = UUID.randomUUID();
    @BeforeEach
    public void setUp()  {
        A.setID(id1);
        B.setID(id2);
        List<Station> stations = List.of(A, B);
        when(stationDAL.ReadAll()).thenReturn(stations);
    }
    @Test
    public void GetAllStationsReturnsListOfStationsDTOTest()
    {
        // arrange
        StationCollection service = new StationMediator(stationDAL, new StationsMapper());
        // act
        List<StationFullDTO> stations = service.GetAll();
        // assert
        Assertions.assertEquals(stations.get(0).getName(),"A");
        Assertions.assertEquals(stations.get(1).getName(),"B");
    }
    @Test
    public void AddStationReturnsTrueTest()
    {
        // arrange
        StationCollection service = new StationMediator(stationDAL, new StationsMapper());
        // act
        UUID id = UUID.randomUUID();
        StationFullDTO station = new StationFullDTO(id,"C", "C", "C");
        service.Add(station);
        //assert
        ArgumentCaptor<Station> StationArgumentCaptor =
                ArgumentCaptor.forClass(Station.class);
        verify(stationDAL).Create(StationArgumentCaptor.capture());
        Station finalRes = StationArgumentCaptor.getValue();
        Assertions.assertEquals(finalRes.getID(),station.getID());
        Assertions.assertEquals(finalRes.getName(),station.getName());
    }
    @Test
    public void UpdateStationReturnsTrueTest()
    {
        // arrange
        StationCollection service = new StationMediator(stationDAL, new StationsMapper());
        // act
        UUID id = UUID.randomUUID();
        StationFullDTO station = new StationFullDTO(id,"C","C", "C");
        service.Add(station);
        station.setName("C1");
        service.Update(station);
        //assert
        ArgumentCaptor<Station> StationArgumentCaptor =
                ArgumentCaptor.forClass(Station.class);
        verify(stationDAL).Update(StationArgumentCaptor.capture());
        Station finalRes = StationArgumentCaptor.getValue();
        Assertions.assertEquals(finalRes.getID(),station.getID());
        Assertions.assertEquals(finalRes.getName(),station.getName());
    }
    @Test
    public void GetStationsByIDReturnsStationDTOTest()
    {
        //arrange
        when(stationDAL.ReadByID(id1)).thenReturn(A);
        //act
        Station stationDTO = stationDAL.ReadByID(id1);
        //assert
        Assertions.assertEquals(A.getID(), stationDTO.getID());
        Assertions.assertEquals(A.getName(), stationDTO.getName());
    }
    @Test
    public void DeleteByIDReturnsStationDTOTest()
    {
        //arrange
        //act
        stationDAL.Delete(id1);
        //assert
        verify(stationDAL).Delete(id1);
    }
}
