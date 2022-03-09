package nl.fontys.TransitSpot.Entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
public class AppUser {
    @Id
    @Column(name = "User_ID")
    @Type(type="org.hibernate.type.UUIDCharType")
    private @Getter @Setter UUID ID = UUID.randomUUID();
    private @Getter @Setter String name;
    private @Getter @Setter String email;
    private @Getter @Setter String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private @Getter @Setter Collection<Role> roles = new ArrayList<>();
    @OneToMany(
            mappedBy = "traveler",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Ticket> tickets;
    public AppUser(UUID ID, String name, String email, String password) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public AppUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public AppUser() {}
}
