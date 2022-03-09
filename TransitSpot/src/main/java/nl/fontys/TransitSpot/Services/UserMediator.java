package nl.fontys.TransitSpot.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.fontys.TransitSpot.Collections.UserCollection;
import nl.fontys.TransitSpot.Converters.UsersConverter;
import nl.fontys.TransitSpot.DTO.UserDTO.UserDTO;
import nl.fontys.TransitSpot.DataJPA.RoleDataJPA;
import nl.fontys.TransitSpot.DataJPA.UserDataJPA;
import nl.fontys.TransitSpot.Entity.AppUser;
import nl.fontys.TransitSpot.Entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service @Transactional
public class UserMediator implements UserCollection, UserDetailsService {
    private UserDataJPA usersRepo;
    private RoleDataJPA rolesRepo;
    private PasswordEncoder encoder;
    private UsersConverter converter;

    @Autowired
    public UserMediator(UserDataJPA usersRepo, RoleDataJPA rolesRepo, PasswordEncoder encoder, UsersConverter converter) {
        this.usersRepo = usersRepo;
        this.rolesRepo = rolesRepo;
        this.encoder = encoder;
        this.converter = converter;
    }


    @Override
    public UserDTO saveUser(UserDTO user) {
        if(usersRepo.findAppUserByEmail(user.getEmail())==null){
            user.setPassword(encoder.encode(user.getPassword()));
            AppUser user_entity = this.converter.DTOtoEntity(user);
            return this.converter.EntityToDTO(usersRepo.save(user_entity));
        }
        return null;
    }

    @Override
    public Role saveRole(Role role) {
        return rolesRepo.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        AppUser user = usersRepo.findAppUserByEmail(email);
        Role role = rolesRepo.findRoleByName(roleName);
        user.getRoles().add(role);

    }

    @Override
    public UserDTO getUser(String email) {
        return this.converter.EntityToDTO(usersRepo.findAppUserByEmail(email));
    }

    @Override
    public UserDTO getUserByID(UUID ID) {
        return this.converter.EntityToDTO(usersRepo.findAppUsersByID(ID));
    }

    @Override
    public List<UserDTO> getUsers() {
        return this.converter.EntityToDTO(usersRepo.findAll());
    }

    @Override
    public boolean UpdatePassword(UUID id, String pass_old, String pass_new) {
        AppUser user = usersRepo.findAppUsersByID(id);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(passwordEncoder.matches(pass_old, user.getPassword())){
             user.setPassword(passwordEncoder.encode(pass_new));
             return true;
        }
        else return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = usersRepo.findAppUserByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("User not found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role ->{
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
