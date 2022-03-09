package nl.fontys.TransitSpot;

import nl.fontys.TransitSpot.Collections.StationCollection;
import nl.fontys.TransitSpot.Collections.UserCollection;
import nl.fontys.TransitSpot.Converters.StationsMapper;
import nl.fontys.TransitSpot.Converters.UsersConverter;
import nl.fontys.TransitSpot.Converters.UsersMapper;
import nl.fontys.TransitSpot.DTO.Station.StationFullDTO;
import nl.fontys.TransitSpot.DTO.UserDTO.UserDTO;
import nl.fontys.TransitSpot.Entity.AppUser;
import nl.fontys.TransitSpot.Entity.Role;
import nl.fontys.TransitSpot.Entity.Station;
import nl.fontys.TransitSpot.Services.StationMediator;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
public class UserServiceMockTest {
    @Mock
    UserCollection usersDAL;
    AppUser user = new AppUser("name", "email","password");
    Role role = new Role(UUID.randomUUID(), "admin");
    UsersConverter converter = new UsersMapper();

    @BeforeEach
    public void setUp()  {
        List<AppUser> usersList = new ArrayList<>();
        usersList.add(user);
        List<Role> rolesList = new ArrayList<>();
        rolesList.add(role);
        when(usersDAL.getUsers()).thenReturn(converter.EntityToDTO(usersList));
    }

    @Test
    public void GetAllUsersReturnsListOfUsersDTOTest()
    {
        // arrange
        // act
        List<UserDTO> users = usersDAL.getUsers();
        // assert
        Assertions.assertEquals(users.get(0).getName(),"name");
        Assertions.assertEquals(users.get(0).getEmail(),"email");
        Assertions.assertEquals(users.get(0).getPassword(),"password");
    }

    @Test
    public void AddUserReturnsUserDTOTest()
    {
        // arrange
        UserDTO user = new UserDTO(UUID.randomUUID(),"name1", "email1", "password1");
        // act
        usersDAL.saveUser(user);

        ArgumentCaptor<UserDTO> UsersArgumentCaptor =
                ArgumentCaptor.forClass(UserDTO.class);
        verify(usersDAL).saveUser(UsersArgumentCaptor.capture());
        AppUser finalRes = converter.DTOtoEntity(UsersArgumentCaptor.getValue());
        // assert
        Assertions.assertEquals(finalRes.getName(),"name1");
        Assertions.assertEquals(finalRes.getEmail(),"email1");
        Assertions.assertEquals(finalRes.getPassword(),"password1");
    }

    @Test
    public void AddRoleReturnsRoleTest()
    {
        // arrange
        Role role = new Role(UUID.randomUUID(),"name1");
        // act
        usersDAL.saveRole(role);
        ArgumentCaptor<Role> RolesArgumentCaptor =
                ArgumentCaptor.forClass(Role.class);
        verify(usersDAL).saveRole(RolesArgumentCaptor.capture());
        Role finalRes = RolesArgumentCaptor.getValue();
        // assert
        Assertions.assertEquals(finalRes.getName(),"name1");
    }

}
