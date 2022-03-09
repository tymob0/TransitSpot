package nl.fontys.TransitSpot.DataJPA;

import nl.fontys.TransitSpot.Entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDataJPA extends JpaRepository<AppUser, UUID> {
   AppUser findAppUserByEmail(String Email);
   AppUser findAppUsersByID(UUID ID);
}
