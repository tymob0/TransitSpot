package nl.fontys.TransitSpot.Collections;

import nl.fontys.TransitSpot.DTO.UserDTO.UserDTO;
import nl.fontys.TransitSpot.Entity.AppUser;
import nl.fontys.TransitSpot.Entity.Role;

import java.util.List;
import java.util.UUID;

public interface UserCollection {
    UserDTO saveUser(UserDTO user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
    UserDTO getUser(String email);
    UserDTO getUserByID(UUID ID);
    List<UserDTO> getUsers();
    boolean UpdatePassword(UUID id, String pass_old,String pass_new);
}
