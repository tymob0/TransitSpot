package nl.fontys.TransitSpot.Converters;

import nl.fontys.TransitSpot.DTO.Station.StationDTO;
import nl.fontys.TransitSpot.DTO.UserDTO.UserDTO;
import nl.fontys.TransitSpot.Entity.AppUser;
import nl.fontys.TransitSpot.Entity.Station;

import java.util.List;

public interface UsersConverter {
    UserDTO EntityToDTO(AppUser user);
    AppUser DTOtoEntity(UserDTO user);
    List<UserDTO> EntityToDTO(List<AppUser> users);
    List<AppUser> DTOtoEntity(List<UserDTO> users);
}
