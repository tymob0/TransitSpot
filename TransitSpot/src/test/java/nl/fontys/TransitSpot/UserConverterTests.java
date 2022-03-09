package nl.fontys.TransitSpot;

import nl.fontys.TransitSpot.Converters.StationsConverter;
import nl.fontys.TransitSpot.Converters.StationsMapper;
import nl.fontys.TransitSpot.Converters.UsersConverter;
import nl.fontys.TransitSpot.Converters.UsersMapper;
import nl.fontys.TransitSpot.DTO.Station.StationDTO;
import nl.fontys.TransitSpot.DTO.UserDTO.UserDTO;
import nl.fontys.TransitSpot.Entity.AppUser;
import nl.fontys.TransitSpot.Entity.Station;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest
public class UserConverterTests {

    @Test
    public void ConvertUserDTOReturnsUserTest()
    {
        // arrange
        UsersConverter converter = new UsersMapper();

        UUID id = UUID.randomUUID();
        String name = "name";
        String email = "email";
        String password = "password";
        UserDTO dto = new UserDTO(id, name, email, password);
        // act
        AppUser user = converter.DTOtoEntity(dto);
        // assert
        Assertions.assertEquals(user.getID(),id);
        Assertions.assertEquals(user.getName(),name);
        Assertions.assertEquals(user.getEmail(),email);
        Assertions.assertEquals(user.getPassword(),password);
    }

    @Test
    public void ConvertUserReturnsUserDTOTest()
    {
        // arrange
        UsersConverter converter = new UsersMapper();

        UUID id = UUID.randomUUID();
        String name = "name";
        String email = "email";
        String password = "password";
        AppUser user = new AppUser(id, name, email, password);
        // act
        UserDTO dto = converter.EntityToDTO(user);
        // assert
        Assertions.assertEquals(dto.getID(),id);
        Assertions.assertEquals(dto.getName(),name);
        Assertions.assertEquals(dto.getEmail(),email);
        Assertions.assertEquals(dto.getPassword(),password);
    }
}
