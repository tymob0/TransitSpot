package nl.fontys.TransitSpot.Entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name ="station")
public class Station {
    @Id
    @Column(name = "Station_ID")
    @Type(type="org.hibernate.type.UUIDCharType")
    private @Getter @Setter UUID ID;
    private @Getter @Setter String name;
    private @Getter @Setter String code;
    private @Getter @Setter String city;
    @OneToMany(
            mappedBy = "Origin",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Route> routes;
    @OneToMany(
            mappedBy = "Destination",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Route> destinations;
    public Station(String name) {
        this.name = name;
    }
    public Station(){};
}
