package nl.fontys.TransitSpot.Entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Role {
    @Id
    @Column(name = "Role_ID")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private @Getter @Setter UUID ID = UUID.randomUUID();
    private @Getter @Setter String name;
    public Role(UUID ID, String name) {
        this.ID = ID;
        this.name = name;
    }
    public Role(){}
}