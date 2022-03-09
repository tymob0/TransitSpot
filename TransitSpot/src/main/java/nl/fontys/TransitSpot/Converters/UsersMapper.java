package nl.fontys.TransitSpot.Converters;
import nl.fontys.TransitSpot.DTO.Station.StationDTO;
import nl.fontys.TransitSpot.DTO.UserDTO.UserDTO;
import nl.fontys.TransitSpot.Entity.AppUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsersMapper implements UsersConverter {
    @Override
    public UserDTO EntityToDTO(AppUser user) {
        ModelMapper mapper = new ModelMapper();
        UserDTO map = mapper.map(user, UserDTO.class);
        return map;
    }

    @Override
    public AppUser DTOtoEntity(UserDTO user) {
        ModelMapper mapper = new ModelMapper();
        AppUser map = mapper.map(user, AppUser.class);
        return map;
    }

    @Override
    public List<UserDTO> EntityToDTO(List<AppUser> users) {
        return users.stream().map(x->EntityToDTO(x)).collect(Collectors.toList());
    }

    @Override
    public List<AppUser> DTOtoEntity(List<UserDTO> users) {
        return users.stream().map(x->DTOtoEntity(x)).collect(Collectors.toList());
    }
}
