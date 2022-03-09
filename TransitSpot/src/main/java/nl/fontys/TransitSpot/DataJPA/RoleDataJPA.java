package nl.fontys.TransitSpot.DataJPA;

import nl.fontys.TransitSpot.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleDataJPA extends JpaRepository<Role, UUID> {
    Role findRoleByName(String Name);
}
