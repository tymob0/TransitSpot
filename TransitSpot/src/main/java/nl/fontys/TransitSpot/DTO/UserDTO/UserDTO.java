package nl.fontys.TransitSpot.DTO.UserDTO;

import lombok.Getter;
import lombok.Setter;
import nl.fontys.TransitSpot.Entity.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class UserDTO {
    private @Getter @Setter UUID ID;
    @Pattern(regexp = "^[a-zA-Z ,.'-]+$")
    private @Getter @Setter String name;
    @Email
    private @Getter @Setter String email;
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$")
    private @Getter @Setter String password;
    private @Getter @Setter Collection<Role> roles = new ArrayList<>();
    public UserDTO(UUID ID, String name, String email, String password, Collection<Role> roles) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
    public UserDTO(UUID ID, String name, String email, String password) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public UserDTO() {}
}
